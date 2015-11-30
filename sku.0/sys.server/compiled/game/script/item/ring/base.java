package script.item.ring;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.marriage;
import script.library.static_item;

public class base extends script.base_script
{
    public base()
    {
    }
    public static final string_id SID_ALREADY_PROPOSING = new string_id("unity", "already_proposing");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (isIdValid(inv) && utils.isNestedWithin(self, inv))
        {
            if (static_item.isStaticItem(self))
            {
                if (!static_item.canEquip(player, self))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (!marriage.isMarried(player))
            {
                obj_id target = getIntendedTarget(player);
                if (isIdValid(target))
                {
                    mi.addRootMenu(menu_info_types.SERVER_MENU1, marriage.MNU_PROPOSE);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id inv = utils.getInventoryContainer(player);
            if (isIdValid(inv) && utils.isNestedWithin(self, inv))
            {
                obj_id target = getIntendedTarget(player);
                if (!isIdValid(target) || (target == player))
                {
                    sendSystemMessage(player, marriage.SID_BAD_TARGET);
                    return SCRIPT_CONTINUE;
                }
                if (utils.hasScriptVar(player, marriage.VAR_PROPOSAL_NAME))
                {
                    String targetName = utils.getStringScriptVar(player, marriage.VAR_PROPOSAL_NAME);
                    if (targetName != null && !targetName.equals(""))
                    {
                        prose_package ppAlreadyProposing = prose.getPackage(SID_ALREADY_PROPOSING);
                        prose.setTT(ppAlreadyProposing, target);
                        sendSystemMessageProse(player, ppAlreadyProposing);
                        return SCRIPT_CONTINUE;
                    }
                }
                if (marriage.isMarried(player))
                {
                    String spouseName = getStringObjVar(player, marriage.VAR_SPOUSE_NAME);
                    if (spouseName != null && !spouseName.equals(""))
                    {
                        prose_package ppAlreadyMarried = prose.getPackage(marriage.PROSE_ALREADY_MARRIED, spouseName);
                        sendSystemMessageProse(player, ppAlreadyMarried);
                        return SCRIPT_CONTINUE;
                    }
                }
                marriage.propose(player, target, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
