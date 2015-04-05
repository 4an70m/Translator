package analyzer.lexical.state;

import java.io.FileNotFoundException;
import java.io.IOException;

import analyzer.lexical.blank.LexicalAnalyzer;

public class Main {

	public static void main(String[] args) {

		LexicalAnalyzer cr;
		try {
			cr = new StateLexicalAnalyzer();

			cr.readLexicAndClassTable();

			cr.analyzeCode();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
