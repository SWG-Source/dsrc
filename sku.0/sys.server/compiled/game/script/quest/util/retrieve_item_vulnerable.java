package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.groundquests;

public class retrieve_item_vulnerable extends script.base_script
{
    public retrieve_item_vulnerable()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
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
                if (item == menu_info_types.ITEM_USE)
                {
                    if (isDead(player) || isIncapacitated(player) || dist > 5.0)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    playClientEffectLoc(player, "clienteffect/avatar_wke_electric_01.cef", getLocation(self), 1f);
                    if (isMob(self))
                    {
                        dictionary webster = new dictionary();
                        webster.put("player", player);
                        messageTo(self, "handleSetVulnerable", webster, 3, false);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetVulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            startCombat(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
