package script.item.tool;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.skill;
import script.library.trial;
import script.library.utils;
import script.library.reverse_engineering;
import script.library.sui;

public class reverse_engineering_poweredup_item extends script.base_script
{
    public reverse_engineering_poweredup_item()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        float dieTime = reverse_engineering.getDieTime(reverse_engineering.EXPIRATION_TIME, self);
        if (dieTime < 1)
        {
            dieTime = 1.0f;
        }
        trial.bumpSession(self, "cleanUp");
        messageTo(self, "cleanUp", trial.getSessionDict(self, "cleanUp"), dieTime, false);
        String slotName = reverse_engineering.getMyEquippedSlot(self);
        if (slotName.equals("none"))
        {
            return SCRIPT_CONTINUE;
        }
        int power = getIntObjVar(self, reverse_engineering.ENGINEERING_POWER);
        String mod = getStringObjVar(self, reverse_engineering.ENGINEERING_MODIFIER);
        int ratio = getIntObjVar(self, reverse_engineering.ENGINEERING_RATIO);
        int finalPower = power / ratio;
        if (finalPower < 1)
        {
            finalPower = 1;
        }
        obj_id player = getContainedBy(self);
        addSkillModModifier(player, slotName + "_powerup", mod, (int)finalPower, -1, false, false);
        reverse_engineering.applyBuffIcon(player, self);
        reverse_engineering.recalcPoolsIfNeeded(player, mod);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        String slotName = reverse_engineering.getMyEquippedSlot(self);
        if (slotName.equals("none"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (hasSkillModModifier(transferer, slotName + "_powerup"))
            {
                String mod = getStringObjVar(self, reverse_engineering.ENGINEERING_MODIFIER);
                obj_id player = getFirstParentInWorld(self);
                reverse_engineering.removeBuffIcon(player, self);
                reverse_engineering.removePlayerPowerUpMods(player, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isPlayer(destContainer))
        {
            String slotName = reverse_engineering.getMyEquippedSlot(self);
            if (slotName.equals("none"))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                int power = getIntObjVar(self, reverse_engineering.ENGINEERING_POWER);
                String mod = getStringObjVar(self, reverse_engineering.ENGINEERING_MODIFIER);
                int ratio = getIntObjVar(self, reverse_engineering.ENGINEERING_RATIO);
                int finalPower = power / ratio;
                if (finalPower < 1)
                {
                    finalPower = 1;
                }
                if (!hasSkillModModifier(transferer, slotName + "_powerup"))
                {
                    obj_id player = getFirstParentInWorld(self);
                    reverse_engineering.applyBuffIcon(player, self);
                    addSkillModModifier(player, slotName + "_powerup", mod, (int)finalPower, -1, false, false);
                    reverse_engineering.recalcPoolsIfNeeded(player, mod);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String slotName = reverse_engineering.getMyEquippedSlot(self);
        if (!slotName.equals("none"))
        {
            obj_id player = getFirstParentInWorld(self);
            if (hasSkillModModifier(player, slotName + "_powerup"))
            {
                reverse_engineering.removePlayerPowerUpMods(player, self);
            }
            reverse_engineering.removeModsAndScript(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id strSpam = new string_id("spam", "powerup_remove");
        mi.addRootMenu(menu_info_types.SERVER_MENU8, strSpam);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU8)
        {
            trial.bumpSession(self, "cleanUp");
            messageTo(self, "cleanUp", trial.getSessionDict(self, "cleanUp"), 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (names == null || attribs == null || names.length != attribs.length)
        {
            return SCRIPT_CONTINUE;
        }
        int i = getFirstFreeIndex(names);
        if (i != -1 && i < names.length)
        {
            if (hasObjVar(self, reverse_engineering.ENGINEERING_POWER) && hasObjVar(self, reverse_engineering.ENGINEERING_MODIFIER) && hasObjVar(self, reverse_engineering.ENGINEERING_RATIO))
            {
                int power = getIntObjVar(self, reverse_engineering.ENGINEERING_POWER);
                String mod = getStringObjVar(self, reverse_engineering.ENGINEERING_MODIFIER);
                int ratio = getIntObjVar(self, reverse_engineering.ENGINEERING_RATIO);
                names[i] = "@spam:pup_modifier";
                attribs[i] = "@stat_n:" + mod;
                i++;
                names[i] = "@spam:pup_power";
                attribs[i] = "" + (power / ratio);
                i++;
            }
            float expiration = reverse_engineering.getDieTime(reverse_engineering.EXPIRATION_TIME, self);
            float timeInMinutes = expiration / 60;
            names[i] = "@spam:pup_expire_time";
            attribs[i] = "" + (int)timeInMinutes;
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (self.isBeingDestroyed())
        {
            return SCRIPT_CONTINUE;
        }
        if (!trial.verifySession(self, params, "cleanUp"))
        {
            return SCRIPT_CONTINUE;
        }
        float dieTime = reverse_engineering.getDieTime(reverse_engineering.EXPIRATION_TIME, self);
        String slotName = reverse_engineering.getMyEquippedSlot(self);
        obj_id player = utils.getContainingPlayer(self);
        if (slotName.equals("none"))
        {
            reverse_engineering.removeModsAndScript(player, self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (hasSkillModModifier(player, slotName + "_powerup"))
            {
                reverse_engineering.removePlayerPowerUpMods(player, self);
            }
            reverse_engineering.removeModsAndScript(player, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleOverrideExistingPowerUp(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            sui.removePid(player, reverse_engineering.POWERUP_PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, reverse_engineering.POWERUP_PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (btn == sui.BP_OK)
        {
            obj_id powerUp = utils.getObjIdScriptVar(self, reverse_engineering.POWERUP_PID_NAME);
            reverse_engineering.removePlayerPowerUpMods(player, self);
            reverse_engineering.addModsAndScript(player, powerUp, self);
            sui.removePid(player, reverse_engineering.POWERUP_PID_NAME);
            utils.removeScriptVar(self, reverse_engineering.POWERUP_PID_NAME);
        }
        return SCRIPT_CONTINUE;
    }
}
