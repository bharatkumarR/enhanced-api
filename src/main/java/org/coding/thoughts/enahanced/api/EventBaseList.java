package org.coding.thoughts.enahanced.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiConsumer;

public class EventBaseList<E> implements List<E> {

	private List<E> list;
	private List<BiConsumer<E, Boolean>> subscribersForAdd = new ArrayList<>();
	private List<BiConsumer<Collection <? extends E>, Boolean>> subscribersForAddAll = new ArrayList<>();
	private List<BiConsumer<Object, Boolean>> subscribersForRemove = new ArrayList<>();
	private List<BiConsumer<Collection <?>, Boolean>> subscribersForRemoveAll = new ArrayList<>();
	private List<BiConsumer<E, Integer>> subscribersForRemoveAtIndex = new ArrayList<>();

	public EventBaseList(List<E> list) {
		this.list = list;
	}
	
	public EventBaseList() {
		this.list = new ArrayList<>();
	}
	
	public void registerAddListener(BiConsumer<E, Boolean> consumer) {
		subscribersForAdd.add(consumer);
	}
	
	public void registerAddAllListener(BiConsumer<Collection <? extends E>, Boolean> consumer) {
		subscribersForAddAll.add(consumer);
	}
	
	public void registerRemoveListener(BiConsumer<Object, Boolean> consumer) {
		subscribersForRemove.add(consumer);
	}
	
	public void registerRemoveAllListener(BiConsumer<Collection <?>, Boolean> consumer) {
		subscribersForRemoveAll.add(consumer);
	}
	
	public void registerRemoveAtIndexListener(BiConsumer<E, Integer> consumer) {
		subscribersForRemoveAtIndex.add(consumer);
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
		subscribersForAdd.stream().forEach(l -> l.accept(e, result));
		return result;
	}

	public boolean remove(Object o) {
		boolean result = list.remove(o);
		subscribersForRemove.stream().forEach(l -> l.accept(o, result));
		return result;
	}

	public boolean containsAll(Collection<?> c) {
		return list.contains(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean result = list.addAll(c);
		subscribersForAddAll.stream().forEach(l -> l.accept(c, result));
		return result;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		boolean result = list.addAll(index,c);
		subscribersForAddAll.stream().forEach(l -> l.accept(c, result));
		return result;
	}

	public boolean removeAll(Collection<?> c) {
		boolean result = list.removeAll(c);
		subscribersForRemoveAll.stream().forEach(l -> l.accept(c, result));
		return result;
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
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
		list.add(index,element);
	}

	public E remove(int index) {
		E element = list.remove(index);
		subscribersForRemoveAtIndex.stream().forEach(l -> l.accept(element, index));
		return element;
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}