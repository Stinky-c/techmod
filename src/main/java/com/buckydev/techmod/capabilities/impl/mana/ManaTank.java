package com.buckydev.techmod.capabilities.impl.mana;

import com.buckydev.techmod.capabilities.interfaces.mana.IManaTank;
import com.buckydev.techmod.capabilities.interfaces.mana.IManaTankModifiable;

public class ManaTank implements IManaTank, IManaTankModifiable {

    private final Runnable contentsChange;

    private int capacity;
    private int amount;

    public ManaTank(int capacity, Runnable contentsChange) {
        this.capacity = capacity;
        this.amount = 0;
        this.contentsChange = contentsChange;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int fill(int newAmount, ManaTankAction action) {
        //        BreezeUtil
        int filled = this.capacity - this.amount;
        if (newAmount < filled) {
            this.amount += newAmount;
            filled = newAmount;
        } else {
            this.amount = this.capacity;
        }
        if (filled > 0) {
            // Contents change
        }
        return filled;
    }

    @Override
    public int drain(int maxDrain, ManaTankAction action) {
        return 0;
    }

    @Override
    public void setCapacity(int amount) {
        this.capacity = amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void onContentsChange() {
        this.contentsChange.run();
    }
}
