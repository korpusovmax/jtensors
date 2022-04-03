# Jtensors
0. [About](#about)
1. [Tensors](#tensors)
    - [Scalar](#tensors_scalar)
    - [Vector](#tensors_vector)
    - [Matrix](#tensors_matrix)
    - [NTensor](#tensors_ntensor)
2. [Basic functions](#funcs)
3. [Examples](#examples)
4. [TODO](#todo)
____
<a name="about"></a>
## About
WIP</br>
Jtensors is a simple java library for working with Tensors. Jtensors supports working with different types of tensors and include basic functions like math operations, summation, product, reshaping, genearating arrays, transposing, activation functions (with deriv-s) etc.
____
<a name="tensors"></a>
## Tensors
Here you can see all tensor types and their creation.
There are some types of tensors: scalar (number), vector (list of numbers), matrix (list of vectors), ntensor(list of matrices/ntensors)
<a name="tensors_scalar"></a>
### Scalar
```java
Tensor l = new Tensor(0.6);
```
(With all Tensors you can use the System.out.println(tensor) or toString() function)
<a name="tensors_vector"></a>
### Vector
```java
Tensor l = new Tensor(1, 2, 3);
```
<a name="tensors_matrix"></a>
### Matrix
```java
Tensor l = new Tensor(
        new Tensor(1, 2, 3),
        new Tensor(4, 5, 6)
    );
//instead of this you can use:
//double[][] data = new double[][]{
//        {1, 2, 3},
//        {4, 5, 6}
//};
//Tensor l = new Tensor(new Tensors.Matrix(data));
```
<a name="tensors_ntensor"></a>
### NTensor
```java
Tensor l = new Tensor(
        new Tensor(1, 2, 3),
        new Tensor(4, 5, 6)
    );
Tensor m = new Tensor(l, l, l);
```
____
<a name="funcs"></a>
## Basic functions
All information from this article has been deleted (temporarily) after changing the syntax of lib. It will be updated soon.
____
<a name="examples"></a>
## Examples
The simplest neural network
```java
import me.korpusovmax.jtensors.*;

public class Main {
    public static void main(String args[]) {
        Tensor inputs = new Tensor(
                new Tensor(0, 0, 1),
                new Tensor(0, 1, 1),
                new Tensor(1, 0, 1),
                new Tensor(1, 1, 1)
                );
        Tensor target = new Tensor(0, 0, 1, 1).pack().T();

        Tensor syn0 = new Tensor(Tensors.random(3, 1));

        Tensor l1 = new Tensor(0);
        for (int i = 0; i < 10000; i++) {
            Tensor l0 = inputs;
            l1 = l0.dot(syn0).activate(Tensors.sigmoid);

            Tensor l1error = target.sub(l1);
            Tensor l1delta = l1.activate(Tensors._sigmoid).mul(l1error);
            syn0 = syn0.add(l0.T().dot(l1delta));
        }
        System.out.println(l1);
    }
}
```
Result:
```
[[0.0096677496361140],
 [0.0078629554744772],
 [0.9935914478825549],
 [0.9921178326055041]]
```
____
<a name="todo"></a>
## TODO
These are some plans for the upcoming features.
- [ ] dot product of two vectors and two scalars
- [x] pack() and pack(int n) functions
- [x] transpose() function
- [x] getValue(int... i) and getDouble(int... i) functions
- [ ] reshaping() function
