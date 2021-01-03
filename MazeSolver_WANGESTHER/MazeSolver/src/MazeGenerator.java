/**
 * Creates new mazes. Please refer to the spec for instructions on how to generate mazes.
 */
/**
* Name: Esther Wang 
* Mrs. Kankelborg
* Period 1
* Project 3 Maze Solver - MazeGenerator 
* Revision History:
* 12/17: started writing generate() 
* 1/4 9:30: finished writing generate() and outlined necessary helper methods
* 1/4 10:00: finished helper methods, mainly unvisited() 
*
* Class Description: Takes a size and generates a maze of that size using depth
* first search algorithm 
*/
public class MazeGenerator
{
    /**
     * Randomly generates a perfect maze of {@param size}.
     *
     * @param size the size of the maze to generate
     * @return the generated maze
     */
    public Maze generate(int size)
    {
    	Maze newMaze = new Maze(size); 
    	Stack<Cell> path = new Stack(); 
    	path.push(new Cell(0,0));
    	while(!path.isEmpty())
    	{
    		newMaze.visit(path.peek().getX(), path.peek().getY());  
    		Cell current = path.pop();
    		Cell newCell = unvisited(current, newMaze); 
    		//cannot remove cells if the new cell was null or the path has been emptied 
    		while(newCell == null && !path.isEmpty())
    		{
    				current = path.pop();
    				newCell = unvisited(current, newMaze);
    		}
    		//cannot add new cell if null 
    		if(newCell!=null)
    		{
	    		path.push(current);
	    		path.push(newCell);
	    		newMaze.visit(newCell.getX(), newCell.getY()); 
    		}    			
    	}
    	Cell start = randomCell(newMaze); 
    	Cell end = randomCell(newMaze); 
    	//loops until an end cell that is unique from the start cell is chosen 
    	while(end.equals(start))
    	{
    		end = randomCell(newMaze); 
    	}
    	newMaze.setStart(start.getX(), start.getY());
    	newMaze.setEnd(end.getX(), end.getY());
    	return newMaze; 
    }
    
    /**
     * Randomly picks a cell in {@param maze} and returns it 
     * 
     * @param the given maze
     * */
    private static Cell randomCell(Maze maze)
    {
    	int randomx = (int)(Math.random()*maze.size());  
    	int randomy = (int)(Math.random()*maze.size());  
    	return new Cell(randomx, randomy); 
    }

    /**
     * Takes a maze {@param maze} and a current cell {@param current} 
     * Randomly picks an unvisited neighbor of {@param current} 
     * using the helper methods which access cells in four directions of
     * a given cell if possible  
     * 
     * @param current cell, current maze
     * @return the random unvisited neighbor cell to visit next 
     * */
    private static Cell unvisited(Cell current, Maze maze)
    {

    	int currentX = current.getX(); 
    	int currentY = current.getY(); 
    	int random = (int)(Math.random()*4); 
    	Cell newCell = null; 
    	//boolean values which indicate if a direction has not been visited 
    	boolean U = true; 
    	boolean D = true; 
    	boolean L = true; 
    	boolean R = true; 
    	
    	//while a neighbor has not been visited and all 4 directions have not bee tried: 
    	//(as soon as one direction has worked, stop as you only need to access 1 direction)
    	//(similarly if all 4 have been tried and none work, a null value will be returned) 
    	while(newCell == null && (U || D || L || R))
    	{
    		if(random == 0) {
    			newCell = visitUP(currentX, currentY, maze); 
    			U = false; 
    		}
    		if(random==1) {
    			newCell = visitDOWN(currentX, currentY, maze);
    			D = false; 
    		}
    		if(random==2) {
    			newCell = visitRIGHT(currentX, currentY, maze);
    			R = false; 
    		}
    		if(random==3) {
    			newCell = visitLEFT(currentX, currentY, maze);
    			L = false; 
    		}		
    		random = (int)(Math.random()*4); 
    	}
    	return newCell; 
    	
    }
    
    /**
     * Visits the cell above the current cell if possible and removes the wall 
     * between the current cell and that one 
     * 
     * @param x and y coordinates of the current cell, current maze
     * @return the cell above the current cell  
     * */
    private static Cell visitUP(int currentX, int currentY, Maze maze)
    {
    	if(maze.size()-1 >currentY && !maze.isVisited(currentX, currentY+1))
    	{
    		maze.removeWall(currentX, currentY, Direction.UP); 
    		Cell newCell = new Cell(currentX, currentY+1); 
    		return newCell; 
    	}
    	return null; 
    }
    
    /**
     * Visits the cell below the current cell if possible and removes the wall 
     * between the current cell and that one 
     * 
     * @param x and y coordinates of the current cell, current maze
     * @return the cell below the current cell  
     * */
    private static Cell visitDOWN(int currentX, int currentY, Maze maze)
    {
    	if(currentY > 0 && !maze.isVisited(currentX, currentY-1))
    	{
    		maze.removeWall(currentX, currentY, Direction.DOWN); 
    		Cell newCell = new Cell(currentX, currentY-1); 
    		return newCell; 
    	}
    	return null; 
    }
    
    /**
     * Visits the cell to the right of the current cell if possible and 
     * removes the wall between the current cell and that one 
     * 
     * @param x and y coordinates of the current cell, current maze
     * @return the cell to the right of the current cell  
     * */
    private static Cell visitRIGHT(int currentX, int currentY, Maze maze)
    {
    	if(maze.size()-1 >currentX && !maze.isVisited(currentX+1, currentY))
    	{
    		maze.removeWall(currentX, currentY, Direction.RIGHT); 
    		Cell newCell = new Cell(currentX+1, currentY); 
    		return newCell; 
    	}
    	return null; 
    }
    
    /**
     * Visits the cell to the left of the current cell if possible and 
     * removes the wall between the current cell and that one 
     * 
     * @param x and y coordinates of the current cell, current maze
     * @return the cell to the left of the current cell  
     * */
    private static Cell visitLEFT(int currentX, int currentY, Maze maze)
    {
    	if(currentX>0 && !maze.isVisited(currentX-1, currentY))
    	{
    		maze.removeWall(currentX, currentY, Direction.LEFT); 
    		Cell newCell = new Cell(currentX-1, currentY); 
    		return newCell; 
    	}
    	return null; 
    }
   
    /**
     * Creates and draws a sample maze. Try generating mazes with different sizes!
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
        StdRandom.setSeed(34);
        int size = 80; // Setting above 200 is not recommended!
        MazeGenerator generator = new MazeGenerator();
        Maze maze = generator.generate(size);
        maze.draw();
    }
}
