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
        System.out.println("java -jar testbuilder.jar ACTION [OPTIONS]");
        System.out.println("--");
        System.out.println("-> java -jar testbuilder.jar generate [GENERATOR] [TEST_TYPE] [TRACE_PATH]");
        System.out.println("-> java -jar testbuilder.jar analyse [TRACE_PATH]");
        System.out.println("--");
        System.out.println("Available generators     :  jest");
        System.out.println("Available types of tests : all, distinct, minimal, kmeans");
    }

    /**
     * Main function that read a json file and give it to the chosen generator
     *
     * @param args is the array containing args from the terminal
     */
    public static void main(String[] args) {
        // check that user entered a file path
        if (args.length == 0) {
            System.out.println("Please choose one action");
            help();
            System.exit(0);
        }

        try {
            // get chosen action
            String action = args[0].toLowerCase();

            // get trace from json file and create a test generator
            Gson gson = new Gson();
            JsonReader reader;

            Trace t;
            String tracePath;

            switch(action) {
                case "analyse":
                    tracePath = args[1];
                    // get json trace file path
                    reader = new JsonReader(new FileReader(tracePath));
                    t = gson.fromJson(reader, Trace.class);
                    t.setTracePath(tracePath);

                    t.makeReport();
                    break;
                case "generate":
                    if (args.length != 4) {
                        System.out.println("Please choose one action");
                        help();
                        System.exit(0);
                    }

                    // get chosen generator
                    String generator = args[1].toLowerCase();

                    // get the type of heuristic the program will use to create tests
                    String testType = args[2];

                    // get json trace file path
                    tracePath = args[3];
                    reader = new JsonReader(new FileReader(tracePath));
                    t = gson.fromJson(reader, Trace.class);
                    t.setTracePath(tracePath);

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
                    break;
                default:
                    System.out.println("Action not found" + System.lineSeparator());
                    help();
                    System.exit(0);

            }
        } catch (Exception e) {
            System.out.println("A problem occurred during testing : " + e);
            e.printStackTrace();
        }
    }
}