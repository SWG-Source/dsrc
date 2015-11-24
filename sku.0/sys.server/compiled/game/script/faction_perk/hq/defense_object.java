package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.utils;

public class defense_object extends script.base_script
{
    public defense_object()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, hq.VAR_DEFENSE_PARENT);
        if (isIdValid(parent))
        {
            dictionary d = new dictionary();
            d.put("sender", self);
            messageTo(parent, "handleRemoveDefense", d, 1, true);
        }
        if (utils.hasScriptVar(self, "hq.defense.remover"))
        {
            obj_id player = utils.getObjIdScriptVar(self, "hq.defense.remover");
            if (isIdValid(player))
            {
                utils.sendDelayedSystemMessage(player, "HQ defense removal complete.", 2f);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
