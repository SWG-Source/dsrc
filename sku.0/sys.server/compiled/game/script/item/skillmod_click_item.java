package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.buff;
import script.library.static_item;
import script.library.prose;

public class skillmod_click_item extends script.base_script
{
    public skillmod_click_item()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "skillmod";
    public static final string_id SID_NOT_YET = new string_id("base_player", "not_yet");
    public static final string_id SID_NOT_LINKED = new string_id("base_player", "not_linked");
    public static final string_id SID_NOT_LINKED_TO_HOLDER = new string_id("base_player", "not_linked_to_holder");
    public static final string_id SID_ITEM_LEVEL_TOO_LOW = new string_id("base_player", "level_too_low");
    public static final string_id SID_ITEM_NOT_IN_INVENTORY = new string_id("base_player", "not_in_your_inventory");
    public static final string_id SID_MUST_BIO_LINK_FROM_INVENTORY = new string_id("base_player", "must_biolink_to_use_from_inventory");
    public static final string_id SID_BIOLINK_OTHER_PLAYER = new string_id("base_player", "wrong_player_per_biolink");
    public static final string_id SID_NO_USE_WHILE_DEAD = new string_id("player_structure", "while_dead");
    public static final string_id SID_ITEM_WRONG_SKILL = new string_id("base_player", "item_wrong_skill");
    public static final string_id SKILLMOD_APPLIED = new string_id("base_player", "skillmod_applied");
    public static final string_id CANT_APPLY_SKILLMOD = new string_id("base_player", "cant_use_item");
    public static final string_id SID_ITEM_NOT_OWNER = new string_id("base_player", "item_not_owner");
    public static final String OWNER_OID = "owner";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("skillmod_click_item.OnObjectMenuRequest: Init.");
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("skillmod_click_item.OnObjectMenuSelect: Init.");
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id biolink = getBioLink(self);
        if (isValidId(biolink) && biolink == utils.OBJ_ID_BIO_LINK_PENDING)
        {
            sendSystemMessage(player, SID_MUST_BIO_LINK_FROM_INVENTORY);
            return SCRIPT_CONTINUE;
        }
        if (isValidId(biolink) && biolink != player)
        {
            sendSystemMessage(player, SID_BIOLINK_OTHER_PLAYER);
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(self, OWNER_OID))
        {
            if (player != getObjIdObjVar(self, OWNER_OID))
            {
                sendSystemMessage(player, SID_ITEM_NOT_OWNER);
                return SCRIPT_CONTINUE;
            }
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIncapacitated(player) || isDead(player))
            {
                sendSystemMessage(player, SID_NO_USE_WHILE_DEAD);
                return SCRIPT_CONTINUE;
            }
            if (hasScript(self, "item.armor.biolink_item_non_faction"))
            {
                obj_id bioLinked = getBioLink(self);
                if (!isValidId(bioLinked) || isIdNull(bioLinked) || bioLinked == utils.OBJ_ID_BIO_LINK_PENDING)
                {
                    sendSystemMessage(player, SID_NOT_LINKED);
                    return SCRIPT_CONTINUE;
                }
                if (bioLinked != player)
                {
                    sendSystemMessage(player, SID_NOT_LINKED_TO_HOLDER);
                    return SCRIPT_CONTINUE;
                }
            }
            String itemName = getStaticItemName(self);
            if (itemName == null || itemName.equals(""))
            {
                LOG("create", "Itemname bad, name is " + itemName);
                return SCRIPT_CONTINUE;
            }
            dictionary itemData = new dictionary();
            itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, itemName);
            if (itemData == null)
            {
                LOG("create", itemName + "Buff Initalize could not happen because row in datatable is bad");
                return SCRIPT_CONTINUE;
            }
            String skillMod = itemData.getString("skill_mods");
            int requiredLevel = itemData.getInt("required_level_for_effect");
            String requiredSkill = itemData.getString("required_skill");
            String clientEffect = itemData.getString("client_effect");
            String clientAnimation = itemData.getString("client_animation");
            String skillModName = "";
            int skillModValue = 0;
            if (skillMod == null || skillMod.equals(""))
            {
                LOG("create", "This object needs a skill mod in the itemstats table to be used " + itemName);
                return SCRIPT_CONTINUE;
            }
            dictionary dict = static_item.parseSkillModifiers(player, skillMod);
            if (dict != null)
            {
                java.util.Enumeration keys = dict.keys();
                while (keys.hasMoreElements())
                {
                    skillModName = (String)keys.nextElement();
                    skillModValue = dict.getInt(skillModName);
                }
            }
            if (skillModName == null || skillModName.equals(""))
            {
                LOG("create", "This object skill mod was incorrectly named in itemstats table " + itemName);
                return SCRIPT_CONTINUE;
            }
            else if (skillModValue <= 0)
            {
                LOG("create", "This object skill mod had an invalid value " + itemName);
                return SCRIPT_CONTINUE;
            }
            boolean hideSkillMod = false;
            if (hasObjVar(self, "hide_skill_mod"))
            {
                hideSkillMod = getBooleanObjVar(self, "hide_skill_mod");
            }
            if (!static_item.validateLevelRequired(player, requiredLevel))
            {
                sendSystemMessage(player, SID_ITEM_LEVEL_TOO_LOW);
                return SCRIPT_CONTINUE;
            }
            if (requiredSkill != null && !requiredSkill.equals(""))
            {
                String classTemplate = getSkillTemplate(player);
                if (classTemplate != null && !classTemplate.equals(""))
                {
                    if (!classTemplate.startsWith(requiredSkill))
                    {
                        sendSystemMessage(player, SID_ITEM_WRONG_SKILL);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            blog("skillmod_click_item.OnObjectMenuSelect: Applying skillmod: " + skillModName + " with a value of: " + skillModValue);
            blog("skillmod_click_item.OnObjectMenuSelect: Entire skillmod string: " + skillMod);
            if (!applySkillStatisticModifier(player, skillModName, skillModValue))
            {
                blog("skillmod_click_item.OnObjectMenuSelect: Applying skillmod failed");
                CustomerServiceLog("create", " Item: " + self + " had skill mod: " + skillMod + " that was added/given to the Owner: " + player + ". Skill mod incremented by 1.");
                sendSystemMessage(player, CANT_APPLY_SKILLMOD);
                return SCRIPT_CONTINUE;
            }
            blog("skillmod_click_item.OnObjectMenuSelect: skillmod succeessfully applied.");
            CustomerServiceLog("create", " Item: " + self + " had skill mod: " + skillMod + " that was added/given to the Owner: " + player + ". Skill mod incremented by 1.");
            if (!hideSkillMod)
            {
                sendSystemMessage(player, SKILLMOD_APPLIED);
            }
            if (clientAnimation != null && !clientAnimation.equals(""))
            {
                doAnimationAction(player, clientAnimation);
            }
            if (clientEffect != null && !clientEffect.equals(""))
            {
                playClientEffectObj(player, clientEffect, player, "");
            }
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
