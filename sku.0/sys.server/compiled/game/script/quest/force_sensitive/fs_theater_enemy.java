package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.xp;
import script.library.group;
import script.library.ai_lib;

public class fs_theater_enemy extends script.base_script
{
    public fs_theater_enemy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        final location anchorLocation = getLocation(self);
        final float minDistance = 0.0f;
        final float maxDistance = 20.0f;
        final float minDelay = 3.0f;
        final float maxDelay = 8.0f;
        loiterLocation(self, anchorLocation, minDistance, maxDistance, minDelay, maxDelay);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "quest.owner"))
        {
            obj_id player = getObjIdObjVar(self, "quest.owner");
            if (isIdValid(player))
            {
                obj_id winner = getObjIdObjVar(self, xp.VAR_TOP_GROUP);
                if (isIdValid(winner))
                {
                    obj_id[] permitted = null;
                    if (group.isGroupObject(winner))
                    {
                        permitted = getGroupMemberIds(winner);
                    }
                    else 
                    {
                        permitted = new obj_id[1];
                        permitted[0] = winner;
                    }
                    if (permitted != null)
                    {
                        for (int i = 0; i < permitted.length; i++)
                        {
                            if (permitted[i] == player)
                            {
                                obj_id inv = utils.getInventoryContainer(self);
                                if (isIdValid(inv))
                                {
                                    obj_id loot = createObject("object/tangible/loot/quest/force_sensitive/theater_datapad.iff", inv, "");
                                    setObjVar(loot, "quest.loot_datapad_2.quest_item_target", player);
                                    setOwner(loot, player);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
