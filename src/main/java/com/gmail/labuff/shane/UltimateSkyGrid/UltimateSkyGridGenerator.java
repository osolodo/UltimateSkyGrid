package com.gmail.labuff.shane.UltimateSkyGrid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class UltimateSkyGridGenerator extends ChunkGenerator {

    public int worldHeight;

    public boolean canSpawn(World world, int x, int z) {

        return true;
    }


    static void setBlock(byte[][] result, int x, int y, int z, byte blkid) {

        if (result[(y >> 4)] == null) {

            result[(y >> 4)] = new byte[4096];
        }

        result[(y >> 4)][((y & 0xF) << 8 | z << 4 | x)] = blkid;
    }

    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid biomes) {


        if (world.getEnvironment() == World.Environment.NETHER) {

            worldHeight = UltimateSkyGrid.cNetherHeight;
        } else if (world.getEnvironment() == World.Environment.NORMAL) {

            worldHeight = UltimateSkyGrid.cHeight;
        } else {

            worldHeight = UltimateSkyGrid.cEndHeight;
        }

        byte[][] result = new byte[worldHeight / 16][];
        if (UltimateSkyGrid.genGlass) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < worldHeight; y++) {
                    for (int z = 0; z < 16; z++) {
                        if (x % 4 == 0 && y % 4 == 0 && z % 4 == 0) {

                            int ID = getRandBlockID(world, random, UltimateSkyGrid.allBlocksOneWorld);
                            setBlock(result, x, y, z, (byte) ID);
                        } else {
                            if (y < (UltimateSkyGrid.cHeight - 3)) {

                                int ID = Material.GLASS.getId();
                                setBlock(result, x, y, z, (byte) ID);
                            }
                        }
                    }
                }
            }
        } else {

            for (int x = 0; x < 16; x += 4) {
                for (int y = 0; y < UltimateSkyGrid.cHeight; y += 4) {
                    for (int z = 0; z < 16; z += 4) {

                        int ID = getRandBlockID(world, random, UltimateSkyGrid.allBlocksOneWorld);
                        setBlock(result, x, y, z, (byte) ID);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {

        return new Location(world, 0.5D, (double) worldHeight, 0.5D);
    }


    public List<BlockPopulator> getDefaultPopulators(World world) {

        return Arrays.asList(new BlockPopulator[]{new UltimateSkyGridPopulator()});
    }

    public int getRandBlockID(World world, Random random, boolean allBlocks) {

        int randID;
        int r;
        if (world.getEnvironment() == World.Environment.NORMAL) {
            if (allBlocks) {

                r = random.nextInt(10000);

                if (r < UltimateSkyGrid.cMythic) {
                    randID = UltimateSkyGrid.iMythic[random.nextInt(UltimateSkyGrid.iMythic.length)];
                    return randID;
                } else if (r < UltimateSkyGrid.cUnique) {
                    randID = UltimateSkyGrid.iUnique[random.nextInt(UltimateSkyGrid.iUnique.length)];
                    return randID;
                } else if (r < UltimateSkyGrid.cRare) {
                    randID = UltimateSkyGrid.iRare[random.nextInt(UltimateSkyGrid.iRare.length)];
                    return randID;
                } else if (r < UltimateSkyGrid.cUncommon) {
                    randID = UltimateSkyGrid.iUncommon[random.nextInt(UltimateSkyGrid.iUncommon.length)];
                    return randID;
                } else {
                    randID = UltimateSkyGrid.iAbundant[random.nextInt(UltimateSkyGrid.iAbundant.length)];
                    return randID;
                }
            } else {

                r = random.nextInt(10000);

                if (r < UltimateSkyGrid.cMythic) {
                    randID = UltimateSkyGrid.iNormMythic[random.nextInt(UltimateSkyGrid.iNormMythic.length)];
                    return randID;
                } else if (r < UltimateSkyGrid.cUnique) {
                    randID = UltimateSkyGrid.iNormUnique[random.nextInt(UltimateSkyGrid.iNormUnique.length)];
                    return randID;
                } else if (r < UltimateSkyGrid.cRare) {
                    randID = UltimateSkyGrid.iNormRare[random.nextInt(UltimateSkyGrid.iNormRare.length)];
                    return randID;
                } else if (r < UltimateSkyGrid.cUncommon) {
                    randID = UltimateSkyGrid.iNormUncommon[random.nextInt(UltimateSkyGrid.iNormUncommon.length)];
                    return randID;
                } else {
                    randID = UltimateSkyGrid.iNormAbundant[random.nextInt(UltimateSkyGrid.iNormAbundant.length)];
                    return randID;
                }
            }
        } else if (world.getEnvironment() == World.Environment.NETHER) {

            r = random.nextInt(100);

            if (r < UltimateSkyGrid.cNethRare) {
                randID = UltimateSkyGrid.iNethBlkRare[random.nextInt(UltimateSkyGrid.iNethBlkRare.length)];
                return randID;
            } else {
                randID = UltimateSkyGrid.iNethBlkNorm[random.nextInt(UltimateSkyGrid.iNethBlkNorm.length)];
                return randID;
            }
        } else {

            r = random.nextInt(100);

            if (r < UltimateSkyGrid.cEndRare) {
                randID = UltimateSkyGrid.iEndBlkRare[random.nextInt(UltimateSkyGrid.iEndBlkRare.length)];
                return randID;
            } else {
                randID = UltimateSkyGrid.iEndBlkNorm[random.nextInt(UltimateSkyGrid.iEndBlkNorm.length)];
                return randID;
            }
        }
    }
}