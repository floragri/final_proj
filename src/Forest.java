import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

// manages a collection of trees within a specified forest
public class Forest implements Serializable {
    private static final long serialVersionUID = 1L; // serialization UID for compatibility

    String forestName; // name of the forest
    ArrayList<Tree> treesInForest; // collection of trees in the forest

    // constructor to initialize the forest with a given name
    public Forest(String name) {
        this.forestName = name;
        this.treesInForest = new ArrayList<>(); // ensures the list of trees is initialized
    } // end of Forest constructor

    // add a randomly generated tree to the forest
    public void addRandomTree() {
        Random randomGenerator = new Random();
        Species species = Species.values()[randomGenerator.nextInt(Species.values().length)];
        int yearPlanted = randomGenerator.nextInt(21) + 2000; // trees planted between 2000 and 2020
        double initialHeight = 10 + randomGenerator.nextDouble() * 10; // height between 10 and 20 feet
        double growthRate = 0.10 + randomGenerator.nextDouble() * 0.10; // growth rates between 10% and 20%
        treesInForest.add(new Tree(species, yearPlanted, initialHeight, growthRate));
    } // end of addRandomTree method

    // remove a tree by its index
    public void cutDownTree(int treeIndex) {
        if (treeIndex >= 0 && treeIndex < treesInForest.size()) {
            treesInForest.remove(treeIndex);
        } else {
            System.out.println("tree number " + treeIndex + " does not exist.");
        }
    } // end of cutDownTree method

    // simulate the growth of each tree in the forest for one year
    public void grow() {
        for (Tree tree : treesInForest) {
            tree.grow(); // use the grow method in the Tree class
        }
    } // end of grow method

    // reap trees that exceed a specified height and replace them with new trees
    public void reapTrees(double minimumHeight) {
        Random randomGenerator = new Random();
        for (int index = 0; index < treesInForest.size(); index++) {
            Tree tree = treesInForest.get(index);
            if (tree.height > minimumHeight) {
                System.out.println("Reaping the tall tree: " + tree);
                treesInForest.remove(index); // Remove the tall tree

                // Add a new random tree at the same index
                Species species = Species.values()[randomGenerator.nextInt(Species.values().length)];
                int yearPlanted = randomGenerator.nextInt(21) + 2000;
                double initialHeight = 10 + randomGenerator.nextDouble() * 10;
                double growthRate = 0.10 + randomGenerator.nextDouble() * 0.10;
                Tree newTree = new Tree(species, yearPlanted, initialHeight, growthRate);
                treesInForest.add(index, newTree); // Add new tree at the same position

                System.out.println("Replacing with new tree: " + newTree);
                index--; // decrement index to account for shift after removal
            }
        }
    }

    // print details of all trees in the forest
    public void print() {
        System.out.println("Forest name: " + forestName);
        double totalHeight = 0;
        for (int index = 0; index < treesInForest.size(); index++) {
            Tree tree = treesInForest.get(index);
            System.out.printf("     %d %s   %d   %.2f'  %.1f%%\n", index, tree.species, tree.yearPlanted, tree.height, tree.growthRate * 100);
            totalHeight += tree.height;
        }
        double averageHeight = (treesInForest.size() > 0) ? totalHeight / treesInForest.size() : 0;
        System.out.printf("There are %d trees, with an average height of %.2f\n", treesInForest.size(), averageHeight);
    } // end of print method
} // end of Forest class
