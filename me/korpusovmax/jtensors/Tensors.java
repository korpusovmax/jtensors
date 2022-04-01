package me.korpusovmax.sann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tensors {
    //TENSORS TYPES (SCALARS/VECTORS/MATRICES/NTENSORS)
    public static class Scalar implements Tensor {
        public double value;

        public Scalar(double value) {
            this.value = value;
        }

        public int size() {
            return 1;
        }
        public int dims() {
            return 0;
        }
        public int[] shape() {
            return new int[]{};
        }
        public String toString() {
            return ("" + value).replaceAll("\\.0", "");
        }
        public Tensor copy() {
            return new Scalar(value);
        }

        //element wise math operations
        public Tensor operate(Tensor l, Operation op) {
            assert l instanceof Scalar : "Illegal operation";

            Scalar result = new Scalar(value);
            result.value = op.activate(value, ((Scalar)l).value);
            return result;
        }
        public Tensor activate(ActivationFunction fn) {
            return new Scalar(fn.activate(value));
        }
        //todo:dot
    }

    public static class Vector implements Tensor {
        public double[] value;

        public Vector(double ...array) {
            this.value = array.clone();
        }

        public int size() {
            return value.length;
        }
        public int dims() {
            return 1;
        }
        public int[] shape() {
            return new int[]{value.length};
        }
        public String toString() {
            return Arrays.toString(value).replaceAll("\\.0", "");
        }
        public Tensor copy() {
            return new Vector(value);
        }

        public double sum() {
            return Arrays.stream(value).sum();
        }

        public Tensor operate(Tensor l, Operation op) {
            assert l instanceof Scalar || (l instanceof Vector && l.size() == size()) : "Illegal operation";

            Vector result = new Vector(value);
            if (l instanceof Vector) {
                result.value = IntStream.range(0, result.size()).mapToDouble(x -> op.activate(result.value[x], ((Vector)l).value[x])).toArray();
            } else {
                result.value = Arrays.stream(result.value).map(x -> op.activate(x, ((Scalar)l).value)).toArray();
            }
            return result;
        }
        public Vector activate(ActivationFunction fn) {
            return new Vector(IntStream.range(0, value.length).mapToDouble(x -> fn.activate((double)value[x])).toArray());
        }
        public Tensor dot (Tensor l) {
            Matrix a = (Matrix) new Matrix(this);

            if (l instanceof Matrix b) {
                return new Vector(((Matrix) a.dot(b)).value[0]);
            }
            return new Scalar(0);
        }
    }

    public static class Matrix implements Tensor {
        public double[][] value;

        public Matrix(double[][] array) {
            this.value = new double[array.length][array[0].length];
            for (int i = 0; i < array.length; i++) {
                this.value[i] = array[i].clone();
            }
        }
        public Matrix(Tensors.Vector ... array) {
            this.value = new double[array.length][array[0].size()];
            for (int i = 0; i < array.length; i++) {
                this.value[i] = array[i].value.clone();
            }
        }
        public Matrix(ArrayList<Vector> array) {
            this.value = new double[array.size()][array.get(0).size()];
            for (int i = 0; i < array.size(); i++) {
                this.value[i] = array.get(i).value.clone();
            }
        }

        public int size() {
            return value.length * value[0].length;
        }
        public int dims() {
            return 2;
        }
        public int[] shape() {
            return new int[]{value.length, value[0].length};
        }
        public String toString() {
            String s = Arrays.deepToString(value).replaceAll(", \\[", ",\n\\[");
            return s.replaceAll("\\.0", "");
        }
        public Tensor copy() {
            return new Matrix(value);
        }

        public Tensor transpose() {
            int width = value[0].length;
            int height = value.length;

            double[][] transposed = IntStream.range(0, width).collect(() -> new double[width][height], (a, i) -> a[i] = IntStream.range(0, height).collect(() -> new double[height], (b, j) -> b[j] = value[j][i], (b, j) -> {}), (a, i) -> {});
            return new Matrix(transposed);
        }

        public Tensor operate(Tensor l, Operation op) {
            assert l instanceof Scalar || (l instanceof Vector && l.size() == value[0].length) || (l instanceof Matrix && ((Matrix)l).value.length == value.length && ((Matrix)l).value[0].length == value[0].length) : "Illegal operation";

            Matrix result = new Matrix(value);
            if (l instanceof Scalar) {
                result.value = IntStream.range(0, value.length).mapToObj(x -> IntStream.range(0, value[0].length).mapToDouble(y -> op.activate(result.value[x][y], ((Scalar)l).value)).toArray()).toArray(double[][]::new);
            } else if (l instanceof Vector) {
                result.value = IntStream.range(0, value.length).mapToObj(x -> IntStream.range(0, result.value[0].length).mapToDouble(y -> op.activate(result.value[x][y], ((Vector)l).value[y])).toArray()).toArray(double[][]::new);
            } else {
                result.value = IntStream.range(0, value.length).mapToObj(x -> IntStream.range(0, result.value[0].length).mapToDouble(y -> op.activate(result.value[x][y], ((Matrix)l).value[x][y])).toArray()).toArray(double[][]::new);
            }
            return result;
        }
        public Tensor activate (ActivationFunction fn) {
            Tensors.Matrix result = this;
            result.value = IntStream.range(0, value.length).mapToObj(x -> IntStream.range(0, value[0].length).mapToDouble(y -> fn.activate(result.value[x][y])).toArray()).toArray(double[][]::new);
            return result;
        }
        public Tensor dot(Tensor l) {
            Matrix a = this;

            if (l instanceof Matrix b) {
                assert a.value[0].length == b.value.length : "The number of columns in first matrix must be equal to the number of rows in the second matrix";

                int rows = a.value.length;
                int columns = b.value[0].length;
                Matrix _b = (Matrix) b.transpose();

                double[][] ret = IntStream.range(0, rows).mapToObj(
                        r -> IntStream.range(0, columns).mapToDouble(
                                c -> ((Vector) new Vector(a.value[r]).mul(new Vector(_b.value[c]))).sum()
                        ).toArray()
                ).toArray(double[][]::new);
                return new Matrix(ret);
            }
            return new Scalar(0);
        }
    }

    public static class NTensor implements Tensor {
        public Tensor[] value;

        public NTensor(Tensor ...array) {
            this.value = new Tensor[array.length];

            int i = 0;
            for (Tensor t : array) {
                this.value[i] = t.copy();
                i++;
            }
        }

        public int size() {
            return value.length * value[0].size();
        }
        public int dims() {
            return value[0].dims() + 1;
        }
        public int[] shape() {
            return new int[]{value.length, value[0].size()};
        }
        public String toString() {
            String s = "[" + Arrays.stream(value).map(Tensor::toString).collect(Collectors.joining(",\n")) + "]";
            return s.replaceAll("\\.0", "");
        }
        public Tensor copy() {
            return new NTensor(value);
        }

        public Tensor transpose() {
            NTensor result = (NTensor) copy();
            result.value[0] = result.value[0].transpose();
            return result;
        }
        public Tensor transpose(int axis) {
            NTensor result = (NTensor) copy();
            result.value[axis] = result.value[axis].transpose();
            return result;
        }

        public Tensor operate(Tensor l, Operation op) {
            assert l.dims() == dims() : "Illegal operation";

            NTensor result = new NTensor(value);
            if (l instanceof NTensor) {
                result.value = IntStream.range(0, value.length).mapToObj(x -> (value[x]).operate(((NTensor)l).value[x], op)).toArray(Tensor[]::new);
            } else {
                result.value = Arrays.stream(value).map(x -> x.operate(l, op)).toArray(Tensor[]::new);
            }
            return result;
        }
        //todo: activate func
//        public Tensor activate(ActivationFunction f) {
//            NTensor result = new NTensor(value);
//            result.value = IntStream.range(0, value.length).mapToObj(x -> result.value[x]).toArray(Tensor[]::new);
//            return result;
//        }
    }

    //ACTIVATION FUNCTIONS (example: relu) & OPERATIONS (example: addition)
    public interface ActivationFunction {
        double activate(double n);
    }
    public static ActivationFunction sigmoid, _sigmoid, relu, _relu, tanh, _tanh;
    static {
        sigmoid = x -> 1 / (1 + Math.exp(-1 * x));
        _sigmoid = x -> x * (1 - x);
        tanh = Math::tanh;
        _tanh = x -> 1 - (x*x);
        relu = x -> Math.max(0, x);
        _relu = x -> (x > 0? 1: 0);
    }

    public interface Operation {
        double activate(double n, double m);
    }
    public static Operation Add, Sub, Mul, Div;
    static {
        Add = Double::sum; //same with (n, m) -> n + m
        Sub = (n, m) -> n - m;
        Mul = (n, m) -> n * m;
        Div = (n, m) -> n / m;
    }

    //BASIC INIT TENSORS FUNCTIONS
    public static Vector zeros(int len) {
        return new Vector(new double[len]);
    }
    public static Matrix zeros(int len, int len2) {
        return new Matrix(new double[len][len2]);
    }

    public static Vector arrange(int x) {
        return new Vector(IntStream.range(0, x).mapToDouble(val -> val).toArray());
    }
    public static Vector arrange(int x, int y) {
        return new Vector(IntStream.range(x, y).mapToDouble(val -> val).toArray());
    }

    public static Scalar random(int max) {
        return new Scalar(randDouble(max));
    }
    public static Vector random(int len, int max) {
        return new Vector(IntStream.range(0, len).mapToDouble(val -> randDouble(max)).toArray());
    }
    public static Matrix random(int x, int y, int max) {
        ArrayList<Vector> res = new ArrayList<Vector>();
        for (int i = 0; i < y; i++) {
            res.add(new Vector(IntStream.range(0, x).mapToDouble(val -> randDouble(max)).toArray()));
        }
        return new Matrix(res);
    }

    //utils
    private static double randDouble(int min, int max) {
        return Math.random() * ((max - min)) + min;
    }
    private static double randDouble(int max) {
        return Math.random() * max;
    }
}
