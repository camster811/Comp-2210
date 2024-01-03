import jdk.incubator.vector.VectorOperators;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
   String query;
   long weight;
   
   /**
    * Initialize a term with the given query and weight.
    * This method throws a NullPointerException if query is null,
    * and an IllegalArgumentException if weight is negative.
    */
   public Term(String query, long weight) {
      if (query.equals(null)) {
         throw new NullPointerException();
      }
      if (weight < 0) {
         throw new IllegalArgumentException();
      }
      this.query = query;
      this.weight = weight;
   }
   
   
   /**
    * Compares the two terms in descending order of weight.
    */
   public static Comparator<Term> byDescendingWeightOrder() {
      return new Comparator<Term>() {
         @Override
         public int compare(Term o1, Term o2) {
            return Long.compare(o2.weight, o1.weight);
         }
      };
   }
   
   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero.
    */
   public static Comparator<Term> byPrefixOrder(int length) {
      if (length < 0) {
         throw new IllegalArgumentException();
      }
      return new Comparator<Term>() {
         @Override
         public int compare(Term o1, Term o2) {
            String term1 = o1.query;
            String term2 = o2.query;
            
            term1 = term1.substring(0, Math.min(length, term1.length()));
            term2 = term2.substring(0, Math.min(length, term2.length()));
            
            return term1.compareTo(term2);
         }
      };
   }
   
   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
   public int compareTo(Term other) {
      Term otherTerm = (Term) other;
      return this.query.compareTo(otherTerm.query);
   }
   
   /**
    * Returns a string representation of this term in the following format:
    * query followed by a tab followed by weight
    */
   @Override
   public String toString() {
      return query + "	" + weight;
   }
}

public class BinarySearch {
   
   /**
    * Returns the index of the first key in a[] that equals the search key,
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      int index = -1;
      if (a == null) {
         throw new NullPointerException();
      }
      if (key == null) {
         throw new NullPointerException();
      }
      if (comparator == null) {
         throw new NullPointerException();
      }
      for (int i = 0; i < a.length; i++) {
         if (a[i] == key) {
            index = i;
            break;
         }
      }
      return index;
   }
   
   /**
    * Returns the index of the last key in a[] that equals the search key,
    * or -1 if no such key exists. This method throws a NullPointerException
    * if any parameter is null.
    */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      int index = -1;
      if (a == null) {
         throw new NullPointerException();
      }
      if (key == null) {
         throw new NullPointerException();
      }
      if (comparator == null) {
         throw new NullPointerException();
      }
      for (int i = 0; i < a.length; i++) {
         if (a[i] == key) {
            index = i;
         }
      }
      return index;
   }
   
   public class Autocomplete {
      private Term[] terms;
      
      /**
       * Initializes a data structure from the given array of terms.
       * This method throws a NullPointerException if terms is null.
       */
      public Autocomplete(Term[] terms) {
      	if (terms == null) {
      		throw new NullPointerException();
			}
         Arrays.sort(terms);
         this.terms = terms;
      }
      
      /**
       * Returns all terms that start with the given prefix, in descending order of weight.
       * This method throws a NullPointerException if prefix is null.
       */
      public Term[] allMatches(String prefix) {
         Comparator<Term> comparator = Term.byPrefixOrder(1);
         Term key = new Term(prefix, -1);
         int indexOne = BinarySearch.firstIndexOf(terms, key, comparator);
         int indexTwo = BinarySearch.lastIndexOf(terms, key, comparator);
         
         if (indexOne == -1) {
            return null;
         } else {
            Term found[] = new Term[indexTwo - indexOne + 1];
            
            for (int i = indexOne; i <= indexTwo; i++) {
               found[i - indexOne] = terms[i];
            }
            Arrays.sort(found, Term.byDescendingWeightOrder());
            return found;
         }
      }
   }
}
