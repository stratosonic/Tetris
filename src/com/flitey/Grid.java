package com.flitey;

public class Grid {
	private Mino[][] gridBlocks;
	private int width, height;

	public Grid(int width, int height, int gridBlockSize) {
		this.width = width;
		this.height = height;

		gridBlocks = new Mino[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				gridBlocks[x][y] = null;
			}
		}
	}

	public void printDebug() {
		StringBuilder debugString = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (gridBlocks[x][y] != null) {
					debugString.append("[x]");
				} else {
					debugString.append("[ ]");
				}
			}
			debugString.append("\n");
		}
		System.out.println(debugString.toString());
	}

	public Mino[][] getGridBlocks() {
		return gridBlocks;
	}

	public boolean isAtEdge(Tetromino tetromino) {
		boolean isAtEdge = false;
		for (Mino mino : tetromino.getMinos()) {
			int x = (tetromino.getX() + mino.getX());
			if (x < 0 || x >= width) {
				isAtEdge = true;
				System.out.println("edge");
			}
		}
		return isAtEdge;
	}

	public boolean isBottom(Tetromino tetromino) {
		boolean isBottom = false;
		for (Mino mino : tetromino.getMinos()) {
			int y = (tetromino.getY() + mino.getY());
			if (y >= height) {
				isBottom = true;
				System.out.println("bottom");
			}
		}
		return isBottom;
	}

	public boolean isTetrominoCollision(Tetromino tetromino) {

		boolean isCollision = false;
		for (Mino mino : tetromino.getMinos()) {
			int x = (tetromino.getX() + mino.getX());
			int y = (tetromino.getY() + mino.getY());

			if (isMinoCollision(x, y)) {
				isCollision = true;
				break;
			}
		}

		return isCollision;
	}

	public boolean isMinoCollision(int x, int y) {
		boolean isContainsMino = false;
		try {
			if (x >= 0 && x < width) {
				if (gridBlocks[x][y] != null) {
					isContainsMino = true;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("out of bounds!!! x:" + x + ",y:" + y);
			isContainsMino = true;
		}
		return isContainsMino;
	}

	public void addMino(int x, int y, Mino mino) {
		gridBlocks[x][y] = mino;
	}

	public void addTetrominoToGrid(Tetromino tetromino) {
		for (Mino mino : tetromino.getMinos()) {
			int x = (tetromino.getX() + mino.getX());
			int y = (tetromino.getY() + mino.getY());
			addMino(x, y, mino);
		}
	}

	public void checkForLineCompletion() {
		printDebug();
		for (int y = 0; y < height; y++) {
			boolean isCompletedLine = true;
			xLoop: for (int x = 0; x < width; x++) {
				if (gridBlocks[x][y] == null) {
					isCompletedLine = false;
					break xLoop;
				}
			}

			if (isCompletedLine) {
				System.out.println("line completed");
				for (int x = 0; x < width; x++) {
					gridBlocks[x][y] = null;
				}
				moveDown(y);
				printDebug();
			}
		}
	}

	private void moveDown(int deletedLevel) {
		System.out.println("deleting level " + deletedLevel);
		for (int y = deletedLevel; y > 0; y--) {
			for (int x = 0; x < width; x++) {
				gridBlocks[x][y] = gridBlocks[x][y - 1];
			}
		}
	}
}
