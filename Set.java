/******
* Set
* Author: Christian Duncan
* Modified by: Kevin Rodriguez  // TO-DO: (Add your names)
* A simple class representing a Set of integers.
* This is not fully implemented as we are just measuring
* the time for computing intersections.
******/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class Set {
  private ArrayList<Integer> elements;

  public Set() {
    elements = new ArrayList<Integer>();
  }

  public void add(int element) {
    elements.add(element);
  }

  public int size() { return elements.size(); }

  // Print out the set
  public void printSet() {
    for (Integer a: elements) System.out.println(a);
  }

  // Read in a set from a given file
  public static Set readSet(String fileName) throws FileNotFoundException {
    Set result = new Set();
    Scanner in = new Scanner(new File(fileName));
    while (in.hasNextInt()) {
      int element = in.nextInt();
      result.add(element);
    }
    in.close();
    return result;
  }

  // Compute the intersection of two sets
  //    Using the brute force approach
  public static Set intersectionA(Set sA, Set sB) {
    Set result = new Set();
    for (int a: sA.elements) {
      for (int b: sB.elements) {
        if (a == b) result.add(a);
      }
    }
    return result;
  }

  // Compute the intersection of two sets
  //    Using a presorting (merge sort) approach first
  public static Set intersectionB(Set sA, Set sB) {
    Set result = new Set();

    // First sort the elements (using the java.Collections method)
    Collections.sort(sA.elements);
    Collections.sort(sB.elements);

    // Now just do a merge of both
    int a = 0;
    int b = 0;

    // As long as there are elements in both sets left
    while (a < sA.elements.size() && b < sB.elements.size()) {
      // Get the current element from each set (array access)
      int eA = sA.elements.get(a);
      int eB = sB.elements.get(b);
      if (eA == eB) {
        result.add(eA);  // Match - add element to result
        a++; b++;
      } else if (eA < eB) a++;  // Advance set A
      else b++;  // Advance set B
    }

    return result;
  }

  // Compute the intersection of two sets
  //    Using a hash-table approach
  public static Set intersectionC(Set sA, Set sB) {
    Set result = new Set();
    HashSet<Integer> hset = new HashSet<Integer>();

    // Add the first set (A) to the HashSet
    for (int a: sA.elements) hset.add(a);

    // See which elements from second set (B) are in HashSet
    for (int b: sB.elements) if (hset.contains(b)) result.add(b);

    return result;
  }

  // Compute the intersection of two sets
  //    Using a variation of bucket sort
  public static Set intersectionD(Set sA, Set sB) {
    Set result = new Set();

// create a bucket of the size of elements in the set
int n = sA.elements.size();
boolean[] bucket = new boolean[2 * n + 1];

// ensure setting the buckets to true 
for (int a: sA.elements){
  bucket[a + n] = true;
} 

// if it's in set b, then ensure to add it to the set. 
for (int b: sB.elements) {
  if (bucket[b + n]){
    result.add(b);
  }
}
// return the final set 
    return result;
  }
}
