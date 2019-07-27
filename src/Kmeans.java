import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents calculations used by the K-means clustering algorithms. Methods are static
 * and used by the DataModel class.
 */
public final class Kmeans {

  /**
   * Chooses k points from the given data set at random to be clusters.
   *
   * @param data given set of points
   * @param k    number of clusters to generate
   * @return array of clusters with each corresponding to the ith point in the dataset
   */
  protected static List<Cluster> getClusters(List<Point2D.Double> data, int k) {
    List<Cluster> clusters = new ArrayList<Cluster>();
    for (int i = 0; i < k; i++) {
      int c = (int) (Math.random() * data.size());
      while (clusters.contains(c)) {
        c = (int) (Math.random() * data.size());
      }
      clusters.add(new Cluster(i, data.get(c)));
    }
    return clusters;
  }

  /**
   * Assigns a single point to a cluster by finding the minimum distance to all clusters.
   *
   * @param point    point to categorize
   * @param clusters list of clusters to compare to the given point
   */
  protected static void categorizePoint(Point2D.Double point, List<Cluster> clusters) {
    List<Double> distances = new ArrayList<>();
    for (int i = 0; i < clusters.size(); i++) {
      double dist = Kmeans.distance(point, clusters.get(i).getCenter());
      distances.add(dist);
    }
    Object minimumDistance = Collections.min(distances);
    Cluster minimumDistCluster = clusters.get(distances.indexOf(minimumDistance));
    minimumDistCluster.addPoint(point);
  }

  /**
   * Computes the new center of each cluster.
   *
   * @param clusters current list of clusters
   */
  protected static void computeClusterCenters(List<Cluster> clusters) {
    for (Cluster c : clusters) {
      c.computeNewCenter();
    }
  }

  /**
   * Computes the new error as the average distance of each data point from the center of its
   * cluster.
   *
   * @param data     list of data points
   * @param clusters list of clusters
   * @return new computed error
   */
  protected static double computeNewError(List<Point2D.Double> data, List<Cluster> clusters) {
    double newError = 0;
    for (Cluster c : clusters) {
      for (Point2D.Double p : c.getDataPoints()) {
        newError = newError + Kmeans.distance(p, c.getCenter());
      }
    }
    newError = newError / data.size();
    return newError;
  }

  /**
   * Creates a list of cluster indices for each point that completes the clustering algorithm.
   *
   * @param data     list of data points
   * @param clusters list of clusters
   * @return list of integers, each corresponding to the index of a point's cluster
   */
  protected static List<Integer> reportClusters(List<Point2D.Double> data, List<Cluster> clusters) {
    List<Integer> clusterIndices = new ArrayList<>();
    for (Point2D.Double point : data) {
      for (Cluster c : clusters) {
        if (c.getDataPoints().contains(point)) {
          clusterIndices.add(c.getIndex());
        }
      }
    }
    return clusterIndices;
  }

  /**
   * Helper method that resets the data point lists for each cluster.
   *
   * @param clusters list of clusters for this current iteration
   */
  protected static void resetClusterDataPointSizes(List<Cluster> clusters) {
    for (Cluster c : clusters) {
      c.resetDataPoints();
    }
  }

  /**
   * Computes the Euclidian distance between two points.
   *
   * @param p1 first point
   * @param p2 second point
   * @return distance between p1 and p2, a double
   */
  protected static double distance(Point2D.Double p1, Point2D.Double p2) {
    double xDiff = p1.getX() - p2.getX();
    double yDiff = p1.getY() - p2.getY();
    double xSq = xDiff * xDiff;
    double ySq = yDiff * yDiff;
    return Math.sqrt(xSq + ySq);

  }


}
