package lab4.grammarReader;

import java.util.ArrayList;

public class GrammarTable {

	GrammarReader gReader;
	ArrayList<String> headline;
	char[][] table;
	ArrayList<String> first;
	ArrayList<String> last;

	public GrammarTable(GrammarReader gReader) {
		this.gReader = gReader;
		table = new char[gReader.getUniqueLexSize() + 1][gReader
				.getUniqueLexSize() + 1];// 1 additional for #
		headline = new ArrayList<String>(gReader.getUniqueLex());
		headline.add("#");
	}

	public void buildTable() {
		evaluateEquals();
		
		evaluateLast();
	
		evaluateFirst();
	
		evaluateHash();
		printTable();
	}

	public void evaluateEquals() {
		for(int i = 0; i < gReader.getUniqueLexSize() + 1; i++)
			for(int j = 0; j < gReader.getUniqueLexSize() + 1; j++)
				table[i][j] = ' ';
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			String[] subparts = unit.getTerminal().split(" ");
			for (int i = 0; i < subparts.length - 1; i++) {
				setSign(subparts[i], subparts[i + 1], '=');
			}
		}
	}

	public void evaluateLast() {
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			String[] subpart = unit.getTerminal().split(" ");
			for (int i = 0; i < subpart.length - 1; i++) {
				if (subpart[i].matches("<(.+)>")
						&& !subpart[i + 1].matches("<(.+)>")) {
					last = new ArrayList<>();
					lastPlus(subpart[i]);
					for (String sublast : last) {
						if(getSign(sublast, subpart[i + 1]) != ' ' && getSign(sublast, subpart[i + 1]) != '>') 
							System.out.println("> error: " + sublast + " " + getSign(sublast, subpart[i + 1]) + " " + subpart[i + 1]);
						setSign(sublast, subpart[i + 1], '>');
					}
				}
			}
		}
	}

	public void evaluateFirst() {
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			String[] subpart = unit.getTerminal().split(" ");
			for (int i = 0; i < subpart.length - 1; i++) {
				if (!subpart[i].matches("<(.+)>")
						&& subpart[i + 1].matches("<(.+)>")) {
					first = new ArrayList<>();
					firstPlus(subpart[i + 1]);
					for (String subfirst : first) {
						if(getSign(subpart[i], subfirst) != ' ' && getSign(subpart[i], subfirst) != '<') 
							System.out.println("< error: " + subpart[i] + " " + getSign(subpart[i], subfirst) + " " + subfirst);
						setSign(subpart[i], subfirst, '<');
					}
				}
			}
		}
	}

	public void evaluateHash() {
		for (int i = 0; i < headline.size(); i++) {
			if (headline.get(i) == "#")
				continue;
			setSign("#", headline.get(i), '<');
			setSign(headline.get(i), "#", '>');
		}
	}

	public void setSign(String x, String y, char sign) {
		int row = headline.lastIndexOf(x);
		int col = headline.lastIndexOf(y);

		table[row][col] = sign;
	}

	public char getSign(String x, String y) {
		int row = headline.lastIndexOf(x);
		int col = headline.lastIndexOf(y);

		return table[row][col];
	}

	public void firstPlus(String lex) {
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			if (unit.getRule().equals(lex)) {
				String[] subparts = unit.getTerminal().split(" ");
				if (!first.contains(subparts[0])) first.add(subparts[0]);
				else continue;
				if (subparts[0].matches("<(.+)>")) {
					firstPlus(subparts[0]);
				}
			}
		}
		return;
	}

	public void lastPlus(String lex) {
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			if (unit.getRule().equals(lex)) {
				String[] subparts = unit.getTerminal().split(" ");
				if (!last.contains(subparts[subparts.length - 1])) 
					last.add(subparts[subparts.length - 1]);
				else continue;
				if (subparts[subparts.length - 1].matches("<(.+)>")) {
					lastPlus(subparts[subparts.length - 1]);
				}
			}
		}
		return;
	}

	public void printTable() {
		System.out.print("\t");
		for (int i = 0; i < gReader.getUniqueLexSize() + 1; i++)
			System.out.print(headline.get(i) + "\t");
		System.out.println();
		for (int i = 0; i < gReader.getUniqueLexSize() + 1; i++) {
			System.out.print(headline.get(i) + "\t");
			for (int j = 0; j < gReader.getUniqueLexSize() + 1; j++) {
				System.out.print(table[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
