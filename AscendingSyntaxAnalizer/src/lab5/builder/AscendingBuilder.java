package lab5.builder;

import java.io.IOException;

import lab.StateReader.StatesTable;
import lab4.grammarReader.GrammarReader;
import lab4.grammarReader.GrammarTable;
import Analyzer.Stack;
import codereader.CodeReader;
import codereader.ErrorHandler;
import codereader.OutputTables.OutputTableItem;

public class AscendingBuilder {

	private static final String FINISHED_KEYWORD = "finished";
	private Stack<String> stack;
	private CodeReader codeReader;
	private GrammarTable grammarTable;
	private StringBuilder tempString;
	private OutputTableItem currentItem;
	private int i; // current syntax analyzer position
	private OutputTableBuilder outputTableBuilder;
	private int step; // steps for output

	public AscendingBuilder() throws IOException {
		stack = new Stack<>();
		stack.push("#");
		codeReader = new CodeReader();
		i = 0;
		GrammarReader grammarReader = new GrammarReader();
		grammarReader.readGrammar();
		grammarTable = new GrammarTable(grammarReader);
		tempString = new StringBuilder();
		outputTableBuilder = new OutputTableBuilder();
		int step = 0;
	}

	/**
	 * <br>
	 * <i>public void readCode()</i> <br>
	 * <p>
	 * Reads code from .classtabel, .lexictable files and then reads and
	 * translates it into an OutputTable.
	 * </p>
	 * 
	 * @throws IOException
	 */
	public void readCode() throws IOException {
		codeReader.readLexicAndClassTable();
		codeReader.readCode();
	}

	/**
	 * <br>
	 * <i>public void buildTable()</i>
	 * <p>
	 * Builds a table of antecedent grammar with relations between elements. Is
	 * needed to proceed with syntax analyze.
	 * </p>
	 * 
	 * @throws IOException
	 */
	public void buildTable() throws IOException {
		grammarTable.buildTable();
	}

	/**
	 * <br>
	 * <i>public void restart()</i> <br>
	 * <p>
	 * Restarts syntax analyze from the beginning. Sets i = 0. Also resets steps
	 * = 0 for output.
	 * </p>
	 */
	public void restart() {
		i = 0;
		step = 0;
	}

	/**
	 * <br>
	 * <i>public OutputTableItem readNext()</i> <br>
	 * <p>
	 * Performs a translation. Reads current lexeme at position i from the
	 * Outputtable of CodeReader. Finds its relation with top of the stack
	 * through a relation table. Performs necessary reeling of lexeme, according
	 * to the rules.
	 * <p>
	 * 
	 * @return <strong>null</strong> - if step was performed <br>
	 *         <strong>OutputTableItem</strong> - if there was an error
	 * @throws IOException
	 */
	private OutputTableItem readNext() throws IOException {
		String currentLexeme = null;
		OutputTableItem currentItem = codeReader.getOutputTable().get(i);
		if (currentItem != null) {

			// if current lexeme is id or const
			if (currentItem.getLexemeCode() == 35) { // identifier
				currentLexeme = "id";
			} else if (currentItem.getLexemeCode() == 36) { // constant
				currentLexeme = "const";
			} else {
				currentLexeme = currentItem.getSubstring().trim(); // any other
																	// lexeme
			}
		} else {
			currentLexeme = "#";
			if (stack.peek().equals("<прог>")) {
				String stackS = stack.toString();
				String[] tempWrite = { String.valueOf(step), stackS, ">",
						currentLexeme };
				outputTableBuilder.writeLine(tempWrite);
				return new OutputTableItem(-1, FINISHED_KEYWORD, -1, -1);
			}
		}

		// finds a relation in a table between current top element of stack
		// and currently read lexeme 'currentLexeme'
		switch (grammarTable.getSign(stack.peek(), currentLexeme)) {
		case '<': {

			String stackS = stack.toString();
			String[] tempWrite = { String.valueOf(step), stackS, "<",
					currentLexeme };
			outputTableBuilder.writeLine(tempWrite);

			stack.push(currentLexeme);
			break;
		}
		case '=': {

			String stackS = stack.toString();
			String[] tempWrite = { String.valueOf(step), stackS, "=",
					currentLexeme };
			outputTableBuilder.writeLine(tempWrite);

			stack.push(currentLexeme);
			break;
		}
		case '>': {

			String stackS = stack.toString();
			String[] tempWrite = { String.valueOf(step), stackS, ">",
					currentLexeme };
			outputTableBuilder.writeLine(tempWrite);

			// reels up lexemes, while there is a rule for this operation

			while (grammarTable.getSign(stack.peek(), currentLexeme.trim()) != '<') {
				tempString.append(currentLexeme = stack.pop() + " ");
			}
			// formation of the rule to check out within grammar table
			String tempRuleParams = reverseWords(tempString.toString().trim());
			String tempRule = grammarTable.findRule(tempRuleParams);
			if (tempRule != null) {
				stack.push(tempRule);
			} else {
				return currentItem;
			}
			tempString = new StringBuilder();
			i--;
			break;
		}
		default:
			return currentItem;
		}
		i++;
		return null;
	}

	/**
	 * <br>
	 * <i>private String reverseWords(String line)<i> <br>
	 * <p>
	 * Additional function, which reverse the order of words in a String object.
	 * </p>
	 * 
	 * @param line
	 *            - input String
	 * @return result - String with reversed order of words
	 */
	private String reverseWords(String line) {
		String[] words = line.split(" ");
		StringBuilder result = new StringBuilder();
		for (int i = words.length - 1; i >= 0; i--) {
			result.append(words[i].trim()).append(" ");
		}
		return result.toString().trim();
	}

	/**
	 * <br>
	 * <i>public void initialize()</i> <br>
	 * <p>
	 * Performs readCode() and buildTable()
	 * </p>
	 * </p>
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		readCode();
		buildTable();
	}

	/**
	 * <br>
	 * <i>public void analyzeSynatax()</i> <br>
	 * <p>
	 * Starts syntax analysis. Throws error if anything gone wrong. Outputs
	 * result to stack.html.
	 * </p>
	 * 
	 * @throws IOException
	 */
	public void analyzeSynatax() throws IOException {
		if (!codeReader.isCodeTranslated()) {
			return;
		}

		// we use states table from AutomatSyntaxAnalyzer v2
		// to avoid tons of same code
		// TODO - if you have free time - rewrite the table for this output
		StatesTable sTable = new StatesTable();
		sTable.readStates();

		outputTableBuilder.startWriting();
		outputTableBuilder.writeHeadlines("N Stack Sign Current_Symbol");
		OutputTableItem currentItem = null;
		// error and finish will return some item
		// while normal code - won't
		while ((currentItem = readNext()) == null) {
			step++;
		}
		if (currentItem.getSubstring().equals(FINISHED_KEYWORD)) {
			restart();
			System.out.println("Code is correct!");
		} else {
			int errorCode = sTable.getErrorByLexemeCode(currentItem
					.getLexemeCode());
			ErrorHandler.error(errorCode, currentItem.getLineNumber());
		}
		outputTableBuilder.finishWriting();
	}

}
