package script.event.ewok_festival;

import script.library.prose;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class loveday_reward_crossbow extends script.base_script
{
    public loveday_reward_crossbow()
    {
    }
    public static final string_id SID_ITEM_NOT_IN_INVENTORY = new string_id("base_player", "not_in_your_inventory");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("base_player", "loveday_crossbow_use"));
                string_id equipUnequip = new string_id("ui_radial", "item_equip");
                if (utils.isEquipped(self))
                {
                    equipUnequip = new string_id("ui_radial", "item_unequip");
                }
                mi.addRootMenu(menu_info_types.SERVER_MENU11, equipUnequip);
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
                sendSystemMessage(player, new string_id("player_structure", "while_dead"));
                return SCRIPT_CONTINUE;
            }
            String coolDownGroup = "loveday_crossbow";
            String clientEffect = "appearance/pt_efol_hearts.prt";
            String clientAnimation = "";
            int reuseTime = 120;
            String varName = "clickItem." + coolDownGroup;
            int buffTime = getIntObjVar(player, varName);
            if (getGameTime() > buffTime || getGameTime() < buffTime && isGod(player))
            {
                if (getGameTime() < buffTime && isGod(player))
                {
                    sendSystemMessage(player, "The item was used because you were in god mode.", null);
                }
                if (reduceHateOnTarget(player, clientEffect))
                {
                    setObjVar(player, varName, (getGameTime() + (reuseTime)));
                    sendCooldownGroupTimingOnly(player, getStringCrc(coolDownGroup.toLowerCase()), reuseTime);
                    sendSystemMessage(player, new string_id("base_player", "loveday_crossbow_hate_reduced"));
                    if (clientAnimation.length() > 0)
                    {
                        doAnimationAction(player, clientAnimation);
                    }
                }
                else 
                {
                    sendSystemMessage(player, new string_id("base_player", "loveday_crossbow_no_hate"));
                }
            }
            else 
            {
                sendSystemMessageProse(player, prose.getPackage(new string_id("base_player", "not_yet"), buffTime - getGameTime()));
                return SCRIPT_CONTINUE;
            }
        }
        else if (item == menu_info_types.SERVER_MENU11)
        {
            if (utils.isEquipped(self))
            {
                putInOverloaded(self, getObjectInSlot(player, "inventory"));
            }
            else 
            {
                equip(self, player);
            }
            sendDirtyObjectMenuNotification(self);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean reduceHateOnTarget(obj_id player, String clientEffect) throws InterruptedException
    {
        obj_id target = getLookAtTarget(player);
        if (!isIdValid(target))
        {
            target = getIntendedTarget(player);
        }
        if (isIdValid(target))
        {
            obj_id[] thoseWhoHateMe = getHateList(player);
            int playerLevel = getLevel(player);
            if (thoseWhoHateMe.length != 0)
            {
                float hateReduction;
                float hate;
                float difficultyClass;

                for (obj_id hater : thoseWhoHateMe) {
                    if (hater == target) {
                        hateReduction = 0.90f;
                        difficultyClass = getIntObjVar(hater, "difficultyClass");
                        if (difficultyClass > 1) {
                            hateReduction = 0.92f;
                        }
                        if (playerLevel - getLevel(hater) < -24) {
                            hateReduction = 0.96f;
                        }
                        hate = getHate(hater, player);
                        hate *= hateReduction;
                        setHate(hater, player, hate);
                        if (clientEffect.length() > 0) {
                            playClientEffectObj(player, clientEffect, target, "head");
                        }
                        return true;
                    }
                }
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("base_player", "loveday_crossbow_no_target"));
        }
        return false;
    }
}
