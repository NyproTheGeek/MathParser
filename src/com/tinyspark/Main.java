package com.tinyspark;

import java.io.File;
import java.util.Scanner;

public class Main {

    // C:\Users\Nypro\Desktop\Code\Java Projects\MathParser
    public static void main(String[] args) {
        System.out.println("Press 1 to enter filename\nOR\nPress any other number for console: ");
        Scanner input = new Scanner(System.in);
        if(input.nextInt() == 1){
            input.nextLine(); // To clear prev buffer, the \n esp.
            File file  = new File(input.nextLine());
            System.out.println(file.getName()); // TODO: Test Only
        }
        for ( char ch = 'a'; ch <= 'z'; ch++){
            System.out.print("\'" + ch +  "\' | ");
        }
        System.out.println("");
        for ( char ch = 'A'; ch <= 'Z'; ch++){
            System.out.print("\'" + ch +  "\' | ");
        }
    }
}
