package org.jthoughtlabs.enahanced.api.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * The Class ListMediator.
 *
 * @param <E>
 *          the element type
 */
public class ListMediator<E> {

	private List<BiConsumer<E, Boolean>> subscribersForAdd = new ArrayList<>();

	private List<BiConsumer<Collection<? extends E>, Boolean>> subscribersForAddAll = new ArrayList<>();

	private List<BiConsumer<Object, Boolean>> subscribersForRemove = new ArrayList<>();

	private List<BiConsumer<Collection<?>, Boolean>> subscribersForRemoveAll = new ArrayList<>();

	private List<BiConsumer<E, Integer>> subscribersForRemoveAtIndex = new ArrayList<>();

	/**
	 * Register add listener.
	 *
	 * @param consumer
	 *          the consumer
	 */
	public void registerAddListener(BiConsumer<E, Boolean> consumer) {
		subscribersForAdd.add(consumer);
	}

	/**
	 * Publish for add.
	 *
	 * @param e
	 *          the e
	 * @param result
	 *          the result
	 */
	public void publishForAdd(E e, boolean result) {
		subscribersForAdd.stream().forEach(l -> l.accept(e, result));
	}

	/**
	 * Register add all listener.
	 *
	 * @param consumer
	 *          the consumer
	 */
	public void registerAddAllListener(BiConsumer<Collection<? extends E>, Boolean> consumer) {
		subscribersForAddAll.add(consumer);
	}

	/**
	 * Publish for add all.
	 *
	 * @param c
	 *          the c
	 * @param result
	 *          the result
	 */
	public void publishForAddAll(Collection<? extends E> c, boolean result) {
		subscribersForAddAll.stream().forEach(l -> l.accept(c, result));
	}

	/**
	 * Register remove listener.
	 *
	 * @param consumer
	 *          the consumer
	 */
	public void registerRemoveListener(BiConsumer<Object, Boolean> consumer) {
		subscribersForRemove.add(consumer);
	}

	/**
	 * Publish for remove.
	 *
	 * @param o
	 *          the o
	 * @param result
	 *          the result
	 */
	public void publishForRemove(Object o, boolean result) {
		subscribersForRemove.stream().forEach(l -> l.accept(o, result));
	}

	/**
	 * Register remove all listener.
	 *
	 * @param consumer
	 *          the consumer
	 */
	public void registerRemoveAllListener(BiConsumer<Collection<?>, Boolean> consumer) {
		subscribersForRemoveAll.add(consumer);
	}

	/**
	 * Publish for remove all.
	 *
	 * @param c
	 *          the c
	 * @param result
	 *          the result
	 */
	public void publishForRemoveAll(Collection<?> c, boolean result) {
		subscribersForRemoveAll.stream().forEach(l -> l.accept(c, result));
	}

	/**
	 * Register remove at index listener.
	 *
	 * @param consumer
	 *          the consumer
	 */
	public void registerRemoveAtIndexListener(BiConsumer<E, Integer> consumer) {
		subscribersForRemoveAtIndex.add(consumer);
	}

	/**
	 * Publish for remove at index.
	 *
	 * @param index
	 *          the index
	 * @param element
	 *          the element
	 */
	public void publishForRemoveAtIndex(int index, E element) {
		subscribersForRemoveAtIndex.stream().forEach(l -> l.accept(element, index));
	}

}
