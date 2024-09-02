package com.buckydev.techmod;

import com.buckydev.techmod.blocks.ModBlockEntities;
import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.creativeTabs.ModCreativeTab;
import com.buckydev.techmod.fluids.ModFluids;
import com.buckydev.techmod.items.ModItems;
import com.buckydev.techmod.menu.ModMenus;
import com.buckydev.techmod.recipes.ModRecipes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

/*
TODO: python script or block bench plugin to render output of a bbmodel file & VoxelShape
 */
@Mod(TechMod.MODID)
public class TechMod {
    public static final String MODID = "techmod";

    public static final Logger LOGGER = LogUtils.getLogger();

    public TechMod(IEventBus modEventBus, ModContainer modContainer) {

        ModBlocks.submitEventBus(modEventBus);
        ModItems.submitEventBus(modEventBus);
        ModFluids.submitEventBus(modEventBus);
        ModBlockEntities.submitEventBus(modEventBus);
        ModMenus.submitEventBus(modEventBus);
        ModCreativeTab.CREATIVE_TAB.register(modEventBus);
        ModRecipes.submitEventbus(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}", Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class
    // annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code

            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info(
                    "MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    public static ResourceLocation createRL(String path) {
        return ResourceLocation.fromNamespaceAndPath(TechMod.MODID, path);
    }
}
