package analyzer.util.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IdentifierTable {

	private ArrayList<TableItem> identifierTable;
	private int number;
	private File file;

	public IdentifierTable() {
		identifierTable = new ArrayList<>();
		file = new File("Output/test.idtable");
		number = 0;
	}

	public int addTableItem(String identifier) {
		int tempnum = -1;

		for (int i = 0; i < identifierTable.size(); i++) {
			if (identifierTable.get(i).getItem().equals(identifier)) {
				tempnum = i + 1;
			}
		}

		if (tempnum == -1) {
			tempnum = ++number;
			TableItem tableItem = new TableItem(identifier, tempnum);
			identifierTable.add(tableItem);
		}

		return tempnum;
	}

	public boolean checkTableItem(String identifier) {
		int tempnum = -1;

		for (int i = 0; i < identifierTable.size(); i++) {
			if (identifierTable.get(i).getItem().equals(identifier)) {
				tempnum = i + 1;
			}
		}

		if (tempnum == -1) {
			return false;
		}

		return true;
	}

	public void writeToFile() throws IOException {
		BufferedWriter bWrighter = new BufferedWriter(new FileWriter(file));
		for (TableItem item : identifierTable) {
			bWrighter.write(item.toString());
		}
		bWrighter.close();
	}

}
