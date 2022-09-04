package algorithms;

import java.util.ArrayList;

public class RQueues<Type> {

	/***
	 * The stack principle is implemented here Functions that can be performed are..
	 * Note Stacks use the LIFO principle PUSH,POP, PEEK, isEmpty -> having their
	 * usual meanings show -> will show all element in the stack size -> will show
	 * the current size of the stack
	 */

//	private int pointer  = 0;
	private ArrayList<Type> data;

	// using generic Type so we can easily add elements
	public RQueues(int sizeOfStack) {
		data = new ArrayList<Type>(sizeOfStack);
	}

	public void enqueue(Type item) {
		data.add(item);
	}
	
	public void enqueueAll(Type item[]) {
		for(Type i:item) {
			data.add(i);
		}
	}

	public Type dequeue() {
		Type val = data.get(0);
		data.remove(0);
		return val;
	}
	
	public void dequeueAll() {
		data.removeAll(data);
	}
	
	public Type peek() {
		Type val = data.get(0);
		return val;
	}

	public int getSize() {
		return data.size();
	}

	public void show() {
		for (Type t : data) {
			System.out.print(t + " ");
		}
	}
	
	public Object[] result(){
		return data.toArray();
	}
}
