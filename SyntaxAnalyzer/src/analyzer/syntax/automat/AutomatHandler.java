package analyzer.syntax.automat;

import analyzer.syntax.automat.reader.state.Rule;
import analyzer.syntax.automat.reader.state.State;
import analyzer.syntax.automat.reader.state.StatesReader;
import analyzer.util.handler.OutputHandler;
import analyzer.util.output.outputtable.OutputTable;

public class AutomatHandler {

	private StatesReader st;
	private int state;
	private OutputTable ot;
	private int lexemeNum;
	private Stack<Integer> stack;
	private int errorCode;
	private int errorLine;

	public AutomatHandler(OutputHandler outputHandler, StatesReader st) {
		this.st = st;
		ot = outputHandler.getOutputTable();
		stack = new Stack<>();
		errorCode = 0;
	}


	public void analyzeSynatax()
	{
		state = 11;
		lexemeNum = 0;
		while (true)
		{
			State currentState = st.getState(state);
			int checker = 0;
			for (Rule rl : currentState.getRules())
			{
				checker++;
				if (ot.get(lexemeNum).getLexemeCode() == rl.getLexeme())
				{
					lexemeNum++;
					state = rl.getNewState();
					if(state == 0 && rl.getStack() == 0)
						return;
					if (rl.getNewState() == -1 && rl.getStack() == -1)
					{
						state = stack.pop();
						break;
					}
					if (rl.getStack() != 0 && rl.getStack() > 0)
					{
						stack.push(rl.getStack());
					}
					break;
				}
				
				if (rl.getLexeme() == -1 && rl.getNewState() != -1)
				{
					state = rl.getNewState();
					if (rl.getStack() != 0)
					{
						stack.push(rl.getStack());
					}
					break;
				}
				
				if (rl.getLexeme() == -1 && rl.getNewState() == -1 && rl.getStack() == -1)
				{
					state = stack.pop();
					break;
				}
				if (checker == currentState.getRules().size())
				{
					errorCode = currentState.getRules().get(0).getError();
					errorLine = ot.get(lexemeNum).getLineNumber();
					return;
				}
			}
			
		}
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}
	public int getErrorLine()
	{
		return errorLine;
	}
	
}
