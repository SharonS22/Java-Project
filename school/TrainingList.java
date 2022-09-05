package school;

/**
 * Class representing the list of trainings. Allows to filter a training based on a qualification.
 *
 * @author Sharon Shaji
 */

import java.util.ArrayList;
import java.util.List;

public class TrainingList {
    private List<Training> trainingList;
    private static final TrainingList instance = new TrainingList();

    /**
     * Create an arrayList with all the available trainings
     */
    private TrainingList() {
        trainingList = new ArrayList<>();
    }

    public static TrainingList instance() {
        return instance; // Ensure there is only a single class storing trainings with singleton
    }

    public void print() {
        for (Training training : trainingList) {
            System.out.println(training.getName() + " giving a " + training.getQualificationGained() + " qualification.");
        }
    }

    /**
     * Add a training to the list
     */
    public void add(Training training) {
        trainingList.add(training);
    }

    /**
     * Filter by qualification
     */
    public Training filter(String qualificationName) throws Exception {
        for (Training training : trainingList) {
            if (training.getName().contains(qualificationName)) {
                return training;
            }
        }
        throw new Exception("No such training.");
    }

    public List<Training> getTrainingList() {
        return trainingList;
    }
}
