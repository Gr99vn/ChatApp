package utils;

public class StringUtil {
    public static String doTitle(String input) {
        String[] pieces = input.split(" ");
        String output = "";
        for (String str : pieces) {
            output += capitalize(str) + " ";
        }
        return output.trim();
    }

    private static String capitalize(String input) {
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
    }
}
