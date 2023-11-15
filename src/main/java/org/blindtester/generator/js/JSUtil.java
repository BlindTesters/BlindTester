package org.blindtester.generator.js;

import org.apache.commons.lang3.ClassUtils;

import java.util.List;

/**
 * Class that contains some utilities for Javascript types
 */
public class JSUtil {

    /**
     * Get the 'real' type of the Object
     *
     * @param o the object to detect type
     * @return the JS associated type
     */
    public static JSType getType(Object o) {
        if (o == null) {
            return JSType.NULL;
        }

        // Check primitive type
        if (ClassUtils.isPrimitiveOrWrapper(o.getClass())) {
            switch (ClassUtils.getName(o.getClass())) {
                case "java.lang.Integer":
                    return JSType.NUMBER;
                case "java.lang.Double":
                    return JSType.NUMBER;
                case "java.lang.Boolean":
                    return JSType.BOOLEAN;
                default:
                    return JSType.OBJECT;
            }
        }

        // Check objects
        if (ClassUtils.getName(o).equals("java.lang.String")) {
            return JSType.STRING;
        }

        return JSType.OBJECT;
    }

    /**
     * Compare two list to see if they have the same types of inputs
     *
     * @param lstO1 the first list of function inputs
     * @param lstO2 the second list of function inputs
     * @return
     */
    public static boolean compareInputsTypes(List<Object> lstO1, List<Object> lstO2) {
        if (lstO1 == null || lstO2 == null) {
            return false;
        }

        if (lstO1.size() != lstO2.size()) {
            return false;
        }

        // if a type disagree for 2 elements of the 2 list even for a position => False
        for (int i = 0; i < lstO1.size(); i++) {
            if (getType(lstO1.get(i)) != getType(lstO2.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if two object have the same type
     *
     * @param o1 the first object to compare type
     * @param o2 the second object to compare type
     * @return the indication that type object are the same or not
     */
    public static boolean equalType(Object o1, Object o2) {
        return o1 != null && o2 != null && getType(o1) == getType(o2);
    }

    /**
     * Enum that represent JS types
     */
    public enum JSType {
        UNDEFINED,
        NULL,
        BOOLEAN,
        NUMBER,
        BIGINT,
        STRING,
        SYMBOL,
        FUNCTION,
        OBJECT
    }
}
