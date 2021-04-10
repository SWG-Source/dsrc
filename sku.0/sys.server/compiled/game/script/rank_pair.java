package script;

import java.util.*;

/**
 * Rank Pair Wrapper
 * Provides a rank_pair for a leader board given a HashMap of data
 *
 * The HashMap should contain a set of entity IDs (integer identifier
 * for e.g. guilds and cities) or a set of obj_id (identifier for players)
 * as the keys and a set of scores (float) to be sorted as the values.
 * *******NOTE****** sortForPlayers() must get passed a HashMap with
 * obj_id's parsed as Long(s), do NOT pass obj_ids directly or you'll get
 * ClassCastException errors. It will give you back obj_id's though.
 *
 * Example implementation:
 *
 * rank_pair data = rank_pair.sortForPlayers(map, 25);
 * obj_id[] players = data.getPlayerEntrants;
 * double[] scores = data.getScores;
 *
 * From the above, you'd have the sorted arrays containing the player
 * winners and their respective scores, both stored in rank order from
 * 1st place to X place (based on total number of entries in HashMap).
 * This design is intended to support direct compatibility with the
 * existing implementation and logic of SUI tables.
 *
 * Use the limit parameter to filter the size of the
 * resulting data (i.e. you only want the top 10 results)
 *
 * SWG Source Addition - 2021
 * Authors: Aconite
 */
public class rank_pair {

    public int[] getEntityEntrants;
    public obj_id[] getPlayerEntrants;
    public float[] getScores;

    private rank_pair(int[] entities, float[] scores) {
        this.getEntityEntrants = entities;
        this.getPlayerEntrants = null;
        this.getScores = scores;
    }
    private rank_pair(obj_id[] players, float[] scores) {
        this.getEntityEntrants = null;
        this.getPlayerEntrants = players;
        this.getScores = scores;
    }

    public static rank_pair sortForEntity(HashMap<Integer, Float> d, int sizeLimit) {
        List<Map.Entry<Integer, Float>> list = new ArrayList<>(d.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        double[] temp = list.stream().mapToDouble(Map.Entry::getValue).toArray();
        float[] data = new float[temp.length];
        for (int i = 0; i < temp.length; i++) {
            data[i] = (float)temp[i];
        }
        return new rank_pair(
                list.stream().mapToInt(Map.Entry::getKey).limit(sizeLimit).toArray(),
                data
        );
    }

    public static rank_pair sortForPlayers(HashMap<Long, Float> d, int sizeLimit) {
        List<Map.Entry<Long, Float>> list = new ArrayList<>(d.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        long[] players_raw = list.stream().mapToLong(Map.Entry::getKey).limit(sizeLimit).toArray();
        obj_id[] players = new obj_id[players_raw.length];
        for (int i = 0; i < players_raw.length; i++) {
            players[i] = obj_id.getObjId(players_raw[i]);
        }
        double[] temp = list.stream().mapToDouble(Map.Entry::getValue).toArray();
        float[] data = new float[temp.length];
        for (int i = 0; i < temp.length; i++) {
            data[i] = (float)temp[i];
        }
        return new rank_pair(players, data);
    }
}