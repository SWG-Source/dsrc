package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.features;
import script.library.utils;

public class han_solo_experience_player extends script.base_script
{
    public han_solo_experience_player()
    {
    }
    public int grenadedamage(obj_id self, dictionary params) throws InterruptedException
    {
        int maxHealth = getMaxHealth(self);
        int newHealth = maxHealth / 2;
        setHealth(self, newHealth);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        int currentHealth = getHealth(self);
        int maxHealth = getMaxHealth(self);
        if (currentHealth <= maxHealth / 2)
        {
            setHealth(self, maxHealth / 2);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String strAction) throws InterruptedException
    {
        if (strAction.equals("clientReady"))
        {
            obj_id hangar = getTopMostContainer(self);
            newbieTutorialEnableHudElement(self, "radar", false, 0);
            newbieTutorialEnableHudElement(self, "chatbox", false, 0);
            messageTo(hangar, "doEvents", null, 1, false);
            checkJtLStatus(self);
        }
        else if (strAction.equals("openInventory"))
        {
            if (utils.hasScriptVar(self, "npe.invCheck"))
            {
                utils.removeScriptVar(self, "npe.invCheck");
                obj_id building = getTopMostContainer(self);
                messageTo(building, "continueMainTable", null, 0, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkJtLStatus(obj_id self) throws InterruptedException
    {
        if (features.isSpaceEdition(self))
        {
            setObjVar(self, "jtlNewbie", 4);
        }
    }
}
