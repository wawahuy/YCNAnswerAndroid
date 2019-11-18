package ml.huytools.ycnanswer.Commons;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiProvider {

    public static String Token;

    public static HttpsURLConnection CreateConnection(String uri) throws IOException {
        URL url = new URL(uri);
        HttpsURLConnection myConnection = (HttpsURLConnection) url.openConnection();
        return myConnection;
    }

}