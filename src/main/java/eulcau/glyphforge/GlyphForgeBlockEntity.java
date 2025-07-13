package eulcau.glyphforge;

import eulcau.glyphforge.screen.GlyphForgeScreenHandler;
import eulcau.glyphforge.util.FormatCodeParser;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GlyphForgeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

	public static final String ID = "glyph_forge";
	public static final Identifier IDENTIFIER = Identifier.of(GlyphForge.MODID, ID);
	private final SimpleInventory inventory = new SimpleInventory(2);

	public static BlockEntityType<GlyphForgeBlockEntity> TYPE;

	public GlyphForgeBlockEntity(BlockPos pos, BlockState state) {
		super(TYPE, pos, state);  // 调用父类构造方法初始化 pos 和 state
	}

	public static void register() {
		TYPE = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			IDENTIFIER,
			FabricBlockEntityTypeBuilder.create(GlyphForgeBlockEntity::new, GlyphForgeBlocks.GLYPH_FORGE_BLOCK).build()
		);
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("block.glyphforge.glyph_forge");
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new GlyphForgeScreenHandler(syncId, inv, this.inventory, this.getPos());
	}

	// 添加物品处理逻辑
	private String currentText = "";

	public String getCurrentText() {
		return currentText;
	}

	public void setCurrentText(String text) {
		this.currentText = text;
		markDirty();
	}

	public void applyNameFormatting() {
		ItemStack input = inventory.getStack(0);
		ItemStack output = inventory.getStack(1);

		if (!input.isEmpty() && output.isEmpty() && !currentText.isEmpty()) {
			// 解析格式代码
			Text formattedName = FormatCodeParser.parse(currentText);

			// 创建新物品
			output = input.copy();
			output.setCount(1);

			// 使用组件系统设置自定义名称
			output.set(DataComponentTypes.CUSTOM_NAME, formattedName);

			inventory.setStack(1, output);
			input.setCount(input.getCount() - 1);
		}
	}

	// NBT 读写
	@Override
	public void readData(ReadView view) {
		super.readData(view);
		currentText = "Text";
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		view.putString("Text", currentText);
	}
}
