import java.util.Locale;
public final class Complex {
    public static final double EPS = 1e-9;
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex o) {
        return new Complex(re + o.re, im + o.im);
    }

    public Complex subtract(Complex o) {
        return new Complex(re - o.re, im - o.im);
    }

    public Complex multiply(Complex o) {
        return new Complex(re * o.re - im * o.im, re * o.im + im * o.re);
    }

    public Complex divide(Complex o) {
        double denom = o.re * o.re + o.im * o.im;
        if (denom < EPS * EPS) {
            throw new ArithmeticException("Деление на комплексный ноль");
        }
        return new Complex((re * o.re + im * o.im) / denom, (im * o.re - re * o.im) / denom);
    }

    public double abs() {
        return Math.hypot(re, im);
    }

    public boolean isZero() {
        return abs() < EPS;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%.2f%+.2fi", re, im);
    }
}