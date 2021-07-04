package io.github.osolodo.UltimateSkyGrid;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class UltimateSkyGridGenerator extends ChunkGenerator {

    public boolean canSpawn(World world, int x, int z) {
        return x % 4 == 1 && z % 4 == 1;
    }

    private int worldHeight(World world) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            return UltimateSkyGrid.cNetherHeight;
        } else if (world.getEnvironment() == World.Environment.NORMAL) {
            return UltimateSkyGrid.cHeight;
        } else {
            return UltimateSkyGrid.cEndHeight;
        }
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        if (UltimateSkyGrid.genGlass) {
            for (int x = 1; x < 16; x++) {
                for (int y = 0; y < worldHeight(world); y++) {
                    for (int z = 1; z < 16; z++) {
                        if (x % 4 == 1 && y % 4 == 0 && z % 4 == 1) {
                            chunk.setBlock(x, y, z, getRandBlock(world, random, UltimateSkyGrid.allBlocksOneWorld));
                        } else {
                            if (y < (UltimateSkyGrid.cHeight - 3)) {
                                chunk.setBlock(x, y, z, Material.GLASS);
                            }
                        }
                    }
                }
            }
        } else {
            for (int x = 1; x < 16; x += 4) {
                for (int y = 0; y < worldHeight(world); y += 4) {
                    for (int z = 1; z < 16; z += 4) {
                        chunk.setBlock(x, y, z, getRandBlock(world, random, UltimateSkyGrid.allBlocksOneWorld));
                    }
                }
            }
        }

        return chunk;
    }

//    @Override
//    public Location getFixedSpawnLocation(World world, Random random) {
//        return new Location(world, 1.5D, worldHeight(world), 1.5D);
//    }

    public List<BlockPopulator> getDefaultPopulators(World world) {

        return Arrays.asList(new BlockPopulator[]{new UltimateSkyGridPopulator()});
    }

    public Material getRandBlock(World world, Random random, boolean allBlocks) {

        int r;
        if (world.getEnvironment() == World.Environment.NORMAL) {
            if (allBlocks) {

                r = random.nextInt(10000);

                if (r < UltimateSkyGrid.cMythic) {
                    return UltimateSkyGrid.iMythic[random.nextInt(UltimateSkyGrid.iMythic.length)];
                } else if (r < UltimateSkyGrid.cUnique) {
                    return UltimateSkyGrid.iUnique[random.nextInt(UltimateSkyGrid.iUnique.length)];
                } else if (r < UltimateSkyGrid.cRare) {
                    return UltimateSkyGrid.iRare[random.nextInt(UltimateSkyGrid.iRare.length)];
                } else if (r < UltimateSkyGrid.cUncommon) {
                    return UltimateSkyGrid.iUncommon[random.nextInt(UltimateSkyGrid.iUncommon.length)];
                } else {
                    return UltimateSkyGrid.iAbundant[random.nextInt(UltimateSkyGrid.iAbundant.length)];
                }
            } else {

                r = random.nextInt(10000);

                if (r < UltimateSkyGrid.cMythic) {
                    return UltimateSkyGrid.iNormMythic[random.nextInt(UltimateSkyGrid.iNormMythic.length)];
                } else if (r < UltimateSkyGrid.cUnique) {
                    return UltimateSkyGrid.iNormUnique[random.nextInt(UltimateSkyGrid.iNormUnique.length)];
                } else if (r < UltimateSkyGrid.cRare) {
                    return UltimateSkyGrid.iNormRare[random.nextInt(UltimateSkyGrid.iNormRare.length)];
                } else if (r < UltimateSkyGrid.cUncommon) {
                    return UltimateSkyGrid.iNormUncommon[random.nextInt(UltimateSkyGrid.iNormUncommon.length)];
                } else {
                    return UltimateSkyGrid.iNormAbundant[random.nextInt(UltimateSkyGrid.iNormAbundant.length)];
                }
            }
        } else if (world.getEnvironment() == World.Environment.NETHER) {

            r = random.nextInt(100);

            if (r < UltimateSkyGrid.cNethRare) {
                return UltimateSkyGrid.iNethBlkRare[random.nextInt(UltimateSkyGrid.iNethBlkRare.length)];
            } else {
                return UltimateSkyGrid.iNethBlkNorm[random.nextInt(UltimateSkyGrid.iNethBlkNorm.length)];
            }
        } else {

            r = random.nextInt(100);

            if (r < UltimateSkyGrid.cEndRare) {
                return UltimateSkyGrid.iEndBlkRare[random.nextInt(UltimateSkyGrid.iEndBlkRare.length)];
            } else {
                return UltimateSkyGrid.iEndBlkNorm[random.nextInt(UltimateSkyGrid.iEndBlkNorm.length)];
            }
        }
    }
}