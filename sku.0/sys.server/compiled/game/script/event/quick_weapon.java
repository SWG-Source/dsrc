package script.event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import java.util.StringTokenizer;

public class quick_weapon extends script.base_script
{
    public quick_weapon()
    {
    }
    public static final String WEAPON_LIST = "datatables/event/weapons.iff";
    public static final String[] HELP_TEXT = 
    {
        "=========================================",
        "USAGE: weapon [Weapon Index Number] [MIN Damage] [MAX Damage] [SPEED]",
        "Say \"categories\" to get a rough range of where different weapons lie in the index.",
        "Say \"detach\" to detach this script immediately.",
        "========================================="
    };
    public static final String[] CATEGORIES = 
    {
        "2H Sword 0-15",
        "Polearm 28-44",
        "Sword 54-59",
        "Carbine 60-72",
        "Grenade 73-100",
        "Heavy Weapons 101-107 (Flame Thrower 132)",
        "Pistol 108-130",
        "Rifle 133-151"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isPlayer(self))
        {
            detachScript(self, "event.quick_weapon");
        }
        if (!isGod(self))
        {
            detachScript(self, "event.quick_weapon");
            sendSystemMessage(self, "You must be in God Mode for this script to take hold.", null);
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 15)
            {
                detachScript(self, "event.quick_weapon");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(self, "Say \"Help\" for usage and options.", null);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (!isGod(self))
        {
            detachScript(self, "event.quick_weapon");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 15)
            {
                detachScript(self, "event.quick_weapon");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isGod(self))
        {
            int godLevel = getGodLevel(self);
            if (godLevel < 15)
            {
                detachScript(self, "event.quick_weapon");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
                return SCRIPT_CONTINUE;
            }
        }
        if (!isGod(self))
        {
            detachScript(self, "event.quick_weapon");
            sendSystemMessage(self, "You must be in God Mode to use this script.", null);
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).startsWith("weapon"))
        {
            int minDamage = 1;
            int maxDamage = 10;
            float weaponSpeed = 7.0f;
            int weaponNum = 0;
            String weaponType[] = dataTableGetStringColumn(WEAPON_LIST, 0);
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 5)
            {
                String command = st.nextToken();
                String weaponNumStr = st.nextToken();
                String minDamageStr = st.nextToken();
                String maxDamageStr = st.nextToken();
                String weaponSpeedStr = st.nextToken();
                weaponNum = utils.stringToInt(weaponNumStr);
                if (weaponNum > weaponType.length)
                {
                    sendSystemMessage(self, "Invalid weapon index number.", null);
                    return SCRIPT_CONTINUE;
                }
                minDamage = utils.stringToInt(minDamageStr);
                maxDamage = utils.stringToInt(maxDamageStr);
                if (maxDamage < minDamage)
                {
                    maxDamage = minDamage + 10;
                }
                weaponSpeed = utils.stringToFloat(weaponSpeedStr);
                if (weaponSpeed < 2.0f)
                {
                    weaponSpeed = 2.0f;
                }
                obj_id objInventory = utils.getInventoryContainer(objSpeaker);
                obj_id objWeapon = createObjectOverloaded(weaponType[weaponNum], objInventory);
                setCrafter(objWeapon, objSpeaker);
                setWeaponAttackSpeed(objWeapon, weaponSpeed);
                setWeaponMinDamage(objWeapon, minDamage);
                setWeaponMaxDamage(objWeapon, maxDamage);
                setName(objWeapon, "***INTERNAL USE ONLY: " + weaponType[weaponNum]);
                attachScript(objWeapon, "event.no_rent");
                float creationTime = getGameTime();
                setObjVar(objWeapon, "event.creationTime", creationTime);
                return SCRIPT_CONTINUE;
            }
        }
        if ((toLower(strText)).equals("detach"))
        {
            detachScript(self, "event.quick_weapon");
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).equals("help"))
        {
            for (int i = 0; i < HELP_TEXT.length; i++)
            {
                sendSystemMessage(self, HELP_TEXT[i], null);
            }
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).equals("categories"))
        {
            for (int i = 0; i < CATEGORIES.length; i++)
            {
                sendSystemMessage(self, CATEGORIES[i], null);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
