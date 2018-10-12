package tbm.licensetocraft.register;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import tbm.licensetocraft.LicenseToCraft;
import tbm.licensetocraft.config.Configs;
import tbm.licensetocraft.dimension.TestWorldProvider;
import tbm.licensetocraft.util.Profiler;

public class DimensionRegistry {

    public static DimensionType TEST;

    private static void registerDimensionTypes() {
        TEST = DimensionType.register(LicenseToCraft.ID, "_test", Configs.dimensionID, TestWorldProvider.class, false);
    }

    private static void registerDimension() {
        DimensionManager.registerDimension(Configs.dimensionID, TEST);
    }

    public static void onRegisterDimensions() {
        Profiler.start("Registering Dimensions");
        registerDimensionTypes();
        registerDimension();
        Profiler.end("Registering Dimensions");
    }
}
