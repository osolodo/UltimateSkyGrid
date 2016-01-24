package com.gmail.labuff.shane.UltimateSkyGrid;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;

public final class UltimateSkyGrid extends JavaPlugin {

    public FileConfiguration configMain;
    public File dirPlayers;
    public File conFileMain;
    public File dataFolder;

    public static int cdelay;
    public static int cHeight;
    public static int cNetherHeight;
    public static int cEndHeight;
    public static int cMythic;
    public static int cUnique;
    public static int cRare;
    public static int cUncommon;
    public static int cMax;
    public static int cMin;
    public static int cChMythic;
    public static int cChRare;
    public static int cNethRare;
    public static int cNethChRare;
    public static int cEndChRare;
    public static int cEndRare;
    public static boolean genGlass;
    public static boolean allBlocksOneWorld;
    public static String cName;
    public static String cNetherName;
    public static String cEndName;
    public static int[] iMythic;
    public static int[] iUnique;
    public static int[] iRare;
    public static int[] iUncommon;
    public static int[] iAbundant;
    public static int[] iNormMythic;
    public static int[] iNormUnique;
    public static int[] iNormRare;
    public static int[] iNormUncommon;
    public static int[] iNormAbundant;
    public static int[] iChMythic;
    public static int[] iChMythicAmount;
    public static int[] iNormChMythic;
    public static int[] iNormChMythicAmount;
    public static int[] iChRare;
    public static int[] iChRareAmount;
    public static int[] iNormChRare;
    public static int[] iNormChRareAmount;
    public static int[] iChNormal;
    public static int[] iChNormalAmount;
    public static int[] iNormChNormal;
    public static int[] iNormChNormalAmount;
    public static int[] iNethBlkRare;
    public static int[] iNethBlkNorm;
    public static int[] iEndBlkRare;
    public static int[] iEndBlkNorm;
    public static int[] iNethChRare;
    public static int[] iNethChRareAmount;
    public static int[] iNethChNorm;
    public static int[] iNethChNormAmount;
    public static int[] iEndChRare;
    public static int[] iEndChRareAmount;
    public static int[] iEndChNorm;
    public static int[] iEndChNormAmount;


