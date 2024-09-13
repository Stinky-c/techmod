package com.buckydev.techmod.capabilities.interfaces.mana;

/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

import com.buckydev.techmod.capabilities.interfaces.IContentsChange;

public interface IManaTank extends IContentsChange {

    enum ManaTankAction {
        EXECUTE,
        SIMULATE;

        public boolean execute() {
            return this == EXECUTE;
        }

        public boolean simulate() {
            return this == SIMULATE;
        }
    }

    /**
     * @return Current amount of mana in the tank.
     */
    int getAmount();

    /**
     * @return Capacity of this mana tank.
     */
    int getCapacity();

    /**
     * @param amount Amount to fill
     * @param action   If SIMULATE, the fill will only be simulated.
     * @return Amount of mana that was accepted (or would be, if simulated) by the tank.
     */
    int fill(int amount, ManaTankAction action);

    /**
     * @param maxDrain Maximum amount of fluid to be removed from the container.
     * @param action   If SIMULATE, the drain will only be simulated.
     * @return Amount of fluid that was removed (or would be, if simulated) from the tank.
     */
    int drain(int maxDrain, ManaTankAction action);
}
