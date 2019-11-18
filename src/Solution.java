import java.util.Arrays;
import java.util.Random;

public class Solution {

    private double[] a;
    private double[] b;
    private double[] c;
    private double[] p;
    private double[] q;
    private double[] r;
    private double[] f;
    private int length = 14;
    private int k = 4;
    private int l = 9;

    public void calculate() {
        a = randomFillVector(length);
        b = randomFillVector(length);
        c = randomFillVector(length);
        p = randomFillVector(length);
        q = randomFillVector(length);
        f = randomFillVector(length);
        setIntersection();

        printMatrix();
        doFirstStep();
        doSecondStep();
        doThirdStep();
        doFourthStep();
//        doFifthStep();
        printMatrix();
        System.out.println(Arrays.toString(f));
    }

    private void printVectors() {
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.toString(p));
        System.out.println(Arrays.toString(q));
    }

    private void printMatrix() {
        double[][] matrix = new double[length][length];
        matrix[0][0] = b[0];
        matrix[0][1] = c[0];

        for (int i = 1; i < length - 1; i++) {
            matrix[i][i - 1] = a[i];
            matrix[i][i] = b[i];
            matrix[i][i + 1] = c[i];
        }

        matrix[length - 1][length - 2] = a[length - 1];
        matrix[length - 1][length - 1] = b[length - 1];

        for (int i = 0; i < length; i++) {
            matrix[k][i] = p[i];
            matrix[l][i] = q[i];
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                //System.out.print(matrix[i][j] + " ");
                System.out.printf("| %06.3f ", matrix[i][j]);
            }

/*            System.out.println();
            for (int j = 0; j < length * 9; j++) {
                System.out.print("-");
            }*/
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }

    private double[] randomFillVector(int length) {
        double leftLimit = 0.1;
        double rightLimit = 10;
        double[] vector = new double[length];

        for (int i = 0; i < length; i++) {
            vector[i] = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
        }

        return vector;
    }

    private void setIntersection() {
        p[k - 1] = a[k];
        p[k] = b[k];
        p[k + 1] = c[k];
        q[l - 1] = a[l];
        q[l] = b[l];
        q[l + 1] = c[l];
    }

    private void doFirstStep() {
        double R;
        for (int i = 0; i < k; i++) {
            R = 1 / b[i];
            b[i] = 1;
            c[i] *= R;
            f[i] *= R;
            R = a[i + 1];
            a[i + 1] = 0;
            b[i + 1] -= R * c[i];
            f[i + 1] -= R * f[i];
            R = p[i];
            p[i] = 0;
            p[i + 1] -= R * c[i];
            f[k] -= R * f[i];
            R = q[i];
            q[i] = 0;
            q[i + 1] -= R * c[i];
            f[l] -= R * f[i];
        }
    }

    private void doSecondStep() {
        double R;
        for (int i = length - 1; i > l; i--) {
            R = 1 / b[i];
            b[i] = 1;
            a[i] *= R;
            f[i] *= R;
            R = c[i - 1];
            c[i - 1] = 0;
            b[i - 1] -= R * a[i];
            f[i - 1] -= R * f[i];
            R = q[i];
            q[i] = 0;
            q[i - 1] -= R * a[i];
            f[l] -= R * f[i];
            R = p[i];
            p[i] = 0;
            p[i - 1] -= R * a[i];
            f[k] -= R * f[i];
        }
    }

    private void doThirdStep() {
        double R;
        r = new double[length];
        r[k + 1] = a[k + 1];

        for (int i = k + 1; i < l; i++) {
         R = 1 / b[i];
         b[i] = 1;
         r[i] = R * r[i];
         c[i] = R * c[i];
         f[i] *= R;
         R = a[i + 1];
         a[i + 1] = 0;
         r[i + 1] = - R * r[i];
         b[i + 1] -= R *c[i];
         f[i + 1] -= R * f[i];
         R = p[i];
         p[i] = 0;
         p[k] -= R * r[i];
         p[i + 1] -= R * c[i];
         f[k] -= R * f[i];
         R = q[i];
         q[i] = 0;
         q[k] -= R * r[i];
         q[i + 1] -= R * c[i];
         f[l] -= R * f[l];
        }
    }

    private void doFourthStep() {
        double R = 1 / p[k];
        p[k] = 1;
        p[l] = R * p[l];
        f[k] *= R;
        R = q[k];
        q[k] = 0;
        q[l] -= R * p[l];
        f[l] -= R * f[k];
        R = q[l];
        q[l] = 1;
        p[l] -= q[l] * p[l]; // удаление числа в p[l]???????????? менять f????
        f[l] *= R;
    }

    private void doFifthStep() {
        double R;
        for (int i = k + 1; i < l - 1; i++) {
            R = r[i];
            r[i] = 0;
            f[i] -= R * f[k];
        }
    }
}
