import java.util.ArrayList;

/**
 * Class used to solve a board object
 * 
 * @author Benjamin Elverson
 *
 */
public class Solver {
	
	private int[][] ogBoard;
	private int[][] boardArray;
	
	private int row;
	private int col;
	
	// Record positions of rows and columns
	private ArrayList<Integer> rowPos;
	private ArrayList<Integer> colPos;
	
	/**
	 * Initialize member variables
	 * @param b
	 */
	public Solver(Board b) {
		ogBoard = b.getContents();
		boardArray = b.getContents();
		rowPos = new ArrayList<Integer>();
		colPos = new ArrayList<Integer>();
		row = 0;
		col = 0;
		
	}
	
	/**
	 * Solves a given board object and returns a new one. Works by
	 * iterating over each empty square and trying the lowest number
	 * that can fit there. If no numbers can legally be placed in a square,
	 * that square is reset to 0 and the last square modified is then tried.
	 * Once the pointer (row and col) has moved across the entire board (so
	 * when row == 9), the board must be solved and the new board object is
	 * returned.
	 * 
	 * @param b Board object to be solved
	 * @return the solved board
	 * @throws Exception if the board cannot be solved
	 */
	public int[][] solve() throws Exception {
		
		// Safety check to see if board is already full
		if (isFull()) {
			throw new Exception("Board already full");
		}
		
		while (row < 9) {
			// If the number at the pointer is an original number, move the pointer and continue.
			if (ogBoard[row][col] != 0) {
				movePointer();
				continue;
			}
			// If a number was placed, continue.
			if (trySquare()) {
				continue;
			}
			// Only get here if no numbers fit the current square.
			
			// If there's no possible numbers to put at the first position,
			// there's no possible solution
			if (rowPos.size() == 0) {
				throw new Exception("Board unsolveable");
			}
			// Set current square to 0
			boardArray[row][col] = 0;
			
			// Return to last position
			row = rowPos.get(rowPos.size() - 1);
			col = colPos.get(colPos.size() - 1);
		
			// Remove last recorded position
			rowPos.remove(rowPos.size() - 1);
			colPos.remove(colPos.size() - 1);	
		}
		
		if (!isSolved()) {
			throw new Exception("Board unsolveable");
		}
		
		return boardArray; // Return a new, solved board array
	}
	
	/**
	 * Tries to fit a number to the current square.
	 * @return true if a number was found, false if not
	 */
	private boolean trySquare() {
		for (int i = boardArray[row][col] + 1; i <= 9; i++) {
			if (tryNumber(i)) {
				boardArray[row][col] = i;
				rowPos.add(row);
				colPos.add(col);
				movePointer();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if it is legal to put a given number in a a given square.
	 * @return whether or not the number can go in the row and column specified
	 */
	private boolean tryNumber(int n) {
		// Check row
		for (int i = 0; i < 9; i++) {
			if (boardArray[i][col] == n && i != row) {
				return false;
			}
		}
		
		// Check column
		for (int i = 0; i < 9; i++) {
			if (boardArray[row][i] == n && i != col) {
				return false;
			}
		}
		
		// Check box
		int boxRow = row / 3;
		int boxCol = col / 3;
		
		// Check each square in the box
		for (int i = boxRow * 3; i < boxRow * 3 + 3; i++) {
			for (int j = boxCol * 3; j < boxCol * 3 + 3; j++) {
				if (boardArray[i][j] == n && i != row && j != col) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * @returns whether or not the board is full
	 */
	private boolean isFull() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// If any elements are 0, the board must not be full.
				if (boardArray[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * Moves the pointer to the next position on the board
	 */
	private void movePointer() {
		if (col == 8) {
			col = 0;
			row++;
		} else {
			col++;
		}
	}
	
	/**
	 * If there are any numbers on the board that don't fit, 
	 * @returns false
	 */
	public boolean isSolved() {
		
		for (row = 0; row < 9; row++) {
			for (col = 0; col < 9; col++) {
				if (!tryNumber(boardArray[row][col]) && isFull()) {
					return false;
				}
			}
		}
		
		return true;
	}

}
