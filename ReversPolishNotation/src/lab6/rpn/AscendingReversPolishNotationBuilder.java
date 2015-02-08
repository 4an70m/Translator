package lab6.rpn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import codereader.CodeReader;
import codereader.OutputTables.OutputTableItem;
import Analyzer.Stack;
import lab4.grammarReader.GrammarReader;
import lab4.grammarReader.GrammarTable;
import lab5.builder.OutputTableBuilder;

public class AscendingReversPolishNotationBuilder implements
		ReversPolishNotationBuilder {

	private GrammarTable gTable; // table of relations
	private String expression; // expression to parce
	private GrammarReader gReader; // reader for table
	private Stack<String> stack; // stack for parsing
	private Stack<String> stackOfRPN; // stack for parsed RPN expression
	private StringBuilder tempString; // string for parsing
	private int i = 0; // current step
	private int step = 0;
	private CodeReader codeReader;
	private boolean isFinished = false;
	private Stack<String> stackTemp;
	private OutputTableBuilder builder;

	public AscendingReversPolishNotationBuilder() {
		try {
			gReader = new GrammarReader();
			gReader.readGrammar();
			gTable = new GrammarTable(gReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stack = new Stack<>();
		stack.push("#");
		stackOfRPN = new Stack<>();
		stackTemp = new Stack<>();
		try {
			builder = new OutputTableBuilder();
			codeReader = new CodeReader();
			codeReader.readLexicAndClassTable();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setExpression(String expression) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					"test.fas")));
			this.expression = expression;
			bw.write(expression);
			bw.close();
			codeReader.readCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String parceExpression() {
		i = 0;
		step = 0;
		isFinished = false;
		gTable.buildTable();
		tempString = new StringBuilder();

		try {
			builder.startWriting();
			builder.writeHeadlines("N Stack Sign Current_Symbol RPN");

			while (!isFinished) {
				parceNext(codeReader.getOutputTable().get(i));
				i++;
				step++;
			}

			builder.finishWriting();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stackOfRPN.toString();
	}

	private void parceNext(OutputTableItem item) throws IOException {
		String curLex = null;
		if (item == null) {
			curLex = "#";
		}
		if (curLex == null) {
			curLex = item.getSubstring();
			if (item.getLexemeCode() == 35) {
				stackTemp.push(curLex);
				curLex = "id";
			}
			if (item.getLexemeCode() == 36) {
				stackTemp.push(curLex);
				curLex = "const";
			}
		}
		if (stack.peek().equals("<â1>") && curLex.equals("#")) {
			String[] tempWrite = { String.valueOf(step), stack.toString(), ">", curLex, stackOfRPN.toString() };
			builder.writeLine(tempWrite);
			isFinished = true;
			return;
		}
		switch (gTable.getSign(stack.peek(), curLex)) {
		case '<': {
			String[] tempWrite = { String.valueOf(step), stack.toString(), "<", curLex, stackOfRPN.toString() };
			builder.writeLine(tempWrite);
			stack.push(curLex);
			break;
		}
		case '=': {
			String[] tempWrite = { String.valueOf(step), stack.toString(), "=", curLex, stackOfRPN.toString() };
			builder.writeLine(tempWrite);
			stack.push(curLex);
			break;
		}
		case '>': {
			String[] tempWrite = { String.valueOf(step), stack.toString(), ">", curLex, stackOfRPN.toString() };
			builder.writeLine(tempWrite);
			while (gTable.getSign(stack.peek(), curLex.trim()) != '<') {
				tempString.append((curLex = stack.pop()) + " ");
			}
			// formation of the rule to check out within grammar table
			String tempRuleParams = reverseWords(tempString.toString().trim());
			String tempRule = gTable.findRule(tempRuleParams);
			if (tempRule != null) {
				stack.push(tempRule);
				if (ruleForRPN(tempRuleParams) != "-1") {
					String ruleRPN = ruleForRPN(tempRuleParams);
					if (ruleRPN.equals("id")) {
						ruleRPN = stackTemp.pop();
					}
					if (ruleRPN.equals("const")) {
						ruleRPN = stackTemp.pop();
					}
					stackOfRPN.push(ruleRPN);
				}
			} else {
				System.err.println("Error parcing expression.");
				return;
			}
			tempString = new StringBuilder();
			i--;
			break;
		}
		}
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

	private String ruleForRPN(String param) {
		switch (param) {
		case "<â> + <ò1>": {
			return "+";
		}
		case "<â> - <ò1>": {
			return "-";
		}
		case "<ò> * <ì>": {
			return "*";
		}
		case "<ò> / <ì>": {
			return "/";
		}
		case "id": {
			return "id";
		}
		case "const": {
			return "const";
		}
		case "- <ò1>": {
			return "@";
		}
		default:
			return "-1";
		}

	}
}
