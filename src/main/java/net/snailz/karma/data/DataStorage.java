package net.snailz.karma.data;

import net.snailz.karma.KarmaUser;

import java.util.UUID;

public interface DataStorage {

    void sterilize(KarmaUser user);

    KarmaUser deSterilize(UUID uuid);

    String getStorageMethod();
}