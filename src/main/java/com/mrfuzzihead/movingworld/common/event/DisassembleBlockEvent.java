package com.mrfuzzihead.movingworld.common.event;

import com.mrfuzzihead.movingworld.common.chunk.LocatedBlock;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by DarkEvilMac on 2/22/2015.
 */

public class DisassembleBlockEvent extends Event {

    public LocatedBlock block;

    public DisassembleBlockEvent(LocatedBlock block) {
        this.block = block;
    }

}
