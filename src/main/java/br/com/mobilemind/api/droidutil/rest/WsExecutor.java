package br.com.mobilemind.api.droidutil.rest;

/*
 * #%L
 * Mobile Mind - Droid Util
 * %%
 * Copyright (C) 2012 Mobile Mind Empresa de Tecnologia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import android.content.Context;
import br.com.mobilemind.api.droidutil.http.Http;
import br.com.mobilemind.api.droidutil.http.HttpException;
import br.com.mobilemind.api.droidutil.http.Response;
import br.com.mobilemind.api.droidutil.logs.AppLogger;
import br.com.mobilemind.api.droidutil.tools.ConnectionTools;
import br.com.mobilemind.api.rest.RestStatus;
import br.com.mobilemind.api.rest.RestTools;
import br.com.mobilemind.api.security.key.Base64;
import br.com.mobilemind.api.utils.MobileMindUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


/**
 * Classe que auxilia na conversa com WebService padrao Rest
 *
 * @author root
 */
@Deprecated
public class WsExecutor<T> {

    //private static final int TIMEOUT_MILLISEC = 10000; // = 10 seconds
    private static final int HTTP_GET = 0;
    private static final int HTTP_POST = 1;
    private static final int HTTP_DELETE = 2;
    private static final int HTTP_PUT = 3;
    private boolean resultToUtf8 = true;
    private int httpStatus;
    private String baseUrl;
    private String resource;
    private String operation;
    private String authentication;
    private String entity;
    private String mediaType = RestTools.MEDIA_TYPE_JSON;
    private boolean encodeAuthenticationBase64;
    private boolean testConnection = false;
    private Map<String, String> headers = new HashMap<String, String>();
    private List<String> params = new LinkedList<String>();
    public int maxFechOnGet;
    private int connectionTimeOutMillisec;
    private T objectEntity;
    private WsEntityConverter<T> converter;
    private BasicAuthentication basicAuthentication;
    private Context context;
    private String charset = RestTools.CHARSET_UTF8;
    private boolean asString;

    private Map<String, String> responseHeaders = new HashMap<String, String>();

    public WsExecutor() {
        this(30000, 10);
    }

    public WsExecutor(Context context) {
        this(30000, 10, context);
    }

    /**
     *
     *
     * @param connectionTimeOutMillisec time out to http connection
     * @param maxFechOnGet max returned records per request
     */
    public WsExecutor(int connectionTimeOutMillisec, int maxFechOnGet) {
        this(connectionTimeOutMillisec, maxFechOnGet, null);
    }

    public WsExecutor(Context context, int connectionTimeOutMillisec) {
        this(connectionTimeOutMillisec, 10, context);
    }

    public WsExecutor(int connectionTimeOutMillisec, int maxFechOnGet, Context context) {
        //HttpConnectionParams.setConnectionTimeout(this.httpParams, connectionTimeOutMillisec);
        //HttpConnectionParams.setSoTimeout(this.httpParams, connectionTimeOutMillisec);
        this.maxFechOnGet = maxFechOnGet;
        this.connectionTimeOutMillisec = connectionTimeOutMillisec;
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getConnectionTimeOutMillisec() {
        return connectionTimeOutMillisec;
    }

    public int getMaxFechOnGet() {
        return maxFechOnGet;
    }

    /**
     * value to http header Authentication. if value is null or empty, header
     * key not is sended
     *
     * @param authentication
     * @return
     */
    public WsExecutor<T> setAuthentication(String authentication) {
        this.authentication = authentication;
        return this;
    }

    /**
     * define basic http authentication
     *
     * @param basicAuthentication
     * @return
     *
     */
    public WsExecutor<T> setBasicAuthentication(BasicAuthentication basicAuthentication) {
        this.basicAuthentication = basicAuthentication;
        return this;
    }

    public WsExecutor<T> setResultToUtf8(boolean resultToUtf8) {
        this.resultToUtf8 = resultToUtf8;
        return this;
    }

    /**
     * resource in server
     *
     * baseUrl + resource + operation
     *
     * @param resource
     * @return
     */
    public WsExecutor<T> setResource(String resource) {
        this.resource = resource;
        return this;
    }

    /**
     * list of http header parameters
     *
     * @param key header key
     * @param value header value
     * @return
     */
    public WsExecutor<T> addHeaderParam(String key, Object value) {
        this.addHeader(key, value);
        return this;
    }

    public WsExecutor<T> addHeader(String key, Object value) {
        this.headers.put(key, value.toString());
        return this;
    }

    /**
     * list of rest url parametes. Ex http://google.com.br/service/1/2
     *
     * 1 and 2 are parametes list
     *
     * @param value
     * @return
     */
    public WsExecutor<T> addParams(Object value) {
        this.params.add(value.toString());
        return this;
    }

    /**
     * entity to put or post operation. can be json, xml, atom or other
     * supported by the server
     *
     * @param entity
     * @return
     */
    public WsExecutor<T> setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    /**
     * operation attached.
     *
     * baseUrl + resource + operation
     *
     * @param operation
     * @return
     */
    public WsExecutor<T> setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    /**
     * base url to service
     *
     * @param baseUrl
     * @return
     */
    public WsExecutor<T> setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * if autentication should be encoded to base64
     *
     * @param encodeAuthenticationBase64
     * @return
     */
    public WsExecutor<T> setEncodeAuthenticationBase64(boolean encodeAuthenticationBase64) {
        this.encodeAuthenticationBase64 = encodeAuthenticationBase64;
        return this;
    }

    /**
     * if connection status shoult tested before connection with service
     *
     * @param testConnection
     * @return
     */
    public WsExecutor<T> setTestConnection(boolean testConnection) {
        this.testConnection = testConnection;
        return this;
    }

    /**
     * status http before response received
     *
     * @return
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * object converter to message. must be informed of the converter
     *
     * @param objectEntity
     */
    public WsExecutor<T> setObjectEntity(T objectEntity) {
        this.objectEntity = objectEntity;
        return this;
    }

    public Map<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }

