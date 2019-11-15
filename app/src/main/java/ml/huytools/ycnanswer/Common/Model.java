package ml.huytools.ycnanswer.Common;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public abstract class Model {

    private final String[] EXCEPT_SYS_ATTR = {"serialVersionUID", "$change"};
    private final String[] m_except_attribute;

    public Model(){
        m_except_attribute = GetAttributeExcept();
    }

    /**
     * Lay gia tri cua attr qua name
     * Viet lai nhu sau:
     *     @Override
     *     public Object GetAttributeValue(String attr) throws NoSuchFieldException, IllegalAccessException {
     *         return this.getClass().getField(attr).get(this);
     *     }
     * @param attr
     * @return
     */
    protected abstract Object GetAttributeValue(String attr) throws NoSuchFieldException, IllegalAccessException;


    /**
     * Loai bo ca ten truong khong muon them vao JSON
     * @return
     */
    public String[] GetAttributeExcept(){
        return new String[0];
    }


    /**
     * Chuyen Object sang JSON
     * @return
     */
    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();

        /// Lay danh sach cac attr cua model
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field:fields) {
            String name  = field.getName();

            if(IsExceptField(field))
                continue;

            try {
                Object value = null;
                try {
                    value = this.GetAttributeValue(name);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                jsonObject.put(name, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  jsonObject;
    }


    /**
     * Kiem tra truong nay co phai khong duoc them vao json
     * @param field
     * @return
     */
    public boolean IsExceptField(Field field){
        for (String attr:m_except_attribute) {
            if(attr.equals(field.getName()))
                return true;
        }

        for (String attr:EXCEPT_SYS_ATTR) {
            if(attr.equals(field.getName()))
                return true;
        }

        return false;
    }


    /**
     * Chuyen JSON sang Model
     * @param clazz
     * @param jsonObject
     * @param <T>
     * @return
     */
    public static <T extends Model> T ParseJSON(Class<T> clazz, JSONObject jsonObject) {
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if(obj == null)
            return null;

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field:fields) {
            String name  = field.getName();

            if(obj.IsExceptField(field))
                continue;

            try {
                field.set(obj, jsonObject.get(name));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }
}
