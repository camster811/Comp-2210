import java.util.Iterator;

/**
 * A collection that implements set behavior.
 *
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 2017-10-31
 *
 */

public interface Set<T> extends Iterable<T> {
   
   /**
    * Ensures the collection contains the specified element.
    * No specific order can be assumed. Neither duplicate nor null
    * values are allowed.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   boolean add(T element);
   
   
   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   boolean contains(T element);
   
   
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   int size();
   
   
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   boolean isEmpty();
   
   
   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as the
    *               parameter set, false otherwise
    */
   boolean equals(Set<T> s);
   
   
   /**
    * Returns an iterator over the elements in this collection.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this collection
    */
   Iterator<T> iterator();
   
}
