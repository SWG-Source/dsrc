package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.colors;
import script.library.healing;
import script.library.prose;
import script.library.pvp;
import script.library.static_item;
import script.library.utils;

public class full_heal_item extends script.base_script
{
    public full_heal_item()
    {
    }
    public static final string_id SID_NOT_YET = new string_id("base_player", "not_yet");
    public static final string_id SID_NOT_LINKED = new string_id("base_player", "not_linked");
    public static final string_id SID_NOT_LINKED_TO_HOLDER = new string_id("base_player", "not_linked_to_holder");
    public static final string_id CANT_APPLY_BUFF = new string_id("base_player", "cant_apply_buff");
    public static final string_id BUFF_APPLIED = new string_id("base_player", "buff_applied");
    public static final string_id SID_ITEM_LEVEL_TOO_LOW = new string_id("base_player", "level_too_low");
    public static final string_id SID_ITEM_NOT_IN_INVENTORY = new string_id("base_player", "not_in_your_inventory");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final string_id SID_NO_NEED_TO_HEAL = new string_id("base_player", "no_need_to_heal");
    public static final String[] ATTRIBUTES = 
    {
        "HEALTH",
        "CONSTITUTION",
        "ACTION",
        "STAMINA",
        "MIND",
        "WILLPOWER"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
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
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIncapacitated(player) || isDead(player))
            {
                sendSystemMessage(player, new string_id("spam", "cant_do_it_state"));
                return SCRIPT_CONTINUE;
            }
            if (hasScript(self, "item.armor.biolink_item_non_faction"))
            {
                obj_id bioLinked = getBioLink(self);
                if (bioLinked == null || bioLinked == utils.OBJ_ID_BIO_LINK_PENDING)
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
            if (getState(player, STATE_COMBAT) > 0)
            {
                if (!hasObjVar(self, "allowInCombat"))
                {
                    sendSystemMessage(player, SID_NOT_WHILE_IN_COMBAT);
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
            String coolDownGroup = itemData.getString("cool_down_group");
            String clientEffect = itemData.getString("client_effect");
            String clientAnimation = itemData.getString("client_animation");
            int reuseTime = itemData.getInt("reuse_time");
            int requiredLevel = itemData.getInt("required_level_for_effect");
            String varName = "clickItem." + coolDownGroup;
            int healTime = getIntObjVar(player, varName);
            int playerLevel = getLevel(player);
            if (static_item.validateLevelRequired(player, requiredLevel))
            {
                if (getGameTime() > healTime)
                {
                    int max_hp = getMaxAttrib(player, HEALTH);
                    int hp = getAttrib(player, HEALTH);
                    int to_heal = max_hp - hp;
                    if (to_heal <= 0)
                    {
                        sendSystemMessage(player, SID_NO_NEED_TO_HEAL);
                        return SCRIPT_CONTINUE;
                    }
                    int delta = healing.healDamage(self, player, HEALTH, to_heal);
                    setObjVar(player, varName, (getGameTime() + (reuseTime)));
                    sendCooldownGroupTimingOnly(player, getStringCrc(coolDownGroup.toLowerCase()), reuseTime);
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("healing", "heal_fly"));
                    pp = prose.setDI(pp, to_heal);
                    pp = prose.setTO(pp, ATTRIBUTES[HEALTH]);
                    showFlyTextPrivateProseWithFlags(player, player, pp, 2.0f, colors.SEAGREEN, FLY_TEXT_FLAG_IS_HEAL);
                    doAnimationAction(player, clientAnimation);
                    playClientEffectObj(player, clientEffect, player, "");
                    if (getCount(self) > 0)
                    {
                        static_item.decrementStaticItem(self);
                    }
                }
                else 
                {
                    int timeDiff = healTime - getGameTime();
                    prose_package pp = prose.getPackage(SID_NOT_YET, timeDiff);
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                sendSystemMessage(player, SID_ITEM_LEVEL_TOO_LOW);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
