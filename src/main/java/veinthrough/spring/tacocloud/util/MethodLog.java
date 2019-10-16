package veinthrough.spring.tacocloud.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodLog {
    public static String inLog(String method) {
        return getMethodString(method) + getPositionString(METHOD_POSITION.IN);
    }

    public static String inLog(String method, String... vars) {
        return inLog(method) + getVarsString(vars);
    }


    public static String midLog(String method, String message, int step) {
        return getMethodString(method)
                + getPositionString(METHOD_POSITION.MID)
                + message
                + getStepString(step);
    }

    public static String midLog(String method, int step, String... vars) {
        return getMethodString(method)
                + getPositionString(METHOD_POSITION.MID)
                + getStepString(step)
                + getVarsString(vars);
    }

    public static String midLog(String method, String message, int step, String... vars) {
        return midLog(method, message, step) + getVarsString(vars);
    }


    public static String outLog(String method) {
        return getMethodString(method) + getPositionString(METHOD_POSITION.OUT);
    }

    public static String outLog(String method, String... vars) {
        return outLog(method) + getVarsString(vars);
    }

    private static String getMethodString(String method) {
        return "---" + method + "()";
    }

    private static String getPositionString(METHOD_POSITION position) {
        return String.format("[%3s]", position);
    }

    private static String getStepString(int step) {
        return String.format("[%2d]", step);
    }

    private static String getVarsString(String... vars) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int length = vars.length;
        for (int i = 0; i < length; i += 2) {
            sb.append(vars[i]).append(":");
            if (i + 1 < length) sb.append(vars[i + 1]);
            if (i + 2 < length) sb.append(";");
        }
        sb.append("}");
        return sb.toString();
    }

    public enum METHOD_POSITION {
        IN, MID, OUT
    }
}
