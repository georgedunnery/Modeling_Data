import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A class to store and operate upon the data. This class has a list of data points, and offers the
 * data modeling methods fitLine and kmeans.
 */
public class DataModel {

  /**
   * A list of data points.
   */
  private List<Point2D.Double> data;

  /**
   * Constructs an initial DataModel object. It does not yet contain any data points.
   */
  public DataModel() {
    // Use a linked list for storage: fast appending, no need to resize
    this.data = new LinkedList<>();
  }

  /**
   * Method to add a new point to the DataModel.
   *
   * @param point Point2D.Double, the point to add to the data model.
   */
  public void add(Point2D.Double point) {
    this.data.add(point);
  }

  /**
   * Method to get a list of all the Point2D objects in the DataModel's list so far.
   *
   * @return ArrayList of Point2D objects, an array of all the data points in the DataModel so far.
   */
  public List<Point2D.Double> getData() {
    // Return an array list: use the .get(int index) method for constant time access to indices
    return new ArrayList<>(this.data);
  }

  /**
   * Method to perform a Linear Regression algorithm on the set of data.
   *
   * @return Line, the standard form line representing the line of best fit for the given data.
   * @throws IllegalStateException When the DataModel has less than 2 data points.
   */
  public Line fitLine() throws IllegalStateException {
    // The helper function will coordinate the necessary calculations and return the Line object
    return calculateLine(this.getData());
  }

  /**
   * Helper method to the fitLine() method. This method coordinates the calculation of the necessary
   * variables in the linear regression, and returns an array of three doubles that correspond to
   * the three constants needed to construct a Line: a,b,c.
   *
   * @param points ArrayList of Point2D.Double, the data from the DataModel.
   * @return Double[], three double that correspond to the constants in standard form equation.
   * @throws IllegalStateException When the DataModel has less than 2 data points.
   */
  private Line calculateLine(List<Point2D.Double> points) throws IllegalStateException {
    // A line of best fit cannot be calculated for a DataModel with less than 2 data points
    if (points.size() < 2) {
      throw new IllegalStateException("Operation requires the DataModel to have at least two" +
              "data points.");
    }
    // Step 1: Compute following sums for all points using Regression class static methods
    // Find the average x and average y; Find the sum of squares for yy, xx, xy
    double avgX = Regression.averageX(points);
    double avgY = Regression.averageY(points);
    // The sumSquares method returns: double array = {sumXX, sumYY, sumXY}
    double[] sumSquares = Regression.sumSquares(points, avgX, avgY);
    double sumXX = sumSquares[0];
    double sumYY = sumSquares[1];
    double sumXY = sumSquares[2];
    // Step 2: Compute distance using the provided formula
    // Piazza @449: d = infinity is a valid case, no need to check for division by zero
    double distance = Regression.distance(sumXX, sumYY, sumXY);
    // Step 3: Compute theta using the provided formula
    double theta = Math.toDegrees(Math.atan(distance));
    // Step 4: Compute positive result for f(t): t = theta or t = theta + 180
    theta = Regression.formula(theta, sumXX, sumYY, sumXY);
    // Step 5: Compute the a,b,c constants required to define a line in the standard form equation
    double lineConstantA = Math.cos(Math.toRadians(theta / 2));
    double lineConstantB = Math.sin(Math.toRadians(theta / 2));
    double lineConstantC = (-1 * lineConstantA * avgX) - (lineConstantB * avgY);
    return new Line(lineConstantA, lineConstantB, lineConstantC);
  }


  /**
   * Runs 10 k-mean clustering algorithms that move the data points until they align with randomized
   * k clusters.
   *
   * @param k integer for number of clusters we want
   * @return list of integers denoting clusters corresponding to the ith point in the dataset
   * @throws IllegalArgumentException if k is not a positive integer or within data size
   */
  public List kmeans(int k) throws IllegalArgumentException {
    if (k <= 0) {
      throw new IllegalArgumentException("k must be positive");
    }
    // Initialize lists for 10 iterations of RANSAC and their corresponding errors
    List<List> ransacList = new ArrayList<>();
    List<Double> errorList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      // Choose k points from data set at random to be centers of clusters
      List<Point2D.Double> data = this.getData();
      if (k > data.size()) {
        throw new IllegalArgumentException("k cannot exceed data size");
      }
      List<Cluster> clusters = Kmeans.getClusters(data, k);
      // Set initial error as infinity
      double error = Double.POSITIVE_INFINITY;
      double newError;
      double percentageError;
      while (true) {
        Kmeans.resetClusterDataPointSizes(clusters);
        // Assign each point to a cluster by finding its nearest (min distance) cluster
        for (Point2D.Double point : data) {
          Kmeans.categorizePoint(point, clusters);
        }
        // For each cluster, find average of its data points' x and y to compute center
        Kmeans.computeClusterCenters(clusters);
        // Compute new error:
        //    Find distance between each point and the center of its cluster
        //    Average these distances to get the new error
        newError = Kmeans.computeNewError(data, clusters);
        // Calculate percentage error
        if (error != Double.POSITIVE_INFINITY) {
          percentageError = Math.abs(newError - error) / error;
          if (percentageError < 0.01) {
            break;
          }
        }
        error = newError;
      }
      // Add this iteration's clusters and error to RANSAC lists
      List ransacClusterIntList = Kmeans.reportClusters(data, clusters);
      ransacList.add(ransacClusterIntList);
      errorList.add(newError);
    }
    // After 10 iterations, return clusters list with minimum error
    Object minimumError = Collections.min(errorList);
    List bestIteration = ransacList.get(errorList.indexOf(minimumError));
    return bestIteration;
  }
}