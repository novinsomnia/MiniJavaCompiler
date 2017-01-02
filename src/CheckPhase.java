import base.MiniJavaBaseListener;
import base.MiniJavaParser;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import scope.*;

public class CheckPhase extends MiniJavaBaseListener {

	private Scope currentScope = new BaseScope(null);
	private ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();

	public void enterMainClass(MiniJavaParser.MainClassContext ctx) {
		String className = ctx.identifier(0).getText();
		ClassScope classScope = new ClassScope(className, currentScope);
		int start = ctx.identifier(0).start.getStartIndex();
        int stop = ctx.identifier(0).start.getStopIndex();

		if (currentScope.defined(className)) {
			Errors.printError(ctx.getStart(), "The class '" +className+ "' has been defined before.", 
								ctx.start.getInputStream().toString());
		}
		currentScope.define(classScope);
		scopes.put(ctx, classScope);
		currentScope = classScope;
	}

	public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
		String className = ctx.identifier(0).getText();
		ClassScope classScope = new ClassScope(className, currentScope);
		int start = ctx.identifier(0).start.getStartIndex();
        int stop = ctx.identifier(0).start.getStopIndex();

		if (ctx.identifier().size() > 1) {
			String extendsName = ctx.identifier(1).getText();
			Scope scope = currentScope.resolve(extendsName);
			if (scope == null || extendsName.equals(className)) {
				Errors.printError(ctx.getStart(),"The class extended '" +extendsName+ "' does not exists.",
								  ctx.start.getInputStream().toString());
			}
			else if (!scope.getType().equals("class")) {
				Errors.printError(ctx.getStart(), "The class extended '" +extendsName+ "' is not a class.", 
								ctx.start.getInputStream().toString());
			}

		}
		if (currentScope.defined(className)) {
			Errors.printError(ctx.getStart(), "The class '" +className+ "' has been defined before.", 
								ctx.start.getInputStream().toString());
		} else {
			currentScope.define(classScope);
			scopes.put(ctx, classScope);
		}
		currentScope = classScope;
	}

/*
	void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx);

	void enterVarDeclaration(MiniJavaParser.VarDeclarationContext ctx);

	void exitVarDeclaration(MiniJavaParser.VarDeclarationContext ctx);

	void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx);

	void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx);

	void enterType(MiniJavaParser.TypeContext ctx);

	void exitType(MiniJavaParser.TypeContext ctx);

	void enterStatement(MiniJavaParser.StatementContext ctx);

	void exitStatement(MiniJavaParser.StatementContext ctx);

	void enterExpression(MiniJavaParser.ExpressionContext ctx);

	void exitExpression(MiniJavaParser.ExpressionContext ctx);

	void enterIdentifier(MiniJavaParser.IdentifierContext ctx);

	void exitIdentifier(MiniJavaParser.IdentifierContext ctx);
	*/
}