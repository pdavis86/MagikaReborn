package magikareborn;

import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.gui.UiOverlayGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        player.getCapability(OpusCapabilityStorage.CAPABILITY, null).init(player);
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGameOverlayEvent.Text event) {
        new UiOverlayGui(Minecraft.getMinecraft());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone evt) {
        if (!evt.isWasDeath() || evt.isCanceled()) {
            return;
        }
        if (evt.getOriginal() == null || evt.getEntityPlayer() == null) {
            return;
        }
        /*if(evt.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }*/

        IOpusCapability oldCapability = evt.getOriginal().getCapability(OpusCapabilityStorage.CAPABILITY, null);
        IOpusCapability newCapability = evt.getEntityPlayer().getCapability(OpusCapabilityStorage.CAPABILITY, null);
        newCapability.cloneFrom(oldCapability, true);
    }

    @SubscribeEvent
    public static void onEntityLiving(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();

        if (entity instanceof EntityPlayer) {
            IOpusCapability capability = entity.getCapability(OpusCapabilityStorage.CAPABILITY, null);
            if (world.isRemote) {
                if (!capability.isInitialised()) {
                    capability.requestFromServer();
                }
            } else {
                capability.regenMana();
            }
        }
    }

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
