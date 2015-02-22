package analyzer.syntax.blank;

import analyzer.util.handler.OutputHandler;

public abstract class SyntaxAnalyzer {

	protected OutputHandler outputHandler;

	public SyntaxAnalyzer(OutputHandler outputHandler) {
		this.outputHandler = outputHandler;
	}

	public abstract void analyzeSyntax();

}
