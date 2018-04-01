package com.flitey;

import javafx.scene.paint.Color;

public class ITetromino extends Tetromino {

	private enum Rotation {
		HORIZONTAL, VERTICAL
	}

	private Rotation rotation;

	public ITetromino(int x, int y, int minoPixelSize) {
		super(x, y, minoPixelSize, Color.LIGHTBLUE);
		rotation = Rotation.HORIZONTAL;

		minos[0] = new Mino(-1, 0, minoPixelSize, color);
		minos[1] = new Mino(0, 0, minoPixelSize, color);
		minos[2] = new Mino(1, 0, minoPixelSize, color);
		minos[3] = new Mino(2, 0, minoPixelSize, color);
	}

	@Override
	public void rotate() {
		if (rotation.equals(Rotation.HORIZONTAL)) {
			rotation = Rotation.VERTICAL;
			minos[0].updateLocation(0, -1);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(0, 1);
			minos[3].updateLocation(0, 2);
		} else {
			rotation = Rotation.HORIZONTAL;
			minos[0].updateLocation(-1, 0);
			minos[1].updateLocation(0, 0);
			minos[2].updateLocation(1, 0);
			minos[3].updateLocation(2, 0);
		}
	}
}