package org.generator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.generator.generators.JestGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class Main {
    private static String ReadJSONFile(String filepath){
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
        JsonReader reader = new JsonReader(new FileReader("trace.json"));
        Trace t = gson.fromJson(reader, Trace.class);
        JestGenerator jest = new JestGenerator(t, System.lineSeparator());

        jest.writeTests();
    }
}