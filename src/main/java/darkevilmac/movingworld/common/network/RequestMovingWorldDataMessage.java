package darkevilmac.movingworld.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import darkevilmac.movingworld.MovingWorld;
import darkevilmac.movingworld.common.entity.EntityMovingWorld;

public class RequestMovingWorldDataMessage extends EntityMovingWorldMessage {

    public RequestMovingWorldDataMessage() {
        super();
    }

    public RequestMovingWorldDataMessage(EntityMovingWorld movingWorld) {
        super(movingWorld);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayer player) {
        if (movingWorld != null) {
            if (movingWorld.getMovingWorldChunk().chunkTileEntityMap.isEmpty()) {
                return;
            }

            TileEntitiesMessage msg = new TileEntitiesMessage(movingWorld);
            MovingWorld.instance.network.sendTo(msg, (EntityPlayerMP) player);
        }
    }
}
