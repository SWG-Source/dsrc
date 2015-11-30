package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.sui;
import script.library.temp_schematic;
import script.library.utils;
import script.library.xp;

public class xp_purchase extends script.base_script
{
    public xp_purchase()
    {
    }
    public static final string_id SID_MENU_LEARN = new string_id("item/xp_purchase", "menu_learn");
    public static final string_id SID_MSG_NO_MIN_SKILL = new string_id("item/xp_purchase", "msg_no_min_skill");
    public static final string_id SID_MSG_NO_XP = new string_id("item/xp_purchase", "msg_no_xp");
    public static final string_id SID_MSG_NOT_ENOUGH_XP = new string_id("item/xp_purchase", "msg_not_enough_xp");
    public static final string_id SID_MSG_ALREADY_HAVE_SCHEMATIC = new string_id("item/xp_purchase", "msg_already_have_schematic");
    public static final string_id SID_MSG_ALREADY_HAVE_COMMAND = new string_id("item/xp_purchase", "msg_already_have_command");
    public static final string_id SID_MSG_LEARNED_SCHEMATIC = new string_id("item/xp_purchase", "msg_learned_schematic");
    public static final string_id SID_MSG_LEARNED_COMMAND = new string_id("item/xp_purchase", "msg_learned_command");
    public static final string_id SUI_PROMPT1 = new string_id("item/xp_purchase", "sui_prompt1");
    public static final string_id SUI_PROMPT2 = new string_id("item/xp_purchase", "sui_prompt2");
    public static final String SUI_TITLE = "@item/xp_purchase:sui_title";
    public static final int MENU_TYPE = menu_info_types.SERVER_MENU10;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isItemConfigured(self))
        {
            mi.addRootMenu(MENU_TYPE, SID_MENU_LEARN);
        }
        else 
        {
            CustomerServiceLog("item", "XP Purchase Item, " + getName(self) + " (" + self + "), is not configured correctly upon use by %TU", player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == MENU_TYPE)
        {
            learn(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!isItemConfigured(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "experience_required";
        attribs[idx] = " " + getXpAmount(self) + " " + localize(new string_id("exp_n", getXpType(self)));
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasMinSkill(self))
        {
            names[idx] = "skill_required";
            attribs[idx] = " " + localize(new string_id("skl_n", getMinSkillName(self)));
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isItemConfigured(obj_id item) throws InterruptedException
    {
        boolean configured = true;
        configured &= hasObjVar(item, "xp_type");
        configured &= hasObjVar(item, "xp_amt");
        configured &= hasObjVar(item, "grant_type");
        configured &= hasObjVar(item, "grant_name");
        return configured;
    }
    public String getXpType(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, "xp_type"))
        {
            return getStringObjVar(item, "xp_type");
        }
        else 
        {
            return null;
        }
    }
    public int getXpAmount(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, "xp_amt"))
        {
            return getIntObjVar(item, "xp_amt");
        }
        else 
        {
            return -1;
        }
    }
    public String getGrantType(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, "grant_type"))
        {
            return getStringObjVar(item, "grant_type");
        }
        else 
        {
            return null;
        }
    }
    public String getGrantName(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, "grant_name"))
        {
            return getStringObjVar(item, "grant_name");
        }
        else 
        {
            return null;
        }
    }
    public boolean hasMinSkill(obj_id item) throws InterruptedException
    {
        return hasObjVar(item, "min_skill");
    }
    public String getMinSkillName(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, "min_skill"))
        {
            return getStringObjVar(item, "min_skill");
        }
        else 
        {
            return null;
        }
    }
    public boolean canLearn(obj_id self, obj_id player, String type, String name) throws InterruptedException
    {
        if (type.equals("schematic"))
        {
            if (hasObjVar(self, "uses"))
            {
                return true;
            }
            if (hasSchematic(player, name))
            {
                return false;
            }
        }
        else if (type.equals("command"))
        {
            if (hasCommand(player, name))
            {
                return false;
            }
        }
        return true;
    }
    public void learn(obj_id self, obj_id player) throws InterruptedException
    {
        if (hasMinSkill(self))
        {
            String minSkill = getMinSkillName(self);
            if (minSkill == null || minSkill.equals(""))
            {
                return;
            }
            if (!hasSkill(player, minSkill))
            {
                prose_package pp = prose.getPackage(SID_MSG_NO_MIN_SKILL, localize(new string_id("skl_n", minSkill)));
                sendSystemMessageProse(player, pp);
                return;
            }
        }
        String xpType = getXpType(self);
        int xpAmt = getXpAmount(self);
        int currentXp = getExperiencePoints(player, xpType);
        if (currentXp == 0)
        {
            prose_package pp = prose.getPackage(SID_MSG_NO_XP, localize(new string_id("exp_n", xpType)));
            sendSystemMessageProse(player, pp);
            return;
        }
        if (currentXp < xpAmt)
        {
            prose_package pp = prose.getPackage(SID_MSG_NOT_ENOUGH_XP, localize(new string_id("exp_n", xpType)), xpAmt);
            sendSystemMessageProse(player, pp);
            return;
        }
        String type = getGrantType(self);
        String name = getGrantName(self);
        if (!canLearn(self, player, type, name))
        {
            if (type.equals("schematic"))
            {
                sendSystemMessage(player, SID_MSG_ALREADY_HAVE_SCHEMATIC);
            }
            else if (type.equals("command"))
            {
                sendSystemMessage(player, SID_MSG_ALREADY_HAVE_COMMAND);
            }
            return;
        }
        int pid = -1;
        if (utils.hasScriptVar(player, "xp_purchase_sui"))
        {
            pid = utils.getIntScriptVar(player, "xp_purchase_sui");
            forceCloseSUIPage(pid);
        }
        String prompt = localize(SUI_PROMPT1) + xpAmt + " " + localize(new string_id("exp_n", xpType)) + localize(SUI_PROMPT2);
        pid = sui.msgbox(self, player, prompt, sui.YES_NO, SUI_TITLE, "handleLearn");
        if (pid > -1)
        {
            utils.setScriptVar(player, "xp_purchase_sui", pid);
        }
    }
    public int handleLearn(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        if (utils.hasScriptVar(player, "xp_purchase_sui"))
        {
            utils.removeScriptVar(player, "xp_purchase_sui");
        }
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        String type = getGrantType(self);
        String name = getGrantName(self);
        String xpType = getXpType(self);
        int xpAmt = getXpAmount(self);
        if (hasMinSkill(self))
        {
            String minSkill = getMinSkillName(self);
            if (minSkill == null || minSkill.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            if (!hasSkill(player, minSkill))
            {
                prose_package pp = prose.getPackage(SID_MSG_NO_MIN_SKILL, minSkill);
                sendSystemMessageProse(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        int currentXp = getExperiencePoints(player, xpType);
        if (currentXp == 0)
        {
            prose_package pp = prose.getPackage(SID_MSG_NO_XP, xpType);
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        if (currentXp < xpAmt)
        {
            prose_package pp = prose.getPackage(SID_MSG_NOT_ENOUGH_XP, xpType, xpAmt);
            sendSystemMessageProse(player, pp);
            return SCRIPT_CONTINUE;
        }
        if (!canLearn(self, player, type, name))
        {
            if (type.equals("schematic"))
            {
                sendSystemMessage(player, SID_MSG_ALREADY_HAVE_SCHEMATIC);
            }
            else if (type.equals("command"))
            {
                sendSystemMessage(player, SID_MSG_ALREADY_HAVE_COMMAND);
            }
            return SCRIPT_CONTINUE;
        }
        xpAmt *= -1;
        if (xp.grantUnmodifiedExperience(player, xpType, xpAmt))
        {
            if (type.equals("schematic"))
            {
                if (hasObjVar(self, "uses"))
                {
                    int uses = getIntObjVar(self, "uses");
                    temp_schematic.grant(player, name, uses);
                    sendSystemMessage(player, SID_MSG_LEARNED_SCHEMATIC);
                }
                else 
                {
                    grantSchematic(player, name);
                    sendSystemMessage(player, SID_MSG_LEARNED_SCHEMATIC);
                }
            }
            else if (type.equals("command"))
            {
                grantCommand(player, name);
                sendSystemMessage(player, SID_MSG_LEARNED_COMMAND);
            }
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(self, "handleLearnSuccess", d, 0.0f, false);
            if (!hasObjVar(self, "no_destroy"))
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
