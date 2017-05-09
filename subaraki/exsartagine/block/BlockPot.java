package subaraki.exsartagine.block;

import java.util.Random;

import lib.util.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import subaraki.exsartagine.mod.ExSartagine;
import subaraki.exsartagine.tileentity.TileEntityPot;

public class BlockPot extends Block {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.15D, 0.0D, 0.15D, 0.85D, 0.6D, 0.85D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool FULL = PropertyBool.create("full");

	public BlockPot() {
		super(Material.ROCK);

		setLightLevel(0.0f);
		setHardness(8f);
		setSoundType(SoundType.STONE);
		setCreativeTab(CreativeTabs.TOOLS);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName(ExSartagine.MODID+".pot");
		setRegistryName("pot");
		setHardness(3.5f);
		this.setLightOpacity(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(FULL, false));

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

		if(!(worldIn.getTileEntity(pos) instanceof TileEntityPot) || hand == EnumHand.OFF_HAND)
			return false;

		ItemStack stack = playerIn.getHeldItem(hand);

		if(!stack.isEmpty() && stack.getItem().equals(Items.WATER_BUCKET))
		{
			((TileEntityPot)worldIn.getTileEntity(pos)).replenishWater();
			worldIn.notifyBlockUpdate(pos, state, getDefaultState(), 3);
			if(!playerIn.capabilities.isCreativeMode)
				playerIn.setHeldItem(hand, stack.getItem().getContainerItem(stack));
			return true;
		}

		playerIn.openGui(ExSartagine.instance, 2, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{

		if(world.getTileEntity(pos) instanceof TileEntityPot){
			if(fromPos.up().equals(pos)){ //if the block is beneath us
				if(world.getBlockState(fromPos).getBlock() == Blocks.AIR)
				{
					dropBlockAsItem(world, pos, getDefaultState(), 0);
					world.setBlockToAir(pos);
				}

				else if(world.getBlockState(fromPos).getBlock() == Blocks.LIT_FURNACE)
				{
					if(((TileEntityPot)world.getTileEntity(pos)).getWaterLevel() > 0)
					{
						((TileEntityPot)world.getTileEntity(pos)).setCooking();
						world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
					}
				}

				else if(world.getBlockState(fromPos).getBlock() == Blocks.FURNACE)
				{
					if(((TileEntityPot)world.getTileEntity(pos)).getWaterLevel() > 0)
					{
						((TileEntityPot)world.getTileEntity(pos)).stopCooking();
						world.notifyBlockUpdate(pos, state, getDefaultState(), 3);
					}
				}
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(worldIn.getBlockState(pos.down()).getBlock() == Blocks.LIT_FURNACE){
			((TileEntityPot)worldIn.getTileEntity(pos)).setCooking();
			worldIn.notifyBlockUpdate(pos, state, getDefaultState(), 3);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityPot)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, ((TileEntityPot)tileentity).getInventory());
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
		return new TileEntityPot();
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
		double d1 = (double)pos.getY() + 0.6D;
		double d2 = (double)pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

		if(worldIn.getTileEntity(pos) instanceof TileEntityPot)
		{
			if(((TileEntityPot)worldIn.getTileEntity(pos)).isCooking() && !((TileEntityPot)worldIn.getTileEntity(pos)).getInventory().getStackInSlot(0).isEmpty() && ((TileEntityPot)worldIn.getTileEntity(pos)).getWaterLevel() > 0)
			{
				for(int i = 0 ; i < 10 ; i++)
					worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, d0+(RANDOM.nextDouble()/3 - 0.15), d1, d2+(RANDOM.nextDouble()/3 - 0.15), 0.0D, -0.02D, 0.0D, new int[1]);

				worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, d0+(RANDOM.nextDouble()/3 - 0.15), d1, d2+(RANDOM.nextDouble()/3 - 0.15), 0.0D, 0.5D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+(RANDOM.nextDouble()/5 - 0.1), d1, d2+(RANDOM.nextDouble()/5 - 0.1), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	/////// TURNING STUFF ////////////////

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FULL, FACING);
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
		i = i | ((state.getValue(FULL) ? 1 : 0) << 2);
		return i;
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta & 3);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(FULL, (meta & 4) > 0);
	}
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}
