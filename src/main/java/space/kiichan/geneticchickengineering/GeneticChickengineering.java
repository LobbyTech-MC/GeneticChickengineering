package space.kiichan.geneticchickengineering;

import io.github.thebusybiscuit.cscorelib2.config.Config;
import io.github.thebusybiscuit.cscorelib2.item.CustomItem;
//import io.github.thebusybiscuit.cscorelib2.updater.GitHubBuildsUpdater;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.papermc.lib.PaperLib;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.UUID;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Chicken;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import space.kiichan.geneticchickengineering.chickens.ChickenTypes;
import space.kiichan.geneticchickengineering.chickens.PocketChicken;
import space.kiichan.geneticchickengineering.commands.Commands;
import space.kiichan.geneticchickengineering.database.DBUtil;
import space.kiichan.geneticchickengineering.items.ChickenNet;
import space.kiichan.geneticchickengineering.items.GCEItems;
import space.kiichan.geneticchickengineering.items.ResourceEgg;
import space.kiichan.geneticchickengineering.listeners.WorldSavedListener;
import space.kiichan.geneticchickengineering.machines.ExcitationChamber;
import space.kiichan.geneticchickengineering.machines.GeneticSequencer;
import space.kiichan.geneticchickengineering.machines.PrivateCoop;
import space.kiichan.geneticchickengineering.machines.RestorationChamber;

public class GeneticChickengineering extends JavaPlugin implements SlimefunAddon {

    private final NamespacedKey categoryId = new NamespacedKey(this, "genetic_chickengineering");
    private final NamespacedKey chickenDirectoryId = new NamespacedKey(this, "genetic_chickengineering_chickens");
    private final NamespacedKey dnakey = new NamespacedKey(this, "gce_pocket_chicken_dna");
    private boolean doPain;
    private boolean painKills;
    private double painChance;
    public PocketChicken pocketChicken;
    private Research research;
    public DBUtil db;
    public Logger log;

