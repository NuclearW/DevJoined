package com.nuclearw.devjoined.listener;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.nuclearw.devjoined.DevJoined;

public class PlayerListener implements Listener {
	private DevJoined plugin;

	public PlayerListener(DevJoined plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Set<String> plugins = plugin.getPlugins(event.getPlayer().getName());

		if(plugins.isEmpty()) return;

		String pluginsFormatted = "";

		int i = 0;
		Iterator<String> iterator = plugins.iterator();
		while(iterator.hasNext()) {
			String p = iterator.next();

			String format = "format";

			if(plugins.size() <= i + 2) {
				if(!iterator.hasNext() && plugins.size() != 1) {
					format += "-last";
				} else {
					format += "-last-singular";
				}
			}

			pluginsFormatted += plugin.getLocale().getString(format, p);

			if(plugins.size() > i + 2) {
				pluginsFormatted += plugin.getLocale().getString("separator");
			}

			i++;
		}

		String message = plugin.getLocale().getString("joined", event.getPlayer().getName(), pluginsFormatted);
		plugin.getServer().broadcastMessage(message);
	}
}
