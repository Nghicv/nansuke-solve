package group4.nanduke.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class NansukeSolver extends Observable{
	private Game game;
	private int n;
	private String[] numbers;
	private String[] sizeCell;
	private int number_black;
	private int[][] array;
	private int number_clause;
	
	//khai bao cac bien cho phan tao file cnf
	private File dir;
	private PrintWriter writer;
	
	public NansukeSolver(Game game){
		this.game = game;
	}
	
	public void Solving(){
		System.out.println("Solving");
		n = game.getSize();
		number_black = game.getBlackCell();
		array = game.getArray();
		numbers = game.getStringNumbers();
		sizeCell = game.getSizeCell();
		System.out.println("so luong o den :"+number_black);
		createFileCNF();
		solver();
		setChanged();
        notifyObservers(UpdateAction.SOLVING);
	}
	
	private void solver(){
		ISolver solver = SolverFactory . newDefault ();
		solver . setTimeout (3600); // 1 hour timeout
		Reader reader = new DimacsReader ( solver );
		// CNF filename is given on the command line
		try {
			IProblem problem = reader . parseInstance ("cnf");
			if ( problem . isSatisfiable ()) {
				System . out . println (" Satisfiable  !");
				String s =  reader . decode ( problem . model ());
				System.out.println("soling: "+s);
				String[] out = s.split("[ ]", 0);
				File file = new File("cnf_solver");
				PrintWriter w = new PrintWriter(file);
				for (int i = 0; i < out.length-1; i++) {
					w.println(out[i]);
				}
				w.close();
				
			} 
			else {
				System . out . println (" Unsatisfiable  !");
			}
		} catch ( FileNotFoundException e) {
		// TODO Auto - generated catch block
		} catch ( ParseFormatException e) {
		// TODO Auto - generated catch block
		} catch ( IOException e) {
		// TODO Auto - generated catch block
		} catch ( ContradictionException e) {
			System .out . println (" Unsatisfiable  ( trivial )!");
		} catch ( TimeoutException e) {
			System .out . println (" Timeout ,  sorry !");
		}
	}
	
	private void createFileCNF(){
		number_clause = 0;
		dir = new File("example");
		try{
			writer = new PrintWriter(dir);
			
			
			//phan tao dieu kien co ban cho cac o
			for (int i = 0; i < numbers.length; i++) {
				if(numbers[i].length()>1){
					String[] number = numbers[i].split("[:]", 0);//cac gia tri ma bai toan cho san
					System.out.println("size "+ (i+1));
					System.out.println(sizeCell[i+1]);
					String[] site = sizeCell[i+1].split("[|]", 0);//vi tri cac o voi kich thuoc cho san
					
					//lap cho tung vi tri mot
					for (int j = 0; j < site.length; j++) {
						String[] position = site[j].split("[:]", 0);//lay ra vi tri cua mot o
						for (int k = 0; k < position.length; k++) {
							
							//tien dieu kien moi o co the nhan mot gia tri trong khoang tu 1 den 9
							for (int k2 = 1; k2 < 10; k2++) {
								writer.print(position[k] + "" + k2 +" ");
							}
							writer.println(""+0);
							
							//gia tri ma mot o ko nhan tu cac so cho san
							ArrayList<Integer> value_posiable = new ArrayList<Integer>();
							for (int k2 = 1; k2 < 10; k2++) {
								boolean condition = false;
								for (int k3 = 0; k3 < number.length; k3++) {
									int v = number[k3].charAt(k)-'0';
									if(k2==v){
										condition = true;
										break;
									}
								}
								if(condition){
									condition = true;
									for (int l = 0; l < value_posiable.size(); l++) {
										if(k2==value_posiable.get(l)){
											condition = false;
											break;
										}
									}
									if(condition)
										value_posiable.add(k2);
								}
								else
									writer.println("-"+position[k] + "" + k2 +" 0");
							}
							
							//cac gia tri ma o co the nhan
							for (int k2 = 0; k2 < value_posiable.size(); k2++) {
								writer.print(position[k]+value_posiable.get(k2)+ " ");
							}
							writer.println(" 0");
							//mot o chi co the nhan mot gia tri 
							if(value_posiable.size()>1){
								for (int k2 = 0; k2 < value_posiable.size(); k2++) {
									for (int l = k2+1; l < value_posiable.size(); l++) {
										writer.println("-"+position[k]+value_posiable.get(k2)+" -"+position[k]+value_posiable.get(l)+ " 0");
									}
								}
							}
						}
						//phan keo theo
						ArrayList<String> array_overlap = new ArrayList<>();
						ArrayList<String> array_noverlap = new ArrayList<>();
						for (int k = 0; k < number.length; k++) {
							int v = number[k].charAt(0)-'0';
							boolean condition = true;
							for (int k2 = k-1; k2 >=0; k2--) {
								int v1 = number[k2].charAt(0)-'0';
								if(v==v1){
									condition = false;
									break;
								}
							}
							for (int k2 = k+1; k2 < number.length; k2++) {
								int v1 = number[k2].charAt(0)-'0';
								if(v==v1){
									condition = false;
									break;
								}
							}
							if(condition)
								array_noverlap.add(number[k]);
							else
								array_overlap.add(number[k]);
						}
						// truong hop so ko bi trung nhau
						for (int k = 0; k < array_noverlap.size(); k++) {
							for (int k2 = 1; k2 < position.length; k2++) {
								writer.println("-"+position[0]+array_noverlap.get(k).charAt(0)+" "+position[k2]+array_noverlap.get(k).charAt(k2)+" 0");
							}
						}	
						//truong hop so bi trung nhau
						for (int k = 0; k < 1; k++) {
							ArrayList<Integer> value_posiable = new ArrayList<Integer>();
							for (int k2 = 0; k2 < array_overlap.size(); k2++) {
								int v = array_overlap.get(k2).charAt(k)-'0';
								boolean condition = true;
								for (int l = 0; l < value_posiable.size(); l++) {
									int v1 = value_posiable.get(l);
									if(v==v1){
										condition = false;
										break;
									}
								}
								if(condition)
									value_posiable.add(v);
							}
							for (int k2 = 0; k2 < value_posiable.size(); k2++) {
								writer.print("-"+position[k]+value_posiable.get(k2));
								ArrayList<Integer> value_posiable_2 = new ArrayList<Integer>();
								for (int l = 0; l < array_overlap.size(); l++) {
									int value_first = array_overlap.get(l).charAt(k)-'0';				
									int v = array_overlap.get(l).charAt(k+1)-'0';
									boolean condition = true;
									for (int m = 0; m < value_posiable_2.size(); m++) {
										int v1 = value_posiable_2.get(m);
										if(v==v1){
											condition = false;
											break;
										}
									}
									if(value_first!=value_posiable.get(k2))
										condition = false;
									if(condition)
										value_posiable_2.add(v);
								}
								for (int l = 0; l < value_posiable_2.size(); l++) {
									writer.print(" "+position[k+1]+value_posiable_2.get(l));
								}
								writer.println(" 0");
								
							}				
						}
						
					}
					
					//phan loai tru
					for (int j = 0; j < site.length; j++) {
						for (int t = 0; t < number.length; t++) {
							for (int j2 = j+1; j2 < site.length; j2++) {
								String[] position = site[j].split("[:]", 0);
								for (int k = 0; k < position.length; k++) {
									writer.print("-"+position[k]+number[t].charAt(k)+" ");
								}
								position = site[j2].split("[:]", 0);
								for (int k = 0; k < position.length; k++) {
									writer.print("-"+position[k]+number[t].charAt(k) + " ");
								}
								writer.println("0");
							}	
						}
					}
					
				}
			}
			
			//phan keo theo giua cac o
			for (int i = 0; i < numbers.length; i++) {
				if(numbers[i].length()>1){
					
				}
			}
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//phan dau file khai bao so luong bien va so luong menh de
		int x =n-1;
		int y = n-1;
		boolean p = false;
		for (; x >=0; x--) {
			for (; y >=0; y--) {
				if(array[x][y]!=-1){
					p=true;
					break;
				}
			}
			if(p)
				break;
		}
		try{
			BufferedReader reader = new BufferedReader(new FileReader(dir));
			number_clause = 0;
			while (reader.readLine() != null) 
				number_clause++;
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String s = "p cnf "+(x+1)+(y+1)+"9 "+number_clause;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(dir));
			dir = new File("cnf");
			writer = new PrintWriter(dir);
			writer.println(s);
			String line = "";
			while ((line=reader.readLine()) != null){
				writer.println(line);
			}
			reader.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
