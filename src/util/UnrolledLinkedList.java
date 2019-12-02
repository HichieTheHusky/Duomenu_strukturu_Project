/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author husky
 */
public class UnrolledLinkedList<E> extends AbstractList<E> implements List<E>, Serializable {

    /**
     * The maximum number of elements that can be stored in a single node.
     */
    private int nodeCapacity;

    /**
     * The current size of this list.
     */
    private int size = 0;

    /**
     * The first node of this list.
     */
    private Node firstNode;

    /**
     * The last node of this list.
     */
    private Node lastNode;

    /**
     * Constructs an empty list with the specified
     * {@link UnrolledLinkedList#nodeCapacity nodeCapacity}. For performance
     * reasons <tt>nodeCapacity</tt> must be greater than or equal to 8.
     *
     * @param nodeCapacity The maximum number of elements that can be stored in
     * a single node.
     * @throws IllegalArgumentException if <tt>nodeCapacity</tt> is less than 8
     */
    public UnrolledLinkedList(int nodeCapacity) throws IllegalArgumentException {

        if (nodeCapacity < 8) {
            throw new IllegalArgumentException("nodeCapacity < 8");
        }
        this.nodeCapacity = nodeCapacity;
        firstNode = new Node();
        lastNode = firstNode;

    }

    /**
     * Constructs an empty list with
     * {@link UnrolledLinkedList#nodeCapacity nodeCapacity} of 16.
     */
    public UnrolledLinkedList() {

        this(16);

    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(int index, E e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(int index, List<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int indexOf(Object O) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(List<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(List<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(List<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Node {

        /**
         * The next node.
         */
        Node next;

        /**
         * The previous node.
         */
        Node previous;

        /**
         * The number of elements stored in this node.
         */
        int numElements = 0;

        /**
         * The array in which the elements are stored.
         */
        Object[] elements;

        /**
         * Constructs a new node.
         */
        Node() {

            elements = new Object[nodeCapacity];
        }
    }
}
