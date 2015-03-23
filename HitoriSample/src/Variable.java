
public class Variable {
	private int index;
	private int row;
	private int col;
	private int val;
	private boolean isChecked;
	private boolean isNeighbor;
	private boolean isMultiple;
	
	public Variable(int index, int row, int col, int val)
	{
		this.index = index;
		this.row = row;
		this.col = col;
		this.val = val;
		isMultiple = false;
		isChecked = false;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return col;
	}
	
	public int getValue()
	{
		return val;
	}
	
	public boolean isChecked(){
		return isChecked;
	}
	
	public void setChecked(boolean b){
		isChecked = b;			
	}
	
	public boolean isNeighbor(Variable var){
		if(this.row == var.row - 1 && (this.col == var.col - 1 || this.col == var.col + 1)) return true;
		if(this.row == var.row + 1 && (this.col == var.col - 1 || this.col == var.col + 1)) return true;
		return false;
	}
	
	public void setMultiple(){
		isMultiple = true;
	}
	
	public boolean isMultiple(){
		return isMultiple;
	}
}
