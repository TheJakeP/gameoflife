/**
 * The model for John Conway's Game of Life.
 *
 * This class has all needed methods as stubs.
 * 
 * Comments explain each method what each method does.
 *
 * @author Rick Mercer and Jacob Phelps
 */
public class GameOfLife {
	private boolean[][] GAME;

	/**
	 * Write the constructor so it takes two integer arguments to represent the
	 * number of rows and columns in the game of life. The constructor creates a
	 * society with no cells but space to store rows*cols cells.
	 *
	 * @param rows
	 *             The height of the grid that shows the cells.
	 * @param cols
	 *             The width of the grid that shows the cells.
	 */
	public GameOfLife(int rows, int cols) {
		GAME = new boolean[rows][cols];
	}

	/**
	 * Return the number of rows, which can be indexed from 0..numberOfRows()-1.
	 * 
	 * @return The height of the society.
	 */
	public int numberOfRows() {
		return GAME.length;
	}

	/**
	 * The number of columns, which can be indexed from 0..numberOfColumns()-1.
	 *
	 * @return The height of the society.
	 */
	public int numberOfColumns() {
		// TODO: Complete this method
		return GAME[0].length;
	}

	/**
	 * Place a new cell in the society.
	 * 
	 * @param row
	 *            The row to grow the cell.
	 * @param col
	 *            The column to grow the cell.
	 *
	 *            Precondition: row and col are in range.
	 */
	public void growCellAt(int row, int col) {
		GAME[row][col] = true;
	}

	/**
	 * 5) Return true if there is a cell at the given row and column. Return
	 * false if there is none at the specified location.
	 *
	 * @param row
	 *            The row to check.
	 * @param col
	 *            The column to check.
	 * @return True if there is a cell at the given row or false if none
	 *
	 *         Precondition: row and col are in range.
	 */
	public boolean cellAt(int row, int col) {
		return GAME[row][col];
	}

	/**
	 * Return one big string of cells to represent the current state of the
	 * society
	 * of cells (see output below where '.' represents an empty space and 'O' is
	 * a
	 * live cell. There is no need to test toString. Simply use it to visually
	 * inspect if needed. Here is one sample output from toString:
	 *
	 * GameOfLife society = new GameOfLife(4, 14); society.growCellAt(1, 2);
	 * society.growCellAt(2, 3); society.growCellAt(3, 4);
	 * System.out.println(society.toString());
	 *
	 * @return A textual representation of this society of cells.
	 */
	// Sample Output:
	// ..............
	// ..O...........
	// ...O..........
	// ....O.........
	@Override
	public String toString() {
		String retStr = "";
		for (boolean[] row : GAME) {
			for (boolean isCell : row) {
				if (isCell) {
					retStr += "O";
				} else {
					retStr += ".";
				}
			}
			retStr += "\r\n";
		}
		return retStr;
	}

	/**
	 * The return values should always be in the range of 0 through 8.
	 *
	 * @return The number of neighbors around any cell using wrap around.
	 * 
	 *         Precondition: row and col are in range.
	 *
	 *         Count the neighbors around the given location. Use wraparound. A
	 *         cell in row
	 *         0 has neighbors in the last row if a cell is in the same column,
	 *         or the
	 *         column to the left or right. In this example, cell 0,5 has two
	 *         neighbors in
	 *         the last row, cell 2,8 has four neighbors, cell 2,0 has four
	 *         neighbors, cell
	 *         1,0 has three neighbors. The cell at 3,8 has 3 neighbors. The
	 *         potential
	 *         location for a cell at 4,8 would have three neighbors.
	 */
	// .....O..O
	// O........
	// O.......O
	// O.......O
	// ....O.O..
	public int neighborCount(int row, int col) {
		int count = 0;

		// Top Left
		count += checkNeighborAt(row - 1, col - 1);
		// Top Center
		count += checkNeighborAt(row - 1, col);
		// Top Right
		count += checkNeighborAt(row - 1, col + 1);

		// Left
		count += checkNeighborAt(row, col - 1);
		// Right
		count += checkNeighborAt(row, col + 1);

		// Bottom Left
		count += checkNeighborAt(row + 1, col - 1);
		// Bottom Center
		count += checkNeighborAt(row + 1, col);
		// Bottom Right
		count += checkNeighborAt(row + 1, col + 1);

		return count;
	}

	/**
	 * Description:
	 * 
	 * @param i
	 * @param col
	 * @return
	 */
	private int checkNeighborAt(int row, int col) {
		if (GAME[adjRow(row)][adjCol(col)]) {
			return 1;
		}
		return 0;
	}

	int adjRow(int row) {
		if (row < 0) {
			return numberOfRows() - 1;
		} else if (row > GAME.length - 1)
			return 0;
		return row;
	}

	int adjCol(int col) {
		if (col < 0) {
			return numberOfColumns() - 1;
		} else if (col > GAME[0].length - 1) {
			return 0;
		}
		return col;
	}

	/**
	 * Description:
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	int checkRow(int row, int col) {
		int count = 0;

		// Check Left
		if (GAME[adjRow(row)][adjCol(col - 1)]) {
			count++;
		}

		// Check Right
		if (GAME[adjRow(row)][adjCol(col + 1)]) {
			count++;
		}
		return count;
	}

	/**
	 * Description:
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	int checkCol(int row, int col) {
		int count = 0;
		// Check Left
		if (GAME[adjRow(row - 1)][adjCol(col)]) {
			count++;
		}

		// Check Right
		if (GAME[adjRow(row + 1)][adjCol(col)]) {
			count++;
		}
		return count;
	}

	/**
	 * Update the state to represent the next society. Typically, some cells
	 * will die off while others are born.
	 */
	public void update() {
		int num;
		int numbRows = numberOfRows();
		int numbCols = numberOfColumns();
		boolean[][] updatedField = new boolean[numbRows][numbCols];
		for (int r = 0; r < GAME.length; r++) {
			for (int c = 0; c < GAME[r].length; c++) {
				num = neighborCount(r, c);

				updatedField[r][c] = GAME[r][c];

				if ((num == 3) && (!GAME[r][c])) {
					updatedField[r][c] = true;
				} else if (GAME[r][c] && ((num == 2) || (num == 3))) {
					updatedField[r][c] = true;
				} else if (GAME[r][c] && (num < 2)) {
					updatedField[r][c] = false;
				} else if (GAME[r][c] && (num > 3)) {
					updatedField[r][c] = false;
				}
			}
		}
		GAME = updatedField;
	}
}