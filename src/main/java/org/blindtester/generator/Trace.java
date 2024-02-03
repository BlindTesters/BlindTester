package org.blindtester.generator;

import com.google.gson.annotations.SerializedName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.javatuples.Pair;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.nio.file.Paths;

/**
 * A trace that contain the execution
 */
public class Trace {
    /**
     * The name of the project
     */
    @SerializedName("project_name")
    private String Name;

    /**
     * The path of the project
     */
    @SerializedName("project_path")
    private String Path;

    /**
     * The main file of the project
     */
    @SerializedName("main_file")
    private String MainFile;

    /**
     * The caller of the function
     */
    @SerializedName("caller")
    private String Caller;

    /**
     * The list of requirements
     */
    @SerializedName("requires")
    private List<Require> Requires;

    /**
     * The list of executed functions in the trace
     */
    @SerializedName("executed_functions")
    private List<ExecutedFunction> ExecutedFunctions;

    private String TracePath;

    /**
     * Get the project name
     *
     * @return the project name
     */
    public String getProjectName() {
        return Name;
    }

    /**
     * Get the project path
     *
     * @return the project path
     */
    public String getProjectPath() {
        return Path;
    }

    /**
     * Get the main file
     *
     * @return the main file
     */
    public String getMainFile() {
        return MainFile;
    }

    /**
     * Get the requirements list
     *
     * @return the requirements list
     */
    public List<Require> getRequires() {
        return Requires;
    }

    /**
     * Get the list of executed functions
     *
     * @return the list of executed functions
     */
    public List<ExecutedFunction> getExecutedFunctions() {
        return ExecutedFunctions;
    }

    /**
     * Override of the toString method to print a Trace
     *
     * @return the string representation of a trace
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        sb.append(String.format("Project    : %s" + sep, getProjectName()));
        sb.append(String.format("Path       : %s" + sep, getProjectPath()));
        sb.append(String.format("Main       : %s" + sep, getMainFile()));
        sb.append("Requires   : " + sep);

        for (Require r : getRequires()) {
            sb.append(String.format("* %s", r.toString()));
        }

        sb.append(sep + "Executed   : " + sep);
        for (ExecutedFunction ef : getExecutedFunctions()) {
            sb.append(String.format("* %s", ef.toString()));
        }

        return sb.toString();
    }

    /**
     *
     * @param tracePath
     */
    public void setTracePath(String tracePath) {
        this.TracePath = tracePath;
    }

    public String getTracePath() {
        return TracePath;
    }

    /**
     * Create a report in console about the number of calls in the trace
     */
    public void makeReport() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String strDate = formatter.format(now);

        PDDocument document = new PDDocument();
        try {
            // TITLE
            PDPage pageTitle = new PDPage();
            document.addPage(pageTitle);
            PDPageContentStream contentStream = new PDPageContentStream(document, pageTitle);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            contentStream.setFont( PDType1Font.TIMES_BOLD, 44 );
            contentStream.setLeading(14.5f);

            // Content
            contentStream.showText("Blindtester - Trace report");

            PDImageXObject pdImage = PDImageXObject.createFromFile("docs/images/glasses.png", document);
            contentStream.endText();

            contentStream.drawImage(pdImage, 10, 5);

            contentStream.close();

            // Project
            PDPage pageProject = new PDPage();
            contentStream = new PDPageContentStream(document, pageProject);
            document.addPage(pageProject);

            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            contentStream.setLeading(17f);

            // PROJECT
            contentStream.setFont( PDType1Font.TIMES_BOLD, 24 );
            contentStream.showText("Project : " + this.getProjectName());
            contentStream.newLine();

            contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
            contentStream.showText(now.toString());
            contentStream.newLine();
            contentStream.newLine();

            // LIBRARIES
            contentStream.setFont( PDType1Font.TIMES_BOLD, 16 );
            contentStream.showText("Libraries import");
            contentStream.newLine();
            contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );

            contentStream.showText(this.getRequires().size()+ " import(s) : ");
            contentStream.newLine();

            for(var r : this.getRequires()) {
                contentStream.showText(" - " + r.toString());
                contentStream.newLine();
            }

            // FUNCTION
            contentStream.setFont( PDType1Font.TIMES_BOLD, 16 );
            contentStream.newLine();
            contentStream.showText("Functions detected");
            contentStream.newLine();

            for (ExecutedFunction f : this.getExecutedFunctions()) {
                contentStream.setFont( PDType1Font.TIMES_BOLD, 14 );
                contentStream.showText(f.getName());
                contentStream.newLine();

                contentStream.setFont( PDType1Font.TIMES_BOLD, 12 );
                contentStream.showText("   Calls ");
                contentStream.newLine();

                contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
                // get distinct calls
                Pair<Boolean, List<Call>> resultDistinct = f.getDistinctCalls();
                List<Call> distinctCalls = resultDistinct.getValue1();
                List<Call> clusteredCalls = f.computeKMeans(this.getTracePath());
                Boolean sideEffect = resultDistinct.getValue0();

                contentStream.showText("   - " + f.getCalls().size() + " total call(s)");
                contentStream.newLine();
                contentStream.showText("   - " + distinctCalls.size() + " distinct call(s)");
                contentStream.newLine();

                if (sideEffect) {
                    contentStream.setNonStrokingColor(Color.RED);
                    contentStream.showText("   - Side effects detected for this function !");
                    contentStream.newLine();
                    contentStream.setNonStrokingColor(Color.BLACK);
                }

                contentStream.setFont( PDType1Font.TIMES_BOLD, 12 );
                contentStream.showText("   Signatures ");
                contentStream.newLine();
                contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );

                for (String sig : f.getSignature()){
                    contentStream.showText("   - " + sig);
                    contentStream.newLine();
                }

                contentStream.setFont( PDType1Font.TIMES_BOLD, 12 );
                contentStream.showText("   Clustering");
                contentStream.newLine();
                contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
                contentStream.showText("   - Detected clusters: " + clusteredCalls.size());
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            String reportFilename = Paths.get(getProjectPath(), "blindtester-report-" + strDate + ".pdf").toString();
            document.save(reportFilename);
            System.out.println("Report generated : " + reportFilename);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
