package com.tinyspark;

/**
 * Created by Nypro on 06-Oct-16.
 */
public class NumExprAST extends ExprAST {
	public double value;
	public NumExprAST(double value){
		this.value = value;
	}
}
