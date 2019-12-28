package ml.huytools.ycnanswer.Core.Game;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Graphics.Transformable;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class Scene implements IGameObject {
    private LinkedListQueue<Node> nodes;
    private Camera camera;
    private Scheduler scheduler;
    boolean hasUpdateSort;

    public Scene(){
        nodes = new LinkedListQueue();
        camera = new Camera();
        scheduler = new Scheduler(this);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void add(Node node){
        hasUpdateSort = true;
        node.scene = this;
        nodes.addQueue(node);
    }

    public void remove(Node node){
        hasUpdateSort = true;
        node.scene = null;
        nodes.removeQueue(node);
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

    public int getSizeNode(){
        return nodes.size();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void draw(Canvas canvas) {
        /// clear
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        /// Thêm ma trận nhìn vào shader
        /// [x, y] * ..... *View
        camera.applyToCanvas(canvas);

        /// Vé các node
        /// Các node không thuộc camera không được cắt khổi kết xuất
        /// Giai đoạn tiếp theo cần xây dựng Tree có thể AABB Dynamic Tree để performance cao hơn
        /// ---- Update ----
        for (Node node : nodes) {
            node.draw(canvas);
        }
    }

    @Override
    public boolean update(){
        /// Update logic
        boolean hasChange = scheduler.update();
        for (Node node : nodes) {
            hasChange = node.update() || hasChange;
        }

        /// Update ZOrder
        if(hasUpdateSort){
            updatePositionNodeByZOrder();
            hasUpdateSort = false;
        }

        /// Dequeue
        nodes.updateQueue();

        return hasChange;
    }


    /***
     * Đối tượng trong một Scene
     *
     */
    public static abstract class Node extends Transformable implements IGameObject {
        protected boolean hasUpdateDraw = false;
        private boolean visible;
        private Action action;
        private Scene scene;

        private int zOrder;
        private int zOrderNodeInc;
        private Node zOrderNode;

        protected Node(){
            visible = true;
            zOrder = 0;
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

        public Action getAction() {
            return action;
        }

        public void runAction(Action action) {
            this.action = action;
            this.action.setup(this);
        }

        public Scene getSceneAttach() {
            return scene;
        }

        public int getZOrder(){
            return zOrderNode == null ? zOrder : zOrderNode.getZOrder() + zOrderNodeInc;
        }

        public void setZOrder(int zIndex){
            zOrderNode = null;
            zOrder = zIndex;
            if(scene != null){
                scene.hasUpdateSort = true;
            }
        }

        public void setZOrderUnder(Node node){
            zOrderNode = node;
            zOrderNodeInc = -1;
            if(scene != null){
                scene.hasUpdateSort = true;
            }
        }

        public void setZOrderUpper(Node node){
            zOrderNode = node;
            zOrderNodeInc = 1;
            if(scene != null){
                scene.hasUpdateSort = true;
            }
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
            /// [0,0] * [ Matrix Model (Transformable) ] * [.... Camera Matrix On Scene ....]
            canvas.concat(getMatrix());
            OnDraw(canvas);
            canvas.restore();
            hasUpdateDraw = false;
        }

//        public void draw(Canvas canvas, Transformable transformable) {
//            if(!visible){
//                return;
//            }
//            canvas.save();
//            /// ------- Update --------------
//            canvas.restore();
//        }

        @Override
        public boolean update(){
            boolean hasActionUpdate = action != null && action.update();
            return needUpdateMatrix || hasUpdateDraw || hasActionUpdate;
        }
    }


}
