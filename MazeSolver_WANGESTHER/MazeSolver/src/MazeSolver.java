/**
* Name: Esther Wang 
* Mrs. Kankelborg
* Period 1
* Project 3 Maze Solver - MazeSolver
* Revision History: 
* 1/4, 10:30 pm: wrote solve() method and outlined helper methods
* 1/4: 11 pm: wrote helper methods and made necessary revisions to make a method to 
* 			backtrack the final path 
* 1/4: 11:45 pm: dealt with some errors involving array length and null objects 
* 1/6: documentation
*  
* Class Description: Takes a given maze and uses a breadth first search to find a
* path between the start and finish cell 
*/
/**
 * Solves mazes. Please refer to the specification for instructions on how to solve mazes.
 */
public class MazeSolver
{
    /**
     * Provides a solution for a given maze, if possible. A solution is a path from the start cell
     * to the finish cell (inclusive). If there is no solution to the maze then returns the static
     * instance {@link Path#NO_PATH}. If the maze is perfect then there must be only one solution.
     *
     * @param maze the maze to solve
     * @return a solution for the maze or {@link Path#NO_PATH} if there is no solution
     */
    public Path solve(Maze maze)
    {
    	Queue<Cell> search = new Queue(); 
    	//makes 2 arrays to store all visited cells without removing any
    	//and another array which stores the cell used to access each cell (the "previous" cell in a path) 
    	Cell[] search2 = new Cell[maze.size() * maze.size()]; 
    	Cell[] origin = new Cell[maze.size() * maze.size()];
    	//variables to store the current index of search2 and origin 
    	int oi = 0; 
    	int s2i = 0; 
    	search.enqueue(maze.getStart());
    	search2[s2i] = maze.getStart();
    	origin[oi] = new Cell (-1, -1);
    	//explore the neighbors of all unprocessed cells in the stack, removing them as you go 
    	while(!search.isEmpty())
    	{
    		Cell current = search.dequeue(); 
    		maze.visit(current.getX(), current.getY());
    		if(current.equals(maze.getEnd()))
    		{
    			return constructPath(search2, origin, maze); 
    		}
    		int n = enqueueNeighbors(search, search2, origin, current, maze, s2i, oi); 
    		s2i += n; 
    		oi += n; 
    	}
    	return Path.NO_PATH; 
    }

    /**
     * Given an array of all the cells visited in the search, a parallel array of origin cells, and the current maze,
     * a path is constructed between the start and end cell 
     * 
     * @param search, every visited cell in the search process, origin a parallel array which stores the cell 
     * used to access each cell respectively in search 
     * @return the finished path 
     * */
    public static Path constructPath(Cell[] search, Cell[] origin, Maze maze)
    {
    	Path fin = new Path(); 
    	Stack<Cell> path = new Stack(); 
    	//this stack will store the path cells in reverse order 
    	path.push(maze.getEnd());
    	//find the index of the current cell 
    	int index = search(search, maze.getEnd());
    	//its previous or origin cell will be at the same index in the origin array 
    	Cell prev = origin[index]; 
    	//repeats that process of getting index, then cell in origin to trace back path 
    	while(index >0 && prev!=null)
    	{
    		path.push(prev);
    		index = search(search, prev); 
    		if(index == -1)
    			break; 
    		prev = origin[index]; 
    	}
    	//makes sure the start cell is included 
    	if(path.peek() != maze.getStart())
    	{
    		path.push(maze.getStart());
    	}
    	//add each value in the stack into the final path to retun 
    	while(!path.isEmpty())
    	{
    		fin.addLast(path.pop());
    	}
    	return fin; 
    	
    }
    
    /**
     * Helper method which linearly searches an array until a given element is found 
     * 
     * @param array arr and Cell cell (target cell) 
     * @return the index of the target cell in array. -1 if not found. 
     * */
    public static int search(Cell[] arr, Cell cell)
    {
    	for(int i =0; i<arr.length; i++)
    	{
    		if(arr[i] != null && arr[i].equals(cell))
    			return i; 
    	}
    	return -1; 
    }
    
    /**
     * Given a maze and a current cell, enqueues all neighbors of that cell that can be accessed and have not been visited yet
     * 
     * @param search: the queue which stores cells to be explored; search2: all cells visited with no removals; 
     * origin: the array which stores the access cells for cells in search2; current: the current cell; 
     * maze: the maze you are working with; s: the current index of search2; o: the current index of origin 
     * @return the number of neighbors that were enqueued 
     * */
    public static int enqueueNeighbors(Queue<Cell> search, Cell[] search2, Cell[] origin, Cell current, 
    		Maze maze, int s, int o)
    {
    	int x = current.getX(); 
    	int y = current.getY(); 
    	int n = 0; 	
    	//check right
    	//qualifications to meet (for each of the following if statements too): does not go out of bounds, 
    	//has no wall between the current and neighbor cell, 
    	//neighbor cell has not been visited 
    	if(maze.size()-1 > x && maze.isOpen(x, y, Direction.RIGHT) && 
    			!maze.isVisited(x+1, y)) {
    		search.enqueue(new Cell(x+1,y));
    		search2[s] = new Cell(x+1,y);
    		origin[o] = current;
    		//indexes are incremented. n represents the number of neighbors that have been enqueued 
    		s++; 
    		o++;
    		n++;}
    	//check left
    	if(x >0 && maze.isOpen(x, y, Direction.LEFT) && 
    			!maze.isVisited(x-1, y)) {
    		search.enqueue(new Cell(x-1,y));
    		search2[s] = new Cell(x-1,y);
    		origin[o] = current;
    		s++; 
    		o++;
    		n++;}
    	//check up
    	if(maze.size()-1 > y && maze.isOpen(x, y, Direction.UP) && 
    			!maze.isVisited(x,y+1)) {
    		search.enqueue(new Cell(x,y+1));
    		search2[s] = new Cell(x,y+1);
    		origin[o] = current;
    		s++; 
    		o++;
    		n++;}
    	//check down 
    	if(y>0 && maze.isOpen(x, y, Direction.DOWN) && 
    			!maze.isVisited(x,y-1)) {
    		search.enqueue(new Cell(x,y-1));
    		search2[s] = new Cell(x,y-1);
    		origin[o] = current;
    		s++; 
    		o++;
    		n++;}
    	return n; 
    }
    
    /**
     * Creates, solves, and draws a sample maze. Try solving mazes with different sizes!
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
        // First, generate a new maze.
        int size = 40; // Setting above 200 is not recommended!
        MazeGenerator generator = new MazeGenerator();
        Maze maze = generator.generate(size);
        maze.freeze();

        // Next, solve it!
        MazeSolver solver = new MazeSolver();
        maze.resetVisited();
        Path solutionPath = solver.solve(maze);
        maze.setSolution(solutionPath);

        // This is so we can see which cells were explored and in what order.
        maze.setDrawVisited(true);

        maze.draw();
    }
}
