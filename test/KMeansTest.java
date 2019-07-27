import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test for the K-means clustering algorithm. It is implemented by the kmeans method in the
 * DataModel class.
 */
public class KMeansTest {

  // Tests that the integer list of clusters returned by kmeans is the same length as the dataset.
  @Test
  public void testSameLengthLists() {
    DataModel testData = new DataModel();
    testData.add(new Point2D.Double(-1, -1));
    testData.add(new Point2D.Double(-1, 1));
    testData.add(new Point2D.Double(1, -1));
    testData.add(new Point2D.Double(1, 1));
    List testClusters = testData.kmeans(3);
    assertEquals(testData.getData().size(), testClusters.size());
  }

  // Tests that points on a dataset with 2 obvious clusters are assigned correctly
  @Test
  public void testSimple2Clusters() {
    DataModel simple = new DataModel();
    simple.add(new Point2D.Double(-50, -40));
    simple.add(new Point2D.Double(-56, -43));
    simple.add(new Point2D.Double(-52, -39));
    simple.add(new Point2D.Double(-47, -41));
    simple.add(new Point2D.Double(82, 77));
    simple.add(new Point2D.Double(79, 90));
    simple.add(new Point2D.Double(80, 84));
    simple.add(new Point2D.Double(89, 85));
    List<Integer> option1 = Arrays.asList(new Integer[]{0, 0, 0, 0, 1, 1, 1, 1});
    List<Integer> option2 = Arrays.asList(new Integer[]{1, 1, 1, 1, 0, 0, 0, 0});
    List simpleClusters = simple.kmeans(2);
    assertTrue(simpleClusters.equals(option1) || simpleClusters.equals(option2));
  }


  // Tests that points on a dataset with 3 obvious clusters are assigned correctly
  @Test
  public void testSimple3Clusters() {
    DataModel simple3 = new DataModel();
    simple3.add(new Point2D.Double(-100, -100));
    simple3.add(new Point2D.Double(-100, -99));
    simple3.add(new Point2D.Double(-100, -98));
    simple3.add(new Point2D.Double(99, 100));
    simple3.add(new Point2D.Double(99, 99));
    simple3.add(new Point2D.Double(99, 98));
    simple3.add(new Point2D.Double(-100, 100));
    simple3.add(new Point2D.Double(-100, 99));
    simple3.add(new Point2D.Double(-100, 98));
    List<Integer> option1 = Arrays.asList(new Integer[]{0, 0, 0, 1, 1, 1, 2, 2, 2});
    List<Integer> option2 = Arrays.asList(new Integer[]{0, 0, 0, 2, 2, 2, 1, 1, 1});
    List<Integer> option3 = Arrays.asList(new Integer[]{1, 1, 1, 2, 2, 2, 0, 0, 0});
    List<Integer> option4 = Arrays.asList(new Integer[]{1, 1, 1, 0, 0, 0, 2, 2, 2});
    List<Integer> option5 = Arrays.asList(new Integer[]{2, 2, 2, 1, 1, 1, 0, 0, 0});
    List<Integer> option6 = Arrays.asList(new Integer[]{2, 2, 2, 0, 0, 0, 1, 1, 1});
    List simpleClusters = simple3.kmeans(3);
    assertTrue(simpleClusters.equals(option1) || simpleClusters.equals(option2)
            || simpleClusters.equals(option3) || simpleClusters.equals(option4)
            || simpleClusters.equals(option5) || simpleClusters.equals(option6));
  }

  // Tests that passing in 0 for k results in an exception
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalKZero() {
    DataModel illegalKMeansZero = new DataModel();
    List illegalClustersZero = illegalKMeansZero.kmeans(0);
  }

  // Tests that passing in a negative number for k results in an exception
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalKNegative() {
    DataModel illegalKMeansNegative = new DataModel();
    List illegalClustersNegative = illegalKMeansNegative.kmeans(0);
  }

  // Tests that passing in a k larger than the size of the data set
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalKTooLarge() {
    DataModel illegalKMeansTooLarge = new DataModel();
    illegalKMeansTooLarge.add(new Point2D.Double(-100, -100));
    illegalKMeansTooLarge.add(new Point2D.Double(-99, -100));
    illegalKMeansTooLarge.add(new Point2D.Double(-98, -100));
    List illegalClustersTooLarge = illegalKMeansTooLarge.kmeans(4);
  }

  // Tests that the distance method correctly returns distance between two points
  @Test
  public void testDistance() {
    Point2D.Double point1 = new Point2D.Double(3, 4);
    Point2D.Double point2 = new Point2D.Double(0, 0);
    assertEquals(5, Kmeans.distance(point1, point2), 0.001);
  }
}
