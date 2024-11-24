package PrintTime;

import static PrintTime.TimePercentage.clearTerminal;
import static PrintTime.TimePercentage.printDayPercentage;

import java.util.Scanner;

public class TimePercentageMain {
    public static void main(String[] args) {
        boolean alsoPrintNormal = false;
        double minDelayInSeconds = 0.5; // if (<0.5) delay = 0.5; // To give CPU breathing room // Happens in update()
        boolean clearAfterPrint = false;
        boolean printStatusBar = false;
        Scanner in = new Scanner(System.in);
        String input;
        if(args.length == 0) {
            clearTerminal();
            System.out.println("Firstly some configuration:\nShould the time also be displayed in a normal format?\n[y|n]");
            while(in.hasNext()) {
                input = in.next();
                if(input.equalsIgnoreCase("y")) {
                    alsoPrintNormal = true;
                    break;
                } else if(input.equalsIgnoreCase("n")) {
                    alsoPrintNormal = false;
                    break;
                } else {
                    System.out.println("Invalid input!");
                    continue;
                }
            }
            clearTerminal();
            System.out.println("Should a status bar be displayed?\n[y|n]");
            while(in.hasNext()) {
                input = in.next();
                if(input.equalsIgnoreCase("y")) {
                    printStatusBar = true;
                    break;
                } else if(input.equalsIgnoreCase("n")) {
                    printStatusBar = false;
                    break;
                } else {
                    System.out.println("Invalid input!");
                    continue;
                }
            }
            clearTerminal();
            System.out.println("Should the terminal be cleaned after every update?\n[y|n]");
            while(in.hasNext()) {
                input = in.next();
                if(input.equalsIgnoreCase("y")) {
                    clearAfterPrint = true;
                    break;
                } else if(input.equalsIgnoreCase("n")) {
                    clearAfterPrint = false;
                    break;
                } else {
                    System.out.println("Invalid input!");
                    continue;
                }
            }
            clearTerminal();
            System.out.println("Lastly, how big should the interval be, before the program checks the time? (in Seconds)\nAnswers under 0.5 will automatically be interpeted as 0.5,\nto give the processor at least half a second of breathing room.");
            double doubleInput = 0.5;
            while(in.hasNext()) {
                try {
                    doubleInput = Double.parseDouble(in.next());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }
                if(doubleInput < 0.5) {
                    clearTerminal();
                    System.out.println("Interval is at least 0.5 seconds.");
                    break;
                } else {
                    clearTerminal();
                    System.out.println("interval is at least " + doubleInput + " seconds.");
                    minDelayInSeconds = doubleInput;
                    break;
                }
            }
            System.out.println("Let's Go!");
        } else {
            clearTerminal();
            try {
                if(args.length == 4) {
                    alsoPrintNormal = Boolean.parseBoolean(args[0]);
                    printStatusBar =  Boolean.parseBoolean(args[1]);
                    clearAfterPrint = Boolean.parseBoolean(args[2]);
                    minDelayInSeconds = Double.parseDouble(args[3]);
                } else {
                    in.close();
                    throw new IndexOutOfBoundsException();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Not 4 arguments!\nThe arguments must be given in the following form when using a preset:");
                System.out.println("\nbool<alsoPrintNormal>\n[true|false]\n\nbool<printStatusBar>\n[true|false]\n\nbool<clearAfterPrint>\n[true|false]\n\ndouble<minDelayInSeconds>\n[Floating-point number] (e.g. 2.5)");
                while(true) {
                    // Programm will end after 100 seconds. There could be better ways...
                    try {
                        Thread.sleep(100000);
                        System.exit(1);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        in.close();
        boolean autoUpdate = true;
        printDayPercentage(minDelayInSeconds, alsoPrintNormal, autoUpdate, clearAfterPrint, printStatusBar);
    }
}
