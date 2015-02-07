package lab5.builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputTableBuilder {

	private File outputTable;
	private BufferedWriter bWriter;

	public OutputTableBuilder() throws IOException {
		File file = new File("stack.html");
		file.setWritable(true);
		bWriter = new BufferedWriter(new FileWriter(file));
	}

	public void startWriting() throws IOException {
		bWriter.write("<html>");
		bWriter.newLine();
		bWriter.write("<head>");
		bWriter.write("</head>");
		bWriter.write("<H1>" + "Table" + "</H1>");
		bWriter.newLine();
		bWriter.write("<body>");
		bWriter.newLine();
		bWriter.write("<table border = 1>");
		bWriter.newLine();
	}

	public void writeHeadlines(String headlines) throws IOException {
		String[] tempString = headlines.split(" ");
		for (int i = 0; i < tempString.length; i++) {
			StringBuilder line = new StringBuilder();
			line.append("<th>");
			line.append(tempString[i]);
			line.append("</th>");
			bWriter.write(line.toString());
			bWriter.newLine();
		}
	}

	public void writeLine(String line) throws IOException {
		String[] tempString = line.split(" ");
		StringBuilder tline = new StringBuilder();
		tline.append("<tr>");
		for (int i = 0; i < tempString.length; i++) {
			tline.append("<td>" + tempString[i] + "</td>");
		}
		tline.append("</tr>");
		bWriter.write(tline.toString());
		bWriter.newLine();
	}
	
	public void writeLine(String[] line) throws IOException {
				StringBuilder tline = new StringBuilder();
		tline.append("<tr>");
		for (int i = 0; i < line.length; i++) {
			tline.append("<td>" + line[i] + "</td>");
		}
		tline.append("</tr>");
		bWriter.write(tline.toString());
		bWriter.newLine();
	}

	public void finishWriting() throws IOException {
		bWriter.write("</table>");
		bWriter.newLine();
		bWriter.write("</body>");
		bWriter.newLine();
		bWriter.write("</html>");
		bWriter.flush();
		bWriter.close();
	}

}
