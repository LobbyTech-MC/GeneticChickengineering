package space.kiichan.geneticchickengineering.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityInteractHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import space.kiichan.geneticchickengineering.GeneticChickengineering;

public class ChickenNet extends SimpleSlimefunItem<EntityInteractHandler> implements NotPlaceable {

    private final GeneticChickengineering plugin;

    public ChickenNet(GeneticChickengineering plugin, ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        this.plugin = plugin;
    }

    @Override
    public EntityInteractHandler getItemHandler() {
        addItemHandler(getSecondItemHandler());
        return (e, i, o) -> {
            if (e.getRightClicked().getType() == EntityType.CHICKEN) {
                Chicken chick = (Chicken) e.getRightClicked();
                ItemStack pc = this.plugin.convert(chick);
                World w = chick.getWorld();
                Location l = chick.getLocation().toCenterLocation();
                w.dropItemNaturally(l,pc);
                w.playSound(l, Sound.ENTITY_CHICKEN_EGG, 1F, 1F);
                chick.remove();
            }
        };
    }
    public ItemUseHandler getSecondItemHandler() {
        return (e) -> {e.cancel();};
    }
}
