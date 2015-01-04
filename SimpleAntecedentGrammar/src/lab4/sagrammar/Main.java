package lab4.sagrammar;

import java.io.FileNotFoundException;
import java.io.IOException;

import lab4.grammarReader.GrammarReader;
import lab4.grammarReader.GrammarTable;

public class Main {
	
	
	public static void main (String[] args)
	{
		GrammarReader gReader;
		GrammarTable gTable;
		try {
			gReader = new GrammarReader();
			gReader.readGrammar();
			gTable = new GrammarTable(gReader);
			gTable.buildTable();
			gTable.outputToFile();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}