package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.fluids.ManaFluid;

//import am2.texture.ResourceManager;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;

//import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
//import net.minecraft.block.material.MaterialLiquid;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.util.IIcon;
//import net.minecraft.world.IBlockAccess;

//import net.minecraft.block.state.BlockFaceShape;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.block.statemap.StateMap;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockAccess;
//import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;

public class ManaFluidBlock extends BlockFluidClassic{

	//@SideOnly(Side.CLIENT)
	//private IIcon[] icons;

	public static final Fluid MANA_FLUID = new ManaFluid();
	public static final Material MANA_MATERIAL = net.minecraft.block.material.Material.WATER; //new MaterialLiquid(MapColor.ICE);

	public ManaFluidBlock(){
		super(MANA_FLUID, MANA_MATERIAL);

		final String name = "manafluidblock";
		setRegistryName(name);
        setUnlocalizedName(ModRoot.MODID + "." + name.toLowerCase());
        setLightLevel(9 / 16f);

    }

	/*@SideOnly(Side.CLIENT)
	public void render() {
		//This will allow our fluid to render at the correct level, so flowing fluids appear smaller than source blocks.
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
	}*/

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

	/*@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}*/

}
