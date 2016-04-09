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

    public static Matrix createRotationMatrix(double xAngle, double yAngle, double zAngle) {
        Matrix xRotation = createXrotationMatrix(xAngle);
        Matrix yRotation = createYrotationMatrix(yAngle);
        Matrix zRotation = createZrotationMatrix(zAngle);
        Matrix xByY = xRotation.multiplyByMatrix(yRotation);
        Matrix result = xByY.multiplyByMatrix(zRotation);
        return result;

    }

    private static Matrix createXrotationMatrix(double xAngle) {
        float cosX = (float) Math.cos(Math.toRadians(xAngle));
        float sinX = (float) Math.sin(Math.toRadians(xAngle));
        float[][] array = new float[3][3];
        array[0][0] = 1;
        array[0][1] = 0;
        array[0][2] = 0;
        array[1][0] = 0;
        array[1][1] = cosX;
        array[1][2] = -1 * sinX;
        array[2][0] = 0;
        array[2][1] = sinX;
        array[2][2] = cosX;
        return new Matrix(array);
    }

    private static Matrix createYrotationMatrix(double yAngle) {
        float cosY = (float) Math.cos(Math.toRadians(yAngle));
        float sinY = (float) Math.sin(Math.toRadians(yAngle));
        float[][] array = new float[3][3];
        array[0][0] = cosY;
        array[0][1] = 0;
        array[0][2] = sinY;
        array[1][0] = 0;
        array[1][1] = 1;
        array[1][2] = 0;
        array[2][0] = -1 * sinY;
        array[2][1] = 0;
        array[2][2] = cosY;
        return new Matrix(array);
    }

    private static Matrix createZrotationMatrix(double zAngle) {
        float cosZ = (float) Math.cos(Math.toRadians(zAngle));
        float sinZ = (float) Math.sin(Math.toRadians(zAngle));
        float[][] array = new float[3][3];
        array[0][0] = cosZ;
        array[0][1] = -1 * sinZ;
        array[0][2] = 0;
        array[1][0] = sinZ;
        array[1][1] = cosZ;
        array[1][2] = 0;
        array[2][0] = 0;
        array[2][1] = 0;
        array[2][2] = 1;
        return new Matrix(array);
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
