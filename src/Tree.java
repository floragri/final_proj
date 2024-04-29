import java.io.Serializable;

// represents a tree with species, planting year, height, and growth rate
public class Tree implements Serializable {
    private static final long serialVersionUID = 1L; // ensures serialization compatibility

    Species species;
    int yearPlanted;
    double height;
    double growthRate;

    // initialize tree properties
    public Tree(Species species, int yearPlanted, double height, double growthRate) {
        this.species = species;
        this.yearPlanted = yearPlanted;
        this.height = height;
        this.growthRate = growthRate;
    }

    // simulate growth based on the growth rate
    public void grow() {
        height += height * growthRate;
    }

    // return formatted tree details
    public String toString() {
        return String.format("%s %d %.2f' %.1f%%", species, yearPlanted, height, growthRate * 100);
    }
} // end of Tree class
