# Jtensors
Simple Java tensors library

## Basic functions
### Creating different type tensors
#### Scalar
```java
Tensor k = new Tensors.Scalar(3.0d);
```
#### Vector
```java
Tensor k = new Tensors.Vector(3, 1, 0);
//or
double[] vec = new double[]{3.0d, 1.0d, 0.0d};
Tensor k = new Tensors.Vector(vec);
```
#### Matrix
```java
double[][] vec = new double[][]{{3.0d, 1.0d, 0.5d}, {7.0d, 0.5d, 32.0d}};
Tensor k = new Tensors.Matrix(vec);
```
#### NTensor
```java
double[][] vec1 = new double[][]{{3.0d, 1.0d, 0.5d}, {7.0d, 0.5d, 32.0d}};
Tensor k1 = new Tensors.Matrix(vec1);
double[][] vec2 = new double[][]{{7.0d, 14.6d, 1.0d}, {4.0d, 8.9d, 2.0d}};
Tensor k2 = new Tensors.Matrix(vec2);
Tensor ntensor = new Tensors.NTensor(k1, k2);
```
### Arrange
```java
Tensor k = Tensors.arrange(5);
```
```
[0.0, 1.0, 2.0, 3.0, 4.0]
```
### Operate
```java
Tensor k = new Tensors.Vector(1, 0, 3, 4);
k = k.operate(new Tensors.Scalar(5), (x, y) -> x * y - 3);
```
```
[2.0, -3.0, 12.0, 17.0]
```
### Shape
```java
Tensor k = new Tensors.Matrix(new double[][]{{1, 5, -1}, {2, 3, 4}});
System.out.println(k.shape()[0] + ", " + k.shape()[1]);
```
```
2, 3
```
### Transposing
#### matrix
```java
Tensor k = new Tensors.Matrix(new double[][]{{1, 5, -1}, {2, 3, 4}});
System.out.println(k.transpose());
```
```
[[1.0, 2.0],
[5.0, 3.0],
[-1.0, 4.0]]
```
#### ntensor
```java
Tensor k = new Tensors.Matrix(new double[][]{
        {1, 5, -1}, 
        {2, 3, 4}
});
Tensor m = new Tensors.Matrix(new double[][]{
        {12, 4, -56}, 
        {7, 23, 9}
});
Tensor ntensor = new Tensors.NTensor(k, m);

System.out.println(ntensor.transpose() + "\n");
System.out.println(ntensor.transpose(1)); //1 - transposing axis, default - 0
```
```
[[[1.0, 2.0],
[5.0, 3.0],
[-1.0, 4.0]],
[[12.0, 4.0, -56.0],
[7.0, 23.0, 9.0]]]

[[[1.0, 5.0, -1.0],
[2.0, 3.0, 4.0]],
[[12.0, 7.0],
[4.0, 23.0],
[-56.0, 9.0]]]
```
