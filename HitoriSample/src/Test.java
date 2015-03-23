import java.io.FileNotFoundException;



public class Test {
	public static void main(String[] args) throws FileNotFoundException
	{
		DataTable  Table = new DataTable("data.txt");
		DataCNF fileCNF = new DataCNF(Table);
		fileCNF.makeCNF();
		//fileCNF.printStringCNF();
		fileCNF.writeFile("filecnf.txt");
		CNFSolver solver = new CNFSolver();
		solver.Solver("filecnf.txt");
		//Table.printTable();
		try {
			HitoriDisplay frame = new HitoriDisplay();
			frame.setTitle("Hitori");
			frame.setLocation(100, 100);
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
