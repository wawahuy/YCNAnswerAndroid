package ml.huytools.ycnanswer.Core.Game.Commons;

import android.os.SystemClock;

public class Sleeper {
    long timeStart;
    long dt;
    long sleep;

    public void reset(){
        timeStart = System.currentTimeMillis();
    }

    public long getDT(){
        return System.currentTimeMillis() - timeStart;
    }

    public long getSleep() {
        return sleep;
    }

    public void setSleep(long sleep) {
        this.sleep = sleep;
    }

    public void sleep(){
        dt = getDT();
        SystemClock.sleep(dt < sleep ? sleep - dt : 1);
    }
}
