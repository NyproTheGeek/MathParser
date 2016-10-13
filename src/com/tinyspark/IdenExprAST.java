package com.tinyspark;

import java.net.IDN;

/**
 * Created by Nypro on 06-Oct-16.
 */
public class IdenExprAST extends ExprAST {
	public String name;
	public IdenExprAST(String name){
		this.name = name;
	}
}
