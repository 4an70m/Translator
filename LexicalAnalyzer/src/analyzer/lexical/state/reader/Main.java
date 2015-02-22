package analyzer.lexical.state.reader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		StateLexicalAnalyzer cr;
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
