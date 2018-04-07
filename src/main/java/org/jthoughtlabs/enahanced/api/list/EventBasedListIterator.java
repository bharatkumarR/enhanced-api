package org.jthoughtlabs.enahanced.api.list;

import java.util.ListIterator;

public class EventBasedListIterator<E> implements ListIterator<E> {

	private ListIterator<E> listIterator;
	private E lastElement;
	private ListMediator<E> listMediator;

	public EventBasedListIterator(ListIterator<E> listIterator, ListMediator<E> listMediator) {
		this.listIterator = listIterator;
		this.listMediator = listMediator;
	}

	@Override
	public boolean hasNext() {
		return this.listIterator.hasNext();
	}

	@Override
	public E next() {
		this.lastElement = this.listIterator.next();
		return this.listIterator.next();
	}

	@Override
	public boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}

	@Override
	public E previous() {
		lastElement = this.listIterator.previous();
		return lastElement;
	}

	@Override
	public int nextIndex() {
		return this.listIterator.nextIndex();
	}

	@Override
	public int previousIndex() {
		return this.listIterator.previousIndex();
	}

	@Override
	public void remove() {
		this.listIterator.remove();
		this.listMediator.publishForRemove(lastElement, true);
	}

	@Override
	public void set(E e) {
		this.listIterator.set(e);
		this.listMediator.publishForRemove(lastElement, true);
		this.listMediator.publishForAdd(e, true);
	}

	@Override
	public void add(E e) {
		this.listIterator.add(e);
		this.listMediator.publishForAdd(e, true);
	}

}
