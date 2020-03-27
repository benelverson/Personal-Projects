import java.util.Scanner;

public class Game {

	/**
	 * Let the user play a game of sudoku
	 * 
	 * @param console
	 */
	public static void play(Scanner console, Board b) {
		Board ogBoard = new Board(b);
		
		while (!b.isSolved()) {
			
			printGameBoard(b.getContents(), ogBoard.getContents());

			// FORMAT: row, column, number
			int[] userInts = getNextInts(console);
			int row = userInts[0] - 1;
			int col = userInts[1] - 1;
			int num = userInts[2];
			
			// Put the numbers in the right places if it's ok
			if (ogBoard.isSquareFull(row, col) || 
					!Solve.tryNumber(num, b.getContents(), row, col)) {
				System.err.println("Cannot go there!");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				continue;
			}
			
			b.setSquare(num, row, col);
			
		}
		
	}
	
	/**
	 * @return a number from the user between @param min and @param max
	 * @param scn scanner from user input
	 */
	private static int[] getNextInts(Scanner scn) {
		int[] a = new int[3];
		int userInt;
		while (true) {
			for (int i = 0; i < 3; i++) {
				if (scn.hasNextInt()) {
					userInt = scn.nextInt();
					if (userInt > 0 && userInt < 10) {
						a[i] = userInt;
					} else break;
				} else break;
				if (i == 2) {
					return a;
				}
			}
			System.err.println("Please enter a valid input.");
			scn.next();
		}
	}
	
	/**
	 * Prints out the game board, using System.err statements to denote numbers
	 * that were there originally.
	 * 
	 * @param boardArray the array containing game board
	 * @param ogBoard the array containing the original board
	 */
	public static void printGameBoard(int[][] boardArray, int[][] ogBoard) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				//if (ogBoard[row][col] != 0) {
					//System.out.println(ogBoard[row][col]);
					//System.out.print(ANSIConstants.ANSI_YELLOW + ogBoard[row][col]);
				//} else {
					System.out.print(boardArray[row][col]);
				//}
				if (col != 8) {
					System.out.print("|");
				}
				if (col == 2 || col == 5) {
					System.out.print(" |");
				}
			}
			if (row == 2 || row == 5) {
				System.out.println();
				for (int i = 0; i < 21; i++) {
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
}
