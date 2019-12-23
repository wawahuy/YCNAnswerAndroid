package ml.huytools.ycnanswer.Commons.Graphics.Interface;

import android.graphics.Canvas;

import ml.huytools.ycnanswer.Commons.Graphics.Transformable;

/**
 * Interface IDrawable là cơ sở để vẽ
 */
public interface IDrawable {
    /**
     *
     * @param canvas Canvas được vẽ
     */
    void draw(Canvas canvas);

    /**
     *
     * @param canvas Canvas được vẽ
     * @param transformable Biến đổi
     */
    void draw(Canvas canvas, final Transformable transformable);
}
