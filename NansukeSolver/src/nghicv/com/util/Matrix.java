package nghicv.com.util;

public class Matrix {
	private int board[][];
	public Matrix(int [][] board){
		this.board=board;
	}
	public int getRow(){
		return board[0].length;
	}
	public int getColumn(){
		return board.length;
	}
	public int get(int row,int col){
		return board[row][col];
	}
}
