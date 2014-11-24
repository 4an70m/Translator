package lab2.syntaxAnalyzer;

import java.util.LinkedList;

import codereader.OutputHandler;
import codereader.OutputTables.ConstantTable;
import codereader.OutputTables.IdentifierTable;
import codereader.OutputTables.OutputTable;

public class AutomatHandler {

	OutputHandler outputHandler;
	OutputTable outputTable;
	Stack<Integer> stack;

	int errorCode;
	int lexemeNum;
	int state;

	public AutomatHandler(OutputHandler outputHandler) {
		this.outputHandler = outputHandler;
		outputTable = outputHandler.getOutputTable();
		stack = new Stack<>();
		errorCode = 0;
		lexemeNum = 0;
		state = 0;
	}

	public void runMain() {
		state = 11;
		while (true) {
			switch (state) {
			case 11: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 1) {// program
					state = 12;
					break;
				}
				errorCode = 0;
				return;
			}
			case 12: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {// id
					state = 13;
					break;
				}
				errorCode = 0;
				return;
			}
			case 13: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 2) {// var
					state = 14;
					break;
				}
				errorCode = 0;
				return;
			}
			case 14: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {// id
					state = 15;
					break;
				}
				errorCode = 0;
				return;
			}
			case 15: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 15) {// :
					state = 16;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 14) {// ,
					state = 14;
					break;
				}
				errorCode = 150;
				return;
			}
			case 16: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 7) {// real
					state = 17;
					break;
				}
				errorCode = 150;
				return;
			}
			case 17: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 13) {// ;
					state = 14;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 3) {// begin 
					stack.push(18);
					this.runOperation();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 150;
				return;
			}
			case 18: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 13) {// ;
					stack.push(18);
					this.runOperation();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 4) {// end.
					return;
				}
				errorCode = 150;
				return;
			}
			}
		}
	}

	private void runOperation() {

		state = 21;

		while (true) {
			switch (state) {
			case 21: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 5) {// read
					state = 22;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 6) {// write
					state = 25;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 8) {// while
					stack.push(28);
					this.runLogicExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 10) {// if
					stack.push(18);
					this.runLogicExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {// id
					state = 32;
					break;
				}
				errorCode = 0;
				return;
			}
			case 22: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 33) {// (
					state = 23;
					break;
				}
				errorCode = 0;
				return;
			}
			case 23: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {// id
					state = 24;
					break;
				}
				errorCode = 0;
				return;
			}
			case 24: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 34) {// )
					return;
				}
				errorCode = 0;
				return;
			}
			case 25: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 33) {// (
					state = 26;
					break;
				}
				errorCode = 150;
				return;
			}
			case 26: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {// id
					state = 27;
					break;
				}
				errorCode = 150;
				return;
			}
			case 27: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 14) {// ,
					state = 26;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 34) {// )
					return;
				}
				errorCode = 150;
				return;
			}
			case 28: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 9) {// do
					stack.push(29);
					this.runOperation();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 150;
				return;
			}
			case 29: {
				return;
			}
			case 30: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 30) {// then
					stack.push(31);
					this.runOperation();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 150;
				return;

			}
			case 31: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 15) {// ;
					stack.push(31);
					this.runOperation();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 11) {// else
					stack.push(29);
					this.runOperation();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 150;
				return;

			}

			case 32: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 16) {// =
					stack.push(29);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 150;
				return;

			}

			}
		}

	}

	private void runLogicExpr() {

		state = 131;

		while (true) {
			switch (state) {
			case 131: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 27) {// not
					state = 131;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 37) {// [
					stack.push(132);
					this.runLogicExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 25) {// true
					state = 133;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 26) {// false
					state = 133;
					break;
				}
				lexemeNum--;

				stack.push(134);
				this.runMathExpr();
				state = stack.pop();
				if (errorCode != 0)
					return;
				break;
			}
			case 132: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 38) {// ]
					state = 133;
					break;
				}
				errorCode = 0;
				return;
			}
			case 133: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 30) {// or
					state = 131;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 31) {// and
					state = 131;
					break;
				}
				lexemeNum--;
				return;
			}

			case 134: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 21) {// >
					stack.push(133);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 22) {// <
					stack.push(133);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 23) {// ==
					stack.push(133);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 24) {// !=
					stack.push(133);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 28) {// >=
					stack.push(133);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 29) {// <=
					stack.push(133);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 0;
				return;
			}
			}
		}

	}

	private void runMathExpr() {

		state = 141;

		while (true) {
			switch (state) {
			case 141: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {// id
					state = 143;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 36) {// cons
					state = 143;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 33) {// (
					stack.push(142);
					this.runMathExpr();
					state = stack.pop();
					if (errorCode != 0)
						return;
					break;
				}
				errorCode = 0;
				return;
			}
			case 142: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 34) {// )
					state = 143;
					break;
				}
				errorCode = 0;
				return;
			}
			case 143: {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 17) {// +
					state = 141;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 18) {// -
					state = 141;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 20) {// *
					state = 141;
					break;
				}
				lexemeNum--;
				if (outputTable.get(lexemeNum++).getLexemeCode() == 19) {// /
					state = 141;
					break;
				}
				lexemeNum--;
				return;
			}
			}
		}

	}

}
