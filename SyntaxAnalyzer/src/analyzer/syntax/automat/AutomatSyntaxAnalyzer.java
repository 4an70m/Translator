package analyzer.syntax.automat;

import java.io.IOException;

import analyzer.syntax.automat.stateReader.StatesTable;
import analyzer.syntax.blank.SyntaxAnalyzer;
import analyzer.util.handler.ErrorHandler;
import analyzer.util.handler.OutputHandler;

public class AutomatSyntaxAnalyzer extends SyntaxAnalyzer{

	private OutputHandler outputHandler;
	private AutomatHandler aHandler;
	private StatesTable sTable;

	public AutomatSyntaxAnalyzer(OutputHandler outputHandler) {
		super(outputHandler);
		this.outputHandler = outputHandler;
		sTable = new StatesTable(); 
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
			System.out.println("Finished, yay!");
	}

}
