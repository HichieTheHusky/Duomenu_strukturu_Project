package ds.proj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnrolledLinkedListTest {
    @Test
    public void testConstructor() {
        assertEquals(0, (new UnrolledLinkedList<>()).size());
        assertThrows(IllegalArgumentException.class, () -> new UnrolledLinkedList<>(1));
    }

    @Test
    public void testConstructor2() {
        UnrolledLinkedList<Object> actualUnrolledLinkedList = new UnrolledLinkedList<>();
        assertTrue(actualUnrolledLinkedList.isEmpty());
        assertEquals(Short.SIZE, actualUnrolledLinkedList.nodeCapacity);
        assertEquals(0, actualUnrolledLinkedList.toArray().length);
        UnrolledLinkedList<Object>.Node node = actualUnrolledLinkedList.lastNode;
        assertEquals(Short.SIZE, node.elements.length);
        assertNull(node.previous);
        assertEquals(0, node.numElements);
    }

    @Test
    public void testConstructor3() throws IllegalArgumentException {
        UnrolledLinkedList<Object> actualUnrolledLinkedList = new UnrolledLinkedList<>(8);
        assertTrue(actualUnrolledLinkedList.isEmpty());
        assertEquals(8, actualUnrolledLinkedList.nodeCapacity);
        assertEquals(0, actualUnrolledLinkedList.toArray().length);
        UnrolledLinkedList<Object>.Node node = actualUnrolledLinkedList.lastNode;
        assertEquals(8, node.elements.length);
        assertNull(node.previous);
        assertEquals(0, node.numElements);
    }

    @Test
    public void testSubSetConstructor() {
        UnrolledLinkedList<Object> unrolledLinkedList = new UnrolledLinkedList<>();
        UnrolledLinkedList<Object> unrolledLinkedList1 = unrolledLinkedList.new SubSet<>(new UnrolledLinkedList<>(), 1, 3).parentList;
        assertEquals(0, unrolledLinkedList1.size());
        assertTrue(unrolledLinkedList1.isEmpty());
    }
}

