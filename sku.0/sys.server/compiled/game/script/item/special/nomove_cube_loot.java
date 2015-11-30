package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class nomove_cube_loot extends script.base_script
{
    public nomove_cube_loot()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "notrade"))
        {
            removeObjVar(self, "notrade");
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            setObjVar(self, "noTrade", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "notrade"))
        {
            removeObjVar(self, "notrade");
        }
        if (utils.isNestedWithinAPlayer(self))
        {
            setObjVar(self, "noTrade", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id dest, obj_id transferer) throws InterruptedException
    {
        if (!isPlayer(transferer) || isGod(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isIdValid(owner))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id inv = utils.getInventoryContainer(owner);
        obj_id bank = utils.getPlayerBank(owner);
        if (!isIdValid(inv) || !isIdValid(bank))
        {
            return SCRIPT_OVERRIDE;
        }
        if (dest == owner || dest == inv || dest == bank)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasScript(dest, "item.loot_cube.republic_assembly_tool"))
        {
            return SCRIPT_CONTINUE;
        }
        String prompt = "@som/som_cube:nomove_prompt";
        String title = "@som/som_cube:nomove_title";
        sui.msgbox(owner, owner, prompt, sui.OK_ONLY, title, "noHandler");
        return SCRIPT_OVERRIDE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(transferer);
        if (destContainer == inv)
        {
            setObjVar(self, "noTrade", true);
        }
        return SCRIPT_CONTINUE;
    }
}
