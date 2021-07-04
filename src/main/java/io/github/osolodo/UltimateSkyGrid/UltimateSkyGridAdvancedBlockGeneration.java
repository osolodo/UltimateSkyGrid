package io.github.osolodo.UltimateSkyGrid;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.*;
import org.bukkit.block.data.Bisected;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import java.util.Random;

public class UltimateSkyGridAdvancedBlockGeneration extends BlockPopulator {

    private static final Material[] logVariantsNorm = {
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.OAK_LOG,
            Material.SPRUCE_LOG
    };
    private static final Material[] logVariantsNether = {
            Material.CRIMSON_STEM,
            Material.WARPED_STEM,
            Material.CRIMSON_HYPHAE,
            Material.WARPED_HYPHAE
    };
    private static final Material[] dirtPopulatable = {
            Material.ACACIA_SAPLING,
            Material.BIRCH_SAPLING,
            Material.DARK_OAK_SAPLING,
            Material.JUNGLE_SAPLING,
            Material.OAK_SAPLING,
            Material.SPRUCE_SAPLING,
            Material.BAMBOO_SAPLING,
            Material.BROWN_MUSHROOM,
            Material.RED_MUSHROOM
    };
    private static final Material[] grassPopulatable = {
            Material.DANDELION,
            Material.POPPY,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.ORANGE_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.OXEYE_DAISY,
            Material.CORNFLOWER,
            Material.LILY_OF_THE_VALLEY,
            Material.SUGAR_CANE,
            Material.SUNFLOWER,
            Material.LILAC,
            Material.ROSE_BUSH,
            Material.PEONY,
            Material.PEONY,
            Material.TALL_GRASS,
            Material.LARGE_FERN,
    };
    private static final Material[] bisectedMaterials = {
            Material.SUNFLOWER,
            Material.LILAC,
            Material.ROSE_BUSH,
            Material.PEONY,
            Material.PEONY,
            Material.TALL_GRASS,
            Material.LARGE_FERN,
            Material.TALL_SEAGRASS
    };
    private static final LootTables[] normChests = {
            LootTables.ABANDONED_MINESHAFT,
            LootTables.BURIED_TREASURE,
            LootTables.DESERT_PYRAMID,
            LootTables.IGLOO_CHEST,
            LootTables.JUNGLE_TEMPLE,
            LootTables.PILLAGER_OUTPOST,
            LootTables.RUINED_PORTAL,
            LootTables.SHIPWRECK_SUPPLY,
            LootTables.SHIPWRECK_TREASURE,
            LootTables.SIMPLE_DUNGEON,
            LootTables.SPAWN_BONUS_CHEST,
            LootTables.STRONGHOLD_CORRIDOR,
            LootTables.STRONGHOLD_CROSSING,
            LootTables.STRONGHOLD_LIBRARY,
            LootTables.UNDERWATER_RUIN_BIG,
            LootTables.UNDERWATER_RUIN_SMALL,
            LootTables.VILLAGE_ARMORER,
            LootTables.VILLAGE_BUTCHER,
            LootTables.VILLAGE_DESERT_HOUSE,
            LootTables.VILLAGE_FISHER,
            LootTables.VILLAGE_FLETCHER,
            LootTables.VILLAGE_MASON,
            LootTables.VILLAGE_PLAINS_HOUSE,
            LootTables.VILLAGE_SAVANNA_HOUSE,
            LootTables.VILLAGE_SHEPHERD,
            LootTables.VILLAGE_SNOWY_HOUSE,
            LootTables.VILLAGE_TAIGA_HOUSE,
            LootTables.VILLAGE_TANNERY,
            LootTables.VILLAGE_TEMPLE,
            LootTables.VILLAGE_TOOLSMITH,
            LootTables.VILLAGE_WEAPONSMITH,
            LootTables.WOODLAND_MANSION,
    };
    private static final LootTables[] netherChests = {
            LootTables.NETHER_BRIDGE,
            LootTables.BASTION_TREASURE,
            LootTables.BASTION_OTHER,
            LootTables.BASTION_BRIDGE,
            LootTables.BASTION_HOGLIN_STABLE,
    };
    private static final LootTables[] endChests = {
            LootTables.END_CITY_TREASURE
    };

