package script.theme_park.newbie_tutorial;

import script.obj_id;

public class new_player_ribbon extends script.base_script
{
    public new_player_ribbon()
    {
    }
    public static final String questNewbieStart = "quest/tatooine_eisley_legacy";
    public static final String questCrafterEntertainer = "quest/tatooine_eisley_noncombat";
    public int OnLogin(obj_id self) throws InterruptedException
    {
        detachScript(self, "theme_park.newbie_tutorial.new_player_ribbon");
        return SCRIPT_CONTINUE;
    }
}
