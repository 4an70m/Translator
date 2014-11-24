package classreader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	//public ClasstableReader a;
	public static void main(String[] args)
	{
		ClasstableReader a;
		try {
			a = new ClasstableReader();
			a.readClasstable();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
