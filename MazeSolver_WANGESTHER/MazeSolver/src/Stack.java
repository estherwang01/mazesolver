/**
* Name: Esther Wang 
* Mrs. Kankelborg
* Period 1
* Project 3 Maze Solver - Stack  
* Revision History:
* 12/17: copy and pasted from the lab turned in online 
*
* Class Description: 
 * A last-in-first-out (LIFO) stack of generic items.
 *
 * @param <T> the type of item to store in the stack
 */
public class Stack<T>
{
    private T[] stack; 
    private int size; 
    
	/**
     * Initializes an empty stack.
     */
    public Stack()
    {
        stack = (T[]) new Object[1]; 
        size = 0; 
    }

    /**
     * Adds an item to the stack.
     *
     * @param newItem the item to add
     */
    public void push(T newItem)
    {
        if(newItem == null)
        {
            throw new IllegalArgumentException(); 
        }
        if(size+1 > stack.length-1)
        {
        	//doubles the stack size if the max capacity has been reached 
            T[] newStack = (T[]) new Object[stack.length*2];
            for(int i =0; i<stack.length; i++)
            {
                //while resizing, reposition the values so the beginning is at index 0
                newStack[i] = stack[i]; 
            }
            stack = newStack; 
        }
        stack[size] = newItem; 
        size++; 
    }

    /**
     * Removes and returns the item on the stack that was most recently added.
     *
     * @return the item on the stack that was most recently added
     */
    public T pop()
    {
        int counter = size; 
        if(counter ==0)
        {
           throw new IllegalStateException();  
        }
        T removed = stack[counter-1]; 
        stack[counter-1] = null; 
        size--; 
        if(size<(stack.length)/4)
        {
        	//halves the size if 1/4 capacity is reached 
            T[] newStack = (T[]) new Object[stack.length/2];
            for(int i =0; i<newStack.length; i++)
            {
                newStack[i] = stack[i]; 
            }
            stack = newStack; 
        }
        return removed; 
    }

    /**
     * Returns the item most recently added to the stack.
     *
     * @return the item most recently added to the stack
     */
    public T peek()
    {
        int counter = size; 
        //cannot peek at an empty stack 
        if(counter ==0)
        {
           throw new IllegalStateException();  
        }
        return stack[counter-1];  
    }

    /**
     * Returns whether the stack is empty.
     *
     * @return whether the stack is empty
     */
    public boolean isEmpty()
    {
        return size == 0; 
    }

    /**
     * Returns the number of items in the stack.
     *
     * @return the number of items in the stack
     */
    public int size()
    {
        return size;
    }
}
