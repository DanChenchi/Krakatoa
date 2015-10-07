package comp;

import ast.*;
import lexer.*;

import java.io.*;
import java.util.*;

public class Compiler {

	// compile must receive an input with an character less than
	// p_input.lenght
	public Program compile(char[] input, PrintWriter outError) {

		ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
		signalError = new SignalError(outError, compilationErrorList);
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, signalError);
		signalError.setLexer(lexer);

		lexer.nextToken();
		return program(compilationErrorList);
	}

	// declaração do program foi para o fim do metodo para a incrementaÃ§Ã£o da lista de kraclass
	private Program program(ArrayList<CompilationError> compilationErrorList) {
		// Program ::= KraClass { KraClass }
		ArrayList<MetaobjectCall> metaobjectCallList = new ArrayList<>();
		ArrayList<KraClass> kraClassList = new ArrayList<>();
		try {
			while (lexer.token == Symbol.MOCall) {
				metaobjectCallList.add(metaobjectCall());
			}
			kraClassList.add(classDec());
			while (lexer.token == Symbol.CLASS)
				kraClassList.add(classDec());
			if (lexer.token != Symbol.EOF) {
				signalError.show("End of file expected");
				
				//TODO: ER-SEM77.KRA, ER-SEM78.KRA, ER-SEM79.KRA, ER-SEM80.KRA, ER-SEM81.KRA
				//Verificar se existe uma classe Program, e se essa possui um método public void run()
				
				KraClass program = symbolTable.getInGlobal("Program");
				if(program == null)
					signalError.show("Source code without a class 'Program'");
				
				Method run = program.getPublicMethodList().search("run");
				if(run == null){
					run = program.getPrivateMethodList().search("run");
					if(run == null)
						signalError.show("Method 'run' was not found in class 'Program'");
					else
						signalError.show("Method 'run' of class 'Program' cannot be private");
				}
				
				if(run.getParameters() != null)
					signalError.show("Method 'run' of class 'Program' cannot take parameters");
				
				if(run.getType() != Type.voidType)
					signalError.show("Method 'run' of class 'Program' with a return value type different from 'void'");
				
			}
		} catch (RuntimeException e) {
			// if there was an exception, there is a compilation signalError
		}
		return new Program(kraClassList, metaobjectCallList, compilationErrorList);
	}

	@SuppressWarnings("incomplete-switch")
	private MetaobjectCall metaobjectCall() {
		String name = lexer.getMetaobjectName();
		lexer.nextToken();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		if ( lexer.token == Symbol.LEFTPAR ) {
			// metaobject call with parameters
			lexer.nextToken();
			while ( lexer.token == Symbol.LITERALINT || lexer.token == Symbol.LITERALSTRING ||
					lexer.token == Symbol.IDENT ) {
				switch ( lexer.token ) {
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				case IDENT:
					metaobjectParamList.add(lexer.getStringValue());
				}
				lexer.nextToken();
				if ( lexer.token == Symbol.COMMA ) 
					lexer.nextToken();
				else
					break;
			}
			if ( lexer.token != Symbol.RIGHTPAR ) 
				signalError.show("')' expected after metaobject call with parameters");
			else
				lexer.nextToken();
		}
		if ( name.equals("nce") ) {
			if ( metaobjectParamList.size() != 0 )
				signalError.show("Metaobject 'nce' does not take parameters");
		}
		else if ( name.equals("ce") ) {
			if ( metaobjectParamList.size() != 3 && metaobjectParamList.size() != 4 )
				signalError.show("Metaobject 'ce' take three or four parameters");
			if ( !( metaobjectParamList.get(0) instanceof Integer)  )
				signalError.show("The first parameter of metaobject 'ce' should be an integer number");
			if ( !( metaobjectParamList.get(1) instanceof String) ||  !( metaobjectParamList.get(2) instanceof String) )
				signalError.show("The second and third parameters of metaobject 'ce' should be literal strings");
			if ( metaobjectParamList.size() >= 4 && !( metaobjectParamList.get(3) instanceof String) )  
				signalError.show("The fourth parameter of metaobject 'ce' should be a literal string");
			
		}
			
		return new MetaobjectCall(name, metaobjectParamList);
	}

