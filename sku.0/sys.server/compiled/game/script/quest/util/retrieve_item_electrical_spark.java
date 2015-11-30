package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class retrieve_item_electrical_spark extends script.base_script
{
    public retrieve_item_electrical_spark()
    {
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float dist = getDistance(here, term);
        if (hasScript(player, "quest.task.ground.retrieve_item"))
        {
            if (groundquests.playerNeedsToRetrieveThisItem(player, self))
            {
                if (item == menu_info_types.ITEM_USE || item == menu_info_types.CONVERSE_START)
                {
                    if (isDead(player) || isIncapacitated(player) || dist > 5.0)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (isMob(self))
                    {
                        if (!isIncapacitated(self))
                        {
                            dictionary webster = new dictionary();
                            webster.put("player", player);
                            messageTo(self, "handleSetIncapped", webster, 1, false);
                        }
                        else 
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                    else 
                    {
                        playClientEffectLoc(player, "clienteffect/quest_item_electrical_spark.cef", getLocation(self), 1f);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetIncapped(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            playClientEffectLoc(player, "clienteffect/quest_item_electrical_spark.cef", getLocation(self), 1f);
        }
        setPosture(self, POSTURE_INCAPACITATED);
        return SCRIPT_CONTINUE;
    }
}
