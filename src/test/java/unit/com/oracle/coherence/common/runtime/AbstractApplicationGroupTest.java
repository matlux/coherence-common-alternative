package com.oracle.coherence.common.runtime;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jonathan Knight
 */
public class AbstractApplicationGroupTest {

    @Test
    public void shouldStoreApplicationsByName() throws Exception {
        Application memberOne = mock(Application.class);
        Application memberTwo = mock(Application.class);

        List<Application> members = Arrays.asList(memberOne, memberTwo);

        when(memberOne.getName()).thenReturn("m1");
        when(memberTwo.getName()).thenReturn("m2");

        AbstractApplicationGroup<Application> group = new AbstractApplicationGroupStub(members);

        assertThat(group.getApplication("m1"), is(sameInstance(memberOne)));
        assertThat(group.getApplication("m2"), is(sameInstance(memberTwo)));
    }

    @Test
    public void shouldIterateOverApplications() throws Exception {
        Application memberOne = mock(Application.class);
        Application memberTwo = mock(Application.class);

        List<Application> members = Arrays.asList(memberOne, memberTwo);

        when(memberOne.getName()).thenReturn("m1");
        when(memberTwo.getName()).thenReturn("m2");

        AbstractApplicationGroup<Application> group = new AbstractApplicationGroupStub(members);
        assertThat(group, containsInAnyOrder(memberOne, memberTwo));
    }

    @Test
    public void shouldDestroyApplications() throws Exception {
        Application memberOne = mock(Application.class);
        Application memberTwo = mock(Application.class);

        List<Application> members = Arrays.asList(memberOne, memberTwo);

        when(memberOne.getName()).thenReturn("m1");
        when(memberTwo.getName()).thenReturn("m2");

        AbstractApplicationGroup<Application> group = new AbstractApplicationGroupStub(members);
        group.destroy();

        verify(memberOne).destroy();
        verify(memberTwo).destroy();
    }

    private class AbstractApplicationGroupStub extends AbstractApplicationGroup<Application> {
        private AbstractApplicationGroupStub(List<Application> applications) {
            super(applications);
        }
    }
}
