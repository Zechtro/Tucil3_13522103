import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
      WordLadder game = new WordLadder();
      String startWord;
      String endWord;
      int algoNum;
      String algo;
      Runtime runtime = Runtime.getRuntime();
      while(true){
        System.out.print("Enter start word  : ");
        startWord = scanner.nextLine().trim().toUpperCase().replace(" ", "");
        // System.out.println(startWord);
        
        while (!game.isInVocabulary(startWord)) {
          System.out.println("! Start word harus terdapat pada English Vocabulary !");
          System.out.print("Enter start word  : ");
          startWord = scanner.nextLine().trim().toUpperCase().replace(" ", "");
          // System.out.println(startWord);
        }
        
        System.out.print("Enter target word : ");
        endWord = scanner.nextLine().trim().toUpperCase().replace(" ", "");
        // System.out.println(endWord);
        
        while (!game.isInVocabulary(endWord)) {
          System.out.println("! Target word harus terdapat pada English Vocabulary !");
          System.out.print("Enter target word : ");
          endWord = scanner.nextLine().trim().toUpperCase().replace(" ", "");
          // System.out.println(endWord);
        }

        while(startWord.length() != endWord.length()){
          System.out.println("Panjang start word harus sama dengan target Word!");
          System.out.print("Enter start word  : ");
          startWord = scanner.nextLine().trim().toUpperCase().replace(" ", "");
          // System.out.println(startWord);
          
          System.out.print("Enter target word : ");
          endWord = scanner.nextLine().trim().toUpperCase().replace(" ", "");
          // System.out.println(endWord);
        }

        System.out.println("Choose Algorithm: ");
        System.out.println("1. Uniform Cost First (UCS)");
        System.out.println("2. Greedy Best First Search");
        System.out.println("3. A*");
        System.out.print(">> ");
        algoNum = scanner.nextInt();
        scanner.nextLine();
        while (algoNum != 1 && algoNum != 2 && algoNum != 3) {
          System.out.print(">> ");
          algoNum = scanner.nextInt();
          scanner.nextLine();
        } 

        if(algoNum == 1){
          algo = "UCS";
        }else if(algoNum == 2){
          algo = "GBFS";
        }else{
          algo = "A*";
        }

        double startTime = System.currentTimeMillis();

        game.initiateGame(startWord, endWord, algo);
        game.process();

        System.out.println("Result Path:");
        game.printResultPath();

        double endTime = System.currentTimeMillis();

        System.out.println("Node checked: "+String.valueOf(game.getNodeChecked()));
        double executionTime = endTime - startTime;
        System.out.println("Executed in: " + String.valueOf(executionTime) + " ms");

        // In bytes
        long usedMemory = runtime.totalMemory() - runtime.freeMemory(); 

        // Getting values in Megabytes (MB)
        long usedMemoryMB = usedMemory / (1024 * 1024);
        System.out.println("Used Memory: " + usedMemoryMB + " MB");
        
        System.out.println("Play Again? (yes/no)");
        String playAgain = scanner.nextLine().trim();
        System.out.println(playAgain);
        while (!playAgain.equals("yes") && !playAgain.equals("no")) {
          System.out.println("Play Again? (yes/no)");
          playAgain = scanner.nextLine().trim().replace(" ", "");
        }
        if(playAgain.equals("no")){
          break;
        }
      }
      scanner.close();
    }
  }
