package com.oracle.coherence.common.runtime;

import com.oracle.coherence.common.events.dispatching.EventDispatcher;
import com.oracle.coherence.common.events.lifecycle.LifecycleEvent;
import com.oracle.coherence.common.events.processing.EventProcessor;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Jonathan Knight
 */
public class AbstractApplicationBuilderTest {

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldSendEvents() throws Exception {
        EventProcessor listener = mock(EventProcessor.class);
        LifecycleEvent event = mock(LifecycleEvent.class);

        AbstractApplicationBuilder builder = new AbstractApplicationBuilderStub();
        builder.addApplicationLifecycleProcessor(listener);
        builder.sendEvent(event);

        verify(listener).process((EventDispatcher) isNull(), same(event));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldCallRealizeWithCorrectSchema() throws Exception {
        ApplicationSchema schema = mock(ApplicationSchema.class);

        AbstractApplicationBuilderStub builder = new AbstractApplicationBuilderStub();
        builder.realize(schema);
        assertThat(builder.schema, is(sameInstance(schema)));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldCallRealizeWithNonNullName() throws Exception {
        ApplicationSchema schema = mock(ApplicationSchema.class);

        AbstractApplicationBuilderStub builder = new AbstractApplicationBuilderStub();
        builder.realize(schema);
        assertThat(builder.name, is(notNullValue()));
        assertThat(builder.name.length(), is(greaterThan(0)));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldCallRealizeWithNullApplicationConsole_1() throws Exception {
        ApplicationSchema schema = mock(ApplicationSchema.class);

        AbstractApplicationBuilderStub builder = new AbstractApplicationBuilderStub();
        builder.realize(schema);
        assertThat(builder.console, is(instanceOf(NullApplicationConsole.class)));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldCallRealizeWithCorrectName() throws Exception {
        ApplicationSchema schema = mock(ApplicationSchema.class);

        AbstractApplicationBuilderStub builder = new AbstractApplicationBuilderStub();
        builder.realize(schema, "test-app");
        assertThat(builder.name, is("test-app"));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldCallRealizeWithNullApplicationConsole_2() throws Exception {
        ApplicationSchema schema = mock(ApplicationSchema.class);

        AbstractApplicationBuilderStub builder = new AbstractApplicationBuilderStub();
        builder.realize(schema, "test-app");
        assertThat(builder.console, is(instanceOf(NullApplicationConsole.class)));
    }

    private class AbstractApplicationBuilderStub extends AbstractApplicationBuilder {
        private Application application;
        private ApplicationSchema schema;
        private String name;
        private ApplicationConsole console;

        private AbstractApplicationBuilderStub() {
            this(null);
        }

        private AbstractApplicationBuilderStub(Application application) {
            this.application = application;
        }

        @Override
        public Application realize(ApplicationSchema schema, String name, ApplicationConsole console) throws IOException {
            this.schema = schema;
            this.name = name;
            this.console = console;
            return this.application;
        }
    }
}
