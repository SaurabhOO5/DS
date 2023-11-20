import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;
import java.util.LinkedList;

public class LRU {

    public static int pageFaults(int[] pages, int n, int capacity) {
        HashSet<Integer> s = new HashSet<>();
        HashMap<Integer, Integer> indexes = new HashMap<>();
        LinkedList<Integer> frame = new LinkedList<>();

        int pageFaults = 0;

        for (int i = 0; i < n; i++) {
            if (s.size() < capacity) {
                if (!s.contains(pages[i])) {
                    s.add(pages[i]);
                    frame.add(pages[i]);
                    pageFaults++;
                }
                indexes.put(pages[i], i);
            } else {
                if (!s.contains(pages[i])) {
                    int lru = Integer.MAX_VALUE;
                    int val = 0;

                    for (Integer it : s) {
                        if (indexes.get(it) < lru) {
                            lru = indexes.get(it);
                            val = it;
                        }
                    }

                    s.remove(val);
                    frame.removeFirst(); // Remove the least recently used page from the frame
                    s.add(pages[i]);
                    frame.add(pages[i]); // Add the current page to the frame
                    pageFaults++;
                }
                indexes.put(pages[i], i);
            }

            // Display frame contents after each iteration
            System.out.print("Frame Contents: ");
            for (Integer pageNum : frame) {
                System.out.print(pageNum + " ");
            }
            System.out.println();
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the value of n: ");
        int n = scanner.nextInt();
        int[] pages = new int[n];

        System.out.println("Enter the page numbers:");
        for (int i = 0; i < n; i++) {
            pages[i] = scanner.nextInt();
        }

        System.out.print("Enter the number of frames: ");
        int capacity = scanner.nextInt();

        int pageFaultCount = pageFaults(pages, n, capacity);
        System.out.println("No. of page faults: " + pageFaultCount);
    }
}
