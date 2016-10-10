package com.tinyspark;

import java.io.File;
import java.io.FileNotFoundException;
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
            Scanner scanFile = null;
            try{
                scanFile = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();
                MathParser parser = new MathParser();
                while(scanFile.hasNext()){
                    fileContent.append(scanFile.nextLine());
                }
                // parser.parse(fileContent.toString()); // TODO: returns ByteCode[]
            }catch (FileNotFoundException fnfe){
                fnfe.printStackTrace();
            }finally {
                scanFile.close();
            }
        }

        cmdManager();
    }

    public static void cmdManager(){
        MathParser parser = new MathParser();
        Scanner input = new Scanner(System.in);
        // parser.parse(input); // TODO: returns ByteCode[]
        System.out.printf("MathJunkie >>> ");
        while(input.hasNext()){
            System.out.println(input.nextLine() + " DONE!"); // TODO: Parsing and eval is done here
            System.out.printf("MathJunkie >>> ");
        }
    }
}
