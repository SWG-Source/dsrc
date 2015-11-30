package script.systems.image_designer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.utils;
import script.library.sui;

public class player_image_designer extends script.base_script
{
    public player_image_designer()
    {
    }
    public static final String SCRIPT_IMAGE_DESIGNER_RESPONSE = "systems.image_designer.image_designer_response";
    public static final String SCRIPT_HOLO_EMOTE = "systems.image_designer.holoemote";
    public static final String SCRIPT_IMAGE_DESIGNER_CANCEL = "systems.image_designer.image_designer_cancel";
    public static final String VAR_IMAGE_DESIGN_CONFIRM = "image_designer.confirm";
    public static final String VAR_IMAGE_DESIGN_LD = "image_designer.linkdeath";
    public static final String VAR_IMAGE_DESIGN_TIMER_VALIDATION = "image_designer.start_time";
    public static final string_id SID_NO_TARGET = new string_id("image_designer", "no_target");
    public static final string_id SID_NOT_A_PLAYER = new string_id("image_designer", "not_a_player");
    public static final string_id SID_TARGET_DEAD = new string_id("image_designer", "target_dead");
    public static final string_id SID_OUT_OF_RANGE = new string_id("image_designer", "out_of_range");
    public static final string_id SID_NOT_IN_SAME_GROUP = new string_id("image_designer", "not_in_same_group");
    public static final string_id SID_NOT_AN_IMAGE_DESIGNER = new string_id("image_designer", "not_an_image_designer");
    public static final string_id SID_ILLEGAL_SELECTION = new string_id("image_designer", "illegal_selection");
    public static final string_id SID_INSUFFICIENT_SKILL = new string_id("image_designer", "insufficient_skill");
    public static final string_id SID_IMAGE_DESIGN_COMPLETE_DESIGNER = new string_id("image_designer", "image_design_complete_designer");
    public static final string_id SID_IMAGE_DESIGN_COMPLETE_TARGET = new string_id("image_designer", "image_design_complete_target");
    public static final string_id SID_EXPERIENCE_GRANTED = new string_id("image_designer", "experience_granted");
    public static final string_id SID_OUTSTANDING_OFFER = new string_id("image_designer", "outstanding_offer");
    public static final string_id SID_ALREADY_IMAGE_DESIGNING = new string_id("image_designer", "already_image_designing");
    public static final string_id SID_TARGET_IS_IMAGE_DESIGNING = new string_id("image_designer", "target_is_image_designing");
    public static final string_id SID_ALREADY_BEING_IMAGE_DESIGNED = new string_id("image_designer", "already_being_image_designed");
    public static final float IMAGE_DESIGN_RANGE = 8.0f;
    public static int id_status = 0;
    public static int id_accept = 0;
    public int OnImageDesignCanceled(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM);
        detachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        return SCRIPT_CONTINUE;
    }
    public int imageDesignRequestStart(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_image_designer::imageDesignRequestStart");
        if (!hasCommand(self, "imagedesign"))
        {
            LOG("LOG_CHANNEL", self + " ->You must be an image designer to do that.");
            sendSystemMessage(self, SID_NOT_AN_IMAGE_DESIGNER);
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE))
        {
            LOG("LOG_CHANNEL", self + " ->You are already performing an image design action.");
            sendSystemMessage(self, SID_ALREADY_IMAGE_DESIGNING);
            return SCRIPT_CONTINUE;
        }
        obj_id design_target = getIntendedTarget(self);
        if (!isIdValid(design_target) || !isPlayer(design_target))
        {
            if (isIdValid(target) && isPlayer(target))
            {
                design_target = target;
            }
            else 
            {
                design_target = self;
            }
        }
        if (!isPlayer(design_target))
        {
            sendSystemMessage(self, SID_NOT_A_PLAYER);
            return SCRIPT_CONTINUE;
        }
        if (isDead(design_target) || isIncapacitated(design_target))
        {
            LOG("LOG_CHANNEL", self + " ->" + getFirstName(design_target) + " needs a lot more than a make over.");
            prose_package pp = prose.getPackage(SID_TARGET_DEAD, design_target);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(design_target, VAR_IMAGE_DESIGN_CONFIRM))
        {
            LOG("LOG_CHANNEL", self + " ->" + getFirstName(design_target) + " already has an outstanding image design offer.");
            prose_package pp = prose.getPackage(SID_OUTSTANDING_OFFER, design_target);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (hasScript(design_target, SCRIPT_IMAGE_DESIGNER_RESPONSE))
        {
            LOG("LOG_CHANNEL", self + " ->" + getFirstName(design_target) + " is already Image Designing someone else.");
            prose_package pp = prose.getPackage(SID_TARGET_IS_IMAGE_DESIGNING, design_target);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, VAR_IMAGE_DESIGN_CONFIRM))
        {
            LOG("LOG_CHANNEL", self + " ->You cannot initiate an Image Design session while being Image Designed.");
            prose_package pp = prose.getPackage(SID_ALREADY_BEING_IMAGE_DESIGNED, design_target);
            sendSystemMessageProse(self, pp);
            return SCRIPT_CONTINUE;
        }
        float distance = (getLocation(self)).distance(getLocation(design_target));
        if (distance > IMAGE_DESIGN_RANGE)
        {
            LOG("LOG_CHANNEL", self + " ->You must be closer to your target in order to use your image design abilities.");
            sendSystemMessage(self, SID_OUT_OF_RANGE);
            return SCRIPT_CONTINUE;
        }
        obj_id designer_group = getGroupObject(self);
        obj_id target_group = getGroupObject(design_target);
        LOG("LOG_CHANNEL", "designer group ->" + designer_group + " target_group ->" + target_group);
        if (design_target != self)
        {
            if ((!isIdValid(designer_group)) || (!isIdValid(target_group)))
            {
                LOG("LOG_CHANNEL", self + " ->You must be within the same group as " + getFirstName(design_target) + " in order to use your image design abilities.");
                prose_package pp = prose.getPackage(SID_NOT_IN_SAME_GROUP, design_target);
                sendSystemMessageProse(self, pp);
                return SCRIPT_CONTINUE;
            }
            if (designer_group != target_group)
            {
                LOG("LOG_CHANNEL", self + " ->You must be within the same group as " + getFirstName(design_target) + " in order to use your image design abilities.");
                prose_package pp = prose.getPackage(SID_NOT_IN_SAME_GROUP, design_target);
                sendSystemMessageProse(self, pp);
                return SCRIPT_CONTINUE;
            }
        }
        String holoEmote = "";
        if (hasScript(design_target, SCRIPT_HOLO_EMOTE))
        {
            
        }
        
        {
            holoEmote = utils.getStringObjVar(design_target, "holoemote");
        }
        obj_id closestTerminal = null;
        obj_id structure = getTopMostContainer(self);
        obj_id targetStructure = getTopMostContainer(design_target);
        if ((structure != self) && utils.hasObjVar(structure, "salon"))
        {
            if ((targetStructure != design_target) && utils.hasObjVar(targetStructure, "salon"))
            {
                if (structure == targetStructure)
                {
                    closestTerminal = structure;
                }
            }
        }
        utils.setScriptVar(design_target, VAR_IMAGE_DESIGN_CONFIRM, design_target);
        utils.setScriptVar(self, VAR_IMAGE_DESIGN_LD, design_target);
        attachScript(self, SCRIPT_IMAGE_DESIGNER_RESPONSE);
        attachScript(design_target, SCRIPT_IMAGE_DESIGNER_CANCEL);
        imagedesignStart(self, design_target, closestTerminal, holoEmote);
        return SCRIPT_CONTINUE;
    }
}
