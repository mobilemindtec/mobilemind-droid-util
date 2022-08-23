package br.com.mobilemind.api.droidutil.http;



import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {

    public static final  int HTTP_STATUS_OK = 200;
    private final int httpStatus;
    private final Map<String, String> headers;
    private InputStream entity;
    private InputStream error;
    private Transformer transformer;

    Response(int httpStatus){
        this(httpStatus, new Transformer());
    }

    Response(int httpStatus, Transformer transformer){
        this.httpStatus = httpStatus;
        this.headers = new HashMap<String, String>();
        this.setTransformer(transformer);
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
        this.transformer.setResponse(this);
    }

    public Transformer transformer() {
        return transformer;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }


    public InputStream getEntity() {
        return entity;
    }

    public void setEntity(InputStream entity) {
        this.entity = entity;
    }

    public InputStream getError() {
        return error;
    }

    public void setError(InputStream error) {
        this.error = error;
    }

    public boolean is200(){ return this.httpStatus == HTTP_STATUS_OK; }
}
