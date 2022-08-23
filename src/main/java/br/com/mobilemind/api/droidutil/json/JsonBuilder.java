package br.com.mobilemind.api.droidutil.json;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonBuilder {

    JsonObject json = new JsonObject();

    public  static JsonBuilder create() {
        return  new JsonBuilder();
    }

    public JsonObject build() { return  this.json; };

    public JsonBuilder add(String property, JsonBuilder builder){
        json.add(property, builder.build());
        return this;
    }

    public JsonBuilder add(String property, JsonElement jsonElement){
        json.add(property, jsonElement);
        return this;
    }

    public JsonBuilder add(String property, String text){
        json.addProperty(property, text);
        return this;
    }

    public JsonBuilder add(String property, Number number){
        json.addProperty(property, number);
        return this;
    }

    public JsonBuilder add(String property, Boolean value){
        json.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, Character character){
        json.addProperty(property, character);
        return this;
    }

}