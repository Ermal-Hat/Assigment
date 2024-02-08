package Polinom;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <input_file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = "output.txt"; // Assuming the output file name is fixed

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                Polynomial polynomial = new Polynomial(line);
                polynomial.evaluate();
                polynomial.takeDerivative();
                writer.write(polynomial.toString());
                writer.newLine();

                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
