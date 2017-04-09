package de.ros.fux;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class FusionController implements Initializable {

	public final int STANDARD_BOARD_WIDTH = 3;
	public final int STANDARD_BOARD_HEIGTH = 3;

	private final int STANDARD_BORDER_WIDTH = 1;
	private final int STANDARD_CELL_WIDTH = 50;
	private final int STANDARD_CELL_HEIGTH = 50;

	public Canvas canvas;

	private FusionBoard board = new FusionBoard(STANDARD_BOARD_WIDTH, STANDARD_BOARD_HEIGTH, 2);

	public FusionController() {

	}

	public void drawBoard() {

		canvas.setWidth(STANDARD_BOARD_WIDTH * (STANDARD_CELL_WIDTH + STANDARD_BORDER_WIDTH * 2));
		canvas.setHeight(STANDARD_BOARD_HEIGTH * (STANDARD_CELL_HEIGTH + STANDARD_BORDER_WIDTH * 2));

		GraphicsContext g = canvas.getGraphicsContext2D();

		g.setFill(Paint.valueOf("black"));

		g.fillRect(0, 0, STANDARD_BOARD_WIDTH * (STANDARD_CELL_WIDTH + STANDARD_BORDER_WIDTH * 2),
				STANDARD_BOARD_HEIGTH * (STANDARD_CELL_HEIGTH + STANDARD_BORDER_WIDTH * 2));

		g.setFill(Paint.valueOf("white"));

		for (int i = 0; i < STANDARD_BOARD_WIDTH; i++) {
			for (int j = 0; j < STANDARD_BOARD_HEIGTH; j++) {

				g.fillRect(i * STANDARD_CELL_WIDTH + 2 * i * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
						j * STANDARD_CELL_HEIGTH + 2 * j * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
						STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			}
		}

		// drawCircles(1, 1, 1, "red");
		// drawCircles(23, 23, 2, "blue");
		// drawCircles(45, 45, 3, "green");

	}

	private void drawCircles(int cellPosX, int cellPosY, int count, String color) {

		GraphicsContext g = canvas.getGraphicsContext2D();

		g.setFill(Paint.valueOf(color));

		int circleSize = (int) Math.min(Math.floor(STANDARD_CELL_WIDTH / 2), Math.floor(STANDARD_CELL_HEIGTH / 2));

		if (count % 4 == 0) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(Paint.valueOf(color));

		}

		if (count % 4 == 1) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(Paint.valueOf(color));
			g.fillOval(cellPosX + circleSize / 2, cellPosY + circleSize / 2, circleSize, circleSize);
		}
		if (count % 4 == 2) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(Paint.valueOf(color));
			g.fillOval(cellPosX + circleSize, cellPosY + circleSize / 2, circleSize, circleSize);
			g.fillOval(cellPosX, cellPosY + circleSize / 2, circleSize, circleSize);
		}
		if (count % 4 == 3) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(Paint.valueOf(color));
			g.fillOval(cellPosX + circleSize, cellPosY + circleSize, circleSize, circleSize);
			g.fillOval(cellPosX, cellPosY + circleSize, circleSize, circleSize);
			g.fillOval(cellPosX + circleSize / 2, cellPosY, circleSize, circleSize);
		}
	}

	public void mouseMoved() {

	}

	public void mouseClicked(MouseEvent event) {
		double mouseX = event.getX();
		double mouseY = event.getY();
		// System.out.println(mouseX + ", " + mouseY + "\n");
		// System.out.println(getHorizontalCellFromMousePos() + ", " +
		// getVerticalCellFromMousePos() + "\n");

		board.addToCell(getHorizontalCellFromMousePos(mouseX), getVerticalCellFromMousePos(mouseY));
		board.checkForExplosion();

		for (int i = 0; i < STANDARD_BOARD_WIDTH; i++) {
			for (int j = 0; j < STANDARD_BOARD_HEIGTH; j++) {

				int val = board.getCellValue(i, j);
				drawCircles(i * STANDARD_CELL_WIDTH + 2 * i * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
						j * STANDARD_CELL_HEIGTH + 2 * j * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH, val, "blue");
			}
		}

	}

	private int getHorizontalCellFromMousePos(double mouseX) {
		return (int) (Math.floor(mouseX / (STANDARD_CELL_WIDTH + 2 * STANDARD_BORDER_WIDTH)));
	}

	private int getVerticalCellFromMousePos(double mouseY) {
		return (int) (Math.floor(mouseY / (STANDARD_CELL_HEIGTH + 2 * STANDARD_BORDER_WIDTH)));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		drawBoard();
	}

}
