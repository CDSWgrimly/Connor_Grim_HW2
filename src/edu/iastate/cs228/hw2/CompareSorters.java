package edu.iastate.cs228.hw2;

/**
 *  
 * @author Connor Grim
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{	boolean continueTest = true;
		int trial = 1;
		System.out.println("Performances of Four Sorting Algorithms in Scanning Points\n");
		Scanner scnr = new Scanner(System.in);
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		while (continueTest) {
			Point[] points;
			PointScanner[] scanners = new PointScanner[4];
			int option;
			System.out.print("Trial: " + trial + "\nPoint Generation Keys: 1 - Random   2 - File Input   3 - Exit\nOption: ");
			option = scnr.nextInt();
			
			switch(option) {
			case 1:
				//Generates random points
				Random rand = new Random();
				int size;
				System.out.print("Enter number of random points: ");
				size = scnr.nextInt();
				points = generateRandomPoints(size, rand);
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);
				break;
			case 2:
				//Generates points from file
				System.out.print("Enter file name: ");
				String inputFileName = scnr.next();
				scanners[0] = new PointScanner(inputFileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(inputFileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(inputFileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(inputFileName, Algorithm.QuickSort);
				break;
			case 3:
				continueTest = false;
				break;
			default:
				break;
			}
			
			//Checks for exit
			if(!continueTest) {
				continue;
			}
			
			System.out.println("algorithm \t size \t time (ns)\n----------------------------------");
			
			//Perform Scans
			for (int i = 0; i < 4; i++) {
				scanners[i].scan();
				//scanners[i].writeMCPToFile();
				System.out.println(scanners[i].stats());
			}
			
			System.out.println("----------------------------------");
			
			trial++;
		}
		
		scnr.close();
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		// A sample scenario is given in Section 2 of the project description. 
		
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		Point pts[] = new Point[numPts];
		
		//Adds random points to pts[] within ([-50, 50], [-50, 50])
		for (int i = 0; i < numPts; i++) {
			pts[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}
		
		return pts;
	}
	
}
