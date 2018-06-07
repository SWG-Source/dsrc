package script.theme_park.tatooine.jabbaspawner.gating;

import script.obj_id;
import script.string_id;

public class block2 extends script.base_script
{
    public block2()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        int gating = getIntObjVar(item, "theme_park_jabba");
        if (gating < 15)
        {
            string_id warning = new string_id("theme_park_jabba/warning", "barada");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
}