//	classdec agora retorna uma kraclass, no fim agora faz update no symboltable
//	verifica o extends se a classe existe e se Ã© diferente da classe atual que esta sendo declarada
//	verifica o static e final em qualquer ordem apenas uma vez

//	KraClass ::= [ "final" ] [ "static" ] ``class'' Id [ ``extends'' Id ] "{" MemberList "}"
//	MemberList ::= { Qualifier Member }
//	Qualifier ::= [ "final" ] [ "static" ]  ( "private" | "public" )
//	Member ::= InstVarDec | MethodDec

	private KraClass classDec() {

		boolean classIsStatic = false, classIsFinal = false;
		while (lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
			switch (lexer.token){
				case STATIC:
					if (!classIsStatic){
						classIsStatic = true;
					}else{
						signalError.show("'static' has already been declared");
					}
					break;
				case FINAL:
					if (!classIsFinal){
						classIsFinal = true;
					}else{
						signalError.show("'final' has already been declared");
					}
					break;
			}
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.CLASS )
			signalError.show("'class' expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.IDENT )
			signalError.show(SignalError.ident_expected);
		String className = lexer.getStringValue();
		if (symbolTable.getInGlobal(className) != null){
			signalError.show("'"+className+"' has already been declared");
		}
		KraClass kraClass = new KraClass(className,classIsFinal,classIsStatic);
		symbolTable.putInGlobal(className, kraClass);
		lexer.nextToken();
		if ( lexer.token == Symbol.EXTENDS ) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show(SignalError.ident_expected);
			String superclassName = lexer.getStringValue();
			KraClass superclass = symbolTable.getInGlobal(superclassName);
			if(superclass == null){
				signalError.show("'"+superclassName+"' was not declared");
			}
			if(superclass != null && superclass.isFinal()){
				signalError.show("'"+superclassName+"' is 'final' and can not be extended");
			}
			if(superclassName.equals(className)){
				signalError.show("Class '"+className+"' is inheriting from itself");
			}
			kraClass.setSuperclass(superclass);
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.LEFTCURBRACKET )
			signalError.show("{ expected", true);
		currentClass = className;
		lexer.nextToken();

		while (lexer.token == Symbol.PRIVATE || lexer.token == Symbol.PUBLIC || lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
			Symbol qualifier;
			boolean memberIsStatic = false, memberIsFinal = false;
			if (classIsFinal){
				memberIsFinal = true;
			}
			while (lexer.token == Symbol.STATIC || lexer.token == Symbol.FINAL) {
				switch (lexer.token){
					case STATIC:
						if (!memberIsStatic){
							memberIsStatic = true;
						}else{
							signalError.show("member is already static");
						}
						break;
					case FINAL:
						if (!memberIsFinal){
							memberIsFinal = true;
						}else{
							signalError.show("member is already final");
						}
						break;
				}
				lexer.nextToken();
			}
			switch (lexer.token) {
				case PRIVATE:
					lexer.nextToken();
					qualifier = Symbol.PRIVATE;
					break;
				case PUBLIC:
					lexer.nextToken();
					qualifier = Symbol.PUBLIC;
					break;
				default:
					signalError.show("private, or public expected");
					qualifier = Symbol.PUBLIC;
			}
			Type t = type();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			String name = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token == Symbol.LEFTPAR )
				if(qualifier == Symbol.PRIVATE){
					if(memberIsFinal){
						signalError.show("final method must be public");
					}
					kraClass.addPrivateMethod(methodDec(t, name, memberIsStatic, memberIsFinal));
				}else{
					kraClass.addPublicMethod(methodDec(t, name, memberIsStatic, memberIsFinal));
				}
			else if ( qualifier != Symbol.PRIVATE )
				signalError.show("Attempt to declare a public instance variable");
			else if (t.getName().equals("void"))
				signalError.show("Attempt to declare a void instance variable");
			else
				kraClass.setInstanceVariableList(instanceVarDec(t, name, memberIsStatic, memberIsFinal));
		}
		
		
		
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.show("public/private or \"}\" expected");
		lexer.nextToken();
		symbolTable.removeInstanceIdent();
		return kraClass;
	}

