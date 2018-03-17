package script.creature_spawner;

import script.dictionary;
import script.obj_id;

public class death_msg extends script.base_script
{
    public death_msg()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        LOG("Newbie_Spawn", "Message in onDestroy: Creature died of type - " + getTemplateName(self));
        dictionary webster = new dictionary();
        webster.put("deadGuy", self);
        obj_id mom = getObjIdObjVar(self, "creater");
        messageTo(mom, "creatureDied", webster, rand(180, 360), false);
        LOG("Newbie_Spawn", "Message in onDestroy: Sending message to mom - " + mom + " from self - " + self);
        return SCRIPT_CONTINUE;
    }
}
