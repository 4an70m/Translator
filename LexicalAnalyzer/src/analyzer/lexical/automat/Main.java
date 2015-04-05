package analyzer.lexical.automat;

import java.io.IOException;

import analyzer.lexical.blank.LexicalAnalyzer;
import analyzer.lexical.state.StateLexicalAnalyzer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			//LexicalAnalyzer la = new AutomatLexicalAnalyzer();
			LexicalAnalyzer la = new StateLexicalAnalyzer();
			la.readLexicAndClassTable();
			la.analyzeCode();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
