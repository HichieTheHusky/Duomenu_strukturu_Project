package ds.proj.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProccesorTest {
    @Test
    public void testBuilderBuild() {
        Proccesor actualBuildResult = (new Proccesor.Builder()).build();
        assertEquals("", actualBuildResult.getBrand());
        assertEquals(-1, actualBuildResult.getYear());
        assertEquals("", actualBuildResult.getType());
        assertEquals(-1.0, actualBuildResult.getPrice(), 0.0);
        assertEquals("", actualBuildResult.getModel());
    }

    @Test
    public void testBuilderBuild2() {
        Proccesor.Builder builder = new Proccesor.Builder();
        builder.price(10.0);
        Proccesor actualBuildResult = builder.build();
        assertEquals("", actualBuildResult.getBrand());
        assertEquals(-1, actualBuildResult.getYear());
        assertEquals("", actualBuildResult.getType());
        assertEquals(10.0, actualBuildResult.getPrice(), 0.0);
        assertEquals("", actualBuildResult.getModel());
    }

    @Test
    public void testBuilderBuildRandom() {
        assertEquals("AMD", (new Proccesor.Builder()).buildRandom().getBrand());
    }

    @Test
    public void testBuilderConstructor() {
        // TODO: This test is incomplete.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     Builder.type
        //     Builder.brand
        //     Builder.model
        //     Builder.year
        //     Builder.price

        Proccesor.Builder actualBuilder = new Proccesor.Builder();
        actualBuilder.brand("Brand");
        actualBuilder.model("Model");
        actualBuilder.price(10.0);
        actualBuilder.type("Type");
        actualBuilder.year(1);
    }

    @Test
    public void testConstructor() {
        Proccesor actualProccesor = new Proccesor();
        actualProccesor.setPrice(10.0);
        assertEquals("", actualProccesor.getBrand());
        assertEquals("", actualProccesor.getModel());
        assertEquals(10.0, actualProccesor.getPrice(), 0.0);
        assertEquals("", actualProccesor.getType());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("_: -1 10,0", actualProccesor.toString());
    }

    @Test
    public void testConstructor2() {
        Proccesor actualProccesor = new Proccesor(new Proccesor.Builder());
        assertEquals("", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("", actualProccesor.getModel());
    }

    @Test
    public void testConstructor3() {
        Proccesor.Builder builder = new Proccesor.Builder();
        builder.price(10.0);
        Proccesor actualProccesor = new Proccesor(builder);
        assertEquals("", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("", actualProccesor.getType());
        assertEquals(10.0, actualProccesor.getPrice(), 0.0);
        assertEquals("", actualProccesor.getModel());
    }

    @Test
    public void testConstructor4() {
        Proccesor actualProccesor = new Proccesor("Data String");
        assertEquals("String", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("Data", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("", actualProccesor.getModel());
    }

    @Test
    public void testConstructor5() {
        Proccesor actualProccesor = new Proccesor("");
        assertEquals("", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("", actualProccesor.getModel());
    }

    @Test
    public void testConstructor6() {
        Proccesor actualProccesor = new Proccesor("Trūksta duomenų -> ");
        assertEquals("duomenų", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("Trūksta", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("->", actualProccesor.getModel());
    }

    @Test
    public void testConstructor7() {
        Proccesor actualProccesor = new Proccesor("Netinkami gamybos metai, turi būti [2000:");
        assertEquals("gamybos", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("Netinkami", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("metai,", actualProccesor.getModel());
    }

    @Test
    public void testConstructor8() {
        Proccesor actualProccesor = new Proccesor("]");
        assertEquals("", actualProccesor.getBrand());
        assertEquals(-1, actualProccesor.getYear());
        assertEquals("]", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("", actualProccesor.getModel());
    }

    @Test
    public void testConstructor9() {
        Proccesor actualProccesor = new Proccesor("Trūksta duomenų -> 42");
        assertEquals("duomenų", actualProccesor.getBrand());
        assertEquals(42, actualProccesor.getYear());
        assertEquals("Trūksta", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("->", actualProccesor.getModel());
    }

    @Test
    public void testConstructor10() {
        Proccesor actualProccesor = new Proccesor("Trūksta duomenų -> 42 Kaina už leistinų ribų [10.0:210000.0]");
        assertEquals("duomenų", actualProccesor.getBrand());
        assertEquals(42, actualProccesor.getYear());
        assertEquals("Trūksta", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("->", actualProccesor.getModel());
    }

    @Test
    public void testConstructor11() {
        Proccesor actualProccesor = new Proccesor("Trūksta duomenų -> 4242");
        assertEquals("duomenų", actualProccesor.getBrand());
        assertEquals(4242, actualProccesor.getYear());
        assertEquals("Trūksta", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("->", actualProccesor.getModel());
    }

    @Test
    public void testConstructor12() {
        Proccesor actualProccesor = new Proccesor("Type", "Brand", "Model", 1, 10.0);

        assertEquals("Brand", actualProccesor.getBrand());
        assertEquals(1, actualProccesor.getYear());
        assertEquals("Type", actualProccesor.getType());
        assertEquals(10.0, actualProccesor.getPrice(), 0.0);
        assertEquals("Model", actualProccesor.getModel());
    }

    @Test
    public void testConstructor13() {
        Proccesor actualProccesor = new Proccesor("Type", "Brand", "Model", 2000, 10.0);

        assertEquals("Brand", actualProccesor.getBrand());
        assertEquals(2000, actualProccesor.getYear());
        assertEquals("Type", actualProccesor.getType());
        assertEquals(10.0, actualProccesor.getPrice(), 0.0);
        assertEquals("Model", actualProccesor.getModel());
    }

    @Test
    public void testConstructor14() {
        Proccesor actualProccesor = new Proccesor("Type", "Brand", "Model", 1, -1.0);

        assertEquals("Brand", actualProccesor.getBrand());
        assertEquals(1, actualProccesor.getYear());
        assertEquals("Type", actualProccesor.getType());
        assertEquals(-1.0, actualProccesor.getPrice(), 0.0);
        assertEquals("Model", actualProccesor.getModel());
    }

    @Test
    public void testParse() {
        Proccesor proccesor = new Proccesor("Data String");
        proccesor.parse("Data");
        assertEquals("Data", proccesor.getType());
    }

    @Test
    public void testParse2() {
        Proccesor proccesor = new Proccesor("Data String");
        proccesor.parse("Trūksta duomenų -> ");
        assertEquals("duomenų", proccesor.getBrand());
        assertEquals("Trūksta", proccesor.getType());
        assertEquals("->", proccesor.getModel());
    }

    @Test
    public void testParse3() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by parse(String)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        (new Proccesor("Data String")).parse("");
    }

    @Test
    public void testParse4() {
        Proccesor proccesor = new Proccesor("Data String");
        proccesor.parse("Trūksta duomenų -> Trūksta duomenų -> ");
        assertEquals("duomenų", proccesor.getBrand());
        assertEquals("Trūksta", proccesor.getType());
        assertEquals("->", proccesor.getModel());
    }

    @Test
    public void testParse5() {
        Proccesor proccesor = new Proccesor("Data String");
        proccesor.parse("Trūksta duomenų -> 42");
        assertEquals("duomenų", proccesor.getBrand());
        assertEquals(42, proccesor.getYear());
        assertEquals("Trūksta", proccesor.getType());
        assertEquals("->", proccesor.getModel());
    }

    @Test
    public void testToString_data() {
        assertEquals("Data String  -1 -1,0", (new Proccesor("Data String")).ToString_data());
    }

    @Test
    public void testEquals() {
        assertFalse((new Proccesor("Data String")).equals(null));
        assertFalse((new Proccesor("Data String")).equals("Different type to Proccesor"));
    }

    @Test
    public void testEquals2() {
        Proccesor proccesor = new Proccesor("Data String");
        assertTrue(proccesor.equals(proccesor));
        int expectedHashCodeResult = proccesor.hashCode();
        assertEquals(expectedHashCodeResult, proccesor.hashCode());
    }

    @Test
    public void testEquals3() {
        Proccesor proccesor = new Proccesor("Data String");
        Proccesor proccesor1 = new Proccesor("Data String");
        assertTrue(proccesor.equals(proccesor1));
        int expectedHashCodeResult = proccesor.hashCode();
        assertEquals(expectedHashCodeResult, proccesor1.hashCode());
    }

    @Test
    public void testEquals4() {
        Proccesor proccesor = new Proccesor("");
        assertFalse(proccesor.equals(new Proccesor("Data String")));
    }

    @Test
    public void testEquals5() {
        Proccesor proccesor = new Proccesor("Type", "Brand", "Model", 1, 10.0);
        assertFalse(proccesor.equals(new Proccesor("Data String")));
    }

    @Test
    public void testEquals6() {
        Proccesor proccesor = new Proccesor("Data String");
        proccesor.setPrice(10.0);
        assertFalse(proccesor.equals(new Proccesor("Data String")));
    }

    @Test
    public void testEquals7() {
        Proccesor proccesor = new Proccesor("Data StringData String");
        assertFalse(proccesor.equals(new Proccesor("Data String")));
    }
}

