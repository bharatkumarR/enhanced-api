package org.jthoughtlabs.enahanced.api.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The Class EventBasedList, can be used to get notifications for any additions
 * or modifications to the list
 *
 * @param <E>
 */
public class EventBasedList<E> implements List<E> {

	private List<E> list;

	private ListMediator<E> listMediator;

	/**
	 * Instantiates a new event based list.
	 *
	 * @param list
	 * @param listMediator
	 */
	public EventBasedList(List<E> list, ListMediator<E> listMediator) {
		this.list = list;
		this.listMediator = listMediator;
	}

	/**
	 * Instantiates a new event based list.
	 *
	 * @param list
	 */
	public EventBasedList(List<E> list) {
		this.list = list;
		this.listMediator = new ListMediator<>();
	}

	/**
	 * Instantiates a new event based list.
	 */
	public EventBasedList() {
		this.list = new ArrayList<>();
		this.listMediator = new ListMediator<>();
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<E> iterator() {
		return list.iterator();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once element is added this method will notify the mediator to publish the
	 * changes. Both element to be added and boolean returned by
	 * {@link Collection#add} method will be send to mediator. subscriber will get
	 * notified as mentioned by {@link ListMediator}
	 * </p>
	 */
	public boolean add(E e) {
		boolean result = list.add(e);
		getListMediator().publishForAdd(e, result);
		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once element is removed this method will notify the mediator to publish the
	 * changes. Both element to be removed and boolean returned by
	 * {@link Collection#remove} method will be send to mediator. subscriber will
	 * get notified as mentioned by {@link ListMediator}
	 * </p>
	 */
	public boolean remove(Object o) {
		boolean result = list.remove(o);
		getListMediator().publishForRemove(o, result);
		return result;
	}

	public boolean containsAll(Collection<?> c) {
		return list.contains(c);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once all elements are added to the list then this method will notify the
	 * mediator to publish the changes. Both collection of elements to be added and
	 * boolean returned by {@link Collection#addAll(Collection)} method will be send
	 * to mediator. subscriber will get notified as mentioned by
	 * {@link ListMediator}
	 * </p>
	 */
	public boolean addAll(Collection<? extends E> c) {
		boolean result = list.addAll(c);
		getListMediator().publishForAddAll(c, result);
		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once all elements are added to the list then this method will notify the
	 * mediator to publish the changes. Both collection of elements to be added and
	 * boolean returned by {@link Collection#addAll(Collection)} method will be send
	 * to mediator. subscriber will get notified as mentioned by
	 * {@link ListMediator}
	 * </p>
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean result = list.addAll(index, c);
		getListMediator().publishForAddAll(c, result);
		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once all elements are removed from the list then this method will notify the
	 * mediator to publish the changes. Both collection of elements to be removed and
	 * boolean returned by {@link Collection#removeAll(Collection)} method will be
	 * send to mediator. subscriber will get notified as mentioned by
	 * {@link ListMediator}
	 * </p>
	 */
	public boolean removeAll(Collection<?> c) {
		boolean result = list.removeAll(c);
		getListMediator().publishForRemoveAll(c, result);
		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once all elements are retained to the list then this method will notify the
	 * mediator to publish the changes. Both collection of elements retained and
	 * boolean returned by {@link Collection#retainAll(Collection)} method will be
	 * send to mediator. subscriber will get notified as mentioned by
	 * {@link ListMediator}
	 * </p>
	 */
	public boolean retainAll(Collection<?> c) {
		boolean result = list.retainAll(c);
		getListMediator().publishForRemoveAll(c, result);
		return result;
	}

	public void clear() {
		list.clear();
	}

	public E get(int index) {
		return list.get(index);
	}

	public E set(int index, E element) {
		return list.set(index, element);
	}

	/**
	 * @see EventBasedList#add(Object)
	 */
	public void add(int index, E element) {
		list.add(index, element);
		getListMediator().publishForAdd(element, true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once element is removed this method will notify the mediator to publish the
	 * changes. Both element returned by {@link List#remove(int)} method and the index
	 * specified will be send to mediator. subscriber will get notified as mentioned
	 * by {@link ListMediator}
	 * </p>
	 */
	public E remove(int index) {
		E element = list.remove(index);
		getListMediator().publishForRemoveAtIndex(index, element);
		return element;
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @return {@link EventBasedListIterator}
	 */
	public ListIterator<E> listIterator() {
		return new EventBasedListIterator<E>(list.listIterator(), this.listMediator);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @return {@link EventBasedListIterator}
	 */
	public ListIterator<E> listIterator(int index) {
		return new EventBasedListIterator<E>(list.listIterator(index), this.listMediator);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @return {@link EventBasedList}
	 */
	public List<E> subList(int fromIndex, int toIndex) {
		return new EventBasedList<>(list.subList(fromIndex, toIndex), listMediator);
	}

	/**
	 * Gets the list mediator.
	 * 
	 * @return the list mediator
	 */
	public ListMediator<E> getListMediator() {
		return listMediator;
	}

}