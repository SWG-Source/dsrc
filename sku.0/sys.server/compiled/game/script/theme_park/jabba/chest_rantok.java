package script.theme_park.jabba;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class chest_rantok extends script.base_script
{
    public chest_rantok()
    {
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        String itemName = getTemplateName(item);
        if (itemName.equals("object/weapon/melee/sword/sword_rantok.iff"))
        {
            obj_id topmost = getTopMostContainer(self);
            obj_id quest_player = getObjIdObjVar(topmost, "player");
            if (quest_player != transferer)
            {
                debugSpeakMsg(self, "You can't get the Sword out of the container...");
                return SCRIPT_OVERRIDE;
            }
            messageTo(quest_player, "finishRantokQuest", null, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
}
