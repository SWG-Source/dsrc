package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.trial;
import script.library.utils;

public class spy_decoy extends script.base_script
{
    public spy_decoy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.combat.credit_for_kills");
        dictionary dict = new dictionary();
        dict.put("degrade", 0);
        messageTo(self, "destroyDecoy", null, 8.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "handleDecoyCleanup", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (pvpCanAttack(self, attacker) && isFlanking(self))
        {
            buff.applyBuff(attacker, "sp_decoy_debuff");
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyDecoy(obj_id self, dictionary params) throws InterruptedException
    {
        int stage = params.getInt("degrade");
        switch (stage)
        {
            case 0:
            setHologramType(self, HOLOGRAM_TYPE1_QUALITY4);
            break;
            case 1:
            setCreatureCoverVisibility(self, false);
            kill(self);
            messageTo(self, "handleDecoyCleanup", null, 2.0f, false);
            return SCRIPT_CONTINUE;
        }
        stage++;
        params.put("degrade", stage);
        messageTo(self, "destroyDecoy", params, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public boolean isFlanking(obj_id self) throws InterruptedException
    {
        return utils.getBooleanScriptVar(self, "flank");
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "decoy_start_trade"));
        mi.addRootMenu(menu_info_types.ELEVATOR_UP, new string_id("spam", "decoy_character_sheet"));
        mi.addRootMenu(menu_info_types.ELEVATOR_DOWN, new string_id("spam", "invite_to_group"));
        return SCRIPT_CONTINUE;
    }
    public int handleDecoyCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] haters = getHateList(self);
        if (haters.length > 0)
        {
            for (int i = 0; i < haters.length; i++)
            {
                removeHateTarget(haters[i], self);
            }
        }
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int queue_ad(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (-1782321567), self, "", COMMAND_PRIORITY_DEFAULT);
        return SCRIPT_CONTINUE;
    }
}
