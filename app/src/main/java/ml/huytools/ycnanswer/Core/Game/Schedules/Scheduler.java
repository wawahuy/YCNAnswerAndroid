package ml.huytools.ycnanswer.Core.Game.Schedules;

import android.os.SystemClock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.Game.Scene;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class Scheduler extends Thread {
    private Scene scene;
    private LinkedListQueue<ScheduleAction> listAction;
    private LinkedListQueue<ScheduleAction> listActionMainThread;

    public Scheduler(){
        this.scene = null;
        initScheduleCBInit();
        start();
    }

    public Scheduler(Scene scene){
        this.scene = scene;
        initScheduleCBInit();
        start();
    }

    @Override
    public void run() {
        // -------- Thread N ---------
        while (true){
            for (ScheduleAction scheduleAction : listAction) {
                if (!scheduleAction.run()) {
                    listAction.removeQueue(scheduleAction);
                }
            }
            listAction.updateQueue();
            SystemClock.sleep(1);
        }
    }

    public boolean update(){
        // ------- Main Thread -----
        for (ScheduleAction scheduleAction : listActionMainThread) {
            if (!scheduleAction.run()) {
                listActionMainThread.removeQueue(scheduleAction);
            }
        }
        listActionMainThread.updateQueue();
        return false;
    }

    public void schedule(ScheduleAction scheduleAction){
        listAction.addQueue(scheduleAction);
        scheduleAction.init();
    }

    public void scheduleOnThreadGame(ScheduleAction scheduleAction){
        listActionMainThread.addQueue(scheduleAction);
        scheduleAction.init();
    }

    public void remove(ScheduleAction scheduleAction){
        /// -------- update ---------
    }

    public void initScheduleCBInit(){
        listAction = new LinkedListQueue<>();
        listActionMainThread = new LinkedListQueue<>();
    }

}