    @Override
    public void onEnable() {
        this.log = this.getLogger();
        
        if (!PaperLib.isPaper()) {
            this.log.severe("GCE must be run on a Paper server because it uses Paper-specific API calls.");
            this.log.severe("This server doesn't appear understand Paper API, so GCE will be disabled.");
            return;
        }
        
        File datadir = this.getDataFolder();
        if (!datadir.exists()) {
            datadir.mkdirs();
        }
        this.db = new DBUtil(datadir.toString(), this.log);
        if (!this.db.checkForConnection()) {
            this.log.severe("Connection to database failed. Aborting initialization.");
            this.log.severe("Check above for more information about the error.");
            return;
        }
        Config cfg = new Config(this);

        int mutationRate = clamp(1, cfg.getOrSetDefault("options.mutation-rate", 30), 100);
        int maxMutation = clamp(1, cfg.getOrSetDefault("options.max-mutation", 2), 6);
        int resFailRate = clamp(0, cfg.getOrSetDefault("options.resource-fail-rate", 0), 100);
        int resBaseTime = clamp(14, cfg.getOrSetDefault("options.resource-base-time", 14), 100);
        boolean displayResources = cfg.getOrSetDefault("options.display-resource-in-name", true);
        this.doPain = cfg.getOrSetDefault("options.enable-pain", false);
        this.painKills = cfg.getOrSetDefault("options.pain-kills", false);
        this.painChance = clamp(0d, cfg.getDouble("options.pain-chance"), 100d);

        /*if (cfg.getOrSetDefault("options.auto-update", false) && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "kii-chan-reloaded/GeneticChickengineering/master").start();
        }*/

        SlimefunItemStack chickenIcon = new SlimefunItemStack("GCE_ICON", "1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893", "&e基因工程雞", "", "&a> 點擊開啟");
        SlimefunItemStack chickenDirectoryIcon = new SlimefunItemStack("GCE_DIRECTORY_ICON", new ItemStack(Material.BLAST_FURNACE), "&e基因雞目錄", "", "&a> 點擊開啟");

        Category category = new Category(categoryId, chickenIcon);
        this.research = new Research(categoryId, 29841, "違抗自然", 13);
        Category chickDir = new Category(chickenDirectoryId, chickenDirectoryIcon);

        ItemStack[] nullRecipe = new ItemStack[] { null, null, null, null, null, null, null, null, null };

        this.pocketChicken = new PocketChicken(this, category, GCEItems.POCKET_CHICKEN, mutationRate, maxMutation, displayResources, dnakey, new RecipeType(new NamespacedKey(this, "gce_from_net"), new CustomItem(GCEItems.CHICKEN_NET,"§r§f用§a雞網§f捕獲", "§r§f或在§e私人雞舍§f內繁殖")), nullRecipe);
        ChickenNet chickenNet = new ChickenNet(this, category, GCEItems.CHICKEN_NET, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            null, new ItemStack(Material.STRING), new ItemStack(Material.STRING),
            null, new ItemStack(Material.STICK), new ItemStack(Material.STRING),
            null, new ItemStack(Material.STICK), null});
        GeneticSequencer geneticSequencer = new GeneticSequencer(this, category, GCEItems.GENETIC_SEQUENCER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            new ItemStack(Material.OAK_PLANKS), null, new ItemStack(Material.OAK_PLANKS),
            new ItemStack(Material.COBBLESTONE), new ItemStack(Material.OBSERVER), new ItemStack(Material.COBBLESTONE),
            new ItemStack(Material.COBBLESTONE), SlimefunItems.ADVANCED_CIRCUIT_BOARD, new ItemStack(Material.COBBLESTONE)});
        ExcitationChamber excitationChamber = new ExcitationChamber(this, category, GCEItems.EXCITATION_CHAMBER, resFailRate, resBaseTime, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            new ItemStack(Material.BLACKSTONE), SlimefunItems.SMALL_CAPACITOR, new ItemStack(Material.BLACKSTONE),
            new ItemStack(Material.CHAIN), null, new ItemStack(Material.CHAIN),
            new ItemStack(Material.STONE), SlimefunItems.ELECTRIC_MOTOR, new ItemStack(Material.STONE)});
        ExcitationChamber excitationChamber2 = new ExcitationChamber(this, category, GCEItems.EXCITATION_CHAMBER_2, resFailRate, resBaseTime, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.LEAD_INGOT, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.LEAD_INGOT,
            SlimefunItems.BLISTERING_INGOT_3, GCEItems.EXCITATION_CHAMBER, SlimefunItems.BLISTERING_INGOT_3,
            SlimefunItems.LEAD_INGOT, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.LEAD_INGOT});
        PrivateCoop privateCoop = new PrivateCoop(this, category, GCEItems.PRIVATE_COOP, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            new ItemStack(Material.BIRCH_PLANKS), new ItemStack(Material.BIRCH_PLANKS), new ItemStack(Material.BIRCH_PLANKS),
            new ItemStack(Material.JUKEBOX), new ItemStack(Material.RED_BED), new ItemStack(Material.POPPY),
            new ItemStack(Material.BIRCH_PLANKS), SlimefunItems.HEATING_COIL, new ItemStack(Material.BIRCH_PLANKS)});

        RecipeType fromChicken = new RecipeType(new NamespacedKey(this, "gce_from_chicken"), new CustomItem(GCEItems.EXCITATION_CHAMBER,"§r§f從§b裝有雞的袋子§f上獲得", "§f在§e鼓舞室§f內產生"));

        SlimefunItem waterEgg = new ResourceEgg(this, category, GCEItems.WATER_EGG, Material.WATER, fromChicken, cfg.getOrSetDefault("options.allow-nether-water", false));
        SlimefunItem lavaEgg = new ResourceEgg(this, category, GCEItems.LAVA_EGG, Material.LAVA, fromChicken, true);

        // Register items
        registerToAll(this.pocketChicken);
        registerToAll(chickenNet);
        registerToAll(waterEgg);
        registerToAll(lavaEgg);

        // Register machines
        registerToAll(geneticSequencer.setCapacity(180).setEnergyConsumption(3).setProcessingSpeed(1));
        registerToAll(privateCoop.setCapacity(30).setEnergyConsumption(1).setProcessingSpeed(1));
        registerToAll(excitationChamber.setCapacity(250).setEnergyConsumption(5).setProcessingSpeed(1));
        registerToAll(excitationChamber2.setCapacity(1000).setEnergyConsumption(10).setProcessingSpeed(2));
        if (this.doPain) {
            int healRate = this.clamp(1, cfg.getOrSetDefault("options.heal-rate", 2), 120);
            RestorationChamber restorationChamber = new RestorationChamber(this, category, healRate, GCEItems.RESTORATION_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.PINK_TERRACOTTA), new ItemStack(Material.PINK_TERRACOTTA), new ItemStack(Material.PINK_TERRACOTTA),
                SlimefunItems.BANDAGE, new ItemStack(Material.WHITE_BED), SlimefunItems.MEDICINE,
                new ItemStack(Material.PINK_TERRACOTTA), SlimefunItems.HEATING_COIL, new ItemStack(Material.PINK_TERRACOTTA)});
            registerToAll(restorationChamber.setCapacity(30).setEnergyConsumption(2).setProcessingSpeed(1));
        }

        // Fill all resource chickens into the book
        ChickenTypes.registerChickens(research, this.pocketChicken, chickDir, fromChicken);
        research.register();

        // Register listener to clean up database on world save
        new WorldSavedListener(this);

        if (cfg.getOrSetDefault("commands.enabled", true)) {
            // Register commands
            new Commands(this, cfg);
        }
    }

    @Override
    public void onDisable() {
        this.cleanUpDB();
        this.db.close();
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/SlimeTraditionalTranslation/GeneticChickengineering/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }


    private int clamp(int low, int value, int high) {
        return Math.min(Math.max(low, value), high);
    }

    private double clamp(double low, double value, double high) {
        return Math.min(Math.max(low, value), high);
    }

    public ItemStack convert(Chicken chick) {
        return this.pocketChicken.convert(chick);
    }

    private void registerToAll(SlimefunItem item) {
        item.register(this);
        this.research.addItems(item);
    }

    private void registerToAll(AContainer item) {
        item.register(this);
        this.research.addItems(item);
    }
    public void cleanUpDB() {
        List<String[]> chicks = this.db.getAll();
        if (chicks.size() == 0) {
            return;
        }
        List<World> ws = this.getServer().getWorlds();
        List<String> found = new ArrayList<String>();
        for (int i=0; i<ws.size(); i++) {
            World w = ws.get(i);
            for (int j=0; j<chicks.size(); j++) {
                String uuid = chicks.get(j)[0];
                if (found.contains(uuid)) {
                    continue;
                }
                Entity chick = w.getEntity(UUID.fromString(uuid));
                if (chick != null) {
                    found.add(uuid);
                }
            }
        }
        int c = 0;
        for (int j=0; j<chicks.size(); j++) {
            String uuid = chicks.get(j)[0];
            if (found.contains(uuid)) {
                continue;
            }
            this.db.delete(uuid);
            c = c + 1;
        }
        this.db.commit();
        if (c > 0) {
            this.log.info(c+" old records deleted from overworld chicken database (did they die?)");
        }
    }

    public boolean painEnabled() {
        return this.doPain;
    }

    public boolean deathEnabled() {
        return this.painKills;
    }

    public boolean survivesPain(ItemStack chick) {
        return this.pocketChicken.getHealth(chick) > 0.25;
    }

    public boolean harm(ItemStack chick) {
        return this.pocketChicken.harm(chick, 0.25);
    }

    public boolean heal(ItemStack chick, double amount) {
        if (amount > 0) {
            amount = amount * -1;
        }
        return this.pocketChicken.harm(chick, amount);
    }

    public void possiblyHarm(ItemStack chick) {
        if (Math.random()*100 < this.painChance) {
            this.harm(chick);
        }
    }
}
