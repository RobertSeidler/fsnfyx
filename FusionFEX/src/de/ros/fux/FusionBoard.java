package de.ros.fux;

public class FusionBoard {

	private int verticalCells;
	private int horizontalCells;
	private int playerCount;

	/**
	 * holds all values for the board. every cell is supposed to be between 0 -
	 * 4 or multiples for more players
	 */
	private Integer[][] cells;

	public FusionBoard(int verticalCells, int horizontalCells, int playerCount) {
		super();
		this.verticalCells = verticalCells;
		this.horizontalCells = horizontalCells;
		this.playerCount = playerCount;
		cells = new Integer[verticalCells][horizontalCells];

		for (int i = 0; i < verticalCells; i++) {
			for (int j = 0; j < horizontalCells; j++) {

				cells[i][j] = 0;
			}
		}
		
		System.out.println(cells.length + " | " + cells[0].length);
	}

	public int getVerticalCells() {
		return verticalCells;
	}

	public int getHorizontalCells() {
		return horizontalCells;
	}

	public void addToCell(int x, int y) {
		cells[x][y]++;
	}

	public boolean checkForExplosion() {
		boolean explosion = false;

		for (int i = 0; i < verticalCells; i++) {
			for (int j = 0; j < horizontalCells; j++) {
				if (cells[i][j] % 4 == 0 && cells[i][j] > 0) {
					explosion = true;
					int player = cells[i][j] / 4;
					System.out.println("-------------");
					explode(i,j,player);
				}
			}
		}
		
//		while(explosion) explosion = checkForExplosion();
		
		return explosion;
	}
	
	public int getCellValue(int x, int y){
		return cells[x][y];
	}
	
	public void explode(int i, int j, int player){
		System.out.println(i + " | " +  j + "\n");
		
		cells[i][j] = 0;
		
		if (i != 0) {
			if(cells[i-1][j]%4 == 3 && cells[i-1][j] > 0) explode(i-1,j,player);
			cells[i - 1][j] = (1 + (cells[i - 1][j] % 4)) * player;
		}
		if (i != verticalCells - 1) {
			if(cells[i+1][j]%4 == 3 && cells[i+1][j] > 0) explode(i+1,j,player);
			cells[i + 1][j] = (1 + (cells[i + 1][j] % 4)) * player;
		}
		if (j != 0) {
			if(cells[i][j-1]%4 == 3 && cells[i][j-1] > 0) explode(i,j-1,player);
			cells[i][j - 1] = (1 + (cells[i][j - 1] % 4)) * player;
		}
		if (j != verticalCells - 1) {
			if(cells[i][j+1]%4 == 3 && cells[i][j+1] > 0) explode(i,j+1,player);
			cells[i][j + 1] = (1 + (cells[i][j + 1] % 4)) * player;
		}
	}

}
