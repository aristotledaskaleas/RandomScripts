/*
	// DEVELOPMENT BRANCH - test
	Aristotle Daskaleas - 2026
	2-number (trivial/simple) lottery game.

	This is a game of chance where the user randomly supplies a number to the program.
	The program generates a random number in the same range as the user-requested number.
	If both numbers are an exact match, the grand prize is won.
	If both numbers are correct, but transposed, the second prize is won.
	If one number is correct, and in the correct place, the third prize is won.
	If one number is correct, but in the incorrect place, the fourth prize is won.
	Otherwise, it's a loss.

	If IS_TEST_MODE = true, then useful debugging statements (currently only one - 9/3) will run.
	This includes printing the randomly generated number before the program asks the user for another one.
	This ensures the branches run properly (checking lottery winning logic).

	The variable DBG_MSG sets what to prepend every debugging test statement with (for clarity in output),
	set to blank to hide from the output which statements are part of debugging (not recommended).
*/
import java.util.*;

public class Main {
	// final - constant & static - can be used in class (not instance var)
	final static boolean IS_TEST_MODE = false; // always set to false in production
	final static String DBG_MSG = "[DEBUG] "; // this string will prepend lines enabled with IS_TEST_MODE = true;
											  // ensure this string ends with a space unless you do not want to separate this string from the message (not recommended)

	public static void main(String[] args) {
		lottery(); // call the main program

		// thank user and exit
		System.out.println("\nThank you for playing!");

		// END of Program
		return;
	}

	public static void lottery() {
		/* 
			PRE: none
			POST: 
				Generate a random number (10-99).
				Prompt user for a number with the same range, validates input and
					continuously requests input while it's invalid.
				If numbers are exactly equivalent, print $10,000 and exit
				Save first and second digit of both numbers with int division and modulo.
				If both digits are correct, but transposed, print $5,000 and exit
				If either digit is correct and in the correct place, print $1,000 and exit
				If either digit is correct and in the incorrect place, print $250 and exit
		*/
		Scanner input = new Scanner(System.in); // initialize Scanner to gather user input
		int x = (int)(Math.random() * 90) + 10; // 10-99 [(int)(Math.random() * [desired values from 0 + 1]) + (values from 0 to skip)]
		int y; // initialize integer to store user input

		if (IS_TEST_MODE) { // simple print statement that exposes the generated number for testing program logic
			System.out.printf("%sSystem: %d\n", DBG_MSG, x);
		}

		while (true) {
			System.out.print("Please enter a number (10-99): ");

			try {
				y = input.nextInt(); // get user input

				input.nextLine(); // advance cursor in input buffer

				if (y >= 10 && y <= 99) { // base case: y is in the requested range
					System.out.println(); // formatting
					input.close(); // prevent resource leakage
					break;
				}
				else if (y < 10 || y > 99) { // try again, hint the user as to what went wrong
					System.err.println("out of range\n");
					continue;
				}
			} catch (InputMismatchException e) { // if they decided to enter a non-integer
				input.nextLine(); // move past invalid input to prevent infinite loop

				System.err.println("expected an integer\n");
				continue;
			}

			System.err.println("bad input"); // an unknown error occurred, try again.
		}

		// print to user randomly generated number (so they don't think they're being cheated)
		System.out.printf("System: %d, User: %d\n", x, y);
		
		if (x == y) { // numbers are an exact match
			System.out.println("Congratulations! You win $10,000!");
			return;
		}

        // extract individual digits from randomly generated number
		int rand1 = x / 10; // first number (XY / 10 = X.R & (int)X.R = X)
		int rand2 = x % 10; // second number (XY % 10 = Y [X is multiple of 10 and Y is remainder])

		// same process, but for user number
		int user1 = y / 10;
		int user2 = y % 10;

		if (rand1 == user2 && rand2 == user1) { // Both numbers are correct, wrong place
			System.out.println("Congratulations! You win $3,000!");
			return;
		}

		if (rand1 == user1 || rand2 == user2) { // Either number is correct, right place
			System.out.println("Congratulations! You win $1,000!");
			return;
		}

		if (rand1 == user2 || rand2 == user1) { // Either number is correct, wrong place
			System.out.println("Congratulations! You win $250!");
			return;
		}

		System.out.println("Better luck next time."); // None of the conditions were met
	}
}
