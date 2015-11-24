package script.library;

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

public class reverse_engineering extends script.base_script
{
    public reverse_engineering()
    {
    }
    public static final String[] POWERUP_TYPES = 
    {
        "armor",
        "clothing",
        "weapon"
    };
    public static final String[] POWERUP_SLOTS = 
    {
        "chest2",
        "chest1",
        "hold_r"
    };
    public static final String[] POWERUP_ITEMS = 
    {
        "item_reverse_engineering_powerup_armor_02_01",
        "item_reverse_engineering_powerup_clothing_02_01",
        "item_reverse_engineering_powerup_weapon_02_01"
    };
    public static final String POWERUP_WEAPON = "powerup_weapon";
    public static final String POWERUP_CHEST = "powerup_chest_armor";
    public static final String POWERUP_SHIRT = "powerup_shirt";
    public static final String POWERUP_SCRIPT = "item.tool.reverse_engineering_poweredup_item";
    public static final String ENGINEERING_MODIFIER = "reverse_engineering.reverse_engineering_modifier";
    public static final String ENGINEERING_RATIO = "reverse_engineering.reverse_engineering_ratio";
    public static final String ENGINEERING_POWER = "reverse_engineering.reverse_engineering_power";
    public static final String ENGINEERING_TIMESTAMP = "reverse_engineering.reverse_engineering_timestamp";
    public static final String POWERUP_PID_NAME = "powerUp";
    public static final String SPECIAL_MOD_TABLE = "datatables/crafting/reverse_engineering_special_mods.iff";
    public static final String MODS_TABLE = "datatables/crafting/reverse_engineering_mods.iff";
    public static final String STATIC_ITEM_TABLE = "datatables/item/master_item/master_item.iff";
    public static final String JUNK_TABLE = "datatables/crafting/reverse_engineering_junk.iff";
    public static final string_id POWERUP_PID_TITLE = new string_id("spam", "powerup_override_title");
    public static final string_id POWERUP_PID_PROMPT = new string_id("spam", "powerup_override");
    public static final float EXPIRATION_TIME = 1800.0f;
    public static void checkPowerUpReApply(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            String[] possiblePowerUps = POWERUP_SLOTS;
            for (int i = 0; i < possiblePowerUps.length; i++)
            {
                obj_id thingInPlayersSlot = getObjectInSlot(player, possiblePowerUps[i]);
                if (isPoweredUpItem(thingInPlayersSlot))
                {
                    applyPowerupItemEquipped(player, thingInPlayersSlot);
                }
            }
        }
    }
    public static void applyPowerupItem(obj_id player, obj_id powerup, int powerupType) throws InterruptedException
    {
        if (powerupType < 0)
        {
            sendSystemMessage(player, new string_id("spam", "powerup_must_equip_item"));
            return;
        }
        obj_id item = getObjectInSlot(player, POWERUP_SLOTS[powerupType]);
        if (isIdValid(item) && exists(item) && !isIdNull(item))
        {
            if (isPoweredUpItem(item))
            {
                String title = utils.packStringId(POWERUP_PID_TITLE);
                String prompt = utils.packStringId(POWERUP_PID_PROMPT);
                utils.setScriptVar(item, POWERUP_PID_NAME, powerup);
                int pid = sui.msgbox(item, player, prompt, sui.OK_CANCEL, title, "handleOverrideExistingPowerUp");
                sui.setPid(player, pid, POWERUP_PID_NAME);
                return;
            }
            addModsAndScript(player, powerup, item);
        }
        else 
        {
            sendSystemMessage(player, new string_id("spam", "powerup_must_equip_item"));
        }
        return;
    }
    public static void applyPowerupItemEquipped(obj_id player, obj_id itemWithPowerUp) throws InterruptedException
    {
        float dieTime = getDieTime(EXPIRATION_TIME, itemWithPowerUp);
        if (dieTime < 1)
        {
            dieTime = 1.0f;
        }
        trial.bumpSession(itemWithPowerUp, "cleanUp");
        messageTo(itemWithPowerUp, "cleanUp", trial.getSessionDict(itemWithPowerUp, "cleanUp"), dieTime, false);
        String slotName = getMyEquippedSlot(itemWithPowerUp);
        if (slotName.equals("none"))
        {
            return;
        }
        int power = getIntObjVar(itemWithPowerUp, ENGINEERING_POWER);
        String mod = getStringObjVar(itemWithPowerUp, ENGINEERING_MODIFIER);
        int ratio = getIntObjVar(itemWithPowerUp, ENGINEERING_RATIO);
        int finalPower = power / ratio;
        if (finalPower < 1)
        {
            finalPower = 1;
        }
        addSkillModModifier(player, slotName + "_powerup", mod, (int)finalPower, -1, false, false);
        applyBuffIcon(player, itemWithPowerUp);
        recalcPoolsIfNeeded(player, mod);
        return;
    }
    public static boolean isPoweredUpItem(obj_id item) throws InterruptedException
    {
        return hasScript(item, POWERUP_SCRIPT);
    }
    public static int getPowerupType(obj_id item) throws InterruptedException
    {
        int powerupType = -1;
        for (int i = 0; i < POWERUP_ITEMS.length; i++)
        {
            String staticName = getStaticItemName(item);
            if (staticName.equals(POWERUP_ITEMS[i]))
            {
                powerupType = i;
            }
        }
        return powerupType;
    }
    public static float getDieTime(float lifeSpan, obj_id tempObject) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(tempObject, ENGINEERING_TIMESTAMP);
        float deathStamp = timeStamp + reverse_engineering.EXPIRATION_TIME;
        float rightNow = getGameTime();
        float dieTime = deathStamp - rightNow;
        return dieTime;
    }
    public static String getMyEquippedSlot(obj_id itemWithPowerUp) throws InterruptedException
    {
        String slotName = "none";
        obj_id containingMe = utils.getContainingPlayer(itemWithPowerUp);
        if (isIdValid(containingMe))
        {
            String[] possibleSlots = POWERUP_SLOTS;
            for (int i = 0; i < possibleSlots.length; i++)
            {
                obj_id thingInPlayersSlot = getObjectInSlot(containingMe, possibleSlots[i]);
                if (thingInPlayersSlot == itemWithPowerUp)
                {
                    return possibleSlots[i];
                }
            }
        }
        return slotName;
    }
    public static String getMyBuffIconInt(obj_id powerUp) throws InterruptedException
    {
        int powerupType = getPowerupType(powerUp);
        String buffName = "";
        switch (powerupType)
        {
            case 0:
            buffName = POWERUP_CHEST;
            break;
            case 1:
            buffName = POWERUP_SHIRT;
            break;
            case 2:
            buffName = POWERUP_WEAPON;
            break;
            default:
            return null;
        }
        return buffName;
    }
    public static String getMyBuffIconString(obj_id itemWithPowerUp) throws InterruptedException
    {
        String buffName = "";
        String slotName = getMyEquippedSlot(itemWithPowerUp);
        if (slotName.equals("chest2"))
        {
            buffName = POWERUP_CHEST;
        }
        if (slotName.equals("chest1"))
        {
            buffName = POWERUP_SHIRT;
        }
        if (slotName.equals("hold_r"))
        {
            buffName = POWERUP_WEAPON;
        }
        return buffName;
    }
    public static void applyBuffIcon(obj_id player, obj_id itemWithPowerUp) throws InterruptedException
    {
        applyBuffIcon(player, obj_id.NULL_ID, itemWithPowerUp);
    }
    public static void applyBuffIcon(obj_id player, obj_id powerUp, obj_id itemWithPowerUp) throws InterruptedException
    {
        String buffName = "";
        float expiration = 0f;
        if (!isIdValid(powerUp))
        {
            buffName = getMyBuffIconString(itemWithPowerUp);
        }
        else 
        {
            buffName = getMyBuffIconInt(powerUp);
        }
        expiration = getDieTime(reverse_engineering.EXPIRATION_TIME, itemWithPowerUp);
        if (expiration < 0)
        {
            return;
        }
        if (buffName != null && !buffName.equals(""))
        {
            buff.applyBuff(player, buffName, expiration);
        }
        return;
    }
    public static void removeBuffIcon(obj_id player, obj_id itemWithPowerUp) throws InterruptedException
    {
        String buffName = getMyBuffIconString(itemWithPowerUp);
        if (buffName != null && !buffName.equals(""))
        {
            buff.removeBuff(player, buffName);
        }
        return;
    }
    public static void recalcPoolsIfNeeded(obj_id player, String mod) throws InterruptedException
    {
        if ((mod.startsWith("constitution")) || (mod.startsWith("stamina")))
        {
            messageTo(player, "recalcPools", null, .25f, false);
        }
        combat.cacheCombatData(player);
        trial.bumpSession(player, "displayDefensiveMods");
        messageTo(player, "setDisplayOnlyDefensiveMods", trial.getSessionDict(player, "displayDefensiveMods"), 5, false);
        return;
    }
    public static void addModsAndScript(obj_id player, obj_id powerUp, obj_id itemToPowerUp) throws InterruptedException
    {
        addModsAndScript(player, powerUp, itemToPowerUp, 0f);
    }
    public static void addModsAndScript(obj_id player, obj_id powerUp, obj_id itemToPowerUp, float remainingTime) throws InterruptedException
    {
        String mod = getStringObjVar(powerUp, ENGINEERING_MODIFIER);
        int ratio = getIntObjVar(powerUp, ENGINEERING_RATIO);
        int power = getIntObjVar(powerUp, ENGINEERING_POWER);
        float timeStamp = getGameTime();
        if (remainingTime > 0f)
        {
            timeStamp = remainingTime;
        }
        setObjVar(itemToPowerUp, ENGINEERING_MODIFIER, mod);
        setObjVar(itemToPowerUp, ENGINEERING_RATIO, ratio);
        setObjVar(itemToPowerUp, ENGINEERING_POWER, power);
        setObjVar(itemToPowerUp, ENGINEERING_TIMESTAMP, timeStamp);
        if (!isPoweredUpItem(itemToPowerUp))
        {
            attachScript(itemToPowerUp, POWERUP_SCRIPT);
        }
        powerUpAttached(player, itemToPowerUp);
        applyBuffIcon(player, powerUp, itemToPowerUp);
        static_item.decrementStaticItem(powerUp);
    }
    public static void removeModsAndScript(obj_id player, obj_id itemWithPowerUp) throws InterruptedException
    {
        removeObjVar(itemWithPowerUp, ENGINEERING_POWER);
        removeObjVar(itemWithPowerUp, ENGINEERING_RATIO);
        removeObjVar(itemWithPowerUp, ENGINEERING_TIMESTAMP);
        removeObjVar(itemWithPowerUp, ENGINEERING_MODIFIER);
        detachScript(itemWithPowerUp, POWERUP_SCRIPT);
        if (utils.isEquipped(itemWithPowerUp))
        {
            removeBuffIcon(player, itemWithPowerUp);
        }
        trial.bumpSession(itemWithPowerUp, "displayDefensiveMods");
        messageTo(itemWithPowerUp, "setDisplayOnlyDefensiveMods", trial.getSessionDict(itemWithPowerUp, "displayDefensiveMods"), 5, false);
        return;
    }
    public static void removePlayerPowerUpMods(obj_id player, obj_id itemWithPowerUp) throws InterruptedException
    {
        String slotName = getMyEquippedSlot(itemWithPowerUp);
        String mod = getStringObjVar(itemWithPowerUp, ENGINEERING_MODIFIER);
        removeAttribOrSkillModModifier(player, slotName + "_powerup");
        recalcPoolsIfNeeded(player, mod);
        return;
    }
    public static void powerUpAttached(obj_id player, obj_id itemWithPowerUp) throws InterruptedException
    {
        float dieTime = getDieTime(reverse_engineering.EXPIRATION_TIME, itemWithPowerUp);
        if (dieTime < 1)
        {
            dieTime = 1.0f;
        }
        trial.bumpSession(itemWithPowerUp, "cleanUp");
        messageTo(itemWithPowerUp, "cleanUp", trial.getSessionDict(itemWithPowerUp, "cleanUp"), dieTime, false);
        String slotName = reverse_engineering.getMyEquippedSlot(itemWithPowerUp);
        if (slotName.equals("none"))
        {
            return;
        }
        int power = getIntObjVar(itemWithPowerUp, ENGINEERING_POWER);
        String mod = getStringObjVar(itemWithPowerUp, ENGINEERING_MODIFIER);
        int ratio = getIntObjVar(itemWithPowerUp, ENGINEERING_RATIO);
        int finalPower = power / ratio;
        if (finalPower < 1)
        {
            finalPower = 1;
        }
        addSkillModModifier(player, slotName + "_powerup", mod, (int)finalPower, -1, false, false);
        recalcPoolsIfNeeded(player, mod);
        return;
    }
    public static boolean canMakePowerUp(String mod) throws InterruptedException
    {
        if (dataTableGetInt(SPECIAL_MOD_TABLE, mod, "no_pup") > 0)
        {
            return false;
        }
        return true;
    }
    public static boolean canStaticItemBeReversedEngineered(obj_id item) throws InterruptedException
    {
        if (!exists(item) || !isIdValid(item))
        {
            return false;
        }
        if (!static_item.isStaticItem(item))
        {
            return false;
        }
        String staticItemName = getStaticItemName(item);
        if (staticItemName == null || staticItemName.equals(""))
        {
            return false;
        }
        int row = dataTableSearchColumnForString(staticItemName, "name", static_item.MASTER_ITEM_TABLE);
        if (row < 0)
        {
            return false;
        }
        int canRe = dataTableGetInt(static_item.MASTER_ITEM_TABLE, row, static_item.COLUMN_CAN_RE);
        return canRe == 1;
    }
}
