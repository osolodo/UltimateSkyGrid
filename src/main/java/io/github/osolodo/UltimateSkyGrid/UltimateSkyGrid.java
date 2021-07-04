package io.github.osolodo.UltimateSkyGrid;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public final class UltimateSkyGrid extends JavaPlugin {

    public FileConfiguration configMain;
    public File conFileMain;
    public File dataFolder;

    public static int cHeight;
    public static int cNetherHeight;
    public static int cEndHeight;
    public static int cMythic;
    public static int cUnique;
    public static int cRare;
    public static int cUncommon;
    public static int cChestNether;
    public static int cChestEnd;
    public static int cNethRare;
    public static int cNethChRare;
    public static int cEndChRare;
    public static int cEndRare;
    public static boolean genGlass;
    public static boolean allBlocksOneWorld;
    public static Material[] iMythic;
    public static Material[] iUnique;
    public static Material[] iRare;
    public static Material[] iAbundant;
    public static Material[] iUncommon;
    public static Material[] iNormMythic;
    public static Material[] iNormUnique;
    public static Material[] iNormRare;
    public static Material[] iNormUncommon;
    public static Material[] iNormAbundant;
    public static Material[] iNethBlkRare;
    public static Material[] iNethBlkNorm;
    public static Material[] iEndBlkRare;
    public static Material[] iEndBlkNorm;
    public static EntityType[] iNormSpawnMythic;
    public static EntityType[] iNormSpawnRare;
    public static EntityType[] iNormSpawnAbundant;
    public static EntityType[] iNetherSpawnMythic;
    public static EntityType[] iNetherSpawnRare;
    public static EntityType[] iNetherSpawnAbundant;
    public static EntityType[] iEndSpawnMythic;
    public static EntityType[] iEndSpawnRare;
    public static EntityType[] iEndSpawnAbundant;
    public static int cSpawnMythic;
    public static int cSpawnRare;


    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File dFolder = getDataFolder();
        File cnFlMn = new File(getDataFolder(), "config.yml");


        this.conFileMain = cnFlMn;
        this.configMain = getConfig();
        this.dataFolder = dFolder;

        if (this.conFileMain.exists()) {
            this.configMain = YamlConfiguration.loadConfiguration(this.conFileMain);
        } else {
            copyConfig(conFileMain, getClass());
        }

        initConfig();

        getLogger().info("UltimateSkyGrid Version v0.2.1 Enabled!");
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new UltimateSkyGridGenerator();
    }

    private static void copyConfig(File config, Class<? extends UltimateSkyGrid> cl) {
        try {
            InputStream in = cl.getResourceAsStream("/" + config.getName());
            FileOutputStream out = new FileOutputStream(config);
            byte[] buffer = new byte[512];
            int i;
            while ((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            out.close();
        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Plugin jar does not appear to have the required config file for writing", e);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, null, e);
        }
    }

    public void initConfig() {

        FileConfiguration config = this.configMain;

        UltimateSkyGrid.cHeight = config.getInt("World_Height", 128);
        UltimateSkyGrid.cMythic = config.getInt("Mythic_Block_Probability", 4);
        UltimateSkyGrid.cNetherHeight = config.getInt("NetherConfig.Nether_World_Height", 128);
        UltimateSkyGrid.cEndHeight = config.getInt("EndConfig.End_World_Height", 128);
        UltimateSkyGrid.cUnique = config.getInt("Unique_Block_Probability", 181);
        UltimateSkyGrid.cRare = config.getInt("Rare_Block_Probability", 1801);
        UltimateSkyGrid.cUncommon = config.getInt("Uncommon_Block_Probability", 4001);
        UltimateSkyGrid.cChestNether = config.getInt("Chest_Prob_Nether", 15);
        UltimateSkyGrid.cChestEnd = config.getInt("Chest_Prob_End", 5);
        UltimateSkyGrid.cSpawnMythic = config.getInt("Spawn_Prob_Mythic", 10);
        UltimateSkyGrid.cSpawnRare = config.getInt("Spawn_Prob_Rare", 30);
        UltimateSkyGrid.genGlass = config.getBoolean("ReplaceAirWithGlass", false);
        UltimateSkyGrid.allBlocksOneWorld = config.getBoolean("AllBlocksOneWorld", false);
        UltimateSkyGrid.cNethRare = config.getInt("NetherConfig.NetherBlocks.RareProb", 1);
        UltimateSkyGrid.cNethChRare = config.getInt("NetherConfig.NetherChestItems.RareProb", 2);
        UltimateSkyGrid.cEndRare = config.getInt("EndConfig.EndBlocks.RareProb", 1);
        UltimateSkyGrid.cEndChRare = config.getInt("EndConfig.EndChestItems.RareProb", 1);

        UltimateSkyGrid.iMythic = configArrayToMaterialArray(config, "BlockGroups.Mythic");
        UltimateSkyGrid.iUnique = configArrayToMaterialArray(config, "BlockGroups.Unique");
        UltimateSkyGrid.iRare = configArrayToMaterialArray(config, "BlockGroups.Rare");
        UltimateSkyGrid.iUncommon = configArrayToMaterialArray(config, "BlockGroups.Uncommon");
        UltimateSkyGrid.iAbundant = configArrayToMaterialArray(config, "BlockGroups.Abundant");
        UltimateSkyGrid.iNethBlkRare = configArrayToMaterialArray(config, "NetherConfig.NetherBlocks.Rare");
        UltimateSkyGrid.iNethBlkNorm = configArrayToMaterialArray(config, "NetherConfig.NetherBlocks.Normal");
        UltimateSkyGrid.iEndBlkRare = configArrayToMaterialArray(config, "EndConfig.EndBlocks.Rare");
        UltimateSkyGrid.iEndBlkNorm = configArrayToMaterialArray(config, "EndConfig.EndBlocks.Normal");
        UltimateSkyGrid.iNormMythic = configArrayToMaterialArray(config, "NormalConfig.BlockGroups.Mythic");
        UltimateSkyGrid.iNormUnique = configArrayToMaterialArray(config, "NormalConfig.BlockGroups.Unique");
        UltimateSkyGrid.iNormRare = configArrayToMaterialArray(config, "NormalConfig.BlockGroups.Rare");
        UltimateSkyGrid.iNormUncommon = configArrayToMaterialArray(config, "NormalConfig.BlockGroups.Uncommon");
        UltimateSkyGrid.iNormAbundant = configArrayToMaterialArray(config, "NormalConfig.BlockGroups.Abundant");

        UltimateSkyGrid.iNormSpawnMythic = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Mythic");
        UltimateSkyGrid.iNormSpawnRare = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Rare");
        UltimateSkyGrid.iNormSpawnAbundant = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Abundant");
        UltimateSkyGrid.iNetherSpawnMythic = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Mythic");
        UltimateSkyGrid.iNetherSpawnRare = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Rare");
        UltimateSkyGrid.iNetherSpawnAbundant = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Abundant");
        UltimateSkyGrid.iEndSpawnMythic = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Mythic");
        UltimateSkyGrid.iEndSpawnRare = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Rare");
        UltimateSkyGrid.iEndSpawnAbundant = configArrayToEntityArray(config, "NormalConfig.SpawnerOptions.Abundant");
    }

    public Material[] configArrayToMaterialArray(FileConfiguration config, String path) {
        String[] stringArray = config.getString(path).split(" ");

        Material[] newArray = new Material[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {

            try {
                newArray[i] = Material.getMaterial(stringArray[i]);
            } catch (NumberFormatException e) {
                Bukkit.getLogger().log(Level.WARNING, "Invalid material in config for " + path + "!", e);
            }
        }
        return newArray;
    }

    public EntityType[] configArrayToEntityArray(FileConfiguration config, String path) {
        String[] stringArray = config.getString(path).split(" ");

        EntityType[] newArray = new EntityType[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {

            try {
                newArray[i] = EntityType.valueOf(stringArray[i]);
            } catch (NumberFormatException e) {
                Bukkit.getLogger().log(Level.WARNING, "Invalid entity in config for " + path + "!", e);
            }
        }
        return newArray;
    }
}