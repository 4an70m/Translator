package lab.StateReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StatesTable {

	private ArrayList<State> table;
	private File f;
	private BufferedReader br;

	public StatesTable() {
		table = new ArrayList<>();
		f = new File("test.states");
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readStates() throws IOException {
		String line = br.readLine();
		Rule ruleForAdding = null;
		if (!line.equals("<statetable>"))
			return;
		boolean addedRule = false;
		for (line = br.readLine(); !line.equals("<!statetable>"); line = br
				.readLine()) {
			addedRule = false;
			String[] temp = line.split(" ");
			if (!table.isEmpty()) {
				State tempState = table.get(table.size() - 1);
				if (temp[0].equals(String.valueOf(tempState.getState()))) {
					int lexeme = Integer.valueOf(temp[1]);
					int newState = Integer.valueOf(temp[2]);
					int stack = Integer.valueOf(temp[3]);
					int error = Integer.valueOf(temp[4]);
					ruleForAdding = new Rule(lexeme, newState, stack, error);
					addedRule = true;

				}
				if (addedRule)
					table.get(table.size() - 1).addRule(ruleForAdding);
			}
			if (!addedRule) {
				State state = new State(Integer.valueOf(temp[0]));
				int lexeme = Integer.valueOf(temp[1]);
				int newState = Integer.valueOf(temp[2]);
				int stack = Integer.valueOf(temp[3]);
				int error = Integer.valueOf(temp[4]);
				ruleForAdding = new Rule(lexeme, newState, stack, error);
				state.addRule(ruleForAdding);
				table.add(state);
			}
		}
	}

	public ArrayList<State> getTable() {
		return table;
	}

	public State getState(int state) {
		for (State st : table) {
			if (st.getState() == state)
				return st;
		}
		return null;
	}

	public int getErrorByLexemeCode(int lexemeCode) {
		for (State st : table) {
			for (Rule rl : st.getRules()) {
				if(lexemeCode == rl.getLexeme())
					return rl.getError();
			}
		}
		return -1;
	}
}
