package ml.huytools.ycnanswer.Core.Game;

import android.os.SystemClock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.Game.Commons.Sleeper;
import ml.huytools.ycnanswer.Core.Game.Schedules.Scheduler;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class GameDirector extends Thread {
    private static final GameDirector ourInstance = new GameDirector();

    public static GameDirector getInstance() {
        return ourInstance;
    }

    private LinkedListQueue<Renderer> renders;
    private int framePerSeconds;
    private Scheduler scheduler;
    private Sleeper sleeper;

    private GameDirector() {
        renders = new LinkedListQueue<>();
        scheduler = new Scheduler();
        sleeper = new Sleeper();
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
            sleeper.setSleep(100);
        }
        // synchronized (renders) {
        renders.removeQueue(renderer);
        //}
    }

    public void setFramePerSecondsMax(int frame){
        framePerSeconds = frame;
        sleeper.setSleep(1000/framePerSeconds);
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void run() {

        while (true){
            sleeper.reset();

            /// Logic
            for (Renderer renderer : renders) {
                renderer.update();
            }

            /// Render
            for (Renderer renderer : renders) {
                renderer.render();
            }

            /// Scheduler Logic
            scheduler.update();

            /// Update linkedList
            renders.updateQueue();

            sleeper.sleep();
        }
    }

}
