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

import net.snailz.karma.user.KarmaLevel;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;


public class KarmaConfig {

    private static KarmaConfig karmaConfig = new KarmaConfig();

    private KarmaConfig(){

    }

    public static KarmaConfig getInstance(){
        return karmaConfig;
    }

    public String prefix;

    public int defaultKarma;

    public boolean yellowEnabled;
    public int yellowTime;

    public boolean maxMinEnabled;
    public int maxKarma;
    public int minKarma;

    private FileConfiguration config;

    /*
    0 = Red Karma Change
    1 = Neutral Karma Change
    2 = Green Karma Change
    */
    private int[] redKarmaChange;
    private int[] neutralKarmaChange;
    private int[] greenKarmaChange;

    private int minRedKarma;
    private int minGreenKarma;
    private int[] rangeNeutralKarma;

    public void initKarmaConfig(FileConfiguration config_instance){
        config = config_instance;
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("prefix")) + " ";
        defaultKarma = config.getInt("defaultkarma");
        yellowEnabled = config.getBoolean("yellowplayers.enabled");
        yellowTime = config.getInt("yellowplayers.duration");
        maxKarma = config.getInt("maxkarmalevels.max");
        minKarma = config.getInt("maxkarmalevels.min");
        maxMinEnabled = config.getBoolean("maxkarmalevels.enabled");

        loadRanges();
        loadKarmaChages();
    }

    private void loadKarmaChages(){
        redKarmaChange = new int[]{config.getInt("karmachanges.red.red"), config.getInt("karmachanges.red.neutral"),
                config.getInt("karmachanges.red.green")};
        neutralKarmaChange = new int[]{config.getInt("karmachanges.neutral.red"), config.getInt("karmachanges.neutral.neutral"),
                config.getInt("karmachanges.neutral.green")};
        greenKarmaChange = new int[]{config.getInt("karmachanges.green.red"), config.getInt("karmachanges.green.neutral"),
                config.getInt("karmachanges.green.green")};
    }

    private void loadRanges(){
        minRedKarma = config.getInt("karmalevels.red");
        minGreenKarma = config.getInt("karmalevels.green");

        String neutralKarma = config.getString("karmalevels.neutral");
        String[] neutralKarmaSplit = neutralKarma.split("~");
        rangeNeutralKarma = new int[]{Integer.parseInt(neutralKarmaSplit[0]), Integer.parseInt(neutralKarmaSplit[1])};
    }

    public int getKarmaChange(KarmaLevel killer, KarmaLevel killed){
        switch (killer){
            case RED:
                switch (killed){
                    case RED:
                        return redKarmaChange[0];
                    case NEUTRAL:
                        return redKarmaChange[1];
                    case GREEN:
                        return redKarmaChange[2];
                }
            case NEUTRAL:
                switch (killed){
                    case RED:
                        return neutralKarmaChange[0];
                    case NEUTRAL:
                        return neutralKarmaChange[1];
                    case GREEN:
                        return neutralKarmaChange[2];
                }
            case GREEN:
                switch (killed){
                    case RED:
                        return greenKarmaChange[0];
                    case NEUTRAL:
                        return greenKarmaChange[1];
                    case GREEN:
                        return greenKarmaChange[2];
                }
            default:
                return 0;
        }

    }

    public KarmaLevel getKarmaLevel(int karma){
        if (karma <= minRedKarma){
            return KarmaLevel.RED;
        } else if (karma >= minGreenKarma){
            return KarmaLevel.GREEN;
        } else if (karma >= rangeNeutralKarma[0] && karma <= rangeNeutralKarma[1]){
            return KarmaLevel.NEUTRAL;
        }
        return null;
    }
}
