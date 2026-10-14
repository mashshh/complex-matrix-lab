import java.util.Scanner;

public class ConsoleReader {
    private final Scanner in = new java.util.Scanner(System.in);
    public String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Нужно целое число");
            }
        }
    }

    public int readPositiveInt(String prompt) {
        while (true) {
            int x = readInt(prompt);
            if (x > 0) {
                return x;
            }
            System.out.println("Число должно быть больше 0");
        }
    }

    public  Complex readComplex(String prompt) {
        while (true) {
            String[] parts = readLine(prompt).replace(',', '.').split("\\s+");
            if (parts.length == 2) {
                try {
                    double re = Double.parseDouble(parts[0]);
                    double im = Double.parseDouble(parts[1]);
                    return new Complex(re, im);
                } catch (NumberFormatException e) {
                }
            }
            System.out.println("Неверный формат. Введите два числа через пробел");
        }
    }

    public ComplexMatrix readMatrix(String name) {
        int rows = readPositiveInt("Число строк матрицы " + name + ": ");
        int cols = readPositiveInt("Число столбцов матрицы " + name + ": ");
        ComplexMatrix m = new ComplexMatrix(rows, cols);
        System.out.println("Вводите элементы построчно");
        System.out.println("Формат комплексного числа: <действительная часть> <мнимая часть>");
        System.out.println("Например, для 3-2i введите 3 -2");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.set(i, j, readComplex(name + "[" + (i + 1) + "][" + (j + 1) + "]: "));
            }
        }
        return m;
    }
}