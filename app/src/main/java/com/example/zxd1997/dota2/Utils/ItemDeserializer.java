package com.example.zxd1997.dota2.Utils;

import com.example.zxd1997.dota2.Beans.Attribute;
import com.example.zxd1997.dota2.Beans.Item;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemDeserializer implements JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Item item = new Item();
        JsonObject jsonObject = json.getAsJsonObject();
//        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
//            Log.d("item", "deserialize: " + entry.getKey());
//        }
//        Log.d("item", "deserialize: name "+jsonObject.get("dname"));
        try {
            item.setDname(jsonObject.get("dname") != null ? jsonObject.get("dname").getAsString() : "");
            item.setId(jsonObject.get("id") != null ? jsonObject.get("id").getAsInt() : 0);
            item.setQual(jsonObject.get("qual") != null ? jsonObject.get("qual").getAsString() : "");
            item.setCost(jsonObject.get("cost") != null ? jsonObject.get("cost").getAsInt() : 0);
            item.setDesc(jsonObject.get("desc") != null ? jsonObject.get("desc").getAsString() : "");
            item.setLore(jsonObject.get("lore") != null ? jsonObject.get("lore").getAsString() : "");
            item.setNotes(jsonObject.get("notes") != null ? jsonObject.get("notes").getAsString() : "");
            item.setMc(jsonObject.get("mc") != null ? jsonObject.get("mc").getAsString() : "");
            item.setCd(jsonObject.get("cd") != null ? jsonObject.get("cd").getAsString() : "");
            if (jsonObject.get("components") == null) item.setComponents(null);
            else
                item.setComponents(context.deserialize(jsonObject.get("components"), new TypeToken<ArrayList<String>>() {
                }.getType()));
            item.setAttrib(context.deserialize(jsonObject.get("attrib"), new TypeToken<ArrayList<Attribute>>() {
            }.getType()));
        } catch (Exception ignored) {

        }
        return item;
    }
}
