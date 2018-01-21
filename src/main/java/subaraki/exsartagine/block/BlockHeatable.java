package subaraki.exsartagine.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import subaraki.exsartagine.tileentity.TileEntityRangeExtension;

public abstract class BlockHeatable extends Block {

	public BlockHeatable(Material materialIn) {
		super(materialIn);
	}

	protected abstract void startHeating(World world, IBlockState state, BlockPos pos);
	protected abstract void stopHeating(World world, IBlockState state, BlockPos pos);
	protected abstract Class getTileEntity();
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		Block blockDown = world.getBlockState(pos.down()).getBlock();
		if(blockDown == Blocks.LIT_FURNACE ||blockDown == Blocks.FURNACE || blockDown instanceof BlockRangeExtension){
			return true;
		}
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{

		if(world.getTileEntity(pos).getClass().equals(this.getTileEntity())){
			if(fromPos.up().equals(pos)){ //if the block is beneath us
				Block down = world.getBlockState(fromPos).getBlock();
				if(down == Blocks.AIR)
				{
					dropBlockAsItem(world, pos, getDefaultState(), 0);
					world.setBlockToAir(pos);
				}

				else if(down == Blocks.LIT_FURNACE)
				{
					startHeating(world, state, pos);
				}
				else if(down == Blocks.FURNACE)
				{
					stopHeating(world, state, pos);
				}
				else if (down == ExSartagineBlock.range_extension_lit)
				{
					startHeating(world, state, pos);
				}
				else if (down == ExSartagineBlock.range_extension)
				{
					stopHeating(world, state, pos);
				}
			}
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if(world.getBlockState(pos.down()).getBlock() == Blocks.LIT_FURNACE){
			startHeating(world, state, pos);
		}
		if (world.getBlockState(pos.down()).getBlock() instanceof BlockRangeExtension){
			if(world.getTileEntity(pos.down()) instanceof TileEntityRangeExtension)
			{
				if(((TileEntityRangeExtension)world.getTileEntity(pos.down())).isCooking())
				{
					startHeating(world, state, pos);
				}
			}
		}
	}
}
