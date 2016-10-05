package com.tinyspark;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Press 1 to enter filename\nOR\nPress any other number for console: ");
        Scanner input = new Scanner(System.in);
        if(input.nextInt() == 1){
            File file  = new File(input.next());
            System.out.println(file.getName());
        }
    }
}
