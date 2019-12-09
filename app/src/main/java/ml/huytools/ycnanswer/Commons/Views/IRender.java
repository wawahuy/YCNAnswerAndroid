package ml.huytools.ycnanswer.Commons.Views;

import android.graphics.Canvas;


/***
 * IRender.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 19/11/2019
 * Update: 21/11/2019
 *
 */
public interface IRender {
    void OnUpdate(int sleep);
    void OnDraw(Canvas canvas);
}
