package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;

public class space_battle_ship extends script.base_script
{
    public space_battle_ship()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "warpOut", null, 1500.f, false);
        return SCRIPT_CONTINUE;
    }
    public int warpOut(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        if (isIdValid(quest) && exists(quest))
        {
            setObjVar(quest, "critical_warped", 1);
        }
        destroyObjectHyperspace(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitMoveToComplete(obj_id self) throws InterruptedException
    {
        obj_id quest = getObjIdObjVar(self, "quest");
        dictionary outp = new dictionary();
        outp.put("ship", self);
        space_utils.notifyObject(quest, "shipArrived", outp);
        return SCRIPT_CONTINUE;
    }
}