//	InstVarDec ::= Type IdList ";"
// 	InstVarDec ::= [ "static" ] "private" Type IdList ";"
	private InstanceVariableList instanceVarDec(Type type, String name, boolean isStatic, boolean isFinal) {

		if (symbolTable.getInInstance(name) != null){
			signalError.show("Variable '"+name+ "' is being redeclared");
		}
		InstanceVariable instanceVariable = new InstanceVariable(type, name, isStatic, isFinal);
		symbolTable.putInInstance(name, instanceVariable);
		InstanceVariableList instanceVariableList = new InstanceVariableList();
		instanceVariableList.addElement(instanceVariable);
		
		if (isFinal) {
			if (lexer.token != Symbol.ASSIGN) {
				signalError.show("Final variable must be initialized");
			}
			Expr expr = expr();
			if (expr.getType() != instanceVariable.getType()){
				signalError.show("Assignment must be of the same type as instance variable");
			}
		}
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			String variableName = lexer.getStringValue();
			if (symbolTable.getInInstance(variableName) != null){
				signalError.show("Variable '"+name+ "' is being redeclared");
			}
			instanceVariableList.addElement(new InstanceVariable(type, variableName, isStatic, isFinal));
			lexer.nextToken();
		}
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return instanceVariableList;
	}

	private KraClass searchMethods(String name, ParamList paramList, boolean isStatic, String clss){
		KraClass kraClass = symbolTable.getInGlobal(clss);
		return (kraClass.searchMethods(name, isStatic, false));
	}

//	MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{" StatementList "}"
	private Method methodDec(Type type, String name, boolean isStatic, boolean isFinal) {
		//TODO: ER-SEM31.KRA
		//Verificar se o nome do método não é o mesmo de uma variável de instância
				
		//TODO: ER-SEM32.KRA, ER-SEM33.KRA, ER-SEM70.KRA
		//Verificar se o método não está sendo redeclarado, isso inclui declarar o mesmo método como private e public
		
		lexer.nextToken();
		ParamList paramList = new ParamList();
		if ( lexer.token != Symbol.RIGHTPAR ){
			paramList =  formalParamDec();
		}
		
		//TODO: ER-SEM30.KRA, ER-SEM51.KRA
		//métodos herdados precisam ter a mesma assinatura do método da classe pai. Isso inclui os tipos dos parâmetros
		//A{ public void put();}    B extends A {public int put(char c);}
		
		
		if ( lexer.token != Symbol.RIGHTPAR ){
			signalError.show(") expected");
		}
		KraClass kraClass = searchMethods(name, paramList, isStatic, currentClass);
		if ( kraClass != null){
			signalError.show(name+" is already defined in "+ kraClass.getName());
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTCURBRACKET ) signalError.show("{ expected");
		lexer.nextToken();
		Method method = new Method(name, paramList, type, isStatic, isFinal);
		currentMethod = method;
		StatementList statementList = statementList();
		method.setStatementList(statementList);
		//TODO: ER-SEM01.KRA
		//checar se há 'return'
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) signalError.show("} expected");
		lexer.nextToken();
		symbolTable.removeLocalIdent();
		return method;
	}

//	recebimento de parametro, o parametro deve ser o filho da classe declarada
// FormalParamDec ::= ParamDec { "," ParamDec }
	private ParamList formalParamDec() {
		ParamList paramList = new ParamList();
		paramList.addElement(paramDec());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			paramList.addElement(paramDec());
		}
		return paramList;
	}

//	 ParamDec ::= Type Id
	private Variable paramDec() {

		Type type = type();
		if ( lexer.token != Symbol.IDENT ){
			signalError.show("Identifier expected");
		}
		String variableName = lexer.getStringValue();
		if (symbolTable.getInLocal(variableName) != null){
			signalError.show("'"+variableName+"' has already been declared");
		}
		Variable variable = new Variable(variableName, type);
		symbolTable.putInLocal(variableName,variable);
		lexer.nextToken();
		return variable;
	}

