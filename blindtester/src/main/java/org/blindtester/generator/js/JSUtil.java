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
        if (ClassUtils.isPrimitiveOrWrapper(o.getClass())) {
            System.out.println(ClassUtils.getName(o.getClass()));
        }

        return JSType.OBJECT;
    }

    public static boolean equalType(Object o1, Object o2) {
        return getType(o1) == getType(o2);
    }
}
