package lab2.syntaxAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

import codereader.CodeReader;

public class Main {

	public static void main(String[] args) {

		CodeReader cr;
		AutomatSyntaxAnalizer sAnalizer;
		try {
			cr = new CodeReader();

			cr.readLexicAndClassTable();

			cr.readCode();

			sAnalizer = new AutomatSyntaxAnalizer(cr.getOutputHandler());

			sAnalizer.analizeSyntax();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
