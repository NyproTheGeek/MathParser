package com.tinyspark;

import com.tinyspark.bytecode.ByteCode;
import com.tinyspark.exceptions.NewlineBounce;
import com.tinyspark.exceptions.SyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nypro on 05-Oct-16.
 */

// GRAMMAR
// idenExpr ::= iden rhs
// numExpr ::= num rhs
// parensExpr ::= '(' expr ')' rhs
// rhs ::= op expr | ϵ
// op ::= '=' | '+' | '-' | '*' | '/' | '^'
// expr ::= idenExpr | numExpr | parensExpr | funcExpr
// funcName := 'sq' | 'cu' | 'rt'
// funcCallExpr ::= ':' funcName params
// params ::= '{' expr args '}' | ϵ
// args ::= ',' expr | args | ϵ
// ----------------------------------------
// a
// a + ...
// 1
// 1 + ...
// ( ... )
// :func{1, a}
// :func{24} + ...
// \n
// EXAMPLE AST STRUCTURE
// a + 1 - 2
// binAST -> op idenAST exprAST (-> binAST (-> op numAST numAST ) )
// -----------------------------------------
// A parse function should handle its own error, i.e, errors
// relating to its tokens.
// A parse function should call getToken after its last token
// this puts the next token in the buffer for the next parse
// function to parse.
public class MathParser {
	private static ArrayList<Token> tokens = null;
	private static int tokensSize;
	private static int tokenIndex;
	private static Token curToken;
	private static HashMap<String, Integer> binOpPrec = new HashMap<>();

	private static void initBinOpPrec(){
		binOpPrec.put("=", 10);
		binOpPrec.put("+", 20);
		binOpPrec.put("-", 20);
		binOpPrec.put("*", 30);
		binOpPrec.put("\\", 30);
		binOpPrec.put("^", 40);
	}

	private static void getToken(){
		if(++tokenIndex < tokensSize){
			curToken = tokens.get(tokenIndex);
		}
		else curToken = null;
	}

	public static ExprAST parse(final ArrayList<Token> tokens){
		MathParser.tokens = tokens;
		tokensSize = tokens.size();
		tokenIndex = -1;
		ExprAST exprAST;
		initBinOpPrec();
		getToken();
		if(curToken == null)
			throw new SyntaxException("Nothing to parse!");

		exprAST = parseExpr(false);
		return exprAST;
	}

	private static ExprAST parsePrimary(boolean mid){
		// idenExpr ::= iden rhs
		// numExpr ::= num rhs
		// parensExpr ::= '(' expr ')' rhs
		// rhs ::= op expr | ϵ
		// expr ::= idenExpr | numExpr | parensExpr | funcExpr
		if(curToken == null)
			throw new SyntaxException("Expected an expression!");

		ExprAST exprAST = new ExprAST();
		switch(curToken.kind){
			case Identifier:
				System.out.println("This is an Identifier"); //DEBUG//
				exprAST = parseIdenExpr();
				break;
			case Number:
				System.out.println("This is a Number"); //DEBUG//
				exprAST = parseNumExpr();
				break;
			case Punctuator:
				System.out.println("This is a Punctuator"); //DEBUG//
				if(curToken.str.equals("(")) exprAST = parseParensExpr();
				if(curToken.str.equals(":")) exprAST = parseFuncCallExpr();
				break;
			case Newline:
				System.out.println("This is a Newline"); //DEBUG//
				throw new NewlineBounce();
			case Others:
				System.out.println("Others!"); //DEBUG//
				throw new SyntaxException("Expected an expression!");
			default: // For legitimate tokens like operators but that appear where they are not expected.
				System.out.println("Inappropriate Location!"); //DEBUG//
				throw new SyntaxException("Expected an expression!");
		}
		return exprAST;
	}

