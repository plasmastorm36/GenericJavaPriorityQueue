/*
 * Author: Noah Rouse
 * Email: plasmastormfire@gmail.com
 * Description:
 */
import java.util.ArrayList;
public class PriorityQueue<K extends Comparable<K>, V> {
   private class Entry {
      final K k;
      final V v;
      private Entry (final K key, final V value) {
         k = key;
         v = value;
      }
   }

   private final ArrayList<Entry> heap = new ArrayList<Entry>();
   private int size = 0;

   private int left (final int p) {
      /*
       * computes the position of the left child in a heap
       * Parameters:
       * p: position given in the heap
       */
      return 2 * p + 1;
   }

   private int right (final int p) {
      /*
       * computes the position of the right child in the heap
       * Parameters:
       * p: an int containing the position of an item in the heaps
       */
      return 2 * p + 2;
   }

   private int parent (final int p) {
      /*
       * computes the parent index given a child index
       * Parameters:
       * None
       */
      return (p - 1) / 2;
   }

   public Entry insert (final K key, final V value) {
      /*
       * inserts an entry to the end of the ArrayList, and then preforms upheap
       * to properly place the entry
       * Parameters:
       * key: the value being compared for priority in the queue
       * value: the value associated with the key
       */
      // base case with an empty priority queue
      if (size == 0) {
         final Entry entry = new Entry(key, value);
         heap.add(entry);
         size++;
         return entry;
      }

      // inserts the data
      final Entry entry = new Entry(key, value);
      heap.add(entry);
      size++;

      // upheap operation
      int i = size - 1;
      while (i > 0 && heap.get(i).k.compareTo(heap.get(parent(i)).k) < 0) {
         final Entry temp = heap.get(parent(i));
         heap.set(parent(i), entry);
         heap.set(i, temp);
         i = parent(i);
      }

      return entry;
   }

   public Entry min () {
      /*
       * returns (but does not remove) the min value in the priority queue
       * Parameters:
       * None
       */
      if (size == 0) {
         return null;
      }
      return heap.get(0);
   }

   public Entry removeMin () {
      /*
       * returns and remove the min
       * Parameters:
       * None
       */
      if (size == 0) {
         return null;
      }

      size--;
      final Entry prevMin = heap.get(0);
      // sets the min index to 
      heap.set(0, heap.get(heap.size() - 1));
      heap.remove(heap.size() - 1);

      // downheap
      int i = 0;
      while (i < heap.size()) {
         final int leftIndex = left(i);
         final int rightIndex = right(i);
         final K leftKey = heap.get(left(i)).k;
         final K rightKey = heap.get(right(i)).k;
         final K key = heap.get(i).k;

         if (rightIndex >= heap.size()) {
            // case right child does not exist
            if (leftIndex >= heap.size()) {
               // left child also does not exist, indicates end of downheap
               return prevMin;
            }

            if (key.compareTo(leftKey) >= 0) {
               // left child is supposed to come before or is equivalent to key
               final Entry temp = heap.get(i);
               heap.set(i, heap.get(leftIndex));
               heap.set(leftIndex, temp);
               i = leftIndex;
            }
         } else {
            // case that both left and right child exists
            if (leftKey.compareTo(rightKey) <= 0) {
               // case left is min child or equal to right
               if (leftKey.compareTo(key) <= 0) {
                  // case leftKey should come before the key
                  final Entry temp = heap.get(i);
                  heap.set(i, heap.get(leftIndex));
                  heap.set(leftIndex, temp);
               }
            } else {
               // case right key comes before left key
               if (rightKey.compareTo(key) <= 0) {
                  // case right key comes before or is equal to key in ordering
                  final Entry temp = heap.get(i);
                  heap.set(i, heap.get(rightIndex));
                  heap.set(rightIndex, temp);
               }
            }
         }
      }
      return prevMin;
   }

   public int size () {
      /*
       * returns the size of the priority queue
       * Parameters:
       * None
       */
      return size;
   }

   public boolean isEmpty () {
      /*
       * returns true if the priority queue is empty
       * Parameters:
       * None
       */
      return size == 0;
   }
}
