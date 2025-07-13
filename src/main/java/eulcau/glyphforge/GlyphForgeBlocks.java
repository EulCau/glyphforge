package eulcau.glyphforge;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

import java.util.Optional;

public final class GlyphForgeBlocks {
	private GlyphForgeBlocks() {}

	public static Block GLYPH_FORGE_BLOCK;

	public static void register() {
		AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.ANVIL)
				.requiresTool()
				.registryKey(RegistryKey.of(
						RegistryKeys.BLOCK,
						Identifier.of("glyphforge", "glyph_forge")
				));
		// 创建方块并立即注册
		GLYPH_FORGE_BLOCK = Registry.register(
				Registries.BLOCK,
				Identifier.of("glyphforge", "glyph_forge"),
				new GlyphForgeBlock(settings)
		);
	}
}
