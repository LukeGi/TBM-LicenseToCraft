package tbm.licensetocraft.dimension;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import tbm.licensetocraft.dimension.worldgen.TestChunkGenerator;
import tbm.licensetocraft.register.DimensionRegistry;

import javax.annotation.Nullable;

public class TestWorldProvider extends WorldProvider {

    @Nullable
    @Override
    public String getSaveFolder() {
        return "TEST";
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionRegistry.TEST;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new TestChunkGenerator(world);
    }
}
