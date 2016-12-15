grammar MiniJava;

goal
	: mainClass (classDeclaration)* <EOF>
	;

mainClass
	: 'class' identifier '{'
	'public' 'static' 'void' 'main' '(' 'String' '[' ']' identifier ')' '{'
	statement
	'}' '}'
	;

