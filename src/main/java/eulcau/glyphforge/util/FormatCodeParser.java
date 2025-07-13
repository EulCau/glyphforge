package eulcau.glyphforge.util;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class FormatCodeParser {
	public static Text parse(String input) {
		String decoded = input.replace("\\u00A7", "§");
		List<Text> parts = new ArrayList<>();
		StringBuilder currentText = new StringBuilder();
		Style currentStyle = Style.EMPTY;

		for (int i = 0; i < decoded.length(); i++) {
			char c = decoded.charAt(i);
			if (c == '§' && i + 1 < decoded.length()) {
				// 处理格式代码
				char code = decoded.charAt(++i);
				Formatting formatting = Formatting.byCode(code);

				if (formatting != null) {
					// 保存当前文本段
					if (!currentText.isEmpty()) {
						parts.add(Text.literal(currentText.toString()).setStyle(currentStyle));
						currentText.setLength(0);
					}

					// 更新样式
					if (formatting.isColor()) {
						currentStyle = currentStyle.withColor(formatting);
					} else {
						currentStyle = currentStyle.withFormatting(formatting);
					}
				}
			} else {
				// 添加普通字符
				currentText.append(c);
			}
		}

		// 添加最后一段文本
		if (!currentText.isEmpty()) {
			parts.add(Text.literal(currentText.toString()).setStyle(currentStyle));
		}

		// 组合所有文本部分
		if (parts.isEmpty()) {
			return Text.empty();
		}

		Text result = parts.getFirst();
		for (int i = 1; i < parts.size(); i++) {
			result = result.copy().append(parts.get(i));
		}
		return result;
	}
}
