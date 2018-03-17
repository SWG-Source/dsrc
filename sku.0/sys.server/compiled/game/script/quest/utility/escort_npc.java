package script.quest.utility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class escort_npc extends script.base_script
{
    public escort_npc()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!hasObjVar(self, "quests.supressFollowMenu") && getMaster(self) == player)
        {
            int mnuUse = mi.addRootMenu(menu_info_types.PET_FOLLOW, new string_id("pet/pet_menu", "menu_follow"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (hasObjVar(self, "quests.supressFollowMenu") || getMaster(self) != player)
        {
            sendDirtyObjectMenuNotification(player);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.PET_FOLLOW)
        {
            obj_id target = getMaster(self);
            ai_lib.aiFollow(self, target, 1.0f, 8.0f);
        }
        return SCRIPT_CONTINUE;
    }
}
