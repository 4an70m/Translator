package lab4.grammarReader;

import java.util.ArrayList;

public class GrammarTable {

	GrammarReader gReader;
	ArrayList<String> headline;
	char[][] table;

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
					ArrayList<String> last = lastPlus(subpart[i]);
					for (String sublast : last) {
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
					ArrayList<String> first= firstPlus(subpart[i + 1]);
					for (String subfirst : first) {
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

	public ArrayList<String> firstPlus(String lex) {
		ArrayList<String> first = new ArrayList<>();
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			if (unit.getRule().equals(lex)) {
				String[] subparts = unit.getTerminal().split(" ");
				first.add(subparts[0]);
				if (subparts[0].matches("<(.+)>")) {
					first.addAll(firstPlus(subparts[0]));
				}
			}
		}
		return first;
	}

	public ArrayList<String> lastPlus(String lex) {
		ArrayList<String> last = new ArrayList<>();
		for (GrammarUnit unit : gReader.getGrammarVocabulary()) {
			if (unit.getRule().equals(lex)) {
				String[] subparts = unit.getTerminal().split(" ");
				last.add(subparts[subparts.length - 1]);
				if (subparts[subparts.length - 1].matches("<(.+)>")) {
					last.addAll(lastPlus(subparts[subparts.length - 1]));
				}
			}
		}
		return last;
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
