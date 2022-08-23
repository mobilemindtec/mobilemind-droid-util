package br.com.mobilemind.api.droidutil.http;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {

    public final static String CONTENT_TYPE_HEADER = "Content-Type";
    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public final static String ACCEPT_HEADER = "Accept";
    public final static String ACCEPT = "application/json";

    private final String url;
    private final Map<String, String> headers;
    private final Object body;

    public Request(@Nonnull String url, @Nonnull Object body, @Nonnull Map<String, String> headers){
        this.url = url;
        this.body = body;
        this.headers = headers;
    }

    public Request(@Nonnull String url, @Nonnull Object body){
        this(url, body, new HashMap<String, String>());
    }

    public Request(@Nonnull String url){
        this(url, null, new HashMap<String, String>());
    }

    public Request(@Nonnull String url, @Nonnull Map<String, String> headers){
        this(url, null, headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Object getBody() throws UnsupportedEncodingException {

        if(bodyIsMap()){

            if(isFormContentType())
                return mapToForm((Map)body);
            if(isJsonContentType())
                return new GsonBuilder().create().toJson(body);

        }

        if(!bodyIsString() && !bodyIsJsonObject() && !bodyIsJsonArray()){
            if(isJsonContentType())
                return new GsonBuilder().create().toJson(body);
        }


        if(bodyIsJsonObject()){
            if(isFormContentType())
                return jsonToForm((JsonObject) body);
        }


        return body;
    }

    public String getUrl() {
        return url;
    }


    public String getContentType(){
        if(headers.containsKey(CONTENT_TYPE_HEADER))
            return headers.get(CONTENT_TYPE_HEADER);
        return  "";
    }

    public boolean isFormContentType(){
        return getContentType() == CONTENT_TYPE_FORM;
    }

    public boolean isJsonContentType(){
        return getContentType() == CONTENT_TYPE_JSON;
    }


    private boolean bodyIsMap() { return this.body instanceof Map; }

    private boolean bodyIsJsonObject() { return this.body instanceof JsonObject; }

    private boolean bodyIsJSONObject() { return this.body instanceof JSONObject; }

    private boolean bodyIsJsonArray() { return this.body instanceof JsonArray; }

    private boolean bodyIsJSONArray() { return this.body instanceof JSONArray; }

    private boolean bodyIsString() { return this.body instanceof String; }

    private String mapToForm(Map params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Object key : params.keySet()){

            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key.toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    private String jsonToForm(JsonObject json) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String, JsonElement> entry : json.entrySet()){

            JsonObject jsonValue = entry.getValue().getAsJsonObject();


            Object value = "";

            if(jsonValue.isJsonPrimitive()){
                value = jsonValue.toString();
            }else if(jsonValue.isJsonObject()){
                value = jsonToForm(jsonValue);
            }

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}

