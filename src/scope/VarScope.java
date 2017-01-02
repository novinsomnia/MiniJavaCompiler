package scope;

public class VarScope extends Scope {

	private String varType;

	public VarScope(String name, Scope parent, String varType) {
		super(name, parent);
		this.varType = varType;
		this.type = "var";
	}

	public String getVarType() {
		return varType;
	}
}