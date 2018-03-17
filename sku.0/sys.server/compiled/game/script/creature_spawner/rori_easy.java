package script.creature_spawner;

public class rori_easy extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 6;

    public rori_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 4))
        {
            case 1:
                return "flesh_eating_chuba";
            case 2:
                return "nightspider";
            case 3:
                return "frail_squall";
            case 4:
                return "frail_squall";
        }
        return "flesh_eating_chuba";
    }
}
