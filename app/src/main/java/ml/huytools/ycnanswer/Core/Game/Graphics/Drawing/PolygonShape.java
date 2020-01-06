package ml.huytools.ycnanswer.Core.Game.Graphics.Drawing;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.LinkedListQueue;
import ml.huytools.ycnanswer.Core.Math.Vector2D;

public class PolygonShape extends Drawable {
    private Path path;
    private List<Vector2D> points;
    private boolean centerMinMax;

    public PolygonShape(){
        super();
        path = new Path();
        points = new LinkedList<>();
    }

    public PolygonShape(List<Vector2D> points){
        this();
        setPoints(points);
    }

    public List<Vector2D> getPoints() {
        return points;
    }

    public void setPoints(List<Vector2D> points) {
        this.points.clear();
        path = new Path();
        for(Vector2D p:points){
            addToPath(p.x, p.y);
            this.points.add(p);
        }
        computeOrigin();
        hasUpdateDraw = true;
    }

    public void addPoint(Vector2D p){
        addToPath(p.x, p.y);
        points.add(p);
        computeOrigin();
        hasUpdateDraw = true;
    }

    private void addToPath(float x, float y){
        if(points.size() == 0){
            path.moveTo(x, y);
        }
        else {
            path.lineTo(x, y);
        }
        hasUpdateDraw = true;
    }

    public void clearPoint(){
        points.clear();
        path = new Path();
        hasUpdateDraw = true;
    }

    public boolean isCenterMinMax() {
        return centerMinMax;
    }

    public void centerMinMax(boolean centerMinMax) {
        this.centerMinMax = centerMinMax;
    }

    /**
     * Tinh điểm giữ của AABB quanh Polygon
     * Đây không phải là đi tìm trọng tậm thực tế
     */
    private void computeOrigin(){
        if(centerMinMax){

            if(points.size() == 0){
                return;
            }

            Vector2D min = null;
            Vector2D max = null;
            for(Vector2D p:points){
                if(min == null){
                    min = p.clone();
                    max = p.clone();
                    continue;
                }

                if(p.x < min.x) min.x = p.x;
                if(p.y < min.y) min.y = p.y;
                if(p.y > max.y) max.y = p.y;
                if(p.y > max.y) max.y = p.y;
            }

            Vector2D size = max.sub(min);
            setOrigin(size.div(2));
            hasUpdateDraw = true;
        }
    }


    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
