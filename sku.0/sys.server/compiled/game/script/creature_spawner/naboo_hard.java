package script.creature_spawner;

public class naboo_hard extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 3;

    public naboo_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 3))
        {
            case 1:
                return "nuna";
            case 2:
                return "rabid_shaupaut";
            case 3:
                return "narglatch_cub";
        }
        return "narglatch_cub";
    }
}
