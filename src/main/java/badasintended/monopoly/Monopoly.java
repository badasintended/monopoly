package badasintended.monopoly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Monopoly implements ModInitializer {

    public static final String ID = "monopoly";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(ID + ".json");

    private static Monopoly INSTANCE;

    private Map<Identifier, UnifyConfig> config;
    private final Type configType = new TypeToken<Map<Identifier, UnifyConfig>>() {}.getType();

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .registerTypeAdapter(UnifyConfig.class, new UnifyConfig.Serializer())
        .create();

    public static Monopoly getInstance() {
        return INSTANCE;
    }

    @Override
    public void onInitialize() {
        INSTANCE = this;
    }

    public void loadConfig() {
        config = new HashMap<>();
        if (Files.notExists(CONFIG_PATH)) {
            LOGGER.warn("[monopoly] Config not found, creating...");
            reset();
        }

        try {
            config = gson.fromJson(String.join("\n", Files.readAllLines(CONFIG_PATH)), configType);
            LOGGER.info("[monopoly] Config loaded.");
        } catch (Exception e) {
            LOGGER.error("[monopoly] Unable to read config.");
            e.printStackTrace();
        }
    }

    public Map<Identifier, UnifyConfig> getConfig() {
        return config;
    }

    public ItemStack unify(World world, ItemStack from) {
        if (world.isClient || from.isEmpty()) return from;
        for (Map.Entry<Identifier, UnifyConfig> entry : getConfig().entrySet()) {
            Tag<Item> tag = world.getTagManager().items().get(entry.getKey());
            if (tag == null) continue;
            if (tag.contains(from.getItem())) {
                UnifyConfig unifyConfig = entry.getValue();
                if (unifyConfig == null) continue;

                if (!unifyConfig.isNbt() && from.getTag() != null) continue;

                Item item = Registry.ITEM.get(unifyConfig.getTarget());
                if (item == Items.AIR) continue;

                ItemStack unified = new ItemStack(item, from.getCount());

                unified.setTag(from.getTag());

                return unified;
            }
        }
        return from;
    }

    private void reset() {
        try {
            Files.write(CONFIG_PATH, gson.toJson(config).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
