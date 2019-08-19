package com.sunxb.集合;


import com.mj.list.LinkedList;
import com.mj.list.List;

/*
 * set 可以直接使用链表list来实现
 * */

public class ListSet<E> implements Set<E>{

	private List<E> list = new LinkedList<>();
	
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(E element) {
		if (element == null) return false;
		return list.contains(element);
	}

	@Override
	public void add(E element) {
		int index = list.indexOf(element);
		if (index != List.ELEMENT_NOT_FOUND) {
			list.set(index, element);
		}
		else {
			list.add(element);
		}
	}

	@Override
	public void remove(E element) {
		list.remove(list.indexOf(element));		
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		if (visitor == null) return;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (visitor.visit(list.get(i))) return;
		}
	}
	
}
