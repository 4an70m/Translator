package lab2.syntaxAnalyzer;

import java.util.LinkedList;

import codereader.OutputHandler;
import codereader.OutputTables.ConstantTable;
import codereader.OutputTables.IdentifierTable;
import codereader.OutputTables.OutputTable;

public class ProceduresHandler {

	OutputHandler outputHandler;
	OutputTable outputTable;
	ConstantTable constantTable;
	IdentifierTable idTable;
	LinkedList<Integer> errorCode;

	int lexemeNum;

	public ProceduresHandler(OutputHandler outputHandler) {
		this.outputHandler = outputHandler;
		this.outputTable = outputHandler.getOutputTable();
		this.constantTable = outputHandler.getConstantTable();
		this.idTable = outputHandler.getIdTable();
		lexemeNum = 0;
		errorCode = new LinkedList<>();
	}

	LinkedList<Integer> getErrorCode() {
		return errorCode;
	}

	public int pProgram() {
		if (outputTable.get(lexemeNum++).getLexemeCode() == 1) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 2) {
					if (pVarList() == 0) {
						if (outputTable.get(lexemeNum++).getLexemeCode() == 3) {
							if (pOperationList() == 0) {
								if (outputTable.get(lexemeNum++)
										.getLexemeCode() == 4) {
									return 0;
								}
								errorCode.push(116);
								return 116;
							}
						}
						errorCode.push(115);
						return 115;
					}
					errorCode.push(117);
					return 117;
				}
				errorCode.push(114);
				return 114;
			}
			errorCode.push(113);
			return 113;
		}
		errorCode.push(100);
		return 100;
	}

	public int pVarList() {

		if (pIdList() == 0) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 15) {
				if (pType() == 0) {
					if (outputTable.get(lexemeNum++).getLexemeCode() == 13) {
						if (pVarList() == 0)
							return 0;
					} else {
						lexemeNum--;
						return 0;
					}
				}
			}
		}
		errorCode.push(101);
		return 101;
	}

	public int pIdList() {

		if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 14) {
				if (pIdList() == 0) {
					return 0;
				}
			} else {
				lexemeNum--;
				return 0;
			}
		}
		errorCode.push(102);
		return 102;
	}

	public int pType() {

		if (outputTable.get(lexemeNum++).getLexemeCode() == 7) {
			return 0;
		}
		errorCode.push(103);
		return 103;
	}

	public int pOperationList() {
		if (outputTable.get(lexemeNum) != null) {
			if (pOperation() == 0) {
				if (outputTable.get(lexemeNum) != null
						&& outputTable.get(lexemeNum++).getLexemeCode() == 13) {
					if (pOperationList() == 0) {
						return 0;
					}
				}
				lexemeNum--;
				return 0;
			}
		}
		errorCode.push(104);
		return 104;
	}

	public int pOperation() {

		if (outputTable.get(lexemeNum++).getLexemeCode() == 5) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 33) {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {
					if (outputTable.get(lexemeNum++).getLexemeCode() == 34) {
						return 0;
					}
				}
			}
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 6) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 33) {
				if (pIdList() == 0) {
					if (outputTable.get(lexemeNum++).getLexemeCode() == 34) {
						return 0;
					}
				}
			}
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 8) {
			if (pLogExpr() == 0) {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 9) {
					if (pOperation() == 0) {
						return 0;
					}
				}
			}
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 10) {
			if (pLogExpr() == 0) {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 12) {
					if (pOperationList() == 0) {
						if (outputTable.get(lexemeNum++).getLexemeCode() == 11) {
							if (pOperation() == 0) {
								return 0;
							}
						}
					}
				}
			}
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 16) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
		}
		errorCode.push(105);
		return 105;
	}

	public int pLogExpr() {

		if (pLogTerm() == 0) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 30) {
				if (pLogTerm() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			return 0;
		}
		errorCode.push(106);
		return 106;
	}

	public int pLogTerm() {

		if (pLogMult() == 0) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 31) {
				if (pLogMult() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			return 0;
		}
		errorCode.push(107);
		return 107;
	}

	public int pLogMult() {

		if (outputTable.get(lexemeNum++).getLexemeCode() == 27) {
			if (pLogRatio() == 0) {
				return 0;
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 37) {
				if (pLogExpr() == 0) {
					if (outputTable.get(lexemeNum++).getLexemeCode() == 38) {
						return 0;
					}
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 25) {
				return 0;
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 36) {
				return 0;
			}
		}
		lexemeNum--;
		if (pLogRatio() == 0) {
			return 0;
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 37) {
			if (pLogExpr() == 0) {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 38) {
					return 0;
				}
			}
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 25) {
			return 0;
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 36) {
			return 0;
		}
		errorCode.push(108);
		return 108;
	}

	public int pLogRatio() {

		if (pMathExp() == 0) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 21) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 22) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 23) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 24) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 28) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 29) {
				if (pMathExp() == 0) {
					return 0;
				}
			}

		}
		errorCode.push(109);
		return 109;
	}

	public int pMathExp() {

		if (pMathTerm() == 0) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 17) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 18) {
				if (pMathExp() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			return 0;

		}
		errorCode.push(110);
		return 110;
	}

	public int pMathTerm() {

		if (pMathMult() == 0) {
			if (outputTable.get(lexemeNum++).getLexemeCode() == 20) {
				if (pMathMult() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			if (outputTable.get(lexemeNum++).getLexemeCode() == 19) {
				if (pMathMult() == 0) {
					return 0;
				}
			}
			lexemeNum--;
			return 0;
		}
		errorCode.push(111);
		return 111;
	}

	public int pMathMult() {

		if (outputTable.get(lexemeNum++).getLexemeCode() == 35) {
			return 0;
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 36) {
			return 0;
		}
		lexemeNum--;
		if (outputTable.get(lexemeNum++).getLexemeCode() == 33) {
			if (pMathExp() == 0) {
				if (outputTable.get(lexemeNum++).getLexemeCode() == 34) {
					return 0;
				}
			}
		}
		errorCode.push(112);
		return 112;
	}
}
