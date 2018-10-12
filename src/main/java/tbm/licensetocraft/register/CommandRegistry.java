package tbm.licensetocraft.register;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import tbm.licensetocraft.command.TeleportCommand;
import tbm.licensetocraft.util.Profiler;

public class CommandRegistry {

    public static void onRegisterCommands(FMLServerStartingEvent event) {
        Profiler.start("Registering Commands");
        event.registerServerCommand(new TeleportCommand());
        Profiler.end("Registering Commands");
    }
}
