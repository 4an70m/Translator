package analyzer.syntax.automat.reader.state;

import java.util.ArrayList;

public class State {

	private int state;
	private ArrayList<Rule> rules;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}

	public State(int state) {
		super();
		this.state = state;
		rules = new ArrayList<Rule>();
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	@Override
	public String toString() {
		return "State [state=" + state + ", rules=" + rules + "]";
	}


}
