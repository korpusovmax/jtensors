package me.korpusovmax.jtensors;

public class Tensor {
    public TensorInterface tens;

    public Tensor(TensorInterface tens) {
        this.tens = tens;
    }
    public Tensor(double... a) {
        if (a.length == 0) {
            this.tens = new Tensors.Scalar(0);
        } else if (a.length == 1) {
            this.tens = new Tensors.Scalar(a[0]);
        } else {
            this.tens = new Tensors.Vector(a);
        }
    }
    public Tensor(Tensor... a) {
        this.tens = new Tensors.Scalar(0);
        if (a.length == 1) {
            this.tens = a[0].tens;
        } else if (a.length > 0) {
            if (a[0].tens instanceof Tensors.Vector) {
                Tensors.Vector[] vecs = new Tensors.Vector[a.length];
                for (int i = 0; i < a.length; i++) {
                    vecs[i] = ((Tensors.Vector) a[i].tens.copy());
                }
                this.tens = new Tensors.Matrix(vecs);
            }
            if (a[0].tens instanceof Tensors.Matrix) {
                Tensors.Matrix[] vecs = new Tensors.Matrix[a.length];
                for (int i = 0; i < a.length; i++) {
                    vecs[i] = ((Tensors.Matrix) a[i].tens.copy());
                }
                this.tens = new Tensors.NTensor(vecs);
            }
        }
    }

    public int size() {
        return tens.size();
    }
    public int dims() {
        return tens.dims();
    }
    public int[] shape() {
        return tens.shape();
    }
    public String toString() {
        return tens.toString();
    }
    public Tensor copy() {
        return new Tensor(tens.copy());
    }
    public Tensor pack() {
        if (tens instanceof Tensors.Scalar t) {
            return new Tensor(new Tensors.Vector(t.value));
        }
        if (tens instanceof Tensors.Vector t) {
            return new Tensor(new Tensors.Matrix(t));
        }
        if (tens instanceof Tensors.Matrix t) {
            return new Tensor(new Tensors.NTensor(t));
        }
        return new Tensor(new Tensors.NTensor((Tensors.NTensor)tens));
    }
    public Tensor pack(int dims) {
        Tensor res = copy();
        for (int i = 0; i < dims; i++) {
            res = res.pack();
        }
        return res;
    }

    public Tensor getValue(int... i) {
        return new Tensor(tens.getValue(i));
    }
    public double getDouble(int... i) {
        assert i.length == dims(): "The arguments count must be equal to dimensions count";
        return ((Tensors.Scalar) getValue(i).tens).value;
    }

    public Tensor transpose() {
        return new Tensor(tens.transpose());
    }
    public Tensor T() {
        return new Tensor(tens.transpose());
    }
    public Tensor transpose(int axis) {
        return new Tensor(tens.transpose(axis));
    }

    public Tensor operate(Tensor a, Tensors.Operation op) {
        return new Tensor(tens.operate(a.tens, op));
    }
    public Tensor activate(Tensors.ActivationFunction f) {
        return new Tensor(tens.activate(f));
    }

    public Tensor dot(Tensor l) {
        return new Tensor(tens.dot(l.tens));
    }

    public Tensor add(Tensor l) {
        return new Tensor(tens.add(l.tens));
    }
    public Tensor sub(Tensor l) {
        return new Tensor(tens.sub(l.tens));
    }
    public Tensor mul(Tensor l) {
        return new Tensor(tens.mul(l.tens));
    }
    public Tensor div(Tensor l) {
        return new Tensor(tens.div(l.tens));
    }
}
