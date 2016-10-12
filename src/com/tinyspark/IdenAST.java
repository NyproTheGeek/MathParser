package com.tinyspark;

import java.net.IDN;

/**
 * Created by Nypro on 06-Oct-16.
 */
public class IdenAST extends ExprAST {
	public String name;
	public IdenAST(String name){
		this.name = name;
	}
}
