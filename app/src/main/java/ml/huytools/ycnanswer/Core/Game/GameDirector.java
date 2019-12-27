package ml.huytools.ycnanswer.Core.Game;

import android.os.SystemClock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class GameDirector extends Thread {
    private static final GameDirector ourInstance = new GameDirector();

    public static GameDirector getInstance() {
        return ourInstance;
    }

    private LinkedListQueue<Renderer> renders;

    /// FPS
    private int framePerSeconds;
    private int sleepOfOnFrame;
    private long timeFrameCurrent;

    private GameDirector() {
        renders = new LinkedListQueue<>();
        setFramePerSecondsMax(60);
        this.setName("Game Director");
        start();
    }

    public void registration(Renderer renderer){
        setFramePerSecondsMax(framePerSeconds);
        //synchronized (renders){
        renders.addQueue(renderer);
        //}
    }

    public void cancelRegistration(Renderer renderer){
        if(renders.size() <= 1){
            sleepOfOnFrame = 100;
        }
        // synchronized (renders) {
        renders.removeQueue(renderer);
        //}
    }

    public void setFramePerSecondsMax(int frame){
        framePerSeconds = frame;
        sleepOfOnFrame = 1000/framePerSeconds;
    }

    @Override
    public void run() {
        long time;

        while (true){
            time = System.currentTimeMillis();

            // synchronized (renders) {
            for (Renderer renderer : renders) {
                renderer.update();
            }
            //}

            //synchronized (renders) {
            for (Renderer renderer : renders) {
                renderer.render();
            }
            //}

            renders.updateQueue();

            time = System.currentTimeMillis() - time;
            SystemClock.sleep(time < sleepOfOnFrame ? sleepOfOnFrame - time : 1);
        }
    }

}
