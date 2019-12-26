package ml.huytools.ycnanswer.Core.Game.Schedules;

import android.os.SystemClock;

import ml.huytools.ycnanswer.Core.Game.Scene;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class Scheduler extends Thread {
    private Scene scene;
    private LinkedListQueue<ScheduleAction> listAction;
    private LinkedListQueue<ScheduleAction> listActionMainThread;
    private LinkedListQueue.Callback callbackInitScheduleAction;

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
            listAction.updateQueue(callbackInitScheduleAction);
            for(ScheduleAction scheduleAction:listAction){
                if(!scheduleAction.run()){
                    listAction.removeQueue(scheduleAction);
                }
            }
            SystemClock.sleep(1);
        }
    }

    public boolean update(){
        // ------- Main Thread -----
        listActionMainThread.updateQueue(callbackInitScheduleAction);
        for(ScheduleAction scheduleAction:listActionMainThread){
            if(!scheduleAction.run()){
                listActionMainThread.removeQueue(scheduleAction);
            }
        }
        return false;
    }

    public void schedule(ScheduleAction scheduleAction){
        listAction.addQueue(scheduleAction);
    }

    public void scheduleOnThreadGame(ScheduleAction scheduleAction){
        listActionMainThread.addQueue(scheduleAction);
    }

    public void remove(ScheduleAction scheduleAction){
        listAction.removeQueue(scheduleAction);
        listActionMainThread.removeQueue(scheduleAction);
    }

    public void initScheduleCBInit(){
        listAction = new LinkedListQueue<>();
        listActionMainThread = new LinkedListQueue<>();
        callbackInitScheduleAction = new LinkedListQueue.Callback() {
            @Override
            public void OnInsert(Object object) {
                ((ScheduleAction)object).init();
            }
        };
    }

}
