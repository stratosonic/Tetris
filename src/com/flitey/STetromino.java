package com.flitey;

import javafx.scene.paint.Color;

public class STetromino extends Tetromino {

	private enum Rotation {
		UP, RIGHT, DOWN, LEFT
	}

	private Rotation rotation;

	public STetromino(int x, int y, int minoPixelSize) {
		super(x, y, minoPixelSize, Color.GREEN);
		rotation = Rotation.UP;
		minos[0] = new Mino(0, 1, minoPixelSize, color);
		minos[1] = new Mino(1, 0, minoPixelSize, color);
		minos[2] = new Mino(1, 1, minoPixelSize, color);
		minos[3] = new Mino(2, 0, minoPixelSize, color);
	}

	@Override
	public void rotate() {
		switch (rotation) {
		case UP:
			rotation = Rotation.RIGHT;
			minos[0].updateLocation(0, -1);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(1, 0);
			minos[3].updateLocation(1, 1);
			break;
		case RIGHT:
			rotation = Rotation.DOWN;
			minos[0].updateLocation(0, 0);
			minos[1].updateLocation(1, -1);
			minos[2].updateLocation(1, 0);
			minos[3].updateLocation(2, -1);
			break;
		case DOWN:
			rotation = Rotation.LEFT;
			minos[0].updateLocation(1, -1);
			minos[1].updateLocation(1, 0);
			minos[2].updateLocation(2, 0);
			minos[3].updateLocation(2, 1);
			break;
		case LEFT:
			rotation = Rotation.UP;
			minos[0].updateLocation(0, 1);
			minos[1].updateLocation(1, 0);
			minos[2].updateLocation(1, 1);
			minos[3].updateLocation(2, 0);
			break;
		default:
			break;
		}
	}

}
