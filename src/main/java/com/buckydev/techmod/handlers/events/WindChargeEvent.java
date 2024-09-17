package com.buckydev.techmod.handlers.events;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.capabilities.ModCapabilities;
import com.buckydev.techmod.capabilities.interfaces.mana.IManaTank.ManaTankAction;
import com.buckydev.techmod.tags.ModTags.ModBlockTags;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

/**
 * Caching might be a good idea here, since this can occur on a lot of blocks and often <p>
 * <a href="https://docs.neoforged.net/docs/datastorage/capabilities/#block-capability-caching">neoforge cap caching</a><p>
 * <a href="https://github.com/google/guava/wiki/CachesExplained">guava cache</a> {@link com.google.common.cache.LoadingCache}<p>
 * <a href="https://github.com/ben-manes/caffeine">Caffeine, a guava replacement</a> {@code implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")}<p>
 * Key: blockPos, Value:BlockCapCache<p>
 * How to handle other mods cap invalidation<p>
 */
@SuppressWarnings("unused")
public class WindChargeEvent {
    public WindChargeEvent() {
        TechMod.LOGGER.info("Built WindChargeEvent");
    }

    @SubscribeEvent
    public void windChargeEffectBlock(ExplosionEvent.Detonate event) {
        if (event.getExplosion().getBlockInteraction() != BlockInteraction.TRIGGER_BLOCK) {
            return;
        }
        Explosion explosion = event.getExplosion();
        Level level = event.getLevel();
        var blocks = event.getAffectedBlocks();

        List<BlockPos> filtered = blocks.stream()
                .filter(blockPos -> level.getBlockState(blockPos).is(ModBlockTags.WIND_CHARGEABLE))
                .toList();
        // cache speed up
        for (var block : filtered) {
            var cap = level.getCapability(ModCapabilities.MANA_HANDLER, block);
            if (cap == null) continue;
            cap.fill(1_000, ManaTankAction.EXECUTE);
        }
    }

    public static void explosionEvent(ExplosionEvent.Detonate event) {
        // cannot cancel this event
        event.getAffectedBlocks(); // returns a list of block positions; might get slow for larger explosions because
        // players will do that
        event.getExplosion().getBlockInteraction().compareTo(Explosion.BlockInteraction.TRIGGER_BLOCK);
        event.getExplosion().canTriggerBlocks(); // extra checks, includes mob griefing rules
        event.getExplosion().radius(); // strength isnt really involved; radius is only applied
        // {WindChargeEvent#EXPLOSION_DAMAGE_CALCULATOR} references a block tag, `blocks_wind_charge_explosions`
        // Check if source is a breeze or the charge
    }
}
