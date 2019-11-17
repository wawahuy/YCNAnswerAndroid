package ml.huytools.ycnanswer.Commons;

import android.content.Context;

public abstract class Presenter<T> {

    protected Context context;
    protected T view;

    public Presenter(Context view){
        this.context = view;
        this.view = (T)view;
    }

    public void Start(){
        this.OnStart();
    }

    protected abstract void OnStart();
}
