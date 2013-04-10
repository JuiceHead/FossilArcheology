package fossil.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemBrokenSapling extends Item
{
    public ItemBrokenSapling(int var1)
    {
        super(var1);
        this.maxStackSize = 64;
    }

    @Override
    public void updateIcons(IconRegister iconRegister)
    {
             iconIndex = iconRegister.registerIcon("Fossil:Plantfossil");
    }

}