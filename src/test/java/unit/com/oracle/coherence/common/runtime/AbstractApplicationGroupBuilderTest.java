package com.oracle.coherence.common.runtime;

import org.junit.Test;
import org.mockito.Matchers;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jonathan Knight
 */
public class AbstractApplicationGroupBuilderTest {

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldRealizeApplicationUsingBuilders() throws Exception {
        ApplicationBuilder builder1 = mock(ApplicationBuilder.class);
        ApplicationBuilder builder2 = mock(ApplicationBuilder.class);
        ApplicationSchema schema1 = mock(ApplicationSchema.class);
        ApplicationSchema schema2 = mock(ApplicationSchema.class);
        Application application = mock(Application.class);

        when(builder1.realize(Matchers.<ApplicationSchema>any(),
                Matchers.<String>any(), Matchers.<ApplicationConsole>any())).thenReturn(application);
        when(builder2.realize(Matchers.<ApplicationSchema>any(),
                Matchers.<String>any(), Matchers.<ApplicationConsole>any())).thenReturn(application);

        AbstractApplicationGroupBuilder groupBuilder = new AbstractApplicationGroupBuilderStub();
        groupBuilder.addBuilder(builder1, schema1, "testA", 2);
        groupBuilder.addBuilder(builder2, schema2, "testB", 1);
        groupBuilder.realize();

        verify(builder1).realize(same(schema1), eq("testA-0"), isA(NullApplicationConsole.class));
        verify(builder1).realize(same(schema1), eq("testA-1"), isA(NullApplicationConsole.class));
        verify(builder2).realize(same(schema2), eq("testB-0"), isA(NullApplicationConsole.class));
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldCallCreateApplicationGroupWithApplications() throws Exception {
        ApplicationBuilder builder = mock(ApplicationBuilder.class);
        ApplicationSchema schema = mock(ApplicationSchema.class);
        Application app1 = mock(Application.class);
        Application app2 = mock(Application.class);

        when(builder.realize(Matchers.<ApplicationSchema>any(),
                Matchers.<String>any(), Matchers.<ApplicationConsole>any())).thenReturn(app1, app2);

        AbstractApplicationGroupBuilderStub groupBuilder = new AbstractApplicationGroupBuilderStub();
        groupBuilder.addBuilder(builder, schema, "test", 2);
        groupBuilder.realize();

        assertThat(groupBuilder.applications, contains(app1, app2));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldPassConsoleToBuilders() throws Exception {
        ApplicationBuilder builder1 = mock(ApplicationBuilder.class);
        ApplicationSchema schema1 = mock(ApplicationSchema.class);
        Application application = mock(Application.class);

        when(builder1.realize(Matchers.<ApplicationSchema>any(),
                Matchers.<String>any(), Matchers.<ApplicationConsole>any())).thenReturn(application);

        AbstractApplicationGroupBuilder groupBuilder = new AbstractApplicationGroupBuilderStub();
        groupBuilder.addBuilder(builder1, schema1, "testA", 1);

        SystemApplicationConsole console = new SystemApplicationConsole();
        groupBuilder.realize(console);

        verify(builder1).realize(same(schema1), eq("testA-0"), same(console));
    }

    private class AbstractApplicationGroupBuilderStub extends AbstractApplicationGroupBuilder {
        private List<Application> applications;

        @SuppressWarnings({"unchecked"})
        @Override
        protected ApplicationGroup createApplicationGroup(List applications) {
            this.applications = applications;
            return null;
        }
    }
}
