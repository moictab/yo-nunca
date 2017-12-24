package com.moictab.yonunca.model;

/**
 * Created by moict on 06/12/2017.
 */

public class Statistic {

    public String name;
    public int number;

    public Statistic(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof Statistic)) {
            return false;
        }

        return ((Statistic) other).name.equals(this.name);
    }
}
