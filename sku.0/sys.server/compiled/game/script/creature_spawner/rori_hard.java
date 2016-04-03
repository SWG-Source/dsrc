package script.creature_spawner;

public class rori_hard extends script.creature_spawner.base_newbie_creature_spawner
{
    public static final boolean SPAWNER_DISABLED = false;
    public int maxPop = 6;

    public rori_hard()
    {
    }
    public String pickCreature() throws InterruptedException
    {
        switch (rand(1, 5))
        {
            case 1:
                return "wormed_vrobal";
            case 2:
                return "bark_mite";
            case 3:
                return "vir_vur";
            case 4:
                return "squall_female";
            case 5:
                return "squall_male";
        }
        return "bark_mite";
    }
}
