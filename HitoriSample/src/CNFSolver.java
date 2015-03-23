import java.io.*;

import org.sat4j.*;
import org.sat4j.minisat.*;
import org.sat4j.reader.*;
import org.sat4j.reader.Reader;
import org.sat4j.specs.*;

public class CNFSolver {
	
	public CNFSolver(){
		
	}
	
	public void Solver(String fileName)
	{
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); 
        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance(fileName);
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
}