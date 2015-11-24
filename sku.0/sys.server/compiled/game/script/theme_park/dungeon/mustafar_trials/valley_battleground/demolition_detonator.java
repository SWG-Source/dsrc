package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.trial;

public class demolition_detonator extends script.base_script
{
    public demolition_detonator()
    {
    }
    public static final String STF = "npc_landmines";
    public static final string_id DETONATE = new string_id(STF, "detonate_charge");
    public static final string_id PAGE = new string_id(STF, "page_charge");
    public static final boolean LOGGING = false;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.verifyLocationBasedDestructionAnchor(self, 500))
        {
            return SCRIPT_OVERRIDE;
        }
        int root_menu = mi.addRootMenu(menu_info_types.ITEM_USE, DETONATE);
        mi.addSubMenu(root_menu, menu_info_types.ITEM_USE_OTHER, PAGE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            detonateCharge(player, self);
        }
        if (item == menu_info_types.ITEM_USE_OTHER)
        {
            pageDetonationCharge(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void detonateCharge(obj_id player, obj_id detonator) throws InterruptedException
    {
        if (!utils.hasScriptVar(detonator, "chargeId"))
        {
            doLogging("detonateCharge", "Detonator did not have an associated chargeId ScriptVar");
            destroyObject(detonator);
            return;
        }
        obj_id charge = utils.getObjIdScriptVar(detonator, "chargeId");
        if (!isIdValid(charge))
        {
            doLogging("placeDetonationCharge", "Charge Obj_Id was invalid, destroying detonator");
            destroyObject(detonator);
            return;
        }
        messageTo(charge, "detonateCharge", null, 0, false);
        destroyObject(detonator);
        return;
    }
    public void pageDetonationCharge(obj_id player, obj_id detonator) throws InterruptedException
    {
        if (!utils.hasScriptVar(detonator, "chargeId"))
        {
            doLogging("pageDetonationCharge", "Detonator did not have an associated chargeId ScriptVar");
            destroyObject(detonator);
            return;
        }
        obj_id charge = utils.getObjIdScriptVar(detonator, "chargeId");
        if (!isIdValid(charge))
        {
            doLogging("placeDetonationCharge", "Charge obj_id was invalid, destroying detonator");
            destroyObject(detonator);
            return;
        }
        messageTo(charge, "pageCharge", null, 0, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/demolition_detonator/" + section, message);
        }
    }
}
