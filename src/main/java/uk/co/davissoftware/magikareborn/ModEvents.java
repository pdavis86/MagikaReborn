package uk.co.davissoftware.magikareborn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.co.davissoftware.magikareborn.common.capabilities.IOpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilitySerializer;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilityStorage;
import uk.co.davissoftware.magikareborn.common.helpers.ResourceHelper;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(ResourceHelper.getResource("opuscapability"), new OpusCapabilitySerializer());
        }
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

        PlayerEntity player = event.getPlayer();

        IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null).orElse(null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(player.getName().toString());
            return;
        }

        opusCapability.init(player);
    }

//    @SubscribeEvent
//    public static void onRenderGui(RenderGameOverlayEvent.Text event) {
//        new UiOverlayGui(Minecraft.getMinecraft());
//    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone evt) {
        if (!evt.isWasDeath() || evt.isCanceled()) {
            return;
        }

        PlayerEntity originalPlayer = evt.getOriginal();
        PlayerEntity newPlayer = evt.getPlayer();

        if (originalPlayer == null || newPlayer == null) {
            return;
        }

        //todo: necessary?
        /*if(evt.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }*/

        IOpusCapability oldCapability = originalPlayer.getCapability(OpusCapabilityStorage.CAPABILITY, null).orElse(null);
        IOpusCapability newCapability = newPlayer.getCapability(OpusCapabilityStorage.CAPABILITY, null).orElse(null);

        if (oldCapability == null || newCapability == null) {
            OpusCapability.logNullWarning(originalPlayer.getName().getString());
            return;
        }

        newCapability.fillFrom(oldCapability, true);
    }

//    @SubscribeEvent
//    public static void onEntityLiving(LivingEvent.LivingUpdateEvent event) {
//        Entity entity = event.getEntity();
//        World world = entity.getEntityWorld();
//
//        if (entity instanceof EntityPlayer) {
//            IOpusCapability capability = entity.getCapability(OpusCapabilityStorage.CAPABILITY, null);
//
//            if (capability == null) {
//                OpusCapability.logNullWarning(entity.getName());
//                return;
//            }
//
//            if (world.isRemote) {
//                if (!capability.isInitialised()) {
//                    capability.requestFromServer();
//                }
//            } else {
//                capability.regenMana();
//            }
//        }
//    }

    /*@SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if (player.getEntityWorld().isRemote) return;

        IManaCapability mana = player.getCapability(ManaCapabilitySerializer.MANA_CAP, null);

        mana.fill(50);

        String message = String.format("You refreshed yourself in the bed. You received 50 mana, you have §7%d§r mana left.", (int) mana.getMana());
        player.sendMessage(new TextComponentString(message));
    }

    @SubscribeEvent
    public static void onPlayerFalls(LivingFallEvent event)
    {
        Entity entity = event.getEntity();

        if (entity.getEntityWorld().isRemote || !(entity instanceof EntityPlayerMP) || event.getDistance() < 3) return;

        EntityPlayer player = (EntityPlayer) entity;
        IManaCapability mana = player.getCapability(ManaCapabilitySerializer.MANA_CAP, null);

        float points = mana.getMana();
        float cost = event.getDistance() * 2;

        if (points > cost)
        {
            mana.consume(cost);

            String message = String.format("You absorbed fall damage. It costed §7%d§r mana, you have §7%d§r mana left.", (int) cost, (int) mana.getMana());
            player.sendMessage(new TextComponentString(message));

            event.setCanceled(true);
        }
    }*/
}
