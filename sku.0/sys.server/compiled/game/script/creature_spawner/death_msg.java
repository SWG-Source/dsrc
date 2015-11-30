package script.creature_spawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class death_msg extends script.base_script
{
    public death_msg()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String creatureTemplate = getTemplateName(self);
        LOG("Newbie_Spawn", "Message in onDestroy: Creature died of type - " + creatureTemplate);
        dictionary webster = new dictionary();
        webster.put("deadGuy", self);
        obj_id mom = getObjIdObjVar(self, "creater");
        int randSpawn = rand(180, 360);
        messageTo(mom, "creatureDied", webster, randSpawn, false);
        LOG("Newbie_Spawn", "Message in onDestroy: Sending message to mom - " + mom + " from self - " + self);
        return SCRIPT_CONTINUE;
    }
}
