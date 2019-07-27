import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A class used to plot the results of a single k-means clustering algorithm. This class expects to
 * work upon a .txt file with two double values per line that correspond to the x and y coordinates
 * of a data point in the data model. Plotter assumes less than 7 clusters/colors.
 */
public class PlotKMeans {

  /**
   * Method to generate a k-means clustering image.
   * @param args ignored (to run main function)
   */
  public static void main(String[] args) {
    ImagePlotter plotter = new ImagePlotter();
    DataModel clusterModel = new DataModel();
    int k = 2;
    // Initialize the x & y min/max values to the origin
    double xMin = 0;
    double xMax = 0;
    double yMin = 0;
    double yMax = 0;
    // Try-catch block necessary due to risk of FileNotFound exception
    try {
      Scanner s = new Scanner(new FileInputStream("data/clusterdata-2.txt"));
      // Add all the points from the text file to the DataModel and the ImagePlotter
      while (s.hasNextLine()) {
        if (s.hasNextDouble()) {
          double x = s.nextDouble();
          double y = s.nextDouble();
          clusterModel.add(new Point2D.Double(x, y));
          //plotter.addPoint((int)Math.round(x), (int)Math.round(y));
          // Set the min and max values as new points are encountered
          if (x < xMin) {
            xMin = x;
          }
          if (x > xMax) {
            xMax = x;
          }
          if (y < yMin) {
            yMin = y;
          }
          if (y > yMax) {
            yMax = y;
          }
        }
        // When a blank line is encountered, exit the loop to avoid a NoSuchElementException
        else {
          break;
        }
      }
      List clusterIntegerList = clusterModel.kmeans(k);
      List<Point2D.Double> points = clusterModel.getData();
      for (int i = 0; i < clusterIntegerList.size(); i++) {
        double x = points.get(i).getX();
        double y = points.get(i).getY();
        Color color;
        int index = (int) clusterIntegerList.get(i);

        switch (index) {
          case 0:
            color = Color.RED;
            break;
          case 1:
            color = Color.BLACK;
            break;
          case 2:
            color = Color.BLUE;
            break;
          case 3:
            color = Color.GREEN;
            break;
          case 4:
            color = Color.PINK;
            break;
          default:
            color = Color.GRAY;
            break;
        }
        plotter.addPoint(
                (int) Math.round(x), (int) Math.round(y), color);
      }
      // Set the dimensions according to min/max
      plotter.setDimensions((int) Math.round(xMin), (int) Math.round(xMax),
              (int) Math.round(yMin), (int) Math.round(yMax));
      // Write the image to a file
      try {
        plotter.write("output/cluster.png");
      } catch (IOException e) {
        System.out.println("File not found, encountered by ImagePlotter");
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found, encountered by PlotKMeans");
    }
  }
}
