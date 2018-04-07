package org.jthoughtlabs.enahanced.api.list;

import java.util.ListIterator;

/**
 * The Class EventBasedListIterator.
 *
 * @param <E>
 *          the element type
 */
public class EventBasedListIterator<E> implements ListIterator<E> {

	/** The list iterator. */
	private ListIterator<E> listIterator;

	/** The last element. */
	private E lastElement;

	/** The list mediator. */
	private ListMediator<E> listMediator;

	/**
	 * Instantiates a new event based list iterator.
	 *
	 * @param listIterator
	 *          the list iterator
	 * @param listMediator
	 *          the list mediator
	 */
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

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once element is removed this method will notify the mediator to publish the
	 * changes. Element to be removed & boolean value of true will be send to
	 * mediator. subscriber will get notified as mentioned by
	 * {@link ListMediator#publishForRemove(Object, boolean)}
	 * </p>
	 */
	@Override
	public void remove() {
		this.listIterator.remove();
		this.listMediator.publishForRemove(lastElement, true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once last element was replaced then  this method will
	 * notify the mediator to publish the changes. Replaced element will be notified
	 * using {@link ListMediator#publishForRemove(Object, boolean)} & Element was
	 * replaced by will be notified as mentioned by
	 * {@link ListMediator#publishForAdd(Object, boolean)}
	 * 
	 * </p>
	 */
	@Override
	public void set(E e) {
		this.listIterator.set(e);
		this.listMediator.publishForRemove(lastElement, true);
		this.listMediator.publishForAdd(e, true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * <P>
	 * Once element is added this method will notify the mediator to publish the
	 * changes. Both element to be added & boolean with default value with true will
	 * be send to mediator. subscriber will get notified as mentioned by
	 * {@link ListMediator#publishForAdd(Object, boolean)}
	 * </p>
	 */
	@Override
	public void add(E e) {
		this.listIterator.add(e);
		this.listMediator.publishForAdd(e, true);
	}

}
