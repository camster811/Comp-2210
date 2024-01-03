import java.util.List;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;

public class GameEngine implements WordSearchGame {
   private TreeSet<String> wordList;
   private String[][] board;
   private int size;
   private SortedSet<String> allWords;
   
   public GameEngine() {
      wordList = null;
      size = 4;
      board = new String[size][size];
      board[0][0] = "E";
      board[0][1] = "E";
      board[0][2] = "C";
      board[0][3] = "A";
      board[1][0] = "A";
      board[1][1] = "L";
      board[1][2] = "E";
      board[1][3] = "P";
      board[2][0] = "H";
      board[2][1] = "N";
      board[2][2] = "B";
      board[2][3] = "O";
      board[3][0] = "Q";
      board[3][1] = "T";
      board[3][2] = "T";
      board[3][3] = "Y";
   }
   
   @Override
   public void loadLexicon(String fileName) throws FileNotFoundException {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         wordList = new TreeSet<String>();
         Scanner file = new Scanner(new File(fileName));
         while (file.hasNext()) {
            wordList.add(file.next().toUpperCase());
         }
         file.close();
      }
      catch (FileNotFoundException e) {
         throw new IllegalArgumentException();
      }
   }
   
   @Override
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      
      double boardSize = (double) Math.sqrt(letterArray.length);
      if (boardSize % 1 > .00001) {
         throw new IllegalArgumentException("array must be square");
      }
      board = new String[(int) boardSize][(int) boardSize];
      int i = 0;
      for (int j = 0; j < boardSize; j++) {
         for (int k = 0; k < boardSize; k++) {
            board[j][k] = letterArray[i++];
         }
      }
      size = (int) boardSize;
   }
   
   @Override
   public String getBoard() {
      StringBuilder strBoard = new StringBuilder();
      for (int i = 0; i < size; i++) {
         strBoard.append("\n| ");
         for (int j = 0; j < size; j++) {
            strBoard.append(board[i][j]).append(" ");
         }
         strBoard.append("|");
      }
      return strBoard.toString();
   }
   
   @Override
   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (wordList == null) {
         throw new IllegalStateException();
      }
      
      allWords = new TreeSet<String>();
      LinkedList<Integer> wordProg = new LinkedList<Integer>();
      for (int i = 0; i < (size * size); i++) {
         wordProg.add(i);
         if (isValidWord(toWord(wordProg))
               && toWord(wordProg).length() >= minimumWordLength) {
            allWords.add(toWord(wordProg));
         }
         if (isValidPrefix(toWord(wordProg))) {
            wordBoardSearch(wordProg, minimumWordLength);
         }
         wordProg.clear();
      }
      return allWords;
   }
   
   private LinkedList<Integer> wordBoardSearch(LinkedList<Integer> wordProg,
                                               int min) {
      Position[] adjArray = new Position(wordProg.getLast()).adjacent(wordProg);
      for (Position p : adjArray) {
         if (p == null) {
            break;
         }
         wordProg.add(p.getIndex());
         if (isValidPrefix(toWord(wordProg))) {
            if (isValidWord(toWord(wordProg))
                  && toWord(wordProg).length() >= min) {
               allWords.add(toWord(wordProg));
            }
            wordBoardSearch(wordProg, min);
         }
         else {
            wordProg.removeLast();
         }
      }
      wordProg.removeLast();
      return wordProg;
   }
   
   @Override
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (wordList == null) {
         throw new IllegalStateException();
      }
      int score = 0;
      for (String word : words) {
         if (word.length() >= minimumWordLength && isValidWord(word)
               && !isOnBoard(word).isEmpty()) {
            score += (word.length() - minimumWordLength) + 1;
         }
      }
      return score;
   }
   
   @Override
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (wordList == null) {
         throw new IllegalStateException();
      }
      
      return wordList.contains(wordToCheck);
   }
   
   @Override
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (wordList == null) {
         throw new IllegalStateException();
      }
      String aux = wordList.ceiling(prefixToCheck);
      if (aux != null) {
         return aux.startsWith(prefixToCheck);
      }
      return false;
   }
   
   @Override
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (wordList == null) {
         throw new IllegalStateException();
      }
      LinkedList<Integer> auxList = new LinkedList<Integer>();
      List<Integer> wordPath = boardSearch(wordToCheck, auxList, 0);
      return wordPath;
   }
   
   private LinkedList<Integer> boardSearch(String wordToCheck,
                                           LinkedList<Integer> listProg, int posIndex) {
      if (listProg.size() > 0 && !wordToCheck.equals(toWord(listProg))) {
         Position[] adjArray =
               new Position(posIndex).adjacent(listProg);
         for (Position p : adjArray) {
            if (p == null) {
               break;
            }
            listProg.add(p.getIndex());
            if (wordToCheck.equals(toWord(listProg))) {
               break;
            }
            if (wordToCheck.startsWith(toWord(listProg))) {
               boardSearch(wordToCheck, listProg, p.getIndex());
            }
            else {
               listProg.removeLast();
            }
         }
      }
      if (listProg.size() == 0) {
         while (posIndex < (size * size)) {
            if (wordToCheck.startsWith(new Position(posIndex).getLetter())) {
               listProg.add(posIndex);
               boardSearch(wordToCheck, listProg, posIndex);
            }
            posIndex++;
         }
         return listProg;
      }
      if (toWord(listProg).equals(wordToCheck)) {
         return listProg;
      }
      else {
         listProg.removeLast();
         return listProg;
      }
   }
   
   public String toWord(LinkedList<Integer> listIn) {
      String word = "";
      for (int i : listIn) {
         word += new Position(i).getLetter();
      }
      return word;
   }
   
   private class Position {
      private int x;
      private int y;
      private int index;
      private String letter;
      private static final int MAX_ADJ = 8;
      
      Position(int indexIn) {
         this.index = indexIn;
         if (index == 0) {
            this.x = 0;
            this.y = 0;
         }
         else {
            this.x = index % size;
            this.y = index / size;
         }
         this.letter = board[y][x];
      }
      
      Position(int xIn, int yIn) {
         this.x = xIn;
         this.y = yIn;
         this.index = (y * size) + x;
         this.letter = board[y][x];
      }
      
      public Position[] adjacent(LinkedList<Integer> visited) {
         Position[] adj = new Position[MAX_ADJ];
         int k = 0;
         for (int i = this.x - 1; i <= this.x + 1; i++) {
            for (int j = this.y - 1; j <= this.y + 1; j++) {
               if (!(i == this.x && j == this.y)) {
                  if (isValid(i, j) && !visited.contains((j * size) + i)) {
                     Position aux = new Position(i, j);
                     adj[k++] = aux;
                  }
               }
            }
         }
         return adj;
      }
      
      public boolean isValid(int xTest, int yTest) {
         return xTest >= 0 && xTest < size && yTest >= 0 && yTest < size;
      }
      
      /**
       * Get position letter.
       *
       * @return letter
       */
      public String getLetter() {
         return letter;
      }
      
      /**
       * Get position index.
       *
       * @return index
       */
      public int getIndex() {
         return index;
      }
   }
}