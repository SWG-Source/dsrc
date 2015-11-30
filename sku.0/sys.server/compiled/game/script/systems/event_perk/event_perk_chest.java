package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.permissions;
import script.library.utils;

public class event_perk_chest extends script.base_script
{
    public event_perk_chest()
    {
    }
    public static final String STF_FILE = "event_perk";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id[] rewarded = new obj_id[1];
        rewarded[0] = self;
        setObjVar(self, "event_perk.chest.rewarded", rewarded);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToOpenContainer(obj_id self, obj_id whoIsOpeningMe) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        if (whoIsOpeningMe == owner)
        {
            setOwner(self, owner);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClosedContainer(obj_id self, obj_id whoClosedMe) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        if (whoClosedMe == owner)
        {
            String noOwnerString = "0";
            obj_id noOwner = utils.stringToObjId(noOwnerString);
            setOwner(self, noOwner);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        obj_id[] rewarded = getObjIdArrayObjVar(self, "event_perk.chest.rewarded");
        if (transferer == owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (transferer != owner)
        {
            for (int i = 0; i < rewarded.length; i++)
            {
                if (rewarded[i] == transferer)
                {
                    sendSystemMessage(transferer, new string_id(STF_FILE, "chest_only_one_item"));
                    return SCRIPT_OVERRIDE;
                }
            }
            obj_id newRewarded[] = new obj_id[rewarded.length + 1];
            newRewarded[rewarded.length] = transferer;
            for (int i = 0; i < rewarded.length; i++)
            {
                newRewarded[i] = rewarded[i];
            }
            setObjVar(self, "event_perk.chest.rewarded", newRewarded);
            CustomerServiceLog("EventPerk", "(Treasure Chest - [" + self + "] had an item removed from it by player [" + transferer + "].");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        if (transferer != owner)
        {
            sendSystemMessage(transferer, new string_id(STF_FILE, "chest_can_not_add"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
