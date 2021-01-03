/**
* Name: Esther Wang 
* Mrs. Kankelborg
* Period 1
* Project 3 Maze Solver - Queue  
* Revision History:
* 12/17: copy and pasted from the lab turned in online 
* 
 * A first-in-first-out (FIFO) queue of generic items.
 *
 * @param <T> the type of item to store in the queue
 */
public class Queue<T>
{
    private T[] q; 
    private int size; 
    private int front; 
    private int back; 
    
	/**
     * Initializes an empty queue.
     */
    public Queue()
    {
        q = (T[]) new Object[1]; 
        size = 0; 
        front = 0; 
        back = 0; 
    }

    /**
     * Adds an item to the queue.
     *
     * @param newItem the item to add
     */
    public void enqueue(T newItem)
    {
    	if(newItem == null)
        {
            throw new IllegalArgumentException(); 
        }
        if(size+1 >= q.length)
        {
        	//doubles size if max capacity has been reached 
            T[] nq = (T[]) new Object[q.length*2]; 
            int beg = front; 
            int counter = 0; 
            while(beg != back && counter<nq.length)
            {
                if(beg >= q.length)
                {
                    beg = 0; 
                }
                nq[counter] = q[beg]; 
                counter++; 
                beg++; 
            }
            q = nq; 
            front = 0; 
            back = size; 
        }

        if(back<q.length)
        {
            q[back] = newItem; 
        }
        else
        {
            int index = back-q.length; 
            q[index] = newItem; 
        }
        back++; 
        if(back==q.length)
        {
            back = 0; 
        }
        size++; 
    
    }

    /**
     * Removes and returns the item on the queue that was least recently added.
     *
     * @return the item on the queue that was least recently added
     */
    public T dequeue()
    {
    	if(size ==0)
        {
            throw new IllegalStateException(); 
        }
        if(size-1 < q.length/4)
        {
        	//halves the array if quarter capacity is reached 
            T[] nq = (T[]) new Object[q.length/2]; 
            int beg = front; 
            int counter = 0; 
            while(beg != back && counter<nq.length)
            {
                if(beg >= q.length)
                {
                    beg = 0; 
                }
                //while resizing, reposition the values so the beginning is at index 0
                nq[counter] = q[beg]; 
                counter++; 
                beg++; 
            }
            q = nq;
            front = 0; 
            back = size; 
        }

        T removed = q[front]; 
        q[front] = null;  
        front++; 
        if(front >= q.length)
        {
            front = 0; 
        }
        size--; 
        return removed; 
    }

    /**
     * Returns the item least recently added to the queue.
     *
     * @return the item least recently added to the queue
     */
    public T peek()
    {
    	//cannot peek at an empty queue 
        if(size ==0)
        {
            throw new IllegalStateException(); 
        }
        return q[front]; 
    }

    /**
     * Returns whether the queue is empty.
     *
     * @return whether the queue is empty
     */
    public boolean isEmpty()
    {
    	return size ==0; 
    }

    /**
     * Returns the number of items in the queue.
     *
     * @return the number of items in the queue
     */
    public int size()
    {
        return size; 
    }
}
