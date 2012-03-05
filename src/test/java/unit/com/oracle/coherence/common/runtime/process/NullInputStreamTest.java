package com.oracle.coherence.common.runtime.process;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Jonathan Knight
 */
public class NullInputStreamTest {

    @Test
    public void shouldReturnBytesInOrder() throws Exception {
        NullInputStream stream = new NullInputStream();
        stream.addByte((byte)2);
        stream.addByte((byte)4);
        stream.addByte((byte)19);

        assertThat(stream.read(), is(2));
        assertThat(stream.read(), is(4));
        assertThat(stream.read(), is(19));
    }

}
