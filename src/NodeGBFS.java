public class NodeGBFS extends Node{
    private String targetWord;

    public NodeGBFS(String word, Node parent, String targetWord){
        super(word, parent);
        this.targetWord = targetWord;
    }

    @Override
    public void setPriority(){
        this.priority = this.getCharDifference(this.targetWord);
    }
}