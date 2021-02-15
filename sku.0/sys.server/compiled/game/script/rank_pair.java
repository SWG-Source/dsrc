package script;

import java.util.*;

/**
 * Rank Pair Wrapper
 * Provides a rank_pair for a leader board given a dictionary of data
 *
 * The dictionary should contain a set of entity IDs (integer identifier
 * for e.g. guilds and cities) or a set of obj_id (identifier for players)
 * as the keys and a set of scores (double) to be sorted as the values.
 *
 * Example implementation:
 *
 * rank_pair data = new rank_pair.sortForPlayers(dictionary);
 * obj_id[] players = data.getPlayerEntrants;
 * double[] scores = data.getScores;
 *
 * From the above, you'd have the sorted arrays containing the player
 * winners and their respective scores, both stored in rank order from
 * 1st place to X place (based on total number of entries in dictionary).
 * This design is intended to support direct compatibility with the
 * existing implementation and logic of SUI tables.
 *
 * SWG Source Addition - 2021
 * Authors: Aconite
 */
public class rank_pair {

    public int[] getEntityEntrants;
    public obj_id[] getPlayerEntrants;
    public double[] getScores;

    private rank_pair(int[] entities, double[] scores) {
        this.getEntityEntrants = entities;
        this.getPlayerEntrants = null;
        this.getScores = scores;
    }
    private rank_pair(obj_id[] players, double[] scores) {
        this.getEntityEntrants = null;
        this.getPlayerEntrants = players;
        this.getScores = scores;
    }

    public static rank_pair sortForEntity(dictionary d) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(d.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return new rank_pair(
                list.stream().mapToInt(Map.Entry::getKey).toArray(),
                list.stream().mapToDouble(Map.Entry::getValue).toArray()
        );
    }

    public static rank_pair sortForPlayers(dictionary d) {
        List<Map.Entry<Long, Double>> list = new ArrayList<>(d.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        long[] players_raw = list.stream().mapToLong(Map.Entry::getKey).toArray();
        obj_id[] players = new obj_id[players_raw.length];
        for (int i = 0; i < players_raw.length; i++) {
            players[i] = obj_id.getObjId(players_raw[i]);
        }
        return new rank_pair(players,
                list.stream().mapToDouble(Map.Entry::getValue).toArray()
        );
    }

}