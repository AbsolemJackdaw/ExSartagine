package subaraki.exsartagine.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.exsartagine.mod.ExSartagine;
import subaraki.exsartagine.tileentity.TileEntityPan;

public class BlockPan extends Block {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

	public BlockPan() {
		super(Material.IRON);

		setLightLevel(0.0f);
		setHardness(8f);
		setSoundType(SoundType.METAL);
		setCreativeTab(CreativeTabs.TOOLS);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName(ExSartagine.MODID+".pan");
		setRegistryName("pan");
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		if(world.getBlockState(pos.down()).getBlock() == Blocks.LIT_FURNACE || world.getBlockState(pos.down()).getBlock() == Blocks.FURNACE){
			return true;
		}
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!(worldIn.getTileEntity(pos) instanceof TileEntityPan) || hand == EnumHand.OFF_HAND)
			return false;

		playerIn.openGui(ExSartagine.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{

		if(world.getTileEntity(pos) instanceof TileEntityPan){
			if(fromPos.up().equals(pos)){ //if the block is beneath us
				if(world.getBlockState(fromPos).getBlock() == Blocks.AIR)
				{
					dropBlockAsItem(world, pos, getDefaultState(), 0);
					world.setBlockToAir(pos);
				}

				else if(world.getBlockState(fromPos).getBlock() == Blocks.LIT_FURNACE)
				{
					((TileEntityPan)world.getTileEntity(pos)).setCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}

				else if(world.getBlockState(fromPos).getBlock() == Blocks.FURNACE)
				{
					((TileEntityPan)world.getTileEntity(pos)).stopCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(worldIn.getBlockState(pos.down()).getBlock() == Blocks.LIT_FURNACE){
			((TileEntityPan)worldIn.getTileEntity(pos)).setCooking();
			worldIn.notifyBlockUpdate(pos, state, getDefaultState(), 3);
		}
	}

	/////////////////rendering//////////////
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	///////////////TE Stuff//////////////////////
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityPan();
	}

	/////////////// MISC //////////////////////

	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return false;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public float getExplosionResistance(Entity exploder) {
		return 20;
	}
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return 20;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.15D;
		double d2 = (double)pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

		if(worldIn.getTileEntity(pos) instanceof TileEntityPan)
			if(((TileEntityPan)worldIn.getTileEntity(pos)).isCooking()){
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0+(RANDOM.nextDouble()/1.5 - 0.35), d1, d2+(RANDOM.nextDouble()/1.5 - 0.35), 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+(RANDOM.nextDouble()/1.5 - 0.35), d1, d2+(RANDOM.nextDouble()/1.5 - 0.35), 0.0D, 0.0D, 0.0D, new int[0]);
			}
	}
}
