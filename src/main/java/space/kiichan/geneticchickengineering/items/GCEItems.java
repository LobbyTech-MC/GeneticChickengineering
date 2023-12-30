package space.kiichan.geneticchickengineering.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

public final class GCEItems {

    private GCEItems() {}

    public static final SlimefunItemStack POCKET_CHICKEN = new SlimefunItemStack("GCE_POCKET_CHICKEN", "1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893", "§r§b装有鸡的袋子", "§7右键方块", "§7来放置鸡");
    public static final SlimefunItemStack CHICKEN_NET = new SlimefunItemStack("GCE_CHICKEN_NET", new ItemStack(Material.COBWEB), "§a鸡笼子", "§r§7§o用于储存鸡", "§e右键§7 使用");
    public static final SlimefunItemStack WATER_EGG = new SlimefunItemStack("GCE_WATER_EGG", new ItemStack(Material.TURTLE_SPAWN_EGG), "§9水蛋", "§r§7§o装有水", "§e右键§7 使用");
    public static final SlimefunItemStack LAVA_EGG = new SlimefunItemStack("GCE_LAVA_EGG", new ItemStack(Material.STRIDER_SPAWN_EGG), "§c岩浆蛋", "§r§7§o装有岩浆", "§e右键§7 使用");
    public static final SlimefunItemStack GENETIC_SEQUENCER = new SlimefunItemStack("GCE_GENETIC_SEQUENCER", new ItemStack(Material.SMOKER), "§e基因测序仪", "§r§7§o用于测试鸡的基因", LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE), LoreBuilder.powerPerSecond(6));
    public static final SlimefunItemStack EXCITATION_CHAMBER = new SlimefunItemStack("GCE_EXCITATION_CHAMBER", new ItemStack(Material.BLAST_FURNACE), "§e鼓舞室", "§r§7§o鸡产生资源的地方", LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE), LoreBuilder.powerPerSecond(10));
    public static final SlimefunItemStack EXCITATION_CHAMBER_2 = new SlimefunItemStack("GCE_EXCITATION_CHAMBER_2", new ItemStack(Material.BLAST_FURNACE), "§e加强版鼓舞室", "§r§7§o鸡产生资源的地方", "§r§7§o速度更快", LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE), LoreBuilder.powerPerSecond(15));
    public static final SlimefunItemStack PRIVATE_COOP = new SlimefunItemStack("GCE_PRIVATE_COOP", new ItemStack(Material.BEEHIVE), "§e鸡舍", "§r§7§o可以放更多鸡...", LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE), LoreBuilder.powerPerSecond(2));
    public static final SlimefunItemStack RESTORATION_CHAMBER = new SlimefunItemStack("GCE_RESTORATION_CHAMBER", new ItemStack(Material.PINK_SHULKER_BOX), "§e休息室", "§r§7§o治疗鸡", LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE), LoreBuilder.powerPerSecond(4));

}
