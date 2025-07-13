package eulcau.glyphforge.client.gui;

import eulcau.glyphforge.GlyphForgeBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import eulcau.glyphforge.screen.GlyphForgeScreenHandler;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GlyphForgeScreen extends HandledScreen<GlyphForgeScreenHandler> {
	private TextFieldWidget nameField;
	private final BlockPos blockPos;

	private static final Identifier TEXTURE =
			Identifier.of("glyphforge", "textures/gui/glyph_forge_gui.png");

	public GlyphForgeScreen(GlyphForgeScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.blockPos = handler.getBlockPos();
	}

	private void onApplyButtonClicked() {
		if (client != null && client.world != null) {
			GlyphForgeBlockEntity blockEntity = (GlyphForgeBlockEntity) client.world.getBlockEntity(blockPos);
			if (blockEntity != null) {
				blockEntity.applyNameFormatting();
			}
		}
	}

	@Override
	protected void init() {
		super.init();
		this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
		GlyphForgeBlockEntity blockEntity = (GlyphForgeBlockEntity) (
				(client != null ? client.world : null) != null ? client.world.getBlockEntity(blockPos) : null);
		String currentText = blockEntity != null ? blockEntity.getCurrentText() : "";

		ButtonWidget applyButton = ButtonWidget.builder(Text.of("Apply"), button -> onApplyButtonClicked())
				.position(x + 100, y + 40)
				.size(50, 20)
				.build();
		this.addDrawableChild(applyButton);

		// 创建文本输入框
		this.nameField = new TextFieldWidget(this.textRenderer,
				this.x + 50, this.y + 24, 103, 12,
				Text.translatable("container.glyphforge.name_field"));

		this.nameField.setText(currentText);
		this.nameField.setChangedListener(this::onTextChanged);
		this.nameField.setMaxLength(50);
		this.nameField.setEditableColor(0xFFFFFF);
		this.addSelectableChild(this.nameField);
		this.setInitialFocus(this.nameField);
	}

	private void onTextChanged(String text) {
		if (client != null && client.world != null) {
			GlyphForgeBlockEntity blockEntity = (GlyphForgeBlockEntity) client.world.getBlockEntity(blockPos);
			if (blockEntity != null) {
				blockEntity.setCurrentText(text);
			}
		}
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;

		context.drawTexturedQuad(
				TEXTURE,
				x, y,
				x + backgroundWidth, y + backgroundHeight,
				0f, (float)backgroundWidth,
				0f, (float)backgroundHeight
		);

		context.drawTexturedQuad(
				TEXTURE,
				x + 48, y + 22,
				x + 48 + 107, y + 22 + 16,
				176f, 176f + 107f,
				0f, 16f
		);

	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		nameField.render(context, mouseX, mouseY, delta);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}

	@Override
	protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
		context.drawText(textRenderer,
				Text.translatable("container.glyphforge.name_label"),
				50, 14, 0x404040, false
		);
	}
}
