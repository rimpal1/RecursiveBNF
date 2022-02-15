public class ExpTreeType {

    private Object root;
    private ExpTreeType leftNode;
    private ExpTreeType rightNode;

    public Object getRoot() {
        return root;
    }
    public void setRoot(Object root) {
        this.root = root;
    }
    public ExpTreeType getLeftNode() {
        return leftNode;
    }
    public void setLeftNode(ExpTreeType leftNode) {
        this.leftNode = leftNode;
    }
    public ExpTreeType getRightNode() {
        return rightNode;
    }
    public void setRightNode(ExpTreeType rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return root + "\t" ;
    }
}