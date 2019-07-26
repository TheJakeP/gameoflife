import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameOfLifeTest {
	@Test
	public void testConstructorAndGetters() {
		GameOfLife society = new GameOfLife(5, 8);
		assertEquals(5, society.numberOfRows());
		assertEquals(8, society.numberOfColumns());
		for (int r = 0; r < society.numberOfRows(); r++)
			for (int c = 0; c < society.numberOfColumns(); c++)
				assertFalse(society.cellAt(r, c));
	}

	@Test
	public void testAdjRow() {
		GameOfLife society = new GameOfLife(5, 8);
		assertEquals(0, society.adjRow(5 + 1));
		assertEquals(4, society.adjRow(0 - 1));

		assertEquals(4, society.adjRow(4));
	}

	@Test
	public void testAdjCol() {
		GameOfLife society = new GameOfLife(5, 8);
		assertEquals(0, society.adjCol(8 + 1));
		assertEquals(7, society.adjCol(0 - 1));

		assertEquals(4, society.adjCol(4));
	}

	@Test
	public void testCellAt() {
		GameOfLife society = new GameOfLife(5, 5);

		assertFalse(society.cellAt(2, 1));
		society.growCellAt(2, 1);
		assertTrue(society.cellAt(2, 1));

	}

	/*
	 * •••••
	 * •000•
	 * •0X0•
	 * •000•
	 * •••••
	 * 
	 * society.growCellAt(1, 1);
	 * society.growCellAt(1, 2);
	 * society.growCellAt(1, 3);
	 * society.growCellAt(2, 1);
	 * society.growCellAt(2, 3);
	 * society.growCellAt(3, 1);
	 * society.growCellAt(3, 2);
	 * society.growCellAt(3, 3);
	 * 
	 */

	/*
	 * •••••
	 * •••••
	 * •0X0•
	 * •••••
	 * •••••
	 * society.growCellAt(2, 1);
	 * society.growCellAt(2, 3);
	 */
	@Test
	public void testCheckRowCenter() {
		GameOfLife society = new GameOfLife(5, 5);

		society.growCellAt(2, 1);
		society.growCellAt(2, 3);

		assertEquals(0, society.checkRow(1, 2));
		assertEquals(2, society.checkRow(2, 2));
		assertEquals(0, society.checkRow(3, 2));
	}

	/*
	 * •••••
	 * •••••
	 * X0••0
	 * •••••
	 * •••••
	 * society.growCellAt(2, 1);
	 * society.growCellAt(2, 5);
	 */
	@Test
	public void testCheckRowEdge() {
		GameOfLife society = new GameOfLife(5, 5);

		society.growCellAt(2, 1);
		society.growCellAt(2, 4);

		assertEquals(0, society.checkRow(1, 0));
		assertEquals(2, society.checkRow(2, 0));
		assertEquals(0, society.checkRow(3, 0));
	}

	/*
	 * •••••
	 * ••0••
	 * ••X••
	 * ••0••
	 * •••••
	 * society.growCellAt(1, 2);
	 * society.growCellAt(3, 2);
	 */

	@Test
	public void testCheckCol() {
		GameOfLife society = new GameOfLife(5, 5);
		society.growCellAt(1, 2);
		society.growCellAt(3, 2);

		assertEquals(0, society.checkCol(2, 1));
		assertEquals(2, society.checkCol(2, 2));
		assertEquals(0, society.checkCol(2, 3));
	}

	/*
	 * X0•X0
	 * 0••0•
	 * •••••
	 * •0•••
	 * 0X•0X
	 */
	@Test
	public void testCheckColEdge() {
		GameOfLife society = new GameOfLife(5, 5);

		society.growCellAt(0, 1);
		society.growCellAt(0, 4);
		society.growCellAt(1, 0);
		society.growCellAt(1, 3);
		society.growCellAt(3, 1);
		society.growCellAt(3, 4);
		society.growCellAt(4, 0);
		society.growCellAt(4, 3);

		// System.out.println(society.toString());

		assertEquals(2, society.checkCol(0, 0));
		assertEquals(2, society.checkCol(0, 3));
		assertEquals(2, society.checkCol(4, 1));
		assertEquals(2, society.checkCol(4, 4));

		assertEquals(0, society.checkCol(0, 1));
		assertEquals(0, society.checkCol(0, 4));
		assertEquals(0, society.checkCol(1, 0));
		assertEquals(0, society.checkCol(1, 3));
		assertEquals(0, society.checkCol(3, 1));
		assertEquals(0, society.checkCol(3, 4));
		assertEquals(0, society.checkCol(4, 0));
		assertEquals(0, society.checkCol(4, 3));

	}

	@Test
	public void testGrowCellAtAndCellAt() {
		GameOfLife society = new GameOfLife(4, 4);
		society.growCellAt(1, 1);
		society.growCellAt(2, 2);
		society.growCellAt(3, 3);
		assertTrue(society.cellAt(1, 1));
		assertTrue(society.cellAt(2, 2));
		assertTrue(society.cellAt(3, 3));
	}

/*
 * ••••••••••••••••
 * ••••••••••••••••
 * •1234•••••••••••
 * •••000••••••••••
 * ••••••••••••••••
 * ••••••••••••••••
 * ••••••••••••••••
 * ••••••••••••••••
 * ••••••••••••••••
 * ••••••••••••••••
 */
	@Test
	public void testNeighborsWrapping() {

		GameOfLife society = new GameOfLife(10, 16);

		society.growCellAt(3, 3);
		society.growCellAt(3, 4);
		society.growCellAt(3, 5);

		// System.out.println(society.toString());

		assertEquals(0, society.neighborCount(2, 0));// 1
		assertEquals(0, society.neighborCount(2, 1));// 1
		assertEquals(1, society.neighborCount(2, 2));// 2
		assertEquals(2, society.neighborCount(2, 3));// 3
		assertEquals(3, society.neighborCount(2, 4));// 4
		assertEquals(2, society.neighborCount(2, 5));// 5
		assertEquals(1, society.neighborCount(2, 6));// 6
		assertEquals(0, society.neighborCount(2, 7));// 7

		society.update();
		society.update();
		society.update();
		society.update();
		// ... many more assertions expected
	}

	@Test
	public void testCornerWrapping() {
		int row = 5;
		int col = 10;
		GameOfLife society = new GameOfLife(row, col);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				society.growCellAt(i, j);
			}
		}
		// System.out.println(society);
