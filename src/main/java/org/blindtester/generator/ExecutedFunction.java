package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.sql.*;
import org.blindtester.generator.js.JSUtil;
import org.javatuples.Pair;

import java.util.*;

public class ExecutedFunction {
    /**
     * The name of the function
     */
    @SerializedName("name")
    private String Name;

    /**
     * List of calls of the function in the trace
     */
    @SerializedName("calls")
    private List<Call> Calls;

    /**
     * Get the function name
     *
     * @return the function name
     */
    public String getName() {
        return Name;
    }

    /**
     * Get the list of calls
     *
     * @return the list of calls
     */
    public List<Call> getCalls() {
        return Calls;
    }

    /**
     * Get all distinct call from a function
     *
     * @return the list of distinct calls
     */
    public Pair<Boolean, List<Call>> getDistinctCalls() {
        List<Call> distinctCalls = new ArrayList<>();
        boolean sideEffect = false;

        for (Call c1 : getCalls()) {
            boolean found = false;
            for (Call c2 : distinctCalls) {
                if (c1.equals(c2)) {
                    found = true;
                    break;
                } else {
                    // check if inputs are equals but not the output to detect side effects
                    if (c1.getInputs().equals(c2.getInputs()) && !c1.getOutput().equals(c2.getOutput())) {
                        sideEffect = true;
                    }
                }
            }
            if (!found) {
                distinctCalls.add(c1);
            }
        }

        return new Pair<>(sideEffect, distinctCalls);
    }

    /**
     * Compute the minimal set of functions based on the types of inputs and output and number of arguments
     *
     * @return the distinct list of function calls
     * @throws Exception if there is a side effect detected in the function
     */
    public List<Call> computeMinimumSetOfCalls() throws Exception {
        // get all distinct calls
        Pair<Boolean, List<Call>> resultDistinct = getDistinctCalls();
        List<Call> distinctCalls = resultDistinct.getValue1();
        Boolean sideEffect = resultDistinct.getValue0();

        // new list that will contain the minimum set of calls
        List<Call> minimumSetCalls = new ArrayList<>();

        // cannot compute when we detected side effects
        if (sideEffect) {
            throw new Exception("Side effect detected => Cannot compute minimum set of calls");
        }

        // Compute and store the number of arguments for each call
        Map<Integer, List<Call>> mapFuncArgs = new HashMap<>();

        for (Call c1 : distinctCalls) {
            int countArgs = c1.getInputs().size();

            if (!mapFuncArgs.containsKey(countArgs)) {
                mapFuncArgs.put(countArgs, new ArrayList<>());
            }

            mapFuncArgs.get(countArgs).add(c1); // put last detected call for now
        }

        // type of inputs and output
        // for each number of args we check that if they have the same type
        // keep cleaned list
        mapFuncArgs.replaceAll((k, v) -> keepDifferentInputsOutput(v));

        // keep cleaned list
        for (var entry : mapFuncArgs.entrySet()) {
            List<Call> calls = entry.getValue();
            minimumSetCalls.addAll(calls);
        }

        return minimumSetCalls;
    }

    /**
     * Compute KMeans
     *
     * @return the list of k calls, one per computed cluster.
     */
    public List<Call> computeKMeans() {
        // Retrieve all calls.
        List<Call> allCalls = getDistinctCalls().getValue1();

        // Create a SparkSession.
        SparkSession spark = SparkSession
                .builder()
                .appName("Blindtester-KMeans")
                .config("spark.master", "local")
                .config("spark.driver.bindAddress", "127.0.0.1")
                .getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        SQLContext sqlContext = new org.apache.spark.sql.SQLContext(jsc);

        Dataset<Row> df = spark.read().csv("data.csv").withColumnRenamed("_c5", "features");
        df.show();

        KMeans kmeans = new KMeans().setK(2).setSeed(1L);

        KMeansModel clusters = kmeans.fit(df);

        spark.stop();

        return null;
    }

    /**
     * Check if all call have different output and output
     *
     * @param calls the list of calls to compare
     * @return a list of call containing different inputs and output
     */
    public List<Call> keepDifferentInputsOutput(List<Call> calls) {
        List<Call> differentCalls = new ArrayList<>();

        for (Call c1 : calls) {
            boolean same = true;

            // check inputs
            for (Call c2 : differentCalls) {
                if (!JSUtil.compareInputsTypes(c1.getInputs(), c2.getInputs())
                    || !JSUtil.equalType(c1.getOutput(), c2.getOutput())) {
                    same = false;
                    break;
                }
            }

            // check if the call is different or if it is the first to add
            if (!same || differentCalls.isEmpty()) {
                differentCalls.add(c1);
            }
        }

        return differentCalls;
    }

    /**
     * Override for the toString function to write the executed function
     *
     * @return the string representation of an executed function
     */
    public List<String> getSignature() {
        Set<String> signatures = new HashSet<>();

        for (Call c : getCalls()) {
            StringBuilder sbCalls = new StringBuilder();
            sbCalls.append(this.getName());
            sbCalls.append("(");

            List<Object> inputs = c.getInputs();
            for (int i=0;i<inputs.size();i++) {
                sbCalls.append(JSUtil.getType(inputs.get(i)));
                if(i < inputs.size()-1) {
                    sbCalls.append(", ");
                }
            }
            sbCalls.append(")");

            sbCalls.append(" : ");

            sbCalls.append(JSUtil.getType(c.getOutput()));

            signatures.add(sbCalls.toString());
        }

        return new ArrayList<>(signatures);
    }

    /**
     * Override for the toString function to write the executed function
     *
     * @return the string representation of an executed function
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        sb.append(getName()).append(sep);

        for (Call c : getCalls()) {
            sb.append(String.format("  %s%s", c.toString(), sep));
        }

        return sb.toString();
    }
}
