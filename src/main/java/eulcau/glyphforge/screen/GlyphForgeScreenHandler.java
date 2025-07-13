package eulcau.glyphforge.screen;

import eulcau.glyphforge.GlyphForge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class GlyphForgeScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final BlockPos blockPos;

	public GlyphForgeScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
		this(syncId, playerInventory, new SimpleInventory(2), blockPos);
	}

	public GlyphForgeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, BlockPos blockPos) {
		super(GlyphForge.GLYPH_FORGE_SCREEN_HANDLER, syncId);
		this.inventory = inventory;
		this.blockPos = blockPos;

		// 输入槽位 (0)
		this.addSlot(new Slot(inventory, 0, 27, 47));
		// 输出槽位 (1)
		this.addSlot(new Slot(inventory, 1, 134, 47) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false; // 输出槽不能放入物品
			}
		});

		// 玩家物品栏
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		// 玩家快捷栏
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);

		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();

			if (invSlot == 1) { // 输出槽
				if (!this.insertItem(originalStack, 2, 38, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickTransfer(originalStack, newStack);
			} else if (invSlot != 0) { // 非输入槽
				if (!this.insertItem(originalStack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return newStack;
	}
}
