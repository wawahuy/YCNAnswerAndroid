package ml.huytools.ycnanswer.Commons;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;

/***
 * Presenter.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/11/2019
 * Update: 20/11/2019
 *
 */
public abstract class Presenter<T> {

    protected WeakReference<Activity> activity;
    protected T view;

    protected Presenter(){
    }

    protected Presenter(Activity activity){
        set(activity);
    }

    public void set(Activity activity){
        this.activity = new WeakReference<>(activity);
        this.view = (T)activity;
    }

    public void Start(){
        this.OnStart();
    }

    protected abstract void OnStart();

    public static<T, V extends Presenter<T>> V of(Activity activity, Class<V> clazz){
        Log.v("Log", "Presenter Create On Activity: "+activity.toString());

        try {
            V presenter =  clazz.newInstance();
            presenter.set(activity);
            return presenter;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
