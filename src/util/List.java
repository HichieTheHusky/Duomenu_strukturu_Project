package util;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author husky
 * @param <E>
 */
public interface List<E> {

    public boolean add(E element);

    public void add(int index, E element);

    public boolean addAll(int index, List<? extends E> c);

    public void clear();

    public boolean contains(Object o);

    public boolean containsAll(List<? extends E> c);

    @Override
    public boolean equals(Object o);

    public E get(int index);

    @Override
    public int hashCode();

    public int indexOf(Object o);

    public boolean isEmpty();

    public int lastIndexOf(Object o);

    public Iterator<E> iterator();

    public ListIterator<E> listIterator();

    public ListIterator<E> listIterator(int index);

    public E remove(int index);

    public boolean remove(Object o);

    public boolean removeAll(List<?> c);

    public boolean retainAll(List<?> c);

    public E set(int index, E element);

    public int size();

    public List<E> subList(int fromIndex, int toIndex);

    public Object[] toArray();

    public <T> T[] toArray(T[] a);

//    default void replaceAll(UnaryOperator<E> operator);
//    default void sort(Comparator<? super E> c);
}
