

/**
 * Solve painted-squares puzzle.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
*/

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Driver
{
	public static void main(String [] args)
	{
		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}

		Board b= new Board(args[0]);
		b.display();
		System.out.println();
		
		Queue <Tile> queue = makeQueue(b.tiles);

		testMethod(queue);

		//System.out.println(current);
		//solveIt(0, queue, b);
	}

	private static void testMethod(Queue<Tile> queue) {
		Tile current = queue.remove();
		if(queue.peek() == current)
			return;
		queue.add(current);
		System.out.println(current);
		testMethod(queue);
		System.out.println("it found it");
		
	}

	public static void solveIt(int pos, Queue <Tile> availible, Board board){
		
		if(availible.peek()== null){
			System.out.println("Done");
			return;
		}
		else{
			for(int i=0; i<(board.numCols*board.numRows); i++){
				Tile current = availible.remove();
				if(pos==0){
					for(int n=0; n<4; n++){
						board.grid[0][0] = current;
						solveIt(pos+1, availible, board);
						current.turn();							//if first tile does not go in orientation zero (very likely).
					}
					availible.add(current);
				}
				else if(pos < board.numCols && pos % board.numCols !=0){ //Checks to see if pos is on the first row and not the left most column.
					for(int j=0; j<4; j++){
						if(current.matchLeft(board.grid[0][(pos%board.numCols)-1])){
							board.grid[0][pos%board.numCols] = current;
							System.out.println(current);
							solveIt(pos+1, availible, board);
						}
						else
							current.turn();
					}
					availible.add(current);
					solveIt(pos, availible, board);
				}
				else if(pos % board.numCols != 0){	//Checks all position except tiles found on top row and left most row.
					
					for(int k=0; k<4; k++){
						if(current.matchLeft(board.grid[(pos/board.numCols)][(pos%board.numCols)-1]) && 		//Checks tile to the left and above to see they match.
						   current.matchTop(board.grid[(pos-board.numCols)/ board.numCols][pos%board.numCols]))
						{
							board.grid[pos/board.numCols][pos%board.numCols] = current;							
							solveIt(pos+1, availible, board);
							
						}
						else{
							current.turn();
							System.out.println(current);
						}
					}
					availible.add(current);
					solveIt(pos, availible, board);
				}
				else if(pos % board.numCols == 0 && pos != 0){ //Checks left most column
					for(int l=0; l<4; l++){
						if(current.matchTop(board.grid[pos/board.numCols][0])){
							board.grid[pos / board.numCols][0] = current;
							solveIt(pos+1, availible, board);
						}
						else
							current.turn();
					}
					availible.add(current);
					solveIt(pos, availible, board);
				}
			}
		}
	}	
		
		/*
		 * 	Tile current = availible.remove();
			System.out.println(current);
			solveIt(pos+1, availible, board);
			availible.add(current);
			while(availible.peek() != null){
				System.out.println(availible.remove());
			}
		 */
		/*if(pos > 3){
			return;
		}
		usedPieces[pos] = pos;
		
		for(int i=0; i<(board.numRows*board.numCols); i++){
			usedPieces[pos] = -1;
			System.out.print(usedPieces[i]);
			
		}
		System.out.println();
		solveIt(pos+1, allPieces, usedPieces, board);
		usedPieces[pos] = pos;
		for(int i=0; i<(board.numRows*board.numCols); i++)
				System.out.print(usedPieces[i]);
		System.out.println();*/
	
	public static Queue<Tile> makeQueue(Tile [] myTiles){
		Queue <Tile> myQueue = new LinkedList<Tile>();
		for(int i=0; i<myTiles.length; i++)
			myQueue.add(myTiles[i]);
		
		return myQueue;
	}
}
