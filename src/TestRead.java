import java.io.*;
import java.util.Scanner;

public class TestRead {
    public static void main(String[] args) {
        File testFile = new File("Acadian.csv");
        System.out.println("Checking file at: " + testFile.getAbsolutePath());
        if (testFile.exists()) {
            System.out.println("File exists");
            try {
                Scanner scanner = new Scanner(testFile);
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Failed to open file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
}
