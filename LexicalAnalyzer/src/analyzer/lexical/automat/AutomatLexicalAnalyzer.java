package analyzer.lexical.automat;

import java.io.FileNotFoundException;
import java.io.IOException;

import analyzer.lexical.automat.reader.state.Rule;
import analyzer.lexical.automat.reader.state.State;
import analyzer.lexical.automat.reader.state.StateReader;
import analyzer.lexical.blank.LexicalAnalyzer;
import analyzer.util.handler.ErrorHandler;

public class AutomatLexicalAnalyzer extends LexicalAnalyzer {

	StateReader stateReader;

	public AutomatLexicalAnalyzer() throws FileNotFoundException {
		super("test.fas", "test.lexictable", "test.classtable");
		stateReader = new StateReader();
	}

	@Override
	public void readLexicAndClassTable() {
		super.readLexicAndClassTable();
		try {
			stateReader.readStates();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	@Override
	public void analyzeCode() throws IOException {

		outputHandler.setCodeWasTranslated(false);
		int row = 1;

		String sline = null;
		State currentState = null;
		StringBuilder lexeme = new StringBuilder();
		String classType = null;
		String symb = null;
		String prevClassType = null;
		boolean needBack = false;
outer:
		while ((sline = reader.readLine()) != null) {
			int state = 1;
			sline = sline.trim() + "  ";
			if (sline.contains("//"))
				continue;
			if (sline.contains("/*"))
				while (!sline.contains("*/"))
					sline = reader.readLine();
			if (sline.contains("*/"))
				continue outer;
			for (int i = 0; i < sline.length();) {
				needBack = true;
				currentState = stateReader.getState(state);
				prevClassType = classType;
				symb = String.valueOf(sline.charAt(i));
				classType = clReader.getVocabulary().checkType(symb);
				lexeme.append(symb);

				for (Rule rule : currentState.getRules()) {
					state = rule.getAlternativeState();

					if (classType.equals("L") && symb.equals("E")) {
						state = 5;
						break;
					}

					if (rule.getType().equals(classType)) {
						state = rule.getState();
						if (rule.getAlternativeState() != -1) {
							needBack = false;
							break;
						}
					}
					if (state > -1) {
						break;
					}
				}

				i++;

				if (state == 0) {

					if (prevClassType != classType && needBack) {
						lexeme.deleteCharAt(lexeme.length() - 1);
						i--;
					}

					int result = ltReader.checkLexeme(lexeme.toString().trim()
							.equals("") ? " " : lexeme.toString().trim());
					if (lexeme.toString().trim().equals("begin")) {
						outputHandler.setBegin();
					}
					if (result == ltReader.findSpace()) {
						lexeme = new StringBuilder("<space>");
					} else {
						errorCode = outputHandler.output(row, result, lexeme
								.toString().trim(), ltReader.findIdentifier(),
								ltReader.findConstant(), clReader);
					}
					if (errorCode != 0) {
						ErrorHandler.error(errorCode, row);
						return;
					}
					state = 1;
					lexeme = new StringBuilder();
					continue;
				}

				if (state <= -1) {
					errorCode = 1;
					ErrorHandler.error(errorCode, row);
					return;
				}

			}
			row++;
		}
		outputHandler.outputToFile();
		outputHandler.setCodeWasTranslated(true);
		System.out.println("Code was translated.");

	}
}
