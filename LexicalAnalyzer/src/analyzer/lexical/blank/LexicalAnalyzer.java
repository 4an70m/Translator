package analyzer.lexical.blank;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import analyzer.util.handler.OutputHandler;
import analyzer.util.output.ConstantTable;
import analyzer.util.output.IdentifierTable;
import analyzer.util.output.outputtable.OutputTable;

public abstract class LexicalAnalyzer {
	
	protected File sourceCode;
	protected BufferedReader reader;
	protected OutputHandler outputHandler;

	public void readLexicAndClassTable() {
		System.out.println("Not implemented in this version.");
	}
	
	public abstract void analyzeCode() throws IOException;
	
	public ConstantTable getConstantTable() {
		return outputHandler.getConstantTable();
	}

	public IdentifierTable getIdTable() {
		return outputHandler.getIdTable();
	}

	public OutputTable getOutputTable() {
		return outputHandler.getOutputTable();
	}

	public OutputHandler getOutputHandler() {
		return outputHandler;
	}
	
	public boolean isCodeTranslated() {
		return outputHandler.getCodeWasTranslated();
	}
}
