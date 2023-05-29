import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    static void multiplyParallel(double[][] a, double[][] b, double[][] c, int size) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i=0;i<size;i++) {
            int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int j=0;j<size;j++) {
                        for (int k=0;k<size;k++) {
                            c[finalI][j] += a[finalI][k] * b[k][j];
                        }
                    }
                    return;
                }
            };
            executor.execute(runnable);
        }
        executor.shutdown();
    }

    static void multiply(double[][] a, double[][] b, double[][] c, int size) {
        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                c[i][j] = 0.0;
                for (int k = 0; k<size;k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

    static void populateMatrix(double[][] arr, int n) {
        Random random = new Random();
        for (int i=0;i<n;i++) {
            for (int j=0;j<n;j++) {
                arr[i][j] = Math.round(random.nextDouble() * 1000)/100.0;
            }
        }
    }

    static void printMatrix(double[][] a, int size) {
        for (int i = 0; i < size; i++) {
            for (int j=0;j<size;j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void testLinear(double[][] a, double[][] b, int size) {
        double[][] c = new double[size][size];
        Instant start = Instant.now();
        multiply(a,b,c,size);
        Instant finish = Instant.now();
        System.out.println(finish.toEpochMilli() - start.toEpochMilli());
    }

    public static void main(String[] args) throws InterruptedException {
//        int size = 3;
//        double[][] a = {{14.0, 9.0, 3.0},
//                {2.0, 11.0, 15.0},
//                {0.0, 12.0, 17.0}};
//        double[][] b = {{12.0, 25.0, 5.0},
//                {9.0, 10.0, 0.0},
//                {8.0, 5.0, 1.0}};
//        double[][] c = new double[size][size];
//        multiplyParallel(a, b, c, size);
//        printMatrix(c, size);
        int size = 1000;
        double[][] a = new double[size][size];
        double[][] b = new double[size][size];
        double[][] c = new double[size][size];
        double[][] d = new double[size][size];
        populateMatrix(a, size);
        populateMatrix(b, size);
        Instant start = Instant.now();
        multiply(a,b,c,size);
        Instant finish = Instant.now();
        System.out.println("Linear execution in ms: " + (finish.toEpochMilli() - start.toEpochMilli()));
        start = Instant.now();
        multiplyParallel(a,b,d,size);
        finish = Instant.now();
        System.out.println("Parallel execution in ms: " + (finish.toEpochMilli() - start.toEpochMilli()));
    }
}
