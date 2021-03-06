package ml.huytools.ycnanswer.Core.Game.Scenes;

import android.graphics.Canvas;

import java.util.Comparator;
import java.util.List;

import ml.huytools.ycnanswer.Core.Exceptions.NodeInAnotherNodeGroupException;
import ml.huytools.ycnanswer.Core.Game.Event.Event;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class NodeGroup extends Node {
    private LinkedListQueue<Node> nodes;
    private boolean hasUpdateSort;

    public NodeGroup(){
        hasUpdateSort = false;
        nodes = new LinkedListQueue<>();
    }

    private void updatePositionNodeByZOrder(){
        nodes.sortQueue(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return node1.getZOrder() < node2.getZOrder() ? -1 :
                        (node1.getZOrder() > node2.getZOrder() ? 1 : 0);
            }
        });
    }

    public void add(Node node){
        if(node.getGroupNode() != null){
            throw new NodeInAnotherNodeGroupException();
        }

        hasUpdateSort = true;
        node.setGroupNode(this);
        nodes.addQueue(node);
    }

    public void remove(Node node){
        if(node.getGroupNode() == null){
            return;
        }

        hasUpdateSort = true;
        node.setGroupNode(null);
        nodes.removeQueue(node);
    }



    public void clear(){
        nodes.clearQueue();
    }

    public int size(){
        return nodes.size();
    }

    public List<Node> getListNode(){
        return nodes;
    }

    public void updateZIndexSort(){
        hasUpdateSort = true;
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        for(Node node:nodes){
            node.draw(canvas);
        }
    }

    @Override
    public boolean update() {
        /// Update
        boolean hasUpdate = super.update();
        for(Node node:nodes){
            hasUpdate = node.update() || hasUpdate;
        }

        /// Sort ZIndex
        if(hasUpdateSort){
            updatePositionNodeByZOrder();
            hasUpdateSort = false;
        }

        /// Update LinkedList Node
        nodes.updateQueue();

        return hasUpdate;
    }

    @Override
    public void updateInput(Event event) {
        if(!isEnableTouch()){
            return;
        }

        super.computePositionWordIfTouches(event);
        super.updateInput(event);
        for(Node node:nodes){
            node.updateInput(event);
        }
    }
}
