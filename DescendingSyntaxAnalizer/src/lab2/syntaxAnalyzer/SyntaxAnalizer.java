package lab2.syntaxAnalyzer;

import codereader.ErrorHandler;
import codereader.OutputHandler;

public class SyntaxAnalizer {

	ProceduresHandler pHandler;
	OutputHandler outputHandler;

	public SyntaxAnalizer(OutputHandler outputHandler) {
		this.outputHandler = outputHandler;
	}

	public void analizeSyntax() {
		if (!outputHandler.getCodeWasTranslated()) {
			ErrorHandler.error(99, 0);
			return;
		}
		pHandler = new ProceduresHandler(outputHandler);
		
		if (pHandler.pProgram() != 0)
		{
			for (int i = 0; i < pHandler.getErrorCode().size(); i++)
				ErrorHandler.error(pHandler.getErrorCode().get(i), pHandler.getErrorLine().get(i));
		}
		
		System.out.println("Syntax analizer worked.");

	}

}
