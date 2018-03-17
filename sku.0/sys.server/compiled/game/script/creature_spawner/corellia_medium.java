package script.creature_spawner;

public class corellia_medium extends script.creature_spawner.base_newbie_creature_spawner
{
    public corellia_medium()
    {
    }
    public static final boolean SPAWNER_DISABLED = true;
    public int maxPop = 4;

    public String pickCreature() throws InterruptedException
    {
        switch (rand(1,4))
        {
            case 1:
                return "corellian_butterfly";
            case 2:
                return "corellian_butterfly_worker";
            case 3:
                return "vynock";
            case 4:
                return "scavenger_rat";
        }
        return "vynock";
    }
}
