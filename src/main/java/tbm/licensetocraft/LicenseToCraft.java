package tbm.licensetocraft;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tbm.licensetocraft.register.BlockRegistry;
import tbm.licensetocraft.register.CommandRegistry;
import tbm.licensetocraft.register.DimensionRegistry;
import tbm.licensetocraft.register.ItemRegistry;
import tbm.licensetocraft.util.Profiler;

@Mod(modid = LicenseToCraft.ID, name = LicenseToCraft.NAME, version = LicenseToCraft.VERSION, useMetadata = true)
public class LicenseToCraft {
    public static final String ID = "tbmlicemsetocraft";
    public static final String NAME = "TBM-Licemse To Craft";
    public static final String VERSION = "0.1.0.0";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    public LicenseToCraft() {
        MinecraftForge.EVENT_BUS.register(BlockRegistry.EventHandler.class);
        MinecraftForge.EVENT_BUS.register(ItemRegistry.EventHandler.class);
    }

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        Profiler.start("Pre Initializing");
        Profiler.end("Pre Initializing");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Profiler.start("Initializing");
        DimensionRegistry.onRegisterDimensions();
        Profiler.end("Initializing");
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        Profiler.start("Post Initializing");
        Profiler.end("Post Initializing");
    }

    @EventHandler
    public void serverstarting(FMLServerStartingEvent event) {
        Profiler.start("Post Initializing");
        CommandRegistry.onRegisterCommands(event);
        Profiler.end("Post Initializing");
    }

    public static boolean isDevEnv() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }
}
