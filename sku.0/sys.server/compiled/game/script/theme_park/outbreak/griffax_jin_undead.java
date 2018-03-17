package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class griffax_jin_undead extends script.base_script
{
    public griffax_jin_undead()
    {
    }
    public static final string_id SID_JIN_ESCAPED = new string_id("theme_park/outbreak/outbreak", "dr_jin_escaped");
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        CustomerServiceLog("quest", "griffax_jin_undead.OnIncapacitateTarget() Dr. Jin Incapacitated a target.");
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner))
        {
            CustomerServiceLog("quest", "griffax_jin_undead.OnIncapacitateTarget() Dr. Jin Incapacitated a target but the owner could not be found.");
            return SCRIPT_CONTINUE;
        }
        if (victim != owner)
        {
            CustomerServiceLog("quest", "griffax_jin_undead.OnIncapacitateTarget() Dr. Jin Incapacitated a target but the owner was not the target incapped.");
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "cleanupDrJin", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("quest", "griffax_jin_undead.OnExitedCombat() Dr. Jin has stopped combat.");
        obj_id owner = getObjIdObjVar(self, "owner");
        if (!isValidId(owner))
        {
            CustomerServiceLog("quest", "griffax_jin_undead.OnIncapacitateTarget() Dr. Jin Incapacitated a target but the owner could not be found.");
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "cleanupDrJin", null, 12, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupDrJin(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id owner = getObjIdObjVar(self, "owner");
        if (ai_lib.isInCombat(self))
        {
            if (isValidId(owner) && exists(owner) && !isDead(owner))
            {
                obj_id[] haters = getHateList(self);
                if (haters != null || haters.length > 0)
                {
                    for (int i = 0; i < haters.length; i++)
                    {
                        if (owner == haters[i])
                        {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        CustomerServiceLog("quest", "griffax_jin_undead.cleanupDrJin() Dr. Jin is being cleaned up because the player owner was incapacitated or ran away.");
        if (isValidId(owner) && exists(owner))
        {
            sendSystemMessage(owner, SID_JIN_ESCAPED);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
