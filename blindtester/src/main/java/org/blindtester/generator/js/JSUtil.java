package org.blindtester.generator.js;

import org.apache.commons.lang3.ClassUtils;

import java.util.List;

public class JSUtil {

    public static JSType getType(Object o) {
        if(o == null){
            return JSType.NULL;
        }

        // Check primitive type
        if (ClassUtils.isPrimitiveOrWrapper(o.getClass())) {
            switch (ClassUtils.getName(o.getClass())){
                case "java.lang.Integer":
                    return JSType.NUMBER;
                case "java.lang.Boolean":
                    return JSType.BOOLEAN;
                default:
                    return JSType.OBJECT;
            }
        }

        // Check objects
        if(ClassUtils.getName(o).equals("java.lang.String")){
            return JSType.STRING;
        }

        return JSType.OBJECT;
    }

    public static boolean compareInputs(List<Object> lstO1, List<Object> lstO2) {
        if (lstO1 == null || lstO2 == null){
            return false;
        }

        if (lstO1.size() != lstO2.size()) {
            return false;
        }

        // if a type disagree for 2 elements of the 2 list even for a position => False
        for(Object o1 : lstO1) {
            boolean allTypesEqual = true;
            for(Object o2 : lstO2) {
                if (getType(o1) != getType(o2)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean equalType(Object o1, Object o2) {
        return o1 != null && o2 != null && getType(o1) == getType(o2);
    }

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