    public void populate(World world, Random random, Chunk chunk) {

        int wH = world.getMaxHeight();

        for (int x = 1; x < 16; x += 4) {
            for (int y = 0; y < wH; y += 4) {
                for (int z = 1; z < 16; z += 4) {

                    Block blk = chunk.getBlock(x, y, z);

                    if (blk.getType() == Material.CHEST) {
                        populateChest(world, random, blk);
                    } else if (blk.getType() == Material.GRASS_BLOCK) {
                        Material mat = getPopulating(random, grassPopulatable);
                        Block bottom = blk.getRelative(BlockFace.UP);
                        bottom.setType(mat, false);
                        boolean bisected = bottom.getBlockData() instanceof Bisected;
                        if (mat == Material.SUGAR_CANE || bisected) {
                            Block top = blk.getRelative(BlockFace.UP).getRelative(BlockFace.UP);
                            top.setType(mat, false);
                            if (bisected) {
                                Bisected topBlockData = ((Bisected) top.getBlockData());
                                topBlockData.setHalf(Bisected.Half.TOP);
                                BlockState topState = top.getState();
                                topState.setBlockData(topBlockData);
                                topState.update(false, false);
                            }
                        }
                    } else if (blk.getType() == Material.DIRT) {
                        blk.getRelative(BlockFace.UP).setType(getPopulating(random, dirtPopulatable, 2), false);
                    } else if (blk.getType() == Material.SAND) {
                        Block up = blk.getRelative(BlockFace.UP);
                        Material mat = getPopulating(random, Material.CACTUS, 10);
                        if (mat != Material.AIR) {
                            up.setType(mat, false);
                            up.getRelative(BlockFace.UP).setType(mat, false);
                        }
                    } else if (blk.getType() == Material.SOUL_SAND) {
                        blk.getRelative(BlockFace.UP).setType(getPopulating(random, Material.NETHER_WART, 10), false);
                    } else if (blk.getType() == Material.SPAWNER) {
                        CreatureSpawner spawner = (CreatureSpawner) blk.getState();
                        spawner.setSpawnedType(getSpawnerType(world, random));
                        spawner.update();
                    } else if (blk.getType() == Material.OAK_LOG) {
                        if (UltimateSkyGrid.allBlocksOneWorld) {
                            int pick = random.nextInt(logVariantsNorm.length + logVariantsNether.length);
                            if (pick < logVariantsNorm.length) {
                                blk.setType(logVariantsNorm[pick], false);
                            } else {
                                blk.setType(logVariantsNether[pick - logVariantsNorm.length], false);
                            }
                            //NOTE: These are not equal so that if logs are in the config to spawn in the end they are still randomised
                        } else if (world.getEnvironment() != Environment.NETHER) {
                            blk.setType(getPopulating(random, logVariantsNorm), false);
                        } else if (world.getEnvironment() != Environment.NORMAL) {
                            blk.setType(getPopulating(random, logVariantsNether), false);
                        }
                    }
                }
            }
        }
    }

    public static Material getPopulating(Random random, Material[] options, int scarcity) {
        int p = random.nextInt(scarcity);
        if (p < 1) {
            return getPopulating(random, options);
        } else {
            return Material.AIR;
        }
    }

    public static Material getPopulating(Random random, Material onlyOption, int scarcity) {
        int p = random.nextInt(scarcity);
        if (p < 1) {
            return onlyOption;
        } else {
            return Material.AIR;
        }
    }

    public static <T> T getPopulating(Random random, T[] options) {
        return options[random.nextInt(options.length)];
    }

    public static EntityType getSpawnerType(World world, Random random) {
        int pickList = random.nextInt(300);
        if (!UltimateSkyGrid.allBlocksOneWorld) {
            pickList %= 100;
            if (world.getEnvironment() == Environment.NETHER) {
                pickList += 100;
            } else if (world.getEnvironment() == Environment.THE_END) {
                pickList += 200;
            }
        }
        if (pickList < UltimateSkyGrid.cSpawnMythic) {
            return getPopulating(random, UltimateSkyGrid.iNormSpawnMythic);
        } else if (pickList < UltimateSkyGrid.cSpawnRare) {
            return getPopulating(random, UltimateSkyGrid.iNormSpawnRare);
        } else if (pickList < 100) {
            return getPopulating(random, UltimateSkyGrid.iNormSpawnAbundant);
        } else if (pickList < 100 + UltimateSkyGrid.cSpawnMythic) {
            return getPopulating(random, UltimateSkyGrid.iNetherSpawnMythic);
        } else if (pickList < 100 + UltimateSkyGrid.cSpawnRare) {
            return getPopulating(random, UltimateSkyGrid.iNetherSpawnRare);
        } else if (pickList < 200) {
            return getPopulating(random, UltimateSkyGrid.iNetherSpawnAbundant);
        } else if (pickList < 200 + UltimateSkyGrid.cSpawnMythic) {
            return getPopulating(random, UltimateSkyGrid.iEndSpawnMythic);
        } else if (pickList < 200 + UltimateSkyGrid.cSpawnRare) {
            return getPopulating(random, UltimateSkyGrid.iEndSpawnRare);
        } else {
            return getPopulating(random, UltimateSkyGrid.iEndSpawnAbundant);
        }
    }

    public void populateChest(World world, Random random, Block blk) {
        final LootTable lootTable;
        Environment env = world.getEnvironment();
        if (!UltimateSkyGrid.allBlocksOneWorld) {
            if (env == Environment.THE_END) {
                lootTable = getPopulating(random, endChests).getLootTable();
            } else if (env == Environment.NETHER) {
                lootTable = getPopulating(random, netherChests).getLootTable();
            } else {
                lootTable = getPopulating(random, normChests).getLootTable();
            }
        } else {
            int pick = random.nextInt(100);
            if (pick < UltimateSkyGrid.cChestEnd) {
                lootTable = getPopulating(random, endChests).getLootTable();
            } else if (pick < UltimateSkyGrid.cChestNether) {
                lootTable = getPopulating(random, netherChests).getLootTable();
            } else {
                lootTable = getPopulating(random, normChests).getLootTable();
            }
        }

        Bukkit.getScheduler().runTaskLater(UltimateSkyGrid.getPlugin(UltimateSkyGrid.class), () -> {
            Chest chest = (Chest) blk.getState();
            chest.setLootTable(lootTable);
            chest.update(false, false);
        }, random.nextInt(20));
    }
}