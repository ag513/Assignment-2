package stock;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Scanner;

public class ReadingTest {
    private static Scanner input;
	private static Scanner infile;

	public static void main(String[] args) throws IOException, ParseException {
        // Import file to java
        File file = new File("table.csv");

        infile = new Scanner(file);

        // Skip the first line in table.csv
        infile.nextLine();

        // Define format of date
        SimpleDateFormat hammerFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat slashFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Name the variables user enters
       
        // Create ArrayList for each column of data
        ArrayList < String > date = new ArrayList < String > ();
        ArrayList < Double > open = new ArrayList < Double > ();
        ArrayList < Double > high = new ArrayList < Double > ();
        ArrayList < Double > low = new ArrayList < Double > ();
        ArrayList < Double > close = new ArrayList < Double > ();

        while (infile.hasNext()) {
            
            String[] data = infile.nextLine().split(",");
            // Organize each column of data to one index of data array
            date.add(data[0]);
            open.add(Double.parseDouble(data[1]));
            high.add(Double.parseDouble(data[2]));
            low.add(Double.parseDouble(data[3]));
            close.add(Double.parseDouble(data[4]));
        }
        // Show options and ask user to choose
        System.out.println("1. Inverted Hammer");
        System.out.println("2. Three white soldiers");
        System.out.println("3. Evening star");

        input = new Scanner(System.in);
        int choice = input.nextInt();

        if (choice == 1)
            for (int i = 0; i < date.size(); i++)
                if (close.get(i) > open.get(i) && close.get(i) > ((high.get(i)) + (low.get(i))) / 2 && ((close.get(i) - low.get(i)) / 2 > (high.get(i) - close.get(i))))

                    System.out.println("Pattern found: " + hammerFormat.format(slashFormat.parse(date.get(i))));
                    System.out.println("pattern:1");
                   System.out.println("pattern found: Start date");
                    System.out.println("pattern found: end date");
                    
    }
}