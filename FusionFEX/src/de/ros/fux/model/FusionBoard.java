package de.ros.fux.model;

import java.io.Serializable;

public class FusionBoard implements Serializable {


	private double winPercentage;
	private int verticalCells;
	private int horizontalCells;
	private int playerCount;

	/**
	 * holds all values for the board. every cell is supposed to be between 0 -
	 * 4 or multiples for more players
	 */
	private Integer[][] cells;

	/**
	 * zero is not owned, one means owned by player 1 and so on ...
	 */
	private Integer[][] player;
	private Integer[] playerPoints;

	public FusionBoard(int verticalCells, int horizontalCells, int playerCount, double winPercentage) {
		super();
		this.verticalCells = verticalCells;
		this.horizontalCells = horizontalCells;
		this.playerCount = playerCount;
		this.winPercentage = winPercentage;
		cells = new Integer[verticalCells][horizontalCells];
		player = new Integer[verticalCells][horizontalCells];
		for (int i = 0; i < verticalCells; i++) {
			for (int j = 0; j < horizontalCells; j++) {

				cells[i][j] = 0;
			}
		}

		for (int i = 0; i < verticalCells; i++) {
			for (int j = 0; j < horizontalCells; j++) {

				player[i][j] = 0;
			}
		}

		playerPoints = new Integer[playerCount];

	}

	public int getVerticalCells() {
		return verticalCells;
	}

	public int getHorizontalCells() {
		return horizontalCells;
	}

	public boolean addToCell(int x, int y, int player) {
		if (player == getPlayerValue(x, y) || getCellValue(x, y) == 0) {
			
			cells[x][y]++;
			this.player[x][y] = player;
			checkForExplosion(player);
			
			return true;
		}
		
		return false;
	}

	public boolean checkForExplosion(int playerNr) {
		boolean explosion = false;

		for (int i = 0; i < verticalCells; i++) {
			for (int j = 0; j < horizontalCells; j++) {
				if (cells[i][j] % 4 == 0 && cells[i][j] > 0) {
					explosion = true;
					int player = cells[i][j] / 4;
					// System.out.println("-------------");
					explode(i, j, playerNr);
				}
			}
		}

		// while(explosion) explosion = checkForExplosion();

		return explosion;
	}

	public int getCellValue(int x, int y) {
		return cells[x][y];
	}

	public int getPlayerValue(int x, int y) {
		return player[x][y];
	}

	public void explode(int i, int j, int playerNr) {

		player[i][j] = playerNr;
		cells[i][j] = 0;

		if (i != 0) {
			if (cells[i - 1][j] % 4 == 3 && cells[i - 1][j] > 0)
				explode(i - 1, j, playerNr);
			else {
				player[i - 1][j] = playerNr;
				cells[i - 1][j] = (1 + (cells[i - 1][j] % 4));
			}
		}
		if (i != verticalCells - 1) {
			if (cells[i + 1][j] % 4 == 3 && cells[i + 1][j] > 0)
				explode(i + 1, j, playerNr);
			else {
				player[i + 1][j] = playerNr;
				cells[i + 1][j] = (1 + (cells[i + 1][j] % 4));
			}
		}
		if (j != 0) {
			if (cells[i][j - 1] % 4 == 3 && cells[i][j - 1] > 0)
				explode(i, j - 1, playerNr);
			else {
				player[i][j - 1] = playerNr;
				cells[i][j - 1] = (1 + (cells[i][j - 1] % 4));
			}
		}
		if (j != verticalCells - 1) {
			if (cells[i][j + 1] % 4 == 3 && cells[i][j + 1] > 0)
				explode(i, j + 1, playerNr);
			else {
				player[i][j + 1] = playerNr;
				cells[i][j + 1] = (1 + (cells[i][j + 1] % 4));
			}
		}

	}

	public Integer[] countPlayerPoints() {

		Integer[] count = new Integer[4];

		for (int n = 0; n < 4; n++) {
			count[n] = new Integer(0);
		}

		for (int i = 0; i < horizontalCells; i++)
			for (int j = 0; j < verticalCells; j++) {

				for (int n = 0; n < 4; n++) {

					if (player[i][j] == n + 1)
						count[n]++;
				}
			}

		return count;
	}

	/**
	 * 
	 * @param spawnChance
	 *            0..1 chance to spawn a number on a field
	 * @param oneTwoChance
	 *            0..1 chance that this spawn is a 2 and not a 1
	 * @param twoThreeChance
	 *            0..1 chance that this spawn is a 3 and not a 1 or 2 (0 for
	 *            now)
	 */
	public void generateRandomStartConfiguration(double spawnChance, double oneTwoChance, double twoThreeChance) {

		for (int x = 0; x < horizontalCells; x++)
			for (int y = 0; y < verticalCells; y++) {

				player[x][y] = 0;

				if (Math.random() < spawnChance) {

					cells[x][y] = 1;
					if (Math.random() < oneTwoChance) {
						cells[x][y] = 2;
					}
					if (Math.random() < twoThreeChance) {
						cells[x][y] = 3;
					}

				}
			}
	}
	
	public int checkWin() {

		Integer[] scores = countPlayerPoints();

		for (int n = 0; n < playerCount; n++) {

//			playerLabels[n].setText("P" + (int) (n + 1) + ": " + scores[n]);

			if (scores[n] >= (int) (Math.floor((horizontalCells * verticalCells) * winPercentage))) {

//				whomsTurn.setText("OH, Player " + (n + 1) + " won!");
//				whomsTurn.setTextFill(playerColors[n + 1]);#
				return n;
			}
		}
		
		return 0;

	}
	
	public void removePlayer(int player){
		
		//TODO implement
		
		
		
	}
	

}
