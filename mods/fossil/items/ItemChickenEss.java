package mods.fossil.items;

import mods.fossil.Fossil;
import mods.fossil.items.forge.ForgeFood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChickenEss extends ForgeFood
{
    public ItemChickenEss(int var1, int var2, float var3, boolean var4,String textname)
    {
        super(var1, var2, var3, var4,textname);
    }

    @Override
    public void onFoodEaten(ItemStack var1, World var2, EntityPlayer var3)
    {
        var3.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        //return super.onFoodEaten(var1, var2, var3);
    }
}
