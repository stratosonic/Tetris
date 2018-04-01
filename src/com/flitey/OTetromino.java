package com.flitey;

import javafx.scene.paint.Color;

public class OTetromino extends Tetromino {

	public OTetromino(int x, int y, int minoPixelSize) {
		super(x, y, minoPixelSize, Color.YELLOW);
		minos[0] = new Mino(0, 0, minoPixelSize, color);
		minos[1] = new Mino(1, 0, minoPixelSize, color);
		minos[2] = new Mino(0, 1, minoPixelSize, color);
		minos[3] = new Mino(1, 1, minoPixelSize, color);
	}

	@Override
	public void rotate() {
		// Do nothing for this shape
	}

}
