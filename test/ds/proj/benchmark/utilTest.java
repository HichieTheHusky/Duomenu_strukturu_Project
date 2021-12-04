package ds.proj.benchmark;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class utilTest {
    @Test
    public void testGenerateList() {
        ArrayList<Float> resultFloatList = new ArrayList<>();
        util.generateList(resultFloatList, 3, new float[]{10.0f, 10.0f, 10.0f, 10.0f});
        assertEquals(3, resultFloatList.size());
    }

    @Test
    public void testGenerateIndexes() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by generateIndexes(int[], int)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        util.generateIndexes(new int[]{}, 3);
    }

    @Test
    public void testGenerateIndexes2() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by generateIndexes(int[], int)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        util.generateIndexes(new int[]{1, 1, 1, 1}, Integer.MIN_VALUE);
    }

    @Test
    public void testShuffleProc() {
        // TODO: This test is incomplete.
        //   Reason: R002 Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   There are no fields that could be asserted on.

        util.shuffleProc(new float[]{10.0f, 10.0f, 10.0f, 10.0f});
    }
}

