package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.blindtester.generator.js.JSUtil;
import org.javatuples.Pair;

import java.util.*;

import javax.xml.crypto.Data;

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
    public List<Call> computeKMeans(String tracePath) {
        // Retrieve all calls.
        List<Call> allCalls = getDistinctCalls().getValue1();
        List<Object> kCalls = new ArrayList<>();
        for (Call c : allCalls) {
            KCall kCall = new KCall(c);
            kCalls.add(kCall.getValues());
        }
        List<Call> calls = new ArrayList<>();

        // 1. ignore header
        // 2. take into account input and output
        // 2.1 autodetect type of input and output

        // 3. compute kmeans

        // Create a SparkSession.
        SparkSession spark = SparkSession
                .builder()
                .appName("Blindtester-KMeans")
                .config("spark.master", "local")
                .config("spark.driver.bindAddress", "127.0.0.1")
                .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .config("spark.kryo.registrationRequired", "false")
                .getOrCreate();
        
        List<Row> ls = new ArrayList<Row>();
        Row row = RowFactory.create(kCalls.toArray());
        ls.add(row);

        List<DataType> datatype = new ArrayList<DataType>();
        datatype.add(DataTypes.createArrayType(
            DataTypes.StringType
        ));
        // datatype.add(DataTypes.StringType);
        // datatype.add(DataTypes.StringType);
        // datatype.add(DataTypes.DoubleType);
        // datatype.add(DataTypes.DoubleType);
        // datatype.add(DataTypes.StringType);
        // datatype.add(DataTypes.createMapType(DataTypes.StringType, DataTypes.StringType));
        // datatype.add(DataTypes.createMapType(DataTypes.StringType, DataTypes.createArrayType(DataTypes.DoubleType)));

        List<String> header = new ArrayList<String>();
        header.add("argument1");
        // header.add("argument2");
        // header.add("argument3");
        // header.add("argument4");
        // header.add("argument5");
        // header.add("outputtype");
        // header.add("outputvalue");

        StructField structField1 = new StructField(header.get(0), datatype.get(0), true, org.apache.spark.sql.types.Metadata.empty());
        // StructField structField2 = new StructField(header.get(1), datatype.get(1), true, org.apache.spark.sql.types.Metadata.empty());
        // StructField structField3 = new StructField(header.get(2), datatype.get(2), true, org.apache.spark.sql.types.Metadata.empty());
        // StructField structField4 = new StructField(header.get(3), datatype.get(3), true, org.apache.spark.sql.types.Metadata.empty());
        // StructField structField5 = new StructField(header.get(4), datatype.get(4), true, org.apache.spark.sql.types.Metadata.empty());
        List<StructField> structFieldsList = new ArrayList<>();
        structFieldsList.add(structField1);
        // structFieldsList.add(structField2);
        // structFieldsList.add(structField3);
        // structFieldsList.add(structField4);
        // structFieldsList.add(structField5);

        StructType schema = new StructType(structFieldsList.toArray(new StructField[0]));

        Dataset<Row> dataset = spark.createDataFrame(ls, schema);

        dataset.show();
        dataset.printSchema();

        // 

        // Dataset<Call> callDataset = spark.createDataset(allCalls, Encoders.bean(Call.class));
        Dataset<Row> datasetRows = spark.createDataFrame(kCalls, KCall.class);
        // Encoder<Call> callEncoder = Encoders.bean(Call.class);
        // Dataset<Call> callDataset = spark.createDataset(allCalls, callEncoder);
        // Dataset<Row> dataset = spark.read().format("libsvm").load("data/mllib/sample_kmeans_data.txt");

        KMeans kmeans = new KMeans().setK(2).setSeed(1L);
        KMeansModel model = kmeans.fit(dataset);

        // Make predictions
        Dataset<Row> predictions = model.transform(dataset);

        // Evaluate clustering by computing Silhouette score
        ClusteringEvaluator evaluator = new ClusteringEvaluator();

        double silhouette = evaluator.evaluate(predictions);
        System.out.println("Silhouette with squared euclidean distance = " + silhouette);

        // Shows the result.
        Vector[] centers = model.clusterCenters();
        System.out.println("Cluster Centers: ");
        for (Vector center: centers) {
            System.out.println(center);
        }
        spark.stop();

        calls.add(allCalls.getFirst());

        return calls;
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
