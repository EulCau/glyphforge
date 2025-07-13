package eulcau.glyphforge;

import eulcau.glyphforge.screen.GlyphForgeScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GlyphForge implements ModInitializer {
	public static final String MODID = "glyphforge";
	public static ScreenHandlerType<GlyphForgeScreenHandler> GLYPH_FORGE_SCREEN_HANDLER;

	@Override
	public void onInitialize() {

		System.out.println("Registering blocks...");
		GlyphForgeBlocks.register();

		System.out.println("Registering block entities...");
		GlyphForgeBlockEntity.register();

		System.out.println("Registering items...");
		GlyphForgeItems.register();

		System.out.println(MODID + " initialized.");
		GLYPH_FORGE_SCREEN_HANDLER = Registry.register(
				Registries.SCREEN_HANDLER,
				Identifier.of(MODID, "glyph_forge"),
				new ScreenHandlerType<>(
						(syncId, inventory) -> new GlyphForgeScreenHandler(syncId, inventory, BlockPos.ORIGIN),
						FeatureFlags.DEFAULT_ENABLED_FEATURES
				)
		);

		System.out.println(MODID + " initialized.");
	}
}
