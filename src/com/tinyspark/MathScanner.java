package com.tinyspark;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Nypro on 05-Oct-16.
 */
public class MathScanner {
	static private String input;
	static private int charIndex;
	static private int inputLength;
	static final char operators[] = {'=', '-', '+', '*', '\\', '^'};
	static final char punctuators[] = {':', '}', '(', ')', ','};

	private static char getChar(){
		if(++charIndex < inputLength){
			return input.charAt(charIndex);
		}
		return '\0'; // null character
	}

	public static ArrayList<Token> scan(final String input){
		MathScanner.input = input;
		inputLength = input.length();
		charIndex = -1;
		ArrayList<Token> tokens = new ArrayList<>();

		char character = getChar(); // if the null character is returned which means end of input, loop and function are exited

		while(true){
			// Spill means, getting the next character after a successful identification of previous character.
			// If there was no successful identification of particular character, it 'spills' automatically until it
			// reaches the Others section.
			// The Others section checks if the character is a topCharacter, if it is the loop continues
			// and the character is used for next identification test. If the character is not a topCharacter
			// it is saved as in tokens as Others.

			// Whitespace
			while(character == ' '){ // spill
				System.out.println("index[" + charIndex + "] | ->! ([space])"); //DEBUG//
				character = getChar();
				if(character == '\0') break;
			}

			// Identifier
			if (Character.isAlphabetic(character) || character == '_'){ // spill
				String idenStr = "";
				do{
					idenStr += character;
					character = getChar();
					if(character == '\0') break;
				}
				while(Character.isAlphabetic(character)
						|| Character.isDigit(character)
						|| character == '_'); // spill
				System.out.println("index[" + charIndex + "] | ->? (" + idenStr + ")"); //DEBUG//
				tokens.add(new Token(idenStr, TokenType.Identifier));
			}

			// Number
			String numStr = "";
			while(Character.isDigit(character)){ // spill
				numStr += character;
				character = getChar();
				if(character == '\0') break;
			}
			if(!numStr.isEmpty()) {
				System.out.println("index[" + charIndex + "] | ->@ (" + numStr + ")"); //DEBUG//
				tokens.add(new Token(numStr, TokenType.Number));
			}

			// Operator
			for(char x : operators){
				if(character == x){
					System.out.println("index[" + charIndex + "] | ->^ (" + character + ")"); //DEBUG//
					tokens.add(new Token("" + character, TokenType.Operator));
					character = getChar(); // spill
					break;
				}
			}

			// Punctuator
			for(char x : punctuators){
				if(character == x){
					System.out.println("index[" + charIndex + "] | ->* (" + character + ")"); //DEBUG//
					tokens.add(new Token("" + character, TokenType.Punctuator));
					character = getChar(); // spill
					break;
				}
			}

			// Newline
			if(character == '\n'){
				System.out.println("index[" + charIndex + "] | ->% (" + character + ")"); //DEBUG//
				tokens.add(new Token("" + character, TokenType.Newline));
				character = getChar(); // spill
			}else if(character == '\r'){
				String nl = "";
				nl += character = getChar();
				if(character == '\0') tokens.add(new Token(nl, TokenType.Newline)); // spill
			}

			// Nil
			// If nil then there is no more content to scan
			if(character == '\0') break;


			// Others
			if(!isTopCharacter(character)) {
				System.out.println("index[" + charIndex + "] | -># (" + character + ")"); //DEBUG//
				tokens.add(new Token("" + character, TokenType.Others));
				character = getChar();
			}
		}
		System.out.println("tokens size ---> " + tokens.size()); //DEBUG//
		return tokens;
	}

	static public boolean isTopCharacter(char c){
		// Whitespace // Identifier // Number // Newline
		if(c == ' ' || Character.isAlphabetic(c) || c == '_' || Character.isDigit(c) || c == '\n' || c == '\r'){
			return true;
		}
		// Operator
		for(char x : operators){
			if(c == x){
				return true;
			}
		}
		// Punctuator
		for(char x : punctuators){
			if(c == x){
				return true;
			}
		}
		return false;
	}
}
