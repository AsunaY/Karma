/*
This file is part of Karma.

Karma is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Karma is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Karma.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.snailz.karma.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static org.bukkit.Bukkit.getLogger;

import net.snailz.karma.Karma;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Snailz
 */
public class CustomConfig {

    File customfile = null;
    FileConfiguration custom = null;
    String name;
    Karma plugin;
    public CustomConfig(String name, Karma plugin){
        this.name = name;
        this.plugin = plugin;
    }

    public void reloadCustomFile(){
        if (customfile == null) {
            customfile = new File(plugin.getDataFolder(), name + ".yml");
        }
        custom = YamlConfiguration.loadConfiguration(customfile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(plugin.getResource(name + ".yml"), "UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CustomConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            custom.setDefaults(defConfig);
        }

    }

    public void saveCustomFile() {
        if (custom == null || customfile == null) {
            return;
        }
        try {
            getCustomFile().save(customfile);
        } catch (IOException ex) {
            getLogger().log(SEVERE, "Could not save config to " + customfile + " Please report this!", ex);
        }
    }

    public FileConfiguration getCustomFile() {
        if (custom == null) {
            reloadCustomFile();
        }
        return custom;
    }

    public void saveDefaultCustomFile() {
        if (customfile == null) {
            customfile = new File(plugin.getDataFolder(), name + ".yml");
        }
        if (!customfile.exists()) {
            plugin.saveResource(name + ".yml", false);
        }
    }
}
