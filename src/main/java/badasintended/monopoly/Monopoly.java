package badasintended.monopoly;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Monopoly implements ModInitializer {

    public static final String ID = "monopoly";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(ID + ".json");

    private static final Pattern DEFAULTS = Pattern.compile(
        ".*_ingots|.*_ores|.*_plates|.*_dusts|.*_gears|.*_nuggets"
    );

    private static Monopoly INSTANCE;

    private Map<Identifier, UnifyConfig> config = new HashMap<>();
    private final Type configType = new TypeToken<Map<Identifier, UnifyConfig>>() {
    }.getType();

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

    public void loadConfig(MinecraftServer server) {
        if (Files.notExists(CONFIG_PATH)) {
            LOGGER.warn("[monopoly] Config not found, creating defaults...");
            reset(server);
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
                if (unifyConfig.getExcluded().contains(from.getItem())) continue;
                if (!unifyConfig.isNbt() && from.getTag() != null) continue;

                Item item = unifyConfig.getTarget();
                if (item == Items.AIR) continue;

                ItemStack unified = new ItemStack(item, from.getCount());

                unified.setTag(from.getTag());

                return unified;
            }
        }
        return from;
    }

    private void reset(MinecraftServer server) {
        config.clear();
        int generated = 0;
        for (Map.Entry<Identifier, Tag<Item>> entry : server.getTagManager().getItems().getTags().entrySet()) {
            Identifier id = entry.getKey();
            if (id.getNamespace().equals("c") && DEFAULTS.matcher(id.getPath()).matches()) {
                List<Item> values = entry.getValue().values();
                if (values.size() <= 1) continue;
                UnifyConfig unifyConfig = new UnifyConfig();
                unifyConfig.setTarget(values.get(0));
                config.put(id, unifyConfig);
                generated++;
            }
        }
        try {
            Files.write(CONFIG_PATH, gson.toJson(config).getBytes());
            LOGGER.info("[monopoly] Generated {} default values", generated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
