public class Main {
    private static final ConsoleReader reader = new ConsoleReader();
    private static ComplexMatrix a = null;
    private static ComplexMatrix b = null;
    private static ComplexMatrix result = null;

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = reader.readInt("Ваш выбор: ");
            try {
                switch (choice) {
                    case 1 -> a = reader.readMatrix("A");
                    case 2 -> b = reader.readMatrix("B");
                    case 3 -> showMatrices();
                    case 4 -> {
                        ComplexMatrix x = chooseMatrix("Первое слагаемое (A/B/R): ");
                        ComplexMatrix y = chooseMatrix("Второе слагаемое (A/B/R): ");
                        saveAndPrint("сумма", x.add(y));
                    }
                    case 5 -> {
                        ComplexMatrix x = chooseMatrix("Первый множитель (A/B/R): ");
                        ComplexMatrix y = chooseMatrix("Второй множитель (A/B/R): ");
                        saveAndPrint("произведение", x.multiply(y));
                    }
                    case 6 -> {
                        ComplexMatrix x = chooseMatrix("Делимое (A/B/R): ");
                        ComplexMatrix y = chooseMatrix("Делитель (A/B/R): ");
                        saveAndPrint("частное", x.divide(y));
                    }
                    case 7 -> {
                        ComplexMatrix x = chooseMatrix("Какую матрицу транспонировать (A/B/R): ");
                        saveAndPrint("транспонированная матрица", x.transpose());
                    }
                    case 8 -> {
                        ComplexMatrix x = chooseMatrix("Для какой матрицы найти определитель (A/B/R): ");
                    System.out.println("Определитель = " + x.determinant());
                    }
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
        System.out.println("3 - Показать матрицы A, B и последний результат R");
        System.out.println("4 - Сложить");
        System.out.println("5 - Умножить");
        System.out.println("6 - Разделить (первая * вторая^-1)");
        System.out.println("7 - Транспонировать");
        System.out.println("8 - Определитель");
        System.out.println("0 - Выход");
        System.out.println("(R - последний полученный результат, его можно использовать как операнд)");
    }

    private static ComplexMatrix chooseMatrix(String prompt) {
        while (true) {
            String s = reader.readLine(prompt).toUpperCase();
            if (s.equals("A")) {
                return need(a, "A");
            }
            if (s.equals("B")) {
                return need(b, "B");
            }
            if (s.equals("R")) {
                if (result == null) {
                    throw new IllegalStateException("Результата ещё нет, сначала выполните любую операцию");
                }
                return result;
            }
            System.out.println("Введите A, B или R");
        }
    }

    private static ComplexMatrix need(ComplexMatrix m, String name) {
        if (m == null) {
            throw new IllegalStateException("Матрица " + name + " ещё не введена (пункт меню 1 или 2)");
        }
        return m;
    }

    private static void saveAndPrint(String title, ComplexMatrix m) {
        result = m;
        System.out.println("Результат (" + title + ") сохранён как R");
        System.out.print(result);
    }

    private static void showMatrices() {
        System.out.println("атрица A:");
        System.out.println(a == null ? "(не введена)" : a);
        System.out.println("Матрица B:");
        System.out.println(b == null ? "(не введена)" : b);
        System.out.println("Последний результат R:");
        System.out.println(result == null ? "(ещё нет)" : result);
    }
}