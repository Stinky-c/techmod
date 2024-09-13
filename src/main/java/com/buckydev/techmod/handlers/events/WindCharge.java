package com.buckydev.techmod.handlers.events;

import com.buckydev.techmod.tags.ModTags.ModBlockTags;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

@SuppressWarnings("unused")
public class WindCharge {
    @SubscribeEvent
    public static void windChargeEffectBlock(ExplosionEvent.Detonate event) {
        if (event.getExplosion().getBlockInteraction() != BlockInteraction.TRIGGER_BLOCK) {
            return;
        }
        Explosion explosion = event.getExplosion();
        Level level = event.getLevel();
        var blocks = event.getAffectedBlocks();

        List<BlockPos> filtered = blocks.stream()
                .filter(blockPos -> level.getBlockState(blockPos).is(ModBlockTags.WIND_CHARGEABLE))
                .toList();
        for (var block : filtered) {
            level.setBlock(block, Blocks.DIRT.defaultBlockState(), 2);
        }
    }

    public static void explosionEvent(ExplosionEvent.Detonate event) {
        // cannot cancel this event
        event.getAffectedBlocks(); // returns a list of block positions; might get slow for larger explosions because
        // players will do that
        event.getExplosion().getBlockInteraction().compareTo(Explosion.BlockInteraction.TRIGGER_BLOCK);
        event.getExplosion().canTriggerBlocks(); // extra checks, includes mob griefing rules
        event.getExplosion().radius(); // strength isnt really involved; radius is only applied
        // {WindCharge#EXPLOSION_DAMAGE_CALCULATOR} references a block tag, `blocks_wind_charge_explosions`
        // Check if source is a breeze or the charge
    }
}
