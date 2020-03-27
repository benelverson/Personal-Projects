import java.util.Scanner;

public class Board {
	
	private int[][] boardArray;
	private boolean isSolved;
	
	public Board(String s) {
		isSolved = false;
		Scanner scn = new Scanner(s);
		Scanner line;
		boardArray = new int[9][9];
		
		// Put numbers into an ArrayList
		for (int row = 0; row < 9; row++) {
			line = new Scanner(scn.nextLine());
			line.useDelimiter(",");
			for (int col = 0; col < 9; col++) {
				boardArray[row][col] = line.nextInt();
			}
		}
		
		scn.close();
	}
	
	public Board(int[][] b) {
		isSolved = false;
		boardArray = new int[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				boardArray[row][col] = b[row][col];
			}
		}
	}
	
	public Board(Board oldBoard) {
		isSolved = false;
		boardArray = new int[9][9];
		int [][] b = oldBoard.getContents();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				boardArray[row][col] = b[row][col];
			}
		}
	}
	
	/**
	 * @return a new array containing the board
	 */
	public int[][] getContents() {
		int[][] b = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				b[i][j] = boardArray[i][j];
			}
		}
		return b;
	}
	
	public void setSquare(int n, int row, int col) {
		if (row < 9 && row >= 0 && col < 9 && col >= 0) {
			boardArray[row][col] = n;
		}
	}
	
	/**
	 * @returns whether or not the board has been solved.
	 */
	public boolean isSolved() {
		return isSolved;
	}
	
	/**
	 * @return if there is a number on the board at @param row
	 * and @param col.
	 */
	public boolean isSquareFull(int row, int col) {
		return boardArray[row][col] != 0;
	}
	
	/**
	 * @returns a CSV string of the board with new-lines
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				sb.append(boardArray[row][col]);
				if (col != 8) {
					sb.append(",");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * @return a formated string representation of the board
	 */
	public String toStringf() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				sb.append(boardArray[row][col]);
				if (col != 8) {
					sb.append("|");
				}
				if (col == 2 || col == 5) {
					sb.append(" |");
				}
			}
			if (row == 2 || row == 5) {
				sb.append("\n");
				for (int i = 0; i < 21; i++) {
					sb.append("-");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public boolean solve() {
		Solver s = new Solver(this);
		try {
			boardArray = s.solve();
			isSolved = true;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
