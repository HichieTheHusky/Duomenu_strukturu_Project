/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import ds.proj.demo.Proccesor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author husky
 */

@RunWith(Enclosed.class)
public class original_UnrolledLinkedListTest {

    private static UnrolledLinkedList<Proccesor> list;
    private static UnrolledLinkedList<Proccesor> list_by_number;

    private static final Proccesor c1 = new Proccesor("GPU", "Nvidia", "RTX_2080_super", 2019, 589);
    private static final Proccesor c2 = new Proccesor("GPU", "Nvidia", "RTX_2080", 2018, 539);
    private static final Proccesor c3 = new Proccesor("GPU", "Nvidia", "GTX_1080TI", 2017, 599);
    private static final Proccesor c4 = new Proccesor("GPU AMD vega_56 2017 449");
    private static final Proccesor c5 = new Proccesor("GPU AMD vega_64 2017 599");
    private static final Proccesor c6 = new Proccesor("GPU AMD rx570 2017 399");

    public static void create() {
        list = new UnrolledLinkedList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
    }

    public static void create_by_number(int n) {
        list_by_number = new UnrolledLinkedList<>();
        switch(n) {
            default:
            case 6:
                list_by_number.add(c1);
            case 5:
                list_by_number.add(c2);
            case 4:
                list_by_number.add(c6);
            case 3:
                list_by_number.add(c3);
            case 2:
                list_by_number.add(c4);
            case 1:
                list_by_number.add(c5);
            case 0:
                break;
        }
    }

    @Before
    public void setUp() {
        create();
    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    /**
     * Test of add method, of class UnrolledLinkedList.
     */
    @Test
    public void testAdd_GenericType() {
        System.out.println("add");
        Proccesor e = new Proccesor("GPU Nvidia gtx_760 2017 250");
        boolean expResult = true;
        boolean result = list.add(e);

        Assert.assertEquals(expResult, result);
        Assert.assertEquals(e, list.get(list.size() - 1));
    }

    /**
     * Test of add method, of class UnrolledLinkedList.
     */
    @Test
    public void testAdd_int_GenericType() {
        System.out.println("addToIndex");
        int index = 1;
        Proccesor e = new Proccesor("GPU Nvidia gtx_780 2017 500");
        list.add(index, e);
        Assert.assertEquals(e, list.get(index));

    }

    /**
     * Test of addAll method, of class UnrolledLinkedList.
     */
    @Test
    public void testAddAll_List() {
        System.out.println("addAll");
        UnrolledLinkedList instance = new UnrolledLinkedList();
        Proccesor e1 = new Proccesor("GPU Nvidia gtx_780 2017 100");
        Proccesor e2 = new Proccesor("GPU Nvidia gtx_780 2017 200");
        Proccesor e3 = new Proccesor("GPU Nvidia gtx_780 2017 300");
        instance.add(e1);
        instance.add(e2);
        instance.add(e3);

        boolean expResult = true;
        boolean result = list.addAll(instance);

        Assert.assertEquals(expResult, result);
        Assert.assertEquals(e1, list.get(list.size() - 3));
        Assert.assertEquals(e2, list.get(list.size() - 2));
        Assert.assertEquals(e3, list.get(list.size() - 1));

    }

    /**
     * Test of addAll method, of class UnrolledLinkedList.
     */
    @Test
    public void testAddAll_int_List() {
        System.out.println("addAllIndex");
        UnrolledLinkedList instance = new UnrolledLinkedList();
        Proccesor e1 = new Proccesor("GPU Nvidia gtx_780 2017 100");
        Proccesor e2 = new Proccesor("GPU Nvidia gtx_780 2017 200");
        Proccesor e3 = new Proccesor("GPU Nvidia gtx_780 2017 300");
        instance.add(e1);
        instance.add(e2);
        instance.add(e3);

        boolean expResult = true;
        boolean result = list.addAll(2, instance);
        Assert.assertEquals(expResult, result);
        Assert.assertEquals(e1, list.get(2));
        Assert.assertEquals(e2, list.get(3));
        Assert.assertEquals(e3, list.get(4));

    }

    /**
     * Test of clear method, of class UnrolledLinkedList.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Iterator<Proccesor> it = list.iterator();
        UnrolledLinkedList instance = new UnrolledLinkedList();
        while (it.hasNext()) {
            instance.add(it.next());
        }
        instance.clear();
        Assert.assertEquals(0, instance.size());
        Assert.assertEquals(null, instance.firstNode.next);
        Assert.assertEquals(0, instance.firstNode.numElements);

    }

    /**
     * Test of get method, of class UnrolledLinkedList.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int index = 5;
        Object expResult = c6;
        Object result = list.get(index);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of hashCode method, of class UnrolledLinkedList.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        UnrolledLinkedList instance = new UnrolledLinkedList();
        int expResult = 1;
        int result = instance.hashCode();
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of indexOf method, of class UnrolledLinkedList.
     */
    @Test
    public void testIndexOf() {
        System.out.println("indexOf");
        Object o = null;
        int expResult1 = 4;
        int expResult2 = -1;
        int result1 = list.indexOf(c5);
        int result2 = list.indexOf("test");
        Assert.assertEquals(expResult1, result1);
        Assert.assertEquals(expResult2, result2);

    }

    /**
     * Test of lastIndexOf method, of class UnrolledLinkedList.
     */
    @Test
    public void testLastIndexOf() {
        System.out.println("lastIndexOf");
        list.add(c4);
        int expResult = 6;
        int result = list.lastIndexOf(c4);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of iterator method, of class UnrolledLinkedList.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        Proccesor expResult = c1;
        Proccesor result = list.iterator().next();
        Assert.assertEquals(expResult, result);

        UnrolledLinkedList instance = new UnrolledLinkedList();
        Assert.assertEquals(false, instance.iterator().hasNext());

    }

    /**
     * Test of listIterator method, of class UnrolledLinkedList.
     */
    @Test
    public void testListIterator_int() {
        System.out.println("listIterator");
        int index = 1;
        Proccesor expResult = c2;
        ListIterator iter = list.listIterator(index);
        Proccesor result = list.listIterator(index).next();
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of remove method, of class UnrolledLinkedList.
     */
    @Test
    public void testRemove_int() {
        System.out.println("removeIndex");
        int index = 1;
        Object expResult = c2;
        Object result = list.remove(index);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of remove method, of class UnrolledLinkedList.
     */
    @Test
    public void testRemove_Object() {
        System.out.println("removeObject");
        Object o = "test";
        boolean result1 = list.remove(c2);
        boolean result2 = list.remove(o);
        Assert.assertEquals(true, result1);
        Assert.assertEquals(false, result2);

    }

    /**
     * Test of removeAll method, of class UnrolledLinkedList.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        UnrolledLinkedList c1 = new UnrolledLinkedList();
        Proccesor e1 = new Proccesor("GPU Nvidia gtx_780 2017 100");
        Proccesor e2 = new Proccesor("GPU Nvidia gtx_780 2017 200");
        Proccesor e3 = new Proccesor("GPU Nvidia gtx_780 2017 300");
        c1.add(e1);
        c1.add(e2);
        c1.add(e3);
        UnrolledLinkedList c2 = new UnrolledLinkedList();
        c2.add(c2);
        c2.add(c3);
        c2.add(c4);

        boolean expResult = false;
        boolean result = list.removeAll(c1);
        Assert.assertEquals(expResult, result);
        expResult = true;
        result = list.removeAll(c2);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of retainAll method, of class UnrolledLinkedList.
     */
    @Test
    public void testRetainAll() {
        System.out.println("retainAll");
        UnrolledLinkedList c = new UnrolledLinkedList();
        c.add(c2);
        c.add(c3);
        c.add(c4);

        boolean expResult = true;
        boolean result = list.retainAll(c);
        Assert.assertEquals(expResult, result);
        expResult = false;
        result = list.retainAll(list);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of set method, of class UnrolledLinkedList.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        int index = 2;
        Proccesor e3 = new Proccesor("GPU Nvidia gtx_780 2017 300");
        Proccesor expResult = c3;
        Object result = list.set(index, e3);
        Assert.assertEquals(expResult, result);
        expResult = e3;
        Assert.assertEquals(expResult, list.get(index));

    }

    @RunWith(Parameterized.class)
    public static class TheParameterizedPart {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0, 0}, {1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}
            });
        }

