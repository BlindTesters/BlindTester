package org.blindtester;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.blindtester.generator.Trace;
import org.blindtester.generator.js.JestGenerator;

import java.io.FileReader;

/**
 * Main class
 */
public class Main {
    /**
     * Show help in the terminal
     */
    public static void help() {
        System.out.println("java -jar testbuilder.jar GENERATOR TEST_TYPE trace.json");
        System.out.println("Generators     :  jest");
        System.out.println("Types of tests : all, distinct, minimal, knn");
    }

    /**
     * Main function that read a json file and give it to the chosen generator
     *
     * @param args is the array containing args from the terminal
     */
    public static void main(String[] args) {
        // check that user entered a file path
        if (args.length != 3) {
            System.out.println("Please choose one generator and one json trace file to analyze");
            help();
            System.exit(0);
        }

        try {
            // get chosen generator
            String generator = args[0].toLowerCase();

            // get trace.json path
            String testType = args[1];

            // get trace.json path
            String tracePath = args[2];

            // get trace from json file and create a test generator
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(tracePath));
            Trace t = gson.fromJson(reader, Trace.class);

            switch (generator) {
                case "jest":
                    JestGenerator jest = new JestGenerator(t, System.lineSeparator());
                    // write test to path
                    jest.writeTests(t.getProjectPath(), testType);
                    break;
                default:
                    System.out.println("Generator not found");
                    break;
            }
        } catch (Exception e) {
            System.out.println("A problem occurred during testing : " + e);
            e.printStackTrace();
        }
    }
}