    @Override
    public void onEnable() {


        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File dFolder = getDataFolder();
        File drPlyrs = new File(getDataFolder(), "Players");
        File cnFlMn = new File(getDataFolder(), "config.yml");


        this.dirPlayers = drPlyrs;
        this.conFileMain = cnFlMn;
        this.configMain = getConfig();
        this.dataFolder = dFolder;

        if (!this.dirPlayers.exists()) {

            dirPlayers.mkdir();
        }

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

        UltimateSkyGrid.cdelay = config.getInt("Delay", 0);
        UltimateSkyGrid.cHeight = config.getInt("World_Height", 128);
        UltimateSkyGrid.cMythic = config.getInt("Mythic_Block_Probability", 4);
        UltimateSkyGrid.cNetherHeight = config.getInt("NetherConfig.Nether_World_Height", 128);
        UltimateSkyGrid.cEndHeight = config.getInt("EndConfig.End_World_Height", 128);
        UltimateSkyGrid.cUnique = config.getInt("Unique_Block_Probability", 181);
        UltimateSkyGrid.cRare = config.getInt("Rare_Block_Probability", 1801);
        UltimateSkyGrid.cUncommon = config.getInt("Uncommon_Block_Probability", 4001);
        UltimateSkyGrid.cMax = config.getInt("Spawn_Max", 500);
        UltimateSkyGrid.cMin = config.getInt("Spawn_Min", 0);
        UltimateSkyGrid.cName = config.getString("World_Name", "Skygrid");
        UltimateSkyGrid.cNetherName = config.getString("NetherConfig.Nether_World_Name", "NetherSkygrid");
        UltimateSkyGrid.cEndName = config.getString("EndConfig.End_World_Name", "EndSkygrid");
        UltimateSkyGrid.cChMythic = config.getInt("Chest_Prob_Mythic", 2);
        UltimateSkyGrid.cChRare = config.getInt("Chest_Prob_Rare", 6);
        UltimateSkyGrid.genGlass = config.getBoolean("ReplaceAirWithGlass", false);
        UltimateSkyGrid.allBlocksOneWorld = config.getBoolean("AllBlocksOneWorld", true);
        UltimateSkyGrid.cNethRare = config.getInt("NetherConfig.NetherBlocks.RareProb", 1);
        UltimateSkyGrid.cNethChRare = config.getInt("NetherConfig.NetherChestItems.RareProb", 2);
        UltimateSkyGrid.cEndRare = config.getInt("EndConfig.EndBlocks.RareProb", 1);
        UltimateSkyGrid.cEndChRare = config.getInt("EndConfig.EndChestItems.RareProb", 1);


        String[] sMythic = config.getString("BlockGroups.Mythic").split(" ");
        String[] sUnique = config.getString("BlockGroups.Unique").split(" ");
        String[] sRare = config.getString("BlockGroups.Rare").split(" ");
        String[] sUncommon = config.getString("BlockGroups.Uncommon").split(" ");
        String[] sAbundant = config.getString("BlockGroups.Abundant").split(" ");
        String[] sNethBlkRare = config.getString("NetherConfig.NetherBlocks.Rare").split(" ");
        String[] sNethBlkNorm = config.getString("NetherConfig.NetherBlocks.Normal").split(" ");
        String[] sEndBlkRare = config.getString("EndConfig.EndBlocks.Rare").split(" ");
        String[] sEndBlkNorm = config.getString("EndConfig.EndBlocks.Normal").split(" ");
        String[] sChMythic = config.getString("ChestItems.Mythic").split(" ");
        String[] sChRare = config.getString("ChestItems.Rare").split(" ");
        String[] sChNormal = config.getString("ChestItems.Normal").split(" ");
        String[] sNethChRare = config.getString("NetherConfig.NetherChestItems.Rare").split(" ");
        String[] sNethChNorm = config.getString("NetherConfig.NetherChestItems.Normal").split(" ");
        String[] sEndChRare = config.getString("EndConfig.EndChestItems.Rare").split(" ");
        String[] sEndChNorm = config.getString("EndConfig.EndChestItems.Normal").split(" ");
        String[] sNormMythic = config.getString("NormalConfig.BlockGroups.Mythic").split(" ");
        String[] sNormUnique = config.getString("NormalConfig.BlockGroups.Unique").split(" ");
        String[] sNormRare = config.getString("NormalConfig.BlockGroups.Rare").split(" ");
        String[] sNormUncommon = config.getString("NormalConfig.BlockGroups.Uncommon").split(" ");
        String[] sNormAbundant = config.getString("NormalConfig.BlockGroups.Abundant").split(" ");
        String[] sNormChMythic = config.getString("NormalConfig.ChestItems.Mythic").split(" ");
        String[] sNormChRare = config.getString("NormalConfig.ChestItems.Rare").split(" ");
        String[] sNormChNormal = config.getString("NormalConfig.ChestItems.Normal").split(" ");

        UltimateSkyGrid.iMythic = stringArrayToIntArray(sMythic);
        UltimateSkyGrid.iUnique = stringArrayToIntArray(sUnique);
        UltimateSkyGrid.iRare = stringArrayToIntArray(sRare);
        UltimateSkyGrid.iUncommon = stringArrayToIntArray(sUncommon);
        UltimateSkyGrid.iAbundant = stringArrayToIntArray(sAbundant);
        UltimateSkyGrid.iNethBlkRare = stringArrayToIntArray(sNethBlkRare);
        UltimateSkyGrid.iNethBlkNorm = stringArrayToIntArray(sNethBlkNorm);
        UltimateSkyGrid.iEndBlkRare = stringArrayToIntArray(sEndBlkRare);
        UltimateSkyGrid.iEndBlkNorm = stringArrayToIntArray(sEndBlkNorm);
        UltimateSkyGrid.iNormMythic = stringArrayToIntArray(sNormMythic);
        UltimateSkyGrid.iNormUnique = stringArrayToIntArray(sNormUnique);
        UltimateSkyGrid.iNormRare = stringArrayToIntArray(sNormRare);
        UltimateSkyGrid.iNormUncommon = stringArrayToIntArray(sNormUncommon);
        UltimateSkyGrid.iNormAbundant = stringArrayToIntArray(sNormAbundant);

        UltimateSkyGrid.iChMythic = positionalStringArrayToIntArray(sChMythic, 0);
        UltimateSkyGrid.iChMythicAmount = positionalStringArrayToIntArray(sChMythic, 1);
        UltimateSkyGrid.iChRare = positionalStringArrayToIntArray(sChRare, 0);
        UltimateSkyGrid.iChRareAmount = positionalStringArrayToIntArray(sChRare, 1);
        UltimateSkyGrid.iChNormal = positionalStringArrayToIntArray(sChNormal, 0);
        UltimateSkyGrid.iChNormalAmount = positionalStringArrayToIntArray(sChNormal, 1);
        UltimateSkyGrid.iNethChRare = positionalStringArrayToIntArray(sNethChRare, 0);
        UltimateSkyGrid.iNethChRareAmount = positionalStringArrayToIntArray(sNethChRare, 1);
        UltimateSkyGrid.iNethChNorm = positionalStringArrayToIntArray(sNethChNorm, 0);
        UltimateSkyGrid.iNethChNormAmount = positionalStringArrayToIntArray(sNethChNorm, 1);
        UltimateSkyGrid.iEndChRare = positionalStringArrayToIntArray(sEndChRare, 0);
        UltimateSkyGrid.iEndChRareAmount = positionalStringArrayToIntArray(sEndChRare, 1);
        UltimateSkyGrid.iEndChNorm = positionalStringArrayToIntArray(sEndChNorm, 0);
        UltimateSkyGrid.iEndChNormAmount = positionalStringArrayToIntArray(sEndChNorm, 1);
        UltimateSkyGrid.iNormChMythic = positionalStringArrayToIntArray(sNormChMythic, 0);
        UltimateSkyGrid.iNormChMythicAmount = positionalStringArrayToIntArray(sNormChMythic, 1);
        UltimateSkyGrid.iNormChRare = positionalStringArrayToIntArray(sNormChRare, 0);
        UltimateSkyGrid.iNormChRareAmount = positionalStringArrayToIntArray(sNormChRare, 1);
        UltimateSkyGrid.iNormChNormal = positionalStringArrayToIntArray(sNormChNormal, 0);
        UltimateSkyGrid.iNormChNormalAmount = positionalStringArrayToIntArray(sNormChNormal, 1);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        String cmdName = cmd.getName();

        if (!(sender instanceof Player) && cmdName.equalsIgnoreCase("usg")) {

            sender.sendMessage("Only a player can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!cmdName.equalsIgnoreCase("usg")) {

            return false;
        }

        initPlayerConfigFile(player, getClass());

        if (args.length == 0) {

            player.sendMessage(ChatColor.RED + "Correct usage of this command is /usg sethome or /usg home");
            return true;
        }
        if (args.length >= 2) {

            player.sendMessage(ChatColor.RED + "Too many arguments. Correct usage of this command is /usg sethome or /usg home");
            return true;
        }

        if (args[0].equalsIgnoreCase("sethome")) {

            if (!player.hasPermission("UltimateSkyGrid.sethome")) {

                player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                return true;
            } else {

                setHome(player);
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("home")) {

            if (!player.hasPermission("UltimateSkyGrid.home")) {

                player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                return true;
            } else {

                File pFile = new File(this.dirPlayers, player.getName() + ".yml");
                FileConfiguration pConfig = YamlConfiguration.loadConfiguration(pFile);
                String wName = UltimateSkyGrid.cName;
                String nWName = UltimateSkyGrid.cNetherName;
                String eWName = UltimateSkyGrid.cEndName;
                World skygrid = getServer().getWorld(wName);
                World nSkygrid = getServer().getWorld(nWName);
                World eSkygrid = getServer().getWorld(eWName);
                World curWorld = player.getWorld();
                World selWorld = getServer().getWorld(wName);
                String selName = UltimateSkyGrid.cName;
                String cWorldName = "World_Name";
                int w = UltimateSkyGrid.cdelay;
                int x = 0;
                int y = 0;
                int z = 0;

                if (curWorld != nSkygrid || curWorld != eSkygrid) {
                    selName = wName;
                    selWorld = skygrid;
                    x = pConfig.getInt("homex");
                    y = pConfig.getInt("homey");
                    z = pConfig.getInt("homez");
                }
                if (curWorld == nSkygrid) {
                    cWorldName = "Nether_World_Name";
                    selName = nWName;
                    selWorld = nSkygrid;
                    x = pConfig.getInt("netherhomex");
                    y = pConfig.getInt("netherhomey");
                    z = pConfig.getInt("netherhomez");
                }
                if (curWorld == eSkygrid) {
                    cWorldName = "End_World_Name";
                    selName = eWName;
                    selWorld = eSkygrid;
                    x = pConfig.getInt("endhomex");
                    y = pConfig.getInt("endhomey");
                    z = pConfig.getInt("endhomez");
                }

                if (selWorld == null) {

                    getLogger().severe("Config value: " + cWorldName + ": = null");
                    getLogger().info("" + cWorldName + ": " + selName + " in config doesn't exist. Make sure the config name matches the actual world name, case sensitive.");
                    player.sendMessage(ChatColor.RED + "Error: UltimateSkyGrid world names are not set up correctly. Tell an Admin");
                    return true;
                } else {
                    Block home = selWorld.getBlockAt(x, y, z);

                    Location homeLoc = home.getLocation().add(.5D, 0D, .5D);

                    if (w > 0) {
                        player.sendMessage(ChatColor.GREEN + "Waiting " + ChatColor.BLUE + (w) + ChatColor.GREEN + " seconds before you port...");
                        new TeleportDelay(player, homeLoc, selName).runTaskLater(this, w * 20);
                        return true;
                    } else {

                        player.sendMessage(ChatColor.GREEN + "Teleporting you to your home in " + selName);
                        player.teleport(homeLoc);
                        return true;
                    }
                }
            }
        } else {

            player.sendMessage(ChatColor.RED + "Correct usage of this command is /usg sethome or /usg home");
            return false;
        }

    }

    public void initPlayerConfigFile(Player player, Class<? extends UltimateSkyGrid> cl) {

        File mFile = new File(getDataFolder(), "config.yml");
        FileConfiguration pConfig;
        FileConfiguration mConfig = YamlConfiguration.loadConfiguration(mFile);
        File pFile = new File(this.dirPlayers, player.getName() + ".yml");

        int wH = mConfig.getInt("World_Height");
        try {
            if (pFile.exists()) {

                pConfig = YamlConfiguration.loadConfiguration(pFile);
                pConfig.save(pFile);
            } else {

                InputStream in = cl.getResourceAsStream("/defaultplayer.yml");
                FileOutputStream out = new FileOutputStream(pFile);
                byte[] buffer = new byte[512];
                int i;
                while ((i = in.read(buffer)) != -1) {

                    out.write(buffer, 0, i);
                }
                out.close();

                pConfig = YamlConfiguration.loadConfiguration(pFile);

                pConfig.addDefault("homex", Integer.valueOf(0));
                pConfig.addDefault("homey", Integer.valueOf(wH - 3));
                pConfig.addDefault("homez", Integer.valueOf(0));
                pConfig.addDefault("netherhomex", Integer.valueOf(0));
                pConfig.addDefault("netherhomey", Integer.valueOf(wH - 3));
                pConfig.addDefault("netherhomez", Integer.valueOf(0));
                pConfig.addDefault("endhomex", Integer.valueOf(0));
                pConfig.addDefault("endhomey", Integer.valueOf(wH - 3));
                pConfig.addDefault("endhomez", Integer.valueOf(0));

                pConfig.set("homex", Integer.valueOf(randCoord()));
                pConfig.set("homey", Integer.valueOf(wH - 3));
                pConfig.set("homez", Integer.valueOf(randCoord()));
                pConfig.set("netherhomex", Integer.valueOf(randCoord()));
                pConfig.set("netherhomey", Integer.valueOf(wH - 3));
                pConfig.set("netherhomez", Integer.valueOf(randCoord()));
                pConfig.set("endhomex", Integer.valueOf(randCoord()));
                pConfig.set("endhomey", Integer.valueOf(wH - 3));
                pConfig.set("endhomez", Integer.valueOf(randCoord()));
                pConfig.save(pFile);
            }
        } catch (FileNotFoundException e) {

            Bukkit.getLogger().log(Level.SEVERE, "defaultplayer.yml is missing from the jar file", e);
        } catch (IOException e) {

            Bukkit.getLogger().log(Level.SEVERE, null, e);
        }
    }

    public void setHome(Player player) {

        int x, y, z;

        x = player.getLocation().getBlockX();
        y = player.getLocation().getBlockY();
        z = player.getLocation().getBlockZ();
        World curWorld = player.getWorld();
        if (curWorld != getServer().getWorld(UltimateSkyGrid.cName) && curWorld != getServer().getWorld(UltimateSkyGrid.cNetherName) && curWorld != getServer().getWorld(UltimateSkyGrid.cEndName)) {

            player.sendMessage(ChatColor.RED + "You cant set your skygrid home in this world.");
            return;
        } else {

            try {

                FileConfiguration pConfig;
                File pConFile = new File(this.dirPlayers, player.getName() + ".yml");
                if (pConFile.exists()) {

                    pConfig = YamlConfiguration.loadConfiguration(pConFile);
                    if (curWorld == getServer().getWorld(UltimateSkyGrid.cName)) {

                        pConfig.set("homex", Integer.valueOf(x));
                        pConfig.set("homey", Integer.valueOf(y));
                        pConfig.set("homez", Integer.valueOf(z));
                        pConfig.save(pConFile);
                        player.sendMessage(ChatColor.BLUE + "Your Skygrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                        return;
                    }
                    if (curWorld == getServer().getWorld(UltimateSkyGrid.cNetherName)) {

                        pConfig.set("netherhomex", Integer.valueOf(x));
                        pConfig.set("netherhomey", Integer.valueOf(y));
                        pConfig.set("netherhomez", Integer.valueOf(z));
                        pConfig.save(pConFile);
                        player.sendMessage(ChatColor.BLUE + "Your NetherSkyGrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                        return;
                    } else {

                        pConfig.set("endhomex", Integer.valueOf(x));
                        pConfig.set("endhomey", Integer.valueOf(y));
                        pConfig.set("endhomez", Integer.valueOf(z));
                        pConfig.save(pConFile);
                        player.sendMessage(ChatColor.BLUE + "Your EndSkyGrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                        return;

                    }
                } else {

                    player.sendMessage(ChatColor.RED + "Your player config file must not have initialized properly. Talk to an admin");
                }

            } catch (FileNotFoundException e) {

                Bukkit.getLogger().log(Level.SEVERE, null, e);
            } catch (IOException e) {

                Bukkit.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }

    /*public void setDelay(int millis){

        long d;
        long t = System.currentTimeMillis();

        do{
            d = System.currentTimeMillis();
        }
        while ((d - t) < millis);

    }*/
    public File getFolder() {

        return this.dataFolder;
    }

    public File getDefConfigFile() {

        return this.conFileMain;
    }

    public int randCoord() {

        Random r = new Random();
        Random r2 = new Random();

        int b = r2.nextInt(2);
        int a = r.nextInt((UltimateSkyGrid.cMax - UltimateSkyGrid.cMin) + 1) + UltimateSkyGrid.cMin;

        while ((a % 4) != 0) {

            a++;
        }


        if (b == 0) {

            a = -a;
            return a;
        } else {

            return a;
        }
    }

    public int[] stringArrayToIntArray(String[] stringArray) {

        int[] newArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {

            try {

                newArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException e) {

                Bukkit.getLogger().log(Level.SEVERE, "Invalid integer in string array!", e);
            }
        }
        return newArray;
    }

    public int[] positionalStringArrayToIntArray(String[] stringArray, int pos) {

        int[] newArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {

            String sP = stringArray[i];
            String[] sPA = sP.split(":");

            try {

                newArray[i] = Integer.parseInt(sPA[pos]);
            } catch (NumberFormatException e) {

                Bukkit.getLogger().log(Level.SEVERE, "Invalid integer in string array, or your format is wrong! <ID>:<AMOUNT>", e);
            }
        }
        return newArray;
    }
}