package badasintended.monopoly;


import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class Config {

    private Map<Identifier, Unify> unify = new HashMap<>();

    public Map<Identifier, Unify> getUnify() {
        return unify;
    }

    public void setUnify(Map<Identifier, Unify> unify) {
        this.unify = unify;
    }

    public static class Unify {

        private Identifier target;

        private boolean nbt = false;

        private boolean thrown = false;

        public Identifier getTarget() {
            return target;
        }

        public boolean isNbt() {
            return nbt;
        }

        public boolean isThrown() {
            return thrown;
        }

        public void setTarget(Identifier target) {
            this.target = target;
        }

        public void setNbt(boolean nbt) {
            this.nbt = nbt;
        }

        public void setThrown(boolean thrown) {
            this.thrown = thrown;
        }

        public static class Serializer implements JsonSerializer<Unify>, JsonDeserializer<Unify> {

            public Serializer() {
            }

            @Override
            public Unify deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Unify unify = new Unify();

                if (json.isJsonPrimitive()) {
                    unify.setTarget(new Identifier(json.getAsString()));
                } else {
                    JsonObject object = json.getAsJsonObject();
                    unify.setTarget(new Identifier(object.get("target").getAsString()));
                    if (object.has("nbt")) unify.setNbt(object.get("nbt").getAsBoolean());
                    if (object.has("thrown")) unify.setThrown(object.get("thrown").getAsBoolean());
                }

                return unify;
            }

            @Override
            public JsonElement serialize(Unify src, Type typeOfSrc, JsonSerializationContext context) {
                if (!src.nbt && !src.thrown) return new JsonPrimitive(src.target.toString());

                JsonObject object = new JsonObject();
                object.addProperty("target", src.target.toString());
                if (src.nbt) object.addProperty("nbt", true);
                if (src.thrown) object.addProperty("thrown", true);

                return object;
            }

        }

    }

}
