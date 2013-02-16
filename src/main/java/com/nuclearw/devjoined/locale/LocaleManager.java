package com.nuclearw.devjoined.locale;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class LocaleManager {
	private ResourceBundle bundle;

	public LocaleManager(JavaPlugin plugin) throws IOException {
		InputStreamReader isr = new InputStreamReader(plugin.getResource("lang.properties"));
		ResourceBundle internal = new PropertyResourceBundle(isr);

		File langFile = new File(plugin.getDataFolder(), "lang.properties");

		if(!langFile.exists()) {
			plugin.saveResource("lang.properties", true);
		}

		ResourceBundle external = new PropertyResourceBundle(new FileReader(langFile));

		Properties externalProp = new Properties();

		boolean needsSave = false;

		for(String key : internal.keySet()) {
			if(external.containsKey(key)) {
				externalProp.put(key, external.getString(key));
			} else {
				externalProp.put(key, internal.getString(key));
				needsSave = true;
			}
		}

		if(needsSave) {
			FileWriter writer = new FileWriter(langFile);
			externalProp.store(writer, " " + plugin.getDescription().getName() + " lang file");

			external = new PropertyResourceBundle(new FileReader(langFile));
		}

		bundle = external;
	}

	public String getString(String key) {
		return getString(key, (Object) null);
	}

	public String getString(String key, Object ... messageArguments) {
		String output = bundle.getString(key);

		if (messageArguments != null) {
			MessageFormat formatter = new MessageFormat("");
			formatter.applyPattern(output);
			output = formatter.format(messageArguments);
		}

        output = ChatColor.translateAlternateColorCodes('&', output);

        return output;
	}
}
