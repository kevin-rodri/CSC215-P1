/******
* SetTester
* Author: Christian Duncan
*
* A simple class to test the correctness of the Set Intersection methods
******/
import java.io.FileNotFoundException;

public class SetTester {
  private static final String prefixName = "TestCases/test_"; // You might need to change the default prefix!
  public static void main(String[] args) throws FileNotFoundException {
    int errors = 0;
    for (int method = 1; method <= 4; method++) {
      for (int test = 1; test <= 3; test++) {
        String fileNameA = prefixName + "A" + test + ".txt";
        String fileNameB = prefixName + "B" + test + ".txt";
        String fileNameC = prefixName + "C" + test + ".txt";
        if (!testIntersection(fileNameA, fileNameB, fileNameC, method)) {
          errors++;
          System.out.println("Error with testing intersection of " + fileNameA + " and " + fileNameB + " using method " + method + ".");
        }
      }
    }
    System.out.println("Errors found: " + errors);
  }

  // Test the specified intersection method
  //    FileA: Name of file storing Set A
  //    FileB: Name of file storing Set B
  //    FileC: The resulting intersection set (to compare with intersection)
  //    method: Which method to use (A=1, B=2, C=3, D=4)
  public static boolean testIntersection(String fileA, String fileB, String fileC, int method) throws FileNotFoundException {
    Set sA = Set.readSet(fileA);
    Set sB = Set.readSet(fileB);
    Set sC = Set.readSet(fileC);

    Set sAB = null;
    switch (method) {
      case 1: sAB = Set.intersectionA(sA, sB); break;
      case 2: sAB = Set.intersectionB(sA, sB); break;
      case 3: sAB = Set.intersectionC(sA, sB); break;
      case 4: sAB = Set.intersectionD(sA, sB); break;
    }

    // Now verify correctness
    if (equal(sAB, sC)) return true;
    else {
      // Uncomment if you want to see the set computed!
      System.out.println("Intersections did not match.");
      System.out.println("Expected result: ");
      sC.printSet();
      System.out.println("Computed:");
      sAB.printSet();
      return false;
    }
  }

  // Compare if two sets are equal - using the brute-force intersection technique
  // NOTE: This assumes both sets are in fact sets (no duplicate elements in each)
  // AND it assumes the intersectionA method works correctly!
  public static boolean equal(Set sA, Set sB) {
    if (sA == null || sB == null || sA.size() != sB.size()) return false;
    Set sC = Set.intersectionA(sA,sB);
    return (sC.size() == sA.size());
  }
}