    /**
     * Object entity converter
     *
     * @param converter
     */
    public WsExecutor<T> setConverter(WsEntityConverter<T> converter) {
        this.converter = converter;
        return this;
    }

    /**
     * set media type. see #{@link RestTools}. default is
     * #{@link RestTools#MEDIA_TYPE_JSON}
     *
     * @param mediaType
     */
    public WsExecutor<T> setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public String executeGetAsString() throws RestException, IOException {
        this.asString = true;
        return (String) this.execute(HTTP_GET);
    }

    public String executePostAsString() throws RestException, IOException {
        this.asString = true;
        return (String) this.execute(HTTP_POST);
    }

    public String executeDeleteAsString() throws RestException, IOException {
        this.asString = true;
        return (String) this.execute(HTTP_DELETE);
    }

    public String executePutAsString() throws RestException, IOException {
        this.asString = true;
        return (String) this.execute(HTTP_PUT);
    }

    public T executeGet() throws RestException, IOException {
        return (T) this.execute(HTTP_GET);
    }

    public T executePost() throws RestException, IOException {
        return (T) this.execute(HTTP_POST);
    }

    public T executeDelete() throws RestException, IOException {
        return (T) this.execute(HTTP_DELETE);
    }

    public T executePut() throws RestException, IOException {
        return (T) this.execute(HTTP_PUT);
    }

    private Object execute(int type) throws RestException, IOException {
        try {
            return this.execute0(type);
        } finally {
            this.headers.clear();
            this.params.clear();
            this.baseUrl = null;
            this.resource = null;
            this.operation = null;
            this.authentication = null;
            this.entity = null;
            this.objectEntity = null;
            this.basicAuthentication = null;
        }
    }

