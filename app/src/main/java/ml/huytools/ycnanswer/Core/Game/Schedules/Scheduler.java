package ml.huytools.ycnanswer.Core.Game.Schedules;

import android.os.SystemClock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.Game.Scene;

public class Scheduler extends Thread {
    private Scene scene;
    private List<ScheduleAction> listAction;
    private List<ScheduleAction> listActionMainThread;

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
            synchronized (listAction) {
                for (ScheduleAction scheduleAction : listAction) {
                    if (!scheduleAction.run()) {
                        listAction.remove(scheduleAction);
                    }
                }
            }
            SystemClock.sleep(1);
        }
    }

    public boolean update(){
        // ------- Main Thread -----
        synchronized (listActionMainThread) {
            for (ScheduleAction scheduleAction : listActionMainThread) {
                if (!scheduleAction.run()) {
                    listActionMainThread.remove(scheduleAction);
                }
            }
        }
        return false;
    }

    public void schedule(ScheduleAction scheduleAction){
        synchronized (listAction) {
            listAction.add(scheduleAction);
            scheduleAction.init();
        }
    }

    public void scheduleOnThreadGame(ScheduleAction scheduleAction){
        synchronized (listActionMainThread) {
            listActionMainThread.add(scheduleAction);
            scheduleAction.init();
        }
    }

    public void remove(ScheduleAction scheduleAction){
        /// -------- update ---------
    }

    public void initScheduleCBInit(){
        listAction = Collections.synchronizedList(new LinkedList<ScheduleAction>());
        listActionMainThread = Collections.synchronizedList(new LinkedList<ScheduleAction>());
    }

}
