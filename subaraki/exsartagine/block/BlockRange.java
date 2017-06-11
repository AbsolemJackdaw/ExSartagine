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
import subaraki.exsartagine.tileentity.TileEntityRange;
import subaraki.exsartagine.tileentity.TileEntityRangeExtension;

public class BlockRange extends Block {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockRange() {
		super(Material.IRON);

		setLightLevel(0.0f);
		setSoundType(SoundType.METAL);
		setCreativeTab(ExSartagineItems.pots);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName(ExSartagine.MODID+".range");
		setRegistryName("range");
		setHardness(3.5f);
		this.setLightOpacity(0);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!(worldIn.getTileEntity(pos) instanceof TileEntityRange) || hand == EnumHand.OFF_HAND)
			return false;

		playerIn.openGui(ExSartagine.instance, 3, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
		BlockPos nextTo = pos.offset(enumfacing.rotateYCCW());

		if(world.getBlockState(nextTo).getBlock() == Blocks.FURNACE){
			world.setBlockToAir(nextTo);
			world.setBlockState(nextTo, ExSartagineBlock.range_extension.getDefaultState().withProperty(BlockRangeExtension.FACING, enumfacing));

			if(world.getTileEntity(nextTo) instanceof TileEntityRangeExtension)
			{
				TileEntityRangeExtension tere = (TileEntityRangeExtension)world.getTileEntity(nextTo);
				tere.setParentRange(pos);

				if(world.getTileEntity(pos) instanceof TileEntityRange)
				{
					TileEntityRange range = (TileEntityRange)world.getTileEntity(pos);
					if(range.canConnect())
						range.connect(tere);
				}
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityRange)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, ((TileEntityRange)tileentity).getInventory());
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
		return new TileEntityRange();
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
		double d1 = (double)pos.getY() + 1.5D;
		double d2 = (double)pos.getZ() + 0.5D;

		if(worldIn.getTileEntity(pos) instanceof TileEntityRange)
		{
			if(((TileEntityRange)worldIn.getTileEntity(pos)).isFueled())
			{
				EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
				switch (enumfacing) {
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+0.3, d1, d2+0.3, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+0.3, d1, d2-0.3, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0-0.3, d1, d2-0.3, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0-0.3, d1, d2+0.3, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				default:
					break;
				}
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
