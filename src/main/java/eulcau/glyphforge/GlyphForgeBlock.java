package eulcau.glyphforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlyphForgeBlock extends BlockWithEntity {
	public static final MapCodec<GlyphForgeBlock> CODEC = BlockWithEntity.createCodec(GlyphForgeBlock::new);

	public GlyphForgeBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos,
							  PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
			serverPlayer.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GlyphForgeBlockEntity(pos, state);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}
}
