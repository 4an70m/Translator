package lab2.syntaxAnalyzer;

import java.util.LinkedList;

public class Stack<T> extends LinkedList<T> {

	public void pushBack(T obj) {
		this.pushBack(obj);
	}

	public T popBack(T obj) {
		return this.pollLast();
	}

	public boolean isNull() {
		return this.isEmpty();
	}

}
