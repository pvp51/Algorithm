package assignment1;

public class QueueADT<E> implements Queue<E> {

	private int total;
    private Node front, last;

    private class Node {
        private E data;
        private Node next;
    }

    public QueueADT() { }
    
    @Override
    public E front(){
    	if (total == 0){
        	throw new java.util.NoSuchElementException();
        }
        E data = front.data;
        System.out.println("Front element of the queue: "+data);
        return data;    	
    }
    
    @Override
    public int size(){
    	//System.out.println("Size of the queue: "+total);
    	return total;
    }
    
    @Override
    public boolean isEmpty()
    {
    	return (total == 0);
    }

    @Override
    public void enqueue(E data)
    {
        Node current = last;
        last = new Node();
        last.data = data;

        if (isEmpty()) {
        	front = last;
        }
        else {
        	current.next = last;
        }
        total++;
        //System.out.println(data+ " added to the queue");
        
    }    

    @Override
    public E dequeue()
    {
        if (total == 0){
        	throw new java.util.NoSuchElementException();
        }
        E data = front.data;
        front = front.next;
        if (isEmpty()){
        	last = null;
        }
        total--;
        //System.out.println(data+ " removed from the queue");
        return data;
    }

}
