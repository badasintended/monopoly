package badasintended.monopoly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class Monopoly implements ModInitializer {

    public static final String ID = "monopoly";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(ID + ".json");

    private static Monopoly INSTANCE;
    private static Config CONFIG;

    public final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Identifier.class, new Identifier.Serializer())
        .registerTypeAdapter(Config.Unify.class, new Config.Unify.Serializer())
        .create();

    @Override
    public void onInitialize() {
        INSTANCE = this;
        CONFIG = new Config();
    }

    public void loadConfig() {
        if (Files.notExists(CONFIG_PATH)) {
            LOGGER.warn("[monopoly] Config not found, creating...");
            reset();
        }

        try {
            CONFIG = gson.fromJson(String.join("\n", Files.readAllLines(CONFIG_PATH)), Config.class);
            LOGGER.info("[monopoly] Config loaded.");
        } catch (Exception e) {
            LOGGER.error("[monopoly] Unable to read config.");
            e.printStackTrace();
        }
    }

    public static Monopoly getInstance() {
        return INSTANCE;
    }

    public static Config getConfig() {
        return CONFIG;
    }

    private void reset() {
        try {
            Files.write(CONFIG_PATH, gson.toJson(CONFIG).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
