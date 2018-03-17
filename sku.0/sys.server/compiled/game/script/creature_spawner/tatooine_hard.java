package script.creature_spawner;

public class tatooine_hard extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 3;
    public boolean newbie = true;

    public tatooine_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,3))
        {
            case 1:
                return "mound_mite";
            case 2:
                return "desert_squill";
            case 3:
                return "womprat";
        }
        return "womprat";
    }
}
