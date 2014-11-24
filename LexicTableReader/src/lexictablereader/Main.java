package lexictablereader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	//public ClasstableReader a;
	public static void main(String[] args)
	{
		LexicTableReader a;
		try {
			a = new LexicTableReader();
			a.readLexicTable();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}