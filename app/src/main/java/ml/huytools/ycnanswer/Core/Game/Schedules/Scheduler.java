package ml.huytools.ycnanswer.Core.Game.Schedules;

import android.graphics.Matrix;
import android.util.Log;

import ml.huytools.ycnanswer.Core.Game.Commons.Sleeper;
import ml.huytools.ycnanswer.Core.LinkedListQueue;

public class Scheduler extends Thread {
    private final int sleepIdle = 300;
    private static int id = 0;
    private Sleeper sleeper;
    private LinkedListQueue<ScheduleAction> listAction;
    private LinkedListQueue<ScheduleAction> listActionMainThread;

    public Scheduler(){
        sleeper = new Sleeper();
        sleeper.setSleep(sleepIdle);
        initScheduleCBInit();
        setName("Scheduler " + ++id);
        start();
    }


    @Override
    public void run() {
        // -------- Thread N ---------
        while (true){
            sleeper.reset();
            for (ScheduleAction scheduleAction : listAction) {
                if (!scheduleAction.run()) {
                    listAction.removeQueue(scheduleAction);
                }
            }
            listAction.updateQueue();
            sleeper.sleep();
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
        updateSleepWithInterval(scheduleAction.getInterval());
        listAction.addQueue(scheduleAction);
        scheduleAction.init(ScheduleAction.PositionThread.CURRENT);
    }

    public void scheduleOnThreadGame(ScheduleAction scheduleAction){
        updateSleepWithInterval(scheduleAction.getInterval());
        listActionMainThread.addQueue(scheduleAction);
        scheduleAction.init(ScheduleAction.PositionThread.MAIN);
    }

    public void remove(ScheduleAction scheduleAction){
        switch (scheduleAction.getSchedulePositionThread()){
            case MAIN:
                listActionMainThread.removeQueue(scheduleAction);
                break;

            case CURRENT:
                if(listAction.size() == 1){
                    sleeper.setSleep(sleepIdle);
                    ///
                    Log.v("Log", "Scheduler " + id + " sleep = " + sleepIdle);
                }
                listAction.removeQueue(scheduleAction);
                break;
        }
    }

    private void updateSleepWithInterval(int interval){
        final int divInterval = 4;
        long intervalMin = Math.min(interval, sleeper.getSleep()*divInterval) / divInterval;
        sleeper.setSleep(intervalMin);
        ///
        Log.v("Log", "Scheduler " + id + " sleep = " + intervalMin);
    }

    private void initScheduleCBInit(){
        listAction = new LinkedListQueue<>();
        listActionMainThread = new LinkedListQueue<>();
    }

}
