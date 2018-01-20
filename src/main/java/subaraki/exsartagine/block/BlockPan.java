package subaraki.exsartagine.block;

import java.util.Random;

import lib.util.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import subaraki.exsartagine.item.ExSartagineItems;
import subaraki.exsartagine.mod.ExSartagine;
import subaraki.exsartagine.tileentity.TileEntityPan;
import subaraki.exsartagine.tileentity.TileEntityRangeExtension;

public class BlockPan extends Block {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockPan() {
		super(Material.IRON);

		setLightLevel(0.0f);
		setSoundType(SoundType.METAL);
		setCreativeTab(ExSartagineItems.pots);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName(ExSartagine.MODID+".pan");
		setRegistryName("pan");
		setHardness(3.5f);
		this.setLightOpacity(0);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		Block blockDown = world.getBlockState(pos.down()).getBlock();
		if(blockDown == Blocks.LIT_FURNACE ||blockDown == Blocks.FURNACE || blockDown == ExSartagineBlock.range_extension){
			return true;
		}
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
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
				Block down = world.getBlockState(fromPos).getBlock();

				if(down == Blocks.AIR)
				{
					dropBlockAsItem(world, pos, getDefaultState(), 0);
					world.setBlockToAir(pos);
				}

				else if(down == Blocks.LIT_FURNACE)
				{
					((TileEntityPan)world.getTileEntity(pos)).setCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}

				else if(down == Blocks.FURNACE)
				{
					((TileEntityPan)world.getTileEntity(pos)).stopCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}
				else if (down == ExSartagineBlock.range_extension_lit)
				{
					((TileEntityPan)world.getTileEntity(pos)).setCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}
				else if (down == ExSartagineBlock.range_extension)
				{
					((TileEntityPan)world.getTileEntity(pos)).stopCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}
			}
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if(world.getBlockState(pos.down()).getBlock() == Blocks.LIT_FURNACE){
			((TileEntityPan)world.getTileEntity(pos)).setCooking();
			world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
		}
		if (world.getBlockState(pos.down()).getBlock() == ExSartagineBlock.range_extension){
			if(world.getTileEntity(pos.down()) instanceof TileEntityRangeExtension)
			{
				if(((TileEntityRangeExtension)world.getTileEntity(pos.down())).isCooking())
				{
					((TileEntityPan)world.getTileEntity(pos)).setCooking();
					world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
				}
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityPan)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, ((TileEntityPan)tileentity).getInventory());
		}

		super.breakBlock(worldIn, pos, state);
	}

	/////////////////rendering//////////////
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
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
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
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
		{
			if(((TileEntityPan)worldIn.getTileEntity(pos)).isCooking())
			{
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0+(RANDOM.nextDouble()/1.5 - 0.35), d1, d2+(RANDOM.nextDouble()/1.5 - 0.35), 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+(RANDOM.nextDouble()/1.5 - 0.35), d1, d2+(RANDOM.nextDouble()/1.5 - 0.35), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	/////// TURNING STUFF ////////////////

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	}

	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}
