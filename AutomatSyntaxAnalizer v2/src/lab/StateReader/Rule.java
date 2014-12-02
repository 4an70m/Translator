package lab.StateReader;
public class Rule {
	private int lexeme;
	private int newState;
	private int stack;
	private int error;
	
	public int getLexeme() {
		return lexeme;
	}
	public void setLexeme(int lexeme) {
		this.lexeme = lexeme;
	}
	public int getNewState() {
		return newState;
	}
	public void setNewState(int newState) {
		this.newState = newState;
	}
	public int getStack() {
		return stack;
	}
	public void setStack(int stack) {
		this.stack = stack;
	}
	public Rule(int lexeme, int newState, int stack, int error) {
		super();
		this.lexeme = lexeme;
		this.newState = newState;
		this.stack = stack;
		this.error = error;
	}
	@Override
	public String toString() {
		return "Rule [lexeme=" + lexeme + ", newState=" + newState + ", stack="
				+ stack + "]";
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}

	
	
}
