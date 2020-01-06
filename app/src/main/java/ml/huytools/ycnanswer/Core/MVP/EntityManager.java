package ml.huytools.ycnanswer.Core.MVP;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Iterator;
import java.util.LinkedList;

/***
 * EntityManager.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/11/2019
 * Update: 20/11/2019
 *
 */
public class EntityManager<T extends Entity> extends LinkedList<T> {


    public EntityManager(){

    }


    public String toJson(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");

        Iterator<T> iterator = iterator();
        while (iterator.hasNext()){
            stringBuilder.append(iterator.next().toJson());
            stringBuilder.append(",");
        }

        return stringBuilder.substring(0, stringBuilder.length()-1) + "]";
    }

    public static<T extends Entity> EntityManager<T> ParseJSON(Class<T> clazz, String arrayJson){
        try {
            JSONArray jsonArray = new JSONArray(arrayJson);
            return ParseJSON(clazz, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static<T extends Entity> EntityManager<T> ParseJSON(Class<T> clazz, JSONArray arrayJson){
        EntityManager<T> modelManager = new EntityManager<>();
        modelManager.set(clazz, arrayJson);
        return modelManager;
    }


    public void set(Class<T> clazz, JSONArray arrayJson){
        int size = arrayJson.length();
        for (int i=0; i<size; i++){
            try {
                add(Entity.ParseJson(clazz, arrayJson.get(i).toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
