/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds.proj.util;

//import com.sun.xml.internal.bind.v2.TODO;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author husky
 * @param <E>
 */
public class UnrolledLinkedList<E> extends AbstractList<E> implements List<E>, Serializable {

    /**
     * Maksimalus elementu skaicius viename mazge.
     */
    protected int nodeCapacity;

    /**
     * dabartinis saraso dydis
     */
    private int size = 0;

    /**
     * pirmas saraso mazgas
     */
    protected Node firstNode;

    /**
     * paskutinis saraso mazgas
     */
    protected Node lastNode;

    /**
     * sukonstruoja specifikuoto didzio sarasa
     * {@link UnrolledLinkedList#nodeCapacity nodeCapacity}. Del pagreitinto
     * veikimo <code>nodeCapacity</code> turi buti didesnis uz 8.
     *
     * @param nodeCapacity Maksimalus elementu skaicius viename mazge.
     *
     * @throws IllegalArgumentException if <code>nodeCapacity</code> yra
     * mazesnis uz 8
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
     * sukonstruoja tuscia sarasa su
     * {@link UnrolledLinkedList#nodeCapacity nodeCapacity} kuris yra nustatytas
     * 16.
     */
    public UnrolledLinkedList() {

        this(16);

    }

    /**
     * Prideda specifikuota elementa i saraso gala
     *
     * @param e elementas kuris bus idetas
     * @return <code>true</code> (paremtas pagal {@link Collection#add})
     */
    @Override
    public boolean add(E e) {

        insertIntoNode(lastNode, lastNode.numElements, e);
        return true;

    }

    /**
     * ideda specifikuota elementa i specifikuota vieta ( index ) sarase
     * pajudina tolimesnius elementus ( jeigu tokiu yra ) i desine puse (
     * prideda +1 prie tolimesniu elementu indekso )
     *
     * @param index indeksas kurio vietoje bus idejimas
     * @param element elementas kuris bus idetas
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node node;
        int p = 0;
        if (size - index > index) {
            node = firstNode;
            while (p <= index - node.numElements) {
                p += node.numElements;
                node = node.next;
            }
        } else {
            node = lastNode;
            p = size;
            while ((p -= node.numElements) > index) {
                node = node.previous;
            }
        }
        insertIntoNode(node, index - p, element);

    }

    /**
     * prideda visus elementus is nurodytos kolekcijos i dabartini sarasa
     * pridejimo eiliskumas yra nustatytas pagal paduotos kolekcijos savo turima
     * iteratoriu.
     *
     * @param index
     * @param c collection su elementais kurie bus prideti i si sarasa
     * @return <code>true</code> jeigu sis sarasas buvo pakeistas del sios
     * klases metodo iskvietimo
     * @throws NullPointerException if the specified collection is null
     * @see #add(Object)
     */
    @Override
    public boolean addAll(List<? extends E> c) {

        if (c == null) {
            throw new NullPointerException();
        }
        boolean changed = false;
        Iterator<? extends E> it = c.iterator();
        while (it.hasNext()) {
            add(it.next());
            changed = true;
        }
        return changed;

    }

    /**
     * *
     * prideda visus elementus is nurodytos kolekcijos i dabartini sarasa
     * pridejimo eiliskumas yra nustatytas pagal paduotos kolekcijos savo turima
     * iteratoriu. Pridejimo vieta yra pagal paduota indeksa.
     *
     * @param index collection su elementais kurie bus prideti i si sarasa
     * @param c collection su elementais kurie bus prideti i si sarasa
     * @return <code>true</code> jeigu sis sarasas buvo pakeistas del sios
     * klases metodo iskvietimo
     * @throws NullPointerException if the specified collection is null
     * @see #add(Object)
     */
    public boolean addAll(int index, List<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }

        int i = 0;

