package org.coding.thoughts.enahanced.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EventBasedList<E> implements List<E> {

	private List<E> list;

	private ListMediator<E> listMediator;

	public EventBasedList(List<E> list, ListMediator<E> listMediator) {
		this.list = list;
		this.listMediator = listMediator;
	}

	public EventBasedList(List<E> list) {
		this.list = list;
		this.listMediator = new ListMediator<>();
	}

	public EventBasedList() {
		this.list = new ArrayList<>();
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

	public boolean add(E e) {
		boolean result = list.add(e);
		getListMediator().publishForAdd(e, result);
		return result;
	}

	public boolean remove(Object o) {
		boolean result = list.remove(o);
		getListMediator().publishForRemove(o, result);
		return result;
	}

	public boolean containsAll(Collection<?> c) {
		return list.contains(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean result = list.addAll(c);
		getListMediator().publishForAddAll(c, result);
		return result;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		boolean result = list.addAll(index, c);
		getListMediator().publishForAddAll(c, result);
		return result;
	}

	public boolean removeAll(Collection<?> c) {
		boolean result = list.removeAll(c);
		getListMediator().publishForRemoveAll(c, result);
		return result;
	}

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

	public void add(int index, E element) {
		list.add(index, element);
		getListMediator().publishForAdd(element, true);
	}

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

	public ListIterator<E> listIterator() {
		return new EventBasedListIterator<E>(list.listIterator(), this.listMediator);
	}

	public ListIterator<E> listIterator(int index) {
		return new EventBasedListIterator<E>(list.listIterator(index), this.listMediator);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return new EventBasedList<>(list.subList(fromIndex, toIndex), listMediator);
	}

	public ListMediator<E> getListMediator() {
		return listMediator;
	}

	public void setListMediator(ListMediator<E> listMediator) {
		this.listMediator = listMediator;
	}

}