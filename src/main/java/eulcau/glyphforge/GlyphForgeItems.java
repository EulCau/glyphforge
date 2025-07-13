package eulcau.glyphforge;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public final class GlyphForgeItems {
	private GlyphForgeItems() {}

	public static final BlockItem GLYPH_FORGE_ITEM = new BlockItem(
		GlyphForgeBlocks.GLYPH_FORGE_BLOCK,
		new Item.Settings().registryKey(
				RegistryKey.of(
				RegistryKeys.ITEM,
				Identifier.of("glyphforge", "glyph_forge")
			)
		)
	);

	public static void register() {
		Registry.register(
				Registries.ITEM,
				Identifier.of(GlyphForge.MODID, "glyph_forge"),
				GLYPH_FORGE_ITEM
		);
	}
}