        private int par_Input;
        private int par_Expected;

        public TheParameterizedPart(int par_input, int par_expected) {
            this.par_Input = par_input;
            this.par_Expected = par_expected;
        }

        /**
         * Test of size method, of class UnrolledLinkedList.
         */
        @Test
        public void testSize() {
            System.out.println("size");
            create_by_number(par_Input);
            int result = list_by_number.size();
            Assert.assertEquals(par_Expected, result);
        }
    }

    /**
     * Test of toArray method, of class UnrolledLinkedList.
     */
    @Test
    public void testToArray_0args() {
        System.out.println("toArray");
        Object[] expResult = {c1, c2, c3, c4, c5, c6};
        Object[] result = list.toArray();
        Assert.assertArrayEquals(expResult, result);

    }

    /**
     * Test of toArray method, of class UnrolledLinkedList.
     */
    @Test
    public void testToArray_GenericType() {
        System.out.println("toArray");
        Proccesor[] a = {};
        Proccesor[] expResulta = {c1, c2, c3, c4, c5, c6};
        Proccesor[] resulta = list.toArray(a);
        Assert.assertArrayEquals(expResulta, resulta);

        Proccesor e1 = new Proccesor("GPU Nvidia gtx_780 2017 100");
        Proccesor e2 = new Proccesor("GPU Nvidia gtx_780 2017 200");
        Proccesor e3 = new Proccesor("GPU Nvidia gtx_780 2017 300");
        Proccesor[] b = {e1, e2, e3};
        Proccesor[] resultb = list.toArray(b);
        Assert.assertArrayEquals(expResulta, resultb);

        Proccesor[] c = new Proccesor[10];
        Proccesor[] resultc = list.toArray(c);
        Assert.assertArrayEquals(expResulta, resultb);

    }

    /**
     * Test of contains method, of class UnrolledLinkedList.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Object o = null;
        boolean expResult = false;
        boolean result = list.contains(o);
        Assert.assertEquals(expResult, result);

        o = c1;
        expResult = true;
        result = list.contains(o);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of containsAll method, of class UnrolledLinkedList.
     */
    @Test
    public void testContainsAll() {
        System.out.println("containsAll");
        UnrolledLinkedList c = new UnrolledLinkedList();
        c.add(c1);
        c.add(c2);
        c.add(c3);

        boolean expResult = true;
        boolean result = list.containsAll(c);
        Assert.assertEquals(expResult, result);

        Proccesor e1 = new Proccesor("GPU Nvidia gtx_780 2017 100");
        c.add(e1);
        expResult = false;
        result = list.containsAll(c);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of isEmpty method, of class UnrolledLinkedList.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        UnrolledLinkedList instance = new UnrolledLinkedList();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        Assert.assertEquals(expResult, result);

        expResult = false;
        result = list.isEmpty();
        Assert.assertEquals(expResult, result);

    }

}
