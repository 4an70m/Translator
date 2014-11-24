package classreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ClasstableReader {

	private File classtable;
	private BufferedReader reader;
	private ClassVocabulary vocabulary;

	public ClasstableReader() throws FileNotFoundException {
		this("test.classtable");
	}

	public ClasstableReader(String path) throws FileNotFoundException {
		classtable = new File(path);
		vocabulary = new ClassVocabulary();
		reader = new BufferedReader(new FileReader(classtable));
	}

	public void readClasstable() throws IOException {
		String line = reader.readLine();
		if (!line.trim().equals("<classtable>"))
			return;
		while (!(line = reader.readLine()).trim().equals("<!classtable>")) {
			String[] item = line.trim().split("~");
			vocabulary.addLexeme(item[0], item[1]);
		}
		reader.close();
	}

	public ClassVocabulary getVocabulary() {
		return vocabulary;
	}
}
