package com.buckydev.techmod.capabilities;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.capabilities.interfaces.mana.IManaTank;
import net.neoforged.neoforge.capabilities.BlockCapability;

public class ModCapabilities {

    public static final BlockCapability<IManaTank, Void> MANA_HANDLER =
            BlockCapability.createVoid(TechMod.createRL("mana"), IManaTank.class);
}
