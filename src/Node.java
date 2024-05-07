import java.util.ArrayList;
import java.util.List;

public class Node {
    private String word;
    private Node parent;
    protected int priority;

    public Node(String word, Node parent){
        this.word = word;
        this.parent = parent;
    }

    protected int getDistFromRoot(){
        int dist = 0;
        Node currParent = this.parent;
        while(currParent != null){
            dist++;
            currParent = currParent.getParent();
        }
        return dist;
    }

    protected int getCharDifference(String targetWord){
        int countDifference = 0;
        for(int i =0;i < targetWord.length();i++){
            if(this.word.charAt(i) != targetWord.charAt(i)){
                countDifference++;
            }
        }
        return countDifference;
    }

    public String getWord(){
        return this.word;
    }

    public Node getParent(){
        return this.parent;
    }

    public int getPriority(){
        return this.priority;
    }

    public void setPriority(){
        this.priority = 0;
    };

    public List<String> getResultList(){
        List<String> listOut = new ArrayList<>();
        listOut.add(this.word);
        Node currParent = this.parent;
        while(currParent != null){
            listOut.add(currParent.word);
            currParent = currParent.getParent();
        }
        return listOut;
    }
}