package ml.huytools.ycnanswer.Commons.API;

import java.net.HttpURLConnection;
import java.util.Collections;

public abstract class APIInject {
    /**
     * Không inject khi gặp uri này
     * Except uri chỉ có hiệu lực khi onlyUri không chứa phần tử nào
     */
    protected String[] exceptUri = new String[0];

    /**
     * Chỉ inject khi gặp uri này
     */
    protected String[] onlyUri = new String[0];


    /**
     * Inject header
     * @param connection
     */
    public abstract void OnHeaderInject(HttpURLConnection connection);

    /**
     * Inject output
     * @param apiOutput
     */
    public abstract void OnResult(APIOutput apiOutput);


    /**
     * Với uri cung cấp so sánh xem có nên gọi OnInject hay không
     * @param uri
     * @param exceptParams
     * @return
     */
    public boolean canInject(String uri, boolean exceptParams){
        if(exceptParams){
            uri = uri.replaceAll("\\?(.*)$", "");
        }

        if(onlyUri.length > 0) {
            for (int i = 0; i < onlyUri.length; i++) {
                if (onlyUri[i].equals(uri)) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 0; i < exceptUri.length; i++) {
                if (exceptUri[i].equals(uri)) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean canInject(String uri){
        return canInject(uri, true);
    }
}
