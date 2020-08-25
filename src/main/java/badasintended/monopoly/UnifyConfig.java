package badasintended.monopoly;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UnifyConfig {

    private Item target = Items.AIR;

    private boolean nbt = false;

    private ArrayList<Item> excluded = new ArrayList<>();

    public Item getTarget() {
        return target;
    }

    public boolean isNbt() {
        return nbt;
    }

    public ArrayList<Item> getExcluded() {
        return excluded;
    }

    public void setTarget(Item target) {
        this.target = target;
    }

    public void setNbt(boolean nbt) {
        this.nbt = nbt;
    }

    public static class Serializer implements JsonSerializer<UnifyConfig>, JsonDeserializer<UnifyConfig> {

        public Serializer() {
        }

        @Override
        public UnifyConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            UnifyConfig unifyConfig = new UnifyConfig();

            if (json.isJsonPrimitive()) {
                unifyConfig.setTarget(Registry.ITEM.get(new Identifier(json.getAsString())));
            } else {
                JsonObject object = json.getAsJsonObject();
                unifyConfig.setTarget(Registry.ITEM.get(new Identifier(object.get("target").getAsString())));
                if (object.has("nbt")) unifyConfig.setNbt(object.get("nbt").getAsBoolean());
                if (object.has("excluded")) {
                    unifyConfig.excluded.clear();
                    JsonElement excluded = object.get("excluded");
                    if (excluded.isJsonPrimitive()) {
                        unifyConfig.excluded.add(Registry.ITEM.get(new Identifier(excluded.getAsString())));
                    } else if (excluded.isJsonArray()) {
                        excluded.getAsJsonArray().forEach(element -> unifyConfig.excluded.add(Registry.ITEM.get(new Identifier(element.getAsString()))));
                    }
                }
            }

            return unifyConfig;
        }

        @Override
        public JsonElement serialize(UnifyConfig src, Type typeOfSrc, JsonSerializationContext context) {
            if (!src.nbt && src.excluded.isEmpty()) return new JsonPrimitive(Registry.ITEM.getId(src.target).toString());

            JsonObject object = new JsonObject();
            object.addProperty("target", Registry.ITEM.getId(src.target).toString());
            if (src.nbt) object.addProperty("nbt", true);
            if (!src.excluded.isEmpty()) {
                if (src.excluded.size() == 1) {
                    object.addProperty("excluded", Registry.ITEM.getId(src.excluded.get(0)).toString());
                } else {
                    JsonArray array = new JsonArray();
                    src.excluded.forEach(item -> array.add(Registry.ITEM.getId(item).toString()));
                }
            }

            return object;
        }

    }

}
