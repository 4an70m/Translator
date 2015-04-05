package rpn.builder;

import java.util.LinkedList;

import analyzer.util.handler.OutputHandler;

public abstract class RPNBuilder {

	protected OutputHandler outputHandelr;
	protected LinkedList<String> rpn;
	protected boolean rpnWasBuilt;

	public RPNBuilder() {
		rpn = new LinkedList<>();
	}

	public abstract void buildRPN();

	public OutputHandler getOutputHandelr() {
		return outputHandelr;
	}

	public void setOutputHandelr(OutputHandler outputHandelr) {
		this.outputHandelr = outputHandelr;
	}

	public LinkedList<String> getRpn() {
		return rpn;
	}

	public boolean getRpnWasBuilt() {
		return rpnWasBuilt;
	}

	@Override
	public String toString() {
		return "RPNBuilder [rpn=" + rpn + "]";
	}

}
