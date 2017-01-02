package scope;

public class BaseScope extends Scope {

	public BaseScope(Scope parent) {
		super(null, parent);
		this.type = "base";
	}

}