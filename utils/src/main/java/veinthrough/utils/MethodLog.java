package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodLog {
    private static final String VARIABLES_PRE = "Variables: {";
    private static final String SPACE_PRE = "    ";
    private static final String DELIM = "\n" + SPACE_PRE;
    private static final String DELIM_NEXT_VARIABLE = DELIM +
            String.format("%-" + VARIABLES_PRE.length() + "s", " ");

    public static String inLog(String method, String... vars) {
        return getPositionString(METHOD_POSITION.IN) + getMethodString(method) + DELIM
                + getVarsString(vars);
    }

    public static String midLog(String method, int step, String... vars) {
        return getPositionString(METHOD_POSITION.MID) + getMethodString(method)
                + getStepString(step) + DELIM
                + getVarsString(vars);
    }

    public static String midLog(String method, String message, int step, String... vars) {
        return getPositionString(METHOD_POSITION.MID) + getMethodString(method)
                + getStepString(step) + getMessageString(message) + DELIM
                + getVarsString(vars);
    }


    public static String outLog(String method, String... vars) {
        return getPositionString(METHOD_POSITION.OUT) + getMethodString(method) + DELIM
                + getVarsString(vars);
    }

    private static String getMethodString(String method) {
        return method + "()";
    }

    private static String getPositionString(METHOD_POSITION position) {
        return String.format("[%3s]", position);
    }

    private static String getStepString(int step) {
        return String.format("[STEP %2d]", step);
    }

    private static String getMessageString(String message) {
        return "Message: " + message;
    }

    private static String getVarsString(String... vars) {
        int length = vars.length;
        if (length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Variables: {");
            for (int i = 0; i < length; i += 2) {
                sb.append(vars[i]).append(": ");
                if (i + 1 < length) sb.append(vars[i + 1]);
                if (i + 2 < length) sb.append(";" + DELIM_NEXT_VARIABLE);
            }
            sb.append("}");
            return sb.toString();
        } else {
            return "";
        }
    }

    public enum METHOD_POSITION {
        IN, MID, OUT
    }
}