//	 Type ::= BasicType | Id
	private Type type() {
		Type result;

		switch (lexer.token) {
		case VOID:
			result = Type.voidType;
			break;
		case INT:
			result = Type.intType;
			break;
		case BOOLEAN:
			result = Type.booleanType;
			break;
		case STRING:
			result = Type.stringType;
			break;
		case IDENT:
			if (symbolTable.getInGlobal(lexer.getStringValue()) == null){
				signalError.show(lexer.getStringValue()+" is not a type");
			}
			result = symbolTable.getInGlobal(lexer.getStringValue());
			break;
		default:
			signalError.show("Type expected");
			result = Type.undefinedType;
		}
		lexer.nextToken();
		return result;
	}

//	 CompStatement ::= "{" { Statement } "}"
	private CompositeStatement compositeStatement() {
		lexer.nextToken();
		CompositeStatement compositeStatement =  new CompositeStatement(statementList());
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.show("} expected");
		else
			lexer.nextToken();
		return compositeStatement;
	}

//	 statements always begin with an identifier, if, read, write, ...
//	 statementList ::= { Statement }
	private StatementList statementList() {
		Symbol tk = lexer.token;
		StatementList statementList = new StatementList();
		while (tk != Symbol.RIGHTCURBRACKET) {
			statementList.addStatement(statement());
		}
		return statementList;
	}

//	Statement ::= Assignment ``;'' | IfStat | WhileStat | MessageSend ``;'' |
//  ReturnStat ``;'' | ReadStat ``;'' | WriteStat ``;'' |
//  ``break''  ``;'' | ``;'' | CompStatement | LocalDec
	private Statement statement() {

		//TODO: ER-SEM34.KRA
		//chamada de método com valor de retorno não pode "retornar para o nada"... Ou seja, apenas a direita do '=' em um Assignment
		
		switch (lexer.token) {
		case THIS:
		case IDENT:
		case SUPER:
		case INT:
		case BOOLEAN:
		case STRING:
			return assignExprLocalDec();
		case RETURN:
			return returnStatement();
		case READ:
			return readStatement();
		case WRITE:
			return writeStatement();
		case WRITELN:
			return writelnStatement();
		case IF:
			return ifStatement();
		case BREAK:
			
			//TODO: ER-SEM26.KRA
			//break; só pode existir dentro de While
			
			return breakStatement();
		case WHILE:
			return whileStatement();
		case SEMICOLON:
			nullStatement();
		case LEFTCURBRACKET:
			return compositeStatement();
		default:
			signalError.show("Statement expected");
		}
		return null;
	}


//	retorne true se 'name' Ã© uma classe declarada anteriormente. Ã‰ necessÃ¡rio fazer uma busca na tabela de sÃ­mbolos para isto.
	private boolean isType(String name) {
		return this.symbolTable.getInGlobal(name) != null;
	}


//	LocalDec ::= Type IdList ";"
	private DeclarationStatement localDec() {

		Type type = type();
		if ( lexer.token != Symbol.IDENT ) signalError.show("Identifier expected");
		String name = lexer.getStringValue();
		VariableList variableList = new VariableList();
		Variable variable = new Variable(name, type);
		if ( symbolTable.getInLocal(name) != null ) signalError.show("Variable already declared");
		variableList.addElement(variable);
		symbolTable.putInLocal(name, variable);
		lexer.nextToken();
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			variable = new Variable(lexer.getStringValue(), type);
			if ( symbolTable.getInLocal(name) != null ) signalError.show("Variable already declared");
			variableList.addElement(variable);
			symbolTable.putInLocal(name,variable);
			lexer.nextToken();
		}
		if (lexer.token != Symbol.SEMICOLON){
			signalError.show("Invalid Character: " + lexer.token);
		}
		return new DeclarationStatement(type, variableList);
	}

