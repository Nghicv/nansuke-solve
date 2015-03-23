import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;


public class DataCNF {
	private String stringCNF;
	private ArrayList<Variable> CheckedList;
	private DataTable Table;
	private int clauseNum;
	public DataCNF(DataTable table)
	{
		stringCNF = "";
		CheckedList = new ArrayList<Variable>();
		this.Table = table;
	}
	
	public void makeCNF()
	{
		// Rule 1
		
		int i, j, k;
		for(i = 0; i < Table.getSize(); i++)
		{
			for(j = 0; j < Table.getSize(); j++)
			{
				for(k = j + 1; k < Table.getSize(); k++)
				{
					if(Table.getData()[i][j].getValue() == Table.getData()[i][k].getValue())
					{
						stringCNF = stringCNF + "-" + Table.getData()[i][j].getIndex() + " " + "-" + Table.getData()[i][k].getIndex() + " 0 " + "\n";
						Table.getData()[i][j].setMultiple();
						Table.getData()[i][k].setMultiple();
					}
				}
			}	
		}
		
		for(j = 0; j < Table.getSize(); j++)
		{
			for(i = 0; i < Table.getSize(); i++)
			{
				for(k = i + 1; k < Table.getSize(); k++)
				{
					if(Table.getData()[i][j].getValue() == Table.getData()[k][j].getValue())
					{
						stringCNF = stringCNF + "-" + Table.getData()[i][j].getIndex() + " " + "-" + Table.getData()[k][j].getIndex() + " 0 " + "\n";
						Table.getData()[i][j].setMultiple();
						Table.getData()[k][j].setMultiple();
					}
				}
			}	
		}
		
		for(i = 0; i < Table.getSize(); i++)
		{
			for(j = 0; j < Table.getSize(); j++)
			{
				if(Table.getData()[i][j].isMultiple() == false)
				{
					stringCNF = stringCNF + Table.getData()[i][j].getIndex() + " 0 " + "\n";
				}
			}
		}
		
		//Rule 2 
		
		for(i = 0; i < Table.getSize(); i++)
		{
			for(j = 0; j < Table.getSize(); j++)
			{
				if(Table.getData()[i][j].isMultiple() == true)
				{
					if(i-1 >= 0)
					{
						stringCNF = stringCNF + Table.getData()[i][j].getIndex() + " " + Table.getData()[i-1][j].getIndex() + " 0 " + "\n";
					}
					
					if(j-1 >= 0)
					{
						stringCNF = stringCNF + Table.getData()[i][j].getIndex() + " " + Table.getData()[i][j-1].getIndex() + " 0 " + "\n";
					}
					
					if(i+1 < Table.getSize())
					{
						stringCNF = stringCNF + Table.getData()[i][j].getIndex() + " " + Table.getData()[i+1][j].getIndex() + " 0 " + "\n";
					}
					
					if(j+1 < Table.getSize())
					{
						stringCNF = stringCNF + Table.getData()[i][j].getIndex() + " " + Table.getData()[i][j+1].getIndex() + " 0 " + "\n";
					}
				}
			}
		}
		
		//Rule 3 
		//Chains
		for(i = 0; i < Table.getSize(); i++)
		{
			for(j = 0; j < Table.getSize(); j++)
			{
				Variable temp = Table.getData()[i][j];
				if(temp.isMultiple() && Table.isOnEdge(temp))
				{
					ArrayList<Variable> chain = new ArrayList<Variable>();
					searchChains(temp, chain);
					CheckedList.add(temp);
				} 
			}
		}
		
		//Cycles
		CheckedList.clear();
		for(i = 0; i < Table.getSize(); i++)
		{
			for(j = 0; j < Table.getSize(); j++)
			{
				Variable temp = Table.getData()[i][j];
				if(temp.isMultiple())
				{
					int countNeighbor = 0;
					if( i - 1 >= 0 && j - 1 >= 0) 
					{
						if(Table.getData()[i-1][j-1].isMultiple()) countNeighbor ++;
					}
					if( i - 1 >= 0 && j + 1 <= Table.getSize() - 1) 
					{
						if(Table.getData()[i-1][j+1].isMultiple()) countNeighbor ++;
					}
					if( i + 1 <= Table.getSize() - 1 && j - 1 >= 0) 
					{
						if(Table.getData()[i+1][j-1].isMultiple()) countNeighbor ++;
					}
					if( i + 1 <= Table.getSize() - 1 && j + 1 <= Table.getSize() - 1) 
					{
						if(Table.getData()[i+1][j+1].isMultiple()) countNeighbor ++;
					}
					
					if(countNeighbor >= 2)
					{	
						ArrayList<Variable> cycle = new ArrayList<Variable>();
						searchCycles(temp, cycle);
						CheckedList.add(temp);
					}
				}
			}
		}
		int index = 0;
		while( index < stringCNF.length()) 
		{
			if(stringCNF.charAt(index) == '\n') clauseNum ++;
			index ++;
		}
		stringCNF = "p cnf " + Table.getNvar() + " " + clauseNum + " \n" + stringCNF;
	}
	
