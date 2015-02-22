package analyzer.util.handler;

import java.io.IOException;
import java.util.Formatter;

import analyzer.lexical.state.classtable.ClasstableReader;
import analyzer.util.output.ConstantTable;
import analyzer.util.output.IdentifierTable;
import analyzer.util.output.outputtable.OutputTable;

public class OutputHandler {

	private OutputTable oTable;
	private IdentifierTable iTable;
	private ConstantTable cTable;
	private boolean isBody;
	private boolean codeWasTranslated;

	public OutputHandler() {
		oTable = new OutputTable();
		iTable = new IdentifierTable();
		cTable = new ConstantTable();
		isBody = false;
		codeWasTranslated = false;
	}

	public int output(int row, int result, String lexeme, int identifier,
			int constant, ClasstableReader clReader) {

		int specializedNumber = -1;

		if (result == -1) {
			String symb = String.valueOf(lexeme.charAt(0));
			String type = clReader.getVocabulary().checkType(symb);

			if (type.equals("L"))
				result = identifier;
			if (symb.equals("."))
				result = constant;
			if (symb.equals("E"))
				result = constant;
			if (type.equals("N"))
				result = constant;
		}
		boolean ok = true;
		if (isBody == true && result == identifier)
			ok = iTable.checkTableItem(lexeme);

		if (!ok) // undeclaired idetifier
			return 2;

		if (result == constant) {
			specializedNumber = cTable.addTableItem(lexeme);
		}
		if (result == identifier) {
			specializedNumber = iTable.addTableItem(lexeme);
		}

		oTable.addItem(row, lexeme, result, specializedNumber);

		return 0;

	}

	public void setBegin() {
		isBody = true;
	}

	public void outputToFile() {
		try {
			oTable.writeToFile();
			iTable.writeToFile();
			cTable.writeToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public OutputTable getOutputTable() {
		return oTable;
	}

	public IdentifierTable getIdTable() {
		return iTable;
	}

	public ConstantTable getConstantTable() {
		return cTable;
	}

	public boolean getCodeWasTranslated() {
		return codeWasTranslated;
	}

	public void setCodeWasTranslated(boolean codeWasTranslated) {
		this.codeWasTranslated = codeWasTranslated;
	}

}
