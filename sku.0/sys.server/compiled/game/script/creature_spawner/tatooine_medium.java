package script.creature_spawner;

public class tatooine_medium extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 4;
    public boolean newbie = true;

    public tatooine_medium()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,3))
        {
            case 1:
                return "rockmite";
            case 2:
                return "mound_mite";
            case 3:
                return "worrt";
        }
        return "worrt";
    }
}
