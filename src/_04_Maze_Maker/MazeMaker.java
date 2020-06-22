package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		int row = randGen.nextInt(maze.getWidth());
		int column = randGen.nextInt(maze.getHeight());

		// 5. call selectNextPath method with the randomly selected cell
		Cell randomCell = maze.getCell(row, column);
		getStartAndExit();
		selectNextPath(randomCell);
		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. Get an ArrayList of unvisited neighbors using the current cell and the
		// method below
		ArrayList<Cell> unvisitedNeighbors = new ArrayList<Cell>();
		unvisitedNeighbors = getUnvisitedNeighbors(currentCell);
		// C. if has unvisited neighbors,
		if (unvisitedNeighbors.size() > 0) {
			// C1. select one at random.
			Cell randomNeighbor = unvisitedNeighbors.get(randGen.nextInt(unvisitedNeighbors.size()));
			// C2. push it to the stack
			uncheckedCells.push(randomNeighbor);
			// C3. remove the wall between the two cells
			removeWalls(currentCell, randomNeighbor);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = randomNeighbor;
			//randomNeighbor.setBeenVisited(true);
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		// D. if all neighbors are visited
		} else {
			// D1. if the stack is not empty
			if (!uncheckedCells.isEmpty()) {
				// D1a. pop a cell from the stack // D1b. make that the current cell
				currentCell = uncheckedCells.pop();
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() == c2.getX()) {
			if (c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			} else {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
		} else {
			if (c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			} else {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		int x = c.getX();
		int y = c.getY();
		ArrayList<Cell> unvisitedNeighbors = new ArrayList<Cell>();
		
		if(x > 0 && !maze.getCell(x-1, y).hasBeenVisited()) {
				unvisitedNeighbors.add(maze.getCell(x-1, y));
		}
		if(x < width - 1 && !maze.getCell(x+1, y).hasBeenVisited()) {
				unvisitedNeighbors.add(maze.getCell(x+1, y));
		}
		if(y > 0 && !maze.getCell(x, y-1).hasBeenVisited()) {
				unvisitedNeighbors.add(maze.getCell(x, y-1));
		}
		if(y < height - 1 && !maze.getCell(x, y+1).hasBeenVisited()) {
				unvisitedNeighbors.add(maze.getCell(x, y+1));
		}
		return unvisitedNeighbors;
	}
	
	private static void getStartAndExit() {
		int choice = randGen.nextInt(2);
		if (choice == 1) {
			int cellRow = randGen.nextInt(height);
			maze.getCell(width-1, cellRow).setEastWall(false);
			cellRow = randGen.nextInt(height);
			maze.getCell(0, cellRow).setWestWall(false);
		} else {
			int cellHeight = randGen.nextInt(width);
			maze.getCell(cellHeight, height-1).setSouthWall(false);
			cellHeight = randGen.nextInt(width);
			maze.getCell(cellHeight, 0).setNorthWall(false);
		}
	}
}
