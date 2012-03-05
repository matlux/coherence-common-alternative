package com.oracle.coherence.common.classloader;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jonathan Knight
 */
public class ClassLoaderOutputStream extends FilterOutputStream {
    public static enum Type {OUT, ERR}

    public static PrintStream originalOut;
    public static PrintStream originalErr;
    private static final AtomicBoolean inUse = new AtomicBoolean(false);

    private static InheritableThreadLocal<Context> context = new InheritableThreadLocal<Context>();
    private static InheritableThreadLocal<AtomicLong> outCount = new InheritableThreadLocal<AtomicLong>();
    private static InheritableThreadLocal<AtomicLong> errCount = new InheritableThreadLocal<AtomicLong>();

    private static final Context defaultContext = new Context("System");

    public static synchronized void use() {
        if (!inUse.getAndSet(true)) {
            originalOut = System.out;
            originalErr = System.err;
            System.setOut(new PrintStream(new ClassLoaderOutputStream(Type.OUT), true));
            System.setErr(new PrintStream(new ClassLoaderOutputStream(Type.ERR), true));
        }
    }

    public static void unuse() {
        if (inUse.getAndSet(false)) {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    public static void setContext(Context ctx) {
        context.set(ctx);
    }

    public static Context getContext() {
        return context.get();
    }

    private Type type;
    private boolean writtenEOL = true;

    private ClassLoaderOutputStream(Type type) {
        super((type == Type.OUT) ? originalOut : originalErr);
        this.type = type;
    }

    @Override
    public void write(int b) throws IOException {
        Context ctx;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader instanceof ChildFirstClassLoader) {
            ctx = ((ContextProvider)classLoader).getContext();
        } else {
            ctx = context.get();
        }
        if (ctx == null) {
            ctx = defaultContext;
        }
        if (ctx != null) {
            if (writtenEOL && b != '\n' && b != '\r') {
                long lineNumber = 0;
                switch (type) {
                    case OUT:
                        lineNumber = ctx.outCount.incrementAndGet();
                        break;
                    case ERR:
                        lineNumber = ctx.errCount.incrementAndGet();
                        break;
                }

                StringBuilder line = new StringBuilder("[");
                line.append(ctx.name).append(':').append(type.name()).append("] %4d: ");
                byte[] bytes = String.format(line.toString(), lineNumber).getBytes();
                for (byte c : bytes) {
                    super.write(c);
                }
                writtenEOL = false;
            }

            super.write(b);

            if (b == '\n' || b == '\r') {
                writtenEOL = true;
            }
        } else {
            super.write(b);
        }
    }

    public static class Context {
        private String name;
        private AtomicLong outCount = new AtomicLong(0);
        private AtomicLong errCount = new AtomicLong(0);

        public Context(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " out=" + this.outCount.get() + " err" + this.errCount.get();
        }
    }

    public static interface ContextProvider {
        Context getContext();
    }
}
