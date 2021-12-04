package ds.proj.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import ds.proj.gui.ValidationException;
import org.junit.Test;

public class ProccesorsGeneratorTest {
    @Test
    public void testGenerateShuffleProccesors() throws ValidationException {
        assertEquals(3, ProccesorsGenerator.generateShuffleProccesors(3).length);
    }

    @Test
    public void testGenerateShuffleProccesorsAndIds() throws ValidationException {
        assertEquals(3, (new ProccesorsGenerator()).generateShuffleProccesorsAndIds(3, 3).length);
        assertEquals(0, (new ProccesorsGenerator()).generateShuffleProccesorsAndIds(0, 3).length);
    }

    @Test
    public void testGetProccesor() {
        assertThrows(ValidationException.class, () -> (new ProccesorsGenerator()).getProccesor());
    }
}

