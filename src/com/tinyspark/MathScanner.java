package com.tinyspark;

import java.util.StringTokenizer;

/**
 * Created by Nypro on 05-Oct-16.
 */
public class MathScanner {
	public static String[] scan (String input){
		StringTokenizer strTok = new StringTokenizer(input, " +-=/*^():{}", true);
		String output[] = new String[strTok.countTokens()];
		int count = 0;
		while(strTok.hasMoreTokens()){
			output[count] = strTok.nextToken();
		}
		return output;
	}
}
