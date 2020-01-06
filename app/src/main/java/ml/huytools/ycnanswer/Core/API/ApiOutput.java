package ml.huytools.ycnanswer.Core.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ml.huytools.ycnanswer.Core.MVP.Entity;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;

/**
 * Các mẫu json được lấy phải tuân thủ theo
 *
 * @param <T>
 */
public class ApiOutput<T extends Entity> {

    /**
     * Cấu hình xây dựng Output theo cách của bạn
     * Interface phục vụ cho việc lấp đầy dữ liệu Output
     * Không phục vụ để bạn custom lại cái thuộc tính của output
     *
     */
    public interface Handling {
        void OnHandling(ApiOutput output, JSONObject jsonObject);
    }

    public boolean Status;
    public String Message;
    public Object Data;
    public String Uri;
    public String DataString;
    public int ResponseCode;

    public ApiOutput(){
        Status = false;
    }

    /**
     * Chuyển đổi JSON sang Output
     * @param json
     * @throws JSONException
     */
    public void set(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        Handling handling = ApiConfig.getCustomOutput();
        if (handling == null) {
            Status = jsonObject.getBoolean("status");
            Message = jsonObject.has("message") ? jsonObject.getString("message") : null;
            Data = jsonObject.get("data");
        } else {
            handling.OnHandling(this, jsonObject);
        }
    }

    /**
     * Kiểm tra xem Output là {...}
     * Bannj có thể chuyển sang Entity
     * @return
     */
    public boolean isDJObject(){
        return Data instanceof JSONObject;
    }

    /**
     * Kiểm tra xem Ouput là [...] array
     * Bạn có thể chuyển sang EntityManager
     * @return
     */
    public boolean isDJArray(){
        return Data instanceof JSONArray;
    }

    /**
     * Chuyên Data sang model manager
     * @param clazz
     *          clazz model (không phải modelmanager)
     * @return
     *          EntityManager<clazz>
     */
    public EntityManager<T> toModelManager(Class<T> clazz){
        if(Data == null || !isDJArray())
            return null;
        return EntityManager.ParseJSON(clazz, Data.toString());
    }

    /**
     * Chuyển Data sang model
     * @param clazz
     * @return
     */
    public Entity toModel(Class<T> clazz){
        if(Data == null || !isDJObject())
            return null;
        return Entity.ParseJson(clazz, Data.toString());
    }

}