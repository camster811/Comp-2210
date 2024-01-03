import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Return the elements in a collection that are strictly less than a specified
 * value.
 *
 */
public class PracticeLab1 {

/**
 * Complete the body of the lessThanSubset method so that it returns a Collection
 * containing only the elements in the parameter collection that are strictly less
 * than the parameter value, with respect to the natural order defined for type T.
 * An instance of ArrayList has been declared for you to use to assemble the correct
 * subset of values and return.
 *
 * The following table lists various examples of the parameters collection and
 * value along with the correct return value from the call lessThanSubset
 * (collection, value).
 * The order of values in the returned collection is not important.
 * If there are no qualifying values, the method should return an empty collection.
 */
   
   /**
    * Returns the elements in collection strictly less than value.
    */
   public static <T extends Comparable<T>>
   Collection<T> greaterThanSubset(Collection<T> collection, T value) {
      ArrayList<T> result = new ArrayList<>();
      for (T element : collection) {
         if (element.compareTo(value) < 0) {
            result.add(element);
         }
      }
      return result;
   }
}