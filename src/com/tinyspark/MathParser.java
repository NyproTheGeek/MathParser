package com.tinyspark;

import com.tinyspark.bytecode.ByteCode;

import java.util.ArrayList;

/**
 * Created by Nypro on 05-Oct-16.
 */
public class MathParser {
	String tokens [] = null;
	int lookahead = 0;
	public ByteCode[] parse(final String input){
		ArrayList<ByteCode> bytecode;
		tokens = MathScanner.scan(input);
		if(tokens == null || tokens.length == 0){
			return  null;
		}

		return null;
	}

	private ExprAST parseBinExpr(){ return new ExprAST();}
	private ExprAST parseCallExpr(){return new ExprAST();}
	private ExprAST parseNumberExpr(){return new ExprAST();}
	private ExprAST parseWordExpr(){return new ExprAST();}
}
