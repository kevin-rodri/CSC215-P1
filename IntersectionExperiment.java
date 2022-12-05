/******
* IntersectionExperiment
* Author: Christian Duncan
*  Modified by: Kevin Rodriguez, // TO-DO: (Add your names)
* Tests out the efficiency of the Set Intersection methods
******/
import java.util.ArrayList;
import java.util.Collections;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class IntersectionExperiment {
  static final int MIN_SIZE = 100;
  static final int MAX_SIZE = 100_000;
  static final int NUM_REPS = 100;
  static final double TTL = 1e8;   // Threshold where it "Took Too Long"
  static final String methodName[] = { "Brute Force", "Sorted", "Hash Set", "Bucket Sort" };
  static final String resultsName = "results.csv";

  public static void main(String[] args) {
    IntersectionExperiment singleton = new IntersectionExperiment();
    try {
      singleton.run();
    } catch (IOException e) {
      System.err.println("Error: Trying to run experiment.");
      System.err.println(e.getMessage());
    }
  }
 

  public void run() throws IOException {
    int size, method, reps;

    PrintWriter out = new PrintWriter(new FileWriter(resultsName));  // Store results in this file

    // Print header information for CSV file
    out.print("Size, ");
    for (size = MIN_SIZE; size <= MAX_SIZE; size *= 2)
    out.print(size + ",");
    out.println();

    // Try the various methods
    for (method = 0; method < methodName.length; method++) {
      System.out.println("Testing Method " + methodName[method]);
      out.print("Method " + methodName[method] + ", ");
      for (size = MIN_SIZE; size <= MAX_SIZE; size *= 2) {
        long totalTime = 0;
        for (reps = 0; reps < NUM_REPS; reps++) {
          // Create the two sets to intersect using the range -size to size
          Set sA = generateSet(size, -size, size);
          Set sB = generateSet(size, -size, size);

          // Start the clock, compute intersection, and stop the clock
          long startTime = System.nanoTime();
          runTest(sA, sB, method);  // This is the actual test we want to measure
          long stopTime = System.nanoTime();
          totalTime += (stopTime - startTime);
        }
        // Report the average time
        double averageTime = (double) totalTime/(double) NUM_REPS;
        out.print(averageTime + ",");
        if (averageTime > TTL) {
          System.out.println("  Terminating early.  Took too long at size: " + size);
          break;  // Abort at this point!
        }
      }
      out.println();
    }
    out.close();  // Close the file so it is saved!
  }

  /****
  * Run one instance of the intersection test.
  *   Compute the set intersection based on the method selected.
  ****/
  public void runTest(Set sA, Set sB, int method) {
    Set sAB = null;   // This is actually ignored anyway.
    switch (method) {
      case 0: sAB = Set.intersectionA(sA, sB); break;
      case 1: sAB = Set.intersectionB(sA, sB); break;
      case 2: sAB = Set.intersectionC(sA, sB); break;
      case 3: sAB = Set.intersectionD(sA, sB); break;
      default: System.err.println("Unrecognized method!");
    }
  }

  /****
  * Create a random set of size N of values in range min to max
  ****/
  public Set generateSet(int n, int min, int max) {
    int range = max - min;
    if (range < 1 || range > 1_000_000) {
      System.err.println("WARNING: Min/Max are out of range.  Aborting.");
      System.exit(1);
    }

    // Add all possible values from min to max
    ArrayList<Integer> a = new ArrayList<Integer>(n);
    for (int i = min; i <= max; i++) a.add(i);

    // Randomly shuffle them and grab the first n to add to the resulting Set.
    Collections.shuffle(a);
    Set result = new Set();
    for (int i = 0; i < n; i++) result.add(a.get(i));
    return result;
  }
}
