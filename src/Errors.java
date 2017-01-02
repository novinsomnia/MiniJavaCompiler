import org.antlr.v4.runtime.*;

public class Errors {
	public static void printError(Token t, String message, String currentInput) {
		int line = t.getLine();
		int position = t.getCharPositionInLine();
		System.err.printf("line %d:%d %s", line, position, message);
		System.err.println();

        String[] lines = currentInput.split("\n");
        String errorLine = lines[line - 1];
        System.err.println(errorLine);

        for (int i = 0; i < position; i++) System.err.print(" ");
        System.err.println("^");
	}

}