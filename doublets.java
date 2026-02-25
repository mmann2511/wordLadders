import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Michael Mann
 * @version 02/19/2026
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   /////////////////////////////////////////////////////////////////////////////
   // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
   // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
   // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
   // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
   // table with chaining).                                                   //
   /////////////////////////////////////////////////////////////////////////////
   private HashSet<String> lexicon;

   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         //////////////////////////////////////
         // INSTANTIATE lexicon OBJECT HERE  //
         //////////////////////////////////////
         lexicon = new HashSet<>();
         
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next().toLowerCase();
            
            this.lexicon.add(str);
           
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   
   
   /**
   * Returns the total number of words in the current lexicon.
   *
   * @return number of words in the lexicon
   */
   public int getWordCount() {
      return this.lexicon.size();
   }
   
   
   /**
   * Checks to see if the given string is a word.
   *
   * @param  str the string to check
   * @return     true if str is a word, false otherwise
   */
   public boolean isWord(String str) {
      return this.lexicon.contains(str.toLowerCase());
   }
   
   
     /**
   * Returns the Hamming distance between two strings, str1 and str2. The
   * Hamming distance between two strings of equal length is defined as the
   * number of positions at which the corresponding symbols are different. The
   * Hamming distance is undefined if the strings have different length, and
   * this method returns -1 in that case. See the following link for
   * reference: https://en.wikipedia.org/wiki/Hamming_distance
   *
   * @param  str1 the first string
   * @param  str2 the second string
   * @return      the Hamming distance between str1 and str2 if they are the
   *                  same length, -1 otherwise
   */
   public int getHammingDistance(String str1, String str2) {
      if (str1.length() != str2.length()) {
         return -1; //undefined
      }
      
      str1 = str1.toLowerCase();
      str2 = str2.toLowerCase();
      
      int distanceCounter = 0;
      
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            distanceCounter++;
         }
      }
      return distanceCounter;
   }
   
   /**
   * Returns all the words that have a Hamming distance of one relative to the
   * given word.
   *
   * @param  word the given word
   * @return      the neighbors of the given word
   */
   public List<String> getNeighbors(String word) {
   
      word = word.toLowerCase();
      List<String> neighbors = new ArrayList<>();
      
      char[] chars = word.toCharArray();
      
      for (int i = 0; i < chars.length; i++) {
         
         char originalChar = chars[i];
         
         for (char c = 'a'; c <= 'z'; c++) {
            if (c == originalChar) {
               continue;
            }
            chars[i] = c;
            String newWord = new String(chars);
            if (lexicon.contains(newWord)) {
               neighbors.add(newWord);
            }
         }
         chars[i] = originalChar;
      }
      
      return neighbors;
    
   }
   
   /**
   * Checks to see if the given sequence of strings is a valid word ladder.
   *
   * @param  sequence the given sequence of strings
   * @return          true if the given sequence is a valid word ladder,
   *                       false otherwise
   */
   public boolean isWordLadder(List<String> sequence) {
     // returns true or false if parameter List is a word ladder
     // word ladder is a start -> end word of SAME length
     // each step just changes 1 letter, so could potentionally use last method
     // example: cat, can, con, cog, dog
     // im thinking a loop
     // check length - return false immeditley is not ==
     // maybe start with sentinal
      if (sequence == null) {
         throw new IllegalArgumentException(
            "List must not be null");
      }
      
      if (sequence.isEmpty()) {
         return false;
      }
     
      String a = sequence.get(0).toLowerCase();
      
      if (!lexicon.contains(a)) {
         return false;
      }
      
      for (int i = 1; i < sequence.size(); i++) {
         String b = sequence.get(i).toLowerCase();
         
         if (!lexicon.contains(b)) {
            return false;
         }
         
         if (b.length() != a.length()) {
            return false;
         }
         
         int hammingDistance = getHammingDistance(a, b);
         
         if (hammingDistance != 1) {
            return false;
         }
         
         a = b;
      }
     
      return true;
   }
   
   /**
  * Returns a minimum-length word ladder from start to end. If multiple
  * minimum-length word ladders exist, no guarantee is made regarding which
  * one is returned. If no word ladder exists, this method returns an empty
  * list.
  *
  * Breadth-first search must be used in all implementing classes.
  *
  * @param  start  the starting word
  * @param  end    the ending word
  * @return        a minimum length word ladder from start to end
  */
   public List<String> getMinLadder(String start, String end) {
   
      start = start.toLowerCase();
      end = end.toLowerCase();
     
      if (!lexicon.contains(start) || !lexicon.contains(end)) {
         return new ArrayList<String>();
      }
      
      if (start.length() != end.length()) {
         return new ArrayList<String>();
      }
      
      if (start.equals(end)) {
         List<String> list = new ArrayList<>();
         list.add(start);
         return list;
      }
      
      HashSet<String> triedWords = new HashSet<>();
      triedWords.add(start);
      
      Deque<Node> queue = new ArrayDeque<>();
      queue.addLast(new Node(start, null));
      
      while (!queue.isEmpty()) {
         Node currentNode = queue.removeFirst();
         
         for (String neighbor: getNeighbors(currentNode.word)) {
         
            if (!triedWords.contains(neighbor)) {
               triedWords.add(neighbor);
               
               Node newNode = new Node(neighbor, currentNode);
               
               if (neighbor.equals(end)) {
                  return wordLadder(newNode);
               }
               
               queue.addLast(newNode);
            }
         }
      }
      return new ArrayList<String>();
   }
   
   
   
   //////////////////////////////////////////////
   //// PRIVATE CLASES AND HELPER METHODS //////
   /////////////////////////////////////////////
   
   /**
    * Node creation class.
    * For linked list/chain for word ladder BFS
    *
    */
   private class Node {
      private String word;
      private Node parent;
      
   
      public Node(String word, Node parent) {
         this.word = word;
         this.parent = parent;
      }
   }
   
   /**
    * wordLadder Builder.
    * takes LinkedList chain from BFS
    * and adds in O(1) time
    */
   private List<String> wordLadder(Node node) {
   
      LinkedList<String> ladder = new LinkedList<>();
      
      while (node != null) {
         ladder.addFirst(node.word);
         
         node = node.parent;
      }
      
      return ladder;
   }
}

