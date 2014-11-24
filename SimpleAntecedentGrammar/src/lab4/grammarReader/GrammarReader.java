package lab4.grammarReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class GrammarReader {

	private File grammarFile;
	private ArrayList<GrammarUnit> grammarVocabulary;
	private BufferedReader bReader;
	private TreeSet<String> uniqeLex;

	public GrammarReader(String filePath) throws FileNotFoundException {
		grammarFile = new File(filePath);
		grammarVocabulary = new ArrayList<>();
		bReader = new BufferedReader(new FileReader(grammarFile));
		uniqeLex = new TreeSet<String>();
	}

	public GrammarReader() throws FileNotFoundException {
		this("Grammar.grmm");
	}

	public boolean readGrammar() throws IOException {
		String line = bReader.readLine();
		if (!line.equals("<grammar>"))
			return false;
		line = bReader.readLine();
		for (; !line.equals("<!grammar>"); line = bReader.readLine()) {
			String[] parts = line.split(" ::= ");
			String[] termparts = parts[1].split("\\|");
			uniqeLex.add(parts[0].trim());
			for (int i = 0; i < termparts.length; i++) {
				GrammarUnit gUnit = new GrammarUnit(parts[0].trim(),
						termparts[i].trim());
				String[] lexs = termparts[i].trim().split(" ");
				for (String lex : lexs) {
					uniqeLex.add(lex.trim());
				}
				System.out.println(gUnit);
			}

		}
		System.out.println(uniqeLex);
		return true;
	}

	public int getUniqueLexSize() {
		return uniqeLex.size();
	}
	
	public TreeSet<String> getUniqueLex() {
		return uniqeLex;
	}
}
