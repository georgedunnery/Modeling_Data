import java.awt.geom.Point2D;
import java.util.List;

/**
 * A class to host calculations for the Linear Regression algorithm. Methods are made static so they
 * may be used like the Math class in Java.
 */
public final class Regression {

  /**
   * Method to calculate the average value of x.
   *
   * @param points ArrayList of Point2D.Double, the data from the DataModel.
   * @return Double, the average value of x.
   */
  protected static double averageX(List<Point2D.Double> points) {
    // Find the sum of all Point2D x values and divide by size to find the average
    return (points.stream().mapToDouble(p -> p.getX()).sum()) / points.size();
  }

  /**
   * Method to calculate the average value of y.
   *
   * @param points ArrayList of Point2D.Double, the data from the DataModel.
   * @return Double, the average value of y.
   */
  protected static double averageY(List<Point2D.Double> points) {
    // Find the sum of all Point2D y values and divide by size to find the average
    return (points.stream().mapToDouble(p -> p.getY()).sum()) / points.size();
  }

  /**
   * Method to get the sum of squares for 'XX', 'YY', and 'XY'. An array of three doubles is
   * returned. The indices are as follows: [0] = sumXX, [1] = sumYY, and [2] = sumXY. This method
   * was developed to reduce sum squares process from O(n3) to O(n) by using a single iteration.
   *
   * @param points ArrayList of Point2D.Double, the data points in the DataModel.
   * @param avgX   Double, the average value of x.
   * @param avgY   Double, the average value of y.
   * @return Double Array, the three double values sum of squares XX, YY, and XY.
   */
  protected static double[] sumSquares(List<Point2D.Double> points, double avgX, double avgY) {
    // Iterate through the list once, updating the sums of squares each cycle
    double sumXX = 0;
    double sumYY = 0;
    double sumXY = 0;
    for (Point2D dot : points) {
      sumXX += (dot.getX() - avgX) * (dot.getX() - avgX);
      sumYY += (dot.getY() - avgY) * (dot.getY() - avgY);
      sumXY += ((dot.getX() - avgX) * (dot.getY() - avgY));
    }
    return new double[]{sumXX, sumYY, sumXY};
  }

  /**
   * Method to return the distance.
   *
   * @param sumXX Double, sum of squares XX.
   * @param sumYY Double, sum of squares YY.
   * @param sumXY Double, sum of squares XY.
   * @return Double, the distance.
   */
  protected static double distance(double sumXX, double sumYY, double sumXY) {
    return (2 * sumXY) / (sumXX - sumYY);
  }

  /**
   * Method to calculate the 't' in the given formula. The variable 't' is required to calculate the
   * constants 'a' and 'b' for the line of best fit.
   *
   * @param theta Double, theta for the DataModel.
   * @param sumXX Double, least sum of squares "XX".
   * @param sumYY Double, least sum of squares "YY".
   * @param sumXY Double, least sum of squares "XY".
   * @return Double, value of theta that yields a positive result of the calculation.
   */
  protected static double formula(double theta, double sumXX, double sumYY, double sumXY) {
    // Calculate the given formula after the required variables have been defined
    // If the first calculation is not positive, then add 180 to theta
    double result = (sumYY - sumXX) * Math.cos(Math.toRadians(theta)) - 2 * sumXY *
            Math.sin(Math.toRadians(theta));
    if (result <= 0) {
      theta += 180;
      result = (sumYY - sumXX) * Math.cos(Math.toRadians(theta)) - 2 * sumXY *
              Math.sin(Math.toRadians(theta));
    }
    return theta;
  }
}
