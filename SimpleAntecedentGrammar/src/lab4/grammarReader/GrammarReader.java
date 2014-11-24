package lab4.grammarReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.TreeSet;

public class GrammarReader {

	private File grammarFile;
	private ArrayList<GrammarUnit> grammarVocabulary; 	//variable to store all the grammar
	private BufferedReader bReader;
	private TreeSet<String> uniqeLex; 					//variable for headlines of table 

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
		//if there's no <grammar> line - an error occures
		if (!line.equals("<grammar>"))
			return false;
		line = bReader.readLine();
		for (; !line.equals("<!grammar>"); line = bReader.readLine()) {
			String[] parts = line.split(" ::= "); //parts contains non-terminal <unit> and the right part
			String[] termparts = parts[1].split("\\|"); //termparts contains only separated right parts
			uniqeLex.add(parts[0].trim());
			for (int i = 0; i < termparts.length; i++) {
				GrammarUnit gUnit = new GrammarUnit(parts[0].trim(),
						termparts[i].trim()); 	//creating new grammar units with current n-t <unit>
												//and each right part
				//this part tries to fill the "unique lexeme" array for headlines
				String[] lexs = termparts[i].trim().split(" ");
				for (String lex : lexs) {
					uniqeLex.add(lex.trim());
				}
				grammarVocabulary.add(gUnit);
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
	
	public ArrayList<GrammarUnit> getGrammarVocabulary()
	{
		return grammarVocabulary;
	}
}
