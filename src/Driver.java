

/**
 * Solve painted-squares puzzle.
 *
 * @author  Terry Sergeant
 * @version Spring 2016
 *
*/

import java.util.Scanner;

public class Driver
{
	public static void main(String [] args)
	{
		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}

		Board b= new Board(args[0]);
		Board solution = b;
		b.display();
		
		int [] temp = b.grid[0][0].getSides();
		
		for(int i=0; i<(b.numRows*b.numCols); i++)
			System.out.println(b.tiles[i]);
	}

}
