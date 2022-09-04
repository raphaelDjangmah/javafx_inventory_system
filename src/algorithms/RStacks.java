package algorithms;

import java.util.ArrayList;
	
public class RStacks<Type> {
	
	/***
	 * The stack principle is implemented here
	 * Functions that can be performed are.. Note Stacks use the LIFO principle
	 * PUSH,POP, PEEK, isEmpty -> having their usual meanings
	 * show -> will show all element in the stack
	 * size -> will show the current size of the stack
	 */
	private ArrayList<Type> data;
	private int sizeOfStack = 0;
	
	//using generic Type so we can easily add elements
	public RStacks(int sizeOfStack) {
		data = new ArrayList<Type>(sizeOfStack);
		this.sizeOfStack = sizeOfStack;
	}
	
	public boolean isFull() {
		if(data.size()>=sizeOfStack) {
			return true;
		}
		
		return false;
	}
	
	public void push(Type item) {
		if(isFull()) {
			System.out.println("The Stack is Full");
			return;
		}
		data.add(item);
	}
	
	public Type pop() {
		
		if(data.size()<1) {
			System.out.println("Cannot Pop On an Empty Stack");
			return null;
		}
		
		Type val = data.get(data.size()-1);
		data.remove(data.size()-1);
		return val;
	}
	
	public int getSize() {
		return data.size();
	}
	
	public Type peak() {
		Type val = data.get(data.size()-1);
		return val;
	}
	
	public void show() {
		for(Type t : data) {
			System.out.print(t+" ");
		}
	}
	
	public Object[] result(){
		return data.toArray();
	}
}
