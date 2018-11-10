package magikareborn;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class Config {

    private static final String _generalCategory = "general";
    private static final String _dimensionsCategory = "dimensions";

    private static Configuration _config;

    //todo: replace these
    public static boolean isThisAGoodTutorial = true;
    public static String yourRealName = "Steve";

    public static void readConfig(File modConfigurationDirectory) {
        _config = new Configuration(new File(modConfigurationDirectory.getPath(), "MagikaReborn.cfg"));
        try {
            _config.load();
            initGeneralConfig(_config);
            initDimensionConfig(_config);
        } catch (Exception ex) {
            ModRoot.logger.log(Level.ERROR, "Problem loading config file!", ex);
        } finally {
            saveChanges();
        }
    }

    public static void saveChanges() {
        if (_config.hasChanged()) {
            _config.save();
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(_generalCategory, "General configuration");
        isThisAGoodTutorial = cfg.getBoolean("goodTutorial", _generalCategory, isThisAGoodTutorial, "Set to false if you don't like this tutorial");
        yourRealName = cfg.getString("realName", _generalCategory, yourRealName, "Set your real name here");
    }

    private static void initDimensionConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(_dimensionsCategory, "Dimension configuration");
    }
}
