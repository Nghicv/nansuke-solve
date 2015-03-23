import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class DataTable {
	private Variable [][] Data;
	private int size;
	private int nVar;
	public DataTable (){
		Data = new Variable[0][0];
		size = 0;
		nVar = 0;
	}
	
	public DataTable (String fileName) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File(fileName));
		int [] temp = new int[1000];
		int count = 0;
		int temp_index = 1;
		int temp_row = 0;
		int temp_col = 0;
		
		if(scanner.hasNextInt()) size = scanner.nextInt();
		while(scanner.hasNextInt())
		{
			temp[count] = scanner.nextInt();
			count++;
		}
		
		scanner.close();
		Data = new Variable[size][size];
		nVar = size*size;
		
		for(int i = 0; i < size ; i++)
			for(int j = 0; j < size; j++)
			{
				temp_row = (temp_index - 1) / size;
				temp_col = (temp_index - 1) % size;
				Data[i][j] = new Variable(temp_index, temp_row, temp_col, temp[temp_index-1]);
				temp_index ++;
			}
	}
	
	public void printTable()
	{
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				System.out.println(Data[i][j].getIndex() + " " + Data[i][j].getRow() + " " + Data[i][j].getColumn() + " " + Data[i][j].getValue() + " ");
			}
		}
	}
	
	public Variable[][] getData()
	{
		return Data;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getNvar()
	{
		return nVar;
	}
	
	public boolean isOnEdge( Variable var){
		if(var.getColumn() == 0 || var.getColumn() == size - 1 || var.getRow() == 0 || var.getRow() == size - 1) return true;
		return false;
	}
}
