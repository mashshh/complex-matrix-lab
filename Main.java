public class Main {
    private static final ConsoleReader reader = new ConsoleReader();
    private static ComplexMatrix a = null;
    private static ComplexMatrix b = null;

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = reader.readInt("Ваш выбор: ");
            try {
                switch (choice) {
                    case 1 -> a = reader.readMatrix("A");
                    case 2 -> b = reader.readMatrix("B");
                    case 3 -> showMatrices();
                    case 4 -> { needBoth(); printResult("A + B", a.add((b))); }
                    case 5 -> { needBoth(); printResult("A * B", a.multiply((b))); }
                    case 6 -> { needBoth(); printResult("A / B", a.divide((b))); }
                    case 7 -> printResult("транспонированная", chooseMatrix().transpose());
                    case 8 -> System.out.println("Определитель = " + chooseMatrix().determinant());
                    case 0 -> {
                        System.out.println("До свидания!");
                        return;
                    }
                    default -> System.out.println("Нет такого пункта меню");
                }
            } catch (IllegalArgumentException | ArithmeticException | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n Матрицы комплексных чисел: ");
        System.out.println("1 - Ввести матрицу A");
        System.out.println("2 - Ввести матрицу B");
        System.out.println("3 - Показать матрицы A и B");
        System.out.println("4 - A + B");
        System.out.println("5 - A * B");
        System.out.println("6 - A / B (A * B^-1)");
        System.out.println("7 - Транспонировать (A или B)");
        System.out.println("8 - Определитель (A или B)");
        System.out.println("0 - Выход");
    }

    private static ComplexMatrix need(ComplexMatrix m, String name) {
        if (m == null) {
            throw new IllegalStateException("Матрица " + name + " ещё не введена (пункт меню 1 или 2)");
        }
        return m;
    }

    private static ComplexMatrix chooseMatrix() {
        while (true) {
            String s = reader.readLine("Какую матрицу взять? (A/B): ").toUpperCase();
            if (s.equals("A")) return need(a, "A");
            if (s.equals("B")) return need(b, "B");
            System.out.println("Введите A или B");
        }
    }

    private static void showMatrices() {
        System.out.println("Матрица A:");
        System.out.println(a == null ? "(не введена)" : a);
        System.out.println("Матрица B:");
        System.out.println(b == null ? "(не введена)" : b);
    }

    private static void needBoth() {
        if (a == null && b == null) {
            throw new IllegalStateException("Матрицы A и B ещё не введены (пункты меню 1 и 2)");
        }
        need(a, "A");
        need(b, "B");
    }

    private static void printResult(String title, ComplexMatrix m) {
        System.out.println("Результат (" + title + "):");
        System.out.println(m);
    }
}