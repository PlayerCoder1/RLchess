		package com.playercoder1;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

@PluginDescriptor(
		name = "Chess Plugin",
		description = "Play chess while playing RuneScape",
		tags = {"chess", "game"}
)
public class ChessPlugin extends Plugin {
	private ChessPanel chessPanel;
	private NavigationButton navButton;

	@Inject
	private ClientToolbar clientToolbar;

	@Override
	protected void startUp() {
		chessPanel = new ChessPanel();

		BufferedImage icon = ImageUtil.loadImageResource(ChessPlugin.class, "panel_icon.png");
		navButton = NavigationButton.builder()
				.tooltip("Chess")
				.icon(icon)
				.priority(5)
				.panel(chessPanel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() {
		clientToolbar.removeNavigation(navButton);
	}
}