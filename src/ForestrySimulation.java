import java.io.*;
import java.util.Scanner;

// main class to run forestry simulation
public class ForestrySimulation implements Serializable {
    private static Scanner scanner = new Scanner(System.in);
    private static Forest currentForest;
    private static String[] forestNames;
    private static int currentForestIndex = 0;

    // main method to run the simulation
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("no forest names provided!");
            return; // exit if no arguments are provided
        }

        forestNames = args;
        System.out.println("welcome to the Forestry Simulation");
        System.out.println("----------------------------------");

        loadForest(forestNames[currentForestIndex]);

        String command = "";
        do {
            System.out.print("(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
            command = scanner.nextLine();
            handleCommand(command);
        } while (!command.equalsIgnoreCase("X"));

        scanner.close();
    } // end of main method

    // loads the forest data from serialized file
    private static boolean loadForest(String forestName) {
        File File = new File(forestName + ".csv");
        if (File.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(File);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                currentForest = (Forest) objectInputStream.readObject();
                System.out.println("Initializing from " + forestName);
                return true;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading forest data: " + e.getMessage());
                return false;
            }
        } else {
            return initializeFromCSV(forestName);
        }
    }

    // initialize forest from file
    private static boolean initializeFromCSV(String forestName) {
        InputStream inputStream = ForestrySimulation.class.getClassLoader().getResourceAsStream(forestName + ".csv");
        if (inputStream == null) {
            System.out.println("Error: file not found.");
            return false;
        }
        try (Scanner fileScanner = new Scanner(inputStream)) {
            currentForest = new Forest(forestName);
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                try {
                    Species species2 = Species.valueOf(data[0].trim());
                    int yearPlanted = Integer.parseInt(data[1].trim());
                    double height = Double.parseDouble(data[2].trim());
                    double growthRate = Double.parseDouble(data[3].trim());
                    currentForest.treesInForest.add(new Tree(species2, yearPlanted, height, growthRate / 100.0));
                } catch (IllegalArgumentException e) {
                    System.out.println("Error with data format: " + e.getMessage());
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error processing file: " + e.getMessage());
            return false;
        }
    }//end of class


    // handles user commands
    private static void handleCommand(String command) {
        switch (command.toUpperCase()) {
            case "P":
                currentForest.print();
                break;
            case "A":
                currentForest.addRandomTree();
                System.out.println("tree added.");
                break;
            case "C":
                System.out.print("tree number to cut down: ");
                try {
                    int index = Integer.parseInt(scanner.nextLine());
                    currentForest.cutDownTree(index);
                    System.out.println("tree cut down.");
                } catch (NumberFormatException e) {
                    System.out.println("that is not a valid number.");
                }
                break;
            case "G":
                currentForest.grow();
                System.out.println("all trees have grown.");
                break;
            case "R":
                System.out.print("height to reap from: ");
                try {
                    double height = Double.parseDouble(scanner.nextLine());
                    currentForest.reapTrees(height);
                    System.out.println("trees reaped.");
                } catch (NumberFormatException e) {
                    System.out.println("that is not a valid height.");
                }
                break;
            case "S":
                saveForest();
                break;
            case "L":
                System.out.print("enter forest name to load: ");
                String name = scanner.nextLine();
                loadForest(name);
                break;
            case "N":
                moveToNextForest();
                break;
            case "X":
                System.out.println("exiting the Forestry Simulation");
                break;
            default:
                System.out.println("invalid menu option, try again");
                break;
        }
    } // end of handleCommand method

    // saves the current forest state to a serialized file
    private static void saveForest() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(currentForest.forestName + ".db");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(currentForest);
            System.out.println("forest saved successfully.");
        } catch (IOException e) {
            System.out.println("error saving the forest: " + e.getMessage());
        }
    } // end of saveForest method

// moves to the next forest in the list, or wraps around
    private static void moveToNextForest() {
        if (forestNames.length > 1) {
            currentForestIndex = (currentForestIndex + 1) % forestNames.length;
            boolean loaded = loadForest(forestNames[currentForestIndex]);
            if (loaded) {
                System.out.println("moved to next forest: " + forestNames[currentForestIndex]);
            } else {
                System.out.println("failed to load forest: " + forestNames[currentForestIndex]);
            }
        }
    }

}
