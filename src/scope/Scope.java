package scope;

import java.util.HashMap;
import java.util.Map;

public abstract class Scope {
	
	String type;
	String name;
	Scope parent;
	Map<String, Scope> children = new HashMap<String, Scope>();

	public Scope(String name, Scope parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public Scope getParent() {
		return this.parent;
	}

	public String getType() {
		return this.type;
	}

	public Scope getParent(String type) {
		Scope s = this;
		while (!s.getType().equals(type) && s != null) {
			s = s.getParent();
		}
		return s;
	}

	public Map<String, Scope> getChildren() {
		return children;
	}

	public Scope resolve(String name) {
		Scope s = children.get(name);
		if (s != null)
			return s;
		if (parent != null)
			return parent.resolve(name);
		return null;
	}

	public void define(Scope scope) {
		children.put(scope.getName(), scope);
	}

	public boolean defined(String scopeName) {
		return children.containsKey(scopeName);
	}

}