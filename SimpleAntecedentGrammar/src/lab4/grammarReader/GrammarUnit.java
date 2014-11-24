package lab4.grammarReader;

public class GrammarUnit {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GrammarUnit [rule=" + rule + ", terminal=" + terminal + "]";
	}

	private String rule;
	private String terminal;

	public GrammarUnit(String rule, String terminal) {
		this.rule = rule;
		this.terminal = terminal;
	}

	public String getRule() {
		return rule;
	}

	public String getTerminal() {
		return terminal;
	}	

}
