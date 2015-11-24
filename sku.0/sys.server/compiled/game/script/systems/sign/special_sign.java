package script.systems.sign;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class special_sign extends script.base_script
{
    public special_sign()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "special_sign";
    public static final string_id SID_NO_USE_WHILE_DEAD = new string_id("player_structure", "while_dead");
    public static final string_id SKILLMOD_APPLIED = new string_id("base_player", "skillmod_applied");
    public static final string_id CANT_APPLY_SKILLMOD = new string_id("base_player", "cant_use_item");
    public static final string_id SID_SKILLMOD_INVOCATION_CANCELED = new string_id("base_player", "skillmod_canceled");
    public static final string_id SID_OBJECT_CONSUMED = new string_id("player_structure", "token_object_consumed");
    public static final string_id SID_CONSUME_OBJECT = new string_id("player_structure", "consume_token");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("tcg_vendor_contract.OnObjectMenuRequest: Init.");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithinAPlayer(self) || utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_CONSUME_OBJECT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("tcg_vendor_contract.OnObjectMenuSelect: Init.");
        if (!utils.isNestedWithinAPlayer(self) || utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        blog("special_sign.OnObjectMenuSelect: sending to skillmod grant function.");
        if (!grantSignSkillMod(self, player))
        {
            blog("special_sign.OnObjectMenuSelect: Skillmod Function Failed.");
            sendSystemMessage(player, SID_SKILLMOD_INVOCATION_CANCELED);
            CustomerServiceLog("playerStructure", "Special Sign Token Object: " + self + " failed to apply a special sign skill mod to player: " + player + ". The contract was not deleted.");
            return SCRIPT_CONTINUE;
        }
        blog("special_sign.OnObjectMenuSelect: Special Sign Skill Mod Application Succeeded. Deleting Contract");
        CustomerServiceLog("playerStructure", " Special Sign Token Object: " + self + " successfully applied a Special Sign skill mod to player: " + player + ". The contract will now be deleted.");
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public boolean grantSignSkillMod(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (isIncapacitated(player) || isDead(player))
        {
            sendSystemMessage(player, SID_NO_USE_WHILE_DEAD);
            return false;
        }
        String itemName = getStaticItemName(self);
        dictionary itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, itemName);
        if (itemData == null)
        {
            blog(itemName + "Special Sign - Initalize could not happen because row in datatable is bad");
            return false;
        }
        String skillMod = itemData.getString("skill_mods");
        String clientEffect = itemData.getString("client_effect");
        String clientAnimation = itemData.getString("client_animation");
        String skillModName = "";
        int skillModValue = 0;
        if (skillMod == null || skillMod.length() <= 0)
        {
            blog("Special Sign - This object needs a skill mod in the itemstats table to be used " + itemName);
            return false;
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
            blog("Special Sign - This object skill mod was incorrectly named in itemstats table " + itemName);
            return false;
        }
        else if (skillModValue <= 0)
        {
            blog("Special Sign - This object skill mod had an invalid value " + itemName);
            return false;
        }
        blog("Special Sign - Applying skillmod: " + skillModName + " with a value of: " + skillModValue);
        blog("Special Sign - Entire skillmod string: " + skillMod);
        if (!applySkillStatisticModifier(player, skillModName, skillModValue))
        {
            blog("Special Sign - Applying skillmod failed");
            CustomerServiceLog("create", " Item: " + self + " had skill mod: " + skillMod + " that was added/given to the Owner: " + player + ". Skill mod incremented by 1.");
            sendSystemMessage(player, CANT_APPLY_SKILLMOD);
            return false;
        }
        blog("Special Sign - skillmod succeessfully applied.");
        CustomerServiceLog("create", " Item: " + self + " had skill mod: " + skillMod + " that was added/given to the Owner: " + player + ". Skill mod incremented by 1.");
        sendSystemMessage(player, SID_OBJECT_CONSUMED);
        if (clientAnimation != null && !clientAnimation.equals(""))
        {
            doAnimationAction(player, clientAnimation);
        }
        if (clientEffect != null && !clientEffect.equals(""))
        {
            playClientEffectObj(player, clientEffect, player, "");
        }
        return true;
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
