package analyzer.lexical.blank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import analyzer.util.handler.OutputHandler;
import analyzer.util.output.ConstantTable;
import analyzer.util.output.IdentifierTable;
import analyzer.util.output.outputtable.OutputTable;
import analyzer.util.tablereader.classtable.ClasstableReader;
import analyzer.util.tablereader.lexic.LexicTableReader;

public abstract class LexicalAnalyzer {

	protected File sourceCode;
	protected BufferedReader reader;
	protected OutputHandler outputHandler;
	protected ClasstableReader clReader;
	protected LexicTableReader ltReader;
	protected int errorCode;

	public LexicalAnalyzer(String sourceCodePath, String lexicTablePath,
			String classTablePath) throws FileNotFoundException {
		sourceCode = new File(sourceCodePath);
		reader = new BufferedReader(new FileReader(sourceCode));
		clReader = new ClasstableReader(classTablePath);
		ltReader = new LexicTableReader(lexicTablePath);
		errorCode = 0;
		outputHandler = new OutputHandler();
	}

	public void readLexicAndClassTable() {
		try {
			clReader.readClasstable();
			ltReader.readLexicTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
