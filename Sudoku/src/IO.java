import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Deals with all the file input and output. Boards are stored
 * in .txt files
 * 
 * @author Benjamin Elverson
 *
 */
public class IO {

	/**
	 * Reads in a CSV file containing the board and
	 * @returns a String containing the contents of the file
	 * 
	 * @throws IOException if the board is formated improperly or if file not found
	 */
	public static String readFileContents(String fileName) throws IOException {

		StringBuilder sb = new StringBuilder();
		File fileIn = new File(fileName);
		Scanner fileScan = new Scanner(fileIn);
		Scanner line;

		// Try to read properly formated sudoku board
			for (int col = 0; fileScan.hasNextLine(); col++) {
				line = new Scanner(fileScan.nextLine());
				line.useDelimiter(",");
				for (int row = 0; row < 9; row++) {
					if (col >= 9 || row >= 9 || !line.hasNextInt()) { 
						// If the board isn't the right shape or there's not an int
						line.close();
						fileScan.close();
						throw new IOException("Error: Improper file format");
					}
					
					int i = line.nextInt();
					if (i < 0 || i > 9) {
						line.close();
						fileScan.close();
						throw new IOException("Error: Invalid numbers in board");
					}
					sb.append(i);
					if (row != 8) {
						sb.append(",");
					}
				}
				sb.append("\n");
			}
			
		fileScan.close();
		return sb.toString();
	}
	
	/**
	 * Saves the contents of a board to a CSV output file
	 * @param b the Board object
	 * @param fileName name of the save file
	 * @throws IOException if there's an IO Problem (i.e. Access is denied)
	 */
	public static void saveBoard(Board b, String fileName) throws IOException {
		
		File fileOut = new File(fileName);
		PrintWriter pw = new PrintWriter(fileOut);
		pw.print(b.toString());
		pw.close();
	}
	
	/**
	 * Saves a String to the specified file location
	 * @param b the Board object
	 * @param fileName name of the save file
	 * @throws IOException if there's an IO Problem (i.e. Access is denied)
	 */
	public static void saveString(String s, String fileName) throws IOException {
		File fileOut = new File(fileName);
		PrintWriter pw = new PrintWriter(fileOut);
		pw.print(s);
		pw.close();
	}

}
