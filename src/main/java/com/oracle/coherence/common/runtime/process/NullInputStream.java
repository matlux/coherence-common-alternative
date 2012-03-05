package com.oracle.coherence.common.runtime.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Jonathan Knight
 */
public class NullInputStream extends InputStream {

    private BlockingQueue<Byte> bytes;

    public NullInputStream() {
        bytes = new LinkedBlockingQueue<Byte>();
    }

    public void addByte(byte b) {
        bytes.add(b);
    }

    @Override
    public int read() throws IOException {
        try {
            return bytes.take();
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}
