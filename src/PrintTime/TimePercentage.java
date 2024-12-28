package PrintTime;
import java.time.LocalTime;

public class TimePercentage {

    public static void printDayPercentage(double minIntervalInSeconds, boolean alsoPrintNormal, boolean clearAfterPrint, boolean printStatusBar) {
        LocalTime localTime = LocalTime.now();
        String localTimeString = localTime.toString();
        String[] hourArray = localTimeString.split("[:|.]", 3);
        hourArray[2] = String.format("%.2s", hourArray[2]);
        double hour   = Double.parseDouble(hourArray[0]);
        double minute = Double.parseDouble(hourArray[1]);
        double second = Double.parseDouble(hourArray[2]);
        if(alsoPrintNormal) printDayTime(hourArray);
        String DayPercentageString = getDayPercentageText(hour, minute, second, alsoPrintNormal);
        System.out.print(DayPercentageString);
        if(printStatusBar) printStatusBar(hour, minute, hour);
        double intervalInMilli = minIntervalInSeconds * 1000;
        update(DayPercentageString, intervalInMilli, alsoPrintNormal, clearAfterPrint, printStatusBar);
    }

    private static String getDayPercentageText(double hour, double minute, double second, boolean alsoPrintNormal) {
        String dayPercentString = String.format("%.2f", getDayPercentage(hour, minute, second));
        String hourArray;
        if(alsoPrintNormal) {
            hourArray = "The day is " + dayPercentString + "% over!";
        } else hourArray = "\nThe day is " + dayPercentString + "% over!";
        return hourArray;
    }

    public static double getDayPercentage(double hour, double minute, double second) {
        double hourPercent = 100 * hour / 24;
        double minutePercent = 100 * minute / 60 / 24;
        double secondPercent = 100 * second / 60 / 60 / 24;
        double dayPercent = (hourPercent + minutePercent + secondPercent);
        return dayPercent;
    }

    private static void printDayTime(String [] hourArray) {
        System.out.println("\nCurrent Time: " + hourArray[0] + ":" + hourArray[1] + ":" + hourArray[2] + "!");
    }

    private static void update(String DayPercentageString, double intervalInMilli, boolean alsoPrintNormal, boolean clearAfterPrint, boolean printStatusBar) {
        while(true) {
            if(intervalInMilli < 500) {
                intervalInMilli = 500;
            }
            try {
                Thread.sleep((int) intervalInMilli);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalTime localTime = LocalTime.now();
            String localTimeString = localTime.toString();
            String[] hourArray = localTimeString.split("[:|.]", 3);
            hourArray[2] = String.format("%.2s", hourArray[2]);
            double hour   = Double.parseDouble(hourArray[0]);
            double minute = Double.parseDouble(hourArray[1]);
            double second = Double.parseDouble(hourArray[2]);
            String newDayPercentage = getDayPercentageText(hour, minute, second, alsoPrintNormal);
            if(!DayPercentageString.equals(newDayPercentage)) {
                if(clearAfterPrint) clearTerminal();
                if(alsoPrintNormal) printDayTime(hourArray);
                DayPercentageString = newDayPercentage;
                System.out.print(newDayPercentage);
                if(printStatusBar) printStatusBar(hour, minute, second);
            }
        }
    }

    protected static void clearTerminal() {
        // clear Terminal. Ist kagge aber ist der einfachste weg.
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    protected static void printStatusBar(double hour, double minute, double second) {
        StringBuilder sb = new StringBuilder();
        double widthOfPrintedText = 21; // -2 because [ ]
        double blockAmount = widthOfPrintedText * getDayPercentage(hour, minute, second) / 100;
        for(int i = 0; i < (int) blockAmount; i++) {
            sb.append("\u2588");
        }
        System.out.printf("\n[%-" + (int) widthOfPrintedText + "s]", sb.toString());
    }
}
