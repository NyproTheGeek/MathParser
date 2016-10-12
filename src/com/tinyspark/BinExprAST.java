package com.tinyspark;

/**
 * Created by Nypro on 06-Oct-16.
 */
public class BinExprAST {
	public String op;
	public ExprAST lhs, rhs;
	public BinExprAST(String op, ExprAST lhs, ExprAST rhs){
		this.op = op;
		this.lhs = lhs;
		this.rhs = rhs;
	}

}
