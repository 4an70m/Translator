package rpn.builder;

import java.io.IOException;
import java.util.LinkedList;

import rpn.builder.util.PriorityTable;
import rpn.builder.util.StackWriterItem;
import rpn.builder.util.StackWriterTable;
import analyzer.lexical.automat.AutomatLexicalAnalyzer;
import analyzer.lexical.blank.LexicalAnalyzer;
import analyzer.syntax.automat.Stack;
import analyzer.syntax.blank.SyntaxAnalyzer;
import analyzer.syntax.descending.DescendingSyntaxAnalyzer;
import analyzer.util.output.ConstantTable;
import analyzer.util.output.IdentifierTable;
import analyzer.util.output.outputtable.OutputTable;
import analyzer.util.output.outputtable.OutputTableItem;

public class DeprecatedRPNBuilder extends RPNBuilder {

	private PriorityTable priorityTable;
	private Stack<String> stack;
	private StackWriterTable swTable;

	// rpn.offer(identifierTable.getItemByIndex(t
	// .getSpecializedNumber()));
	public DeprecatedRPNBuilder() throws IOException {
		this("test.pt");
	}

	public DeprecatedRPNBuilder(String priorityTablePath) throws IOException {
		super();
		priorityTable = new PriorityTable();
		stack = new Stack<>();
		rpn = new LinkedList<>();
		priorityTable.readTable(priorityTablePath);
		swTable = new StackWriterTable();
		swTable.readTable("test.wts");
	}

	@Override
	public void buildRPN() {
		OutputTable outTable = outputHandelr.getOutputTable();
		ConstantTable constantTable = outputHandelr.getConstantTable();
		IdentifierTable idTable = outputHandelr.getIdTable();
		int index = 0;

		boolean insideelse = false;
		// move to the body of a program (find begin)
		for (OutputTableItem t = outTable.get(index); t.getLexemeCode() != 3; t = outTable
				.get(++index))
			;
		index++;
		int m = -2;
		boolean elseF = false;
		Stack<Integer> mm = new Stack<>();
		int fGroup = -1;
		StackWriterItem item = null;
		Stack<String> callStack = new Stack<>();
		for (OutputTableItem t = outTable.get(index); index < outTable.size(); t = outTable
				.get(++index)) {
			if (t.getSubstring().equals("while")) {
				fGroup = 1;
				m += 2;
				mm.push(m);
				callStack.offer("while");
				rpn.offer("m" + m + ":");
			}
			if (t.getSubstring().equals("if")) {
				fGroup = 2;
				m += 2;
				callStack.offer("if");
				mm.push(m);
			}

			if (t.getLexemeCode() == 13 && !elseF) {
				if (stack.contains("if")) {
					fGroup = -1;
				}
			}

			if (t.getLexemeCode() == 11) {
				callStack.offer("else");
				fGroup = 2;
				elseF = true;
//				if(stack.contains("if"))
//					stack.remove(stack.lastIndexOf("if"));
			}

			item = swTable.getItemByIdAndFuncGroup(t.getLexemeCode(), fGroup);
			if (t.getLexemeCode() == 11) {
				if (stack.amountOfOccurance("while") < 1
						&& stack.amountOfOccurance("if") == 1 && mm.size() > 1) {
					index--;
					insideelse = true;
					item = swTable.getItemByIdAndFuncGroup(13, fGroup);
					fGroup = -1;
				}
			}
			if (t.getLexemeCode() == 11 && !elseF) {
				fGroup = -1;
			}
			if (item == null)
				continue;
			if (item.getItemId() == 36) {
				rpn.offer(constantTable.getItemByIndex(t.getSpecializedNumber()));
				continue;
			}
			if (item.getItemId() == 35) {
				rpn.offer(idTable.getItemByIndex(t.getSpecializedNumber()));
				continue;
			}

			if (stack.isNull() && item.hasStackRepresentation()) {
				stack.push(item.getStackRepresentation());
				continue;
			}

			if (!stack.isNull()) {

				int priorityItem = priorityTable.getPriorityByItem(		t
						.getSubstring());
				int priorityStack = priorityTable.getPriorityByItem(stack
						.peek());
				int compStackPrior = priorityTable.getComparisionByItem(stack
						.peek());
				int compItemPrior = priorityTable.getComparisionByItem(t
						.getSubstring());
				if (priorityStack >= priorityItem) {
					while (priorityStack >= priorityItem) {
						if (compStackPrior > compItemPrior) {
							if (item.hasStackRepresentation())
								stack.push(item.getStackRepresentation());
							break;
						}
						if (swTable.getItemByStackRep(stack.peek())
								.hasRPNRepresentation()) {

							if ((priorityStack == -2) && (priorityItem == -2)) {
								break;
							}
							rpn.offer(swTable.getItemByStackRep(stack.pop())
									.getRpnRepresentation());
						} else
							stack.pop();

						if (rpn.peekLast().contains("%")
								|| rpn.peekLast().contains("$")) {
							if (insideelse && mm.size() > 1) {
								mm.pop();
								m -= 2;
								insideelse = false;
							}
							int mmm = mm.peek();
							String temp = rpn.pollLast();
							temp = temp.replace("%", "m" + mmm);
							temp = temp.replace("$", "m" + (mmm + 1));
							rpn.offer(temp);

						}

						if (!stack.isNull()) {
							priorityStack = priorityTable
									.getPriorityByItem(stack.peek());
						} else {
							break;
						}
					}
				}

				if (item.hasStackRepresentation()) {
					stack.push(item.getStackRepresentation());
					continue;
				}
				if (item.hasRPNRepresentation()) {
					
					if(callStack.amountOfOccurance("else") > 1)
					{
						
					}
					
					rpn.offer(item.getRpnRepresentation());
					if (rpn.peekLast().contains("%")
							|| rpn.peekLast().contains("$")) {
						int mmm = mm.peek();
						String temp = rpn.pollLast();
						temp = temp.replace("%", "m" + mmm);
						temp = temp.replace("$", "m" + (mmm + 1));
						rpn.offer(temp);
						if (insideelse && mm.size() > 1) {
							mm.pop();
							insideelse = false;
							//if
						}
					}
				}
				if ((fGroup == 1 || fGroup == 2) && t.getLexemeCode() == 13) {
					if(insideelse)
						mm.pop();
					if (mm.isEmpty()) {
						fGroup = -1;
						if (stack.contains("if")) {
							if (stack.peek().equals("if")) {
								stack.pop();
							}
							elseF = false;
						}
					} else {
						if (stack.contains("if")) {
							fGroup = 2;
							mm.pop();
						}
						if (stack.contains("while")) {
							fGroup = -1;
							mm.pop();
						}
					}
				}
				if (!stack.isNull() && stack.peek().equals("if")
						&& (t.getSubstring().equals(";") /*|| t.getSubstring().equals("else")*/)) {
					stack.pop();
				}
			}

		}
	}

	public static void main(String[] args) throws IOException {

		LexicalAnalyzer la = new AutomatLexicalAnalyzer();
		la.readLexicAndClassTable();
		la.analyzeCode();

		SyntaxAnalyzer sa = new DescendingSyntaxAnalyzer(la.getOutputHandler());
		sa.analyzeSyntax();
/*
		RPNBuilder rpn = new PriorityRPNBuilder();
		rpn.setOutputHandelr(la.getOutputHandler());
		rpn.buildRPN();

		System.out.println(rpn.getRpn());
*/
	}
}
