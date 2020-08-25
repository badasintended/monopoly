package badasintended.monopoly;

import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class UnifyConfig {

    private Identifier target;

    private boolean nbt = false;

    public Identifier getTarget() {
        return target;
    }

    public boolean isNbt() {
        return nbt;
    }

    public void setTarget(Identifier target) {
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
                unifyConfig.setTarget(new Identifier(json.getAsString()));
            } else {
                JsonObject object = json.getAsJsonObject();
                unifyConfig.setTarget(new Identifier(object.get("target").getAsString()));
                if (object.has("nbt")) unifyConfig.setNbt(object.get("nbt").getAsBoolean());
            }

            return unifyConfig;
        }

        @Override
        public JsonElement serialize(UnifyConfig src, Type typeOfSrc, JsonSerializationContext context) {
            if (!src.nbt) return new JsonPrimitive(src.target.toString());

            JsonObject object = new JsonObject();
            object.addProperty("target", src.target.toString());
            if (src.nbt) object.addProperty("nbt", true);

            return object;
        }

    }

}
