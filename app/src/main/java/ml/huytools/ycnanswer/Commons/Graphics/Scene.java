package ml.huytools.ycnanswer.Commons.Graphics;

import android.graphics.Camera;
import android.graphics.Canvas;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Commons.Graphics.Interface.IDrawable;

public class Scene {
    private LinkedList<Node> nodes;
    private Camera camera;

    public Scene(){
        nodes = new LinkedList<>();
        camera = new Camera();
    }

    public void render(Canvas canvas){
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

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void add(Node node){
        nodes.add(node);
    }

    public void remove(Node node){
        nodes.remove(node);
    }

    /***
     * Đối tượng trong một Scene
     *
     */
    public static abstract class Node extends Transformable implements IDrawable {
        private boolean visible;

        Node(){
            visible = true;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
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
        public abstract void OnDraw(Canvas canvas);

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
        }

        @Override
        public void draw(Canvas canvas, Transformable transformable) {
            if(!visible){
                return;
            }
            canvas.save();
            /// ------- Update --------------
            canvas.restore();
        }
    }


}
