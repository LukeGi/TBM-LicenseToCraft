package tbm.licensetocraft.util;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tbm.licensetocraft.LicenseToCraft;

public final class Profiler {
    private static final Object2LongMap<String> sections = new Object2LongOpenHashMap<>();
    private static final Logger LOGGER = LogManager.getLogger(LicenseToCraft.ID + "-profiler");
    private static long loadingTime = 0L;

    static {
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
        if (!LicenseToCraft.isDevEnv()) {
            ((org.apache.logging.log4j.core.Logger) LOGGER).setLevel(Level.OFF);
        }
    }

    public static void start(String section) {
        sections.put(section, System.nanoTime());
        LOGGER.info(String.format("Starting Section [%s]", section));
    }

    public static void end(String section) {
        long start = sections.removeLong(section);
        if (start == 0) {
            LOGGER.error(String.format("Attempted to end a section, [%s], in profiler which is not yet created", section));
        } else {
            long time = (System.nanoTime() - start);
            if (sections.isEmpty()) loadingTime += time;
            LOGGER.info(String.format("Ending Section [%s], took %d ms", section, (time / 1000000)));
        }
    }

    public static class EventHandler {
        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void onGuiOpen(GuiOpenEvent event) {
            if (event.getGui() instanceof GuiMainMenu) {
                LicenseToCraft.LOGGER.info(String.format("Took %d ms to load.", (loadingTime / 1000000)));
                MinecraftForge.EVENT_BUS.unregister(EventHandler.class);
            }
        }
    }
}
