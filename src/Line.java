import java.util.Objects;

/**
 * A class to represent a line on a 2D graph. This implementation uses the standard form of a line,
 * <code>ax + by + c = 0</code>.
 */
public class Line {

  /**
   * The first constant variable 'a'.
   */
  private double a;

  /**
   * The second constant variable 'b'.
   */
  private double b;

  /**
   * The third constant variable 'c'.
   */
  private double c;

  /**
   * Constructs a line object in standard form: <code>ax + by + c = 0</code>.
   *
   * @param aInit Double, the first constant 'a' in the standard form line equation.
   * @param bInit Double, the second constant 'b' in the standard form line equation.
   * @param cInit Double, the third constant 'c' in the standard form line equation.
   * @throws IllegalArgumentException When A and B are both zero.
   */
  public Line(double aInit, double bInit, double cInit) throws IllegalArgumentException {
    if (aInit == 0 && bInit == 0) {
      throw new IllegalArgumentException("Constants 'a' and 'b' cannot both be zero.");
    }
    this.a = aInit;
    this.b = bInit;
    this.c = cInit;
  }

  /**
   * Method to get the value of the first constant 'a'.
   *
   * @return Double, the value of 'a'.
   */
  public double getA() {
    return this.a;
  }

  /**
   * Method to get the value of the second constant 'b'.
   *
   * @return Double, the value of 'b'.
   */
  public double getB() {
    return this.b;
  }

  /**
   * Method to get the value of the third constant 'c'.
   *
   * @return Double, the value of 'c'.
   */
  public double getC() {
    return this.c;
  }

  /**
   * Method to generate a string representation of this line in standard form. The general formula
   * of a standard form equation is <code>ax + by + c = 0</code>.
   *
   * @return String, representation of this line as a standard form equation.
   */
  @Override
  public String toString() {
    return this.getA() + "x + " + this.getB() + "y + " + this.getC() + " = 0";
  }

  /**
   * Method to determine if this Line is equal to another object. Note a line cannot be equal to any
   * other kind of object.
   *
   * @param obj Object, the other object to compare with.
   * @return Boolean, true if the two objects are equal, otherwise false.
   */
  @Override
  public boolean equals(Object obj) {
    // Reflexivity: if other is indeed the same object
    if (this == obj) {
      return true;
    }
    // A Line cannot be equivalent to any other type of object
    if (!(obj instanceof Line)) {
      return false;
    }
    // Cast the other object as a Line
    Line other = (Line) obj;
    // Symmetry and Transitivity: Lastly compare the a,b,c constants of each standard form Line
    return (this.getA() == other.getA()
            && this.getB() == other.getB()
            && this.getC() == other.getC());
  }

  /**
   * Method to generate a hashcode for the Line object. Relies on the same values that determine
   * equality.
   *
   * @return Integer, the hash value.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getA(), this.getB(), this.getC());
  }

  /**
   * Method to get the corresponding y coordinate for a given x coordinate on this line.
   *
   * @param x Double, the x coordinate whose y coordinate is requested.
   * @return Double, the corresponding y coordinate.
   * @throws IllegalArgumentException When A or B is zero (perfectly horizontal or vertical line).
   */
  public double getY(double x) {
    if (this.a == 0 || this.b == 0) {
      throw new IllegalStateException("Line is perfectly horizontal or vertical.");
    }
    return -((this.a * x + this.c) / this.b);
  }

  /**
   * Method to get the corresponding x coordinate for a given y coordinate on this line.
   *
   * @param y Double, the y coordinate whose x coordinate is requested.
   * @return Double, the corresponding y coordinate.
   * @throws IllegalArgumentException When A or B is zero (perfectly horizontal or vertical line).
   */
  public double getX(double y) {
    if (this.a == 0 || this.b == 0) {
      throw new IllegalStateException("Line is perfectly horizontal or vertical.");
    }
    return -((this.b * y + this.c) / this.a);
  }
}