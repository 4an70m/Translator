package analyzer.syntax.automat;

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

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = this.size() - 1; i >= 0; i--) {
			sb.append(this.get(i)).append(" ");
		}
		return sb.toString();
	}
}