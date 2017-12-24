package com.moictab.yonunca.controller;

import com.moictab.yonunca.model.Entry;
import com.moictab.yonunca.model.Statistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by moict on 06/12/2017.
 */

public class StatisticController {

    public List<Statistic> getSortedStatistics(List<Entry> entries) {

        List<Statistic> statistics = new ArrayList<>();

        for (Entry entry : entries) {

            if (statistics.contains(new Statistic(entry.user, 0))) {
                int index = getIndex(statistics, new Statistic(entry.user, 0));
                Statistic statistic = statistics.get(index);
                statistics.set(index, new Statistic(statistic.name, statistic.number + 1));
            } else {
                statistics.add(new Statistic(entry.user, 1));
            }
        }

        Collections.sort(statistics, new StatisticComparator());
        return statistics;
    }

    private int getIndex(List<Statistic> statistics, Statistic statistic) {

        for (int i = 0; i < statistics.size(); i++) {
            if (statistics.get(i).name.equals(statistic.name)) {
                return i;
            }
        }

        return -1;
    }

    static class StatisticComparator implements Comparator<Statistic> {

        @Override
        public int compare(Statistic statistic, Statistic newStatistic) {
            return newStatistic.number - statistic.number;
        }
    }
}
