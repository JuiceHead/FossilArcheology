package mods.fossil.items.forge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class ForgePickaxe extends ItemPickaxe
{
	String TextureFileName;
	public ForgePickaxe(int par1, EnumToolMaterial par2EnumToolMaterial,String TextureFileName0)
	{
		super(par1,par2EnumToolMaterial);
		this.TextureFileName=TextureFileName0;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon("fossil:"+TextureFileName);
    }

}
