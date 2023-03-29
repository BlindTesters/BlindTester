package org.generator.generators;


import org.generator.Trace;

public abstract class Generator {
        private Trace trace;
        private String lineSeparator;

        public Generator (Trace trace, String lineSeparator){
                this.trace = trace;
                this.lineSeparator = lineSeparator;
        }

        abstract String createTests();

        abstract void writeTests();

        public String getLineSeparator() {
                return lineSeparator;
        }

        public Trace getTrace() {
                return trace;
        }
}
