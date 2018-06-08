package script.player.skill;

import script.obj_id;

public class player_healing extends script.base_script
{
    public player_healing()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, "player.skill.player_healing");
        return SCRIPT_CONTINUE;
    }
}
