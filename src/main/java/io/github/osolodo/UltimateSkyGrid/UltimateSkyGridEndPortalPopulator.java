package io.github.osolodo.UltimateSkyGrid;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class UltimateSkyGridEndPortalPopulator extends BlockPopulator {

    private static final int xPos = 1;
    private static final int yPos = 0;
    private static final int zPos = 1;

    public void populate(World world, Random random, Chunk chunk) {
        if (chunk.getX() == 0 && chunk.getZ() == 0) {
            for (int x = xPos + 1; x < xPos + 4; x++) {
                setBlockToPortal(chunk, x, yPos, zPos, random, BlockFace.SOUTH);
            }
            for (int x = xPos + 1; x < xPos + 4; x++) {
                setBlockToPortal(chunk, x, yPos, zPos + 4, random, BlockFace.NORTH);
            }
            for (int z = zPos + 1; z < zPos + 4; z++) {
                setBlockToPortal(chunk, xPos, yPos, z, random, BlockFace.EAST);
            }
            for (int z = zPos + 1; z < zPos + 4; z++) {
                setBlockToPortal(chunk, xPos + 4, yPos, z, random, BlockFace.WEST);
            }
        }
    }

    private void setBlockToPortal(Chunk chunk, int x, int y, int z, Random random, BlockFace facing) {
        Block block = chunk.getBlock(x, y, z);
        block.setType(Material.END_PORTAL_FRAME, false);
        BlockState state = block.getState();
        EndPortalFrame frame = (EndPortalFrame) state.getBlockData();
        frame.setFacing(facing);
        frame.setEye(random.nextInt(10) == 0);
        state.setBlockData(frame);
        state.update(false, false);
    }
}
