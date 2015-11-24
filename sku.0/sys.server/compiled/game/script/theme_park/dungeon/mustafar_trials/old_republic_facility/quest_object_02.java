package script.theme_park.dungeon.mustafar_trials.old_republic_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.prose;
import script.library.sui;

public class quest_object_02 extends script.base_script
{
    public quest_object_02()
    {
    }
    public int OnOpenedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status < 2)
        {
            activateObject(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void activateObject(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        int status = 0;
        if (hasObjVar(building, "status"))
        {
            status = getIntObjVar(building, "status");
        }
        if (status >= 2)
        {
            return;
        }
        setObjVar(building, "status", 2);
        sendSystemMessage(player, new string_id("mustafar/old_republic_facility", "quest_object_02_msg_self"));
        obj_id group = getGroupObject(player);
        if (isIdValid(group))
        {
            obj_id[] members = getGroupMemberIds(group);
            if (members == null || members.length == 0)
            {
                return;
            }
            for (int i = 0; i < members.length; i++)
            {
                if (members[i] != player)
                {
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("mustafar/old_republic_facility", "quest_object_02_msg_other"));
                    pp = prose.setTU(pp, player);
                    sendSystemMessageProse(members[i], pp);
                }
            }
        }
        return;
    }
}
