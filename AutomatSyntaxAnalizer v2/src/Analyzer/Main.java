package Analyzer;

import java.io.IOException;

import lab.StateReader.State;
import lab.StateReader.StatesTable;

import codereader.CodeReader;
import codereader.ErrorHandler;

public class Main {

	public static void main (String[] args) throws IOException
	{
		StatesTable st = new StatesTable();
		CodeReader cr = new CodeReader();
		st.readStates();
		for (State sts : st.getTable())
		{
			System.out.println(sts);
		}
		cr.readLexicAndClassTable();
		cr.readCode();
		
		AutomatHandler ah = new AutomatHandler(cr, st);
		if(cr.isCodeTranslated())
		{
			ah.read();
		if (ah.getErrorCode() != 0)
			ErrorHandler.error(ah.getErrorCode(), ah.getErrorLine());
		else
			System.out.println("Code is correct!");
		}
	}
}
