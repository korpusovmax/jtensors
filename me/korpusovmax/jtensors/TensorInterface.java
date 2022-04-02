package me.korpusovmax.jtensors;

public interface TensorInterface {
    int size();
    int dims();
    int[] shape();
    TensorInterface getValue(int... i);
    String toString();
    TensorInterface copy();

    default TensorInterface transpose() {
        return this;
    }
    default TensorInterface T() {
        return transpose();
    }
    default TensorInterface transpose(int axis) {
        return transpose();
    }

    TensorInterface operate(TensorInterface l, Tensors.Operation op);
    default TensorInterface activate(Tensors.ActivationFunction f) {
        return new Tensors.Scalar(0);
    }
    default TensorInterface dot(TensorInterface a) {
        return new Tensors.Scalar(0);
    }

    default TensorInterface add(TensorInterface l){
        return this.operate(l, Tensors.Add);
    }
    default TensorInterface sub(TensorInterface l){
        return this.operate(l, Tensors.Sub);
    }
    default TensorInterface div(TensorInterface l){
        return this.operate(l, Tensors.Div);
    }
    default TensorInterface mul(TensorInterface l){
        return this.operate(l, Tensors.Mul);
    }
}
