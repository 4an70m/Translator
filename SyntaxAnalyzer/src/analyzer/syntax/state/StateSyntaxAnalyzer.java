package analyzer.syntax.state;

import analyzer.syntax.blank.SyntaxAnalyzer;
import analyzer.util.handler.ErrorHandler;
import analyzer.util.handler.OutputHandler;

public class StateSyntaxAnalyzer extends SyntaxAnalyzer {

	private OutputHandler outputHandler;
	private StateHandler aHandler;

	public StateSyntaxAnalyzer(OutputHandler outputHandler) {
		super(outputHandler);
		this.outputHandler = outputHandler;
	}

	@Override
	public void analyzeSyntax() {
		if (!outputHandler.getCodeWasTranslated()) {
			ErrorHandler.error(99, 0);
			return;
		}
		aHandler = new StateHandler(outputHandler);
		aHandler.analyzeSynatax();
		if (aHandler.getErrorCode() != 0) {
			ErrorHandler
					.error(aHandler.getErrorCode(), aHandler.getErrorLine());
		} else {
			outputHandler.setCodeWasAnalyzed(true);
			System.out.println("Code was analyzed.");
		}
	}

}
