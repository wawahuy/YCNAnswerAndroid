package ml.huytools.ycnanswer.Commons.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ml.huytools.ycnanswer.Commons.MVP.Model;
import ml.huytools.ycnanswer.Commons.MVP.ModelManager;

/**
 * Các mẫu json được lấy phải tuân thủ theo
 *
 * @param <T>
 */
public class APIOutput<T extends Model> {

    /**
     * Cấu hình xây dựng Output theo cách của bạn
     */
    public interface Handling {
        void OnHandling(APIOutput output, JSONObject jsonObject);
    }

    public boolean Status;
    public String Message;
    public Object Data;
    public String Uri;
    public String DataString;
    public int ResponseCode;

    public APIOutput(){
        Status = false;
    }

    public void set(String json) throws JSONException {
        APIOutput output = new APIOutput();
        JSONObject jsonObject = new JSONObject(json);

        Handling handling = APIConfig.getCustomOutput();
        if (handling == null) {
            output.Status = jsonObject.getBoolean("status");
            output.Message = jsonObject.has("message") ? jsonObject.getString("message") : null;
            output.Data = jsonObject.get("data");
        } else {
            handling.OnHandling(output, jsonObject);
        }
    }

    public boolean isDJObject(){
        return Data instanceof JSONObject;
    }

    public boolean isDJArray(){
        return Data instanceof JSONArray;
    }


    public ModelManager<T> toModelManager(Class<T> clazz){
        if(Data == null || !isDJArray())
            return null;
        return ModelManager.ParseJSON(clazz, Data.toString());
    }

    public Model toModel(Class<T> clazz){
        if(Data == null || !isDJObject())
            return null;
        return Model.ParseJson(clazz, Data.toString());
    }

}