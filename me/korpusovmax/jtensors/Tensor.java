package me.korpusovmax.jtensors;

public interface Tensor {
    int size();
    int dims();
    int[] shape();
    String toString();
    Tensor copy();

    default Tensor transpose() {
        return this;
    }
    default Tensor T() {
        return transpose();
    }
    default Tensor transpose(int axis) {
        return transpose();
    }

    Tensor operate(Tensor l, Tensors.Operation op);
    default Tensor activate(Tensors.ActivationFunction f) {
        return new Tensors.Scalar(0);
    }
    default Tensor dot(Tensor a) {
        return new Tensors.Scalar(0);
    }

    default Tensor add(Tensor l){
        return this.operate(l, Tensors.Add);
    }
    default Tensor sub(Tensor l){
        return this.operate(l, Tensors.Sub);
    }
    default Tensor div(Tensor l){
        return this.operate(l, Tensors.Div);
    }
    default Tensor mul(Tensor l){
        return this.operate(l, Tensors.Mul);
    }
}
