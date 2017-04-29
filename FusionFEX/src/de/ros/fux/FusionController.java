package de.ros.fux;

import de.ros.fux.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.ros.fux.model.FusionBoard;
import de.ros.fux.tools.Config;
import de.ros.fux.tools.ReadIn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class FusionController implements Initializable {

	public int STANDARD_BOARD_WIDTH;
	public int STANDARD_BOARD_HEIGTH;
	private int STANDARD_BORDER_WIDTH;
	private int STANDARD_CELL_WIDTH;
	private int STANDARD_CELL_HEIGTH;
	private double WIN_PERCENTAGE;
	private int MAX_PLAYER;

	public Canvas canvas;
	public Label whomsTurn = new Label();
	public Label[] playerLabels = new Label[4];
	public HBox labelBox = new HBox();

	/**
	 * current player (should be greater than 0)
	 */
	private int player = 1;

	private Paint[] playerColors = new Paint[5];

	private FusionBoard board;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Config conf = ReadIn.readIniFile();
		
		STANDARD_BOARD_WIDTH = conf.getSTANDARD_BOARD_WIDTH();
		STANDARD_BOARD_HEIGTH = conf.getSTANDARD_BOARD_HEIGTH();
		STANDARD_BORDER_WIDTH = conf.getSTANDARD_BORDER_WIDTH();
		STANDARD_CELL_WIDTH = conf.getSTANDARD_CELL_WIDTH();
		STANDARD_CELL_HEIGTH = conf.getSTANDARD_CELL_HEIGTH();
		WIN_PERCENTAGE = conf.getWIN_PERCENTAGE();
		MAX_PLAYER = conf.getMAX_PLAYER();

		board = new FusionBoard(STANDARD_BOARD_WIDTH, STANDARD_BOARD_HEIGTH, MAX_PLAYER);
		
		playerColors[0] = Paint.valueOf("black");
		playerColors[1] = Paint.valueOf("blue");
		playerColors[2] = Paint.valueOf("red");
		playerColors[3] = Paint.valueOf("yellow");
		playerColors[4] = Paint.valueOf("green");

		whomsTurn.setText("Player " + player + "'s turn");
		whomsTurn.setTextFill(playerColors[player]);

		for (int i = 0; i < MAX_PLAYER; i++) {
			playerLabels[i] = new Label();
			playerLabels[i].setTextFill(playerColors[i + 1]);
			playerLabels[i].setText("P" + (int) (i + 1) + ": 0");
			labelBox.getChildren().add(playerLabels[i]);

		}

		board.generateRandomStartConfiguration(0.5, 0.7, 0.2);
		drawBoard();
	}

	public void drawBoard() {

		canvas.setWidth(STANDARD_BOARD_WIDTH * (STANDARD_CELL_WIDTH + STANDARD_BORDER_WIDTH * 2));
		canvas.setHeight(STANDARD_BOARD_HEIGTH * (STANDARD_CELL_HEIGTH + STANDARD_BORDER_WIDTH * 2));

		
		// g.setFill(Paint.valueOf("black"));

		// g.fillRect(0, 0, STANDARD_BOARD_WIDTH * (STANDARD_CELL_WIDTH +
		// STANDARD_BORDER_WIDTH * 2),
		// STANDARD_BOARD_HEIGTH * (STANDARD_CELL_HEIGTH + STANDARD_BORDER_WIDTH
		// * 2));

		drawFrameWithColor();

		for (int i = 0; i < STANDARD_BOARD_WIDTH; i++) {
			for (int j = 0; j < STANDARD_BOARD_HEIGTH; j++) {

				int val = board.getCellValue(i, j);

				drawCircles(i * STANDARD_CELL_WIDTH + 2 * i * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
						j * STANDARD_CELL_HEIGTH + 2 * j * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH, val,
						playerColors[0]);

			}
		}

		// drawCircles(1, 1, 1, "red");
		// drawCircles(23, 23, 2, "blue");
		// drawCircles(45, 45, 3, "green");

	}

	private void drawCircles(int cellPosX, int cellPosY, int count, Paint color) {

		
		GraphicsContext g = canvas.getGraphicsContext2D();

		g.setFill(color);

		int circleSize = (int) Math.min(Math.floor(STANDARD_CELL_WIDTH / 2), Math.floor(STANDARD_CELL_HEIGTH / 2));

		if (count % 4 == 0) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(color);

		}

		if (count % 4 == 1) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(color);
			g.fillOval(cellPosX + circleSize / 2, cellPosY + circleSize / 2, circleSize, circleSize);
		}
		if (count % 4 == 2) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(color);
			g.fillOval(cellPosX + circleSize, cellPosY + circleSize / 2, circleSize, circleSize);
			g.fillOval(cellPosX, cellPosY + circleSize / 2, circleSize, circleSize);
		}
		if (count % 4 == 3) {

			g.setFill(Paint.valueOf("white"));
			g.fillRect(cellPosX, cellPosY, STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			g.setFill(color);
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

		int x = getHorizontalCellFromMousePos(mouseX);
		int y = getVerticalCellFromMousePos(mouseY);

		if (player == board.getPlayerValue(x, y) || board.getCellValue(x, y) == 0) {
			board.addToCell(getHorizontalCellFromMousePos(mouseX), getVerticalCellFromMousePos(mouseY), player);
			board.checkForExplosion(player);
			drawFrameWithColor();
			for (int i = 0; i < STANDARD_BOARD_WIDTH; i++) {
				for (int j = 0; j < STANDARD_BOARD_HEIGTH; j++) {

					int val = board.getCellValue(i, j);
					int pla = board.getPlayerValue(i, j);

					drawCircles(i * STANDARD_CELL_WIDTH + 2 * i * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
							j * STANDARD_CELL_HEIGTH + 2 * j * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH, val,
							playerColors[pla]);

				}
			}

			nextPlayer();
			updatePlayerScoreAndCheckWin();
			

		}

	}

	private void updatePlayerScoreAndCheckWin() {

		Integer[] scores = board.countPlayerPoints();

		for (int n = 0; n < MAX_PLAYER; n++) {

			playerLabels[n].setText("P" + (int) (n + 1) + ": " + scores[n]);

			if (scores[n] >= (int) (Math.floor((STANDARD_BOARD_WIDTH * STANDARD_BOARD_HEIGTH) * WIN_PERCENTAGE))) {

				whomsTurn.setText("OH, Player " + (n + 1) + " won!");
				whomsTurn.setTextFill(playerColors[n + 1]);
			}
		}

	}

	private void nextPlayer() {

		if (player == MAX_PLAYER)
			player = 1;
		else
			player++;

		whomsTurn.setText("Player " + player + "'s turn");
		whomsTurn.setTextFill(playerColors[player]);
	}

	private int getHorizontalCellFromMousePos(double mouseX) {
		return (int) (Math.floor(mouseX / (STANDARD_CELL_WIDTH + 2 * STANDARD_BORDER_WIDTH)));
	}

	private int getVerticalCellFromMousePos(double mouseY) {
		return (int) (Math.floor(mouseY / (STANDARD_CELL_HEIGTH + 2 * STANDARD_BORDER_WIDTH)));
	}

	private void drawFrameWithColor() {
		GraphicsContext g = canvas.getGraphicsContext2D();

		for (int i = 0; i < STANDARD_BOARD_WIDTH; i++) {
			for (int j = 0; j < STANDARD_BOARD_HEIGTH; j++) {

				g.setFill(playerColors[board.getPlayerValue(i, j)]);
				g.fillRect(i * STANDARD_CELL_WIDTH + 2 * i * STANDARD_BORDER_WIDTH,
						j * STANDARD_CELL_HEIGTH + 2 * j * STANDARD_BORDER_WIDTH,
						STANDARD_CELL_WIDTH + 2* STANDARD_BORDER_WIDTH, STANDARD_CELL_HEIGTH + 2* STANDARD_BORDER_WIDTH);
			}
		}

		g.setFill(Paint.valueOf("white"));

		for (int i = 0; i < STANDARD_BOARD_WIDTH; i++) {
			for (int j = 0; j < STANDARD_BOARD_HEIGTH; j++) {

				g.fillRect(i * STANDARD_CELL_WIDTH + 2 * i * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
						j * STANDARD_CELL_HEIGTH + 2 * j * STANDARD_BORDER_WIDTH + STANDARD_BORDER_WIDTH,
						STANDARD_CELL_WIDTH, STANDARD_CELL_HEIGTH);
			}
		}
	}
}
