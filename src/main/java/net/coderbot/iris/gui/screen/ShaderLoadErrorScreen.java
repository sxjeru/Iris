package net.coderbot.iris.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ShaderLoadErrorScreen extends Screen {
	private final Component line1;
	private final Component line2;
	private final Exception e;
	private int showCopySuccess = -1;

	public ShaderLoadErrorScreen(Component title, Component line1, Component line2, Exception e) {
		super(title);
		this.line1 = line1;
		this.line2 = line2;
		this.e = e;
	}

	@Override
	public void init(Minecraft minecraft, int i, int j) {
		super.init(minecraft, i, j);
		this.addButton(new Button(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, Component.nullToEmpty("Return to menu"), arg -> {
			// Reset the whole screen
			minecraft.setScreen(new ShaderPackScreen(null));
		}));
		this.addButton(new Button(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, Component.nullToEmpty("Copy error"), arg -> {
			StringWriter writer = new StringWriter();
			try (PrintWriter printWriter = new PrintWriter(writer)) {
				e.printStackTrace(printWriter);
				String errorString = writer.toString();
				minecraft.keyboardHandler.setClipboard(errorString);
			}
			showCopySuccess = 240;
		}));
	}

	@Override
	public void render(PoseStack poseStack, int i, int j, float f) {
		this.fillGradient(poseStack, 0, 0, width, height, -1073741824, -1073741824);

		drawCenteredString(poseStack, this.font, title, (int)(this.width * 0.5), (this.height - 166) / 2 + 8, 0xFFFFFF);
		drawCenteredString(poseStack, this.font, line1, (int)(this.width * 0.5), (this.height - 140) / 2 + 8, 0xFFFFFF);
		drawCenteredString(poseStack, this.font, line2, (int)(this.width * 0.5), (this.height - 114) / 2 + 8, 0xFFFFFF);

		if (showCopySuccess > -1) {
			showCopySuccess--;
			drawCenteredString(poseStack, this.font, Component.nullToEmpty("Copied error to clipboard!"), (int)(this.width * 0.5), this.height / 2 + 102 - 16, 0xFFFFFF);
		}
		super.render(poseStack, i, j, f);
	}
}
