import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        if(n1.priority == n2.priority){
            return n1.getWord().compareTo(n2.getWord());
        }else{
            return n1.priority - n2.priority;
        }
    }
}