package Polinom;



import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private List<Polinome.Polynomial.Term> terms;

    public Polynomial(String expression) {
        terms = new ArrayList<>();
        String[] termStrings = expression.split("\\+");

        for (String termString : termStrings) {
            Polinome.Polynomial.Term term = parseTerm(termString.trim());
            if (term != null) {
                terms.add(term);
            }
        }
    }

    public static double evaluate(String polynomial) {

        polynomial = polynomial.replaceAll("\\s+", "");


        double result = 0;
        String[] terms = polynomial.split("[\\+\\-]");
        for (String term : terms) {
            if (term.isEmpty()) {
                continue;
            }

            double coefficient = 1;
            double exponent = 0;


            String[] parts = term.split("\\*");
            if (parts.length == 1) {
                coefficient = Double.parseDouble(parts[0]);
            } else if (parts.length == 2) {
                coefficient = Double.parseDouble(parts[0]);
                exponent = Double.parseDouble(parts[1].substring(1));
            }


            if (term.startsWith("-")) {
                result -= coefficient * Math.pow(2, exponent);
            } else {
                result += coefficient * Math.pow(2, exponent);
            }
        }

        return result;
    }

    public static double takeDerivative(String polynomial) {
        // Remove any whitespace from the polynomial string
        polynomial = polynomial.replaceAll("\\s+", "");

        // Take the derivative of the polynomial
        String derivative = polynomial.replaceAll("(?<!\\^)([\\+\\-])(?=\\d+\\*x)", "$1 1*");
        derivative = derivative.replaceAll("(?<!\\^)([\\+\\-])(?=x)", "$1 1");
        derivative = derivative.replaceAll("(?<=\\d)(?=x)", "*");
        derivative = derivative.replaceAll("x(?=[^\\^])", "1*x");

        return evaluate(derivative);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            if (i != 0) {
                sb.append(" + ");
            }
            sb.append(terms.get(i).toString());
        }
        return sb.toString();
    }

    private Polinome.Polynomial.Term parseTerm(String termString) {
        double coefficient = 1.0;
        String variable = "";
        int exponent = 0;

        String[] parts = termString.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                try {
                    coefficient = Double.parseDouble(parts[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid coefficient: " + parts[i]);
                    return null;
                }
            } else if (i == parts.length - 1) {
                try {
                    exponent = Integer.parseInt(parts[i].substring(1));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid exponent: " + parts[i]);
                    return null;
                }
            } else {
                variable = parts[i];
            }
        }

        return new Polinome.Polynomial.Term(coefficient, variable, exponent);
    }
}
