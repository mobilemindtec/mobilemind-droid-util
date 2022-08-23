package br.com.mobilemind.api.droidutil.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Transformer {

    protected static final Logger logger = Logger.getLogger(Transformer.class.getName());

    public static String GSON_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static FieldNamingPolicy GSON_NAMING_CONVENTION = FieldNamingPolicy.UPPER_CAMEL_CASE;
    private Response response;
    private Gson gson;

    public Transformer(){}

    public void setResponse(Response response) {
        this.response = response;
    }

    public Gson makeGson(){
        if(this.gson == null)
            this.gson = new GsonBuilder()
                .setFieldNamingPolicy(GSON_NAMING_CONVENTION)
                .setDateFormat(GSON_TIMESTAMP_FORMAT)
                .create();

        return this.gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public static void setGsonTimestampFormat(String format){
        GSON_TIMESTAMP_FORMAT = format;
    }

    public static void setGsonNamingConvention(FieldNamingPolicy gsonNamingConvention) {
        GSON_NAMING_CONVENTION = gsonNamingConvention;
    }


    public <T> List<T> list() throws IOException {
        Gson gson = this.makeGson();
        Type typeToken = new TypeToken<List<T>>() {}.getType();
        return  list(typeToken, gson);
    }

    public <T> List<T> list(Type typeToken) throws IOException {
        Gson gson = this.makeGson();
        return  list(typeToken, gson);
    }

    public <T> List<T> list(Type typeToken, Gson gson) throws IOException {
        String reader = string();
        return gson.fromJson(reader, typeToken);
    }

    public <T> T object() throws IOException {
        Gson gson = this.makeGson();
        Type typeToken = new TypeToken<T>() {}.getType();
        return  object(typeToken, gson);
    }

    public <T> T object(Class<T> clazz) throws IOException {
        Gson gson = this.makeGson();
        return  object(clazz, gson);
    }

    public <T> T object(Class<T> clazz, Gson gson) throws IOException {
        String reader = string();
        return gson.fromJson(reader, clazz);
    }

    public <T> T object(Type typeToken) throws IOException {
        Gson gson = this.makeGson();
        return  object(typeToken, gson);
    }

    public <T> T object(Type typeToken, Gson gson) throws IOException {
        String reader = string();
        return gson.fromJson(reader, typeToken);
    }

    public JsonObject json() throws IOException {
        JsonParser parser = new JsonParser();
        String reader = string();
        return parser.parse(reader).getAsJsonObject();
    }

    public JsonArray jsonArray() throws IOException {
        JsonParser parser = new JsonParser();
        String reader = string();
        return parser.parse(reader).getAsJsonArray();
    }

    public JSONObject toJSONObject() throws IOException, JSONException {
        String reader = string();
        return new JSONObject(reader);
    }

    public JSONArray toJSONArray() throws IOException, JSONException {
        String reader = string();
        return new JSONArray(reader);
    }

    private Reader reader(){
        BufferedReader br = new BufferedReader(new InputStreamReader(
                this.response.getEntity()));
        return  br;
    }

    public String string() throws IOException {

        BufferedReader reader = (BufferedReader)this.reader();
        StringBuilder content = new StringBuilder();
        String output = null;

        try {

            while ((output = reader.readLine()) != null) {
                content.append(output);
            }

            logger.info("result content = " + content);

            return content.toString();
        }finally {
            reader.close();
        }
    }

    public String errorString() throws IOException {

        if(this.response.getError() == null)
            return "unknow error";

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                this.response.getError()));

        StringBuilder content = new StringBuilder();
        String output = null;

        try {
            while ((output = reader.readLine()) != null) {
                content.append(output);
            }

            logger.info("error content = " + content);

            return content.toString();
        }finally {
            reader.close();
        }
    }

    public JsonObject errorJson() throws IOException {
        JsonParser parser = new JsonParser();
        return parser.parse(errorString()).getAsJsonObject();
    }

    public <T> T errorObject(Class<T> clazz) throws IOException {
        Gson gson = new GsonBuilder().create();
        return errorObject(clazz, gson);
    }

    public <T> T errorObject(Class<T> clazz, Gson gson) throws IOException {
        return gson.fromJson(errorString(), clazz);
    }
}
