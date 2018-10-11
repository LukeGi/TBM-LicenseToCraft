package tbm.licensetocraft.register;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tbm.licensetocraft.util.Profiler;

public class ItemRegistry {

    public static class EventHandler {
        @SubscribeEvent
        public static void onRegisterItem(Register<Item> event) {
            Profiler.start("Register Items");
            Profiler.end("Register Items");
        }
    }
}
