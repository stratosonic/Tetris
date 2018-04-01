package com.flitey;

import javafx.scene.paint.Color;

public class Mino {

	private int x, y;
	public int minoSize;
	public Color color;

	public Mino(int x, int y, int minoSize, Color color) {
		this.x = x;
		this.y = y;
		this.minoSize = minoSize;
		this.color = color;
	}

	public void updateLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
