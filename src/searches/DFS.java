package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import application.Maze;

public class DFS extends SearchAlgorithm{
	
	public DFS(Maze mazeBlocks, Point startPoint, Point goalPoint){
		//maze = mazeBlocks;
		//goal = goalPoint;
		//current = startPoint;
		//maze.markPath(current);
		super(mazeBlocks, startPoint, goalPoint);
		// The data structure for DFS is a stack.
		Stack<Point> stack =new Stack<>();
		stack.push(startPoint);
		data = stack;
	}
	
	/*
	 * Depth-First Search Algorithm.
	 */
	public boolean step(){
		// Don't keep computing after goal is reached or determined impossible.
		if(searchOver){
			return searchResult;
		}
		// Find possible next steps
		Collection<Point> neighbors = getNeighbors();
		// Choose one to be a part of the path
		Point next = chooseNeighbor(neighbors);
		// mark the next step
		if(next!=null){
			maze.markPath(next);
			recordLink(next);
		}
		// if no next step is found
		else{	
			maze.markVisited(current);
			Stack<Point> stack = (Stack<Point>)data;
			stack.pop();
		}
		resetCurrent();
		checkSearchOver();
		return searchResult;	
	}
	
	/*
	 * This method defines the neighbor that gets chosen as the newest "fringe" member
	 * 
	 * It chooses the first point it finds that is empty.
	 */
	private Point chooseNeighbor(Collection<Point> neighbors){
		for(Point p: neighbors){
			if(maze.get(p)==Maze.EMPTY){
				return p;
			}
		}
		return null;
	}
	
	// When a new node is chosen, push it on the stack
	private void recordLink(Point next){
		Stack<Point> stack = (Stack<Point>)data;
		// FIXME: add try/catch for ClassCastException
		stack.push(next);
	}
	
	/*
	 * Get the next fringe point to consider.
	 * 
	 * This implementation resets the "current" instance variable 
	 * to be the next one on the fringe data structure.
	 */
	protected void resetCurrent(){
		Stack<Point> stack = (Stack<Point>)data;
		current = stack.peek();
	}
	
}
