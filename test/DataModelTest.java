import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test for the DataModel and underlying Point2D classes. Focus on initial functionality.
 */
public class DataModelTest {

  private Point2D.Double origin;
  private Point2D.Double decimal;

  @Before
  public void setup() {
    origin = new Point2D.Double(0, 0);
    decimal = new Point2D.Double(52.336789, -24.263409);
  }

  /**
   * Verifies that the DataModel constructor creates an initial DataModel object properly.
   */
  @Test
  public void testDataModelInitializer() {
    DataModel empty = new DataModel();
    ArrayList<Point2D.Double> expected = new ArrayList<>(0);
    assertEquals(expected, empty.getData());
  }

  /**
   * Verifies that a point is added to the data model correctly.
   */
  @Test
  public void testAdd() {
    DataModel addToMe = new DataModel();
    addToMe.add(origin);
    assertEquals(origin, addToMe.getData().get(0));
    addToMe.add(decimal);
    assertEquals(decimal, addToMe.getData().get(1));
  }

  /**
   * Verifies that the array list is returned correctly.
   */
  @Test
  public void testGetData() {
    DataModel manyPoints = new DataModel();
    ArrayList<Point2D.Double> expected = new ArrayList<>();
    manyPoints.add(origin);
    expected.add(origin);
    manyPoints.add(decimal);
    expected.add(decimal);
    Point2D.Double negatives = new Point2D.Double(-100.123456, -300.654321);
    manyPoints.add(negatives);
    expected.add(negatives);
    Point2D.Double positives = new Point2D.Double(55.000009, 71.0000009);
    manyPoints.add(positives);
    expected.add(positives);
    assertEquals(expected, manyPoints.getData());
  }
}
