package subaraki.exsartagine.block;

import java.util.Random;

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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
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
import subaraki.exsartagine.tileentity.TileEntityRange;
import subaraki.exsartagine.tileentity.TileEntityRangeExtension;
import subaraki.exsartagine.tileentity.TileEntitySmelter;

public class BlockRangeExtension extends Block {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ENDBLOCK = PropertyBool.create("endblock");

	public BlockRangeExtension() {
		super(Material.ROCK);

		setLightLevel(0.0f);
		setHardness(8f);
		setSoundType(SoundType.STONE);
		setCreativeTab(CreativeTabs.TOOLS);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName(ExSartagine.MODID+".range_extended");
		setRegistryName("range_extended");
		setHardness(3.5f);
		this.setLightOpacity(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ENDBLOCK, true));

	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		worldIn.notifyBlockUpdate(pos, state, state, 3);
		//		if(!(worldIn.getTileEntity(pos) instanceof TileEntitySmelter) || hand == EnumHand.OFF_HAND)
		//			return false;
		//
		//		playerIn.openGui(ExSartagine.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
		BlockPos nextTo = pos.offset(enumfacing.rotateYCCW());

		//the previous extension is broken : break this
		BlockPos prev = pos.offset(enumfacing.rotateY());
		if(world.getBlockState(prev).getBlock() == Blocks.AIR ){
			dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
			return;
		}

		else if(world.getTileEntity(pos) instanceof TileEntityRangeExtension)
		{
			TileEntityRangeExtension tere = (TileEntityRangeExtension)world.getTileEntity(pos);

			//placed (should exclude being broken from the left and having a furnace right, because the code for popping the block is above)
			if(world.getBlockState(nextTo).getBlock() == Blocks.FURNACE )
			{
				if(tere.getParentRange() != null && world.getTileEntity(tere.getParentRange()) instanceof TileEntityRange)
				{
					TileEntityRange range = (TileEntityRange)world.getTileEntity(tere.getParentRange());
					if(range.canConnect())
					{
						world.setBlockToAir(nextTo);
						world.setBlockState(nextTo, ExSartagineBlock.range_extension.getDefaultState().withProperty(FACING, enumfacing).withProperty(ENDBLOCK, true));
						world.setBlockState(pos, world.getBlockState(pos).withProperty(ENDBLOCK, false),3);
						TileEntity te = world.getTileEntity(nextTo);
						if(te instanceof TileEntityRangeExtension)
						{
							((TileEntityRangeExtension)te).setParentRange(tere.getParentRange());
							range.connect(((TileEntityRangeExtension)te));
						}
					}
				}
			}
			//another extension is removed next to it
			else if(world.getBlockState(nextTo).getBlock() == Blocks.AIR ){
				world.setBlockState(pos, world.getBlockState(pos).withProperty(ENDBLOCK, true),3);
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tere = worldIn.getTileEntity(pos);
		if(tere instanceof TileEntityRangeExtension)
		{
			TileEntity range = worldIn.getTileEntity(((TileEntityRangeExtension)tere).getParentRange());
			if(range instanceof TileEntityRange)
				((TileEntityRange)range).disconnect(pos);
		}

		super.breakBlock(worldIn, pos, state);
	}

	/////////////////rendering//////////////
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	//see trough ! 
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	///////////////TE Stuff//////////////////////
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityRangeExtension(); 
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
		double d1 = (double)pos.getY() + 0.3D;
		double d2 = (double)pos.getZ() + 0.5D;

		if(worldIn.getTileEntity(pos) instanceof TileEntityRangeExtension)
		{
			TileEntityRangeExtension tere = ((TileEntityRangeExtension)worldIn.getTileEntity(pos));
			if(tere.isCooking()){

				EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
				switch (enumfacing) {
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2-0.6, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0-0.6, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2+0.6, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0+0.6, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Blocks.FURNACE);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		return 1;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.FURNACE);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	/////// TURNING STUFF ////////////////

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ENDBLOCK, FACING);
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
		i = i | ((state.getValue(ENDBLOCK) ? 1 : 0) << 2); //push to third bit
		return i;
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta & 3); //untill third bit ? so facing only

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(ENDBLOCK, (meta & 4) > 0); //facing + (saved data after facing = &4
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
