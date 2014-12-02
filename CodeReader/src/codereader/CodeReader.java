package codereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import codereader.OutputTables.ConstantTable;
import codereader.OutputTables.IdentifierTable;
import codereader.OutputTables.OutputTable;
import classreader.*;
import lexictablereader.*;

public class CodeReader {

	private File sourceCode;
	private BufferedReader reader;
	private ClasstableReader clReader;
	private LexicTableReader ltReader;
	private int errorCode;
	private OutputHandler outputHandler;

	public CodeReader(String sourceCodePath) throws FileNotFoundException {
		sourceCode = new File(sourceCodePath);
		reader = new BufferedReader(new FileReader(sourceCode));
		clReader = new ClasstableReader();
		ltReader = new LexicTableReader();
		errorCode = 0;
		outputHandler = new OutputHandler();
	}

	public CodeReader(String sourceCodePath, String lexicTablePath,
			String classTablePath) throws FileNotFoundException {
		sourceCode = new File(sourceCodePath);
		reader = new BufferedReader(new FileReader(sourceCode));
		clReader = new ClasstableReader(classTablePath);
		ltReader = new LexicTableReader(lexicTablePath);
		errorCode = 0;
		outputHandler = new OutputHandler();
	}

	public CodeReader() throws FileNotFoundException {
		this("test.fas");
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

	public void readCode() throws IOException {
		
		outputHandler.setCodeWasTranslated(false);

		int row = 1;
		String sline = null;
		while ((sline = reader.readLine()) != null) {
			String line = sline.trim() + " ";
			int state = 1;
			StringBuilder lexeme = new StringBuilder();

			for (int i = 0; i < line.length(); i++) {

				String symb = String.valueOf(line.charAt(i));

				switch (state) {
				case 1: {

					String classType = clReader.getVocabulary().checkType(symb);
					lexeme.append(symb);
					if (classType.equals("L")) {
						state = 2;
						break;
					}
					if (classType.equals("N")) {
						state = 3;
						break;
					}
					if (classType.equals("OP")) {
						state = 0;
						i--;
						break;
					}
					if (classType.equals("!")) {
						state = 8;
						break;
					}
					if (classType.equals("=")) {
						state = 7;
						break;
					}
					if (classType.equals(">")) {
						state = 7;
						break;
					}
					if (classType.equals("<")) {
						state = 7;
						break;
					}
					if (classType.equals(".")) {
						state = 4;
						break;
					}
					if (classType.equals("E")) {
						state = 5;
						break;
					}

					state = -1;
					// error
					errorCode = 1;
					break;
				}
				case 2: {

					String classType = clReader.getVocabulary().checkType(symb);
					lexeme.append(symb);
					if (classType.equals("L")) {
						state = 2;
						break;
					}
					if (classType.equals("N")) {
						state = 2;
						break;
					}
					if (classType.equals("OP")) {
						state = 0;
						lexeme.deleteCharAt(lexeme.length() - 1);
						i -= 2;
						break;
					}
					if (classType.equals(".")) {
						state = 2;
						break;
					}

					state = -1;
					// error
					errorCode = 1;

					break;
				}
				case 3: {

					String classType = clReader.getVocabulary().checkType(symb);
					lexeme.append(symb);
					if (classType.equals("N")) {
						state = 3;
						break;
					}
					if (classType.equals("OP")) {
						state = 0;
						lexeme.deleteCharAt(lexeme.length() - 1);
						i -= 2;
						break;
					}
					if (classType.equals(".")) {
						state = 4;
						break;
					}
					if (classType.equals("E")) {
						state = 5;
						break;
					}

					state = -1;
					// error
					errorCode = 1;

					break;
				}
				case 4: {
					String classType = clReader.getVocabulary().checkType(symb);
					lexeme.append(symb);
					if (classType.equals("N")) {
						state = 4;
						break;
					}
					if (classType.equals("L")) {
						if (symb.equals("E")) {
							state = 5;
							break;
						}
					}

					state = -1;
					// error
					errorCode = 1;
					break;
				}
				case 5: {
					String classType = clReader.getVocabulary().checkType(symb);
					lexeme.append(symb);
					if (symb.equals("+")) {
						state = 6;
						break;
					}
					if (classType.equals("-")) {
						state = 6;
						break;
					}
					if (classType.equals("N")) {
						state = 6;
						break;
					}

					state = -1;
					// error
					errorCode = 1;
					break;
				}
				case 6: {
					String classType = clReader.getVocabulary().checkType(symb);
					lexeme.append(symb);
					if (classType.equals("N")) {
						state = 6;
						break;
					}
					if (classType.equals("OP")) {
						state = 0;
						lexeme.deleteCharAt(lexeme.length() - 1);
						i -= 2;
						break;
					}

					state = -1;
					// error
					errorCode = 1;
					break;
				}
				case 7: {
					lexeme.append(symb);

					if (symb.equals("=")) {
						i--;
						state = 0;
						break;
					} else {
						i--;
						state = 0;
						break;
					}
				}

				case 8: {
					lexeme.append(symb);

					if (symb.equals("=")) {
						i--;
						state = 0;
						break;
					} else {
						i--;
						state = -1;
						// error
						errorCode = 1;
						break;
					}
				}

				case -1: {
					// error state
					ErrorHandler.error(errorCode, row);
					return;
				}
				case 0: {

					// write down info about found lexeme

					int result = ltReader.checkLexeme(lexeme.toString().trim()
							.equals("") ? " " : lexeme.toString().trim());

					if (lexeme.toString().trim().equals("begin"))
						outputHandler.setBegin();

					if (result == ltReader.findSpace())
						lexeme = new StringBuilder("<space>");
					else
						errorCode = outputHandler.output(row, result,
								lexeme.toString(), ltReader.findIdentifier(),
								ltReader.findConstant(), clReader);

					if (errorCode != 0) {
						ErrorHandler.error(errorCode, row);
						return;
					}

					lexeme = new StringBuilder();
					state = 1;

					// exit from iteration
					break;
				}
				default: {
					// unknown exception
					ErrorHandler.error(-1, row);
				}
				}

			}
			row++;

		}
		System.out.println("Process successfully finished.");
		System.out.println("Writing to file...");

		outputHandler.outputToFile();

		System.out.println("Finished.");
		outputHandler.setCodeWasTranslated(true);
	}

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

}