//  AssignExprLocalDec ::= Expression [ "=" Expression ] | LocalDec
	private Statement assignExprLocalDec() {

		if ( lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN || lexer.token == Symbol.STRING ||
		   ( lexer.token == Symbol.IDENT && isType(lexer.getStringValue())) ) {
			 return localDec();
		}
		
		//TODO: ER-SEM02.KRA
		//int i; I = 0;	variável I não foi declarada

		else {
			Expr exprLeft, exprRight = null;
			exprLeft = expr();
			if ( lexer.token == Symbol.ASSIGN ) {
				lexer.nextToken();
				exprRight = expr();
				
				//TODO: ER-SEM04.KRA, ER-SEM05.KRA, ER-SEM38.KRA
				//verificar se o lado direito do simbolo '=' é um subtipo do lado esquerdo (o contrário não vale)
				
				//TODO: ER-SEM36.KRA
				//O lado direito do símbolo '=' tem que ter pelo menos uma expressão e não pode ser um método void
				
				//TODO: ER-SEM41.KRA, ER-SEM42.KRA
				//O lado direito da expressão não pode ser um tipo básico quando o lado esquerdo for do tipo de uma Classe, e vice-versa
				
				//TODO: ER-SEM43.KRA
				//não pode fazer um tipo básico = null... int i = null;
				
				if (!checkAssignment(exprLeft.getType(), exprRight.getType())) {
					signalError.show("Types do not match");
				}
				if (lexer.token != Symbol.SEMICOLON)
					signalError.show("';' expected", true);
				else
					lexer.nextToken();
			}
			return new AssignmentStatement(exprLeft, exprRight);
		}
	}

	private boolean checkAssignment(Type typeLeft, Type typeRight){
		return (typeLeft == typeRight && typeLeft.getName().equals(typeRight.getName()));
	}

//	whileStatement ::= "while" "(" Expression ")" statement
	private WhileStatement whileStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		Expr expr = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		
		//TODO: ER-SEM11.KRA
		//Verificar se o tipo da expressão resulta em booleano
		
		lexer.nextToken();
		Statement statement = statement();
		return new WhileStatement(expr,statement);
	}

//	ifStatement ::= "if" "(" Expression ")" Statement [ "else" statement]
	private IfStatement ifStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		Expr expr = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		
		//TODO: ER-SEM11.KRA
		//Verificar se o tipo da expressão resulta em booleano
		
		lexer.nextToken();
		Statement ifStatement = statement();
		Statement elseStatement = null;
		if ( lexer.token == Symbol.ELSE ) {
			lexer.nextToken();
			elseStatement = statement();
		}
		return new IfStatement(expr, ifStatement, elseStatement);
	}

//	returnStatement ::= "return" Expression
	private ReturnStatement returnStatement() {

		//TODO: ER-SEM35.KRA
		//métodos void não podem retornar nada
		
		lexer.nextToken();
		Expr expr = expr();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		
		//TODO: ER-SEM39.KRA
		//A expressão retornada deve ser um subtipo do tipo do método (ou de preferência o mesmo tipo né)
		
		lexer.nextToken();
		return new ReturnStatement(expr);
	}

//	readStatement ::= "read" "(" LeftValue { "," LeftValue } ")"
	private ReadStatement readStatement() {

		VariableList variableList = new VariableList();
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		while (true) {
			if ( lexer.token == Symbol.THIS ) {
				lexer.nextToken();
				if ( lexer.token != Symbol.DOT ) signalError.show(". expected");
				lexer.nextToken();
			}
			if ( lexer.token != Symbol.IDENT )
				signalError.show(SignalError.ident_expected);

			String name = lexer.getStringValue();
			
			//TODO: ER-SEM13.KRA
			//comando read não aceita variáveis booleanas como argumento
			
			//TODO: ER-SEM44.KRA
			//comando read só pode aceitar argumentos do tipo String ou int
			
			variableList.addElement(symbolTable.getInLocal(name));
			lexer.nextToken();
			if ( lexer.token == Symbol.COMMA )
				lexer.nextToken();
			else
				break;
		}

		if ( lexer.token != Symbol.RIGHTPAR ){
			signalError.show(") expected");
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new ReadStatement(variableList);
	}

//	writeStatement ::= "write" "(" ExpressionList ")"
	private WriteStatement writeStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		ExprList exprList = exprList();
		
		//TODO: ER-SEM14.KRA
		//comando write não aceita expressões booleanas como argumento
		
		//TODO: ER-SEM44.KRA
		//comando write não aceita objetos como argumento
		
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new WriteStatement(exprList);
	}

