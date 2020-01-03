package ml.huytools.ycnanswer.Core.Game.Scenes;

import android.graphics.Canvas;
import android.util.Log;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Event.Event;
import ml.huytools.ycnanswer.Core.Game.Event.TouchEvent;
import ml.huytools.ycnanswer.Core.Game.Graphics.Transformable;
import ml.huytools.ycnanswer.Core.Game.IGameObject;
import ml.huytools.ycnanswer.Core.Game.Event.OnTouchListener;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public abstract class Node extends Transformable implements IGameObject {
    private int id;
    private static int IDGlobal = 0;

    protected boolean hasUpdateDraw = false;
    private boolean visible;
    private boolean enableAction;
    private boolean enableTouch;
    private Action action;
    private NodeGroup nodeGroup;

    private OnTouchListener touchListener;
    private TouchEvent.TouchType typeTouch;
    protected Vector2D positionWord;

    private int zOrder;
    private int zOrderNodeInc;
    private Node zOrderNode;

    protected Node(){
        id = IDGlobal++;
        visible = true;
        enableAction = true;
        enableTouch = true;
        zOrder = 0;
        typeTouch = TouchEvent.TouchType.NORMAL;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if(visible){
            hasUpdateDraw = false;
        }
        this.visible = visible;
    }

    public boolean isEnableAction() {
        return enableAction;
    }

    public void setEnableAction(boolean enableAction) {
        this.enableAction = enableAction;
    }

    public Action getAction() {
        return action;
    }

    public void runAction(Action action) {
        this.action = action;
        this.action.setup(this);
    }

    protected void setGroupNode(NodeGroup nodeGroup){
        this.nodeGroup = nodeGroup;
    }

    public NodeGroup getGroupNode(){
        return nodeGroup;
    }

    public int getId() {
        return id;
    }

    public int getZOrder(){
        return zOrderNode == null ? zOrder : zOrderNode.getZOrder() + zOrderNodeInc;
    }

    public void setZOrder(int zIndex){
        zOrderNode = null;
        zOrder = zIndex;
        if(nodeGroup != null){
            nodeGroup.updateZIndexSort();
        }
    }

    public void setZOrderUnder(Node node){
        zOrderNode = node;
        zOrderNodeInc = -1;
        if(nodeGroup != null){
            nodeGroup.updateZIndexSort();
        }
    }

    public void setZOrderUpper(Node node){
        zOrderNode = node;
        zOrderNodeInc = 1;
        if(nodeGroup != null){
            nodeGroup.updateZIndexSort();
        }
    }

    protected boolean testTouchPoint(Vector2D point){
        return false;
    }

    public OnTouchListener getTouchListener() {
        return touchListener;
    }

    /**
     * Kiểm tra sự kiện nhấn
     * Hiện tại chỉ xác thức thông qua khung AABB bao quanh không chihs xác
     * @param touchListener
     */
    public void setTouchListener(OnTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public boolean isEnableTouch() {
        return enableTouch;
    }

    public void setEnableTouch(boolean enableTouch) {
        this.enableTouch = enableTouch;
    }

    /***
     * Các đối tượng sẽ được vẽ kèm theo ma trận biến đổi bởi Transformable
     * Nên vẽ các đối tượng ở góc tọa độ và di chuyển đến trọng tâm
     * Hoặc vẽ các đối tượng sao cho nó được đặt ở trọng tâm
     * Để có thể nhận được các phép quy và kéo giản quanh trọng tâm
     *
     * [x-draw, y-draw] * Matrix Transformable * Camera Matrix
     *
     * @param canvas
     */
    protected abstract void OnDraw(Canvas canvas);

    @Override
    public void draw(Canvas canvas) {
        if(!visible){
            return;
        }
        canvas.save();
        /// [0,0] * [ Matrix Entity (Transformable) ] * [.... Camera Matrix On Scene ....]
        canvas.concat(getMatrix());
        OnDraw(canvas);
        canvas.restore();
        hasUpdateDraw = false;
        positionWord = null;
    }

    @Override
    public boolean update(){
        boolean hasActionUpdate = enableAction && action != null && action.update();
        return hasUpdateDraw || needUpdateMatrix || hasActionUpdate;
    }

    @Override
    public void updateInput(Event event) {
        if(!enableTouch){
            return;
        }
        switch (event.getEventType()){
            case Touch:
                progressionTouch((TouchEvent) event);
                break;
        }
    }


    ///// -------- touches -----------
    protected void computePositionWordIfTouches(Event event){
        if(positionWord == null && event.getEventType() == Event.EventType.Touch){
            positionWord = computePositionWordTrx();
            if( nodeGroup!=null ){
                positionWord = positionWord.add(nodeGroup.positionWord);
            }
        }
    }

    private void progressionTouch(TouchEvent touchEvent){
        if(touchListener == null) {
            return;
        }

        computePositionWordIfTouches(touchEvent);

        if(testTouchPoint(touchEvent.getPoint()) && touchEvent.getType() == TouchEvent.TouchType.END){
            touchListener.OnTouchEnd(this, touchEvent.getPoint().clone());
        }

//        if(testTouchPoint(touchEvent.getPoint()) && touchEvent.getType() != TouchEvent.TouchType.END){
//            if(typeTouch == TouchEvent.TouchType.NORMAL){
//                touchListener.OnTouchBegin(this, touchEvent.getPoint().clone());
//                typeTouch = TouchEvent.TouchType.BEGIN;
//            } else {
//                touchListener.OnTouchMove(this, touchEvent.getPoint().clone());
//                typeTouch = TouchEvent.TouchType.MOVE;
//            }
//            return;
//        }
//
//        if(typeTouch == TouchEvent.TouchType.MOVE || typeTouch == TouchEvent.TouchType.BEGIN){
//            touchListener.OnTouchEnd(this, touchEvent.getPoint().clone());
//        }
//        typeTouch = TouchEvent.TouchType.NORMAL;

    }

}
