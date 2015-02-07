package lab5.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import lab5.builder.AscendingBuilder;

public class Main {

	public static void main(String[] args) {

		try {
			AscendingBuilder ab = new AscendingBuilder();
			ab.initialize();
			ab.analyzeSynatax();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
