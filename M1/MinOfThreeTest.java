import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MinOfThreeTest {
   
   /** A test that tests method 1. **/
   @Test public void min1Test1() {
      assertEquals(MinOfThree.min1(1, 2, 1), 1);
   }
   
   /** A test that tests method 1. **/
   @Test public void min1Test2() {
      assertEquals(MinOfThree.min1(1, 1, 5), 1);
   }
   
   /** A test that tests method 1. **/
   @Test public void min2Test1() {
      assertEquals(MinOfThree.min1(1, 2, 1), 1);
   }
   
   /** A test that tests method 1. **/
   @Test public void min2Test2() {
      assertEquals(MinOfThree.min1(5, 1, 1), 1);
   }
   
   
}
