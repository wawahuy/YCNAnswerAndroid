package ml.huytools.ycnanswer.Core.Game.Actions;

public class ActionRepeatForever extends ActionRepeat {

    public static ActionRepeatForever create(Action action){
        ActionRepeatForever actionRepeatForever = new ActionRepeatForever();
        actionRepeatForever.action = action;
        return actionRepeatForever;
    }

    @Override
    public void progressFinish() {
        ///super.progressFinish();
        /// Không xữ lí kết thúc
    }
}
