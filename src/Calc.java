import java.util.List;
import java.util.Scanner;

public class Calc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        System.out.println(calc(input));
    }

    public static String calc(String input) {
        String[] strings = input.split(" ");
        if ((strings.length <= 1 || strings.length > 3) || (isDouble(strings[0]) || isDouble(strings[2])))
            throw new ArithmeticException("the format of the mathematical operation does not satisfy the task");
        String action = strings[1];

        if (isNumArabic(strings[0]) && isNumArabic(strings[2])) {
            int a = Integer.parseInt(strings[0]);
            int b = Integer.parseInt(strings[2]);
            return Integer.toString(innerCalc(action, a, b));
        } else {
            int a = toArabian(strings[0]);
            int b = toArabian(strings[2]);
            if (innerCalc(action, a, b) < 1)
                throw new ArithmeticException("The result of the calculator with Roman numbers can only be positive numbers");
            return toRomanian(innerCalc(action, a, b));

        }
    }

    private static int toArabian(String input) {
        if (isNumArabic(input))
            throw new IllegalArgumentException("different number systems are used in one input");

        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    private static String toRomanian(int number) {
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    private static boolean isNumArabic(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private static boolean isDouble(String str) {
        return str.contains(".");
    }

    private static int innerCalc(String action, int a, int b) {
        if ((a > 10 || b > 10) || (a == 0 || b == 0))
            throw new IllegalArgumentException("numbers should be from 1 to 10 inclusive");
        return switch (action) {
            case "+" -> (a + b);
            case "-" -> (a - b);
            case "*" -> (a * b);
            case "/" -> (a / b);
            default -> throw new ArithmeticException("incorrect action");
        };
    }
}
