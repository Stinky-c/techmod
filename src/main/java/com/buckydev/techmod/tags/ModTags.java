package com.buckydev.techmod.tags;

import com.buckydev.techmod.TechMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class ModBlockTags {
        public static final TagKey<Block> WIND_CHARGEABLE = TagKey.create(
                // The registry key. The type of the registry must match the generic type of the tag.
                Registries.BLOCK,
                // The location of the tag. This example will put our tag at
                // data/examplemod/tags/blocks/example_tag.json.
                TechMod.createRL("wind_chargeable"));
    }

    /**
     * Helper to build a common tag, apply the neoforge conventions to enable interop with other mods
     * <pre>
     *     Naming the tag itself also has some conventions to follow:
     *
     *     - Use the plural form. E.g.: minecraft:planks, c:ingots.
     *     - Use folders for multiple objects of the same type, and an overall tag for each folder. E.g.: c:ingots/iron, c:ingots/gold, and c:ingots containing both. (Note: This is a NeoForge convention, Minecraft does not follow this convention for most tags.)
     * </pre>
     * @param name
     * @return
     */
    public static ResourceLocation common(String name) {
        return ResourceLocation.fromNamespaceAndPath("c", name);
    }
}
