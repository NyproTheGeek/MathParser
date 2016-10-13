package com.tinyspark;

import java.util.ArrayList;

/**
 * Created by Nypro on 06-Oct-16.
 */
public class FuncCallExprAST extends ExprAST{
	String callee;
	ArrayList<ExprAST> args;
	public FuncCallExprAST(String name, ArrayList<ExprAST> args){
		this.callee = name;
		this.args = args;
	}
}
