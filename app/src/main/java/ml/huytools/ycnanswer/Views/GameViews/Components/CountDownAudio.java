package ml.huytools.ycnanswer.Views.GameViews.Components;

import android.content.Context;
import android.media.MediaPlayer;

/***
 * CountDownAudio.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 19/11/2019
 * Update: 20/11/2019
 *
 *
 */
public class CountDownAudio implements CountDown.Callback {

    MediaPlayer mpTimeOut;

    public void setAudioTimeout(MediaPlayer timeout){
        mpTimeOut = timeout;
    }

    @Override
    public void OnEnd() {
        mpTimeOut.start();
    }

    @Override
    public void OnTick(int cur) {
    }
}
