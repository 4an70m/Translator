package analyzer;

import java.io.FileNotFoundException;
import java.io.IOException;

import analyzer.lexical.state.reader.StateLexicalAnalyzer;
import analyzer.syntax.ascending.AscendingSyntaxAnalyzer;
import analyzer.syntax.automat.AutomatSyntaxAnalyzer;
import analyzer.syntax.blank.SyntaxAnalyzer;
import analyzer.syntax.descending.DescendingSyntaxAnalyzer;
import analyzer.syntax.state.StateSyntaxAnalyzer;
import analyzer.util.handler.OutputHandler;

public class Main {

	public static void main(String[] args) {

		try {
			
			StateLexicalAnalyzer c = new StateLexicalAnalyzer();
			c.readLexicAndClassTable();
			c.analyzeCode();
			
			SyntaxAnalyzer s = new AscendingSyntaxAnalyzer(c.getOutputHandler());
			s.analyzeSyntax();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
