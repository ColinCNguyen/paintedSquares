

/**
 * Solve painted-squares puzzle.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
*/

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Driver
{
	static Boolean foundIt = false;
	static int solutionOrNot = 0;
	public static void main(String [] args)
	{
		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}

		Board b= new Board(args[0]);
		b.display();
		System.out.println();
		
		ArrayDeque <Tile> queue = makeQueue(b.tiles);
		solveIt(0, queue, b);
		b.display();
	}

	/**
	 * This method is supposed to solve the "painted square" puzzles using a backtracking approach.
	 * @param pos current position on the board
	 * @param availible the current availible tiles that can be places
	 * @param board the board itself
	 */
	public static void solveIt(int pos, ArrayDeque <Tile> availible, Board board){
		
		if(availible.peek()== null){	//Checks to see if we found a solution.
			foundIt = true;
			System.out.println("Done");
			return;
		}
		else{
			for(int i=0; i<(board.numCols*board.numRows); i++){
				Tile current = availible.remove();
				if(foundIt)
					return;
				if(pos==0){
					for(int n=0; n<4; n++){
						board.grid[0][0] = current;
						solveIt(pos+1, availible, board);
						if(foundIt)
							return;
						current.turn();							//if first tile does not go in orientation zero (very likely).
					}
					availible.addLast(current);
				}
				else if(pos < board.numCols && pos % board.numCols !=0){ //Checks to see if pos is on the first row and not the left most column.
					for(int j=0; j<4; j++){
						if(current.matchLeft(board.grid[0][(pos%board.numCols)-1])){
							board.grid[0][pos%board.numCols] = current;
							solveIt(pos+1, availible, board);
							if(foundIt)
								return;
						}
						else
							current.turn();
					}
					availible.addLast(current);
				}
				else if(pos % board.numCols != 0){	//Checks all position except tiles found on top row and left most row.
					
					for(int k=0; k<4; k++){
						if(current.matchLeft(board.grid[(pos/board.numCols)][(pos%board.numCols)-1]) && 		//Checks tile to the left and above to see they match.
						   current.matchTop(board.grid[(pos-board.numCols)/ board.numCols][pos%board.numCols]))
						{
							board.grid[pos/board.numCols][pos%board.numCols] = current;							
							solveIt(pos+1, availible, board);
							if(foundIt)
								return;
							
						}
						else{
							current.turn();
						}
					}
					availible.addLast(current);
				}
				else if(pos % board.numCols == 0 && pos != 0){ //Checks left most column
					for(int l=0; l<4; l++){
						if(current.matchTop(board.grid[(pos-board.numCols)/board.numCols][0])){
							board.grid[pos / board.numCols][0] = current;
							solveIt(pos+1, availible, board);
							if(foundIt)
								return;
						}
						else
							current.turn();
					}
					availible.addLast(current);
				}
			}
			return;		//return outside of initial for-loop due to having checked all elements in current queue (availible).
		}
	}	
	
	/**
	 * This method simply fills a queue with all the possible tiles the computer can use.
	 * @param myTiles is the array of all tiles that can be used
	 * @return The filled queue of tiles
	 */
	public static ArrayDeque<Tile> makeQueue(Tile [] myTiles){
		ArrayDeque <Tile> myQueue = new ArrayDeque<Tile>();
		for(int i=0; i<myTiles.length; i++)
			myQueue.add(myTiles[i]);
		
		return myQueue;
	}
}
