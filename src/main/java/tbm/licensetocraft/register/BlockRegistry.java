package tbm.licensetocraft.register;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tbm.licensetocraft.util.Profiler;

public class BlockRegistry {

    public static class EventHandler {
        @SubscribeEvent
        public static void onRegisterBlocks(Register<Block> event) {
            Profiler.start("Register Blocks");
            Profiler.end("Register Blocks");
        }
    }
}
