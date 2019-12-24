package ml.huytools.ycnanswer.Commons.API;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.regex.Pattern;


public class APIConfig {
    private static String hostname;
    private static LinkedList<APIInject> injects = new LinkedList<>();
    private static APIOutput.Handling customOutput;
    private static boolean canInject;


    public static String getHostname() {
        return hostname;
    }

    public static void setHostname(String hostname) {
        if(!isUrl(hostname)){
            hostname = "http://" + hostname;
        }

        if(hostname.charAt(hostname.length() - 1) == '/'){
            hostname = hostname.substring(0, hostname.length()-1);
        }

        APIConfig.hostname = hostname;
    }

    public static boolean isUrl(String url){
        return Pattern.matches("([hH][tT][tT][pP][sS]?):\\/\\/(.*)", url);
    }

    public static boolean isHttp(String url){
        return Pattern.matches("([hH][tT][tT][pP]):\\/\\/(.*)", url);
    }

    public static boolean isHttps(String url){
        return Pattern.matches("([hH][tT][tT][pP][s].):\\/\\/(.*)", url);
    }

    public static APIOutput.Handling getCustomOutput() {
        return customOutput;
    }

    public static void setCustomOutput(APIOutput.Handling customOutput) {
        APIConfig.customOutput = customOutput;
    }


    /***
     * URL = hostname + uri
     * Ưu tiên uri, nếu uri chứa ^http([s]{0,1}):\\\\ => URL = uri
     *
     * @param uri
     * @return
     */
    public static String buildUrl(String uri){
        if(isUrl(uri) || hostname == null){
            return uri;
        }

        if(uri.charAt(0) != '/'){
            return hostname + '/' + uri;
        }

        return hostname + uri;
    }

    public static void addInject(APIInject injectHeader){
        injects.add(injectHeader);
    }

    public static void removeInject(APIInject inject){
        injects.remove(inject);
    }

    public static void applyInjectHeader(HttpURLConnection connection, String uri){
        for(APIInject c:injects){
            if(c.canInject(uri)){
                c.OnHeaderInject(connection);
            }
        }
    }

    public static void applyInjectOutput(APIOutput output, String uri){
        for(APIInject c:injects){
            if(c.canInject(uri)){
                c.OnResult(output);
            }
        }
    }

    public static boolean isCanInject() {
        return canInject;
    }

    public static void setCanInject(boolean canInject) {
        APIConfig.canInject = canInject;
    }
}
