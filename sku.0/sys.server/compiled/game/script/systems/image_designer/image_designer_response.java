package script.systems.image_designer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class image_designer_response extends script.base_script
{
    public image_designer_response()
    {
    }
    public static final String DATATABLE_CUSTOMIZATION = "datatables/customization/customization_skill_mods.iff";
    public static final String DATATABLE_HAIR_STYLE_MODS = "datatables/customization/hair_assets_skill_mods.iff";
    public static final String SCRIPT_IMAGE_DESIGNER_RESPONSE = "systems.image_designer.image_designer_response";
    public static final String SCRIPT_HOLO_EMOTE = "systems.image_designer.holoemote";
    public static final String SCRIPT_IMAGE_DESIGNER_CANCEL = "systems.image_designer.image_designer_cancel";
    public static final String VAR_IMAGE_DESIGN_CONFIRM = "image_designer.confirm";
    public static final String VAR_IMAGE_DESIGN_LD = "image_designer.linkdeath";
    public static final String VAR_IMAGE_DESIGN_TIMESTAMP = "image_designer.time_stamp";
    public static final string_id SID_NO_TARGET = new string_id("image_designer", "no_target");
    public static final string_id SID_NOT_A_PLAYER = new string_id("image_designer", "not_a_player");
    public static final string_id SID_NO_IMAGE_SELF = new string_id("image_designer", "no_image_self");
    public static final string_id SID_TARGET_DEAD = new string_id("image_designer", "target_dead");
    public static final string_id SID_OUT_OF_RANGE = new string_id("image_designer", "out_of_range");
    public static final string_id SID_NOT_IN_SAME_GROUP = new string_id("image_designer", "not_in_same_group");
    public static final string_id SID_NOT_AN_IMAGE_DESIGNER = new string_id("image_designer", "not_an_image_designer");
    public static final string_id SID_ILLEGAL_SELECTION = new string_id("image_designer", "illegal_selection");
    public static final string_id SID_INSUFFICIENT_SKILL = new string_id("image_designer", "insufficient_skill");
    public static final string_id SID_INSUFFICIENT_ACTION = new string_id("image_designer", "insufficient_action");
    public static final string_id SID_IMAGE_DESIGN_COMPLETE_DESIGNER = new string_id("image_designer", "image_design_complete_designer");
    public static final string_id SID_IMAGE_DESIGN_COMPLETE_TARGET = new string_id("image_designer", "image_design_complete_target");
    public static final string_id SID_EXPERIENCE_GRANTED = new string_id("image_designer", "experience_granted");
    public static final string_id SID_OUTSTANDING_OFFER = new string_id("image_designer", "outstanding_offer");
    public static final string_id SID_OUT_OF_RANGE_DESIGNER = new string_id("image_designer", "out_of_range_designer");
    public static final string_id SID_OUT_OF_RANGE_TARGET = new string_id("image_designer", "out_of_range_target");
    public static final string_id SID_OFFER_DECLINED = new string_id("image_designer", "offer_declined");
    public static final string_id SID_OFFER_TIMEOUT = new string_id("image_designer", "offer_timeout");
    public static final string_id SID_OFFER_TIMEOUT_TARGET = new string_id("image_designer", "offer_timeout_target");
    public static final String STF_FILE = "image_designer";
    public static final float IMAGE_DESIGN_RANGE = 8.0f;
    public static final int IMAGE_DESIGN_COST_BASE = 65;
    public static final int IMAGE_DESIGN_EXPERIENCE_COSMETIC = 100;
    public static final int IMAGE_DESIGN_EXPERIENCE_PHYSICAL = 300;
    public static final int IMAGE_DESIGN_EXPERIENCE_STAT_MIG = 2000;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE))
        {
            detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        }
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE))
        {
            detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        }
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE))
        {
            detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        }
        if (isIdValid(self))
        {
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        }
        obj_id ldTarget = null;
        if (utils.hasScriptVar(self, VAR_IMAGE_DESIGN_LD))
        {
            ldTarget = utils.getObjIdScriptVar(self, VAR_IMAGE_DESIGN_LD);
        }
        if (hasScript(ldTarget, SCRIPT_IMAGE_DESIGNER_CANCEL))
        {
            detachScript(ldTarget, SCRIPT_IMAGE_DESIGNER_CANCEL);
        }
        if (isIdValid(ldTarget))
        {
            utils.removeScriptVar(ldTarget, VAR_IMAGE_DESIGN_CONFIRM);
        }
        if (isIdValid(ldTarget))
        {
            utils.removeScriptVar(ldTarget, VAR_IMAGE_DESIGN_LD);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnImageDesignValidate(obj_id self, obj_id designerId, obj_id target, obj_id terminalId, int startingTime, int designType, boolean newHairSet, String newHairAsset, String hairCustomizationData, int designerRequiredCredits, int recipientPaidCredits, boolean accepted, String[] morphChangesKeys, float[] morphChangesValues, String[] indexChangesKeys, int[] indexChangesValues, String holoEmote) throws InterruptedException
    {
        if (!accepted)
        {
            LOG("LOG_CHANNEL", self + " ->" + getFirstName(target) + " has refused your image design offer.");
            prose_package pp = prose.getPackage(SID_OFFER_DECLINED, target);
            sendSystemMessageProse(self, pp);
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_TIMESTAMP);
            if (isIdValid(target))
            {
                utils.removeScriptVar(target, VAR_IMAGE_DESIGN_CONFIRM);
            }
            detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
            return SCRIPT_CONTINUE;
        }
        float distance = (getLocation(self)).distance(getLocation(target));
        if (distance > IMAGE_DESIGN_RANGE)
        {
            LOG("LOG_CHANNEL", self + " ->Your target has moved too far away.");
            sendSystemMessage(self, SID_OUT_OF_RANGE);
            LOG("LOG_CHANNEL", target + " ->You are now too far away from the image designer.");
            sendSystemMessage(target, SID_OUT_OF_RANGE_TARGET);
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_TIMESTAMP);
            if (isIdValid(target))
            {
                utils.removeScriptVar(target, VAR_IMAGE_DESIGN_CONFIRM);
            }
            detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
            return SCRIPT_CONTINUE;
        }
        obj_id closestTerminal = null;
        obj_id structure = getTopMostContainer(self);
        obj_id targetStructure = getTopMostContainer(target);
        if ((structure != self) && utils.hasObjVar(structure, "salon"))
        {
            if ((targetStructure != target) && utils.hasObjVar(targetStructure, "salon"))
            {
                if (structure == targetStructure)
                {
                    if (terminalId != structure)
                    {
                        accepted = false;
                    }
                }
                else 
                {
                    accepted = false;
                }
            }
            else 
            {
                accepted = false;
            }
        }
        else if ((targetStructure != self) && utils.hasObjVar(targetStructure, "salon"))
        {
            accepted = false;
        }
        else if (structure != self && targetStructure != self && structure != targetStructure)
        {
            accepted = false;
        }
        int cost = IMAGE_DESIGN_COST_BASE * designType;
        int action = getAttrib(self, ACTION);
        LOG("LOG_CHANNEL", "cost ->" + cost + " action ->" + action);
        if (cost > action)
        {
            LOG("LOG_CHANNEL", self + " ->You don't have enough mind to do that.");
            sendSystemMessage(self, SID_INSUFFICIENT_ACTION);
            utils.removeScriptVar(self, VAR_IMAGE_DESIGN_TIMESTAMP);
            if (isIdValid(target))
            {
                utils.removeScriptVar(target, VAR_IMAGE_DESIGN_CONFIRM);
            }
            detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
            return SCRIPT_CONTINUE;
        }
        drainAttributes(self, cost, 0);
        if (!holoEmote.equals(""))
        {
            utils.setObjVar(target, "holoEmote", holoEmote);
            int numCharges = 20;
            if (buff.hasBuff(designerId, "entertainer_inspiration"))
            {
                numCharges = 50;
            }
            utils.setObjVar(target, "holoEmoteCharges", numCharges);
            attachScript(target, SCRIPT_HOLO_EMOTE);
        }
        imagedesignValidated(designerId, target, terminalId, startingTime, designType, newHairSet, newHairAsset, hairCustomizationData, designerRequiredCredits, recipientPaidCredits, accepted, morphChangesKeys, morphChangesValues, indexChangesKeys, indexChangesValues, holoEmote);
        return SCRIPT_CONTINUE;
    }
    public int OnImageDesignCompleted(obj_id self, obj_id designerId, obj_id target, obj_id terminalId, int startingTime, int designType, boolean newHairSet, String newHairAsset, String hairCustomizationData, int designerRequiredCredits, int recipientPaidCredits, boolean accepted, String[] morphChangesKeys, float[] morphChangesValues, String[] indexChangesKeys, int[] indexChangesValues, String holoEmote) throws InterruptedException
    {
        utils.removeScriptVar(self, VAR_IMAGE_DESIGN_TIMESTAMP);
        if (isIdValid(target))
        {
            utils.removeScriptVar(target, VAR_IMAGE_DESIGN_CONFIRM);
        }
        if (hasScript(self, "theme_park.new_player.new_player"))
        {
            dictionary webster = new dictionary();
            webster.put("target", target);
            webster.put("hairStyled", newHairSet);
            messageTo(self, "handleNewPlayerEntertainerAction", webster, 1, false);
        }
        if (!holoEmote.equals(""))
        {
            utils.setObjVar(target, "holoEmote", holoEmote);
            int numCharges = 20;
            if (buff.hasBuff(designerId, "entertainer_inspiration"))
            {
                numCharges = 50;
            }
            utils.setObjVar(target, "holoEmoteCharges", numCharges);
            attachScript(target, SCRIPT_HOLO_EMOTE);
            sendSystemMessage(target, new string_id(STF_FILE, "new_holoemote"));
        }
        if (newHairSet || !holoEmote.equals("") || morphChangesKeys.length != 0 || indexChangesKeys.length != 0)
        {
            int experience = 0;
            if (designType == 2)
            {
                experience = IMAGE_DESIGN_EXPERIENCE_STAT_MIG;
                if (target == self)
                {
                    experience = (int)(experience / 2);
                }
            }
            else if (designType == 1)
            {
                experience = IMAGE_DESIGN_EXPERIENCE_PHYSICAL;
                if (target == self)
                {
                    experience = (int)(experience / 2);
                }
            }
            else 
            {
                experience = IMAGE_DESIGN_EXPERIENCE_COSMETIC;
                if (target == self)
                {
                    experience = (int)(experience / 2);
                }
            }
            xp.grantSocialStyleXp(self, xp.IMAGEDESIGNER, experience);
        }
        detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        return SCRIPT_CONTINUE;
    }
    public int OnImageDesignCanceled(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        return SCRIPT_CONTINUE;
    }
}
