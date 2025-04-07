package utils.io;

import utils.textcolour.TextColour;

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

    public static void writeWarning(String output) {
        System.out.println(TextColour.YELLOW + output + TextColour.RESET);
    }

    public static void writeError(String error){
        System.err.println(error);
        System.err.flush();
    }

    public static void writeOutputNoLn(String output){
        System.out.print(output);
    }

    public static void writeOutputWithColour(String output, String colour){
        String colouredString = colour + output + TextColour.RESET;
        System.out.println(colouredString);
    }

    public static void flushOutput(){
        System.out.flush();
    }

    public static void flushError(){
        System.err.flush();
    }
}
