package nghicv.com.nansukesolver;

public class Variable {
	
	
	private int row;
	private int col;
	private int index;
	
	
	public Variable(int row, int col, int index) {
		super();
		this.row = row;
		this.col = col;
		this.index = index;
	}
	
	public Variable(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+row+","+col+")"+" ";
	}
	

}
