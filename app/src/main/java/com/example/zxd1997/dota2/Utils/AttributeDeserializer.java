package com.example.zxd1997.dota2.Utils;

import com.example.zxd1997.dota2.Beans.Attribute;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class AttributeDeserializer implements JsonDeserializer<Attribute> {
    @Override
    public Attribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Attribute attribute = new Attribute();
        JsonObject jsonObject = json.getAsJsonObject();
//        for (Map.Entry<String,JsonElement> entry:jsonObject.entrySet()){
//            Log.d("item", "deserialize: "+entry.getKey());
//        }
        try {
            attribute.setKey(jsonObject.get("key") != null ? jsonObject.get("key").getAsString() : "");
            attribute.setFooter(jsonObject.get("footer") != null ? jsonObject.get("footer").getAsString() : "");
            attribute.setHeader(jsonObject.get("header") != null ? jsonObject.get("header").getAsString() : "");
            JsonElement value = jsonObject.get("value");
            if (value != null) {
                if (value.isJsonArray()) {
                    StringBuilder s = new StringBuilder();
                    JsonArray jsonArray = value.getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        s.append(element.getAsString()).append("/");
                    }
                    attribute.setValue(s.toString().substring(0, s.length() - 1));
                } else attribute.setValue(value.getAsString());
            }
//            Log.d("attr", "deserialize: "+attribute.getHeader()+ attribute.getValue()+attribute.getKey()+attribute.getFooter());
        } catch (Exception ignored) {

        }
        return attribute;
    }
}
