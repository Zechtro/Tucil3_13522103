public class NodeUCS extends Node{
    public NodeUCS(String word, Node parent){
        super(word, parent);
    }

    @Override
    public void setPriority(){
        this.priority = this.getDistFromRoot();
    }
}