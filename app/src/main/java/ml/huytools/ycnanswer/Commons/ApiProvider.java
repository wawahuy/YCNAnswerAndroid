package ml.huytools.ycnanswer.Commons;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/***
 * ApiProvider.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 16/11/2019
 * Update: 20/11/2019
 *
 */
public class ApiProvider {

    public static String Token;

    public static HttpsURLConnection CreateConnection(String uri) throws IOException {
        URL url = new URL(uri);
        HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
        return myConnection;
    }


    public static String Get(){
        return "{}";
    }
}
