package ch03.scratch;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class AssertHamcrestTest {

    @Test
    @Ignore
    @ExpectToFail
    public void assertDoublesEqual() {
        assertThat(2.32 * 3, equalTo(6.96));
    }

    @Test
    public void assertWithTolerance() {
        assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
    }

    @Test
    public void assertDoublesCloseTo() {
        assertThat(2.32 * 3, closeTo(6.96, 0.0005));
    }
}
