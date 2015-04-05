package rpn.builder.util;

public class LabelTableItem {

	private String label;
	private int index;

	public LabelTableItem() {
		// TODO Auto-generated constructor stub
	}

	public LabelTableItem(String label, int index) {
		super();
		this.label = label;
		this.index = index;
	}

	public String getLabel() {
		return label;
	}

	public int getIndex() {
		return index;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "LabelTableItem [label=" + label + ", index=" + index + "]";
	}

}
