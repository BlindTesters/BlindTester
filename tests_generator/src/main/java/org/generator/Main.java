package org.generator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.generator.generators.JestGenerator;

import java.io.BufferedReader;
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

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(Paths.get("traces_examples", "trace.json").toFile()));

        Trace t = gson.fromJson(reader, Trace.class);
        JestGenerator jest = new JestGenerator(t, System.lineSeparator());


        Call c1 = new Call();
        Call c2 = new Call();
        //System.out.println(c1.equals(c1));
        //System.out.println(c1.equals(c2));
        jest.makeReport();

        jest.writeTests(t.getProjectPath());
    }
}