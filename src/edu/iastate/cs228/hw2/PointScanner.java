package edu.iastate.cs228.hw2;

/**
 * 
 * @author Connor Grim
 *
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	private String outputFileName;
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		else {
			sortingAlgorithm = algo;
			points = new Point[pts.length];
			for (int i = 0; i < pts.length; i++) {
				points[i] = pts[i];
			}
		}
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			outputFileName = "selection.txt";
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			outputFileName = "insertion.txt";
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			outputFileName = "merge.txt";
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			outputFileName = "quick.txt";
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File inputFile = new File(inputFileName);
		int count = 0;
		Scanner pointScan = new Scanner(inputFile);
		
		sortingAlgorithm = algo;
		//Checks for no input file
		if (inputFileName == null) {
			throw new FileNotFoundException();
		}
		
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			outputFileName = "selection.txt";
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			outputFileName = "insertion.txt";
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			outputFileName = "merge.txt";
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			outputFileName = "quick.txt";
		}
		
		//Checks number of values and throws InputMismatchException if odd
		while (pointScan.hasNextInt()) {
			pointScan.nextInt();
			count++;
		}
		
		if (count % 2 == 1) {
			throw new InputMismatchException();
		}
		
		
		//Creates array of points
		int size = count / 2;
		Scanner scnr = new Scanner(inputFile);
		points = new Point[size];
		
		for (int i = 0; i < size; i++) {
			int x = scnr.nextInt();
			int y = scnr.nextInt();
			points[i] = new Point(x, y);
		}
		scnr.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		long startX, startY, endX, endY;
		
		
		// Sets Algorithm to perform 
		AbstractSorter aSorter = null;
		
		//First Round: Sort X
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		} 
		
		aSorter.setComparator(0);
		startX = System.nanoTime();
		aSorter.sort();
		endX = System.nanoTime();
		
		//Second Round: Sort Y
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		} 
		
		aSorter.setComparator(1);
		startY = System.nanoTime();
		aSorter.sort();
		endY = System.nanoTime();
		
		//Create median point & set total sort time
		medianCoordinatePoint = new Point(aSorter.getMedian());
		scanTime = (endX - startX) + (endY - startY);		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String sortStat = "";
		int whitespaceLength = 0;
		
		//Adds name of sorter and aligns using whitespace
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			sortStat += "SelectionSort";
			whitespaceLength = 5;
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			sortStat += "InsertionSort";
			whitespaceLength = 5;
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			sortStat += "MergeSort";
			whitespaceLength = 9;
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			sortStat += "QuickSort";
			whitespaceLength = 9;
		}
		
		for (int i = 0; i <= whitespaceLength; i++) {
			sortStat += " ";
		}
		//Add size and time
		sortStat += points.length + "  " + scanTime;
		return sortStat;
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() +")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File outputFile = new File(outputFileName);
		PrintWriter pw = new PrintWriter(outputFile);
		pw.write(this.toString());
		pw.close();
	}	

	

		
}
