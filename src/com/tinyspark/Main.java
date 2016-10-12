package com.tinyspark;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // C:\Users\Nypro\Desktop\Code\Java Projects\MathParser
    public static void main(String[] args) {
//        System.out.println("Press 1 to enter filename\nOR\nPress any other number for console: ");
//        Scanner input = new Scanner(System.in);
//
//        if(input.nextInt() == 1){
//            input.nextLine(); // To clear prev buffer, the \n esp.
//            File file  = new File(input.nextLine());
//            System.out.println(file.getName()); // TODO: Test Only
//            Scanner scanFile = null;
//            try{
//                scanFile = new Scanner(file);
//                StringBuilder fileContent = new StringBuilder();
//                MathParser parser = new MathParser();
//                while(scanFile.hasNext()){
//                    fileContent.append(scanFile.nextLine());
//                }
//            }catch (FileNotFoundException fnfe){
//                fnfe.printStackTrace();
//            }finally {
//                scanFile.close();
//            }
//        }

        cmdManager();
    }

    public static void cmdManager(){
        ArrayList<Token> tokens = new ArrayList();
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.printf("mathopia >>> ");
            tokens = MathScanner.scan(input.nextLine());
            for(Token x : tokens){
                System.out.println(x.str + " "); ////
            }
            System.out.println("DONE!"); ////
        }
    }
}
