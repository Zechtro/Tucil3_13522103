public class NodeAstar extends Node{
    private String targetWord;

    public NodeAstar(String word, Node parent, String targetWord){
        super(word, parent);
        this.targetWord = targetWord;
    }

    @Override
    public void setPriority(){
        this.priority = this.getDistFromRoot() + this.getCharDifference(targetWord);
    }
}
