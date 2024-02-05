package Polinome;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java polynomial.Main input.txt output.txt");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             FileWriter writer = new FileWriter(outputFileName)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;


                line = line.replaceAll("\\s", "");


                if (!Polynomial.isValidExpression(line)) {
                    writer.write("Invalid polynomial\n");
                    continue;
                }


                Polynomial polynomial = new Polynomial(line);


                if (polynomial.hasError()) {
                    writer.write("Invalid polynomial\n");
                    continue;
                }

                // Evaluate the polynomial
                String result = polynomial.evaluate();


                writer.write(result + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}