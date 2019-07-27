import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class used to plot a linear regression. This class expects to work upon a .txt file with two
 * double values per line that correspond to the x and y coordinates of a data point in the data
 * model.
 */
public class PlotLinearRegression {

  /**
   * Method to generate a linear regression image. Image will contain black dots representing the
   * data points, and a red line representing the line of best fit. The image will be saved to the
   * output folder as 'linear.png'.
   * @param args ignored (to run main function)
   */
  public static void main(String[] args) {
    ImagePlotter graph = new ImagePlotter();
    DataModel linear = new DataModel();
    // Initialize the x & y min/max values to the origin
    double xMin = 0;
    double xMax = 0;
    double yMin = 0;
    double yMax = 0;
    // Try-catch block necessary due to risk of FileNotFound exception
    try {
      Scanner scan = new Scanner(new FileInputStream("data/linedata-1.txt"));
      // Add all the points from the text file to the DataModel and the ImagePlotter
      while (scan.hasNextLine()) {
        if (scan.hasNextDouble()) {
          double x = scan.nextDouble();
          double y = scan.nextDouble();
          linear.add(new Point2D.Double(x, y));
          graph.addPoint((int) Math.round(x), (int) Math.round(y));
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
      // Set the dimensions according to min/max
      graph.setDimensions((int) Math.round(xMin), (int) Math.round(xMax),
              (int) Math.round(yMin), (int) Math.round(yMax));
      // Now that all the points are set, plot the line
      Line bestFit = linear.fitLine();
      graph.addLine((int) Math.round(xMin) - 20,
              (int) Math.round(bestFit.getY(Math.round(xMin) - 20)),
              (int) Math.round(xMax) + 20,
              (int) Math.round(bestFit.getY(Math.round(xMax) + 20)), Color.RED);

      // Write the image to a file
      try {
        graph.write("output/linear.png");
      } catch (IOException e) {
        System.out.println("File not found, encountered by ImagePlotter");
      }

    } catch (FileNotFoundException e) {
      System.out.println("File not found, encountered by PlotLinearRegression.");
    }
  }
}