    private Object execute0(int type) throws RestException, IOException {

        if (this.testConnection) {
            this.valitetConnection();
        }


        //HttpClient client = new DefaultHttpClient(httpParams);
        //HttpRequestBase request = null;
        //HttpResponse response = null;
        //HttpEntity httpEntity = null;
        Object result = null;
        InputStream instream = null;

        if (this.basicAuthentication != null) {
            headers.put(BasicAuthentication.AUTHORIZATION, "Basic " + this.basicAuthentication.toBase64());
        }

        String url = createUrl();

        if (this.converter != null) {
            AppLogger.info(getClass(), "using WsEntityConverter");
            if (this.objectEntity != null) {
                this.entity = this.converter.toEntity(this.objectEntity);
            }
        }

        Response response = null;

        AppLogger.info(this.getClass(), "base url [" + url + "]");

        if (this.entity != null && this.entity.length() < 10000) {
            AppLogger.info(this.getClass(), "send entity message [" + this.entity + "]");
        }

        if(!this.headers.containsKey("Content-Type"))
            this.headers.put("Content-Type", this.mediaType);

        if (!MobileMindUtil.isNullOrEmpty(this.authentication)) {
            if (this.encodeAuthenticationBase64) {
                AppLogger.info(getClass(), "encode authentication to base64");
                this.headers.put(BasicAuthentication.AUTHORIZATION,
                        "Basic " + Base64.encodeBytes(this.authentication.getBytes()));
            } else {
                AppLogger.info(getClass(), "don't enconde authentication [" + this.authentication + "]");
                this.headers.put(BasicAuthentication.AUTHORIZATION,
                        "Basic " + this.authentication);
            }
        }

        try {

            switch (type) {
                case HTTP_GET:
                    response = new Http()
                            .get(url, this.headers);
                    AppLogger.info(getClass(), "executing HTTP GET operation");
                    break;
                case HTTP_POST:
                    response = new Http()
                            .post(url, this.entity, this.headers);
                    AppLogger.info(getClass(), "executing HTTP POST operation");
                    break;
                case HTTP_DELETE:
                    response = new Http()
                            .delete(url, this.entity ,this.headers);
                    AppLogger.info(getClass(), "executing HTTP DELETE operation");
                    break;
                case HTTP_PUT:
                    response = new Http()
                            .put(url, this.entity ,this.headers);
                    AppLogger.info(getClass(), "executing HTTP PUT operation");
                    break;
            }

        }catch (HttpException e){

            if(e.getCause().getMessage().contains("refused")){
                AppLogger.getInstance().log(Level.SEVERE, this.getClass(),
                        "error execute httpCliente request", e);
                if (e.getMessage().contains("refused")) {
                    throw new RestException(RestStatus.HTTP_CONNECTION_REFUSED, "conneciton refused");
                } else {
                    throw new RestException(e.getMessage(), e);
                }
            } else if(e.getCause().getMessage().contains("timeout")){
                throw new RestException(RestStatus.HTTP_CONNECTION_TIME_OUT, "connection timeout");
            } else {
                throw e;
            }
        }
        catch (Exception e) {
            AppLogger.getInstance().log(Level.SEVERE, this.getClass(),
                    "error execute httpCliente request", e);
            throw new RestException(e.getMessage(), e);
        }

        AppLogger.info(getClass(), "Http Status Returned [" + response.getHttpStatus() + "]");

        this.httpStatus = response.getHttpStatus();

        Map<String, String> responseHeaders = response.getHeaders();

        for(Map.Entry<String, String> entry : responseHeaders.entrySet())
            this.responseHeaders.put(entry.getKey(), entry.getValue());


        InputStream httpEntity = response.getEntity();
        String content = null;

        if (httpEntity != null) {

            try {
                //instream = httpEntity.getContent();
                content = convertStreamToString(httpEntity);
                result = content;
            } catch (Exception e) {
                AppLogger.getInstance().log(Level.SEVERE, this.getClass(),
                        "error convert response content", e);
                throw new RestException("error convert response content: " + e.getMessage(), e);
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException io) {
                        AppLogger.getInstance().log(Level.SEVERE,
                                this.getClass(), "error closing instream", io);
                    }
                }
            }
            AppLogger.info(this.getClass(), "result of http conversation is: [" + result + "]");

            if (!asString) {
                if (converter != null) {
                    AppLogger.info(this.getClass(), "convert result");
                    result = converter.toObject(content);
                }
            }
        }


        if (this.httpStatus != RestStatus.OK) {
            throw new RestException(this.httpStatus, response.transformer().errorString(), content);
        }

        return result;
    }

    private String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = null;

        if (resultToUtf8) {
            reader = new BufferedReader(new InputStreamReader(is,
                    "UTF-8"));
        } else {
            reader = new BufferedReader(new InputStreamReader(is));
        }

        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        return sb.toString();
    }

    private void valitetConnection() throws RestException {
        if (context != null) {
            if (!ConnectionTools.isConnected(context)) {
                throw new RestException(RestStatus.CONNECTION_NOT_FOUND, "network connection not found");
            }
        }
    }

    private String createUrl() {
        String url = this.baseUrl;

        if (!MobileMindUtil.isNullOrEmpty(this.resource)) {
            if (!this.resource.startsWith("/") && !url.endsWith("/")) {
                url += "/";
            }
            url += this.resource;
        }

        if (!MobileMindUtil.isNullOrEmpty(this.operation)) {
            if (!this.operation.startsWith("/") && !url.endsWith("/")) {
                url += "/";
            }
            url += this.operation;
        }

        if (!this.params.isEmpty()) {
            if (!url.endsWith("/")) {
                url += "/";
            }

            int size = this.params.size();

            for (int i = 0; i < size; i++) {
                url += this.params.get(i);
                if (i < (size - 1)) {
                    url += "/";
                }
            }
        }

        return url;
    }

}
