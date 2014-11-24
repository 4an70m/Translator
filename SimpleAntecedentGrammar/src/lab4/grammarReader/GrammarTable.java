package lab4.grammarReader;

import java.util.ArrayList;

public class GrammarTable {

	GrammarReader gReader;
	ArrayList<String> headline;
	char[][] table;

	public GrammarTable(GrammarReader gReader) {
		this.gReader = gReader;
		table = new char[gReader.getUniqueLexSize() + 1][gReader
				.getUniqueLexSize() + 1];
		headline = new ArrayList<String>(gReader.getUniqueLex());
	}

	public void buildTable() {
		
	}
	
	public void setSign(String x, String y, char sign)
	{
		int row = headline.lastIndexOf(x);
		int col = headline.lastIndexOf(y);
		
		table[row][col] = sign;
	}
	
	public ArrayList<String> firstPlus ()
	{
		return new ArrayList<>();
	}
	public ArrayList<String> lastPlus ()
	{
		return new ArrayList<>();
	}
}
