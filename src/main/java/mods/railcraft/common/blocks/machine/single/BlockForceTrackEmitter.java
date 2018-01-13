package mods.railcraft.common.blocks.machine.single;

import mods.railcraft.common.blocks.BlockEntityDelegate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 *
 */
public class BlockForceTrackEmitter extends BlockEntityDelegate {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockForceTrackEmitter() {
        super(Material.ROCK);
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public Class<? extends TileEntity> getTileClass(IBlockState state) {
        return TileForceTrackEmitter.class;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileForceTrackEmitter();
    }
}
