package riskyken.cosmeticWings.utils;

import org.apache.logging.log4j.Level;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import net.minecraftforge.fml.common.FMLLog;

public class ModLogger {
    public static void log(Object object) {
        FMLLog.log(LibModInfo.NAME, Level.INFO, String.valueOf(object));
    }

    public static void log(Level logLevel, Object object) {
        FMLLog.log(LibModInfo.NAME, logLevel, String.valueOf(object));
    }
}
