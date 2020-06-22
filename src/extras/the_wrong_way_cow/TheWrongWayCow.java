//https://www.codewars.com/kata/the-wrong-way-cow
//
//Task
//Given a field of cows find which one is the Wrong-Way Cow and return her position.
//
//Notes:
//
//There are always at least 3 cows in a herd
//There is only 1 Wrong-Way Cow!
//Fields are rectangular
//The cow position is zero-based [x,y] of her head (i.e. the letter c)
//Examples
//Ex1
//
//cow.cow.cow.cow.cow
//cow.cow.cow.cow.cow
//cow.woc.cow.cow.cow
//cow.cow.cow.cow.cow
//Answer: [6,2]
//
//Ex2
//
//c..........
//o...c......
//w...o.c....
//....w.o....
//......w.cow
//Answer: [8,4]
//
//Notes
//The test cases will NOT test any situations where there are "imaginary" cows, so your solution does not need to worry about such things!
//
//To explain - Yes, I recognize that there are certain configurations where an "imaginary" cow may appear that in fact is just made of three other "real" cows.
//In the following field you can see there are 4 real cows (3 are facing south and 1 is facing north). There are also 2 imaginary cows (facing east and west).
//
//...w...
//..cow..
//.woco..
//.ow.c..
//.c.....

package extras.the_wrong_way_cow;

import java.util.ArrayList;

public class TheWrongWayCow {

	public static int[] findWrongWayCow(final char[][] field) {
		// Fill in the code to return the x,y coordinate position of the
		// head (letter 'c') of the wrong way cow!
		int horizontalCounter = 0;
		int verticalCounter = 0;
		int horizontalBackwardsCounter = 0;
		int verticalBackwardsCounter = 0;
		int[] horizontalArray = new int[2];
		int[] verticalArray = new int[2];
		int[] horizontalBackwardsArray = new int[2];
		int[] verticalBackwardsArray = new int[2];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (j < field[i].length - 2) {
					if (field[i][j] == 'c' && field[i][j + 1] == 'o' && field[i][j + 2] == 'w') { // cow horizontal
						horizontalCounter++;
						horizontalArray[0] = j;
						horizontalArray[1] = i;
					}
					if (field[i][j] == 'w' && field[i][j + 1] == 'o' && field[i][j + 2] == 'c') { // cow backwards
																									// horizontal
						horizontalBackwardsCounter++;
						horizontalBackwardsArray[0] = j+2;
						horizontalBackwardsArray[1] = i;
					}
				}
				if (i < field.length - 2) {
					if (field[i][j] == 'c' && field[i + 1][j] == 'o' && field[i + 2][j] == 'w') { // cow vertical
						verticalCounter++;
						verticalArray[0] = j;
						verticalArray[1] = i;
					}
					if (field[i][j] == 'w' && field[i + 1][j] == 'o' && field[i + 2][j] == 'c') { // cow backwards
						// vertical
						verticalBackwardsCounter++;
						verticalBackwardsArray[0] = j;
						verticalBackwardsArray[1] = i+2;
					}
				}

			}
		}
		ArrayList<Integer> counters = new ArrayList<Integer>();
		counters.add(horizontalCounter);
		counters.add(verticalCounter);
		counters.add(horizontalBackwardsCounter);
		counters.add(verticalBackwardsCounter);
		int minIndex = Integer.MAX_VALUE;
		for (int i = 0; i < counters.size(); i++) {
			if (counters.get(i) == 1) {
				minIndex = i;
			}
		}

		if (minIndex == 0) {
			return horizontalArray;
		} else if (minIndex == 1) {
			return verticalArray;
		} else if (minIndex == 2) {
			return horizontalBackwardsArray;
		} else {
			return verticalBackwardsArray;
		}

	}
}
