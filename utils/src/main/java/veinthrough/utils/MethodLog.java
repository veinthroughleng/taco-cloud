package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodLog {
    private static final String VARIABLES_PRE = "Variables: {";
    private static final String SPACE_PRE = "    ";
    private static final String DELIM_MESSAGE = ", ";
    private static final String DELIM_NEXT_LINE = ",\n" + SPACE_PRE;
    private static final String DELIM_NEXT_VARIABLE = DELIM_NEXT_LINE +
            String.format("%-" + VARIABLES_PRE.length() + "s", " ");
    private static final String DELIM_END_LINE = ".";

    public static String log(String method, String... vars) {
        if (vars.length == 0) {
            return getPositionString() + getMethodString(method) + DELIM_END_LINE;
        } else if (vars.length % 2 != 0) {
            return getPositionString() + getMethodString(method)
                    + DELIM_MESSAGE + vars[0] + DELIM_NEXT_LINE
                    + getVarsString(Arrays.copyOfRange(vars, 1, vars.length))
                    + DELIM_END_LINE;
        } else {
            return getPositionString() + getMethodString(method) + DELIM_NEXT_LINE
                    + getVarsString(vars)
                    + DELIM_END_LINE;
        }
    }

    public static String log(String method, int step, String... vars) {
        if (vars.length == 0) {
            return getPositionString() + getMethodString(method) + DELIM_END_LINE;
        } else if (vars.length % 2 != 0) {
            return getPositionString() + getMethodString(method)
                    + getStepString(step) + DELIM_MESSAGE + vars[0] + DELIM_NEXT_LINE
                    + getVarsString(Arrays.copyOfRange(vars, 1, vars.length))
                    + DELIM_END_LINE;
        } else {
            return getPositionString() + getMethodString(method)
                    + getStepString(step) + DELIM_NEXT_LINE
                    + getVarsString(vars)
                    + DELIM_END_LINE;
        }
    }

    private static String getMethodString(String method) {
        return method + "()";
    }

    private static String getPositionString() {
        return "[IN]";
    }

    private static String getStepString(int step) {
        return String.format("[STEP %2d]", step);
    }

    private static String getVarsString(String... vars) {
        int length = vars.length;
        if (length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Variables: {");
            for (int i = 0; i < length; i += 2) {
                sb.append(vars[i]).append(": ");
                if (i + 1 < length) sb.append(vars[i + 1]);
                if (i + 2 < length) sb.append(";").append(DELIM_NEXT_VARIABLE);
            }
            sb.append("}");
            return sb.toString();
        } else {
            return "";
        }
    }

}
