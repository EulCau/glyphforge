package eulcau.glyphforge;

import eulcau.glyphforge.client.gui.GlyphForgeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class GlyphForgeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(GlyphForge.GLYPH_FORGE_SCREEN_HANDLER, GlyphForgeScreen::new);
	}
}