//	writelnStatement ::= "writeln" "(" ExpressionList ")"
	private WriteLnStatement writelnStatement() {

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		ExprList exprList = exprList();
		
		//TODO: ER-SEM14.KRA
		//comando write não aceita expressões booleanas como argumento
		
		//TODO: ER-SEM44.KRA
		//comando write não aceita objetos como argumento
		
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new WriteLnStatement(exprList);
	}

	private BreakStatement breakStatement() {
		
		//TODO: ER-SEM50.KRA
		//break; só pode existir dentro de Whiles
		
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(SignalError.semicolon_expected);
		lexer.nextToken();
		return new BreakStatement();
	}

	private void nullStatement() {
		lexer.nextToken();
	}

// ExpressionList ::= Expression { "," Expression }
	private ExprList exprList() {

		ExprList anExprList = new ExprList();
		anExprList.addElement(expr());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			anExprList.addElement(expr());
		}
		return anExprList;
	}

//	Expression ::= SimpleExpression [ Relation  SimpleExpression]
	private Expr expr() {

		Expr left = simpleExpr();
		Symbol op = lexer.token;
		if ( op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE || op == Symbol.LT || op == Symbol.GE || op == Symbol.GT ) {
			lexer.nextToken();
			Expr right = simpleExpr();
			
			//TODO: ER-SEM57.KRA, ER-SEM58.KRA
			//tipos diferentes não podem ser comparados com '==' nem '!=', nesses casos vai sempre retornar falso
			
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

//	SimpleExpression ::= Term { LowOperator Term}
	private Expr simpleExpr() {
		Symbol op;
		Expr left = term();
		while ((op = lexer.token) == Symbol.MINUS || op == Symbol.PLUS || op == Symbol.OR) {
			lexer.nextToken();
			Expr right = term();
			
			//TODO: ER-SEM08.KRA, ER-SEM12.KRA
			//verificar o tipo dos dois lados da da soma pra saber se os tipos são compatíveis... (true + false) tá errado
			
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

// Term ::= SignalFactor { HighOperator SignalFactor }
	private Expr term() {

		Symbol op;
		Expr left = signalFactor();
		while ((op = lexer.token) == Symbol.DIV || op == Symbol.MULT || op == Symbol.AND) {
			lexer.nextToken();
			Expr right = signalFactor();
			
			//TODO: ER-SEM09.KRA
			//Verificar os tipos dos dois lados... int não suporta o operador &&
			
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

//	SignalFactor ::= [ Signal ] Factor
	private Expr signalFactor() {

		Symbol op;
		if ( (op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS ) {
			lexer.nextToken();
			
			//TODO: ER-SEM16.KRA
			//Operadores + e - não aceitam expressões booleanas... boolean b = false; write(-b);
			
			return new SignalExpr(op, factor());
		}
		else
			return factor();
	}

	/*
	 * Factor ::= BasicValue | "(" Expression ")" | "!" Factor | "null" | ObjectCreation | PrimaryExpr
	 * 
	 * BasicValue ::= IntValue | BooleanValue | StringValue 
	 * BooleanValue ::=  "true" | "false" 
	 * ObjectCreation ::= "new" Id "(" ")" 
	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 Id  |
	 *                 Id "." Id | 
	 *                 Id "." Id "(" [ ExpressionList ] ")" |
	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
	 *                 "this" | 
	 *                 "this" "." Id | 
	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
	 */
	private Expr factor() {

		Expr e;
		ExprList exprList;
		String messageName, identifier;

		switch (lexer.token) {

//		IntValue
		case LITERALINT:
			return literalInt();

//		BooleanValue
		case FALSE:
			lexer.nextToken();
			return LiteralBoolean.False;

//		BooleanValue
		case TRUE:
			lexer.nextToken();
			return LiteralBoolean.True;

//		StringValue
		case LITERALSTRING:
			String literalString = lexer.getLiteralStringValue();
			lexer.nextToken();
			return new LiteralString(literalString);

//		"(" Expression ")" |
		case LEFTPAR:
			lexer.nextToken();
			e = expr();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
			lexer.nextToken();
			return new ParenthesisExpr(e);

//		"null"
		case NULL:
			lexer.nextToken();
			return new NullExpr();

//		"!" Factor
		case NOT:
			lexer.nextToken();
			e = expr();
			
			//TODO: ER-SEM15.KRA
			//verificar se a expr é do tipo booleano
			if(!e.getType().equals(Type.booleanType))
				signalError.show("Operator '!' does not accepts '"+ e.getType().getName() +"' values");
			
			
			return new UnaryExpr(e, Symbol.NOT);

//		ObjectCreation ::= "new" Id "(" ")"
		case NEW:
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");

			String className = lexer.getStringValue();
			KraClass kraClass = symbolTable.getInGlobal(className);
			if (kraClass == null) signalError.show(className+" was not declared");
			lexer.nextToken();
			if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
			lexer.nextToken();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
			lexer.nextToken();
			return new ObjectExpr(kraClass);

// 		PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"
		case SUPER:
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				signalError.show("'.' expected");
			}
			else
				lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show("Identifier expected");
			messageName = lexer.getStringValue();
			
			//TODO: ER-SEM46.KRA
			//verificar se essa classe tem mesmo uma superclasse
			
			//TODO: ER-SEM47.KRA
			//Verificar se a superclasse tem mesmo esse método, ou se suas superclasses têm (fazer a busca pelo método na árvore de hierarquia)
			
			//TODO: ER-SEM59.KRA
			//Verificar se na superclasse o método é publico
			
			lexer.nextToken();
			exprList = realParameters();
			if (currentMethod.isStatic()){
				signalError.show("super can not be called in a static method");
			}
			MessageSendToSuper messageSendToSuper = new MessageSendToSuper(exprList, messageName);
			if (messageSendToSuper.validate(symbolTable.getInGlobal(currentClass))){
				signalError.show("method was not declared");
			}
			return messageSendToSuper;

// 		PrimaryExpr ::= Id  |
// 		PrimaryExpr ::= Id "." Id |
//		PrimaryExpr ::= Id "." Id "(" [ ExpressionList ] ")" |
// 		PrimaryExpr ::= Id "." Id "." Id "(" [ ExpressionList ] ")"
		case IDENT:

			MessageSendToVariable messageSendToVariable = null;
			String firstId = lexer.getStringValue();
			lexer.nextToken();
			Variable firstVariable = symbolTable.getInLocal(firstId);
			if ( lexer.token != Symbol.DOT ) {
				// Id
				return new VariableExpr(firstVariable);
			}
			
			//TODO: ER-SEM07.KRA
			//verificar se o tipo da variável aceita uma chamada de método... int i; i.run();
			if( !firstVariable.getType().equals(Type.kraClass) )
				signalError.show("Message send to a non-object receiver");
			
			//TODO: ER-SEM62.KRA
			//essa não pode ser uma variável de instância... variáveis de instância só podem ser usadas com o this.
			
			

			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT ) {
				signalError.show("Identifier expected");
			}
			else {

				identifier = lexer.getStringValue();
				
				//TODO: ER-SEM37.KRA, ER-SEM61.KRA
				//Verificar se o método foi declarado na classe, ou em uma de suas superclasses
				
				//TODO: ER-SEM59.KRA
				//Verificar se o método é publico
				
				lexer.nextToken();
				if ( lexer.token == Symbol.DOT ) {
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						signalError.show("Identifier expected");
					messageName = lexer.getStringValue();
					lexer.nextToken();
					exprList = this.realParameters();
					// Id "." Id "." Id "(" [ ExpressionList ] ")"
					messageSendToVariable =  new MessageSendToVariable(firstId, identifier, messageName,exprList);
					if (symbolTable.getInGlobal(firstId) == null){
						signalError.show(firstId+" was not declared");
					}
					if (!messageSendToVariable.validateInstance(symbolTable.getInGlobal(firstId))){
						signalError.show(firstId+" does not have an static instance called "+ identifier);
					}
					if (!messageSendToVariable.validateIndentifierMessage()){
						signalError.show(identifier+" does not have a method called "+ messageName);
					}

				}
				else if ( lexer.token == Symbol.LEFTPAR ) {

					exprList = this.realParameters();
					
					//TODO: ER-SEM40.KRA
					//Verificar se o tipo do parâmetro enviado é subtipo do parâmetro na declaração do método
					
					// Id "." Id "(" [ ExpressionList ] ")"
					messageSendToVariable =  new MessageSendToVariable(firstId, identifier, exprList);
					if (symbolTable.getInGlobal(firstId) == null){
						if (symbolTable.getInLocal(firstId) == null) {
							signalError.show(firstId + " was not declared");
						}
						signalError.show(firstId + " was not declared");
					}
					if (!messageSendToVariable.validateMethodMessage(symbolTable.getInGlobal(firstId), symbolTable.getInGlobal(symbolTable.getInLocal(firstId).getName()))){
						if (symbolTable.getInGlobal(firstId) == null){
							if (symbolTable.getInLocal(firstId) == null) {
								signalError.show(firstId + " does not have the method "+ identifier);
							}
							signalError.show(symbolTable.getInLocal(firstId).getType() + " does not have the method "+ identifier);
						}
					}
				}
				else {// Id "." Id
					messageSendToVariable =  new MessageSendToVariable(firstId, identifier);
					if (symbolTable.getInGlobal(firstId) == null){
						signalError.show(firstId+" was not declared");
					}
					if (!messageSendToVariable.validateInstance(symbolTable.getInGlobal(firstId))){
						signalError.show(firstId+" does not have an static instance called "+ identifier);
					}
				}
			}
			return messageSendToVariable;

// 		PrimaryExpr ::= "this" |
// 		PrimaryExpr ::= "this" "." Id |
// 		PrimaryExpr ::= "this" "." Id "(" [ ExpressionList ] ")"  |
// 		PrimaryExpr ::= "this" "." Id "." Id "(" [ ExpressionList ] ")"
		case THIS:

			if (currentMethod.isStatic()){
				signalError.show("This can not be called in a static method");
			}
			MessageSendToSelf messageSendToSelf;
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				return new ObjectExpr(symbolTable.getInGlobal(currentClass));
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT )
					signalError.show("Identifier expected");
				identifier = lexer.getStringValue();
				lexer.nextToken();
				if ( lexer.token == Symbol.LEFTPAR ) {
					exprList = this.realParameters();
					messageSendToSelf = new MessageSendToSelf(symbolTable.getInGlobal(currentClass),identifier,exprList);
					if (messageSendToSelf.validateClassMessage()){
						signalError.show(currentClass+" does not have a method called "+identifier);
					}
				}
				else if ( lexer.token == Symbol.DOT ) {
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						signalError.show("Identifier expected");
					messageName = lexer.getStringValue();
					lexer.nextToken();
					exprList = this.realParameters();
					if (symbolTable.getInInstance(identifier) == null){
						signalError.show("Instance Variable "+identifier+" not declared");
					}
					messageSendToSelf = new MessageSendToSelf(symbolTable.getInGlobal(currentClass),symbolTable.getInInstance(identifier), messageName, exprList, symbolTable.getInGlobal(symbolTable.getInInstance(identifier).getType().getName()));
					if (!messageSendToSelf.validateInstanceMessage()){
						signalError.show("Instance Variable "+identifier+" can not access method "+ messageName);
					}
				}
				else {
					if (symbolTable.getInInstance(identifier) == null){
						signalError.show("Instance Variable "+identifier+" not declared");
					}
					messageSendToSelf = new MessageSendToSelf(symbolTable.getInGlobal(currentClass),symbolTable.getInInstance(identifier));
				}
			}
			
			//TODO: 
			//Verificar se esse atributo existe nessa classe
			
			return messageSendToSelf;

		default:
			signalError.show("Expression expected");
		}
		return null;
	}

	private LiteralInt literalInt() {

		int value = lexer.getNumberValue();
		lexer.nextToken();
		return new LiteralInt(value);
	}

	private static boolean startExpr(Symbol token) {

		return token == Symbol.FALSE || token == Symbol.TRUE
				|| token == Symbol.NOT || token == Symbol.THIS
				|| token == Symbol.LITERALINT || token == Symbol.SUPER
				|| token == Symbol.LEFTPAR || token == Symbol.NULL
				|| token == Symbol.IDENT || token == Symbol.LITERALSTRING;

	}

	private ExprList realParameters() {

		ExprList anExprList = null;
		if ( lexer.token != Symbol.LEFTPAR ) signalError.show("( expected");
		lexer.nextToken();
		if ( startExpr(lexer.token) ) anExprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.show(") expected");
		lexer.nextToken();
		return anExprList;
	}


	private Method			currentMethod;
	private String			currentClass;
	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private SignalError		signalError;

}