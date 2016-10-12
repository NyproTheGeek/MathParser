package com.tinyspark;

import com.tinyspark.bytecode.ByteCode;

import java.util.ArrayList;

/**
 * Created by Nypro on 05-Oct-16.
 */
public class MathParser {
	private static ArrayList<Token> tokens = null;
	private static int tokensSize;
	private static int tokenIndex;
	private static Token curToken;

	private static void getToken(){
		if(++tokenIndex < tokens.size()){
			curToken = tokens.get(tokenIndex);
		}
		else curToken = null;
	}

	public static ArrayList<ExprAST> parse(final ArrayList<Token> tokens){
		MathParser.tokens = tokens;
		tokensSize = tokens.size();
		tokenIndex = -1;
		ArrayList<ExprAST> asts = new ArrayList();

		getToken(); //
		if(curToken == null || curToken.kind == TokenType.Newline)
			curToken = tokens ;
		return asts;
	}

	private static ExprAST parsePrimary(boolean mid){
		// idenExpr ::= iden rhs
		// numExpr ::= num rhs
		// parensExpr ::= '(' expr ')' rhs
		// rhs ::= op expr | ϵ
		// expr ::= idenExpr | numExpr | parensExpr | funcExpr
		ExprAST exprAST = new ExprAST();
		switch(curToken.kind){

		}
		return exprAST;
	}
}