	public void searchChains(Variable var, ArrayList<Variable> chain)
	{
		ArrayList<Variable> tempChain = new ArrayList<Variable>(chain);
		tempChain.add(var);
		searchNextChain(var.getRow()-1, var.getColumn()-1, tempChain);
		searchNextChain(var.getRow()-1, var.getColumn()+1, tempChain);
		searchNextChain(var.getRow()+1, var.getColumn()-1, tempChain);
		searchNextChain(var.getRow()+1, var.getColumn()+1, tempChain);
	}

	public void searchNextChain(int nextRow, int nextCol, ArrayList<Variable> chain)
	{
		if(nextCol >= 0 && nextCol <= Table.getSize() - 1 && nextRow >= 0 && nextRow <= Table.getSize() - 1)
		{
			Variable current = Table.getData()[nextRow][nextCol];
			if(current.isMultiple())
			{
				if(isInList(CheckedList, current)) 
				{
					return;
				}
				if(!isInList(chain,current))
				{
					for(int i = 0; i < chain.size() - 1; i++)
					{
						if(chain.get(i).isNeighbor(current))
						{
							return;
						}
					}
					if(Table.isOnEdge(current))
					{
						chain.add(current);
						writeRule3(chain);
						chain.remove(chain.size()-1);
					}
					else searchChains(current, chain);
				}
			}
		}
	}
	
	public void searchCycles(Variable var, ArrayList<Variable> cycle)
	{
		ArrayList<Variable> tempCycle = new ArrayList<Variable>(cycle);
		tempCycle.add(var);
		searchNextCycle(var.getRow()-1, var.getColumn()-1, tempCycle);
		searchNextCycle(var.getRow()-1, var.getColumn()+1, tempCycle);
		searchNextCycle(var.getRow()+1, var.getColumn()-1, tempCycle);
		searchNextCycle(var.getRow()+1, var.getColumn()+1, tempCycle);
	}
	
	public void searchNextCycle(int nextRow, int nextCol, ArrayList<Variable> cycle)
	{
		if(nextCol >= 0 && nextCol <= Table.getSize() - 1 && nextRow >= 0 && nextRow <= Table.getSize() - 1)
		{
			Variable current = Table.getData()[nextRow][nextCol];
			if(current.isMultiple())
			{
				if(!isInList(cycle,current)) 
				{
					if(isInList(CheckedList, current) ) 
					{
						return;
					}
					
					if(cycle.size() > 2 )
					{
						int countOnEdge = 0;
						for(int i = 1; i < cycle.size() - 1; i++)
						{
							if(Table.isOnEdge(cycle.get(i))) countOnEdge ++;
							if(cycle.get(i).isNeighbor(current))
							{
								return;
							}
						}
						
						if(Table.isOnEdge(cycle.get(cycle.size()-1)))
						{
							countOnEdge++;
						}
						
						if(Table.isOnEdge(cycle.get(0)))
						{
							countOnEdge++;
						}
						
						if(Table.isOnEdge(current)) 
						{
							countOnEdge++;
						}
						if(countOnEdge >= 2)
						{
							return;
						}
						
						if(cycle.get(0).isNeighbor(current))
						{
							cycle.add(current);
							writeRule3(cycle);
							cycle.remove(cycle.size()-1);
							return;
						}
					}
					searchCycles(current, cycle);
				}
				
			}
		}
	}
	
	public boolean isInList(ArrayList<Variable> array, Variable var)
	{
		for(int i = 0; i < array.size(); i++)
		{
			if(array.get(i).getIndex() == var.getIndex()) return true;
		}
		return false;
	}
	
	public void writeRule3(ArrayList<Variable> c)
	{
		for(int i = 0; i < c.size(); i++)
		{
			stringCNF = stringCNF + c.get(i).getIndex() + " ";
		}
		stringCNF = stringCNF + "0" + "\n";
	}
	
	public void printStringCNF()
	{
		System.out.println(stringCNF);
	}
	
	public void writeFile(String fileName)
	{
		  try{
		    FileWriter fstream=new FileWriter(fileName);
		    BufferedWriter out=new BufferedWriter(fstream);
		    out.write(stringCNF);
		    out.close();
		  }
		 catch (Exception e){
		    System.err.println("Error: " + e.getMessage());
		  }
	}
}
	
