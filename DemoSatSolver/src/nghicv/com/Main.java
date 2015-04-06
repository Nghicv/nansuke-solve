package nghicv.com;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Main {
	public static void main(String[] args){
		ISolver mISolver=SolverFactory.newDefault();
		mISolver.setTimeout(3600);
		Reader mReader=new DimacsReader(mISolver);
		
		try {
			IProblem mIProblem=mReader.parseInstance("filecnf.cnf");
			
			if(mIProblem.isSatisfiable()){
				System.out.println(mReader.decode(mIProblem.model()));
			}else{
				System.out.println("unsat");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("loi tai day");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContradictionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
