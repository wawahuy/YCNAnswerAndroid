package ml.huytools.ycnanswer.Core.Game;

import android.os.SystemClock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameDirector extends Thread {
    private static final GameDirector ourInstance = new GameDirector();

    public static GameDirector getInstance() {
        return ourInstance;
    }

    private List<Renderer> renders;

    /// FPS
    private int framePerSeconds;
    private int sleepOfOnFrame;
    private long timeFrameCurrent;

    private GameDirector() {
        renders = Collections.synchronizedList(new LinkedList<Renderer>());
        setFramePerSecondsMax(60);
        this.setName("Game Director");
        start();
    }

    public void registration(Renderer renderer){
        setFramePerSecondsMax(framePerSeconds);
        synchronized (renders){
            renders.add(renderer);
        }
    }

    public void cancelRegistration(Renderer renderer){
        if(renders.size() <= 1){
            sleepOfOnFrame = 100;
        }
        synchronized (renders) {
            renders.remove(renderer);
        }
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

            synchronized (renders) {
                for (Renderer renderer : renders) {
                    renderer.update();
                }
            }

            synchronized (renders) {
                for (Renderer renderer : renders) {
                    renderer.render();
                }
            }

            time = System.currentTimeMillis() - time;
            SystemClock.sleep(time < sleepOfOnFrame ? sleepOfOnFrame - time : 1);
        }
    }

}
