import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * A JUnit test for the Line class. The Line class uses the standard form linear equation.
 */
public class StandardLineTest {

  private Line construct;

  @Before
  public void setup() {
    construct = new Line(55, -9.3321, 75.44);
  }

  /**
   * Verifies that the line constructor works properly.
   */
  @Test
  public void testConstructor() {
    assertEquals(55.0, construct.getA(), 0.0001);
    assertEquals(-9.3321, construct.getB(), 0.0001);
    assertEquals(75.44, construct.getC(), 0.0001);
  }

  /**
   * Confirm that an invalid line throws an exception.
   */
  @Test
  public void testABBothZeroException() {
    try {
      Line badLine = new Line(0, 0, 10);
      fail("A and B cannot both be zero.");
    } catch (IllegalArgumentException e) {
      // Pass
    }
  }

  /**
   * Verifies that the toString method generates a properly formatted string.
   */
  @Test
  public void testToString() {
    assertEquals("55.0x + -9.3321y + 75.44 = 0", construct.toString());
  }

  /**
   * Verifies that the equals method works correctly.
   */
  @Test
  public void testEquals() {
    // Reflexivity: the same object is equal to itself
    assertEquals(construct, construct);
    // Symmetry: this object is equal to the other, and the other is equal to this
    Line same = new Line(55, -9.3321, 75.44);
    assertEquals(construct, same);
    assertEquals(same, construct);
    // Different by one constant value: a,b,c
    Line differentA = new Line(54, -9.3321, 75.44);
    assertNotEquals(construct, differentA);
    Line differentB = new Line(55, -9.0, 75.44);
    assertNotEquals(construct, differentB);
    Line differentC = new Line(55, -9.3321, 75.46);
    assertNotEquals(construct, differentC);
    // Transitivity: if a equal to b and b equal to c, a equal to c
    Line transitive = new Line(55, -9.3321, 75.44);
    assertEquals(construct, same);
    assertEquals(same, transitive);
    assertEquals(construct, transitive);
  }

  /**
   * Verifies that hash values match up with the equals method.
   */
  @Test
  public void testHashCode() {
    // Reflexivity: the same object is equal to itself
    assertEquals(construct.hashCode(), construct.hashCode());
    // Identical Line objects
    Line same = new Line(55, -9.3321, 75.44);
    assertEquals(construct.hashCode(), same.hashCode());
    assertEquals(same.hashCode(), construct.hashCode());
    // Different by one constant value: a,b,c
    Line differentA = new Line(54, -9.3321, 75.44);
    assertNotEquals(construct.hashCode(), differentA.hashCode());
    Line differentB = new Line(55, -9.0, 75.44);
    assertNotEquals(construct.hashCode(), differentB.hashCode());
    Line differentC = new Line(55, -9.3321, 75.46);
    assertNotEquals(construct.hashCode(), differentC.hashCode());
    // Transitivity: if a equal to b and b equal to c, a equal to c
    Line transitive = new Line(55, -9.3321, 75.44);
    assertEquals(construct.hashCode(), same.hashCode());
    assertEquals(same.hashCode(), transitive.hashCode());
    assertEquals(construct.hashCode(), transitive.hashCode());
  }

  /**
   * Verifies the getX method returns the correct x value for a given y coordinate.
   */
  @Test
  public void testGetX() {
    // Checking values for a line with a negative slope
    Line negative = new Line(2, 4, 8);
    assertEquals(-4, negative.getX(0), 0.01);
    assertEquals(-2, negative.getX(-1), 0.01);
    assertEquals(0, negative.getX(-2), 0.01);
    // Checking values for a line with a positive slope
    Line positive = new Line(-3, 6, 12);
    assertEquals(0, positive.getX(-2), 0.01);
    assertEquals(2, positive.getX(-1), 0.01);
    assertEquals(4, positive.getX(0), 0.01);
    // Verify functionality when c = 0
    Line zero = new Line(1, 1, 0);
    assertEquals(-2, zero.getX(2), 0.01);
  }

  /**
   * Verifies the getY method returns the correct y value for a given x coordinate.
   */
  @Test
  public void testGetY() {
    // Checking values for a line with a negative slope
    Line negative = new Line(2, 4, 8);
    assertEquals(0, negative.getY(-4), 0.01);
    assertEquals(-1, negative.getY(-2), 0.01);
    assertEquals(-2, negative.getY(0), 0.01);
    // Checking values for a line with a positive slope
    Line positive = new Line(-3, 6, 12);
    assertEquals(-2, positive.getY(0), 0.01);
    assertEquals(-1, positive.getY(2), 0.01);
    assertEquals(0, positive.getY(4), 0.01);
    // Verify functionality when c = 0
    Line zero = new Line(1, 1, 0);
    assertEquals(2, zero.getY(-2), 0.01);
  }

  /**
   * Checks that the getX method throws an exception for a perfectly vertical or horizontal line.
   */
  @Test
  public void testGetXZeroException() {
    // Perfectly Horizontal Line
    Line horizontal = new Line(0, 2, 2);
    try {
      horizontal.getX(5);
      fail("Exception should have been thrown.");
    } catch (IllegalStateException e) {
      // Pass
    }
    Line vertical = new Line(2, 0, 2);
    try {
      vertical.getX(5);
      fail("Exception should have been thrown.");
    } catch (IllegalStateException e) {
      // Pass
    }
  }

  /**
   * Checks that the getX method throws an exception for a perfectly vertical or horizontal line.
   */
  @Test
  public void testGetYZeroException() {
    // Perfectly Horizontal Line
    Line horizontal = new Line(0, 2, 2);
    try {
      horizontal.getY(5);
      fail("Exception should have been thrown.");
    } catch (IllegalStateException e) {
      // Pass
    }
    Line vertical = new Line(2, 0, 2);
    try {
      vertical.getY(5);
      fail("Exception should have been thrown.");
    } catch (IllegalStateException e) {
      // Pass
    }
  }
}
