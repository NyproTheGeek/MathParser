package com.tinyspark;

/**
 * Created by Nypro on 06-Oct-16.
 */
public class FuncCallExprAST extends ExprAST{
	String callee;
	ExprAST args [];
	public FuncCallExprAST(String name, ExprAST args[]){
		this.callee = name;
		this.args = args;
	}
}
