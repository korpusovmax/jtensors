# Jtensors
Simple Java tensors library

## Examples
### Creating different d tensors
#### Scalar
```java
Tensor k = new Tensors.Scalar(3.0d);
```
#### Vector
```java
Tensor k = new Tensors.Vector(3.0d, 1.0d, 0.5d);
//or
double[] vec = new double[]{3.0d, 1.0d, 0.5d};
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
