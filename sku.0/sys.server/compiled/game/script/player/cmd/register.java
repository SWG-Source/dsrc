package script.player.cmd;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.factions;
import script.library.planetary_map;

public class register extends script.base_script
{
    public register()
    {
    }
    public static final string_id SID_CANNOT_REGISTER_BAD_LOC = new string_id("register", "cannot_register_bad_loc");
    public static final string_id SID_CANNOT_REGISTER_NO_SUPPORT = new string_id("register", "cannot_register_no_support");
    public static final string_id SID_CANNOT_REGISTER_NOT_NEUTRAL = new string_id("register", "cannot_register_not_neutral");
    public static final string_id SID_CANNOT_REGISTER_WRONG_FACTION = new string_id("register", "cannot_register_wrong_faction");
    public static final string_id SID_CANNOT_REGISTER_ALREADY_REG = new string_id("register", "cannot_register_already_reg");
    public static final string_id SID_SUCCESS_REGISTER = new string_id("register", "success_register");
    public static final string_id SID_CANNOT_REGISTER_LACK_SKILL = new string_id("register", "cannot_register_lack_skill");
    public int cmdRegisterWithLocation(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        obj_id updateTarget = null;
        obj_id topMost = getTopMostContainer(self);
        if (!isIdValid(topMost) || topMost == self)
        {
            obj_id camp = utils.getObjIdScriptVar(self, "camp.current");
            if (isIdValid(camp))
            {
                if (hasSkill(self, "social_dancer_novice") || hasSkill(self, "social_musician_novice") || hasSkill(self, "science_doctor_novice"))
                {
                    updateTarget = camp;
                }
            }
        }
        else 
        {
            map_location maploc = getPlanetaryMapLocation(topMost);
            if (maploc == null)
            {
                sendSystemMessage(self, SID_CANNOT_REGISTER_BAD_LOC);
                return SCRIPT_CONTINUE;
            }
            if (!maploc.isActive() && !maploc.isInactive())
            {
                sendSystemMessage(self, SID_CANNOT_REGISTER_NO_SUPPORT);
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(topMost, "healing.canhealwound"))
            {
                if (hasSkill(self, "science_doctor_novice"))
                {
                    updateTarget = topMost;
                }
            }
            if (hasObjVar(topMost, "healing.canhealshock"))
            {
                if (hasSkill(self, "class_entertainer_phase1_novice"))
                {
                    updateTarget = topMost;
                }
            }
        }
        if (isIdValid(updateTarget))
        {
            Vector registrants = utils.getResizeableObjIdBatchScriptVar(updateTarget, "registrants");
            if (registrants != null && registrants.size() > 0)
            {
                if (utils.getElementPositionInArray(registrants, self) > -1)
                {
                    sendSystemMessage(self, SID_CANNOT_REGISTER_ALREADY_REG);
                    return SCRIPT_CONTINUE;
                }
            }
            if (planetary_map.updateFacilityActive(updateTarget, true))
            {
                if (!hasScript(updateTarget, "planet_map.active_registered"))
                {
                    attachScript(updateTarget, "planet_map.active_registered");
                }
                registrants = utils.addElement(registrants, self);
                utils.setBatchScriptVar(updateTarget, "registrants", registrants);
                sendSystemMessage(self, SID_SUCCESS_REGISTER);
                utils.setScriptVar(self, "registerWithLocation", updateTarget);
            }
        }
        else 
        {
            sendSystemMessage(self, SID_CANNOT_REGISTER_LACK_SKILL);
        }
        return SCRIPT_CONTINUE;
    }
}
