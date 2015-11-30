package script.poi.badge;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.collection;

public class badge_exploration extends script.base_script
{
    public badge_exploration()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume("badge1", 10f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id breecher) throws InterruptedException
    {
        if (!isPlayer(breecher))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (!hasObjVar(self, "badge") && !hasObjVar(self, "icon"))
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, "badge"))
            {
                int badgeNum = getIntObjVar(self, "badge");
                String badgeName = getCollectionSlotName(badgeNum);
                if ((badgeName != null) && (badgeName.length() > 0) && (!badge.hasBadge(breecher, badgeName)))
                {
                    dictionary explorerBadges = new dictionary();
                    explorerBadges.put("badgeNumber", badgeNum);
                    messageTo(breecher, "explorerBadge", explorerBadges, 0, false);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                int iconNum = getIntObjVar(self, "icon");
                String slotName = getCollectionSlotName(iconNum);
                if ((slotName != null) && (slotName.length() > 0) && (!hasCompletedCollectionSlot(breecher, slotName)))
                {
                    modifyCollectionSlotValue(breecher, slotName, 1);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
