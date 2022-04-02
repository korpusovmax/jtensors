# Jtensors
0. [About](#about)
1. [Tensors](#tensors)
    - [Scalar](#tensors_scalar)
    - [Vector](#tensors_vector)
    - [Matrix](#tensors_matrix)
    - [NTensor](#tensors_ntensor)
2. [Basic functions](#funcs)
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
Temporarily deleted, after changing the syntax of lib :(
