import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Sudoku launcher
 * 
 * @author Benjamin Elverson
 */
public class Sudoku {

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Launch Sudoku

		System.out.println("Starting sudoku...\n");

		Scanner console = new Scanner(System.in);

		while (true) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
			}
			System.out.println("\nWelcome to Sudoku! Please select one"
					+ " of the following options:");
			System.out.println("   1: Solve a puzzle");
			System.out.println("   2: Have the computer solve a puzzle");
			System.out.println("   3: Quit");
			int userInt = getNextUserInt(console, 1, 3);

			// Open dialog for user solving or computer solving
			Board b;
			switch (userInt) {
			case 1: // User solve
				try {
					b = getGameBoard(console);
					Game.play(console, b);
				} catch (IOException e) {
					System.err.println(e);
				}
				break;
			case 2: // Computer solves
				try {
					b = getBoard(console);
					b.solve();
				} catch (IOException e) {
					System.out.println("Error creating board: " + e);
					break;
				} catch (Exception e) {
					System.err.println(e);
					e.printStackTrace();
					break;
				}
				System.out.println("\nBoard solved successfully!\n");
				System.out.println("Would you like to:");
				System.out.println("   1: Save the board to a file");
				System.out.println("   2: Display the board out to the console");
				System.out.println("   3: Display the formated board out to the console");
				userInt = getNextUserInt(console, 1, 3);

				// Open dialog for user choosing what to do after board is solved
				switch (userInt) {
				case 1:
					System.out.println("Enter the location to save the solved board");
					String fileLocation = console.next();
					try {
						IO.saveBoard(b, fileLocation);
					} catch (IOException e) {
						System.err.println(e);
						break;
					}
					System.out.println("Board saved successfully!");
					break;
				case 2:
					System.out.println("\n" + b);
					break;
				case 3:
					System.out.println("\n" + b.toStringf());
					break;
				}
				break;
				
			case 3: // Quit
				return;
			}
		}

	}

	/**
	 * @return a number from the user between @param min and @param max
	 * @param scn scanner from user input
	 */
	private static int getNextUserInt(Scanner scn, int min, int max) {
		int userInt;
		while (true) {
			if (scn.hasNextInt()) {
				userInt = scn.nextInt();
				if (userInt <= max && userInt >= min) {
					break;
				}
			} else {
				scn.nextLine();
			}
			System.out.println("Please enter a valid input.");
		}
		return userInt;
	}

	/**
	 * Selects a random board of the chosen difficulty for the user to solve
	 * 
	 * @param console
	 * @return a board of the chosen difficulty
	 * @throws IOException
	 */
	public static Board getGameBoard(Scanner console) throws IOException {
		System.out.println("Select a dificulty level");
		System.out.println("   1: Easy");
		System.out.println("   2: Medium");
		System.out.println("   3: Hard");
		System.out.println("   4: Expert");
		int userInt = getNextUserInt(console, 1, 4);
		String difficulty;
		switch (userInt) {
		case 1:
			difficulty = "Easy";
			break;
		case 2:
			difficulty = "Medium";
			break;
		case 3:
			difficulty = "Hard";
			break;
		case 4:
			difficulty = "Expert";
			break;
		default:
			difficulty = "Easy";
		}

		File f = new File("Boards/" + difficulty);
		String[] filenames = f.list();
		if (filenames.length == 0) {
			throw new IOException("No files exist in selected directory.");
		}
		Random rnd = new Random();
		String boardName = filenames[rnd.nextInt(filenames.length)];

		Board b = new Board(IO.readFileContents("Boards/" + difficulty + "/" + boardName));

		return b;

	}

	/**
	 * 
	 * @param console
	 * @return
	 * @throws Exception if there was an issue solving the board
	 */
	public static Board getBoard(Scanner console) throws Exception {
		System.out.println("Would you like to:");
		System.out.println("   1: Paste the board into the console as comma separated text");
		System.out.println("   2: Select a file containing the board you want to solve");
		int userInt = getNextUserInt(console, 1, 2);


		switch (userInt) {
		case 1: // Paste as a string
			System.out.println("Enter the String:");
			console.nextLine();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 9; i++) {
				sb.append(console.nextLine() + "\n");
			}
			IO.saveString(sb.toString(), "Boards/temp");
			return new Board(IO.readFileContents("Boards/temp"));

		case 2: // Select file

			return new Board(IO.readFileContents(selectFile(console, "Boards")));


		default:
			// Shouldn't execute
			return new Board(IO.readFileContents(null));
		}

	}

	/**
	 * Lets the user select a file from the parent directory @param parentDirectory
	 * 
	 * @param console scanner for the user input
	 * @return a string representing the file path from the parent directory
	 */
	public static String selectFile(Scanner console, String parentDirectory) {
		
		String directory = parentDirectory;
		File f = new File(directory);
		
		while (f.isDirectory()) {
			String[] filenames = f.list();
			if (filenames.length == 0) {
				System.out.println("Directory Empty\n");
				directory = parentDirectory;
				f = new File(directory);
				continue;
			}
			
			System.out.println("Select a file, or press 0 to enter a file path.\n");
			for (int i = 0; i < filenames.length; i++) {
				System.out.println((i + 1) + ": " + filenames[i]);
			}
			int userInt = getNextUserInt(console, 0, filenames.length);

			// Have user enter file directory manually
			if (userInt == 0) {
				System.out.println("Enter the directory of the file containing the " 
						+ "board you would like to solve:");
				return console.next();
			}
			
			directory = directory + "/" + filenames[userInt - 1];
			f = new File(directory);
			
			
		}
		return directory;
	}

}
