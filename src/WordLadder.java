import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class WordLadder {
    private Map<Integer, Map<String, Boolean>> englishVocabulary;
    private String alphaChars;
    private Map<String, Boolean> visitedNode;
    private PriorityQueue<Node> prioQueue;
    private List<String> resultList;
    private String startWord;
    private String targetWord;
    private String algorithm;   // "UCS" / "GBFS" / "A*""
    private boolean isFound;
    private Node targetFoundNode;
    private int nodeChecked;

    public WordLadder(){
        this.isFound = false;
        this.visitedNode = new HashMap<>();
        this.englishVocabulary = new HashMap<>();
        this.alphaChars = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        this.prioQueue = new PriorityQueue<>(new NodeComparator());
        try{
            loadEnglishVocabulary("EnglishVocabulary.txt");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        this.targetFoundNode = null;
        this.resultList = new ArrayList<>();
    }

    public void initiateGame(String startWord, String endWord, String algorithm){
        ResetData();
        this.startWord = startWord;
        this.targetWord = endWord;
        this.algorithm = algorithm;
    }

    private void loadEnglishVocabulary(String filename) throws IOException{
        String cwdPath = System.getProperty("user.dir");
        File cwdAbs = new File(cwdPath);
        File parentDir = cwdAbs.getParentFile();
        String parent = parentDir.getAbsolutePath();
        try (BufferedReader br = new BufferedReader(new FileReader(parent+"/test/"+filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim().toUpperCase();
                int wordLen = word.length();
                if(wordLen > 0){
                    if(englishVocabulary.get(wordLen) == null){
                        englishVocabulary.put(wordLen,new HashMap<>());
                    }
                    englishVocabulary.get(wordLen).put(word, true);
                }
            }
        }

    }

    public boolean isInVocabulary(String word){
        if(englishVocabulary.get(word.length()) == null){
            return false;
        }else if(englishVocabulary.get(word.length()).get(word)!=null){
            return englishVocabulary.get(word.length()).get(word);
        }else{
            return false;
        }
    }

    private void enqueueToPrioQueue(Node n){
        this.prioQueue.add(n);
        if(this.algorithm.equals("GBFS")){
            Node temp = dequeueFromPrioQueue();
            prioQueue.clear();
            prioQueue.add(temp);
        }
    }

    private Node dequeueFromPrioQueue(){
        return this.prioQueue.remove();
    }

    private Node createNodeBasedOnAlgorithm(String word, Node parentNode){
        Node newNode;
        if(this.algorithm.equals("UCS")){
            newNode = new NodeUCS(word, parentNode);
        }else if(this.algorithm.equals("GBFS")){
            newNode = new NodeGBFS(word, parentNode, this.targetWord);
        }else if(this.algorithm.equals("A*")) {
            newNode = new NodeAstar(word, parentNode, this.targetWord);
        }else{
            newNode = null;
        }
        return newNode;
    }

    private boolean isVisited(String tempWord){
        boolean ret;
        if(visitedNode.get(tempWord)!=null){
            ret = visitedNode.get(tempWord);
        }else{
            ret = false;
        }
        return ret;
    }

    private void generatePossibleChildNode(Node node){
        String word = node.getWord();
        visitedNode.put(word, true);
        int lenWord = word.length();
        for(int i=0;i < lenWord;i++){
            for(int j=0;j < alphaChars.length();j++){
                String tempWord = word.substring(0, i) + alphaChars.charAt(j) + word.substring(i+1, lenWord);
                if(isInVocabulary(tempWord) && !isVisited(tempWord)){
                    visitedNode.put(tempWord, true);
                    Node newNode = createNodeBasedOnAlgorithm(tempWord, node);
                    newNode.setPriority();
                    this.nodeChecked++;
                    if(tempWord.equals(this.targetWord)){
                        this.isFound = true;
                        this.targetFoundNode = newNode;
                        break;
                    }else{
                        enqueueToPrioQueue(newNode);
                    }
                }
            }
            if(this.isFound){
                break;
            }
        }
    }

    public int getNodeChecked(){
        return this.nodeChecked;
    }

    public List<String> getResultList(){
        List<String> reversedList = new ArrayList<>();
        for(int i=this.resultList.size()-1;i >= 0;i--){
            reversedList.add(this.resultList.get(i));
        }
        return reversedList;
    }

    public void process(){
        this.nodeChecked++;
        if(startWord.equals(targetWord)){
            this.resultList.add(targetWord);
        }else{
            Node rootNode = createNodeBasedOnAlgorithm(startWord, null);
            rootNode.setPriority();
            enqueueToPrioQueue(rootNode);
            while (!prioQueue.isEmpty() && !isFound) {
                generatePossibleChildNode(dequeueFromPrioQueue());
            };
            if(isFound){
                this.resultList = this.targetFoundNode.getResultList();
            }else{
                this.resultList.add("No Possible Path :(");
            }
        }
    }

    public void printResultPath(){
        if(this.resultList.get(0).equals("No Possible Path :(")){
            System.out.println(this.resultList.get(0));
        }else{
            for(int i=this.resultList.size()-1;i >= 0;i--) {
                System.out.print(String.valueOf(this.resultList.size() - i) + ". ");
                System.out.println(this.resultList.get(i));
            }
        }
    }

    public void ResetData(){
        this.visitedNode.clear();
        this.prioQueue.clear();
        this.isFound = false;
        this.targetFoundNode = null;
        this.resultList.clear();
        this.nodeChecked = 0;
    }
}
