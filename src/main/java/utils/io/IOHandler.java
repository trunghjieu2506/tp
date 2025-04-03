package utils.io;

import java.util.Scanner;

//this is a class that handles all the input and output
//it needs a scanner from the main method
//just throw everything related to System.out.println/input to this class
//this ensures that our code does not violate OOP rules
public class IOHandler {
    private Scanner scanner;

    public IOHandler (Scanner scanner){
        this.scanner = scanner;
    }

    public String readInput(){
        String userInput = scanner.nextLine();
        //can process the string here
        return userInput;
    }

    public static void writeOutput(String output){

        //add in necessary stuff
        System.out.println(output);
    }
}
