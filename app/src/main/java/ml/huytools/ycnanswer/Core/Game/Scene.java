package ml.huytools.ycnanswer.Core.Game;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.Game.Actions.Action;
import ml.huytools.ycnanswer.Core.Game.Graphics.Transformable;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleAction;
import ml.huytools.ycnanswer.Core.Game.Schedules.ScheduleCallback;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;

public class Scene {
    private List<Node> nodes;
    private Camera camera;
    private Scheduler scheduler;

    /// scene, scheduler, actionspawn, gamedirector sử dụng Collections.Sync
    /// ____,

    public Scene(){
        nodes = Collections.synchronizedList(new LinkedList<Node>());
        camera = new Camera();
        scheduler = new Scheduler(this);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void add(final Node node){
        node.scene = this;
//        synchronized (nodes) {
//            nodes.add(node);
//        }

        scheduler.scheduleOnThreadGame(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnUpdate(float dt) {
                nodes.add(node);
            }
        }, 0));
    }

    public void remove(final Node node){
        node.scene = null;
//        synchronized (nodes) {
//            nodes.remove(node);
//        }
        scheduler.scheduleOnThreadGame(ScheduleAction.One(new ScheduleCallback() {
            @Override
            public void OnUpdate(float dt) {
                nodes.remove(node);
            }
        }, 0));
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void render(Canvas canvas) {
        /// clear
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        /// Thêm ma trận nhìn vào shader
        /// [x, y] * ..... *View
        camera.applyToCanvas(canvas);

        /// Vé các node
        /// Các node không thuộc camera không được cắt khổi kết xuất
        /// Giai đoạn tiếp theo cần xây dựng Tree có thể AABB Dynamic Tree để performance cao hơn
        /// ---- Update ----
        synchronized (nodes) {
            for (Node node : nodes) {
                node.draw(canvas);
            }
        }
    }

    public boolean update(){
        boolean hasChange = scheduler.update();
        synchronized (nodes) {
            for (Node node : nodes) {
                hasChange = node.update() || hasChange;
            }
        }
        return hasChange;
    }


    /***
     * Đối tượng trong một Scene
     *
     */
    public static abstract class Node extends Transformable {
        private boolean visible;
        private boolean hasDraw = false;
        private Action action;
        private Scene scene;

        protected Node(){
            visible = true;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            if(visible){
                hasDraw = false;
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

        public void draw(Canvas canvas) {
            if(!visible){
                return;
            }
            canvas.save();
            /// [0,0] * [ Matrix Model (Transformable) ] * [.... Camera Matrix On Scene ....]
            canvas.concat(getMatrix());
            OnDraw(canvas);
            canvas.restore();
            hasDraw = true;
        }

        public void draw(Canvas canvas, Transformable transformable) {
            if(!visible){
                return;
            }
            canvas.save();
            /// ------- Update --------------
            canvas.restore();
        }

        public boolean update(){
            boolean hasActionUpdate = action != null && action.update();
            return needUpdateMatrix || !hasDraw || hasActionUpdate;
        }
    }


}