/*
 * society.growCellAt(0, 1); // A
 * society.growCellAt(0, 5);// B
 * society.growCellAt(1, 0);// C
 * society.growCellAt(1, 1);// D
 * society.growCellAt(1, 5);// E
 * society.growCellAt(4, 0);// F
 * society.growCellAt(4, 1);// G
 * society.growCellAt(4, 5);// H
 */

		// System.out.println(society.toString());
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// System.out.println("i=" + i + ", j=" + j + ", "
				// + society.neighborCount(i, j));
				assertEquals(8, society.neighborCount(i, j));
			}
		}
		society.update();
	}

	@Test
	public void testWrapGrow() {
		GameOfLife society = new GameOfLife(5, 5);

		society.growCellAt(0, 0);
		society.growCellAt(0, 1);
		society.growCellAt(0, 2);

		System.out.println(society);
		society.update();

		System.out.println(society);

		assertFalse(society.cellAt(0, 0));
		assertFalse(society.cellAt(0, 2));

		assertTrue(society.cellAt(0, 1));
		assertTrue(society.cellAt(1, 1));
		assertTrue(society.cellAt(4, 1));

		society = new GameOfLife(5, 5);

		society.growCellAt(1, 4);
		society.growCellAt(2, 4);
		society.growCellAt(3, 4);

		society.update();

		assertFalse(society.cellAt(1, 4));
		assertFalse(society.cellAt(3, 4));

		assertTrue(society.cellAt(2, 0));
		assertTrue(society.cellAt(2, 3));
		assertTrue(society.cellAt(2, 4));

	}

}