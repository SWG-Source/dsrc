package script.npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.factions;
import script.library.battlefield;
import script.library.utils;

public class special_item extends script.base_script
{
    public special_item()
    {
    }
    public static final String VAR_FACTION = "faction_recruiter.faction";
    public static final String VAR_DECLARED = "faction_recruiter.declared";
    public static final string_id SID_MUST_BE_DECLARED = new string_id("faction_recruiter", "must_be_declared");
    public static final string_id SID_MUST_BE_FACTION_MEMBER = new string_id("faction_recruiter", "must_be_faction_member");
    public static final string_id SID_MUST_BE_DECLARED_USE = new string_id("faction_recruiter", "must_be_declared_use");
    public static final string_id SID_MUST_BE_FACTION_MEMBER_USE = new string_id("faction_recruiter", "must_be_faction_member_use");
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id appearanceInventory = getAppearanceInventory(transferer);
        boolean appearanceInventoryEquip = (isIdValid(appearanceInventory) && destContainer == appearanceInventory);
        if (destContainer == transferer || appearanceInventoryEquip)
        {
            if (!utils.hasSpecialSkills(transferer))
            {
                string_id specialItem = new string_id("error_message", "mandalorian_armor_restriction");
                sendSystemMessage(transferer, specialItem);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
