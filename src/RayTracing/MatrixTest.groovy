package RayTracing

import org.junit.Test

class MatrixTest extends GroovyTestCase {

    @Test
   void testCreateRotationMatrix() {
        double a = 6.0;
        double b = 0.0;
        double c = 0.0;

        double[] rotationAngles = new double[3];
        rotationAngles[0] = a;
        rotationAngles[1] = b;
        rotationAngles[2] = c;
        MyVector vectorToTotate = new MyVector(1.0, 0.5, 0.2);
        Matrix rotationMatrix = Matrix.createRotationMatrix(rotationAngles);
        MyVector rotatedVector = rotationMatrix.multiplyByVector(vectorToTotate);

//1,0.476355,0.251169
        assert(rotatedVector._x == 1 && rotatedVector._y == 0.47635525503060594 && rotatedVector._z == 0.25116861070748137);


    }
}
