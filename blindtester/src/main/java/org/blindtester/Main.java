package org.blindtester;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.blindtester.generator.js.JSUtil;
import org.blindtester.generator.js.JestGenerator;
import org.blindtester.generator.Trace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;


public class Main {
    private static String ReadJSONFile(String filepath) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            sb.append(line);
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
                sb.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void help(){
        System.out.println("java -jar testbuilder.jar GENERATOR trace.json");
    }

    public static void main(String[] args) throws IOException {
        // check that user entered a file path

        JSUtil.equalType(1, true);

        if(args.length != 2){
            System.out.println("Please choose one generator and one json trace file to analyze");
            help();
            System.exit(0);
        }

        try{
            // get chosen generator
            String generator = args[0].toLowerCase();

            // get trace.json path
            String tracePath = args[1];

            switch(generator) {
                case "jest":
                    // get trace from json file and create a test generator
                    Gson gson = new Gson();
                    JsonReader reader = new JsonReader(new FileReader(tracePath));
                    Trace t = gson.fromJson(reader, Trace.class);
                    JestGenerator jest = new JestGenerator(t, System.lineSeparator());

                    // write report about the calls
                    jest.makeReport();

                    // write test to path
                    jest.writeTests(t.getProjectPath());
                    break;
                default:
                    System.out.println("Generator not found");
                    break;
            }
        }
        catch (Exception e){
            System.out.println("A problem occurred during testing : " + e);
        }
    }
}