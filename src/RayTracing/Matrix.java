package RayTracing;

/**
 * Created by nogalavi on 09/04/2016.
 */
public class Matrix {

    float[][] array;
    int m, n;

    public Matrix(float[][] A) {
        this.array = A;
        int m = array.length;
        int n = array[0].length;
    }

    // return C = A * B
    public Matrix multiplyByMatrix(Matrix other) {
        float[][] A = this.array;
        float[][] B = other.array;
        int mA = this.m;
        int nA = this.n;
        int mB = other.m;
        int nB = other.n;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        float[][] C = new float[mA][nB];
        for (int i = 0; i < mA; i++)
            for (int j = 0; j < nB; j++)
                for (int k = 0; k < nA; k++)
                    C[i][j] += A[i][k] * B[k][j];
        return new Matrix(C);
    }

    public MyVector multiplyByVector(MyVector other){
        Matrix vectorAsMatrix = other.toMatrix();
        Matrix result = this.multiplyByMatrix(vectorAsMatrix);
        return result.toMyVector();
    }

    public MyVector toMyVector(){
        if (n != 1 || m != 3) {
            throw new RuntimeException("Can't transform a matrix to vector when n!=1 or m != 3");
        }
        MyVector vector = new MyVector(array[0][0], array[1][0], array[2][0]);
        return vector;
    }


}
