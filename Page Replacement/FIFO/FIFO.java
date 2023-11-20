import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FIFO {

    public static void fifoPage(int[] pg, int pn, int fn) {
        ArrayList<Integer> fr = new ArrayList<>();
        Queue<Integer> fifoQueue = new LinkedList<>();
        int pageFaults = 0;

        for (int i = 0; i < pn; i++) {
            int currentPage = pg[i];

            if (!fr.contains(currentPage)) {
                pageFaults++;

                if (fr.size() < fn) {
                    fr.add(currentPage);
                    fifoQueue.add(currentPage);
                } else {
                    int replacedPage = fifoQueue.poll();
                    fr.remove(fr.indexOf(replacedPage));
                    fr.add(currentPage);
                    fifoQueue.add(currentPage);
                }
            }

            System.out.println("Page " + currentPage + " is in the frame. Frame Contents: " + fr);
        }

        //System.out.println("No. of page faults = " + pageFaults);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of pages: ");
        int pn = scanner.nextInt();
        int[] pg = new int[pn];

        System.out.println("Enter the page numbers:");
        for (int i = 0; i < pn; i++) {
            pg[i] = scanner.nextInt();
        }

        System.out.print("Enter the frame size: ");
        int fn = scanner.nextInt();

        fifoPage(pg, pn, fn);
    }
}
