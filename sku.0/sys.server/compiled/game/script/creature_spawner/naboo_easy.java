package script.creature_spawner;

public class naboo_easy extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 6;
    public boolean newbie = true;

    public naboo_easy()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 3))
        {
            case 1:
                return "flesh_eating_chuba";
            case 2:
                return "capper_spineflap";
            case 3:
                return "diseased_nuna";
        }
        return "diseased_nuna";
    }
}
