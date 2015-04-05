package analyzer.syntax.automat;

import java.io.IOException;

import analyzer.syntax.automat.reader.state.StatesReader;
import analyzer.syntax.blank.SyntaxAnalyzer;
import analyzer.util.handler.ErrorHandler;
import analyzer.util.handler.OutputHandler;

public class AutomatSyntaxAnalyzer extends SyntaxAnalyzer{

	private OutputHandler outputHandler;
	private AutomatHandler aHandler;
	private StatesReader sTable;

	public AutomatSyntaxAnalyzer(OutputHandler outputHandler) {
		super(outputHandler);
		this.outputHandler = outputHandler;
		sTable = new StatesReader(); 
	}

	@Override
	public void analyzeSyntax() {
		if (!outputHandler.getCodeWasTranslated()) {
			ErrorHandler.error(99, 0);
			return;
		}
		try {
			sTable.readStates();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aHandler = new AutomatHandler(outputHandler, sTable);
		aHandler.analyzeSynatax();
		if (aHandler.getErrorCode() != 0)
			ErrorHandler
					.error(aHandler.getErrorCode(), aHandler.getErrorLine());
		else
		{
			System.out.println("Code was analyzed.");
			outputHandler.setCodeWasAnalyzed(true);
		}
	}

}
