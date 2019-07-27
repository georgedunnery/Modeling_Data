import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the individual operations of the Cluster class.
 */
public class ClusterTest {
  private Cluster c;

  @Before
  public void testSetUp() {
    c = new Cluster(0, new Point2D.Double());
  }

  // Tests that the toString function returns the cluster's center in string form
  @Test
  public void testToString() {
    assertEquals("(0.0, 0.0)", c.toString());
  }

  // Tests that a cluster correctly adds a point to its list of data points
  @Test
  public void testAddPoint() {
    assertEquals(0, c.getDataPoints().size());
    c.addPoint(new Point2D.Double(10, 10));
    assertEquals(1, c.getDataPoints().size());
  }

  // Tests that a cluster correctly computes its new center point given some data points
  @Test
  public void testComputeNewCenter() {
    c.addPoint(new Point2D.Double(10, 10));
    c.addPoint(new Point2D.Double(30, 30));
    c.computeNewCenter();
    assertEquals("(20.0, 20.0)", c.toString());
  }
}