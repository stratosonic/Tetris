package com.flitey;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tetris extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private static final long MS_TO_NS = 1_000_000;
	private static final long TASK_UPDATE_PERIOD_MS = 250 * MS_TO_NS;

	private static final int GRID_PIXEL_SIZE = 30;

	private static final int WINDOW_HEIGHT = GRID_PIXEL_SIZE * 30;
	private static final int WINDOW_WIDTH = GRID_PIXEL_SIZE * 20;

	private static final int PLAYING_AREA_WIDTH = GRID_PIXEL_SIZE * 10;
	private static final int PLAYING_AREA_HEIGHT = GRID_PIXEL_SIZE * 20;

	private static final int PLAYING_AREA_X = PLAYING_AREA_WIDTH / 2;
	private static final int PLAYING_AREA_Y = PLAYING_AREA_HEIGHT / 4;

	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private AnimationTimer animationTimer;

	private Grid grid;
	private Tetromino activeTetromino;
	private long previousUpdate = TASK_UPDATE_PERIOD_MS + 1;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Tetris");
		Group root = new Group();
		canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		graphicsContext = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		scene.setOnKeyPressed((e) -> {
			if (e.getCode() == KeyCode.UP) {
				System.out.println("rotate pressed");
				activeTetromino.rotate();
			} else if (e.getCode() == KeyCode.DOWN) {
				// activeTetromino.moveDown();
			} else if (e.getCode() == KeyCode.LEFT) {
				activeTetromino.moveLeft();
				if (grid.isAtEdge(activeTetromino)) {
					activeTetromino.moveRight();
				}
				if (grid.isTetrominoCollision(activeTetromino)) {
					activeTetromino.moveRight();
				}
			} else if (e.getCode() == KeyCode.RIGHT) {
				activeTetromino.moveRight();
				if (grid.isAtEdge(activeTetromino)) {
					activeTetromino.moveLeft();
				}
				if (grid.isTetrominoCollision(activeTetromino)) {
					activeTetromino.moveLeft();
				}
			}
		});

		primaryStage.show();

		grid = new Grid(PLAYING_AREA_WIDTH / GRID_PIXEL_SIZE, PLAYING_AREA_HEIGHT / GRID_PIXEL_SIZE, GRID_PIXEL_SIZE);

		activeTetromino = new ITetromino(3, 0, GRID_PIXEL_SIZE);

		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
				updateTetromino(timestamp);
				drawGrid();
				drawGridMinos();
				drawActiveTetromino();
			}
		};
		animationTimer.start();
	}

	@Override
	public void stop() {
		System.out.println("stopping");
	}

	private void drawActiveTetromino() {
		for (Mino mino : activeTetromino.getMinos()) {
			int x = (activeTetromino.getX() + mino.getX()) * GRID_PIXEL_SIZE + PLAYING_AREA_X;
			int y = (activeTetromino.getY() + mino.getY()) * GRID_PIXEL_SIZE + PLAYING_AREA_Y;
			drawMino(x, y, mino.minoSize, mino.minoSize, mino.color);
		}
	}

	private void drawGrid() {
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillRect(PLAYING_AREA_X, PLAYING_AREA_Y, PLAYING_AREA_WIDTH, PLAYING_AREA_HEIGHT);

		graphicsContext.setStroke(Color.LIGHTGRAY);
		graphicsContext.setLineWidth(0.25);

		for (int x = PLAYING_AREA_X; x < PLAYING_AREA_X + PLAYING_AREA_WIDTH; x += GRID_PIXEL_SIZE) {
			graphicsContext.strokeLine(x, PLAYING_AREA_Y, x, PLAYING_AREA_Y + PLAYING_AREA_HEIGHT);
		}

		for (int y = PLAYING_AREA_Y; y < PLAYING_AREA_Y + PLAYING_AREA_HEIGHT; y += GRID_PIXEL_SIZE) {
			graphicsContext.strokeLine(PLAYING_AREA_X, y, PLAYING_AREA_X + PLAYING_AREA_WIDTH, y);
		}
	}

	private void drawGridMinos() {
		Mino[][] minos = grid.getGridBlocks();

		for (int x = 0; x < minos.length; x++) {
			for (int y = 0; y < minos[x].length; y++) {
				Mino mino = minos[x][y];
				if (mino != null) {
					int minoX = x * GRID_PIXEL_SIZE + PLAYING_AREA_X;
					int minoY = y * GRID_PIXEL_SIZE + PLAYING_AREA_Y;
					drawMino(minoX, minoY, mino.minoSize, mino.minoSize, mino.color);
				}
			}
		}
	}

	private void drawMino(int x, int y, int height, int width, Color color) {
		graphicsContext.setFill(color);
		graphicsContext.fillRect(x, y, width, height);
		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.strokeRect(x, y, width, height);
		float buffer = GRID_PIXEL_SIZE / 6;
		graphicsContext.strokeRect(x + buffer, y + buffer, width - buffer * 2, height - buffer * 2);
	}

	private void updateTetromino(long now) {

		long elapsedTime = now - previousUpdate;

		if (elapsedTime >= TASK_UPDATE_PERIOD_MS) {
			previousUpdate = now;
			activeTetromino.moveDown();

			if (grid.isBottom(activeTetromino) || grid.isTetrominoCollision(activeTetromino)) {
				System.out.println("Collision");
				activeTetromino.moveUp();
				moveActiveTetrominoToGrid();
				createNewTetromino();
				grid.checkForLineCompletion();
			}
		}
	}

	private void moveActiveTetrominoToGrid() {
		grid.addTetrominoToGrid(activeTetromino);
	}

	private void createNewTetromino() {

		Random random = new Random();

		int max = 5;
		int min = 0;

		int randomTetromino = random.nextInt(max - min + 1) + min;
		System.out.println(randomTetromino);

		switch (randomTetromino) {
		case 0:
			activeTetromino = new OTetromino(3, 0, GRID_PIXEL_SIZE);
			break;
		case 1:
			activeTetromino = new ITetromino(4, 0, GRID_PIXEL_SIZE);
			break;
		case 2:
			activeTetromino = new TTetromino(4, 0, GRID_PIXEL_SIZE);
			break;
		case 3:
			activeTetromino = new LTetromino(4, 0, GRID_PIXEL_SIZE);
			break;
		case 4:
			activeTetromino = new JTetromino(4, 0, GRID_PIXEL_SIZE);
			break;
		case 5:
			activeTetromino = new STetromino(4, 0, GRID_PIXEL_SIZE);
			break;
		case 6:
			activeTetromino = new ZTetromino(4, 0, GRID_PIXEL_SIZE);
			break;
		default:
			System.out.println("Not a valid type");
			break;
		}
	}

}
