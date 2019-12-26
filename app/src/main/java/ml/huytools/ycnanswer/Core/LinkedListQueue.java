package ml.huytools.ycnanswer.Core;

import java.util.LinkedList;

public class LinkedListQueue<T> extends LinkedList<T> {
    LinkedList<T> listRemove;
    LinkedList<T> listAdd;
    boolean hasUpdate;

    public LinkedListQueue(){
        listRemove = new LinkedList<>();
        listAdd = new LinkedList<>();
    }

    public void addQueue(T t){
        hasUpdate = true;
        listAdd.add(t);
    }

    public void removeQueue(T t){
        hasUpdate = true;
        listRemove.add(t);
    }

    public void updateQueue(){
        if(!hasUpdate){
            return;
        }

        addAll(listAdd);
        removeAll(listRemove);
        listAdd.clear();
        listRemove.clear();
    }

    public void updateQueue(Callback callback){
        if(!hasUpdate){
            return;
        }

        for(T t:listAdd) callback.OnInsert(t);
        addAll(listAdd);
        removeAll(listRemove);
        listAdd.clear();
        listRemove.clear();
    }

    public interface Callback {
        void OnInsert(Object object);
    }
}
