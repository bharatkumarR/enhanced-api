package org.jthoughtlabs.enahanced.api.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class ListMediator<E> {

	private List<BiConsumer<E, Boolean>> subscribersForAdd = new ArrayList<>();
	private List<BiConsumer<Collection<? extends E>, Boolean>> subscribersForAddAll = new ArrayList<>();

	private List<BiConsumer<Object, Boolean>> subscribersForRemove = new ArrayList<>();
	private List<BiConsumer<Collection<?>, Boolean>> subscribersForRemoveAll = new ArrayList<>();
	private List<BiConsumer<E, Integer>> subscribersForRemoveAtIndex = new ArrayList<>();

	public void registerAddListener(BiConsumer<E, Boolean> consumer) {
		subscribersForAdd.add(consumer);
	}

	public void publishForAdd(E e, boolean result) {
		subscribersForAdd.stream().forEach(l -> l.accept(e, result));
	}

	public void registerAddAllListener(BiConsumer<Collection<? extends E>, Boolean> consumer) {
		subscribersForAddAll.add(consumer);
	}

	public void publishForAddAll(Collection<? extends E> c, boolean result) {
		subscribersForAddAll.stream().forEach(l -> l.accept(c, result));
	}

	public void registerRemoveListener(BiConsumer<Object, Boolean> consumer) {
		subscribersForRemove.add(consumer);
	}

	public void publishForRemove(Object o, boolean result) {
		subscribersForRemove.stream().forEach(l -> l.accept(o, result));
	}

	public void registerRemoveAllListener(BiConsumer<Collection<?>, Boolean> consumer) {
		subscribersForRemoveAll.add(consumer);
	}

	public void publishForRemoveAll(Collection<?> c, boolean result) {
		subscribersForRemoveAll.stream().forEach(l -> l.accept(c, result));
	}

	public void registerRemoveAtIndexListener(BiConsumer<E, Integer> consumer) {
		subscribersForRemoveAtIndex.add(consumer);
	}

	public void publishForRemoveAtIndex(int index, E element) {
		subscribersForRemoveAtIndex.stream().forEach(l -> l.accept(element, index));
	}

}
