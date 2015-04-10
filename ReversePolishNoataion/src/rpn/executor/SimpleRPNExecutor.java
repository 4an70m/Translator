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

	private LinkedList<String> constrpn;
	private int globalIndex;
	private Scanner sc;

	@SuppressWarnings("unchecked")
	public SimpleRPNExecutor(OutputHandler oHandler, RPNBuilder rpnBuilder) {
		super(oHandler, rpnBuilder);
		constrpn = (LinkedList<String>) rpnBuilder.getRpn().clone();
		globalIndex = -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		if (!rpnBuilder.getRpnWasBuilt()) {
			return;
		}
		rpn = (LinkedList<String>) rpnBuilder.getRpn().clone();
		LinkedList<String> workStack = new LinkedList<>();
		// main processing loop
		for (int index = 0; index < rpn.size(); index++) {
			globalIndex++;
			String curItem = rpn.get(index);
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
		}
		System.out.println("Finished " + varMap);
	}

	private int writeOperation(int index, LinkedList<String> workStack) {
		String key = null;
		while (true) {
			index--;
			rpn.pollFirst();
			key = workStack.pollLast();
			Double val = varMap.get(key);
			if (val == null) {
				break;
			}
			System.out.println(key + " = " + val);
		}
		return index;
	}

	private int readOperation(int index, LinkedList<String> workStack) {
		if (sc == null)
			sc = new Scanner(System.in);
		try {
			String key = rpn.pollFirst();
			rpn.pollFirst();
			double value = 0;
			System.out.print(key + " : ");
			if (sc.hasNextDouble())
				value = sc.nextDouble();
			varMap.replace(key, value);
		} catch (NumberFormatException e) {
			index--;
		}
		index--;
		globalIndex++;
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
			result = value2 - value1;
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
		globalIndex -= 2;
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
		}
		rpn.add(index - 2, String.valueOf(result));
		index -= 3;
		globalIndex--;
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
		case "and": {
			index = logicCondition(index, "and", workStack);
			break;
		}
		case "or": {
			index = logicCondition(index, "or", workStack);
			break;
		}
		case "not": {
			index = logicCondition(index, "not", workStack);
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
		default: {
			if (curItem.contains("UPL")) {
				Boolean cond = Boolean.parseBoolean(workStack.pop());
				rpn.pollFirst();
				rpn.pollFirst();

				if (!cond) {
					// false
					String t = curItem.split("UPL")[0];
					int newIndex = labels.get(t + ":");
					if (globalIndex < newIndex) {
						// int diff = newIndex - globalIndex;
						while (globalIndex < newIndex) {
							globalIndex++;
							rpn.pollFirst();
						}
						return index - 2;
					} else {
						// true
						// ToDo: finish this block
						globalIndex = addListFromPos(newIndex);
					}

				} else {
					return index - 2;
				}
			}

			if (curItem.contains("BP")) {
				rpn.pollFirst();
				String t = curItem.split("BP")[0];
				int newIndex = labels.get(t + ":");
				if (globalIndex < newIndex) {
					// int diff = newIndex - globalIndex;
					while (globalIndex < newIndex) {
						globalIndex++;
						rpn.pollFirst();
					}
					return -1;
				} else {
					// ToDo: finish this block
					globalIndex = addListFromPos(newIndex);
					return -1;
				}
			}

			if (labels.containsKey(curItem)) {
				rpn.pollFirst();
				index--;
			}
		}
		}
		return index;
	}

	private int logicCondition(int index, String string,
			LinkedList<String> workStack) {
		boolean secondCond = Boolean.parseBoolean(workStack.pollFirst());
		rpn.pollFirst();
		boolean firstCond = false;
		boolean result = false;

		switch (string) {
		case "and":
			firstCond = Boolean.parseBoolean(workStack.pollFirst());
			rpn.pollFirst();
			result = firstCond && secondCond;
			break;

		case "or":
			firstCond = Boolean.parseBoolean(workStack.pollFirst());
			rpn.pollFirst();
			result = firstCond || secondCond;
			break;

		case "not":
			result = !secondCond;
			break;
		}
		rpn.pollFirst();
		rpn.add(index - 2, String.valueOf(result));
		index -= 3;
		globalIndex--;
		return index;
	}

	private int addListFromPos(int newIndex) {
		for (int i = globalIndex; i >= newIndex; i--) {
			rpn.push(constrpn.get(i));
		}
		return newIndex - 1;
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
	}
}
