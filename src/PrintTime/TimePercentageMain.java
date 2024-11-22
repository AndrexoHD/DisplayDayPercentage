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
            System.out.println("Erstmal ein paar Konfigurationen:\nSoll die Zeit auch im normalen Format ausgegeben werden?\n[y|n]");
            while(in.hasNext()) {
                input = in.next();
                if(input.equals("y") || input.equals("Y")) {
                    alsoPrintNormal = true;
                    break;
                } else if(input.equals("n") || input.equals("N")) {
                    alsoPrintNormal = false;
                    break;
                } else {
                    System.out.println("Ungültige Eingabe!");
                    continue;
                }
            }
            clearTerminal();
            System.out.println("Soll eine Statusleiste Ausgegeben werden?[y|n]");
            while(in.hasNext()) {
                input = in.next();
                if(input.equals("y") || input.equals("Y")) {
                    printStatusBar = true;
                    break;
                } else if(input.equals("n") || input.equals("N")) {
                    printStatusBar = false;
                    break;
                } else {
                    System.out.println("Ungültige Eingabe!");
                    continue;
                }
            }
            clearTerminal();
            System.out.println("Soll das Terminal nach jeder Aktualisierung sauber gemacht werden?\n[y|n]");
            while(in.hasNext()) {
                input = in.next();
                if(input.equals("y") || input.equals("Y")) {
                    clearAfterPrint = true;
                    break;
                } else if(input.equals("n") || input.equals("N")) {
                    clearAfterPrint = false;
                    break;
                } else {
                    System.out.println("Ungültige Eingabe!");
                    continue;
                }
            }
            clearTerminal();
            System.out.println("Als letztes, wie groß soll das Intervall mindestens sein, bevor das Programm den Fortschritt prüft? (in Sekunden)\nAntworten unter 0.5 werden automatisch als 0.5 interpretiert,\num dem Prozessor mindestens eine halbe Sekunde Zeit zu geben.");
            double doubleInput = 0.5;
            while(in.hasNext()) {
                try {
                    doubleInput = Double.parseDouble(in.next());
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe!");
                    continue;
                }
                if(doubleInput < 0.5) {
                    clearTerminal();
                    System.out.println("Intervall ist mindestens 0.5 Sekunden.");
                    break;
                } else {
                    clearTerminal();
                    System.out.println("Intervall ist mindestens " + doubleInput + " Sekunden.");
                    minDelayInSeconds = doubleInput;
                    break;
                }
            }
            System.out.println("Abfahrt!");
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
                System.out.println("Nicht 4 Argumente!\nBei einem Preset müssen die Argumente in dieser Form übergeben werden:");
                System.out.println("\nbool<alsoPrintNormal>\n[true|false]\n\nbool<printStatusBar>\n[true|false]\n\nbool<clearAfterPrint>\n[true|false]\n\ndouble<minDelayInSeconds>\n[Gleitkommazahl]");
                while(true) {
                    // Programm wird nach 100 Sekunden beendet. Gibt bessere implementierungen...
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
