package ml.huytools.ycnanswer.Core.Exceptions;

public class NodeInAnotherNodeGroupException extends RuntimeException {
    public NodeInAnotherNodeGroupException() {
        super("Node in another NodeGroup, please remove before!");
    }
}
