package rpn.executor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import rpn.builder.RPNBuilder;
import analyzer.util.handler.OutputHandler;
import analyzer.util.output.ConstantTable;
import analyzer.util.output.IdentifierTable;
import analyzer.util.output.TableItem;

public abstract class RPNExecutor {

	protected LinkedList<String> rpn;
	protected Map<String, Double> varMap;
	protected IdentifierTable idTable;
	protected ConstantTable cTable;
	protected RPNBuilder rpnBuilder;

	public RPNExecutor(OutputHandler oHandler, RPNBuilder rpnBuilder) {
		this.rpnBuilder = rpnBuilder;
		idTable = oHandler.getIdTable();
		cTable = oHandler.getConstantTable();
		varMap = new HashMap<String, Double>();
		fillVarMap();
	}

	private void fillVarMap() {
		for (TableItem t : idTable.getIdentifierTable()) {
			varMap.put(t.getItem(), 0d);
		}
	}

	abstract void execute();
}
