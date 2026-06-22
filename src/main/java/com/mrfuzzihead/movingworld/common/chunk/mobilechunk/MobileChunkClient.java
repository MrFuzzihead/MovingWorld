package com.mrfuzzihead.movingworld.common.chunk.mobilechunk;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mrfuzzihead.movingworld.client.render.MobileChunkRenderer;
import com.mrfuzzihead.movingworld.common.entity.EntityMovingWorld;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MobileChunkClient extends MobileChunk {

    private MobileChunkRenderer renderer;

    public MobileChunkClient(World world, EntityMovingWorld movingWorld) {
        super(world, movingWorld);
        renderer = new MobileChunkRenderer(this);
    }

    public MobileChunkRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void onChunkUnload() {
        List<TileEntity> iterator = new ArrayList<TileEntity>(chunkTileEntityMap.values());
        for (TileEntity te : iterator) {
            removeChunkBlockTileEntity(te.xCoord, te.yCoord, te.zCoord);
        }
        super.onChunkUnload();
        renderer.markRemoved();
    }

    @Override
    public void setChunkModified() {
        super.setChunkModified();
        renderer.markDirty();
    }
}
