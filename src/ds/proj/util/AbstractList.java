/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.util;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author husky
 * @param <E>
 */
public abstract class AbstractList<E> implements List<E> {

    protected int modCount;

    public AbstractList() {

    }

    @Override
    public abstract boolean add(E element);

    @Override
    public abstract void add(int index, E element);

    @Override
    public abstract boolean addAll(List<? extends E> c);

    @Override
    public abstract boolean addAll(int index, List<? extends E> c);

    @Override
    public abstract void clear();

    @Override
    public abstract E get(int index);

    @Override
    public abstract int hashCode();

    @Override
    public abstract int indexOf(Object O);

    @Override
    public abstract int lastIndexOf(Object o);

    @Override
    public abstract Iterator<E> iterator();

    @Override
    public abstract ListIterator<E> listIterator();

    @Override
    public abstract ListIterator<E> listIterator(int index);

    @Override
    public abstract E remove(int index);

    @Override
    public abstract boolean remove(Object o);

    @Override
    public abstract boolean removeAll(List<?> c);

    @Override
    public abstract boolean retainAll(List<?> c);

    @Override
    public abstract E set(int index, E element);

    @Override
    public abstract int size();

    @Override
    public abstract List<E> subList(int fromIndex, int toIndex);

    @Override
    public abstract Object[] toArray();

    @Override
    public abstract <T> T[] toArray(T[] a);

}
