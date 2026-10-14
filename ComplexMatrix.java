public class ComplexMatrix {
    private final int rows;
    private final int cols;
    private final Complex[][] data;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = Complex.ZERO;
            }
        }
    }

    public  int getRows() {return rows; }
    public  int getCols() {return cols; }
    public Complex get(int i, int j) {return data[i][j]; }
    public void set(int i, int j, Complex value) {data[i][j] = value; }
    public boolean isSquare() {return rows == cols; }

    public  ComplexMatrix add(ComplexMatrix o) {
        if (rows != o.rows || cols != o.cols) {
            throw new IllegalArgumentException("Сложение невозможно, размеры матриц должны совпадать");
        }

        ComplexMatrix r = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                r.data[i][j] = data[i][j].add(o.data[i][j]);
            }
        }
        return r;
    }

    public ComplexMatrix multiply(ComplexMatrix o) {
        if (cols != o.rows) {
            throw new IllegalArgumentException("Умножение невозможно, число столбцов первой матрицы не равно числу строк второй");
        }

        ComplexMatrix r = new ComplexMatrix(rows, o.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < o.cols; j++) {
                Complex sum = Complex.ZERO;
                for (int k = 0; k < cols; k++) {
                    sum = sum.add(data[i][k].multiply(o.data[k][j]));
                }
                r.data[i][j] = sum;
            }
        }
        return r;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix r = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                r.data[j][i] = data[i][j];
            }
        }
        return r;
    }

    public  Complex determinant() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Определитель не существует, матрица не квадратная");
        }
        int n = rows;
        Complex[][] a = copyData();
        Complex det = Complex.ONE;

        for (int col = 0; col < n; col++) {
            int pivot = col;
            for (int r = col + 1; r < n; r++) {
                if (a[r][col].abs() > a[pivot][col].abs()) {
                    pivot = r;
                }
            }
            if(a[pivot][col].isZero()) {
                return Complex.ZERO;
            }
            if (pivot != col) {
                Complex[] tmp = a[pivot];
                a[pivot] = a[col];
                a[col] = tmp;
                det = det.multiply(new Complex(-1, 0));
            }

            det = det.multiply(a[col][col]);
            for (int r = col + 1; r < n; r++) {
                Complex factor = a[r][col].divide(a[col][col]);
                for (int c = col; c < n; c++) {
                    a[r][c] = a[r][c].subtract(factor.multiply(a[col][c]));
                }
            }
        }
        return det;
    }

    public ComplexMatrix inverse() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Обратная матрица есть только у квадратной");
        }
        int n = rows;
        Complex[][] a = new Complex[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = data[i][j];
                a[i][n + j] = (i == j) ? Complex.ONE : Complex.ZERO;
            }
        }

        for (int col = 0; col < n; col++) {
            int pivot = col;
            for (int r = col + 1; r < n; r++) {
                if (a[r][col].abs() > a[pivot][col].abs()) {
                    pivot = r;
                }
            }
            if (a[pivot][col].isZero()) {
                throw new ArithmeticException("Матрица вырожденная, обратной не существует");
            }
            Complex[] tmp = a[pivot];
            a[pivot] = a[col];
            a[col] = tmp;
            Complex p = a[col][col];
            for (int c = 0; c < 2 * n; c++) {
                a[col][c] = a[col][c].divide(p);
            }
            for (int r = 0; r < n; r++) {
                if (r != col) {
                    Complex factor = a[r][col];
                    for (int c = 0; c < 2 * n; c++) {
                        a[r][c] = a[r][c].subtract(factor.multiply(a[col][c]));
                    }
                }
            }
        }

        ComplexMatrix res = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.data[i][j] = a[i][n + j];
            }
        }
        return res;
    }

    public ComplexMatrix divide(ComplexMatrix o) {
        if (!o.isSquare()) {
            throw new IllegalArgumentException("Деление невозможно, делитель должен быть квадратной матрицей");
        }
        if (cols != o.rows) {
            throw new IllegalArgumentException("Деление невозможно, число столбцов первой матрицы должно равняться размеру второй");
        }
        return this.multiply(o.inverse());
    }

    private Complex[][] copyData() {
        Complex[][] copy = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            copy[i] = data[i].clone();
        }
        return copy;
    }

    @Override
    public String toString() {
        int width = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                width = Math.max(width, data[i][j].toString().length());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("| ");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%" + width + "s ", data[i][j]));
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}