/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
//import com.sun.istack.internal.Nullable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.util.*;
import base.MiniJavaLexer;
import base.MiniJavaParser;
import java.lang.reflect.Method;
import java.io.*;

public class Compiler {
    public static class UnderlineListener extends BaseErrorListener {
	   public void syntaxError(Recognizer<?, ?> recognizer,
	                           Object offendingSymbol,
                               int line, int charPositionInLine,
                               String msg,
                               RecognitionException e) {
            System.err.println("line "+line+":"+charPositionInLine+" "+msg);
            underlineError(recognizer,(Token)offendingSymbol,
                           line, charPositionInLine);
        }

        protected void underlineError(Recognizer recognizer,
                                      Token offendingToken, int line,
                                      int charPositionInLine) {
            CommonTokenStream tokens =
                (CommonTokenStream)recognizer.getInputStream();
            String input = tokens.getTokenSource().getInputStream().toString();
            String[] lines = input.split("\n");
            String errorLine = lines[line - 1];
            System.err.println(errorLine);
            for (int i=0; i<charPositionInLine; i++) System.err.print(" ");
            int start = offendingToken.getStartIndex();
            int stop = offendingToken.getStopIndex();
            if ( start>=0 && stop>=0 ) {
                for (int i=start; i<=stop; i++) System.err.print("^");
            }
            System.err.println();
        }
    }

    public static void main(String[] args) throws Exception {
        
        InputStream is = System.in;
        if (args.length < 1) {
            System.err.println("java Compiler [input-filename]");
            System.err.println("Omitting input-filename makes compiler read from stdin");
        }
        else {
            String inputFilename = args[0];
            String absolutePath = System.getProperty("user.dir") + "/resources/" + inputFilename;
            is = new FileInputStream(absolutePath);
        }

        InputStreamReader r = new InputStreamReader((InputStream)is);
        ANTLRInputStream input = new ANTLRInputStream(r);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
		MiniJavaParser parser = new MiniJavaParser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new UnderlineListener());
        ParseTree tree = parser.goal();

        ParseTreeWalker walker = new ParseTreeWalker();
        CheckPhase checkPhase = new CheckPhase();
        walker.walk(checkPhase, tree); 

        Trees.inspect(tree, parser);

    }
}