package script.creature_spawner;

public class corellia_hard extends script.creature_spawner.base_newbie_creature_spawner
{
    public corellia_hard()
    {
    }
    public static final boolean SPAWNER_DISABLED = true;
    public int maxPop = 3;

    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 3))
        {
            case 1:
                return "crazed_durni";
            case 2:
                return "gurrcat";
            case 3:
                return "sand_panther_cub";
        }
        return "sand_panther_cub";
    }
}
