package com.nuclearw.devjoined;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.nuclearw.devjoined.listener.PlayerListener;
import com.nuclearw.devjoined.locale.LocaleManager;
import com.nuclearw.devjoined.runnable.GetDevelopersRunnable;

public class DevJoined extends JavaPlugin {
	private static LocaleManager locale;

	HashMap<String, HashSet<String>> plugins = new HashMap<String, HashSet<String>>();

	@Override
	public void onEnable() {
		try {
			locale = new LocaleManager(this);
		} catch (IOException e) {
			// We could not load locale, this is an error we cannot get around.
			getLogger().severe("Could not load Locale!  This is a non-recoverable error!");
			e.printStackTrace();
			getPluginLoader().disablePlugin(this);
			return;
		}

		getServer().getScheduler().scheduleSyncDelayedTask(this, new GetDevelopersRunnable(this));

		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

		metrics();

		getLogger().info("Finished loading " + getDescription().getFullName());
	}

	@Override
	public void onDisable() {
		getLogger().info("Finished unloading " + getDescription().getFullName());
	}

	public Set<String> getPlugins(String name) {
		return (plugins.containsKey(name)) ? plugins.get(name) : new HashSet<String>();
	}

	public void register(String name, String plugin) {
		if(!plugins.containsKey(name)) {
			plugins.put(name, new HashSet<String>());
		}
		plugins.get(name).add(plugin);
	}

	public LocaleManager getLocale() {
		return locale;
	}

	private void metrics() {
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) { }
	}
}
