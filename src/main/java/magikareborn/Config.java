package magikareborn;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_DIMENSIONS = "dimensions";

    private static Configuration _config;

    //todo: replace these
    public static boolean isThisAGoodTutorial = true;
    public static String yourRealName = "Steve";

    public static void readConfig(File modConfigurationDirectory) {
        _config = new Configuration(new File(modConfigurationDirectory.getPath(),"MagikaReborn.cfg"));
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
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        isThisAGoodTutorial = cfg.getBoolean("goodTutorial", CATEGORY_GENERAL, isThisAGoodTutorial, "Set to false if you don't like this tutorial");
        yourRealName = cfg.getString("realName", CATEGORY_GENERAL, yourRealName, "Set your real name here");
    }

    private static void initDimensionConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_DIMENSIONS, "Dimension configuration");
    }
}
