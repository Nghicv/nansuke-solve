package nghicv.com.test;

import java.util.Calendar;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;


public class Test {
	
	
	public static void main(String [] args){
		encode(19, 19, 7);
		decode("76");
		Calendar cal=Calendar.getInstance();
		long timeStart=cal.getTimeInMillis();
		long timeEnd=0;
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); 
        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance("data.txt");
            timeEnd=cal.getTimeInMillis();
            long time=timeEnd-timeStart;
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                System.out.println(reader.decode(problem.model()));
            } else {
                System.out.println("Unsatisfiable !");
            }
        } catch (Exception e) {
            System.out.println("Unsatisfiable");
            e.printStackTrace();
        }
	}
	
	public static String encode(int row, int col, int number){
		int sizecol=20;
		String s="";
		int var=row*sizecol+col;
		s=""+var+""+number;
		System.out.println(s);
		return s;
		
	}
	public static  void decode(String s){
		int sizeCol=4;
	
		char ch=s.charAt(s.length()-1);
		int number=Integer.parseInt(String.valueOf(ch));
		String sub=s.substring(0, s.length()-1);
		int position=Integer.parseInt(sub);
		int row =position/sizeCol;
		int col=position%sizeCol;
	//	Variable var=new Variable(row,col);
		System.out.print(""+row+" "+col+" "+number);
	}

}