        boolean changed = false;
        Iterator<? extends E> it = c.iterator();
        while (it.hasNext()) {
            add(index + i, it.next());
            i++;
            changed = true;
        }
        return changed;

    }

    /**
     * Pasalina visus elementus is dabartinio saraso.
     */
    @Override
    public void clear() {

        Node node = firstNode.next;
        while (node != null) {
            Node next = node.next;
            node.next = null;
            node.previous = null;
            node.elements = null;
            node = next;
        }
        lastNode = firstNode;
        for (int ptr = 0; ptr < firstNode.numElements; ptr++) {
            firstNode.elements[ptr] = null;
        }
        firstNode.numElements = 0;
        firstNode.next = null;
        size = 0;

    }

    /**
     * grazina elementa kuris yra specifikuotame indekse
     *
     * @param index indeksas elemento kuris bus grazinamas
     * @return grazina elementa kuris yra specifikuotame indekse
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node;
        int p = 0;
        if (size - index > index) {
            node = firstNode;
            while (p <= index - node.numElements) {
                p += node.numElements;
                node = node.next;
            }
        } else {
            node = lastNode;
            p = size;
            while ((p -= node.numElements) > index) {
                node = node.previous;
            }
        }
        return (E) node.elements[index - p];

    }

    /**
     * grazina saraso hashCode
     * @return
     */
    @Override
    public int hashCode() {

        int hashCode = 1;

        for (Node node = firstNode; node != null; node = node.next) {
            for (int i = 0; i < node.numElements; i++) {
                hashCode = 31 * hashCode + (node.elements[i] == null ? 0 : node.elements[i].hashCode());
            }
        }
        return hashCode;

    }

    /**
     * grazina indeksa pirmos pasitaikusios pozicijos kurioje rastas
     * specifikuoto elemento siame sarasa, arba -1 jeigu sis sarasas 
     * neturi nurodyto elemento. Formaliau grazina zemiausia indeksa 
     * <code>i</code> kuris grazina true siuo kodu
     * <code>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</code>,
     * or -1 if there is no such index.
     *
     * @param o elementas kuris bus jieskomas
     * @return zemiausia indeksa nurodyto elemento siame sarase arba -1 jeigu
     * toks elementas nera surastas
     */
    @Override
    public int indexOf(Object o) {

        int index = 0;
        Node node = firstNode;
        if (o == null) {
            while (node != null) {
                for (int ptr = 0; ptr < node.numElements; ptr++) {
                    if (node.elements[ptr] == null) {
                        return index + ptr;
                    }
                }
                index += node.numElements;
                node = node.next;
            }
        } else {
            while (node != null) {
                for (int ptr = 0; ptr < node.numElements; ptr++) {
                    if ((node.elements[ptr]).equals(o)) {
                        return index + ptr;
                    }
                }
                index += node.numElements;
                node = node.next;
            }
        }
        return -1;

    }

    /**
     * grazina indeksa paskutinios pasitaikusios pozicijos kurioje rastas
     * specifikuoto elemento siame sarasa, arba -1 jeigu sis sarasas 
     * neturi nurodyto elemento. Formaliau grazina auksciausia indeksa 
     * <code>i</code> kuris grazina true siuo kodu
     * <code>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</code>,
     * or -1 if there is no such index.
     *
     * @param o elementas kuris bus jieskomas
     * @return zemiausia indeksa nurodyto elemento siame sarase arba -1 jeigu
     * toks elementas nera surastas
     */
    @Override
    public int lastIndexOf(Object o) {

        int index = size;
        Node node = lastNode;
        if (o == null) {
            while (node != null) {
                index -= node.numElements;
                for (int i = node.numElements - 1; i >= 0; i--) {
                    if (node.elements[i] == null) {
                        return (index + i);
                    }
                }
                node = node.previous;
            }
        } else {
            while (node != null) {
                index -= node.numElements;
                for (int i = node.numElements - 1; i >= 0; i--) {
                    if ((node.elements[i]).equals(o)) {
                        return (index + i);
                    }
                }
                node = node.previous;
            }
        }
        return -1;

    }

    /**
     * grazina iterariu per sios sarasa elementus taisiklingame eiliskume
     *
     * @return grazina iterariu per sios sarasa elementus 
     * taisiklingame eiliskume
     */
    @Override
    public Iterator<E> iterator() {

        return new ULLIterator(firstNode, 0, 0);

    }

    /**
     * grazina list tipo iterariu per sios sarasa elementus
     * taisiklingame eiliskume
     *
     * @return grazina list tipo iterariu per sios sarasa
     * elementus taisiklingame eiliskume
     */
    @Override
    public ListIterator<E> listIterator() {

        return new ULLIterator(firstNode, 0, 0);

    }

    /**
     * 
     * grazina list tipo iteratoriu per sios saraso elementus
     * ( taisiklingame elementu eiliskume ), pradetant nuo
     * nurodyto indekso siame sarase.  
     *
     * @param index indeksas pirmo elemento kuris bus graziniamas suo
     * list tipo iteratoriumi ( naudojant metoda <code>next</code>)
     * @return grazina list tipo iterariu per sios sarasa
     * elementus taisiklingame eiliskume
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node node;
        int p = 0;
        if (size - index > index) {
            node = firstNode;
            while (p <= index - node.numElements) {
                p += node.numElements;
                node = node.next;
            }
        } else {
            node = lastNode;
            p = size;
            while ((p -= node.numElements) > index) {
                node = node.previous;
            }
        }
        return new ULLIterator(node, index - p, index);

    }

    /**
     * 
     * pasalina elementas is specifikuotos posicijos (indekso ) siame sarasa
     * Pastumia visus toliau einancius elementus i kaire per viena ( indeksa
     * sumazina 1 ) grazina true jei elementas buvo pasalintas is saraso
     *
     * @param index indeksas elemento kuris bus pasalinamas
     * @return grazina elementa kuris buvo nurodytame indekse
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E remove(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E element = null;
        Node node;
        int p = 0;
        if (size - index > index) {
            node = firstNode;
            while (p <= index - node.numElements) {
                p += node.numElements;
                node = node.next;
            }
        } else {
            node = lastNode;
            p = size;
            while ((p -= node.numElements) > index) {
                node = node.previous;
            }
        }
        element = (E) node.elements[index - p];
        removeFromNode(node, index - p);
        return element;

    }

    /**
     * 
     * Pasalina pirmaji pasitaikusi pasirodyma nurodyto elemento is dabartinio
     * saraso , jeigu toks elementas yra sarase, jeigu elementas neegzistuoja
     * siame sarase tada sarasas yra nepakeiciamas. Formaliai , pasalinamas 
     * elementas su paciu maziausiausiu indeksu <code>i</code> kai galioja salyga
     * <code>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</code>
     * (jeigu toks elementas egzistuoja). grazina <code>true</code> jeigu sis
     * sarasas turejo specifikuota elementa ( kitaip tariant , sarasas
     * buvo pakeistas del metodo iskvietimo
     *
     * @param o elementas kuris bus istriniamas jeigu bus rastas
     * @return <code>true</code> jeigu sis sarasas turejo nurodyta elementas
     */
    @Override
    public boolean remove(Object o) {

        int index = 0;
        Node node = firstNode;
        if (o == null) {
            while (node != null) {
                for (int ptr = 0; ptr < node.numElements; ptr++) {
                    if (node.elements[ptr] == null) {
                        removeFromNode(node, ptr);
                        return true;
                    }
                }
                index += node.numElements;
                node = node.next;
            }
        } else {
            while (node != null) {
                for (int ptr = 0; ptr < node.numElements; ptr++) {
                    if ((node.elements[ptr]).equals(o)) {
                        removeFromNode(node, ptr);
                        return true;
                    }
                }
                index += node.numElements;
                node = node.next;
            }
        }
        return false;
    }

    /**
     * 
     * pasalina visus elementus is sios saraso kurie yra nurodytame sarase
     *
     * @param c kolekcija turinti elementu sarasa kuris bus istriniamas
     * @return <code>true</code> jeigu sis sarasas buvo pakeistas del 
     * metodo iskvietimo
     * @throws NullPointerException if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(List<?> c) {

        if (c == null) {
            throw new NullPointerException();
        }
        Iterator<?> it = c.iterator();
        boolean changed = false;
        while (it.hasNext()) {
            if (remove(it.next())) {
                changed = true;
            }
        }
        return changed;

    }

    /**
     * 
     * pasalina visus elementus is sios saraso kurie nera nurodytame sarase
     *
     * @param c kolekcija turinti elementu sarasa kurie nebus istriniami
     * @return <code>true</code> jeigu sis sarasas buvo pakeistas del 
     * metodo iskvietimo
     * @throws NullPointerException if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean retainAll(List<?> c) {

        if (c == null) {
            throw new NullPointerException();
        }
        boolean changed = false;
        for (Node node = firstNode; node != null; node = node.next) {
            for (int i = 0; i < node.numElements; i++) {
                if (!c.contains(node.elements[i])) {
                    removeFromNode(node, i);
                    i--;
                    changed = true;
                }
            }
        }
        return changed;

    }

    /**
     * 
     * Nurodyto indekso elemento reiskme pakeicia nurodyta reiksme
     *
     * @param index indeksas elemento kuris bus pakeistas
     * @param element elementas kuris bus patalpintas i nurodyta indeksa
     * @return elementas kuris priestai buvo nurodytame indekse
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E el = null;
        Node node;
        int p = 0;
        if (size - index > index) {
            node = firstNode;
            while (p <= index - node.numElements) {
                p += node.numElements;
                node = node.next;
            }
        } else {
            node = lastNode;
            p = size;
            while ((p -= node.numElements) > index) {
                node = node.previous;
            }
        }
        el = (E) node.elements[index - p];
        node.elements[index - p] = element;
        return el;

    }

    /**
     * grazina elementu skaiciu sarase
     *
     * @return elementu skaiciu sarase
     */
    @Override
    public int size() {

        return size;

    }

    /***
     * 
     * grazina sarasasa, kuris yra reference i si sarasa, bet juo galima
     * vaikscioti tik nuo nurodyto indekso iki nurodyto indekso
     * 
     * @param fromIndex naujo reference i si sarasa pradzios indekas
     * @param toIndex naujo reference i si sarasa pabaigos indekas
     * @return 
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {

        return new SubSet(this, fromIndex, toIndex);

    }

    /**
     * 
     * Grazina masyva turinti visus sio saraso elementus taisiklingame
     * eiliskume nuo pradzios indekso iki pabaigos indekso.
     *
     * <p>
     * The returned array will be "safe" in that no references to it are
     * maintained by this list. (In other words, this method must allocate a new
     * array). The caller is thus free to modify the returned array.
     *
     * <p>
     * This method acts as bridge between array-based and collection-based APIs.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence
     */
    @Override
    public Object[] toArray() {

        Object[] array = new Object[size];
        int p = 0;
        for (Node node = firstNode; node != null; node = node.next) {
            for (int i = 0; i < node.numElements; i++) {
                array[p] = node.elements[i];
                p++;
            }
        }
        return array;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     *
     * <p>
     * The returned array will be "safe" in that no references to it are
     * maintained by this list. (In other words, this method must allocate a new
     * array). The caller is thus free to modify the returned array.
     *
     * <p>
     * This method acts as bridge between array-based and collection-based APIs.
     *
     * @param <T>
     * @return an array containing all of the elements in this list in proper
     * sequence
     */
    @Override
    public <T> T[] toArray(T[] a) {

        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }
        Object[] result = a;
        int p = 0;
        for (Node node = firstNode; node != null; node = node.next) {
            for (int i = 0; i < node.numElements; i++) {
                result[p] = node.elements[i];
                p++;
            }
        }
        return a;

    }

    /**
     * Returns <code>true</code> if this list contains the specified element.
     * More formally, returns <code>true</code> if and only if this list
     * contains at least one element <code>e</code> such that
     * <code>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</code>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <code>true</code> if this list contains the specified element
     */
    @Override
    public boolean contains(Object o) {

        return (indexOf(o) != -1);

    }

    /**
     * Returns <code>true</code> if this list contains all of the elements of
     * the specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return <code>true</code> if this list contains all of the elements of
     * the specified collection
     * @throws NullPointerException if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(List<? extends E> c) {

        if (c == null) {
            throw new NullPointerException();
        }
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;

    }

    /**
     * Returns <code>true</code> if this list contains no elements.
     *
     * @return <code>true</code> if this list contains no elements
     */
    @Override
    public boolean isEmpty() {

        return (size == 0);

    }

    protected class Node {

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

    /**
     * Insert an element into the specified node. If the node is already full, a
     * new node will be created and inserted into the list after the specified
     * node.
     *
     * @param node
     * @param pos the position at which the element should be inserted into the <code>node.elements<code> array
     * @param element the element to be inserted
     */
    private void insertIntoNode(Node node, int pos, E element) {

        // if the node is full
        if (node.numElements == nodeCapacity) {
            // create a new node
            Node newNode = new Node();
            // move half of the elements to the new node
            int elementsToMove = nodeCapacity / 2;
            int startIndex = nodeCapacity - elementsToMove;
            int i;
            for (i = 0; i < elementsToMove; i++) {
                newNode.elements[i] = node.elements[startIndex + i];
                node.elements[startIndex + i] = null;
            }
            node.numElements -= elementsToMove;
            newNode.numElements = elementsToMove;
            // insert the new node into the list
            newNode.next = node.next;
            newNode.previous = node;
            if (node.next != null) {
                node.next.previous = newNode;
            }
            node.next = newNode;

            if (node == lastNode) {
                lastNode = newNode;
            }

            // check whether the element should be inserted into
            // the original node or into the new node
            if (pos > node.numElements) {
                node = newNode;
                pos -= node.numElements;
            }
        }
        for (int i = node.numElements; i > pos; i--) {
            node.elements[i] = node.elements[i - 1];
        }
        node.elements[pos] = element;
        node.numElements++;
        size++;
        modCount++;

    }

    /**
     * Removes an element from the specified node.
     *
     * @param node the node from which an element should be removed
     * @param ptr the index of the element to be removed within the      <code>node.elements<code> array
     */
    private void removeFromNode(Node node, int ptr) {

        node.numElements--;
        for (int i = ptr; i < node.numElements; i++) {
            node.elements[i] = node.elements[i + 1];
        }
        node.elements[node.numElements] = null;
        if (node.next != null && node.next.numElements + node.numElements <= nodeCapacity) {
            mergeWithNextNode(node);
        } else if (node.previous != null && node.previous.numElements + node.numElements <= nodeCapacity) {
            mergeWithNextNode(node.previous);
        }
        size--;
        modCount++;

    }

    /**
     * This method does merge the specified node with the next node.
     *
     * @param node the node which should be merged with the next node
     */
    private void mergeWithNextNode(Node node) {

        Node next = node.next;
        for (int i = 0; i < next.numElements; i++) {
            node.elements[node.numElements + i] = next.elements[i];
            next.elements[i] = null;
        }
        node.numElements += next.numElements;
        if (next.next != null) {
            next.next.previous = node;
        }
        node.next = next.next;
        if (next == lastNode) {
            lastNode = node;
        }

    }

    private class ULLIterator implements ListIterator<E> {

        Node currentNode;
        int pos;
        int index;

        private int expectedModCount = modCount;

        ULLIterator(Node node, int ptr, int index) {

            this.currentNode = node;
            this.pos = ptr;
            this.index = index;

        }

        @Override
        public boolean hasNext() {

            return (index < size - 1);

        }

        @Override
        public E next() {

            pos++;
            if (pos >= currentNode.numElements) {
                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                    pos = 0;
                } else {
                    throw new NoSuchElementException();
                }
            }
            index++;
            checkForModification();
            return (E) currentNode.elements[pos];

        }

        @Override
        public boolean hasPrevious() {

            return (index > 0);

        }

        @Override
        public E previous() {

            pos--;
            if (pos < 0) {
                if (currentNode.previous != null) {
                    currentNode = currentNode.previous;
                    pos = currentNode.numElements - 1;
                } else {
                    throw new NoSuchElementException();
                }
            }
            index--;
            checkForModification();
            return (E) currentNode.elements[pos];

        }

        @Override
        public int nextIndex() {

            return (index + 1);

        }

        @Override
        public int previousIndex() {

            return (index - 1);

        }

        @Override
        public void remove() {

            checkForModification();
            removeFromNode(currentNode, pos);

        }

        @Override
        public void set(E e) {

            checkForModification();
            currentNode.elements[pos] = e;

        }

        @Override
        public void add(E e) {

            checkForModification();
            insertIntoNode(currentNode, pos + 1, e);

        }

        private void checkForModification() {

            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

        }

    }

    public class SubSet<E> extends UnrolledLinkedList<E> {

        UnrolledLinkedList<E> parentList = null;
        int startPosition = 0;
        int endPosition = 0;

        public SubSet(UnrolledLinkedList<E> parentList, int startPosition, int endPosition) {
            this.parentList = parentList;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        @Override
        public int size() {
            return endPosition - startPosition;
        }

        @Override
        public boolean add(E e) {

            parentList.add(endPosition, e);
            return true;
        }

        @Override
        public void add(int index, E element) {
            parentList.add(index + startPosition, element);
        }

        @Override
        public boolean addAll(List<? extends E> c) {
            if (c == null) {
                throw new NullPointerException();
            }
            boolean changed = false;
            Iterator<? extends E> it = c.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (i >= startPosition && i <= endPosition) {
                    add(it.next());
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        @Override
        public boolean addAll(int index, List<? extends E> c) {
            if (c == null) {
                throw new NullPointerException();
            }
            boolean changed = false;
            Iterator<? extends E> it = c.iterator();
            int k = 0;
            int i = 0;
            while (it.hasNext()) {
                if (k >= startPosition && k <= endPosition) {
                    add(it.next());
                    changed = true;
                }
                k++;
            }
            return changed;
        }

        @Override
        public void clear() {
            int k = 0;
            for (Node node = parentList.firstNode; node != null; node = node.next) {
                for (int i = 0; i < node.numElements; i++) {
                    if (k >= startPosition && k <= endPosition) {
                        parentList.remove(node.elements[i]);
                    }
                    k++;
                }
            }
        }

        @Override
        public E get(int index) {

            return super.get(startPosition + index);

        }

        @Override
        public int hashCode() {

            int hashCode = 1;

            int k = 0;
            for (Node node = parentList.firstNode; node != null; node = node.next) {
                for (int i = 0; i < node.numElements; i++) {
                    if (k >= startPosition && k <= endPosition) {
                        hashCode = 31 * hashCode + (node.elements[i] == null ? 0 : node.elements[i].hashCode());
                    }
                    k++;
                }
            }
            return hashCode;

        }

        @Override
        public int indexOf(Object o) {

            int index = 0;
            Node node = parentList.firstNode;
            if (o == null) {
                while (node != null) {
                    for (int ptr = 0; ptr < node.numElements; ptr++) {
                        if (node.elements[ptr] == null && (index + ptr) >= startPosition && (index + ptr) <= endPosition) {
                            return index + ptr;
                        }
                    }
                    index += node.numElements;
                    node = node.next;
                }
            } else {
                while (node != null) {
                    for (int ptr = 0; ptr < node.numElements; ptr++) {
                        if ((node.elements[ptr].equals(o)) && (index + ptr) >= startPosition && (index + ptr) <= endPosition) {
                            return index + ptr;
                        }
                    }
                    index += node.numElements;
                    node = node.next;
                }
            }
            return -1;

        }

        @Override
        public int lastIndexOf(Object o) {

            int index = size;
            Node node = parentList.lastNode;
            if (o == null) {
                while (node != null) {
                    index -= node.numElements;
                    for (int i = node.numElements - 1; i >= 0; i--) {
                        if (node.elements[i] == null && (index + i) >= startPosition && (index + i) <= endPosition) {
                            return (index + i);
                        }
                    }
                    node = node.previous;
                }
            } else {
                while (node != null) {
                    index -= node.numElements;
                    for (int i = node.numElements - 1; i >= 0; i--) {
                        if ((node.elements[i].equals(o)) && (index + i) >= startPosition && (index + i) <= endPosition) {
                            return (index + i);
                        }
                    }
                    node = node.previous;
                }
            }
            return -1;

        }

        @Override
        public Iterator<E> iterator() {

            return listIterator(0);

        }

        @Override
        public ListIterator<E> listIterator() {

            return listIterator(0);

        }

        @Override
        public ListIterator<E> listIterator(int index) {

            index = index + startPosition;

            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }
            Node node;
            int p = 0;
            if (size - index > index) {
                node = parentList.firstNode;
                while (p <= index - node.numElements) {
                    p += node.numElements;
                    node = node.next;
                }
            } else {
                node = parentList.lastNode;
                p = size;
                while ((p -= node.numElements) > index) {
                    node = node.previous;
                }
            }
            return new ULLIterator(node, index - p, index);

        }

        @Override
        public E remove(int index) {

            index = index + startPosition;
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException();
            }
            E element = null;
            Node node;
            int p = 0;
            if (size - index > index) {
                node = parentList.firstNode;
                while (p <= index - node.numElements) {
                    p += node.numElements;
                    node = node.next;
                }
            } else {
                node = parentList.lastNode;
                p = size;
                while ((p -= node.numElements) > index) {
                    node = node.previous;
                }
            }
            element = (E) node.elements[index - p];
            parentList.removeFromNode(node, index - p);
            return element;

        }

        @Override
        public boolean remove(Object o) {

            int index = startPosition;
            Node node = parentList.firstNode;
            if (o == null) {
                while (node != null) {
                    for (int ptr = 0; ptr < node.numElements; ptr++) {
                        if (node.elements[ptr] == null && index <= endPosition) {
                            parentList.removeFromNode(node, ptr);
                            return true;
                        }
                    }
                    index += node.numElements;
                    node = node.next;
                }
            } else {
                while (node != null) {
                    for (int ptr = 0; ptr < node.numElements; ptr++) {
                        if (o.equals(node.elements[ptr]) && index <= endPosition) {
                            parentList.removeFromNode(node, ptr);
                            return true;
                        }
                    }
                    index += node.numElements;
                    node = node.next;
                }
            }
            return false;
        }

        @Override
        public boolean removeAll(List<?> c) {

            if (c == null) {
                throw new NullPointerException();
            }
            Iterator<?> it = c.iterator();
            boolean changed = false;
            while (it.hasNext()) {
                if (remove(it.next())) {
                    changed = true;
                }
            }
            return changed;

        }

        @Override
        public boolean retainAll(List<?> c) {

            if (c == null) {
                throw new NullPointerException();
            }
            boolean changed = false;
            int index = 0;

            for (Node node = parentList.firstNode; node != null; node = node.next) {
                for (int i = 0; i < node.numElements; i++) {
                    if (!c.contains(node.elements[i]) && index >= startPosition && index <= endPosition) {
                        parentList.removeFromNode(node, i);
                        i--;
                        changed = true;
                    }
                    index++;
                }
            }
            return changed;

        }

        @Override
        public E set(int index, E element) {

            index = index + startPosition;
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException();
            }
            E el = null;
            Node node;
            int p = 0;
            if (size - index > index) {
                node = parentList.firstNode;
                while (p <= index - node.numElements) {
                    p += node.numElements;
                    node = node.next;
                }
            } else {
                node = parentList.lastNode;
                p = size;
                while ((p -= node.numElements) > index) {
                    node = node.previous;
                }
            }
            el = (E) node.elements[index - p];
            node.elements[index - p] = element;
            return el;

        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {

            return new SubSet(this, fromIndex, toIndex);

        }

        @Override
        public Object[] toArray() {

            Object[] array = new Object[size];
            int p = 0;
            int index = 0;
            for (Node node = parentList.firstNode; node != null; node = node.next) {
                for (int i = 0; i < node.numElements; i++) {
                    if (index >= startPosition && index <= endPosition) {
                        array[p] = node.elements[i];
                        p++;
                    } else {
                        index++;
                    }
                }
            }
            return array;
        }

        @Override
        public <T> T[] toArray(T[] a) {

            if (a.length < size) {
                a = (T[]) java.lang.reflect.Array.newInstance(
                        a.getClass().getComponentType(), size);
            }
            Object[] result = a;
            int p = 0;
            int index = 0;
            for (Node node = parentList.firstNode; node != null; node = node.next) {
                for (int i = 0; i < node.numElements; i++) {
                    if (index >= startPosition && index <= endPosition) {
                        result[p] = node.elements[i];
                        p++;
                    } else {
                        index++;
                    }
                }
            }
            return a;

        }

        @Override
        public boolean contains(Object o) {

            return (indexOf(o) != -1);

        }

        @Override
        public boolean containsAll(List<? extends E> c) {

            if (c == null) {
                throw new NullPointerException();
            }
            Iterator<?> it = c.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;

        }

        public boolean isEmpty() {

            return (size == 0);

        }

        private class ULLIterator implements ListIterator<E> {

            Node currentNode;
            int pos;
            int index;

            private int expectedModCount = modCount;

            ULLIterator(Node node, int ptr, int index) {

                this.currentNode = node;
                this.pos = ptr;
                this.index = index;

            }

            @Override
            public boolean hasNext() {

                if (index > endPosition) {
                    return false;
                } else {
                    return (index < size - 1);
                }
            }

            @Override
            public E next() {

                pos++;
                if (pos >= currentNode.numElements) {
                    if (currentNode.next != null) {
                        currentNode = currentNode.next;
                        pos = 0;
                    } else {
                        throw new NoSuchElementException();
                    }
                }
                index++;
                checkForModification();
                return (E) currentNode.elements[pos];

            }

            @Override
            public boolean hasPrevious() {

                return (index > startPosition);

            }

            @Override
            public E previous() {

                pos--;
                if (pos < 0) {
                    if (currentNode.previous != null) {
                        currentNode = currentNode.previous;
                        pos = currentNode.numElements - 1;
                    } else {
                        throw new NoSuchElementException();
                    }
                }
                index--;
                checkForModification();
                return (E) currentNode.elements[pos];

            }

            @Override
            public int nextIndex() {

                return (index + 1);

            }

            @Override
            public int previousIndex() {

                return (index - 1);

            }

            @Override
            public void remove() {

                checkForModification();
                parentList.removeFromNode(currentNode, pos);

            }

            @Override
            public void set(E e) {

                checkForModification();
                currentNode.elements[pos] = e;

            }

            @Override
            public void add(E e) {

                checkForModification();
                parentList.insertIntoNode(currentNode, pos + 1, e);

            }

            private void checkForModification() {

                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
}
