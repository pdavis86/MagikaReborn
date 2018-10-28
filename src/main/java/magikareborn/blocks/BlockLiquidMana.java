package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.fluids.FluidMana;

//import am2.texture.ResourceManager;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.util.IIcon;
//import net.minecraft.world.IBlockAccess;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockLiquidMana extends BlockFluidClassic{

	//@SideOnly(Side.CLIENT)
	//private IIcon[] icons;

	public static final Fluid liquidEssenceFluid = new FluidMana();
	public static final Material liquidEssenceMaterial = new MaterialLiquid(MapColor.ICE);

	public BlockLiquidMana(){
		super(liquidEssenceFluid, liquidEssenceMaterial);

		final String name = "LiquidMana";
		setRegistryName(name);
        setUnlocalizedName(ModRoot.MODID + "." + name);
        //setLightLevel(9 / 16f);
        setCreativeTab(CreativeTabs.MISC); //todo: add your own creative tab
    }

	/*@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		icons = new IIcon[2];

		icons[0] = ResourceManager.RegisterTexture("liquidEssenceStill", iconRegister);
		icons[1] = ResourceManager.RegisterTexture("liquidEssenceFlowing", iconRegister);

		liquidEssenceFluid.setIcons(icons[0], icons[1]);
	}*/

	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		if (side <= 1)
			return icons[0]; //still
		else
			return icons[1]; //flowing
	}*/

}
