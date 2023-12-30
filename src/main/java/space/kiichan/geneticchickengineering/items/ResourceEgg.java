package space.kiichan.geneticchickengineering.items;

import java.util.Optional;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import space.kiichan.geneticchickengineering.GeneticChickengineering;
import space.kiichan.geneticchickengineering.genetics.DNA;

interface useAction {
    void action(Block place);
}

public class ResourceEgg extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {
    private Material resource;
    private useAction action;

    public ResourceEgg(GeneticChickengineering plugin, ItemGroup category, SlimefunItemStack item, Material resource, RecipeType recipeType, boolean allowedInNether) {
        super(category, item, recipeType, new ItemStack[] {null, null, null, null, makeFakeChicken(plugin, resource), null, null, null, null});
        this.resource = resource;
        if (resource == Material.WATER) {
            if (allowedInNether == false) {
                this.action = (Block place) -> {
                    World w = place.getWorld();
                    if (w.getEnvironment() == World.Environment.NETHER) {
                        w.spawnParticle(Particle.CLOUD, place.getLocation().add(0.5,0,0.5), 5);
                        w.playSound(place.getLocation().toCenterLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1F, 1F);
                    } else {
                        place.setType(this.resource);
                    }
                };
            } else {
                this.action = (Block place) -> {
                    place.setType(this.resource);
                };
            }
        } else {
            this.action = (Block place) -> {
                place.setType(this.resource);
            };
        }
    }

    private static ItemStack makeFakeChicken (GeneticChickengineering plugin, Material resource) {
        ItemStack fake = GCEItems.POCKET_CHICKEN.clone();
        DNA dna;
        if (resource == Material.WATER) {
            dna = new DNA(62);
        } else {
            dna = new DNA(41);
        }
        plugin.pocketChicken.setLore(fake, null, dna);
        return fake;
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();

            Optional<Block> block = e.getClickedBlock();

            if (block.isPresent()) {
                Block b = block.get();
                Block place = b.getRelative(e.getClickedFace());
                if (place.isReplaceable()) {
                    this.action.action(place);
                    if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                        ItemUtils.consumeItem(e.getItem(), false);
                    }
                }
            }
        };
    }
}
