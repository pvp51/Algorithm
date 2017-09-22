package assignment1;

public interface Queue<E> {
	
	    public void enqueue(E ele);
	    public E dequeue();
	    public boolean isEmpty();
	    public int size();
	    public E front();

}
