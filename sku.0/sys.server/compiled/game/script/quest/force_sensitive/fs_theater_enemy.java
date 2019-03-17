package script.quest.force_sensitive;

import script.dictionary;
import script.library.group;
import script.library.utils;
import script.library.xp;
import script.location;
import script.obj_id;

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
                        for (obj_id obj_id : permitted) {
                            if (obj_id == player) {
                                obj_id inv = utils.getInventoryContainer(self);
                                if (isIdValid(inv)) {
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
