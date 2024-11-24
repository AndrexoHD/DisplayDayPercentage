package PrintTime;
import java.time.LocalTime;

public class TimePercentage {

    public static void printDayPercentage(double minIntervalInSeconds, boolean alsoPrintNormal, boolean autoUpdate, boolean clearAfterPrint, boolean printStatusBar) {
        LocalTime localTime = LocalTime.now();
        String localTimeString = localTime.toString();
        String[] hourArray = localTimeString.split("[:|.]", 3);
        hourArray[2] = String.format("%.2s", hourArray[2]);
        double hour   = Double.parseDouble(hourArray[0]);
        double minute = Double.parseDouble(hourArray[1]);
        double second = Double.parseDouble(hourArray[2]);
        if(alsoPrintNormal) printDayTime(hourArray);
        String DayPercentageString = getDayPercentage(hour, minute, second, alsoPrintNormal);
        System.out.print(DayPercentageString);
        if(printStatusBar) printStatusBar(hour);
        double intervalInMilli = minIntervalInSeconds * 1000;
        if(autoUpdate) update(DayPercentageString, intervalInMilli, alsoPrintNormal, clearAfterPrint, printStatusBar);
    }

    private static String getDayPercentage(double hour, double minute, double second, boolean alsoPrintNormal) {
        double hourPercent = 100 * hour / 24;
        double minutePercent = 100 * minute / 60 / 24;
        double secondPercent = 100 * second / 60 / 60 / 24;
        double dayPercent = (hourPercent + minutePercent + secondPercent);
        String dayPercentString = String.format("%.2f", dayPercent);
        String hourArray;
        if(alsoPrintNormal) {
            hourArray = "Der Tag ist " + dayPercentString + "% vorbei!";
        } else hourArray = "\nDer Tag ist " + dayPercentString + "% vorbei!";
        return hourArray;
    }

    private static void printDayTime(String [] hourArray) {
        for(int i = 0; i < hourArray.length; i++) {
            if(i == 0) System.out.print("\nAktuelle Uhrzeit: ");
            if(i != hourArray.length-1) {
                System.out.print(hourArray[i] + ":");
            } else {
                System.out.println(hourArray[i]);
            }
        }
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
            String newDayPercentage = getDayPercentage(hour, minute, second, alsoPrintNormal);
            if(!DayPercentageString.equals(newDayPercentage)) {
                if(clearAfterPrint) clearTerminal();
                if(alsoPrintNormal) printDayTime(hourArray);
                DayPercentageString = newDayPercentage;
                System.out.print(newDayPercentage);
                if(printStatusBar) printStatusBar(hour);
            }
        }
    }

    protected static void clearTerminal() {
        // clear Terminal. Ist kagge aber ist der einfachste weg.
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    protected static void printStatusBar(double hour) {
        StringBuilder sb = new StringBuilder();
        int widthOfPrintedText = 24; // MAX 24 !!! TODO
        double maxStatusBarLength = Math.nextDown(((int)hour)/(24/widthOfPrintedText));
        for(int i = 0; i <= maxStatusBarLength; i++) {
            sb.append("\u2588");
        }
        System.out.printf("\n[%-" + widthOfPrintedText + "s]", sb.toString());
    }
}
