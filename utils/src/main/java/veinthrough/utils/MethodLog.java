package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodLog {
    private static final String DELIM = "\n    ";

    public static String inLog(String method) {
        return getPositionString(METHOD_POSITION.IN) + getMethodString(method);
    }

    public static String inLog(String method, String... vars) {
        return inLog(method) + DELIM
                + getVarsString(vars);
    }


    public static String midLog(String method, String message, int step) {
        return getPositionString(METHOD_POSITION.MID) + getMethodString(method) + getStepString(step) + DELIM
                + getMessageString(message);
    }

    public static String midLog(String method, int step, String... vars) {
        return getPositionString(METHOD_POSITION.MID) + getMethodString(method) + getStepString(step) + DELIM
                + getVarsString(vars);
    }

    public static String midLog(String method, String message, int step, String... vars) {
        return midLog(method, message, step) + DELIM
                + getVarsString(vars);
    }


    public static String outLog(String method) {
        return getPositionString(METHOD_POSITION.OUT) + getMethodString(method);
    }

    public static String outLog(String method, String... vars) {
        return outLog(method) + DELIM
                + getVarsString(vars);
    }

    private static String getMethodString(String method) {
        return method + "()";
    }

    private static String getPositionString(METHOD_POSITION position) {
        return String.format("[%3s]", position);
    }

    private static String getStepString(int step) {
        return String.format("[%2d]", step);
    }

    private static String getMessageString(String message) {
        return "Message: " + message;
    }

    private static String getVarsString(String... vars) {
        StringBuilder sb = new StringBuilder();
        sb.append("Variables: {");
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
