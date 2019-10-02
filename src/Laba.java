import java.util.*;

/**
 * @author Grisha
 * @version 1.0
 */


public class Laba {
    /**
     * @param array nothing
    */
    static void swap(int [] array,int i, int j) {
        int k = array[i];
        array[i] = array[j];
        array[j] = k;
    }

    /**
     *
     * @param args
     *  nothing
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Array size: ");
        int n = scanner.nextInt();
        int[] array = new int[n];
        for(int i = 0; i < n ; i++){
            array[i] = scanner.nextInt();
        }
        for(int i = 0; i < n ; i++){
            for(int j = 1; j < n - i; j++){
                if(array[j-1] < array[j]){
                    swap(array, j-1 ,j);
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
