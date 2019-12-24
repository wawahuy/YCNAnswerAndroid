package ml.huytools.ycnanswer.Commons.API;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import ml.huytools.ycnanswer.Commons.MVP.Model;
import ml.huytools.ycnanswer.Commons.MVP.ModelManager;


/***
 * APIProvider.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 16/11/2019
 * Update: 24/11/2019
 *
 */
public class APIProvider {

    static String token;
    static String host;


    /**
     * Async
     */
    public static class Async implements Runnable {
        Async.AnyRun runnable;
        Callback callback;
        Thread thread;
        Uri.Builder params;
        Handler handler;
        int requestCode;

        private Async(Async.AnyRun run){
            thread = new Thread(this);
            params = new Uri.Builder();
            runnable = run;
            handler = new Handler(Looper.myLooper());
        }

        private static Async ANY(AnyRun anyRun){
            Async async = new Async(anyRun);
            return async;
        }

        public static Async GET(final String uri){
            return ANY(new Async.AnyRun() {
                @Override
                public APIOutput run(Async async) {
                    return APIProvider.GET(uri);
                }
            });
        }

        public static Async POST(final String uri){
            return ANY(new Async.AnyRun() {
                @Override
                public APIOutput run(Async async) {
                    return APIProvider.POST(uri, async.params);
                }
            });
        }

        public Async AddParam(String key, String value){
            params.appendQueryParameter(key, value);
            return this;
        }

        public Async SetParams(Uri.Builder params){
            this.params = params;
            return this;
        }

        public Async SetRequestCode(int code){
            requestCode = code;
            return this;
        }

        public Async Then(Callback callback){
            this.callback = callback;
            this.thread.start();
            return this;
        }

        @Override
        public void run() {
            // run
            final APIOutput output = runnable.run(Async.this);

            // run on thread created
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Async.this.callback.OnAPIResult(output, Async.this.requestCode);
                }
            });
        }

        public interface Callback {
            void OnAPIResult(APIOutput output, int requestCode);
        }

        private interface AnyRun {
            APIOutput run(Async async);
        }
    }


    /**
     * Create connection
     * @param uri
     * @return
     * @throws IOException
     */
    private static HttpURLConnection CreateConnection(String uri) throws IOException {
        URL url = new URL(APIConfig.buildUrl(uri));
        HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
        return myConnection;
    }


    /**
     * Read string
     * @param httpURLConnection
     */
    private static String ReadString(HttpURLConnection httpURLConnection){
        try {
            InputStream ip = httpURLConnection.getInputStream();
            InputStreamReader ipr = new InputStreamReader(ip);
            BufferedReader bufferedReader = new BufferedReader(ipr);

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{}";
    }


    /**
     * ALL Method
     * @param uri
     * @param runnable
     * @return
     */
    private static APIOutput ANY(String uri, AnyRun runnable){
        APIOutput output = new APIOutput();

        try {
            HttpURLConnection httpURLConnection = CreateConnection(uri);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);

            /// Inject
            APIConfig.applyInjectHeader(httpURLConnection,uri);

            /// Custom
            runnable.run(httpURLConnection);

            /// code
            int responseCode = httpURLConnection.getResponseCode();
            output.Uri = uri;
            output.ResponseCode = responseCode;

            if(responseCode == HttpURLConnection.HTTP_OK){
                String strJson = ReadString(httpURLConnection);
                output.DataString = strJson;
                output.set(strJson);
                Log(strJson);
            } else {
                Log("Response code: "+ responseCode);
            }

            /// disconnection
            httpURLConnection.disconnect();

        } catch (IOException e) {
            Log("Error create HttpsUrlConnection: "+ host + uri);
            e.printStackTrace();
        } catch (JSONException e) {
            Log("Error format json");
            e.printStackTrace();
        }

        /// inject
        APIConfig.applyInjectOutput(output,uri);

        return output;
    }

    private static APIOutput ANY_NO_DATA(String uri, final String method){
        return ANY(uri, new AnyRun() {
            @Override
            public void run(HttpURLConnection connection) throws ProtocolException {
                connection.setRequestMethod(method);
            }
        });
    }

    private static APIOutput ANY_HAS_DATA(String uri, final String method , final Uri.Builder params){
        return ANY(uri, new AnyRun() {
            @Override
            public void run(HttpURLConnection connection) throws ProtocolException {
                connection.setRequestMethod(method);
                connection.setDoOutput(true);
                connection.setDoInput(true);

                //out
                try {
                    OutputStream out = connection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                    ///Out data
                    bufferedWriter.write(params.build().getEncodedQuery());

                    ///Close
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log("Error post data");
                }

            }
        });
    }


    /// ---------------------
    public static APIOutput GET(String uri){
        return ANY_NO_DATA(uri, "GET");
    }

    public static APIOutput POST(String uri, final Uri.Builder params){
        return ANY_HAS_DATA(uri, "POST", params);
    }





    /// ----------------------
    public static void SetToken(String token){
        APIProvider.token = token;
    }

    public static void SetHost(String host){
        APIProvider.host = host;
    }


    private static void Log(String str){
        Log.v("Log", str);
    }


    interface AnyRun {
        void run(HttpURLConnection connection) throws ProtocolException;
    }

}
