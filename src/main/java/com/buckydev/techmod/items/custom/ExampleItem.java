package com.buckydev.techmod.items.custom;

import com.buckydev.techmod.items.BaseModItem;
import com.buckydev.techmod.items.interfaces.IAppendHoverTooltip;
import java.util.List;
import net.minecraft.network.chat.Component;

public class ExampleItem extends BaseModItem implements IAppendHoverTooltip {

    public ExampleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void getHoverDetails(List<Component> componentList) {
        componentList.add(Component.translatable("tooltip.techmod.exmaple_item"));
    }
}
