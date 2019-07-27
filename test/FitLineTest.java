import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A JUnit test for the Linear Regression algorithm. It is implemented by the fitLine method in the
 * DataModel class.
 */
public class FitLineTest {

  /**
   * Verify that a DataModel with no data points is blocked from the fitLine method with an
   * IllegalStateException.
   */
  @Test
  public void testDataPointsException() {
    // Zero data points should throw an exception
    DataModel empty = new DataModel();
    try {
      empty.fitLine();
      fail("Cannot produce a line of best fit without data points.");
    } catch (IllegalStateException e) {
      // Pass
    }
    // One data point should throw an exception
    DataModel single = new DataModel();
    single.add(new Point2D.Double(0, 0));
    try {
      single.fitLine();
      fail("Cannot produce a line of best fit from a single data point.");
    } catch (IllegalStateException e) {
      // Pass
    }
    // Two data points should not throw an exception, this is the minimum required to run fitLine()
    DataModel minimum = new DataModel();
    minimum.add(new Point2D.Double(-1, -1));
    minimum.add(new Point2D.Double(1, 1));
    try {
      minimum.fitLine();
      // Pass
    } catch (IllegalStateException e) {
      fail("A line of best fit can indeed by calculated from 2 data points.");
    }
  }

  /**
   * Check that the line of best fit is accurate when given data points that follow a perfect line.
   */
  @Test
  public void testPerfectLinePositiveSlope() {
    // Create a Line, and add DataModel points that exactly follow the line
    Line perfect = new Line(1, -1, 0);
    DataModel simple = new DataModel();
    simple.add(new Point2D.Double(1, 1));
    simple.add(new Point2D.Double(2, 2));
    simple.add(new Point2D.Double(3, 3));
    // The line of best fit should match the original line
    // The ax + by + c = 0 lines will have the same ratio (since sin and cos only return -1 to 1)
    // Confirm they produce the same results by checking the x and y intercepts
    Line actual = simple.fitLine();
    assertEquals(perfect.getX(0), actual.getX(0), 0.01);
    assertEquals(perfect.getY(0), actual.getY(0), 0.01);
  }

  /**
   * Check that the line of best fit is accurate when given data points that follow a perfect line.
   */
  @Test
  public void testPerfectLineNegativeSlope() {
    // Adding points that fall exactly upon the given line
    Line perfect = new Line(3, 6, -24);
    DataModel simple = new DataModel();
    simple.add(new Point2D.Double(-2, 5));
    simple.add(new Point2D.Double(10, -1));
    simple.add(new Point2D.Double(4, 2));
    // Verify the line of best fit matches by checking intercepts
    Line actual = simple.fitLine();
    assertEquals(perfect.getX(0), actual.getX(0), 0.01);
    assertEquals(perfect.getY(0), actual.getY(0), 0.01);
  }

  /**
   * Check that the line of best fit is accurate when given data points that follow a nearly perfect
   * line.
   */
  @Test
  public void testApproximateLinePositiveSlope() {
    Line approximate = new Line(5, -2.5, 15);
    DataModel complex = new DataModel();
    // These points follow the line:
    complex.add(new Point2D.Double(-4, -2));
    complex.add(new Point2D.Double(-3, 0));
    complex.add(new Point2D.Double(-2, 2));
    complex.add(new Point2D.Double(-1, 4));
    complex.add(new Point2D.Double(0, 6));
    complex.add(new Point2D.Double(1, 8));
    // These points deviate:
    complex.add(new Point2D.Double(-2.51, 1));
    complex.add(new Point2D.Double(-2, 2.03));
    complex.add(new Point2D.Double(0, 6.03));
    // Confirm that the best fit line matches the approximate line by checking the intercepts
    Line actual = complex.fitLine();
    assertEquals(approximate.getX(0), actual.getX(0), 0.01);
    assertEquals(approximate.getY(0), actual.getY(0), 0.01);
  }

  /**
   * Check that the line of best fit is accurate when given data points that follow a nearly perfect
   * line.
   */
  @Test
  public void testApproximateLineNegativeSlope() {
    // Add many points that follow the line, and a few that deviate from it (should be averaged out)
    Line approximate = new Line(2, 4, 8);
    DataModel complex = new DataModel();
    // These points follow the line:
    complex.add(new Point2D.Double(-4, 0));
    complex.add(new Point2D.Double(-3.5, -0.25));
    complex.add(new Point2D.Double(-3, -0.5));
    complex.add(new Point2D.Double(-2.5, -0.75));
    complex.add(new Point2D.Double(-2, -1));
    complex.add(new Point2D.Double(-1.5, -1.25));
    complex.add(new Point2D.Double(-1, -1.5));
    complex.add(new Point2D.Double(-0.5, -1.75));
    complex.add(new Point2D.Double(0, -2));
    // These points deviate:
    complex.add(new Point2D.Double(-2, -0.96));
    complex.add(new Point2D.Double(-1.5, -1.22));
    Line actual = complex.fitLine();
    // Confirm that the best fit line matches the approximate line by checking the intercepts
    assertEquals(approximate.getX(0), actual.getX(0), 0.01);
    assertEquals(approximate.getY(0), actual.getY(0), 0.01);
  }

  /**
   * Add points from two parallel lines to the DataModel, and confirm the line of best fit is
   * exactly between them. The lines used to generate the points are 2x - 2y + 4 = 0 and 2x - 2y + 2
   * = 0. The line of best fit should match the ratio of 2x - 2y + 3 = 0.
   */
  @Test
  public void testOpposingParallelLines() {
    DataModel parallel = new DataModel();
    // Add points from the upper line: 2x - 2y + 4 = 0
    parallel.add(new Point2D.Double(-2, 0));
    parallel.add(new Point2D.Double(-1.5, 0.5));
    parallel.add(new Point2D.Double(-1, 1));
    parallel.add(new Point2D.Double(-0.5, 1.5));
    parallel.add(new Point2D.Double(0, 2));
    // Add points from the lower line: 2x - 2y + 2 = 0
    parallel.add(new Point2D.Double(-1.5, -0.5));
    parallel.add(new Point2D.Double(-1, 0));
    parallel.add(new Point2D.Double(-0.5, 0.5));
    parallel.add(new Point2D.Double(0, 1));
    parallel.add(new Point2D.Double(0.5, 1.5));
    // Establish the expected line and the calculated line of best fit
    Line expected = new Line(2, -2, 3);
    Line actual = parallel.fitLine();
    // Compare their intercepts to verify the best fit has averaged the parallel lines
    assertEquals(expected.getX(0), actual.getX(0), 0.01);
    assertEquals(expected.getY(0), actual.getY(0), 0.01);
  }
}
