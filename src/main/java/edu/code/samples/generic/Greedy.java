package edu.code.samples.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class Greedy {

    /**
     * At bus station, you have time-table for buses arrival and departure.
     * You need to find the minimum number of platforms so that all
     * the buses can be accommodated as per the schedule.
     */
    public static int minimumNumberOfPlatforms(int[] arrival, int []departure) {
        Arrays.sort(arrival);
        Arrays.sort(departure);

        int maxBusesRequired = 0;
        // Arrival time array will always be traversed before departure time.
        // Assuming the input is not malformed.
        for (int i=0, j=0; i<arrival.length; ) {
            if (arrival[i] < departure[j]) {
                maxBusesRequired++;
                i++;
            } else {
                maxBusesRequired--;
                j++;
            }
        }
        return maxBusesRequired;
    }

    /**
     * You are given n activities with their start and finish times.
     * Select the maximum number of activities that can be performed
     * by single person, assuming that a person can only work
     * on a single activity at a time.
     */
    public static int maximumNumberOfActivities(int[] start, int[] finish) {
        List<Activity> activities = new ArrayList<>(start.length);
        for (int i=0; i<start.length; i++) {
            activities.add(new Activity(start[i], finish[i]));
        }

        Comparator<Activity> comparator = (o1, o2) -> {
            if (o1.start < o2.start) {
                return -1;
            } else if(o1.start == o2.start) {
                return o1.finish - o2.finish <= 0 ? -1 : 1;
            }
            return 1;
        };
        activities.sort(comparator);

        int maxCount = 1;
        Activity previous = activities.get(0), current;
        for (int i=1; i < activities.size(); i++) {
            current = activities.get(i);
            if (previous.finish < current.start)  {
                // no conflict
                maxCount++;
                previous = current;
            } else {
                // We can only chose one of them, so we pick the one with minimum end time.
                if (previous.finish > current.finish) {
                    // we won't increment activity count as we are replacing and not adding new activity
                    previous = current;
                }
                // Ignore current activity.
            }
        }
        return maxCount;
    }

    static class Activity {
        int start;
        int finish;
        public Activity(int s, int f) {
            start = s; finish = f;
        }
    }
}
