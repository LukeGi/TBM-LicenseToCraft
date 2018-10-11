package tbm.licensetocraft.util;

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tbm.licensetocraft.LicenseToCraft;

public final class Profiler {
    private static final Object2LongMap<String> sections = new Object2LongArrayMap<>();
    private static final Logger LOGGER = LogManager.getLogger(LicenseToCraft.ID + "-profiler");

    private Profiler() {
        if (!LicenseToCraft.isDevEnv()) {
            ((org.apache.logging.log4j.core.Logger) LOGGER).setLevel(Level.OFF);
        }
    }

    public static void start(String section) {
        sections.put(section, System.currentTimeMillis());
        LOGGER.info(String.format("Starting Section [%s]", section));
    }

    public static void end(String section) {
        long start = sections.getOrDefault(section, -1L);
        if (start == -1) {
            LOGGER.error(String.format("Attempted to end a section, [%s], in profiler which is not yet created", section));
        } else {
            LOGGER.info(String.format("Ending Section [%s], took %d ms", section, (System.currentTimeMillis() - start)));
        }
    }
}
