package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.npe;
import script.library.groundquests;
import script.library.sequencer;
import script.library.static_item;
import script.library.xp;

public class npe_tutorial_pistol extends script.base_script
{
    public npe_tutorial_pistol()
    {
    }
    public static final string_id SID_ITEM_MUST_NOT_BE_EQUIP = new string_id("base_player", "not_while_equipped");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isPlayer(destContainer) && utils.hasScriptVar(destContainer, "npe.pistolReady"))
        {
            utils.removeScriptVar(destContainer, "npe.pistolReady");
            obj_id building = getTopMostContainer(destContainer);
            xp.grantXpByTemplate(destContainer, 10);
            messageTo(building, "continueMainTable", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id container = getContainedBy(self);
        if (!isPlayer(container))
        {
            obj_id player = utils.getContainingPlayer(self);
            if (hasScript(player, "npe.han_solo_experience_player"))
            {
                obj_id pistol = static_item.createNewItemFunction("weapon_pistol_02_02", container);
                attachScript(pistol, "npe.npe_tutorial_pistol");
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id player = utils.getContainingPlayer(self);
            sendSystemMessage(player, SID_ITEM_MUST_NOT_BE_EQUIP);
            return SCRIPT_OVERRIDE;
        }
    }
}
