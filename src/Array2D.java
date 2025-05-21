/*
 * Learning array data structure
 * 
 * @author: Lely Hiryanto
 */

import java.util.Scanner;
import java.lang.Math;   

public class Array2D {
    //constants
    static final int MIN = 1;
    static final int MAX = 100;
    //variable - matrix
    private int[][] mat;

    //constructor
    Array2D() {
        //input the number of elements in an array
        Scanner scan = new Scanner(System.in);
        int n,m;
        do {
            System.out.print("Jumlah baris n (n >= 2) = ");
            n = scan.nextInt();
        } while(n < 2);
        do {
            System.out.print("Jumlah kolom m (n >= 2) = ");
            m = scan.nextInt();
        } while(m < 2);
        scan.close();

        //create object matrix mat
        mat = new int[n][m];
    }

    //generate random values for the element of matrix mat
    void randomValue() {
        //generate random numbers for the array
        for(int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j] = (int)(Math.random()*(MAX - MIN + 1));
            }
        }
    }

    //transpose matrix mat
    void transposeMatrix() {
        int n = mat.length;
        int m = mat[0].length;
        int[][] matT = new int[m][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matT[j][i] = mat[i][j];
            }
        }
        displayMatrix(matT);
    }

    void displayMatrix() {
        for(int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    //@overloading display matrix hasil transpose
    void displayMatrix(int[][] A) {
        for(int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Array2D M = new Array2D();
        M.randomValue(); M.displayMatrix();
        System.out.println("Transpose: ");
        M.transposeMatrix(); 
    }
}
