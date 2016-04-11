package RayTracing;

/**
 * Created by nogalavi on 09/04/2016.
 */
public class Matrix {

    double[][] array;
    int m, n;

    public Matrix(double[][] A) {
        this.array = A;
        m = array.length;
        n = array[0].length;
    }

    public static Matrix createRotationMatrix(double[] angles) {
        double xAngle = angles[0];
        double yAngle = angles[1];
        double zAngle = angles[2];
        Matrix xRotation = createXrotationMatrix(xAngle);
        Matrix yRotation = createYrotationMatrix(yAngle);
        Matrix zRotation = createZrotationMatrix(zAngle);
        //Matrix xByY = xRotation.multiplyByMatrix(yRotation);
        //Matrix result = xByY.multiplyByMatrix(zRotation);
        Matrix result = zRotation.multiplyByMatrix(yRotation).multiplyByMatrix(xRotation);
        return result;

    }

    private static Matrix createXrotationMatrix(double xAngle) {
        double cosX = (double) Math.cos(Math.toRadians(xAngle));
        double sinX = (double) Math.sin(Math.toRadians(xAngle));
        double[][] array = new double[3][3];
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
        double cosY = (double) Math.cos(Math.toRadians(yAngle));
        double sinY = (double) Math.sin(Math.toRadians(yAngle));
        double[][] array = new double[3][3];
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
        double cosZ = (double) Math.cos(Math.toRadians(zAngle));
        double sinZ = (double) Math.sin(Math.toRadians(zAngle));
        double[][] array = new double[3][3];
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
        double[][] A = this.array;
        double[][] B = other.array;
        int mA = this.m;
        int nA = this.n;
        int mB = other.m;
        int nB = other.n;
        if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] C = new double[mA][nB];
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
