package ds.proj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class AditionalFunctionsTest {
    @Test
    public void testRound() {
        assertEquals(10.0, AditionalFunctions.round(10.0, 1), 0.0);
        assertEquals(10.0, AditionalFunctions.round(10.0, 0), 0.0);
        assertEquals(0.5, AditionalFunctions.round(0.5, 1), 0.0);
        assertEquals(-0.5, AditionalFunctions.round(-0.5, 1), 0.0);
        assertThrows(IllegalArgumentException.class, () -> AditionalFunctions.round(10.0, -1));
    }
}

