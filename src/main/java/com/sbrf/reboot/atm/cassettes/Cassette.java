package com.sbrf.reboot.atm.cassettes;

import java.util.List;

public class Cassette<T extends Banknote> {
    private final List<T> banknotes;

    public Cassette(List<T> banknotes) {
        this.banknotes = banknotes;
    }

    public int getCountBanknotes() {
        return banknotes.size();
    }
}
