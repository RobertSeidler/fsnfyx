package de.ros.fux.tools;

import java.io.File;
import java.io.IOException;

public class Config {

	private int STANDARD_BOARD_WIDTH; // 0
	private int STANDARD_BOARD_HEIGTH; // 1
	private int STANDARD_BORDER_WIDTH; // 2
	private int STANDARD_CELL_WIDTH; // 3
	private int STANDARD_CELL_HEIGTH; // 4
	private double WIN_PERCENTAGE; // 5
	private int MAX_PLAYER; // 6

	// TODO fehlende Parameter: chances, colors, evtl andere

	public static final int params = 7;

	public Config(){
		
		STANDARD_BOARD_WIDTH = 6;
		STANDARD_BOARD_HEIGTH = 6;
		STANDARD_BORDER_WIDTH = 2;
		STANDARD_CELL_WIDTH = 50;
		STANDARD_CELL_HEIGTH = 50;
		WIN_PERCENTAGE = 0.8;
		MAX_PLAYER = 2;

	}

	public void setInput(String[] inputValues) {

		STANDARD_BOARD_WIDTH = Integer.valueOf(inputValues[0]);
		STANDARD_BOARD_HEIGTH = Integer.valueOf(inputValues[1]);
		STANDARD_BORDER_WIDTH = Integer.valueOf(inputValues[2]);
		STANDARD_CELL_WIDTH = Integer.valueOf(inputValues[3]);
		STANDARD_CELL_HEIGTH = Integer.valueOf(inputValues[4]);
		WIN_PERCENTAGE = Double.valueOf(inputValues[5]);
		MAX_PLAYER = Integer.valueOf(inputValues[6]);

		
	}

	public int getSTANDARD_BOARD_WIDTH() {
		return STANDARD_BOARD_WIDTH;
	}

	public int getSTANDARD_BOARD_HEIGTH() {
		return STANDARD_BOARD_HEIGTH;
	}

	public int getSTANDARD_BORDER_WIDTH() {
		return STANDARD_BORDER_WIDTH;
	}

	public int getSTANDARD_CELL_WIDTH() {
		return STANDARD_CELL_WIDTH;
	}

	public int getSTANDARD_CELL_HEIGTH() {
		return STANDARD_CELL_HEIGTH;
	}

	public double getWIN_PERCENTAGE() {
		return WIN_PERCENTAGE;
	}

	public int getMAX_PLAYER() {
		return MAX_PLAYER;
	}

}
