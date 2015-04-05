package rpn.executor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import rpn.builder.PriorityRPNBuilder;
import rpn.builder.RPNBuilder;
import analyzer.lexical.automat.AutomatLexicalAnalyzer;
import analyzer.lexical.blank.LexicalAnalyzer;
import analyzer.syntax.blank.SyntaxAnalyzer;
import analyzer.syntax.descending.DescendingSyntaxAnalyzer;
import analyzer.util.handler.OutputHandler;

public class SimpleRPNExecutor extends RPNExecutor {

	public SimpleRPNExecutor(OutputHandler oHandler, RPNBuilder rpnBuilder) {
		super(oHandler, rpnBuilder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		if (!rpnBuilder.getRpnWasBuilt()) {
			return;
		}
		rpn = (LinkedList<String>) rpnBuilder.getRpn().clone();
		LinkedList<String> cycleStack = new LinkedList<String>();
		LinkedList<String> workStack = new LinkedList<>();
		boolean insideCycle = false;
		// main processing loop
		for (int index = 0; index < rpn.size(); index++) {

			String curItem = rpn.get(index);
			/*
			 * if (curItem.matches("m[0-9]*:")) { String stoppingString =
			 * curItem.substring(0, curItem.length() - 1); rpn.pollFirst(); for
			 * (int i = index; i < rpn.size(); i++) {
			 * cycleStack.add(rpn.get(i)); if
			 * (cycleStack.peekFirst().contains(stoppingString)) { break; } }
			 * index--; continue; }
			 */
			if (idTable.isIdentifier(curItem)) {
				workStack.push(curItem);
				continue;
			}

			if (isNumber(curItem)) {
				workStack.push(curItem);
				continue;
			}

			if (isBoolean(curItem)) {
				workStack.push(curItem);
				continue;
			}

			index = mainSwitch(index, curItem, workStack);

			if (curItem.contains("UPL")) {
				boolean cond = Boolean.parseBoolean(workStack.pollFirst());
				rpn.pollFirst();
				String label = curItem.substring(0, curItem.indexOf("UPL"))
						+ ":";
				if (cond) {
					rpn.pollFirst();
					index-=2;
					continue;
				} else {
					curItem = rpn.pollFirst();
					while (!curItem.contains(label)) {
						curItem = rpn.pollFirst();
						index++;
					}
					continue;
				}
			}
			

			if (curItem.contains("BP")) {
				boolean cond = Boolean.parseBoolean(workStack.pollFirst());
				rpn.pollFirst();
				String label = curItem.substring(0, curItem.indexOf("UPL"))
						+ ":";
				if (cond) {
					rpn.pollFirst();
					index-=2;
					continue;
				} else {
					curItem = rpn.pollFirst();
					while (!curItem.contains(label)) {
						curItem = rpn.pollFirst();
						index++;
					}
					continue;
				}
			}
		}
		System.out.println("Finished " + varMap);
	}

	private int writeOperation(int index, LinkedList<String> workStack) {
		String key = null;
		int minusIndex = 0;
		while (true) {
			key = rpn.pollFirst();
			minusIndex++;
			Double val = varMap.get(key);
			if (val == null) {
				break;
			}
			System.out.println(key + " = " + val);
			workStack.pollFirst();
		}
		index -= minusIndex;
		return index;
	}

	private int readOperation(int index, LinkedList<String> workStack) {
		try (Scanner sc = new Scanner(System.in)) {
			String key = rpn.pollFirst();
			rpn.pollFirst();
			double value = 0;
			System.out.print(key + " : ");
			value = sc.nextDouble();
			varMap.replace(key, value);
		} catch (NumberFormatException e) {
			index--;
		}
		index--;
		return index;
	}

	private int assignment(int index, LinkedList<String> workStack) {
		rpn.remove(index);
		rpn.remove(index - 1);
		rpn.remove(index - 2);

		String id = workStack.pollLast();
		double value = getDoubleValue(workStack.pop());
		varMap.replace(id, value);
		index -= 3;
		return index;
	}

	private int arythmeticExpr(int index, String string,
			LinkedList<String> workStack) {
		rpn.remove(index);
		rpn.remove(index - 1);
		rpn.remove(index - 2);

		double value1 = getDoubleValue(workStack.pollFirst());
		double value2 = getDoubleValue(workStack.pollFirst());
		double result = 0;
		switch (string) {
		case "+":
			result = value1 + value2;
			break;
		case "-":
			result = value1 - value2;
			break;
		case "*":
			result = value1 * value2;
			break;
		case "/":
			result = value2 / value1;
			break;
		}

		rpn.add(index - 2, String.valueOf(result));
		workStack.pop();
		index -= 4;

		return index;
	}

	private int logicExpr(int index, String string, LinkedList<String> workStack) {
		rpn.remove(index);
		rpn.remove(index - 1);
		rpn.remove(index - 2);

		double value2 = getDoubleValue(workStack.pollFirst());
		double value1 = getDoubleValue(workStack.pollFirst());
		boolean result = false;
		switch (string) {
		case "<":
			result = value1 < value2;
			break;
		case ">":
			result = value1 > value2;
			break;
		case "<=":
			result = value1 <= value2;
			break;
		case ">=":
			result = value2 >= value1;
			break;
		case "==":
			result = value2 == value1;
			break;
		case "!=":
			result = value2 != value1;
			break;
		/*
		 * case "and": result = (value2 == true) && (value1 == true); break;
		 * 
		 * case "or": result = value2 >= value1; break;
		 * 
		 * case "not": result = value2 >= value1; break;
		 */
		}
		rpn.add(index - 2, String.valueOf(result));
		index -= 3;

		return index;
	}

	private double getDoubleValue(String item) {
		try {
			return Double.parseDouble(item);
		} catch (NumberFormatException e) {
			return Double.valueOf(varMap.get(item));
		}
	}

	private static boolean isNumber(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static boolean isBoolean(String bool) {

		if (bool.equals("true") || bool.equals("false"))
			return true;
		return false;

	}

	private int mainSwitch(int index, String curItem,
			LinkedList<String> workStack) {
		switch (curItem) {
		case "=": {
			index = assignment(index, workStack);
			break;
		}
		case "+": {
			index = arythmeticExpr(index, "+", workStack);
			break;
		}
		case "-": {
			index = arythmeticExpr(index, "-", workStack);
			break;
		}
		case "*": {
			index = arythmeticExpr(index, "*", workStack);
			break;
		}
		case "/": {
			index = arythmeticExpr(index, "/", workStack);
			break;
		}
		case "<": {
			index = logicExpr(index, "<", workStack);
			break;
		}
		case ">": {
			index = logicExpr(index, ">", workStack);
			break;
		}
		case "!=": {
			index = logicExpr(index, "!=", workStack);
			break;
		}
		case "==": {
			index = logicExpr(index, "==", workStack);
			break;
		}
		case "<=": {
			index = logicExpr(index, "<=", workStack);
			break;
		}
		case ">=": {
			index = logicExpr(index, ">=", workStack);
			break;
		}
		case "WRITE": {
			index = writeOperation(index, workStack);
			break;
		}

		case "READ": {
			index = readOperation(index, workStack);
			break;
		}
		}
		return index;
	}

	public static void main(String[] args) throws IOException {
		LexicalAnalyzer la = new AutomatLexicalAnalyzer();
		la.readLexicAndClassTable();
		la.analyzeCode();

		SyntaxAnalyzer sa = new DescendingSyntaxAnalyzer(la.getOutputHandler());
		sa.analyzeSyntax();

		RPNBuilder rpn = new PriorityRPNBuilder();
		rpn.setOutputHandelr(la.getOutputHandler());
		rpn.buildRPN();

		System.out.println(rpn.getRpn());

		RPNExecutor exec = new SimpleRPNExecutor(la.getOutputHandler(), rpn);
		exec.execute();

		System.out.println("m1223:".matches("m[0-9]*:"));
	}
}
