package br.com.mobilemind.api.droidutil.http;

import android.util.Log;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Http {

    protected static final Logger logger = Logger.getLogger(Http.class.getName());

    private int timeout;
    private Transformer transformer;

    public Http(int timeout, Transformer transformer){
        this.timeout = timeout;
        this.transformer = transformer;
    }

    public Http(){ this(20 * 1000, new Transformer());}

    public Http(Transformer transformer){ this(20 * 1000, transformer);}

    public Http(int timeout){ this(timeout, new Transformer());}

    public Response get(@Nonnull String url) {
        return get(url, new HashMap<String, String>());
    }

    public Response get(@Nonnull String url, @Nonnull Map<String, String> headers){
        Request request = new Request(url, headers);

        HttpURLConnection httpConnection = null;

        try {
            httpConnection  = getHttpConnection(request);
            httpConnection.setRequestMethod("GET");

            httpConnection.connect();

            Response response = new Response(httpConnection.getResponseCode());
            this.transformer.setResponse(response);

            this.tryFillResponse(httpConnection, response);

            for(String fieldName : httpConnection.getHeaderFields().keySet()){
                List values = httpConnection.getHeaderFields().get(fieldName);
                if(!values.isEmpty())
                    response.getHeaders().put(fieldName, values.get(0).toString());
            }

            return  response;

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpException(-1, e.getMessage(), e);
        }finally{
            if(httpConnection != null)
                httpConnection.disconnect();
        }
    }

    public Response post(@Nonnull String url, @Nonnull Object body, @Nonnull Map<String, String> headers){
        Request request = new Request(url, body, headers);

        HttpURLConnection httpConnection = null;

        try {
            httpConnection  = getHttpConnection(request);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod( "POST" );

            String bodyContent = request.getBody().toString();

            byte[] postData = bodyContent.getBytes( "UTF-8" );
            int postDataLength = postData.length;

            httpConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            DataOutputStream dataWriter = new DataOutputStream(httpConnection.getOutputStream());
            dataWriter.write(postData);
            dataWriter.flush();
            dataWriter.close();

            httpConnection.connect();


            Response response = new Response(httpConnection.getResponseCode());
            this.transformer.setResponse(response);

            this.tryFillResponse(httpConnection, response);

            for(String fieldName : httpConnection.getHeaderFields().keySet()){
                List values = httpConnection.getHeaderFields().get(fieldName);
                if(!values.isEmpty())
                    response.getHeaders().put(fieldName, values.get(0).toString());
            }

            return  response;

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpException(-1, e.getMessage(), e);
        }finally{
            if(httpConnection != null)
                httpConnection.disconnect();
        }
    }

    public Response put(@Nonnull String url, @Nonnull Object body, @Nonnull Map<String, String> headers){
        Request request = new Request(url, body, headers);

        HttpURLConnection httpConnection = null;

        try {
            httpConnection  = getHttpConnection(request);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod( "PUT" );

            String bodyContent = request.getBody().toString();

            byte[] postData = bodyContent.getBytes( "UTF-8" );
            int postDataLength = postData.length;

            httpConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            DataOutputStream dataWriter = new DataOutputStream(httpConnection.getOutputStream());
            dataWriter.write(postData);
            dataWriter.flush();
            dataWriter.close();

            httpConnection.connect();

            Response response = new Response(httpConnection.getResponseCode());
            this.transformer.setResponse(response);

            this.tryFillResponse(httpConnection, response);

            for(String fieldName : httpConnection.getHeaderFields().keySet()){
                List values = httpConnection.getHeaderFields().get(fieldName);
                if(!values.isEmpty())
                    response.getHeaders().put(fieldName, values.get(0).toString());
            }

            return  response;

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpException(-1, e.getMessage(), e);
        }finally{
            if(httpConnection != null)
                httpConnection.disconnect();
        }
    }

    public Response delete(@Nonnull String url, @Nonnull Object body, @Nonnull Map<String, String> headers){
        Request request = new Request(url, body, headers);

        HttpURLConnection httpConnection = null;

        try {
            httpConnection  = getHttpConnection(request);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod( "PUT" );

            String bodyContent = request.getBody().toString();

            byte[] postData = bodyContent.getBytes( "UTF-8" );
            int postDataLength = postData.length;

            httpConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

            DataOutputStream dataWriter = new DataOutputStream(httpConnection.getOutputStream());
            dataWriter.write(postData);
            dataWriter.flush();
            dataWriter.close();

            httpConnection.connect();


            Response response = new Response(httpConnection.getResponseCode());
            this.transformer.setResponse(response);

            this.tryFillResponse(httpConnection, response);

            for(String fieldName : httpConnection.getHeaderFields().keySet()){
                List values = httpConnection.getHeaderFields().get(fieldName);
                if(!values.isEmpty())
                    response.getHeaders().put(fieldName, values.get(0).toString());
            }

            return  response;

        }catch(Exception e){
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpException(-1, e.getMessage(), e);
        }finally{
            if(httpConnection != null)
                httpConnection.disconnect();
        }
    }

    private void tryFillResponse(HttpURLConnection connection, Response response) throws IOException {
        int httpCode = connection.getResponseCode();
        boolean isError = httpCode >= 400;

        if(isError)
            try {
                response.setError(cloneInputStream(connection.getErrorStream()));
            }catch (Exception e){}
        else
            try {
                response.setEntity(cloneInputStream(connection.getInputStream()));
            }catch (Exception e){}
    }


    private HttpURLConnection getHttpConnection(@Nonnull Request request) throws IOException {


        logger.info("HTTP CONNECT: " + request.getUrl());

        URL url = new URL(request.getUrl());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        for(String name : request.getHeaders().keySet()){
            conn.setRequestProperty (name, request.getHeaders().get(name));
        }

        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty( "charset", "utf-8");
        conn.setUseCaches(false);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setAllowUserInteraction(false);
        return conn;
    }


    private InputStream cloneInputStream(InputStream in) throws IOException {

        if(in == null)
            return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Fake code simulating the copy
        // You can generally do better with nio if you need...
        // And please, unlike me, do something about the Exceptions :D
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        //logger.info("clonned stream = " + new String(baos.toByteArray()));

        return new ByteArrayInputStream(baos.toByteArray());
    }

}
