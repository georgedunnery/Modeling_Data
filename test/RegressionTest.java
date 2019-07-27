import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for the Regression class. Verifies that the calculations hosted by the Regression
 * test are accurate.
 */
public class RegressionTest {

  /**
   * Verify that the average x in a list of Point2D.Double objects is calculated correctly.
   */
  @Test
  public void testAverageX() {
    // Zero
    DataModel zero = new DataModel();
    zero.add(new Point2D.Double(0, 0));
    assertEquals(0, Regression.averageX(zero.getData()), 0.01);
    // Single Point
    DataModel averageX = new DataModel();
    averageX.add(new Point2D.Double(3, 5));
    assertEquals(3, Regression.averageX(averageX.getData()), 0.01);
    // Two points
    averageX.add(new Point2D.Double(9, 5));
    assertEquals(6, Regression.averageX(averageX.getData()), 0.01);
    // Three points
    averageX.add(new Point2D.Double(3, 5));
    assertEquals(5, Regression.averageX(averageX.getData()), 0.01);
    // Four points, including negative
    averageX.add(new Point2D.Double(-3, 5));
    assertEquals(3, Regression.averageX(averageX.getData()), 0.01);
  }

  /**
   * Verify that the average y in a list of Point2D.Double objects is calculated correctly.
   */
  @Test
  public void testAverageY() {
    // Zero
    DataModel zero = new DataModel();
    zero.add(new Point2D.Double(0, 0));
    assertEquals(0, Regression.averageY(zero.getData()), 0.01);
    // Single
    DataModel averageY = new DataModel();
    averageY.add(new Point2D.Double(3, 5));
    assertEquals(5, Regression.averageY(averageY.getData()), 0.01);
    // Two points, including negative
    averageY.add(new Point2D.Double(3, -9));
    assertEquals(-2, Regression.averageY(averageY.getData()), 0.01);
    // Three points
    averageY.add(new Point2D.Double(3, 22));
    assertEquals(6, Regression.averageY(averageY.getData()), 0.01);
    // Four points
    averageY.add(new Point2D.Double(3, 10));
    assertEquals(7, Regression.averageY(averageY.getData()), 0.01);
  }

  /**
   * Verify that the sum of squares XX is calculated correctly.
   */
  @Test
  public void testSumSquaresXX() {
    DataModel sXX = new DataModel();
    sXX.add(new Point2D.Double(3, 0));
    sXX.add(new Point2D.Double(6, 0));
    sXX.add(new Point2D.Double(9, 0));
    sXX.add(new Point2D.Double(12, 0));
    sXX.add(new Point2D.Double(15, 0));
    sXX.add(new Point2D.Double(18, 0));
    sXX.add(new Point2D.Double(21, 0));
    sXX.add(new Point2D.Double(24, 0));
    sXX.add(new Point2D.Double(27, 0));
    // Prepare for the assert statement
    List<Point2D.Double> list = sXX.getData();
    double avgX = Regression.averageX(list);
    double avgY = Regression.averageY(list);
    // Check the 0th index of the array
    assertEquals(540, Regression.sumSquares(list, avgX, avgY)[0], 0.01);
  }

  /**
   * Verify that the sum of squares YY is calculated correctly.
   */
  @Test
  public void testSumSquaresYY() {
    DataModel sYY = new DataModel();
    sYY.add(new Point2D.Double(0, 2));
    sYY.add(new Point2D.Double(0, 4));
    sYY.add(new Point2D.Double(0, 6));
    sYY.add(new Point2D.Double(0, 8));
    sYY.add(new Point2D.Double(0, 10));
    sYY.add(new Point2D.Double(0, 12));
    sYY.add(new Point2D.Double(0, 14));
    sYY.add(new Point2D.Double(0, 16));
    // Prepare for the assert statement
    List<Point2D.Double> list = sYY.getData();
    double avgX = Regression.averageX(list);
    double avgY = Regression.averageY(list);
    // Check the 1st index of the array
    assertEquals(168, Regression.sumSquares(list, avgX, avgY)[1], 0.01);
  }

  /**
   * Verify that the sum of squares XY is calculated correctly.
   */
  @Test
  public void testSumSquaresXY() {
    DataModel sXY = new DataModel();
    sXY.add(new Point2D.Double(2, 4));
    sXY.add(new Point2D.Double(3, 5));
    sXY.add(new Point2D.Double(5, 7));
    sXY.add(new Point2D.Double(9, 15));
    sXY.add(new Point2D.Double(11, 12));
    // Prepare for the assert statement
    List<Point2D.Double> list = sXY.getData();
    double avgX = Regression.averageX(list);
    double avgY = Regression.averageY(list);
    // Check the 2nd index in the array (last)
    assertEquals(67, Regression.sumSquares(list, avgX, avgY)[2], 0.01);
  }

  /**
   * Verify that the distance is calculated correctly.
   */
  @Test
  public void testDistance() {
    // Simple numbers
    double dist = Regression.distance(6, 3, 5);
    assertEquals(3.3333333333333, dist, 0.000001);
    // Allow infinity to be a valid case, as noted on Piazza @449
    dist = Regression.distance(3, 3, 5);
    assertEquals(Double.POSITIVE_INFINITY, dist, 0.1);
    dist = Regression.distance(3, 3, -5);
    assertEquals(Double.NEGATIVE_INFINITY, dist, 0.1);
  }
}
