package searches;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import application.Maze;

public class RandomWalk extends SearchAlgorithm{
	public final double EXPLORE_BIAS = .999;
	private Point next;
	private Random rand;
	
	public RandomWalk(Maze mazeBlocks, Point startPoint, Point goalPoint){
		//maze = mazeBlocks;
		//goal = goalPoint;
		//current = startPoint;
		//maze.markPath(current);
		super(mazeBlocks, startPoint, goalPoint);
		next = startPoint;
		rand = new Random();
	}
	
	/*
	 * Algorithm for a Random Walk: Randomly choose a neighbor to go to
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
		}
		resetCurrent();
		checkSearchOver();
		return searchResult;	
	}
	
	
	/*
	 * Choose a random neighbor out of all the non-wall neighbors. 
	 * To make the algorithm prefer going to unexplored areas, make the EXPLORE_BIAS higher.
	 */
	private Point chooseNeighbor(Collection<Point> neighbors){
		List<Point> empties = new ArrayList<>();
		List<Point> possibles = new ArrayList<>();
		for(Point p: neighbors){
			if(maze.get(p)==Maze.EMPTY){
				empties.add(p);
			}
			if(maze.get(p) != Maze.WALL){	// includes empties - all candidates
				possibles.add(p);
			}
		}
		if((rand.nextDouble()<EXPLORE_BIAS && !empties.isEmpty())) {
			int randIndex = rand.nextInt(empties.size());
			return empties.get(randIndex);
		}
		if(!possibles.isEmpty()){
			int randIndex = rand.nextInt(possibles.size());
			return possibles.get(randIndex);
		}
		return null;
	}
	
	private void recordLink(Point next){	
		this.next = next;
		maze.markVisited(current);
	}
	
	private void resetCurrent(){
		current = next;
	}
	
}
