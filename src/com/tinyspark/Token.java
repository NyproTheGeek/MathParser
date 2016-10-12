package com.tinyspark;

/**
 * Created by Nypro on 10-Oct-16.
 */
public class Token {
	String str;
	TokenType kind;
	public Token(String str, TokenType kind){
		this.str = str;
		this.kind = kind;
	}
}
