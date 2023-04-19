package org.blindtester.generator.js;

import org.apache.commons.lang3.ClassUtils;
import org.blindtester.generator.Call;

import java.util.List;

public class JSUtil {

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

    public static JSType getType(Object o) {
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

    public static boolean equalType(Object o1, Object o2) {
        return getType(o1) == getType(o2);
    }
}
