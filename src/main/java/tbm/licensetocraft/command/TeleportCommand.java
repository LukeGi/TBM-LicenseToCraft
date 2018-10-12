package tbm.licensetocraft.command;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import tbm.licensetocraft.LicenseToCraft;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TeleportCommand extends CommandBase {

    public TeleportCommand() {
        aliases = Lists.newArrayList(LicenseToCraft.ID, "TP", "tp");
    }

    private final List<String> aliases;

    @Override
    @Nonnull
    public String getName() {
        return "tp";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "tp <id>";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 1) {
            return;
        }
        String s = args[0];
        int dim;
        try {
            dim = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Error parsing dimension!"));
            return;
        }

        if (sender instanceof EntityPlayer) {
            CustomTeleporter.teleportToDimension((EntityPlayer) sender, dim, 0, 100, 0);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    public static class CustomTeleporter extends Teleporter {

        public CustomTeleporter(WorldServer world, double x, double y, double z) {
            super(world);
            this.worldServer = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private final WorldServer worldServer;
        private double x;
        private double y;
        private double z;

        @Override
        public void placeInPortal(@Nonnull Entity entity, float rotationYaw) {
            // The main purpose of this function is to *not* create a nether portal
            this.worldServer.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));

            entity.setPosition(this.x, this.y, this.z);
            entity.motionX = 0.0f;
            entity.motionY = 0.0f;
            entity.motionZ = 0.0f;
        }


        public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
            int oldDimension = player.getEntityWorld().provider.getDimension();
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
            MinecraftServer server = player.getEntityWorld().getMinecraftServer();
            WorldServer worldServer = server.getWorld(dimension);
            player.addExperienceLevel(0);

            if (worldServer == null || worldServer.getMinecraftServer() == null) { //Dimension doesn't exist
                throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist!");
            }

            worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(worldServer, x, y, z));
            player.setPositionAndUpdate(x, y, z);
            if (oldDimension == 1) {
                // For some reason teleporting out of the end does weird things. Compensate for that
                player.setPositionAndUpdate(x, y, z);
                worldServer.spawnEntity(player);
                worldServer.updateEntityWithOptionalForce(player, false);
            }
        }

    }
}
