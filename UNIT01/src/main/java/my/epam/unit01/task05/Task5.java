package my.epam.unit01.task05;

public class Task5 {

    public static int[][] getMatrix(int size){
        if(size <= 0) throw new IllegalArgumentException();

        int[][] result = new int[size][size];

        for (int i = 0; i < size; i++) {
            result[i][i] = 1;
            result[i][size-i-1] = 1;
        }

        return result;
    }

    public static void printMatrix(int[][] matrix){
        for (int[] row : matrix) {
            System.out.print("| ");
            for (int column : row) {
                System.out.print(column + " ");
            }
            System.out.println("|");
        }
    }
}
