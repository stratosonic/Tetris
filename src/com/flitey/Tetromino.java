package com.flitey;

import javafx.scene.paint.Color;

public abstract class Tetromino {

	protected int x;
	protected int y;
	protected int minoPixelSize;
	protected Mino[] minos = new Mino[4];
	protected Color color;

	public Tetromino(int x, int y, int minoPixelSize, Color color) {
		this.x = x;
		this.y = y;
		this.minoPixelSize = minoPixelSize;
		this.color = color;
	}

	public abstract void rotate();

	public Mino[] getMinos() {
		return minos;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void moveDown() {
		this.y += 1;
	}

	public void moveUp() {
		this.y -= 1;
	}

	public void moveLeft() {
		this.x -= 1;
	}

	public void moveRight() {
		this.x += 1;
	}

}
