package com.nuclearw.devjoined.runnable;

import org.bukkit.plugin.Plugin;

import com.nuclearw.devjoined.DevJoined;

public class GetDevelopersRunnable implements Runnable {
	private DevJoined plugin;

	public GetDevelopersRunnable(DevJoined plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for(Plugin p : plugin.getServer().getPluginManager().getPlugins()) {
			if(!p.isEnabled()) continue;
			for(String author : p.getDescription().getAuthors()) {
				plugin.register(author, p.getDescription().getName());
			}
		}
	}
}
