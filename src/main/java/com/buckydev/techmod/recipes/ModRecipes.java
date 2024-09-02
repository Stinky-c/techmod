package com.buckydev.techmod.recipes;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.recipes.simple.SimpleRecipe;
import com.buckydev.techmod.recipes.simple.SimpleRecipeSerializer;
import com.buckydev.techmod.recipes.test.RightClickBlockInput;
import com.buckydev.techmod.recipes.test.RightClickBlockRecipe;
import com.buckydev.techmod.recipes.test.RightClickBlockRecipeSerializer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE =
            DeferredRegister.create(Registries.RECIPE_TYPE, TechMod.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, TechMod.MODID);

    public static final Supplier<RecipeType<RightClickBlockRecipe>> ZZZ = registerType("right_click_block");
    public static final Supplier<RecipeSerializer<RightClickBlockRecipe>> VVV =
            registerSerializer("right_click_block", RightClickBlockRecipeSerializer::new);

    public static final Supplier<RecipeType<SimpleRecipe>> SIMPLE_RECIPE = registerType("simple_recipe");
    public static final Supplier<RecipeSerializer<SimpleRecipe>> SIMPLE_RECIPE_SER =
            registerSerializer("simple_recipe", SimpleRecipeSerializer::new);

    public static <T extends Recipe<?>> Supplier<RecipeType<T>> registerType(String name) {
        return RECIPE_TYPE.register(
                name, () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(TechMod.MODID, name)));
    }

    public static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> registerSerializer(
            String name, Supplier<RecipeSerializer<T>> supplier) {
        return RECIPE_SERIALIZER.register(name, supplier);
    }

    public static void submitEventbus(IEventBus eventBus) {
        RECIPE_TYPE.register(eventBus);
        RECIPE_SERIALIZER.register(eventBus);
        //        eventBus.addListener(ModRecipes::useItemOnBlock);
        NeoForge.EVENT_BUS.addListener(ModRecipes::useItemOnBlock);
    }

    public static void useItemOnBlock(UseItemOnBlockEvent event) {
        // Skip if we are not in the block-dictated phase of the event. See the event's javadocs for details.
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) return;
        // Get the parameters we need.
        UseOnContext context = event.getUseOnContext();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemStack = context.getItemInHand();
        RecipeManager recipes = level.getRecipeManager();
        // Create an input and query the recipe.
        RightClickBlockInput input = new RightClickBlockInput(blockState, itemStack);
        var optional = recipes.getRecipeFor(ZZZ.get(), input, level);
        ItemStack result = optional.map(RecipeHolder::value)
                .map(e -> e.assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
        // If there is a result, break the block and drop the result in the world.
        if (!result.isEmpty()) {
            level.removeBlock(pos, false);
            // If the level is not a server level, don't spawn the entity.
            if (!level.isClientSide()) {
                ItemEntity entity = new ItemEntity(
                        level,
                        // Center of pos.
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        result);
                level.addFreshEntity(entity);
            }
            // Cancel the event to stop the interaction pipeline.
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
