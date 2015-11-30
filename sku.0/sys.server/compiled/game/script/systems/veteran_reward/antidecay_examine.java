package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class antidecay_examine extends script.base_script
{
    public antidecay_examine()
    {
    }
    public static final String STF_VETERAN = "veteran_new";
    public static final string_id SID_INVENTORY_FULL = new string_id(STF_VETERAN, "inventory_full");
    public static final string_id SID_FAILED_KIT_CREATE = new string_id(STF_VETERAN, "failed_kit_create");
    public static final string_id SID_KIT_CREATED = new string_id(STF_VETERAN, "kit_created");
    public static final string_id SID_DETACH_KIT = new string_id(STF_VETERAN, "detach_kit");
    public static final String ANTIDECAYKIT = "object/tangible/veteran_reward/antidecay.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setHitpoints(self, getMaxHitpoints(self));
        setInvulnerableHitpoints(self, getMaxHitpoints(self));
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id playerId, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = getFirstFreeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isAntiDecay(self))
        {
            names[idx] = "@veteran_new:antidecay_examine_title";
            attribs[idx] = "@veteran_new:antidecay_examine_text";
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (canManipulate(player, self, false, true, 0, true))
        {
            item.addRootMenu(menu_info_types.SERVER_MENU9, SID_DETACH_KIT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU9)
        {
            if (canManipulate(player, self, false, true, 0, true))
            {
                if (detachKit(self))
                {
                    removeObjVar(self, "antidecay");
                    detachScript(self, "systems.veteran_reward.antidecay_examine");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!utils.isAntiDecay(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!detachKit(self))
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean detachKit(obj_id item) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(item);
        if (!isIdValid(player) || getContainedBy(item) == player)
        {
            return false;
        }
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return false;
        }
        if (getVolumeFree(inv) < 1)
        {
            sendSystemMessage(player, SID_INVENTORY_FULL);
            return false;
        }
        obj_id kit = createObject(ANTIDECAYKIT, inv, "");
        if (!isIdValid(kit))
        {
            sendSystemMessage(player, SID_FAILED_KIT_CREATE);
            return false;
        }
        sendSystemMessage(player, SID_KIT_CREATED);
        return true;
    }
}
