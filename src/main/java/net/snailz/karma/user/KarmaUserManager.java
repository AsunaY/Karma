package net.snailz.karma.user;

import net.snailz.karma.config.KarmaConfig;
import net.snailz.karma.data.DataStorage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class KarmaUserManager {

    private DataStorage dataStorage;

    private HashMap<UUID, KarmaUser> karmaUsersMap = new HashMap<>();
    private ArrayList<KarmaUser> karmaUsers = new ArrayList<>();

    public KarmaUserManager(DataStorage dataStorage){
        this.dataStorage = dataStorage;
    }

    public void addKarmaUser(KarmaUser karmaUser){
        karmaUsers.add(karmaUser);
        karmaUsersMap.put(karmaUser.getPlayer().getUniqueId(), karmaUser);
    }

    public void addKarmaUser(Player player){
        addKarmaUser(dataStorage.deSterilize(player.getUniqueId()));
    }

    public void addNewKarmaUser(Player player){
        KarmaUser karmaUser = new KarmaUser(player, KarmaConfig.getInstance().defaultKarma);
        karmaUsers.add(karmaUser);
        karmaUsersMap.put(player.getUniqueId(), karmaUser);
        dataStorage.addNewKarmaUser(karmaUser);
    }

    public void removeKarmaUser(KarmaUser karmaUser){
        karmaUsers.remove(karmaUser);
        karmaUsersMap.remove(karmaUser);
    }

    public void removeKarmaUser(Player player){
        removeKarmaUser(karmaUsersMap.get(player.getUniqueId()));
    }

    public void sterilizeKarmaUser(KarmaUser karmaUser){
        dataStorage.sterilize(karmaUser);
    }

    public void sterilizeKarmaUser(Player player){
        sterilizeKarmaUser(karmaUsersMap.get(player.getUniqueId()));
    }

    public KarmaUser getKarmaUser(UUID uuid){
        return karmaUsersMap.get(uuid);
    }

    public KarmaUser getKarmaUser(Player player){
        return karmaUsersMap.get(player.getUniqueId());
    }

    public void saveAllKarma(){
        System.out.println("(KUM) Saving Karma...");
        System.out.println("(KUM) DEBUG: karmaUsers is " + karmaUsers.size());
        for (KarmaUser karmaUser : karmaUsers){
            System.out.println("(KUM) DEBUG: Saving " + karmaUser.getPlayer().getName());
            dataStorage.sterilize(karmaUser);
        }
    }
}