	private static ExprAST parseExpr(boolean mid){
		// idenExpr ::= iden rhs
		// numExpr ::= num rhs
		// rhs ::= op expr | ϵ
		// a
		// a + ...
		// 1
		// 1 + ...
		// ( ... )
		// :func{1, a}
		// :func{24} + ...
		// \n
		ExprAST lhs = parsePrimary(mid);
		if(curToken == null || curToken.kind == TokenType.Newline) {
			System.out.println("Single expression"); //DEBUG//
			return lhs;
		}
		System.out.println("Possible binary expression"); //DEBUG//
		return parseBinExpr(0, lhs);
	}

	private static IdenExprAST parseIdenExpr(){
		// idenExpr ::= iden rhs
		// rhs ::= op expr | ϵ
		// a
		IdenExprAST idenExprAST = new IdenExprAST(curToken.str);
		// Exception handled by parsePrimary
		getToken(); // eat iden
		return idenExprAST;
	}

	private static NumExprAST parseNumExpr(){
		// numExpr ::= num rhs
		// rhs ::= op expr | ϵ
		// 1
		NumExprAST numExprAST = new NumExprAST(Double.valueOf(curToken.str));
		// Exception handled by parsePrimary
		getToken(); // eat num
		return numExprAST;
	}

	private static ExprAST parseParensExpr(){
		// parensExpr ::= '(' expr ')' rhs
		// ( ... )
		// Exception handled by parsePrimary
		getToken(); // eat "("
		ExprAST exprAST = parseExpr(true);
		if(curToken == null || !curToken.str.equals(")"))
			throw new SyntaxException("Expected a ')'!");
		getToken(); // eat ")"
		return exprAST;
	}

	private static FuncCallExprAST parseFuncCallExpr(){
		getToken(); // eat ":"
		String funcName;
		if(curToken == null || curToken.kind != TokenType.Identifier)
			throw new SyntaxException("Expected function name!");
		funcName = curToken.str;
		System.out.println("This is possibly a function"); //DEBUG//

		ArrayList<ExprAST> args = new ArrayList<>();
		getToken(); // eat funcName

		if(curToken == null || !curToken.str.equals("{"))
			throw new SyntaxException("Expected a '{'");
		getToken(); // eat "{"

		while(!curToken.str.equals("}")){
			args.add(parseExpr(true));
			if(curToken == null) throw new SyntaxException("Expected a '}'");
			if(curToken.str.equals(",")) getToken(); // eat ","
			if(curToken == null) throw new SyntaxException("Expected a '}'");
		}
		getToken(); // eat "}"

		return new FuncCallExprAST(funcName, args);
	}

	private static ExprAST parseBinExpr(int op1Prec, ExprAST lhs){
		if(curToken.str.equals(",")
				|| curToken.str.equals("}")
				|| curToken.str.equals(")")) // for functions, bracketed exprs, etc // step out // "," is not eaten
			return lhs;
		// op1 lhs op2 rhs op3
		while(true) {
			if (curToken.kind != TokenType.Operator) throw new SyntaxException("Expected a binary operator");
			String op2 = curToken.str;
			int op2prec = binOpPrec.get(curToken.str);
			System.out.println("This is a Operator"); //DEBUG//

			if (op2prec < op1Prec) // op1[>=] lhs) op2[<]
				return lhs;
			getToken(); // eat op2

			ExprAST rhs = parsePrimary(true);

			if (curToken == null
					|| curToken.kind == TokenType.Newline
					|| curToken.str.equals(",")
					|| curToken.str.equals("}")
					|| curToken.str.equals(")")) // op2 rhs) // no eat // NEEDS REVIEW!
				return new BinExprAST(op2, lhs, rhs);

			if (curToken.kind != TokenType.Operator) throw new SyntaxException("Expected a binary operator");
			String op3 = curToken.str;
			int op3prec = binOpPrec.get(curToken.str);
			System.out.println("This is a Operator"); //DEBUG//

			if (op2prec < op1Prec) // op1[>=] lhs) op2[<]
				rhs = parseBinExpr(op2prec, rhs);
			lhs = new BinExprAST(op2, lhs, rhs);
		}
	}
}
