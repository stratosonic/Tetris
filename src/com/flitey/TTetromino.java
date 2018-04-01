package com.flitey;

import javafx.scene.paint.Color;

public class TTetromino extends Tetromino {

	private enum Rotation {
		UP, RIGHT, DOWN, LEFT
	}

	private Rotation rotation;

	public TTetromino(int x, int y, int minoPixelSize) {
		super(x, y, minoPixelSize, Color.PURPLE);
		rotation = Rotation.UP;
		minos[0] = new Mino(-1, 0, minoPixelSize, color);
		minos[1] = new Mino(0, 0, minoPixelSize, color);
		minos[2] = new Mino(1, 0, minoPixelSize, color);
		minos[3] = new Mino(0, -1, minoPixelSize, color);
	}

	@Override
	public void rotate() {
		switch (rotation) {
		case UP:
			rotation = Rotation.RIGHT;
			minos[0].updateLocation(0, -1);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(0, 1);
			minos[3].updateLocation(1, 0);
			break;
		case RIGHT:
			rotation = Rotation.DOWN;
			minos[0].updateLocation(-1, 0);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(1, 0);
			minos[3].updateLocation(0, 1);
			break;
		case DOWN:
			rotation = Rotation.LEFT;
			minos[0].updateLocation(0, -1);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(0, 1);
			minos[3].updateLocation(-1, 0);
			break;
		case LEFT:
			rotation = Rotation.UP;
			minos[0].updateLocation(-1, 0);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(1, 0);
			minos[3].updateLocation(0, -1);
			break;
		default:
			break;
		}
	}

}
