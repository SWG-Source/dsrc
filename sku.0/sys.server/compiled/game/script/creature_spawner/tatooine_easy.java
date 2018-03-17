package script.creature_spawner;

public class tatooine_easy extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 8;
    public boolean newbie = true;

    public tatooine_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 8))
        {
            case 1:
                return "minor_worrt";
            case 2:
                return "kreetle";
            case 3:
                return "rill";
            case 4:
                return "lesser_desert_womprat";
            case 5:
                return "worrt";
            case 6:
                return "rockmite";
            case 7:
                return "mound_mite";
            case 8:
                return "womprat";
        }
        return "minor_worrt";
    }
}
