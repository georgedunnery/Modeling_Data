import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single k-means cluster. A cluster has a center point, integer (index),
 * and a list of assigned data points.
 */
public class Cluster {
  private int index;
  private Point2D.Double center;
  private List<Point2D.Double> dataPoints;

  /**
   * Constructs a new cluster object given an index and center point. Initializes an empty list of
   * data points.
   *
   * @param i index of cluster
   * @param c center point of cluster
   */
  public Cluster(int i, Point2D.Double c) {
    this.index = i;
    this.center = c;
    this.dataPoints = new ArrayList<>();
  }

  /**
   * Adds a single point to this cluster's list of data points.
   *
   * @param point point to add to cluster
   */
  public void addPoint(Point2D.Double point) {
    this.dataPoints.add(point);
  }

  /**
   * Getter method for center of cluster.
   *
   * @return center point of this cluster
   */
  public Point2D.Double getCenter() {
    return this.center;
  }

  /**
   * Getter method for the data points of cluster.
   *
   * @return list of data points assigned to this cluster
   */
  public List<Point2D.Double> getDataPoints() {
    return this.dataPoints;
  }

  /**
   * Getter method for the index of cluster.
   *
   * @return index of this cluster
   */
  public int getIndex() {
    return this.index;
  }

  /**
   * Computes the new center of this cluster by averaging the data points assigned to that cluster.
   */
  public void computeNewCenter() {
    double averageX = 0;
    double averageY = 0;
    for (Point2D.Double point : this.dataPoints) {
      averageX = averageX + point.getX();
      averageY = averageY + point.getY();
    }
    averageX = averageX / this.dataPoints.size();
    averageY = averageY / this.dataPoints.size();
    this.center = new Point2D.Double(averageX, averageY);
  }

  /**
   * Resets this cluster's list of data points.
   */
  public void resetDataPoints() {
    this.dataPoints = new ArrayList<>();
  }

  /**
   * Returns a string representation of this cluster's center point.
   *
   * @return string in the form (x, y)
   */
  public String toString() {
    String printable = "(" + this.center.getX() + ", " + this.center.getY() + ")";
    return printable;
  }
}
