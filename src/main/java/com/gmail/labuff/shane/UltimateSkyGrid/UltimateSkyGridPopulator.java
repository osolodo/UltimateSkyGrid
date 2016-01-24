package com.gmail.labuff.shane.UltimateSkyGrid;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class UltimateSkyGridPopulator extends BlockPopulator {

    int[] spawnEgg = {

            61, //EntityType.BLAZE,
            56, //EntityType.GHAST,
            62, //EntityType.MAGMA_CUBE,
            50, //EntityType.CREEPER,
            58, //EntityType.ENDERMAN,
            54, //EntityType.ZOMBIE,
            51, //EntityType.SKELETON,
            52, //EntityType.SPIDER,
            57, //EntityType.PIG_ZOMBIE,
            55, //EntityType.SLIME,
            90, //EntityType.PIG,
            91, //EntityType.SHEEP,
            93, //EntityType.CHICKEN,
            92, //EntityType.COW,
            96, //EntityType.MUSHROOM_COW,
            95, //EntityType.WOLF,
            //53, //EntityType.GIANT,
            120, //EntityType.VILLAGER,
            59, //EntityType.CAVE_SPIDER,
            //97, //EntityType.SNOWMAN,
            98, //EntityType.OCELOT,
            //64, //EntityType.WITHER,
            66};//EntityType.WITCH

    public void populate(World world, Random random, Chunk chunk) {

        int wH = world.getMaxHeight();

        for (int x = 0; x < 16; x += 4) {
            for (int y = 0; y < wH; y += 4) {
                for (int z = 0; z < 16; z += 4) {

                    Block blk = chunk.getBlock(x, y, z);

                    if (blk.getType() == Material.CHEST) {

                        Chest chest = (Chest) blk.getState();
                        populateChest(world, random, UltimateSkyGrid.allBlocksOneWorld, chest);

                    } else if (blk.getType() == Material.GRASS) {

                        blk.getRelative(BlockFace.UP).setType(getGrassPop());
                    } else if (blk.getType() == Material.DIRT) {

                        Random r = new Random();
                        blk.getRelative(BlockFace.UP).setType(getDirtPop());
                        if (blk.getRelative(BlockFace.UP).getType() == Material.SAPLING) {
                            blk.getRelative(BlockFace.UP).setData((byte) r.nextInt(4));
                        }

                    } else if (blk.getType() == Material.SAND) {

                        Random r = new Random();
                        int a = r.nextInt(10);

                        if (a < 1) {

                            blk.getRelative(BlockFace.UP).setTypeId(Material.CACTUS.getId(), false);
                        } else {

                            blk.getRelative(BlockFace.UP).setTypeId(Material.AIR.getId(), false);
                        }

                    } else if (blk.getType() == Material.SOUL_SAND) {

                        blk.getRelative(BlockFace.UP).setType(getSoulPop());
                    } else if (blk.getType() == Material.MOB_SPAWNER) {
                        if (world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END) {
                            if (world.getEnvironment() == World.Environment.NETHER) {

                                CreatureSpawner spawner = (CreatureSpawner) blk.getState();
                                spawner.setSpawnedType(getNetherEntity());
                            } else {

                                CreatureSpawner spawner = (CreatureSpawner) blk.getState();
                                spawner.setSpawnedType(EntityType.ENDERMAN);
                            }
                        } else {
                            if (!(UltimateSkyGrid.allBlocksOneWorld)) {


                                CreatureSpawner spawner = (CreatureSpawner) blk.getState();
                                spawner.setSpawnedType(getNormEntity());

                            } else {

                                CreatureSpawner spawner = (CreatureSpawner) blk.getState();
                                spawner.setSpawnedType(getEntityType());

                            }
                        }
                    } else if (blk.getType() == Material.LOG) {

                        Random rnd = new Random();
                        blk.setData((byte) rnd.nextInt(4));
                    }
                }
            }
        }
    }

    public static EntityType getEntityType() {

        EntityType[] mobHosNorm = {
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.SPIDER,
                EntityType.PIG_ZOMBIE,
                EntityType.SLIME};

        EntityType[] mobHosRare = {
                EntityType.BLAZE,
                EntityType.GHAST,
                EntityType.MAGMA_CUBE,
                EntityType.CREEPER,
                EntityType.ENDERMAN};

        EntityType[] mobNorm = {
                EntityType.PIG,
                EntityType.SHEEP,
                EntityType.CHICKEN};

        EntityType[] mobRare = {
                EntityType.COW,
                EntityType.MUSHROOM_COW};

        Random random = new Random();
        int c = random.nextInt(100);
        EntityType entRet;

        if (c < 2) {
            entRet = mobHosRare[random.nextInt(mobHosRare.length)];

        } else if (c < 5) {
            entRet = mobRare[random.nextInt(mobRare.length)];

        } else if (c < 14) {
            entRet = mobHosNorm[random.nextInt(mobHosNorm.length)];

        } else {
            entRet = mobNorm[random.nextInt(mobNorm.length)];

        }
        return entRet;
    }

    public EntityType getNormEntity() {

        EntityType[] mobHosNorm = {
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.SPIDER,
                EntityType.SLIME};

        EntityType[] mobHosRare = {
                EntityType.CREEPER,
                EntityType.ENDERMAN};

        EntityType[] mobNorm = {
                EntityType.PIG,
                EntityType.SHEEP,
                EntityType.CHICKEN};

        EntityType[] mobRare = {
                EntityType.COW,
                EntityType.MUSHROOM_COW};

        Random random = new Random();
        int c = random.nextInt(100);
        EntityType ent;

        if (c < 2) {

            ent = mobHosRare[random.nextInt(mobHosRare.length)];
        } else if (c < 5) {

            ent = mobRare[random.nextInt(mobRare.length)];
        } else if (c < 14) {

            ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        } else {

            ent = mobNorm[random.nextInt(mobNorm.length)];
        }
        return ent;
    }

    public EntityType getNetherEntity() {

        EntityType[] mobHosNorm = {
                EntityType.PIG_ZOMBIE};

        EntityType[] mobHosRare = {
                EntityType.BLAZE,
                EntityType.GHAST,
                EntityType.MAGMA_CUBE};

        Random random = new Random();
        int c = random.nextInt(100);
        EntityType ent;

        if (c < 2) {

            ent = mobHosRare[random.nextInt(mobHosRare.length)];
        } else {

            ent = mobHosNorm[random.nextInt(mobHosNorm.length)];
        }
        return ent;
    }

    public static Material getGrassPop() {
        Random rand = new Random();
        int p = rand.nextInt(100);

        if (p < 5) {

            Material popMat = Material.RED_MUSHROOM;
            return popMat;
        } else if (p < 10) {

            Material popMat = Material.BROWN_MUSHROOM;
            return popMat;
        } else if (p < 15) {

            Material popMat = Material.RED_ROSE;
            return popMat;
        } else if (p < 20) {

            Material popMat = Material.YELLOW_FLOWER;
            return popMat;
        } else if (p < 25) {

            Material popMat = Material.SUGAR_CANE_BLOCK;
            return popMat;
        } else {
            return Material.AIR;
        }
    }

    public static Material getSoulPop() {
        Random random = new Random();
        int a = random.nextInt(10);

        if (a < 1) {

            return Material.NETHER_WARTS;
        } else {

            return Material.AIR;
        }
    }


    public static Material getDirtPop() {

        Random r = new Random();
        int p = r.nextInt(10);
        if (p < 1) {

            return Material.SAPLING;
        } else {

            return Material.AIR;
        }
    }

    public void populateChest(World world, Random random, boolean allBlocks, Chest chest) {

        Environment env = world.getEnvironment();
        int maxI;

        int[] itemMythicID = {0};
        int[] itemMythicAmount = {0};
        int[] itemRareID;
        int[] itemRareAmount;
        int[] itemID;
        int[] itemAmount;
        int mythChance = 0;
        int rareChance;


        if (env == World.Environment.NETHER || env == World.Environment.THE_END) {

            if (env == World.Environment.NETHER) {

                rareChance = UltimateSkyGrid.cNethChRare;
                itemRareID = UltimateSkyGrid.iNethChRare;
                itemRareAmount = UltimateSkyGrid.iNethChRareAmount;
                itemID = UltimateSkyGrid.iNethChNorm;
                itemAmount = UltimateSkyGrid.iNethChNormAmount;
                int a = random.nextInt(10);
                int preMax1 = random.nextInt(2);
                int preMax2 = random.nextInt(5);

                if (a < 2) {

                    maxI = 1 + preMax2;
                } else {

                    maxI = 1 + preMax1;
                }
            } else {

                rareChance = UltimateSkyGrid.cEndChRare;
                itemRareID = UltimateSkyGrid.iEndChRare;
                itemRareAmount = UltimateSkyGrid.iEndChRareAmount;
                itemID = UltimateSkyGrid.iEndChNorm;
                itemAmount = UltimateSkyGrid.iEndChNormAmount;
                int a = random.nextInt(10);
                int preMax1 = random.nextInt(2);
                int preMax2 = random.nextInt(5);

                if (a < 2) {

                    maxI = 1 + preMax2;
                } else {

                    maxI = 1 + preMax1;
                }
            }
        } else {
            if (allBlocks) {

                mythChance = UltimateSkyGrid.cChMythic;
                rareChance = UltimateSkyGrid.cChRare;
                itemMythicID = UltimateSkyGrid.iChMythic;
                itemMythicAmount = UltimateSkyGrid.iChMythicAmount;
                itemRareID = UltimateSkyGrid.iChRare;
                itemRareAmount = UltimateSkyGrid.iChRareAmount;
                itemID = UltimateSkyGrid.iChNormal;
                itemAmount = UltimateSkyGrid.iChNormalAmount;
                int a = random.nextInt(10);
                int preMax1 = random.nextInt(4);
                int preMax2 = random.nextInt(10);

                if (a < 2) {

                    maxI = 1 + preMax2;
                } else {

                    maxI = 1 + preMax1;
                }
            } else {

                mythChance = UltimateSkyGrid.cChMythic;
                rareChance = UltimateSkyGrid.cChRare;
                itemMythicID = UltimateSkyGrid.iNormChMythic;
                itemMythicAmount = UltimateSkyGrid.iNormChMythicAmount;
                itemRareID = UltimateSkyGrid.iNormChRare;
                itemRareAmount = UltimateSkyGrid.iNormChRareAmount;
                itemID = UltimateSkyGrid.iNormChNormal;
                itemAmount = UltimateSkyGrid.iNormChNormalAmount;
                int a = random.nextInt(10);
                int preMax1 = random.nextInt(4);
                int preMax2 = random.nextInt(10);

                if (a < 2) {

                    maxI = 1 + preMax2;
                } else {

                    maxI = 1 + preMax1;
                }
            }
        }

        for (int i = 0; i < maxI; i++) {


            Inventory inv = chest.getInventory();
            int quality = random.nextInt(100);

            if (quality < rareChance) {
                if (quality < mythChance && env == World.Environment.NORMAL) {
                    int amount;
                    int aPos = random.nextInt(itemMythicID.length);
                    int ID = itemMythicID[aPos];
                    int maxAmount = itemMythicAmount[aPos];
                    if (maxAmount == 1) {

                        amount = 1;
                    } else {

                        amount = random.nextInt(maxAmount) + 1;
                    }

                    ItemStack itm = new ItemStack(ID, amount, (short) 0);

                    if (itm.getType() == Material.MONSTER_EGG) {

                        Random rdm = new Random();
                        itm = new ItemStack(Material.MONSTER_EGG, 1, (short) spawnEgg[rdm.nextInt(spawnEgg.length)]);
                        inv.addItem(itm);

                    } else {

                        inv.addItem(itm);

                    }
                } else {

                    int amount;
                    int aPos = random.nextInt(itemRareID.length);
                    int ID = itemRareID[aPos];
                    int maxAmount = itemRareAmount[aPos];

                    if (maxAmount == 1) {

                        amount = 1;
                    } else {

                        amount = random.nextInt(maxAmount) + 1;
                    }

                    ItemStack itm = new ItemStack(ID, amount, (short) 0);

                    if (itm.getType() == Material.MONSTER_EGG || itm.getType() == Material.LOG) {
                        if (itm.getType() == Material.MONSTER_EGG) {

                            itm = new ItemStack(Material.MONSTER_EGG, amount, (short) spawnEgg[random.nextInt(spawnEgg.length)]);
                            inv.addItem(itm);

                        } else {

                            itm = new ItemStack(Material.LOG, amount, (short) random.nextInt(4));
                            inv.addItem(itm);
                        }
                    } else {

                        inv.addItem(itm);

                    }
                }
            } else {

                int amount;
                int aPos = random.nextInt(itemID.length);
                int ID = itemID[aPos];
                int maxAmount = itemAmount[aPos];

                if (maxAmount == 1) {

                    amount = 1;
                } else {

                    amount = random.nextInt(maxAmount) + 1;
                }

                ItemStack itm = new ItemStack(ID, amount, (short) 0);

                if (itm.getType() == Material.MONSTER_EGG || itm.getType() == Material.LOG) {
                    if (itm.getType() == Material.MONSTER_EGG) {

                        itm = new ItemStack(Material.MONSTER_EGG, amount, (short) spawnEgg[random.nextInt(spawnEgg.length)]);
                        inv.addItem(itm);

                    } else {

                        itm = new ItemStack(Material.LOG, amount, (short) random.nextInt(4));
                        inv.addItem(itm);
                    }
                } else {

                    inv.addItem(itm);

                }
            }
        }
    }
}