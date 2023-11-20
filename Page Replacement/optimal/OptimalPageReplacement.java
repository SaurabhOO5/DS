import java.util.ArrayList;
import java.util.Scanner;

public class OptimalPageReplacement {

    public static boolean search(int key, ArrayList<Integer> fr) {
        for (int it : fr) {
            if (it == key) return true;
        }
        return false;
    }

    public static int predict(int[] pg, ArrayList<Integer> fr, int pn, int index) {
        int res = -1;
        int farthest = index;
        for (int i = 0; i < fr.size(); i++) {
            int j;
            for (j = index; j < pn; j++) {
                if (fr.get(i) == pg[j]) {
                    if (j > farthest) {
                        farthest = j;
                        res = i;
                    }
                    break;
                }
            }
            if (j == pn) {
                return i;
            }
        }
        return (res == -1) ? 0 : res;
    }

    public static void optimalPage(int[] pg, int pn, int fn) {
        ArrayList<Integer> fr = new ArrayList<>();
        int hit = 0;
        int pageIndex = 0;
        
        for (int i = 0; i < fn; i++) {
            fr.add(pg[i]);
            System.out.println("Page " + pg[i] + " added to frame. Frame Contents: " + fr);
            pageIndex++;
        }

        for (int i = pageIndex; i < pn; i++) {
            if (search(pg[i], fr)) {
                hit++;
                System.out.println("Page " + pg[i] + " is in the frame. Frame Contents: " + fr);
            } else {
                int j = predict(pg, fr, pn, i + 1);
                fr.set(j, pg[i]);
                System.out.println("Page " + pg[i] + " is not in the frame. Frame Contents: " + fr);
            }
        }
        
        System.out.println("No. of hits = " + hit);
        System.out.println("No. of misses = " + (pn - hit));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the value of pages: ");
        int pn = scanner.nextInt();
        int[] pg = new int[pn];

        System.out.println("Enter the page numbers:");
        for (int i = 0; i < pn; i++) {
            pg[i] = scanner.nextInt();
        }

        System.out.print("Enter the number of frames: ");
        int fn = scanner.nextInt();

        optimalPage(pg, pn, fn);
    }
}
