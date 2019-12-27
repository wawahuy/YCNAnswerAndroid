package ml.huytools.ycnanswer.Core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LinkedListQueue<T> extends LinkedList<T> {
    List<T> listRemove;
    List<T> listAdd;
    boolean hasUpdate;

    public LinkedListQueue(){
        listRemove = Collections.synchronizedList(new LinkedList<T>());
        listAdd =Collections.synchronizedList(new LinkedList<T>());
    }

    public void addQueue(T t){
        hasUpdate = true;
        synchronized (listAdd) {
            listAdd.add(t);
        }
    }

    public void removeQueue(T t){
        hasUpdate = true;
        synchronized (listRemove) {
            listRemove.add(t);
        }
    }

    public void updateQueue(){
        if(!hasUpdate){
            return;
        }

        synchronized (listAdd){
            addAll(listAdd);
            listAdd.clear();
        }

        synchronized (listAdd) {
            removeAll(listRemove);
            listRemove.clear();
        }
    }
}
