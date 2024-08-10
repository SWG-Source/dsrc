package script.library;

import script.*;

import java.io.*;
import java.util.*;

public class utils extends script.base_script
{
    public utils()
    {
    }
    public static final String VERSION = "v0.01.00";
    public static final String VAR_OWNER = "owner";
    public static final String VAR_COOWNERS = "coowners";
    public static final String SLOT_INVENTORY = "inventory";
    public static final String SLOT_DATAPAD = "datapad";
    public static final String SLOT_HANGAR = "hangar";
    public static final String SLOT_BANK = "bank";
    public static final String SLOT_MISSION_BAG = "mission_bag";
    public static final String FREE_TRIAL = "free_trial";
    public static final String TIP = FREE_TRIAL + ".tip";
    public static final String TIP_OUT_NUM = TIP + ".tip_out_num";
    public static final String TIP_OUT_AMMOUNT = TIP + ".tip_out_ammount";
    public static final String TIP_IN_NUM = TIP + ".tip_in_num";
    public static final String TIP_IN_AMMOUNT = TIP + ".tip_in_ammount";
    public static final String TIP_IN_THACK = TIP + ".tip_in_timeHack";
    public static final String TIP_OUT_THACK = TIP + ".tip_out_timeHack";
    //public static final String TRIAL_STRUCTURE = FREE_TRIAL + ".trial_structure";
    public static final int TIP_NUM_MAX = 25;
    public static final int TIP_AMT_MAX = 100000;
    public static final String NO_TRADE_SCRIPT = "item.special.nomove";
    public static final String CTS_OBJVAR_HISTORY = "ctsRetroHistory";
    public static final int BIT_LIST_SIZE = 32;
    public static final string_id SID_OBJECT_NOT_ACTIVE = new string_id("error_message", "object_not_active");
    public static final String[] WAYPOINT_COLORS = 
    {
        waypoint_colors.blue,
        waypoint_colors.green,
        waypoint_colors.orange,
        waypoint_colors.yellow,
        waypoint_colors.purple,
        waypoint_colors.white
    };
    //public static final string_id SID_SECONDS = new string_id("spam", "seconds");
    //public static final string_id SID_MINUTES = new string_id("spam", "minutes");
    //public static final string_id SID_HOURS = new string_id("spam", "hours");
    //public static final string_id SID_DAYS = new string_id("spam", "days");
    public static final string_id SID_OUT_OF_RANGE = new string_id("spam", "out_of_range");
    public static final int COMMANDO = 1;
    public static final int SMUGGLER = 2;
    public static final int MEDIC = 3;
    public static final int OFFICER = 4;
    public static final int SPY = 5;
    public static final int BOUNTY_HUNTER = 6;
    public static final int FORCE_SENSITIVE = 7;
    public static final int TRADER = 8;
    public static final int ENTERTAINER = 9;
    public static final String LIFEDAY_OWNER = "lifeday.owner";
    //public static final String XMAS_RECEIVED_V1 = "gift.xmas05";
    //public static final String XMAS_RECEIVED_V2 = "gift.xmas05v2";
    public static final String XMAS_RECEIVED_VI1 = "gift.xmas06";
    //public static final String XMAS_RECEIVED_VI2 = "gift.xmas06v2";
    //public static final String XMAS_RECEIVED_VII1 = "gift.xmas07";
    //public static final String XMAS_RECEIVED_VII2 = "gift.xmas07v2";
    public static final String XMAS_NOT_RECEIVED_TUTORIAL = "gift.xmas06_inTutorialFail";
    //public static final String XMAS_RECEIVED_VIII1 = "gift.xmas08";
    //public static final String XMAS_RECEIVED_VIII2 = "gift.xmas08v2";
    public static final String XMAS_RECEIVED_IX_01 = "gift.xmas09";
    //public static final String EMPIRE_DAY_RECEIVED_VI = "gift.empire08";
    public static final string_id GIFT_GRANTED = new string_id("system_msg", "gift_granted");
    public static final string_id GIFT_GRANTED_SUB = new string_id("system_msg", "gift_granted_sub");
    //public static final int HOUSE_CURRENT = 0;
    //public static final int HOUSE_HISTORY = 1;
    public static final int HOUSE_MAX = 2;
    public static final obj_id OBJ_ID_BIO_LINK_PENDING = obj_id.getObjId(1);
    public static final String VENDOR_SCRIPT = "terminal.vendor";
    public static final String BAZAAR_SCRIPT = "terminal.bazaar";
    public static int clipRange(int iValue, int iClipMin, int iClipMax) throws InterruptedException
    {
        return (iValue < iClipMin) ? iClipMin : Math.min(iValue, iClipMax);
    }
    public static location getRandomAwayLocation(location pos, float fMinRadius, float fMaxRadius) throws InterruptedException
    {
        float fTheta = rand() * (2.0f * (float)Math.PI);
        float fRadius = Math.min(fMinRadius, fMaxRadius) + rand() * Math.abs(fMaxRadius - fMinRadius);
        pos.x += fRadius * StrictMath.cos(fTheta);
        pos.z += fRadius * StrictMath.sin(fTheta);
        return pos;
    }
    public static float getDistance2D(location locTarget1, location locTarget2) throws InterruptedException
    {
        if (locTarget1 == null || locTarget2 == null)
        {
            return -1.00f;
        }
        location loc1 = (location)locTarget1.clone();
        loc1.y = 0.0f;
        location loc2 = (location)locTarget2.clone();
        loc2.y = 0.0f;
        return getDistance(loc1, loc2);
    }
    public static float getDistance2D(obj_id hereTarget, obj_id thereTarget) throws InterruptedException
    {
        if (!isIdValid(hereTarget) || !isIdValid(thereTarget))
        {
            return -1.00f;
        }
        return getDistance2D(getWorldLocation(hereTarget), getWorldLocation(thereTarget));
    }
    public static attrib_mod[] addMindAttribToStim(int power) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[2];
        for (int i = 0; i < 2; i++)
        {
            am[i] = createHealDamageAttribMod(i * 2, power);
        }
        return am;
    }
    public static int randix(float[] fArray) throws InterruptedException
    {
        if (fArray.length > 1)
        {
            float fSum = 0.0f;
            for (float aFArray : fArray) {
                fSum += aFArray;
            }
            float fRandom = rand() * fSum;
            fSum = 0.0f;
            for (int i = 0; i < fArray.length; i++)
            {
                fSum += fArray[i];
                if (fRandom <= fSum)
                {
                    return i;
                }
            }
        }
        return 0;
    }
    public static boolean isMando(obj_id armor) throws InterruptedException
    {
        if (isIdValid(armor))
        {
            String template = getTemplateName(armor);
            return template.endsWith("armor_mandalorian_belt.iff") || template.endsWith("armor_mandalorian_bicep_l.iff") || template.endsWith("armor_mandalorian_bicep_r.iff") || template.endsWith("armor_mandalorian_bracer_l.iff") || template.endsWith("armor_mandalorian_bracer_r.iff") || template.endsWith("armor_mandalorian_chest_plate.iff") || template.endsWith("armor_mandalorian_helmet.iff") || template.endsWith("armor_mandalorian_leggings.iff") || template.endsWith("armor_mandalorian_shoes.iff") || template.endsWith("armor_mandalorian_gloves.iff");
        }
        return false;
    }
    public static boolean hasSpecialSkills(obj_id player) throws InterruptedException
    {
        boolean skillCheck = false;
        if (hasSkill(player, "class_commando_phase4_master"))
        {
            skillCheck = true;
        }
        if (hasSkill(player, "class_bountyhunter_phase4_master"))
        {
            skillCheck = true;
        }
        if (hasSkill(player, "class_officer_phase4_master"))
        {
            skillCheck = true;			
        }
        return skillCheck;
    }
    public static int unequipAndNotifyUncerted(obj_id player) throws InterruptedException
    {
        int totalUnequipped = 0;
        String[] armorSlots = new String[]
        {
            "hat",
            "chest2",
            "bicep_r",
            "bicep_l",
            "bracer_upper_r",
            "bracer_lower_r",
            "bracer_upper_l",
            "bracer_lower_l",
            "gloves",
            "pants2",
            "shoes",
            "chest1",
            "pants1",
            "utility_belt"
        };
        obj_id curArmor;
        obj_id inv = utils.getInventoryContainer(player);
        String classTemplate = getSkillTemplate(player);
        prose_package pp;

        for (String armorSlot : armorSlots) {
            curArmor = getObjectInSlot(player, armorSlot);
            if ((isIdValid(curArmor) && !armor.isArmorCertified(player, curArmor)) || (isMando(curArmor) && !hasSpecialSkills(player))) {
                totalUnequipped++;
                pp = new prose_package();
                pp = prose.setStringId(pp, new string_id("spam", "armor_lost_cert"));
                pp = prose.setTT(pp, curArmor);
                sendSystemMessageProse(player, pp);
                putInOverloaded(curArmor, inv);
            }
            if (!isIdNull(curArmor) && hasObjVar(curArmor, "armor.fake_armor") && hasObjVar(curArmor, "dynamic_item.required_skill")) {
                if (classTemplate != null && !classTemplate.equals("")) {
                    if (!classTemplate.startsWith(getStringObjVar(curArmor, "dynamic_item.required_skill"))) {
                        totalUnequipped++;
                        pp = new prose_package();
                        pp = prose.setStringId(pp, new string_id("spam", "armor_lost_cert"));
                        pp = prose.setTT(pp, curArmor);
                        sendSystemMessageProse(player, pp);
                        putInOverloaded(curArmor, inv);
                    }
                }
            }
        }
        obj_id[] equipSlots = metrics.getWornItems(player);
        if (equipSlots != null && equipSlots.length > 0)
        {
            for (obj_id equipSlot : equipSlots) {
                if (!static_item.validateLevelRequired(player, (equipSlot))) {
                    totalUnequipped++;
                    pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "item_lost_cert"));
                    pp = prose.setTT(pp, equipSlot);
                    sendSystemMessageProse(player, pp);
                    putInOverloaded(equipSlot, inv);
                } else if (!static_item.validateLevelRequiredForWornEffect(player, (equipSlot))) {
                    static_item.removeWornBuffs(equipSlot, player);
                    pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "item_lost_cert_effect"));
                    pp = prose.setTT(pp, equipSlot);
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        obj_id weapon = getCurrentWeapon(player);
        if (isIdValid(weapon) && !combat.hasCertification(player, weapon))
        {
            totalUnequipped++;
            pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", "weapon_lost_cert"));
            pp = prose.setTT(pp, weapon);
            sendSystemMessageProse(player, pp);
            putInOverloaded(weapon, inv);
        }
        obj_id hold_l = getObjectInSlot(player, "hold_l");
        obj_id hold_r = getObjectInSlot(player, "hold_r");
        if (isIdValid(hold_l) && !performance.isDancePropCertified(player, hold_l))
        {
            totalUnequipped++;
            pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", "prop_lost_cert"));
            pp = prose.setTT(pp, hold_l);
            sendSystemMessageProse(player, pp);
            putInOverloaded(hold_l, inv);
            hold_l = obj_id.NULL_ID;
        }
        if (isIdValid(hold_r) && !performance.isDancePropCertified(player, hold_r))
        {
            totalUnequipped++;
            pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", "prop_lost_cert"));
            pp = prose.setTT(pp, hold_r);
            sendSystemMessageProse(player, pp);
            putInOverloaded(hold_r, inv);
            hold_r = obj_id.NULL_ID;
        }
        if (performance.isValidDanceProp(hold_l) && performance.isValidDanceProp(hold_r) && !hasCommand(player, "prop_dual_wield"))
        {
            totalUnequipped++;
            pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", "dual_prop_lost_cert"));
            pp = prose.setTT(pp, hold_l);
            sendSystemMessageProse(player, pp);
            putInOverloaded(hold_l, inv);
            hold_l = obj_id.NULL_ID;
        }
        obj_id[] appearanceItems = getAllItemsFromAppearanceInventory(player);
        if (appearanceItems != null && appearanceItems.length > 0)
        {
            for (obj_id appearanceItem : appearanceItems) {
                curArmor = appearanceItem;
                if ((isIdValid(curArmor) && !armor.isArmorCertified(player, curArmor)) || (isMando(curArmor) && !hasSpecialSkills(player))) {
                    totalUnequipped++;
                    pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "armor_lost_cert"));
                    pp = prose.setTT(pp, curArmor);
                    sendSystemMessageProse(player, pp);
                    putInOverloaded(curArmor, inv);
                }
                if (!isIdNull(curArmor) && hasObjVar(curArmor, "armor.fake_armor") && hasObjVar(curArmor, "dynamic_item.required_skill")) {
                    if (classTemplate != null && !classTemplate.equals("")) {
                        if (!classTemplate.startsWith(getStringObjVar(curArmor, "dynamic_item.required_skill"))) {
                            totalUnequipped++;
                            pp = new prose_package();
                            pp = prose.setStringId(pp, new string_id("spam", "armor_lost_cert"));
                            pp = prose.setTT(pp, curArmor);
                            sendSystemMessageProse(player, pp);
                            putInOverloaded(curArmor, inv);
                        }
                    }
                }
                if (!static_item.validateLevelRequired(player, curArmor)) {
                    totalUnequipped++;
                    pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "item_lost_cert"));
                    pp = prose.setTT(pp, curArmor);
                    sendSystemMessageProse(player, pp);
                    putInOverloaded(curArmor, inv);
                } else if (!static_item.validateLevelRequiredForWornEffect(player, curArmor)) {
                    static_item.removeWornBuffs(curArmor, player);
                    pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("spam", "item_lost_cert_effect"));
                    pp = prose.setTT(pp, curArmor);
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        return totalUnequipped;
    }
    public static int getIntObjVar(obj_id object, String name, int intDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return intDefault;
        }
        return getIntObjVar(object, name);
    }
    public static int[] getIntArrayObjVar(obj_id object, String name, int[] intArrayDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return intArrayDefault;
        }
        return getIntArrayObjVar(object, name);
    }
    public static float getFloatObjVar(obj_id object, String name, float fltDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return fltDefault;
        }
        return getFloatObjVar(object, name);
    }
    public static float[] getFloatArrayObjVar(obj_id object, String name, float[] fltArrayDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return fltArrayDefault;
        }
        return getFloatArrayObjVar(object, name);
    }
    public static String getStringObjVar(obj_id object, String name, String strDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return strDefault;
        }
        return getStringObjVar(object, name);
    }
    public static String[] getStringArrayObjVar(obj_id object, String name, String[] strArrayDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return strArrayDefault;
        }
        return getStringArrayObjVar(object, name);
    }
    public static obj_id getObjIdObjVar(obj_id object, String name, obj_id idDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return idDefault;
        }
        return getObjIdObjVar(object, name);
    }
    public static obj_id[] getObjIdArrayObjVar(obj_id object, String name, obj_id[] idArrayDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return idArrayDefault;
        }
        return getObjIdArrayObjVar(object, name);
    }
    public static location getLocationObjVar(obj_id object, String name, location locDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return locDefault;
        }
        return getLocationObjVar(object, name);
    }
    public static location[] getLocationArrayObjVar(obj_id object, String name, location[] locArrayDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return locArrayDefault;
        }
        return getLocationArrayObjVar(object, name);
    }
    public static boolean getBooleanObjVar(obj_id object, String name, boolean bDefault) throws InterruptedException
    {
        if (!hasObjVar(object, name))
        {
            return bDefault;
        }
        return getBooleanObjVar(object, name);
    }
    public static boolean isObjIdInArray(obj_id[] objIdArray, obj_id objTarget) throws InterruptedException
    {
        if (objIdArray == null || objIdArray.length == 0)
        {
            return false;
        }
        for (obj_id anObjIdArray : objIdArray) {
            if (anObjIdArray == objTarget) {
                return true;
            }
        }
        return false;
    }
    public static boolean isElementInArray(Vector objIdArray, Object objTarget) throws InterruptedException {
        return !(objIdArray == null || objIdArray.isEmpty()) && objIdArray.contains(objTarget);
    }
    public static int getElementPositionInArray(Vector array, Object element) throws InterruptedException
    {
        if (array == null)
        {
            return -1;
        }
        return array.indexOf(element);
    }
    public static int getElementPositionInArray(obj_id[] array, obj_id element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == element)
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(int[] array, int element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == element)
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(float[] array, float element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == element)
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(boolean[] array, boolean element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == element)
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(String[] array, String element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i].equals(element))
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(region[] array, region element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == element)
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(location[] array, location element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i].equals(element))
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(string_id[] array, string_id element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i].equals(element))
            {
                return i;
            }
        }
        return -1;
    }
    public static int getElementPositionInArray(attrib_mod[] array, attrib_mod element) throws InterruptedException
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == element)
            {
                return i;
            }
        }
        return -1;
    }
    public static obj_id[] copyObjIdArray(obj_id[] objSourceArray, obj_id[] objDestinationArray) throws InterruptedException
    {
        if (objSourceArray == null || objDestinationArray == null || objSourceArray.length == 0 || objDestinationArray.length == 0 || objSourceArray.length > objDestinationArray.length)
        {
            return null;
        }
        System.arraycopy(objSourceArray, 0, objDestinationArray, 0, objSourceArray.length);
        return objDestinationArray;
    }
    public static obj_id[] copyArray(obj_id[] oldArray, obj_id[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static int[] copyArray(int[] oldArray, int[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static float[] copyArray(float[] oldArray, float[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static boolean[] copyArray(boolean[] oldArray, boolean[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static String[] copyArray(String[] oldArray, String[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static region[] copyArray(region[] oldArray, region[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static location[] copyArray(location[] oldArray, location[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static string_id[] copyArray(string_id[] oldArray, string_id[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static attrib_mod[] copyArray(attrib_mod[] oldArray, attrib_mod[] newArray) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length > newArray.length)
        {
            return null;
        }
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    public static String[] copyArrayOfRange(String[] oldArray, String[] newArray, int startIndex, int stopIndex) throws InterruptedException
    {
        if (oldArray == null || newArray == null || oldArray.length == 0 || newArray.length == 0 || oldArray.length < stopIndex + 1)
        {
            return null;
        }
        int j = 0;
        for (int i = startIndex; i <= stopIndex; i++)
        {
            newArray[j] = oldArray[i];
            j++;
        }
        return newArray;
    }
    public static int setBit(int intBits, int intPos) throws InterruptedException
    {
        if (intPos < 0 || intPos > 31)
        {
            return 0;
        }
        int posVal = 1 << intPos;
        return (intBits |= posVal);
    }
    public static int clearBit(int intBits, int intPos) throws InterruptedException
    {
        if (intPos < 0 || intPos > 31)
        {
            return ~0;
        }
        int posVal = 1 << intPos;
        return (intBits &= ~posVal);
    }
    public static boolean checkBit(int intBits, int intPos) throws InterruptedException
    {
        if (intPos < 0 || intPos > 31)
        {
            return false;
        }
        int posVal = 1 << intPos;
        return ((intBits & posVal) != 0);
    }
    public static obj_id stringToObjId(String text) throws InterruptedException
    {
        Long lngId;
        try
        {
            lngId = Long.valueOf(text);
        }
        catch(NumberFormatException err)
        {
            return null;
        }
        return obj_id.getObjId(lngId.longValue());
    }
    public static obj_id[] stringToObjId(String[] text) throws InterruptedException
    {
        if ((text == null) || (text.length == 0))
        {
            return null;
        }
        obj_id[] ids = new obj_id[text.length];
        for (int i = 0; i < text.length; i++)
        {
            ids[i] = stringToObjId(text[i]);
        }
        if (ids.length == text.length)
        {
            return ids;
        }
        return null;
    }
    public static int stringToInt(String text) throws InterruptedException
    {
        if (text == null || text.equals(""))
        {
            return -1;
        }
        int amt;
        try
        {
            amt = Integer.parseInt(text);
        }
        catch(NumberFormatException err)
        {
            return -1;
        }
        return amt;
    }
    public static long stringToLong(String text) throws InterruptedException
    {
        if (text == null || text.equals(""))
        {
            return -1;
        }
        long amt;
        try
        {
            amt = Long.parseLong(text);
        }
        catch(NumberFormatException err)
        {
            return -1;
        }
        return amt;
    }
    public static float stringToFloat(String text) throws InterruptedException
    {
        if (text == null || text.equals(""))
        {
            return Float.NEGATIVE_INFINITY;
        }
        float amt;
        try
        {
            amt = Float.parseFloat(text);
        }
        catch(NumberFormatException err)
        {
            return Float.NEGATIVE_INFINITY;
        }
        return amt;
    }
    public static String objIdArrayToString(obj_id[] array) throws InterruptedException
    {
        String text = "[ ";
        for (int i = 0; i < array.length; i++)
        {
            text += "" + array[i];
            if (i < array.length - 1)
            {
                text += ", ";
            }
        }
        text += " ]";
        return text;
    }
    public static Vector removeElementAt(Vector array, int index) throws InterruptedException
    {
        if (array == null)
        {
            return null;
        }
        if (index < 0 || index >= array.size())
        {
            return array;
        }
        array.removeElementAt(index);
        return array;
    }
    public static Vector removeElement(Vector array, obj_id element) throws InterruptedException
    {
        if (array == null)
        {
            return null;
        }
        if (element == null)
        {
            return array;
        }
        array.removeElement(element);
        return array;
    }
    public static Vector removeElements(Vector array, obj_id[] elements) throws InterruptedException
    {
        if (array == null)
        {
            return null;
        }
        if (elements == null || elements.length == 0)
        {
            return array;
        }
        List collection = Arrays.asList(elements);
        array.removeAll(collection);
        return array;
    }
    public static Vector removeElements(obj_id[] array, obj_id[] elements) throws InterruptedException
    {
        if (array == null)
        {
            return null;
        }
        Vector ret = new Vector(Arrays.asList(array));
        if (elements == null || elements.length == 0)
        {
            return ret;
        }
        List collection = Arrays.asList(elements);
        ret.removeAll(collection);
        return ret;
    }
    public static Vector removeElements(Vector array, Vector elements) throws InterruptedException
    {
        if (array == null)
        {
            return null;
        }
        if (elements == null || elements.size() == 0)
        {
            return array;
        }
        array.removeAll(elements);
        return array;
    }
    public static Vector addElement(Vector array, Object element) throws InterruptedException
    {
        if (array == null)
        {
            return addElement(new Vector(), element);
        }
        if (element == null)
        {
            return array;
        }
        array.add(element);
        return array;
    }
    public static Vector addElement(Vector array, boolean element) throws InterruptedException
    {
        return addElement(array, Boolean.valueOf(element));
    }
    public static Vector addElement(Vector array, int element) throws InterruptedException
    {
        return addElement(array, Integer.valueOf(element));
    }
    public static Vector addElement(Vector array, float element) throws InterruptedException
    {
        return addElement(array, Float.valueOf(element));
    }
    public static obj_id[] toStaticObjIdArray(Vector vector) throws InterruptedException
    {
        if (vector == null || vector.size() == 0)
        {
            return null;
        }
        obj_id[] ret = new obj_id[vector.size()];
        vector.toArray(ret);
        return ret;
    }
    public static String[] toStaticStringArray(Vector vector) throws InterruptedException
    {
        if (vector == null || vector.size() == 0)
        {
            return null;
        }
        String[] ret = new String[vector.size()];
        vector.toArray(ret);
        return ret;
    }
    public static location[] toStaticLocationArray(Vector vector) throws InterruptedException
    {
        if (vector == null || vector.size() == 0)
        {
            return null;
        }
        location[] ret = new location[vector.size()];
        vector.toArray(ret);
        return ret;
    }
    public static boolean[] messageTo(obj_id[] targets, String messageName, dictionary params, float time, boolean guaranteed) throws InterruptedException
    {
        if (targets != null && targets.length > 0)
        {
            boolean[] results = new boolean[targets.length];
            for (int i = 0; i < targets.length; i++)
            {
                results[i] = messageTo(targets[i], messageName, params, time, guaranteed);
            }
            return results;
        }
        return null;
    }
    public static boolean isLocSufficientDistanceFromObjects(location loc, obj_id[] items, float dist) throws InterruptedException
    {
        if ((items == null) || (dist < 0))
        {
            return false;
        }
        for (obj_id item : items) {
            if (getDistance2D(loc, getLocation(item)) < dist) {
                return false;
            }
        }
        return true;
    }
    public static obj_id[] concatArrays(obj_id[] array1, obj_id[] array2) throws InterruptedException
    {
        if (array1 == null)
        {
            return array2;
        }
        else if (array2 == null)
        {
            return array1;
        }
        obj_id[] toPass = new obj_id[array1.length + array2.length];
        System.arraycopy(array1, 0, toPass, 0, array1.length);
        System.arraycopy(array2, 0, toPass, array1.length, array2.length);
        return toPass;
    }
    public static Vector concatArrays(Vector array1, Vector array2) throws InterruptedException
    {
        if (array1 == null)
        {
            return array2;
        }
        else if (array2 == null)
        {
            return array1;
        }
        array1.addAll(array2);
        return array1;
    }
    public static Vector concatArrays(Vector array1, Object[] array2) throws InterruptedException
    {
        if (array2 == null)
        {
            return array1;
        }
        if (array1 == null)
        {
            array1 = new Vector(array2.length + 10);
        }
        array1.addAll(Arrays.asList(array2));
        return array1;
    }
    public static Vector concatArrays(Vector array1, int[] array2) throws InterruptedException
    {
        if (array2 == null)
        {
            return array1;
        }
        Object[] toPass = new Object[array2.length];
        for (int i = 0; i < array2.length; i++)
        {
            toPass[i] = array2[i];
        }
        return concatArrays(array1, toPass);
    }
    public static Vector concatArrays(Vector array1, float[] array2) throws InterruptedException
    {
        if (array2 == null)
        {
            return array1;
        }
        Object[] toPass = new Object[array2.length];
        for (int i = 0; i < array2.length; i++)
        {
            toPass[i] = array2[i];
        }
        return concatArrays(array1, toPass);
    }
    public static Vector concatArrays(Vector array1, boolean[] array2) throws InterruptedException
    {
        if (array2 == null)
        {
            return array1;
        }
        Object[] toPass = new Object[array2.length];
        for (int i = 0; i < array2.length; i++)
        {
            toPass[i] = array2[i];
        }
        return concatArrays(array1, toPass);
    }
    public static String[] concatArrays(String[] array1, String[] array2) throws InterruptedException
    {
        if (array1 == null)
        {
            return array2;
        }
        if (array2 == null)
        {
            return array1;
        }
        String[] toPass = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, toPass, 0, array1.length);
        System.arraycopy(array2, 0, toPass, array1.length, array2.length);
        return toPass;
    }
    public static boolean isSubset(obj_id[] array1, obj_id[] array2) throws InterruptedException
    {
        if ((array1 == null) || (array2 == null))
        {
            return false;
        }
        Vector v1 = new Vector(Arrays.asList(array1));
        Vector v2 = new Vector(Arrays.asList(array2));
        return v1.containsAll(v2);
    }
    public static boolean isSubset(String[] array1, String[] array2) throws InterruptedException
    {
        if ((array1 == null) || (array2 == null))
        {
            return false;
        }
        Vector v1 = new Vector(Arrays.asList(array1));
        Vector v2 = new Vector(Arrays.asList(array2));
        return v1.containsAll(v2);
    }
    public static boolean isSubset(string_id[] array1, string_id[] array2) throws InterruptedException
    {
        if ((array1 == null) || (array2 == null))
        {
            return false;
        }
        Vector v1 = new Vector(Arrays.asList(array1));
        Vector v2 = new Vector(Arrays.asList(array2));
        return v1.containsAll(v2);
    }
    public static boolean isSubset(region[] array1, region[] array2) throws InterruptedException
    {
        if ((array1 == null) || (array2 == null))
        {
            return false;
        }
        Vector v1 = new Vector(Arrays.asList(array1));
        Vector v2 = new Vector(Arrays.asList(array2));
        return v1.containsAll(v2);
    }
    public static boolean isSubset(location[] array1, location[] array2) throws InterruptedException
    {
        if ((array1 == null) || (array2 == null))
        {
            return false;
        }
        Vector v1 = new Vector(Arrays.asList(array1));
        Vector v2 = new Vector(Arrays.asList(array2));
        return v1.containsAll(v2);
    }
    public static boolean isSubset(attrib_mod[] array1, attrib_mod[] array2) throws InterruptedException
    {
        if ((array1 == null) || (array2 == null))
        {
            return false;
        }
        Vector v1 = new Vector(Arrays.asList(array1));
        Vector v2 = new Vector(Arrays.asList(array2));
        return v1.containsAll(v2);
    }
    public static String[] makeNameListFromPlayerObjIdList(obj_id[] players) throws InterruptedException
    {
        if (players == null)
        {
            return null;
        }
        Vector nameList = new Vector();
        nameList.setSize(0);
        for (obj_id player : players) {
            if (isPlayer(player)) {
                nameList = addElement(nameList, getName(player));
            }
        }
        if (nameList.size() == players.length)
        {
            String[] _nameList = new String[nameList.size()];
            nameList.toArray(_nameList);
            return _nameList;
        }
        else 
        {
            return null;
        }
    }
    public static String[] makeNameList(obj_id[] targets) throws InterruptedException
    {
        if (targets == null)
        {
            return null;
        }
        Vector nameList = new Vector();
        nameList.setSize(0);
        String itemName;

        for (obj_id target : targets) {
            if (isIdValid(target)) {
                itemName = getEncodedName(target);
                if (itemName != null) {
                    nameList = addElement(nameList, itemName);
                }
            }
        }
        if (nameList.size() == targets.length)
        {
            String[] _nameList = new String[nameList.size()];
            nameList.toArray(_nameList);
            return _nameList;
        }
        else 
        {
            return null;
        }
    }
    public static String[] makeNameList(Vector targets) throws InterruptedException
    {
        if (targets == null || targets.isEmpty())
        {
            return null;
        }
        obj_id[] targetsArray = new obj_id[targets.size()];
        targetsArray = (obj_id[])targets.toArray(targetsArray);
        return makeNameList(targetsArray);
    }
    public static boolean isOwner(obj_id target, obj_id player) throws InterruptedException
    {
        if ((!isIdValid(target)) || (!isIdValid(player)))
        {
            return false;
        }
        if (getOwner(target) == player)
        {
            return true;
        }
        return getObjIdObjVar(target, VAR_OWNER) == player;
    }
    public static boolean isCoOwner(obj_id target, obj_id player) throws InterruptedException
    {
        if ((target == null) || (!isIdValid(player)))
        {
            return false;
        }
        obj_id[] coowners = getObjIdArrayObjVar(target, VAR_COOWNERS);
        if (coowners == null)
        {
            return false;
        }
        return getElementPositionInArray(coowners, player) > -1;
    }
    public static obj_id[] getContents(obj_id container, boolean recurse, Vector excludedNodes) throws InterruptedException
    {
        Vector contents = getResizeableContents(container, recurse, excludedNodes);
        obj_id[] _contents = new obj_id[0];
        if (contents != null)
        {
            _contents = new obj_id[contents.size()];
            contents.toArray(_contents);
        }
        return _contents;
    }
    public static obj_id[] getContents(obj_id container, boolean recurse) throws InterruptedException
    {
        Vector contents = getResizeableContents(container, recurse, null);
        obj_id[] _contents = new obj_id[0];
        if (contents != null)
        {
            _contents = new obj_id[contents.size()];
            contents.toArray(_contents);
        }
        return _contents;
    }
    public static Vector getResizeableContents(obj_id container, boolean recurse, Vector excludedNodes) throws InterruptedException
    {
        if ((!isIdValid(container)) || (getContainerType(container) == 0))
        {
            return null;
        }
        if (!recurse)
        {
            return getResizeableContents(container);
        }
        else 
        {
            Vector contents = getResizeableContents(container);
            if (contents == null)
            {
                return null;
            }
            boolean exclude = true;
            if ((excludedNodes == null) || excludedNodes.isEmpty())
            {
                exclude = false;
            }
            obj_id item;
            boolean keepItem;
            int idx;
            int itemGameObjectType;
            int containerType;
            obj_id[] newContents;

            for (int i = 0; i < contents.size(); i++)
            {
                item = ((obj_id)contents.get(i));
                if (isIdValid(item))
                {
                    keepItem = true;
                    if (exclude)
                    {
                        idx = excludedNodes.indexOf(item);
                        if (idx > -1)
                        {
                            contents = removeElementAt(contents, i);
                            i--;
                            keepItem = false;
                        }
                    }
                    if (keepItem)
                    {
                        itemGameObjectType = getGameObjectType(item);
                        if (itemGameObjectType != GOT_misc_factory_crate && itemGameObjectType != GOT_chronicles_quest_holocron)
                        {
                            containerType = getContainerType(item);
                            if (containerType != 0)
                            {
                                newContents = getContents(item);
                                if (newContents != null)
                                {
                                    contents = concatArrays(contents, newContents);
                                }
                            }
                        }
                    }
                }
            }
            return contents;
        }
    }
    public static obj_id[] getSharedContainerObjects(obj_id object) throws InterruptedException
    {
        if (!isIdValid(object) || !exists(object))
        {
            return null;
        }
        obj_id container = getContainedBy(object);
        if (!isIdValid(container) || !exists(container))
        {
            return null;
        }
        return getContents(container);
    }
    public static Vector getResizeableContents(obj_id container, boolean recurse) throws InterruptedException
    {
        return getResizeableContents(container, recurse, null);
    }
    public static obj_id[] getNonBankPlayerContents(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        obj_id bank = getPlayerBank(player);
        if (!isIdValid(bank))
        {
            return null;
        }
        Vector toExclude = addElement(null, bank);
        return getContents(player, true, toExclude);
    }
    public static obj_id[] getFilteredPlayerContents(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        Vector toExclude = null;
        obj_id bank = getPlayerBank(player);
        if (isIdValid(bank))
        {
            toExclude = addElement(null, bank);
        }
        obj_id missionBag = getMissionBag(player);
        if (isIdValid(missionBag))
        {
            toExclude = addElement(toExclude, missionBag);
        }
        obj_id datapad = getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            toExclude = addElement(toExclude, datapad);
        }
        return getContents(player, true, toExclude);
    }
    public static obj_id[] getAllContentsOwnedByPlayer(obj_id container, obj_id player) throws InterruptedException
    {
        if ((!isIdValid(container)) || (container == obj_id.NULL_ID) || (!isIdValid(player)))
        {
            return null;
        }
        Vector ownedObjects = new Vector();
        ownedObjects.setSize(0);
        obj_id[] allObjects = getContents(container, true);
        if (allObjects == null)
        {
            return null;
        }
        for (obj_id allObject : allObjects) {
            if (getObjIdObjVar(allObject, VAR_OWNER) == player) {
                ownedObjects = addElement(ownedObjects, allObject);
            }
        }
        if (ownedObjects.size() > 0)
        {
            obj_id[] _ownedObjects = new obj_id[ownedObjects.size()];
            ownedObjects.toArray(_ownedObjects);
            return _ownedObjects;
        }
        return null;
    }
    public static boolean isNestedWithin(obj_id item, obj_id container) throws InterruptedException
    {
        if (!isIdValid(item) || !isIdValid(container))
        {
            return false;
        }
        obj_id containedBy = getContainedBy(item);
        if (!isIdValid(containedBy))
        {
            return false;
        }
        else if (containedBy == container)
        {
            return true;
        }
        return isNestedWithin(containedBy, container);
    }
    public static boolean isNestedWithinAPlayer(obj_id item) throws InterruptedException
    {
        return isNestedWithinAPlayer(item, true);
    }
    public static boolean isNestedWithinAPlayer(obj_id item, boolean searchBank) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (isIdValid(getContainingPlayer(item)))
        {
            if (searchBank)
            {
                return true;
            }
            else 
            {
                obj_id bank = getPlayerBank(getContainingPlayer(item));
                if (isIdValid(bank))
                {
                    obj_id containedBy = getContainedBy(item);
                    return containedBy != bank;
                }
                else 
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static obj_id getContainingPlayer(obj_id item) throws InterruptedException
    {
        obj_id containedBy = getContainedBy(item);
        if (!isIdValid(containedBy))
        {
            return null;
        }
        else if (isPlayer(containedBy))
        {
            return containedBy;
        }
        return getContainingPlayer(containedBy);
    }
    public static boolean isNestedWithinANpcCreature(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        return isIdValid(getContainingNpcCreature(item));
    }
    public static obj_id getContainingNpcCreature(obj_id item) throws InterruptedException
    {
        obj_id containedBy = getContainedBy(item);
        if (!isIdValid(containedBy))
        {
            return null;
        }
        else if (isNpcCreature(containedBy))
        {
            return containedBy;
        }
        return getContainingNpcCreature(containedBy);
    }
    public static obj_id getContainingPlayerOrCreature(obj_id item) throws InterruptedException
    {
        obj_id containedBy = getContainedBy(item);
        if (!isIdValid(containedBy))
        {
            return null;
        }
        else if (isNpcCreature(containedBy) || isPlayer(containedBy))
        {
            return containedBy;
        }
        return getContainingPlayerOrCreature(containedBy);
    }
    public static boolean playerHasItemByTemplate(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objInventory = getInventoryContainer(objPlayer);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = getContents(objInventory, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean playerHasItemByTemplateInDataPad(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id pDataPad = getPlayerDatapad(objPlayer);
        if (isIdValid(pDataPad))
        {
            LOG("utils", "playerHasItemByTemplateInDataPad::pDataPad " + pDataPad);
            obj_id[] objContents = getContents(pDataPad, true);
            LOG("utils", "playerHasItemByTemplateInDataPad::objContents.length " + objContents.length);
            String strItemTemplate;
            for (obj_id objContent : objContents) {
                strItemTemplate = getTemplateName(objContent);
                LOG("utils", "playerHasItemByTemplateInDataPad::strItemTemplate " + strItemTemplate);
                LOG("utils", "playerHasItemByTemplateInDataPad::strTemplate " + strTemplate);
                if (strItemTemplate.equals(strTemplate)) {
                    return true;
                }
            }
        }
        else 
        {
            LOG("utils", "playerHasItemByTemplateInDataPad::Dpad was invalid");
        }
        return false;
    }
    public static int playerHasHowManyItemByTemplateInDataPad(obj_id objPlayer, String strTemplate, boolean recurse) throws InterruptedException
    {
        int count = 0;
        obj_id pDataPad = getPlayerDatapad(objPlayer);
        if (isIdValid(pDataPad))
        {
            obj_id[] objContents = getContents(pDataPad, recurse);
            if (objContents != null)
            {
                String strItemTemplate;

                for (obj_id objContent : objContents) {
                    strItemTemplate = getTemplateName(objContent);
                    if (strItemTemplate.equals(strTemplate)) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }
    public static boolean playerHasItemByTemplateInInventoryOrEquipped(obj_id player, String desiredTemplate) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (getTemplateName(content).equals(desiredTemplate)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static obj_id getItemByTemplateInInventoryOrEquipped(obj_id player, String desiredTemplate) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (getTemplateName(content).equals(desiredTemplate)) {
                    return content;
                }
            }
        }
        return null;
    }
    public static boolean playerHasItemWithObjVarInInventoryOrEquipped(obj_id player, String desiredObjVar) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (hasObjVar(content, desiredObjVar)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean playerHasItemByTemplateWithObjVarInInventoryOrEquipped(obj_id player, String desiredTemplate, String desiredObjVar) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (getTemplateName(content).equals(desiredTemplate)) {
                    if (hasObjVar(content, desiredObjVar)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean playerHasItemByTemplateInBank(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objBank = getPlayerBank(objPlayer);
        if (isIdValid(objBank))
        {
            obj_id[] objContents = getContents(objBank, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean playerHasItemByTemplateInBankOrInventory(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        if (playerHasItemByTemplate(objPlayer, strTemplate))
        {
            return true;
        }
        else if (playerHasItemByTemplateInBank(objPlayer, strTemplate))
        {
            return true;
        }
        return false;
    }
    public static boolean playerHasStaticItemInBankOrInventory(obj_id player, String staticItem) throws InterruptedException
    {
        return (isIdValid(getStaticItemInBankOrInventory(player, staticItem)));
    }
    public static obj_id getStaticItemInBankOrInventory(obj_id player, String staticItem) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        String itemName;
        if (contents != null && contents.length > 0)
        {
            for (obj_id content : contents) {
                itemName = getStaticItemName(content);
                if (itemName != null && staticItem != null && staticItem.equals(itemName)) {
                    return content;
                }
            }
        }
        obj_id bank = getPlayerBank(player);
        if (isIdValid(bank))
        {
            contents = getContents(bank, true);
            if (contents != null && contents.length > 0)
            {
                for (obj_id content : contents) {
                    itemName = static_item.getStaticItemName(content);
                    if (itemName != null && staticItem != null && staticItem.equals(itemName)) {
                        return content;
                    }
                }
            }
        }
        return null;
    }
    public static boolean playerHasStaticItemInAppearanceInventory(obj_id player, String staticItem) throws InterruptedException
    {
        return (isIdValid(getStaticItemInAppearanceInventory(player, staticItem)));
    }
    public static obj_id getStaticItemInAppearanceInventory(obj_id player, String staticItem) throws InterruptedException
    {
        obj_id[] contents = getAllItemsFromAppearanceInventory(player);
        
        {
            if (contents != null && contents.length > 0)
            {
                String itemName;

                for (obj_id content : contents) {
                    itemName = getStaticItemName(content);
                    if (itemName != null && staticItem != null && staticItem.equals(itemName)) {
                        return content;
                    }
                }
            }
        }
        return null;
    }
    public static obj_id[] getAllStaticItemsInPlayerInventory(obj_id player, String staticItem) throws InterruptedException
    {
        Vector objectList = new Vector();
        obj_id inventory = getInventoryContainer(player);
        if (isIdValid(inventory))
        {
            obj_id[] contents = getInventoryAndEquipment(player);
            if (contents != null && contents.length > 0)
            {
                for (obj_id content : contents) {
                    if (content != null) {
                        if (static_item.isStaticItem(content)) {
                            if (getStaticItemName(content).equals(staticItem)) {
                                objectList = addElement(objectList, content);
                            }
                        }
                    }
                }
            }
        }
        if (objectList.size() > 0)
        {
            obj_id[] staticList = new obj_id[objectList.size()];
            objectList.toArray(staticList);
            return staticList;
        }
        return null;
    }
    public static obj_id getStaticItemInInventory(obj_id player, String staticItem) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null && contents.length > 0)
        {
            String itemName;
            for (obj_id content : contents) {
                itemName = getStaticItemName(content);
                if (itemName != null && staticItem != null && staticItem.equals(itemName)) {
                    return content;
                }
            }
        }
        return null;
    }
    public static int countOfStackedItemsInArray(obj_id[] items) throws InterruptedException
    {
        int totalCount = 0;

        for (obj_id item : items) {
            testAbortScript();
            totalCount += getCount(item);
        }
        return totalCount;
    }
    public static obj_id getItemPlayerHasByTemplate(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objInventory = getInventoryContainer(objPlayer);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = getContents(objInventory, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        return objContent;
                    }
                }
            }
        }
        return null;
    }
    public static obj_id getItemPlayerHasByTemplateInDatapad(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id pDataPad = getPlayerDatapad(objPlayer);
        if (isIdValid(pDataPad))
        {
            obj_id[] objContents = getContents(pDataPad, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        return objContent;
                    }
                }
            }
        }
        return null;
    }
    public static obj_id getItemPlayerHasByTemplateInBank(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objBank = getPlayerBank(objPlayer);
        if (isIdValid(objBank))
        {
            obj_id[] objContents = getContents(objBank, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        return objContent;
                    }
                }
            }
        }
        return null;
    }
    public static obj_id getItemPlayerHasByTemplateInBankOrInventory(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id itemObj = getItemPlayerHasByTemplate(objPlayer, strTemplate);
        if (isIdValid(itemObj))
        {
            return itemObj;
        }
        else 
        {
            itemObj = getItemPlayerHasByTemplateInBank(objPlayer, strTemplate);
            if (isIdValid(itemObj))
            {
                return itemObj;
            }
        }
        return null;
    }
    public static obj_id[] getAllItemsPlayerHasByTemplate(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objInventory = getInventoryContainer(objPlayer);
        if (isIdValid(objInventory))
        {
            return getAllItemsInContainerByTemplate(objInventory, strTemplate, true);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getAllItemsPlayerHasByTemplateInBank(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objBank = getPlayerBank(objPlayer);
        if (isIdValid(objBank))
        {
            return getAllItemsInContainerByTemplate(objBank, strTemplate, true);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getAllItemsInContainerByTemplate(obj_id container, String template, boolean recurse) throws InterruptedException
    {
        return getAllItemsInContainerByTemplate(container, template, recurse, true);
    }
    public static obj_id[] getAllItemsInContainerByTemplate(obj_id container, String template, boolean recurse, boolean allowEquipped) throws InterruptedException
    {
        Vector objectList = new Vector();
        if (isIdValid(container))
        {
            obj_id[] objContents = getContents(container, recurse);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(template)) {
                        if (!allowEquipped) {
                            if (isEquipped(objContent)) {
                                continue;
                            }
                        }
                        objectList = addElement(objectList, objContent);
                    }
                }
            }
        }
        if (objectList.size() > 0)
        {
            obj_id[] staticList = new obj_id[objectList.size()];
            objectList.toArray(staticList);
            return staticList;
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getAllItemsPlayerHasByTemplateStartsWith(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objInventory = getInventoryContainer(objPlayer);
        if (isIdValid(objInventory))
        {
            return getAllItemsInContainerByTemplateStartsWith(objInventory, strTemplate, true);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getAllItemsPlayerHasByTemplateInBankStartsWith(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        obj_id objBank = getPlayerBank(objPlayer);
        if (isIdValid(objBank))
        {
            return getAllItemsInContainerByTemplateStartsWith(objBank, strTemplate, true);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getAllItemsInContainerByTemplateStartsWith(obj_id container, String template, boolean recurse) throws InterruptedException
    {
        Vector objectList = new Vector();
        if (isIdValid(container))
        {
            obj_id[] objContents = getContents(container, recurse);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).startsWith(template)) {
                        objectList = addElement(objectList, objContent);
                    }
                }
            }
        }
        if (objectList.size() > 0)
        {
            obj_id[] staticList = new obj_id[objectList.size()];
            objectList.toArray(staticList);
            return staticList;
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getAllItemsPlayerHasByTemplateInBankAndInventory(obj_id objPlayer, String strTemplate) throws InterruptedException
    {
        Vector objectList = new Vector();
        obj_id objInventory = getInventoryContainer(objPlayer);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = getContents(objInventory, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        objectList = addElement(objectList, objContent);
                    }
                }
            }
        }
        obj_id objBank = getPlayerBank(objPlayer);
        if (isIdValid(objBank))
        {
            obj_id[] objContents = getContents(objBank, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    if (getTemplateName(objContent).equals(strTemplate)) {
                        objectList = addElement(objectList, objContent);
                    }
                }
            }
        }
        if (objectList.size() > 0)
        {
            obj_id[] staticList = new obj_id[objectList.size()];
            objectList.toArray(staticList);
            return staticList;
        }
        return null;
    }
    public static obj_id[] getAllItemsInBankAndInventory(obj_id objPlayer) throws InterruptedException
    {
        Vector objectList = new Vector();
        obj_id[] objContents = getInventoryAndEquipment(objPlayer);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                objectList = addElement(objectList, objContent);
            }
        }
        obj_id objBank = getPlayerBank(objPlayer);
        if (isIdValid(objBank))
        {
            objContents = getContents(objBank, true);
            if (objContents != null)
            {
                for (obj_id objContent : objContents) {
                    objectList = addElement(objectList, objContent);
                }
            }
        }
        if (objectList.size() > 0)
        {
            obj_id[] staticList = new obj_id[objectList.size()];
            objectList.toArray(staticList);
            return staticList;
        }
        return null;
    }
    public static location getRandomLocationInRing(location locOrigin, float fltMinDistance, float fltMaxDistance) throws InterruptedException
    {
        float fltDistance = (fltMaxDistance - fltMinDistance) * rand() + fltMinDistance;
        debugServerConsoleMsg(null, "fltDistance is " + fltDistance);
        return rotatePointXZ(locOrigin, fltDistance, rand(0, 360));
    }
    public static location rotatePointXZ(location locOrigin, float fltDistance, float fltAngle) throws InterruptedException
    {
        location locOffset = (location)locOrigin.clone();
        locOffset.x = locOrigin.x + fltDistance;
        locOffset.z = locOrigin.z;
        return rotatePointXZ(locOrigin, locOffset, fltAngle);
    }
    public static location rotatePointXZ(location locOrigin, location locPoint, float fltAngle) throws InterruptedException
    {
        float dx = locPoint.x - locOrigin.x;
        float dz = locPoint.z - locOrigin.z;
        float fltRadians = (float)Math.toRadians(fltAngle);
        float fltC = (float) StrictMath.cos(fltRadians);
        float fltS = (float) StrictMath.sin(fltRadians);
        location locNewOffset = (location)locOrigin.clone();
        locNewOffset.x += (dx * fltS) - (dz * fltC);
        locNewOffset.y = locPoint.y;
        locNewOffset.z += (dx * fltC) + (dz * fltS);
        return locNewOffset;
    }
    public static location rotatePointXZ(location locPoint, float fltAngle) throws InterruptedException
    {
        float fltRadians = (float)Math.toRadians(fltAngle);
        float fltC = (float) StrictMath.cos(fltRadians);
        float fltS = (float) StrictMath.sin(fltRadians);
        location locNewPoint = (location)locPoint.clone();
        locNewPoint.x += (locPoint.x * fltC) - (locPoint.z * fltS);
        locNewPoint.z += (locPoint.x * fltS) + (locPoint.z * fltC);
        return locNewPoint;
    }
    public static float getHeadingToLocation(location here, location there) throws InterruptedException
    {
        if (here == null || there == null)
        {
            return -1.0f;
        }
        float dx = there.x - here.x;
        float dz = there.z - here.z;
        double radHeading = StrictMath.atan2(-dx, dz);
        double degreeHeading = Math.toDegrees(radHeading);
        return (float)(degreeHeading);
    }
    public static location findLocInFrontOfTarget(obj_id target, float distance) throws InterruptedException
    {
        if (target == null || !exists(target))
        {
            return null;
        }
        location origin = getLocation(target);
        if (origin == null)
        {
            return null;
        }
        return rotatePointXZ(origin, distance, getYaw(target));
    }
    public static attrib_mod createAttribMod(int attrib, int value, float duration, float attack, float decay) throws InterruptedException
    {
        return new attrib_mod(attrib, value, duration, attack, decay);
    }
    public static attrib_mod createAttribMod(int attrib, int value, float duration, float attack) throws InterruptedException
    {
        return new attrib_mod(attrib, value, duration, attack, 0.0f);
    }
    public static attrib_mod createAttribMod(int attrib, int value, float duration) throws InterruptedException
    {
        return new attrib_mod(attrib, value, duration, 0.0f, 0.0f);
    }
    public static attrib_mod createHealDamageAttribMod(int attrib, int value) throws InterruptedException
    {
        if ((value < 1) || (attrib % 3 != 0))
        {
            return null;
        }
        return new attrib_mod(attrib, value, 0.0f, 0.0f, MOD_POOL);
    }
    public static attrib_mod createHealWoundAttribMod(int attrib, int value) throws InterruptedException
    {
        if (value < 1)
        {
            return null;
        }
        return new attrib_mod(attrib, value, 0.0f, healing.AM_HEAL_WOUND, 0.0f);
    }
    public static attrib_mod createHealShockAttribMod(int value) throws InterruptedException
    {
        if (value < 1)
        {
            return null;
        }
        return new attrib_mod(0, value, 0.0f, healing.AM_HEAL_SHOCK, 0.0f);
    }
    public static attrib_mod createAddShockAttribMod(int value) throws InterruptedException
    {
        if (value < 1)
        {
            return null;
        }
        return new attrib_mod(0, value, 0.0f, healing.AM_ADD_SHOCK, 0.0f);
    }
    public static attrib_mod createWoundAttribMod(int attrib, int value) throws InterruptedException
    {
        if (value < 1)
        {
            return null;
        }
        return new attrib_mod(attrib, value, 0.0f, 0.0f, MOD_WOUND);
    }
    public static attrib_mod createAntidoteAttribMod(int attrib) throws InterruptedException
    {
        return new attrib_mod(attrib, 0, 0.0f, 0.0f, MOD_ANTIDOTE);
    }
    public static boolean addAttribMod(obj_id target, int attrib, int value, float duration) throws InterruptedException
    {
        return addAttribModifier(target, attrib, value, duration, 0.0f, 0.0f);
    }
    public static boolean addAttribMod(obj_id target, attrib_mod am) throws InterruptedException
    {
        if (target == null || am == null)
        {
            return false;
        }
        boolean litmus = true;
        int attrib = am.getAttribute();
        float duration = am.getDuration();
        int amt = am.getValue();
        int attack = (int)(am.getAttack());
        if (attack < 0)
        {
            switch (attack)
            {
                case (int)healing.AM_HEAL_WOUND:
                    if (amt < 0)
                    {
                        amt = 0;
                    }
                    break;
                case (int)healing.AM_HEAL_SHOCK:
                    if (amt < 0)
                    {
                        amt = 0;
                    }
                    litmus = healShockWound(target, amt);
                    break;
                case (int)healing.AM_ADD_SHOCK:
                    if (amt < 0)
                    {
                        amt = 0;
                    }
                    litmus = addShockWound(target, amt);
                    break;
            }
        }
        else 
        {
            if (!addAttribModifier(target, attrib, amt, duration, am.getAttack(), am.getDecay()))
            {
                litmus = false;
            }
        }
        return litmus;
    }
    public static boolean addAttribMod(obj_id target, attrib_mod[] am) throws InterruptedException
    {
        if ((target == null) || (am == null) || (am.length == 0))
        {
            return false;
        }
        boolean ret = true;
        for (attrib_mod anAm : am) {
            ret &= addAttribMod(target, anAm);
        }
        return ret;
    }
    public static boolean setHeading(obj_id target, float heading) throws InterruptedException {
        return target != null && setYaw(target, heading);
    }
    public static dictionary parseKeyPairList(String keyPairList, char delim) throws InterruptedException
    {
        if (keyPairList.equals(""))
        {
            return null;
        }
        dictionary d = new dictionary();
        String[] pairs = split(keyPairList, delim);
        if ((pairs == null) || (pairs.length == 0))
        {
            return null;
        }
        String[] tmp;
        int val;

        for (String pair : pairs) {
            tmp = split(pair, '=');
            if ((tmp != null) && (tmp.length == 2)) {
                val = stringToInt(tmp[1]);
                if (val != -1) {
                    d.put(tmp[0], val);
                }
            }
        }
        return d;
    }
    public static dictionary parseKeyPairList(String keyPairList) throws InterruptedException
    {
        return parseKeyPairList(keyPairList, ',');
    }
    public static boolean putInPlayerInventory(obj_id player, obj_id item) throws InterruptedException {
        if ((!isIdValid(player)) || (!isPlayer(player)) || (item == null)) {
            return false;
        }
        obj_id inventory = getObjectInSlot(player, SLOT_INVENTORY);
        return inventory != null && putIn(item, inventory);
    }
    public static boolean putInPlayerDatapad(obj_id player, obj_id item) throws InterruptedException {
        if ((!isIdValid(player)) || (!isPlayer(player)) || (item == null)) {
            return false;
        }
        obj_id datapad = getObjectInSlot(player, SLOT_DATAPAD);
        return datapad != null && putIn(item, datapad);
    }
    public static obj_id getInventoryContainer(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        return getObjectInSlot(target, SLOT_INVENTORY);
    }
    public static obj_id getDroidInventoryContainer(obj_id droid) throws InterruptedException
    {
        if (!isIdValid(droid))
        {
            return null;
        }
        if (callable.hasCallableCD(droid))
        {
            return getObjectInSlot(callable.getCallableCD(droid), SLOT_INVENTORY);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getMissionBag(obj_id objPlayer) throws InterruptedException
    {
        if (!isIdValid(objPlayer))
        {
            return null;
        }
        return getObjectInSlot(objPlayer, SLOT_MISSION_BAG);
    }
    public static obj_id getDatapad(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        return getObjectInSlot(target, SLOT_DATAPAD);
    }
    public static obj_id getPlayerDatapad(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return null;
        }
        return getObjectInSlot(player, SLOT_DATAPAD);
    }
    public static obj_id getPlayerHangar(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return null;
        }
        return getObjectInSlot(player, SLOT_HANGAR);
    }
    public static obj_id getPlayerBank(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return null;
        }
        return getObjectInSlot(player, SLOT_BANK);
    }
    public static boolean itemNotActive(obj_id player) throws InterruptedException
    {
        if ((!isIdValid(player)) || (!isPlayer(player)))
        {
            return false;
        }
        sendSystemMessage(player, SID_OBJECT_NOT_ACTIVE);
        return true;
    }
    public static boolean requestContainerOpen(obj_id player, obj_id container) throws InterruptedException
    {
        if ((!isIdValid(player)) || (!isPlayer(player)) || (!isIdValid(container)) || (getContainerType(container) == 0))
        {
            return false;
        }
        queueCommand(player, (1880585606), container, "", COMMAND_PRIORITY_DEFAULT);
        return true;
    }
    public static String packStringId(string_id strId) throws InterruptedException
    {
        if (strId == null)
        {
            return null;
        }
        if (!strId.isValid())
        {
            return null;
        }
        return "@" + strId.getTable() + ":" + strId.getAsciiId();
    }
    public static string_id unpackString(String strId) throws InterruptedException
    {
        if (strId == null || strId.equals(""))
        {
            return null;
        }
        if (strId.startsWith("@"))
        {
            strId = strId.substring(1);
        }
        String[] s = split(strId, ':');
        if (s != null && s.length == 2)
        {
            return new string_id(s[0], s[1]);
        }
        return null;
    }
    public static obj_id getNearbyPlayerByName(obj_id actor, String name) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(name);
        if (st.hasMoreTokens())
        {
            String compareName = toLower(st.nextToken());
            obj_id[] players = getPlayerCreaturesInRange(actor, 128.0f);
            if (players != null)
            {
                StringTokenizer st2;
                String playerName;

                for (obj_id player : players) {
                    st2 = new StringTokenizer(getName(player));
                    playerName = toLower(st2.nextToken());
                    if (compareName.equals(playerName)) {
                        return player;
                    }
                }
            }
        }
        return obj_id.NULL_ID;
    }
    public static void sendPostureChange(obj_id objCreature, int intPosture) throws InterruptedException
    {
        attacker_results cbtAttackerResults = new attacker_results();
        setPosture(objCreature, intPosture);
        String strPlayback = "change_posture";
        cbtAttackerResults.id = objCreature;
        cbtAttackerResults.endPosture = intPosture;
        cbtAttackerResults.weapon = null;
        doCombatResults(strPlayback, cbtAttackerResults, null);
    }
    public static obj_id[] getBuildingsInObjIdList(obj_id[] items) throws InterruptedException
    {
        if ((items == null) || (items.length == 0))
        {
            return null;
        }
        Vector buildings = new Vector();
        buildings.setSize(0);
        for (obj_id item : items) {
            if (getCellNames(item) != null) {
                buildings = addElement(buildings, item);
            }
        }
        if ((buildings == null) || (buildings.size() == 0))
        {
            return null;
        }
        else 
        {
            obj_id[] _buildings = new obj_id[0];
            _buildings = new obj_id[buildings.size()];
            buildings.toArray(_buildings);
            return _buildings;
        }
    }
    public static obj_id[] getContainedGOTObjects(obj_id container, int got, boolean recurse, boolean allowDerived) throws InterruptedException
    {
        if (!isIdValid(container) || (got < 0))
        {
            return null;
        }
        if (getContainerType(container) == 0)
        {
            return null;
        }
        obj_id[] contents = getContents(container, recurse);
        if ((contents == null) || (contents.length == 0))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        for (obj_id content : contents) {
            int myType = getGameObjectType(content);
            if (!allowDerived) {
                if (myType == got) {
                    ret = addElement(ret, content);
                }
            } else {
                if (isGameObjectTypeOf(myType, got)) {
                    ret = addElement(ret, content);
                }
            }
        }
        if ((ret == null) || (ret.size() == 0))
        {
            return null;
        }
        obj_id[] _ret = new obj_id[0];
        _ret = new obj_id[ret.size()];
        ret.toArray(_ret);
        return _ret;
    }
    public static obj_id[] getContainedGOTObjects(obj_id container, int got, boolean recurse) throws InterruptedException
    {
        return getContainedGOTObjects(container, got, recurse, false);
    }
    public static obj_id[] getContainedGOTObjects(obj_id container, int got) throws InterruptedException
    {
        return getContainedGOTObjects(container, got, false);
    }
    public static obj_id[] getContainedObjectsWithObjVar(obj_id container, String var, boolean recurse) throws InterruptedException
    {
        if (!isIdValid(container))
        {
            return null;
        }
        if (getContainerType(container) == 0)
        {
            return null;
        }
        if (var == null || var.equals(""))
        {
            return null;
        }
        obj_id[] contents = getContents(container, recurse);
        if (contents == null || contents.length == 0)
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        for (obj_id content : contents) {
            if (hasObjVar(content, var)) {
                ret = addElement(ret, content);
            }
        }
        if ((ret == null) || (ret.size() == 0))
        {
            return null;
        }
        obj_id[] _ret = new obj_id[0];
        _ret = new obj_id[ret.size()];
        ret.toArray(_ret);
        return _ret;
    }
    public static obj_id[] getContainedObjectsWithObjVar(obj_id container, String var) throws InterruptedException
    {
        return getContainedObjectsWithObjVar(container, var, false);
    }
    public static obj_id[] getContainedObjectsWithScript(obj_id container, String script, boolean recurse) throws InterruptedException
    {
        if (!isIdValid(container))
        {
            return null;
        }
        if (getContainerType(container) == 0)
        {
            return null;
        }
        if (script == null || script.equals(""))
        {
            return null;
        }
        obj_id[] contents = getContents(container, recurse);
        if (contents == null || contents.length == 0)
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        for (obj_id content : contents) {
            if (hasScript(content, script)) {
                ret = addElement(ret, content);
            }
        }
        if ((ret == null) || (ret.size() == 0))
        {
            return null;
        }
        obj_id[] _ret = new obj_id[0];
        _ret = new obj_id[ret.size()];
        ret.toArray(_ret);
        return _ret;
    }
    public static obj_id[] getContainedObjectsWithScript(obj_id container, String script) throws InterruptedException
    {
        return getContainedObjectsWithScript(container, script, false);
    }
    public static void sendMail(string_id subject, string_id body, String to, String from) throws InterruptedException
    {
        chatSendPersistentMessage(from, to, "@" + subject.toString(), null, chatMakePersistentMessageOutOfBandBody(null, body));
    }
    public static void sendMail(string_id subject, string_id body, obj_id to, String from) throws InterruptedException
    {
        chatSendPersistentMessage(from, to, "@" + subject.toString(), null, chatMakePersistentMessageOutOfBandBody(null, body));
    }
    public static void sendMail(string_id subject, prose_package body, obj_id to, String from) throws InterruptedException
    {
        chatSendPersistentMessage(from, to, "@" + subject.toString(), null, chatMakePersistentMessageOutOfBandBody(null, body));
    }
    public static void sendMail(string_id subject, prose_package body, String to, String from) throws InterruptedException
    {
        chatSendPersistentMessage(from, to, "@" + subject.toString(), null, chatMakePersistentMessageOutOfBandBody(null, body));
    }
    public static boolean isNightTime() throws InterruptedException
    {
        return getLocalTime() >= getLocalDayLength();
    }
    public static void sendSystemMessageTestingOnly(obj_id[] players, String message) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        for (obj_id player : players) {
            if (isIdValid(player) && exists(player)) {
                sendSystemMessageTestingOnly(player, message);
            }
        }
    }
    public static void sendSystemMessage(obj_id[] players, string_id message) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        for (obj_id player : players) {
            if (isIdValid(player) && exists(player)) {
                sendSystemMessage(player, message);
            }
        }
    }
    public static void sendSystemMessageProse(obj_id[] players, prose_package message) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        for (obj_id player : players) {
            if (isIdValid(player) && exists(player)) {
                sendSystemMessageProse(player, message);
            }
        }
    }
    public static void sendSystemMessagePob(obj_id pob, string_id message) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(pob);
        if (players != null && players.length > 0)
        {
            utils.sendSystemMessage(players, message);
        }
    }
    public static void sendSystemMessageProsePob(obj_id pob, prose_package message) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(pob);
        if (players != null && players.length > 0)
        {
            utils.sendSystemMessageProse(players, message);
        }
    }
    public static void messagePlayer(obj_id source, obj_id player, string_id message, String templateOverride) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = message;
        if (templateOverride.equals("none"))
        {
            commPlayer(source, player, pp);
        }
        else 
        {
            commPlayer(source, player, pp, templateOverride);
        }
    }
    public static void messagePlayer(obj_id source, obj_id player, string_id message) throws InterruptedException
    {
        messagePlayer(source, player, message, "none");
    }
    public static void messagePlayer(obj_id source, obj_id[] players, string_id message, String templateOverride) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = message;
        if (templateOverride.equals("none"))
        {
            commPlayers(source, players, pp);
        }
        else 
        {
            commPlayers(source, players, pp, templateOverride);
        }
    }
    public static void messagePlayer(obj_id source, obj_id[] players, string_id message, String templateOverride, float duration) throws InterruptedException
    {
        prose_package pp = new prose_package();
        pp.stringId = message;
        if (templateOverride.equals("none"))
        {
            commPlayers(source, players, pp);
            return;
        }
        if (duration <= 0.0f)
        {
            commPlayers(source, players, pp, templateOverride);
        }
        else 
        {
            commPlayers(source, templateOverride, null, duration, players, pp);
        }
    }
    public static void messagePlayer(obj_id source, obj_id[] players, string_id message) throws InterruptedException
    {
        messagePlayer(source, players, message, "none");
    }
    public static boolean setLocalVar(obj_id target, String path, Vector val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        if (val == null || val.isEmpty())
        {
            return false;
        }
        Object sample = val.get(0);
        if (sample == null)
        {
            return false;
        }
        boolean result = false;
        if (sample instanceof Integer)
        {
            int count = val.size();
            int[] tempVal = new int[count];
            for (int i = 0; i < count; ++i)
            {
                tempVal[i] = (Integer) val.get(i);
            }
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof Boolean)
        {
            int count = val.size();
            boolean[] tempVal = new boolean[count];
            for (int i = 0; i < count; ++i)
            {
                tempVal[i] = (Boolean) val.get(i);
            }
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof Float)
        {
            int count = val.size();
            float[] tempVal = new float[count];
            for (int i = 0; i < count; ++i)
            {
                tempVal[i] = (((Float)val.get(i))).intValue();
            }
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof String)
        {
            String[] tempVal = new String[val.size()];
            tempVal = (String[])val.toArray(tempVal);
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof obj_id)
        {
            obj_id[] tempVal = new obj_id[val.size()];
            tempVal = (obj_id[])val.toArray(tempVal);
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof location)
        {
            location[] tempVal = new location[val.size()];
            tempVal = (location[])val.toArray(tempVal);
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof transform)
        {
            transform[] tempVal = new transform[val.size()];
            tempVal = (transform[])val.toArray(tempVal);
            result = setLocalVar(target, path, tempVal);
        }
        else if (sample instanceof vector)
        {
            vector[] tempVal = new vector[val.size()];
            tempVal = (vector[])val.toArray(tempVal);
            result = setLocalVar(target, path, tempVal);
        }
        return result;
    }
    public static boolean setLocalVar(obj_id target, String path, location val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, location[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, String val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, String[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, obj_id val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, obj_id[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, int val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, int[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, float val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, float[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, boolean val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, transform val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, transform[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, vector val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, vector[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, boolean[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean setLocalVar(obj_id target, String path, dictionary val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().put(path, val);
        return true;
    }
    public static boolean hasLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().containsKey(path);
    }
    public static boolean removeLocalVar(obj_id target, String path) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptDictionary().remove(path);
        return true;
    }
    public static int getIntLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getInt(path);
    }
    public static int[] getIntArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getIntArray(path);
    }
    public static float getFloatLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getFloat(path);
    }
    public static float[] getFloatArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getFloatArray(path);
    }
    public static String getStringLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getString(path);
    }
    public static String[] getStringArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getStringArray(path);
    }
    public static transform[] getTransformArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getTransformArray(path);
    }
    public static boolean getBooleanLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getBoolean(path);
    }
    public static boolean[] getBooleanArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getBooleanArray(path);
    }
    public static location getLocationLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getLocation(path);
    }
    public static location[] getLocationArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getLocationArray(path);
    }
    public static obj_id getObjIdLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getObjId(path);
    }
    public static obj_id[] getObjIdArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getObjIdArray(path);
    }
    public static Vector getResizeableObjIdArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        obj_id[] objArray = dd.getObjIdArray(path);
        return new Vector(Arrays.asList(objArray));
    }
    public static Vector getResizeableLocationArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        location[] locArray = dd.getLocationArray(path);
        return new Vector(Arrays.asList(locArray));
    }
    public static Vector getResizeableIntArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        int[] intArray = dd.getIntArray(path);
        Vector rszArray = new Vector(intArray.length + 1);
        for (int anIntArray : intArray) {
            rszArray.add(anIntArray);
        }
        return rszArray;
    }
    public static Vector getResizeableFloatArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        float[] fltArray = dd.getFloatArray(path);
        Vector rszArray = new Vector(fltArray.length + 10);
        for (float aFltArray : fltArray) {
            rszArray.add(aFltArray);
        }
        return rszArray;
    }
    public static Vector getResizeableStringArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        String[] strArray = dd.getStringArray(path);
        return new Vector(Arrays.asList((String[])strArray));
    }
    public static Vector getResizeableTransformArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        transform[] trArray = dd.getTransformArray(path);
        return new Vector(Arrays.asList(trArray));
    }
    public static Vector getResizeableVectorArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        dictionary dd = target.getScriptDictionary();
        vector[] vctArray = dd.getVectorArray(path);
        return new Vector(Arrays.asList(vctArray));
    }
    public static string_id getStringIdLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getStringId(path);
    }
    public static string_id[] getStringIdArrayLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getStringIdArray(path);
    }
    public static dictionary getDictionaryLocalVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptDictionary().getDictionary(path);
    }
    public static boolean setScriptVar(obj_id target, String path, location val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, location[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, String val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, String[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, String[][] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, obj_id val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, obj_id[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, int val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, int[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, long val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, long[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, double val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, double[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, float val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, float[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, boolean val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, transform val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, transform[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, vector val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, vector[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, boolean[] val) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean setScriptVar(obj_id target, String path, Vector val) throws InterruptedException
    {
        if (!isIdValid(target) || path == null || path.equals("") || val == null || val.isEmpty())
        {
            return false;
        }
        Object sample = val.get(0);
        if (sample == null)
        {
            return false;
        }
        boolean result = false;
        if (sample instanceof Integer)
        {
            int count = val.size();
            int[] tempVal = new int[count];
            for (int i = 0; i < count; ++i)
            {
                tempVal[i] = (Integer) val.get(i);
            }
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof Boolean)
        {
            int count = val.size();
            boolean[] tempVal = new boolean[count];
            for (int i = 0; i < count; ++i)
            {
                tempVal[i] = (Boolean) val.get(i);
            }
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof Float)
        {
            int count = val.size();
            float[] tempVal = new float[count];
            for (int i = 0; i < count; ++i)
            {
                tempVal[i] = ((Float)val.get(i)).intValue();
            }
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof String)
        {
            String[] tempVal = new String[val.size()];
            tempVal = (String[])val.toArray(tempVal);
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof obj_id)
        {
            obj_id[] tempVal = new obj_id[val.size()];
            tempVal = (obj_id[])val.toArray(tempVal);
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof location)
        {
            location[] tempVal = new location[val.size()];
            tempVal = (location[])val.toArray(tempVal);
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof transform)
        {
            transform[] tempVal = new transform[val.size()];
            tempVal = (transform[])val.toArray(tempVal);
            result = setScriptVar(target, path, tempVal);
        }
        else if (sample instanceof vector)
        {
            vector[] tempVal = new vector[val.size()];
            tempVal = (vector[])val.toArray(tempVal);
            result = setScriptVar(target, path, tempVal);
        }
        return result;
    }
    public static boolean setScriptVar(obj_id target, String path, dictionary val) throws InterruptedException
    {
        if (!isIdValid(target) || path == null || path.equals(""))
        {
            return false;
        }
        target.getScriptVars().put(path, val);
        return true;
    }
    public static boolean hasScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.hasScriptVar(path);
    }
    public static boolean hasScriptVarTree(obj_id target, String path) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return false;
        }
        if ((path == null) || (path.equals("")))
        {
            return false;
        }
        Enumeration keys = target.getScriptVars().keys();
        String key;
        while (keys.hasMoreElements())
        {
            key = (String) (keys.nextElement());
            if (key.equals(path) || key.startsWith(path + "."))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean removeScriptVar(obj_id target, String path) throws InterruptedException
    {
        if (target == null || target == obj_id.NULL_ID || path == null || path.equals(""))
        {
            return false;
        }
        target.getScriptVars().remove(path);
        return true;
    }
    public static boolean removeScriptVarTree(obj_id target, String path) throws InterruptedException
    {
        if (target == null || target == obj_id.NULL_ID || path == null || path.equals(""))
        {
            return false;
        }
        Enumeration keys = target.getScriptVars().keys();
        String key;
        while (keys.hasMoreElements())
        {
            key = (String) (keys.nextElement());
            if (key.equals(path) || key.startsWith(path + "."))
            {
                target.getScriptVars().remove(key);
            }
        }
        return true;
    }
    public static int getIntScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getInt(path);
    }
    public static int[] getIntArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getIntArray(path);
    }
    public static float getFloatScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getFloat(path);
    }
    public static float[] getFloatArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getFloatArray(path);
    }
    public static long getLongScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getLong(path);
    }
    public static long[] getLongArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getLongArray(path);
    }
    public static double getDoubleScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getDouble(path);
    }
    public static double[] getDoubleArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getDoubleArray(path);
    }
    public static String getStringScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getString(path);
    }
    public static String[] getStringArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getStringArray(path);
    }
    public static String[][] getStringArrayArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getStringArrayArray(path);
    }
    public static transform[] getTransformArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getTransformArray(path);
    }
    public static transform getTransformScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getTransform(path);
    }
    public static boolean getBooleanScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getBoolean(path);
    }
    public static boolean[] getBooleanArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getBooleanArray(path);
    }
    public static location getLocationScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getLocation(path);
    }
    public static location[] getLocationArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getLocationArray(path);
    }
    public static obj_id getObjIdScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getObjId(path);
    }
    public static obj_id[] getObjIdArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getObjIdArray(path);
    }
    public static Vector getResizeableObjIdArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableObjIdArray(path);
    }
    public static Vector getResizeableLocationArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableLocationArray(path);
    }
    public static Vector getResizeableIntArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableIntArray(path);
    }
    public static Vector getResizeableFloatArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableFloatArray(path);
    }
    public static Vector getResizeableStringArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableStringArray(path);
    }
    public static Vector getResizeableTransformArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableTransformArray(path);
    }
    public static Vector getResizeableVectorArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getResizeableVectorArray(path);
    }
    public static string_id getStringIdScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getStringId(path);
    }
    public static string_id[] getStringIdArrayScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getStringIdArray(path);
    }
    public static dictionary getDictionaryScriptVar(obj_id target, String path) throws InterruptedException
    {
        return target.getScriptVars().getDictionary(path);
    }
    public static boolean emptyContainer(obj_id target) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return false;
        }
        obj_id[] contents = getContents(target);
        if ((contents == null) || (contents.length == 0))
        {
            return false;
        }
        for (obj_id content : contents) {
            destroyObject(content);
        }
        return true;
    }
    public static boolean emptyContainerExceptStorytellerLoot(obj_id target) throws InterruptedException
    {
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            return false;
        }
        obj_id[] contents = getContents(target);
        if ((contents == null) || (contents.length == 0))
        {
            return false;
        }
        for (obj_id content : contents) {
            if (!utils.hasScriptVar(content, "storytellerLoot")) {
                destroyObject(content);
            }
        }
        return true;
    }
    public static location getLocationInArc(obj_id objPlayer, float fltStartAngle, float fltArcSize, float fltDistance) throws InterruptedException
    {
        location locHeading = getHeading(objPlayer);
        location locCenter = getLocation(objPlayer);
        locHeading.x = locHeading.x * fltDistance;
        locHeading.z = locHeading.z * fltDistance;
        locHeading.y = locCenter.y;
        locHeading.cell = locCenter.cell;
        locHeading.area = locCenter.area;
        float fltAngle = rand(fltStartAngle - fltArcSize, fltStartAngle + fltArcSize);
        location locNewHeading = rotatePointXZ(locHeading, fltAngle);
        locNewHeading.x = locCenter.x + locNewHeading.x;
        locNewHeading.z = locCenter.z + locNewHeading.z;
        return locNewHeading;
    }
    public static float getHeadingDegrees(obj_id objTarget) throws InterruptedException
    {
        location locStart = getLocation(objTarget);
        location locHeading = getHeading(objTarget);
        locHeading.x = locStart.x + locHeading.x;
        locHeading.z = locStart.z + locHeading.z;
        locHeading.y = locStart.y + locHeading.y;
        return thetaDegrees(locStart, locHeading);
    }
    public static float thetaDegrees(location direction) throws InterruptedException
    {
        return (float)Math.toDegrees(StrictMath.atan2(direction.x, direction.z));
    }
    public static float thetaDegrees(location start, location end) throws InterruptedException
    {
        location direction = new location();
        direction.x = end.x - start.x;
        direction.y = end.y - start.y;
        direction.z = end.z - start.z;
        return thetaDegrees(direction);
    }
    public static int countSubStringObjVars(obj_id[] objObjects, String strObjVar, String strSubString) throws InterruptedException
    {
        if (objObjects == null)
        {
            return 0;
        }
        int intCount = 0;
        String strString;
        for (obj_id objObject : objObjects) {
            strString = getStringObjVar(objObject, strObjVar);
            if (strString != null && strString.contains(strSubString))
                intCount++;
        }
        return intCount;
    }
    public static dictionary addObjVarToDictionary(obj_var ov, dictionary d, String basePath) throws InterruptedException
    {
        if (ov == null || d == null)
        {
            return d;
        }
        if (basePath == null)
        {
            basePath = "";
        }
        Object dta = ov.getData();
        if (dta == null)
        {
            return d;
        }
        String name = ov.getName();
        String path = basePath + name;
        if (dta instanceof Integer)
        {
            int iVal = ov.getIntData();
            int iCur = d.getInt(path);
            iVal += iCur;
            d.put(path, iVal);
        }
        else if (dta instanceof Float)
        {
            float fVal = ov.getFloatData();
            float fCur = d.getFloat(path);
            fVal += fCur;
            d.put(path, fVal);
        }
        else if (dta instanceof int[])
        {
            int[] iaVal = ov.getIntArrayData();
            int[] iaCur = d.getIntArray(path);
            if ((iaCur == null) || (iaCur.length == 0))
            {
                d.put(path, iaVal);
            }
            else 
            {
                if (iaVal.length == iaCur.length)
                {
                    for (int z = 0; z < iaVal.length; z++)
                    {
                        iaVal[z] += iaCur[z];
                    }
                    d.put(path, iaVal);
                }
            }
        }
        else if (dta instanceof float[])
        {
            float[] faVal = ov.getFloatArrayData();
            float[] faCur = d.getFloatArray(path);
            if ((faCur == null) || (faCur.length == 0))
            {
                d.put(path, faVal);
            }
            else 
            {
                if (faVal.length == faCur.length)
                {
                    for (int z = 0; z < faVal.length; z++)
                    {
                        faVal[z] += faCur[z];
                    }
                    d.put(path, faVal);
                }
            }
        }
        return d;
    }
    public static dictionary addObjVarToDictionary(obj_var ov, dictionary d) throws InterruptedException
    {
        return addObjVarToDictionary(ov, d, null);
    }
    public static dictionary addObjVarListToDictionary(obj_var_list ovl, dictionary d, String basePath) throws InterruptedException
    {
        if (ovl == null || d == null)
        {
            return d;
        }
        if (basePath == null)
        {
            basePath = "";
        }
        String path = basePath + ovl.getName();
        int numItems = ovl.getNumItems();
        obj_var ov;
        for (int i = 0; i < numItems; i++)
        {
            ov = ovl.getObjVar(i);
            if (ov != null)
            {
                if (ov instanceof obj_var_list)
                {
                    d = addObjVarListToDictionary((obj_var_list)(ov), d, path + ".");
                }
                else 
                {
                    d = addObjVarToDictionary(ov, d, path + ".");
                }
            }
        }
        return d;
    }
    public static dictionary addObjVarListToDictionary(obj_var_list ovl, dictionary d) throws InterruptedException
    {
        return addObjVarListToDictionary(ovl, d, null);
    }
    public static obj_id cloneObject(obj_id template, obj_id container) throws InterruptedException
    {
        obj_id clone = createObject(getTemplateName(template), container, "");
        if (!isIdValid(clone))
        {
            return null;
        }
        copyObjectData(template, clone);
        return clone;
    }
    public static obj_id cloneObject(obj_id template, location loc) throws InterruptedException
    {
        obj_id clone = createObject(getTemplateName(template), loc);
        if (!isIdValid(clone))
        {
            return null;
        }
        copyObjectData(template, clone);
        return clone;
    }
    public static void copyObjectData(obj_id template, obj_id clone) throws InterruptedException
    {
        obj_var_list ovl = getObjVarList(clone, "");
        if (ovl == null)
        {
            return;
        }
        int numItems = ovl.getNumItems();
        obj_var ov;
        for (int x = 0; x < numItems; x++)
        {
            ov = ovl.getObjVar(x);
            if (ov != null)
            {
                copyObjVar(template, clone, ov.getName());
            }
        }
    }
    public static boolean copyObjVar(obj_id target, String basePath, obj_var ov) throws InterruptedException
    {
        if (!isIdValid(target) || ov == null)
        {
            return false;
        }
        if (basePath == null)
        {
            basePath = "";
        }
        String path = basePath + ov.getName();
        Object dta = ov.getData();
        if (dta instanceof Integer)
        {
            return setObjVar(target, path, ov.getIntData());
        }
        else if (dta instanceof int[])
        {
            return setObjVar(target, path, ov.getIntArrayData());
        }
        else if (dta instanceof Float)
        {
            return setObjVar(target, path, ov.getFloatData());
        }
        else if (dta instanceof float[])
        {
            return setObjVar(target, path, ov.getFloatArrayData());
        }
        else if (dta instanceof Boolean)
        {
            return setObjVar(target, path, ov.getBooleanData());
        }
        else if (dta instanceof Boolean[])
        {
            return setObjVar(target, path, ov.getBooleanArrayData());
        }
        else if (dta instanceof String)
        {
            return setObjVar(target, path, ov.getStringData());
        }
        else if (dta instanceof String[])
        {
            return setObjVar(target, path, ov.getStringArrayData());
        }
        else if (dta instanceof obj_id)
        {
            return setObjVar(target, path, ov.getObjIdData());
        }
        else if (dta instanceof obj_id[])
        {
            return setObjVar(target, path, ov.getObjIdArrayData());
        }
        else if (dta instanceof location)
        {
            return setObjVar(target, path, ov.getLocationData());
        }
        else if (dta instanceof location[])
        {
            return setObjVar(target, path, ov.getLocationArrayData());
        }
        else if (dta instanceof string_id)
        {
            return setObjVar(target, path, ov.getStringIdData());
        }
        else if (dta instanceof string_id[])
        {
            return setObjVar(target, path, ov.getStringIdArrayData());
        }
        else if (dta instanceof attrib_mod)
        {
            return setObjVar(target, path, ov.getAttribModData());
        }
        else if (dta instanceof attrib_mod[])
        {
            return setObjVar(target, path, ov.getAttribModArrayData());
        }
        return false;
    }
    public static boolean[] removeObjVarList(obj_id object, String[] objVarList) throws InterruptedException
    {
        boolean[] result = new boolean[objVarList.length];
        for (int i = 0; i < objVarList.length; i++)
        {
            if (!hasObjVar(object, objVarList[i]))
            {
                result[i] = false;
            }
            else 
            {
                removeObjVar(object, objVarList[i]);
                result[i] = true;
            }
        }
        if (result.length == 0)
        {
            result[0] = false;
        }
        return result;
    }
    public static boolean saveDictionaryAsObjVar(obj_id object, String rootName, dictionary dict) throws InterruptedException
    {
        if (!isIdValid(object) || dict == null)
        {
            return false;
        }
        if (rootName == null)
        {
            rootName = "";
        }
        else if (rootName.length() > 0)
        {
            if (!Character.isLetterOrDigit(rootName.charAt(0)))
            {
                return false;
            }
            if (rootName.charAt(rootName.length() - 1) != '.')
            {
                rootName = rootName + ".";
            }
        }
        Enumeration keys = dict.keys();
        Object data;
        while (keys.hasMoreElements())
        {
            String key;
            try
            {
                key = (String)keys.nextElement();
            }
            catch(ClassCastException err)
            {
                return false;
            }
            data = dict.get(key);
            if (data instanceof Integer)
            {
                setObjVar(object, rootName + key, ((Integer)data).intValue());
            }
            else if (data instanceof Float)
            {
                setObjVar(object, rootName + key, ((Float)data).floatValue());
            }
            else if (data instanceof String)
            {
                setObjVar(object, rootName + key, (String)data);
            }
            else if (data instanceof int[])
            {
                setObjVar(object, rootName + key, (int[])data);
            }
            else if (data instanceof float[])
            {
                setObjVar(object, rootName + key, (float[])data);
            }
            else if (data instanceof dictionary)
            {
                saveDictionaryAsObjVar(object, rootName + key, (dictionary)data);
            }
        }
        return true;
    }
    public static String getFactionSubString(String strSearchString) throws InterruptedException
    {
        if (strSearchString == null)
        {
            return null;
        }
        if(toLower(strSearchString).contains(toLower(factions.FACTION_IMPERIAL)))
            return factions.FACTION_IMPERIAL;
        else if(toLower(strSearchString).contains(toLower(factions.FACTION_REBEL)))
            return factions.FACTION_REBEL;
        return null;
    }
    public static boolean setBatchObjVar(obj_id target, String base_path, Object[] array) throws InterruptedException
    {
        if (!isIdValid(target) || base_path == null || base_path.equals("") || array == null || array.length == 0)
        {
            return false;
        }

        removeObjVar(target, base_path);

        int BatchSize = 10;
        boolean litmus = true;
        int n = 0;
        Vector toSet = new Vector();
        for (Object anArray : array) {
            toSet.add(anArray);
            if (toSet.size() >= BatchSize) {
                litmus &= setObjectArrayObjVar(target, base_path + "." + n, toSet);
                toSet.clear();
                n++;
            }
        }
        litmus &= setObjectArrayObjVar(target, base_path + "." + n, toSet);
        return litmus;
    }
    public static void removeBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target) || base_path == null || base_path.equals("") || !hasObjVar(target, base_path))
        {
            return;
        }

        removeObjVar(target, base_path);

        obj_var_list list = getObjVarList(target, null);
        if (list == null)
        {
            return;
        }
        int intQ = 0;
        for (int i = 0; i < list.getNumItems(); i++)
        {
            if (hasObjVar(target, base_path + "." + intQ))
            {
                removeObjVar(target, base_path + "." + intQ);
                intQ++;
            }
        }
    }
    public static boolean setBatchObjVar(obj_id target, String base_path, int[] array) throws InterruptedException
    {
        if (!isIdValid(target) || (base_path == null) || (base_path.equals("")) || (array == null) || (array.length == 0))
        {
            return false;
        }

        removeObjVar(target, base_path);

        int BatchSize = 10;
        boolean litmus = true;
        int n = 0;
        Vector toSet = new Vector();
        for (int anArray : array) {
            toSet.add(anArray);
            if (toSet.size() >= BatchSize) {
                litmus &= setObjectArrayObjVar(target, base_path + "." + n, toSet);
                toSet.clear();
                n++;
            }
        }
        litmus &= setObjectArrayObjVar(target, base_path + "." + n, toSet);
        return litmus;
    }
    public static boolean setResizeableBatchObjVar(obj_id target, String base_path, Vector vector) throws InterruptedException
    {
        if (!isIdValid(target) || (base_path == null) || (base_path.equals("")) || (vector == null) || (vector.size() == 0))
        {
            return false;
        }

        removeObjVar(target, base_path);

        int BatchSize = 10;
        boolean litmus = true;
        int n = 0;
        Vector toSet = new Vector();
        for (Object aVector : vector) {
            toSet.add(aVector);
            if (toSet.size() >= BatchSize) {
                litmus &= setObjectArrayObjVar(target, base_path + "." + n, toSet);
                toSet.clear();
                n++;
            }
        }
        litmus &= setObjectArrayObjVar(target, base_path + "." + n, toSet);
        return litmus;
    }
    public static boolean setObjectArrayObjVar(obj_id target, String path, Vector vec) throws InterruptedException
    {
        if (!isIdValid(target) || (path == null) || (path.equals("")) || (vec == null) || (vec.size() == 0))
        {
            return false;
        }
        int size = vec.size();
        Object test = vec.elementAt(0);
        if (test instanceof String)
        {
            return setObjVar(target, path, vec.toArray(new String[size]));
        }
        else if (test instanceof string_id)
        {
            return setObjVar(target, path, vec.toArray(new string_id[size]));
        }
        else if (test instanceof obj_id)
        {
            return setObjVar(target, path, vec.toArray(new obj_id[size]));
        }
        else if (test instanceof attrib_mod)
        {
            return setObjVar(target, path, vec.toArray(new attrib_mod[size]));
        }
        else if (test instanceof location)
        {
            return setObjVar(target, path, vec.toArray(new location[size]));
        }
        else if (test instanceof Integer)
        {
            int[] tmp = new int[size];
            for (int i = 0; i < vec.size(); i++)
            {
                tmp[i] = (Integer) (vec.get(i));
            }
            return setObjVar(target, path, tmp);
        }
        return false;
    }
    public static String[] getStringBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target) || (base_path == null) || (base_path.equals("")) || !hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            String[] tmp = getStringArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            String[] _ret = new String[0];
            _ret = new String[ret.size()];
            ret.toArray(_ret);
            return _ret;
        }
        return null;
    }
    public static Vector getResizeableStringBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target) || (base_path == null) || (base_path.equals("")) || !hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            String[] tmp = getStringArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static string_id[] getStringIdBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            string_id[] tmp = getStringIdArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            string_id[] _ret = new string_id[0];
            _ret = new string_id[ret.size()];
            ret.toArray(_ret);
            return _ret;
        }
        return null;
    }
    public static obj_id[] getObjIdBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            obj_id[] tmp = getObjIdArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            obj_id[] _ret = new obj_id[0];
            _ret = new obj_id[ret.size()];
            ret.toArray(_ret);
            return _ret;
        }
        return null;
    }
    public static Vector getResizeableObjIdBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        obj_id[] tmp;
        for (int i = 0; i < numItems; i++)
        {
            tmp = getObjIdArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static int[] getIntBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            int[] tmp = getIntArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            int[] _ret = new int[0];
            _ret = new int[ret.size()];
            for (int _i = 0; _i < ret.size(); ++_i)
			{
				_ret[_i] = (Integer) ret.get(_i);
			}
            return _ret;
        }
        return null;
    }
    public static Vector getResizeableIntBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            int[] tmp = getIntArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static attrib_mod[] getAttribModBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        int numItems = ovl.getNumItems();
        attrib_mod[] tmp;
        for (int i = 0; i < numItems; i++)
        {
            tmp = getAttribModArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            attrib_mod[] _ret = new attrib_mod[0];
            _ret = new attrib_mod[ret.size()];
            ret.toArray(_ret);
            return _ret;
        }
        return null;
    }
    public static location[] getLocationBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasObjVar(target, base_path))
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        obj_var_list ovl = getObjVarList(target, base_path);
        if ((ovl == null) || (ovl.getNumItems() == 0))
        {
            return null;
        }
        int numItems = ovl.getNumItems();
        location[] tmp;
        for (int i = 0; i < numItems; i++)
        {
            tmp = getLocationArrayObjVar(target, base_path + "." + i);
            if ((tmp != null) && (tmp.length > 0))
            {
                ret = concatArrays(ret, tmp);
            }
        }
        if ((ret != null) && (ret.size() > 0))
        {
            location[] _ret = new location[0];
            _ret = new location[ret.size()];
            ret.toArray(_ret);
            return _ret;
        }
        return null;
    }
    public static boolean hasStringBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getStringBatchObjVar(target, base_path) != null;
    }
    public static boolean hasResizeableStringBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableStringBatchObjVar(target, base_path) != null;
    }
    public static boolean hasStringIdBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getStringIdBatchObjVar(target, base_path) != null;
    }
    public static boolean hasObjIdBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getObjIdBatchObjVar(target, base_path) != null;
    }
    public static boolean hasResizeableObjIdBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableObjIdBatchObjVar(target, base_path) != null;
    }
    public static boolean hasIntBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getIntBatchObjVar(target, base_path) != null;
    }
    public static boolean hasResizeableIntBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableIntBatchObjVar(target, base_path) != null;
    }
    public static boolean hasAttribModBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getAttribModBatchObjVar(target, base_path) != null;
    }
    public static boolean hasLocationBatchObjVar(obj_id target, String base_path) throws InterruptedException
    {
        return getLocationBatchObjVar(target, base_path) != null;
    }
    public static boolean setBatchScriptVar(obj_id target, String base_path, Vector array) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return false;
        }
        if ((array == null) || (array.size() == 0))
        {
            return false;
        }
        deltadictionary dd = target.getScriptVars();
        int BatchSize = 10;
        int n = 0;
        Vector toSet = new Vector();
        for (int i = 0; i < array.size(); i++)
        {
            toSet.add(array.elementAt(i));
            if (toSet.size() >= BatchSize)
            {
                dd.put(base_path + "." + n, toSet);
                toSet.clear();
                n++;
            }
        }
        if (toSet.size() > 0)
        {
            dd.put(base_path + "." + n, toSet);
            n++;
        }
        dd.put(base_path, n);
        return true;
    }
    public static boolean setBatchScriptVar(obj_id target, String base_path, obj_id[] array) throws InterruptedException
    {
        if ((array == null) || (array.length == 0))
        {
            return false;
        }
        Vector vArray = new Vector(Arrays.asList(array));
        return setBatchScriptVar(target, base_path, vArray);
    }
    public static boolean setBatchScriptVar(obj_id target, String base_path, String[] array) throws InterruptedException
    {
        if ((array == null) || (array.length == 0))
        {
            return false;
        }
        Vector vArray = new Vector(Arrays.asList(array));
        return setBatchScriptVar(target, base_path, vArray);
    }
    public static boolean setBatchScriptVar(obj_id target, String base_path, string_id[] array) throws InterruptedException
    {
        if ((array == null) || (array.length == 0))
        {
            return false;
        }
        Vector vArray = new Vector(Arrays.asList(array));
        return setBatchScriptVar(target, base_path, vArray);
    }
    public static boolean setBatchScriptVar(obj_id target, String base_path, location[] array) throws InterruptedException
    {
        if ((array == null) || (array.length == 0))
        {
            return false;
        }
        Vector vArray = new Vector(Arrays.asList(array));
        return setBatchScriptVar(target, base_path, vArray);
    }
    public static Vector getResizeableObjIdBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasScriptVar(target, base_path))
        {
            return null;
        }
        int cnt = getIntScriptVar(target, base_path);
        Vector ret = new Vector();
        Vector tmp;
        for (int i = 0; i < cnt; i++)
        {
            tmp = target.getScriptVars().getResizeableObjIdArray(base_path + "." + i);
            if ((tmp != null) && (tmp.size() > 0))
            {
                ret.addAll(tmp);
            }
        }
        if ((ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static obj_id[] getObjIdBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        Vector ret = getResizeableObjIdBatchScriptVar(target, base_path);
        if ((ret != null) && (ret.size() > 0))
        {
            return toStaticObjIdArray(ret);
        }
        return null;
    }
    public static Vector getResizeableStringBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasScriptVar(target, base_path))
        {
            return null;
        }
        int cnt = getIntScriptVar(target, base_path);
        Vector ret = new Vector();
        for (int i = 0; i < cnt; i++)
        {
            Vector tmp = target.getScriptVars().getResizeableStringArray(base_path + "." + i);
            if ((tmp != null) && (tmp.size() > 0))
            {
                ret.addAll(tmp);
            }
        }
        if ((ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static String[] getStringBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        Vector ret = getResizeableStringBatchScriptVar(target, base_path);
        if ((ret != null) && (ret.size() > 0))
        {
            return toStaticStringArray(ret);
        }
        return null;
    }
    public static Vector getResizeableLocationBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasScriptVar(target, base_path))
        {
            return null;
        }
        int cnt = getIntScriptVar(target, base_path);
        Vector ret = new Vector();
        Vector tmp;
        for (int i = 0; i < cnt; i++)
        {
            tmp = target.getScriptVars().getResizeableLocationArray(base_path + "." + i);
            if ((tmp != null) && (tmp.size() > 0))
            {
                ret.addAll(tmp);
            }
        }
        if ((ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static Vector getResizeableIntBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return null;
        }
        if (!hasScriptVar(target, base_path))
        {
            return null;
        }
        int cnt = getIntScriptVar(target, base_path);
        Vector ret = new Vector();
        Vector tmp;
        for (int i = 0; i < cnt; i++)
        {
            tmp = target.getScriptVars().getResizeableIntArray(base_path + "." + i);
            if ((tmp != null) && (tmp.size() > 0))
            {
                ret.addAll(tmp);
            }
        }
        if ((ret.size() > 0))
        {
            return ret;
        }
        return null;
    }
    public static location[] getLocationBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        Vector ret = getResizeableLocationBatchScriptVar(target, base_path);
        if ((ret != null) && (ret.size() > 0))
        {
            return toStaticLocationArray(ret);
        }
        return null;
    }
    public static int[] getIntBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        Vector ret = getResizeableIntBatchScriptVar(target, base_path);
        if ((ret != null) && (ret.size() > 0))
        {
            int[] _ret = new int[ret.size()];
            for (int _i = 0; _i < ret.size(); ++_i)
            {
                _ret[_i] = (Integer) ret.get(_i);
            }
            return _ret;
        }
        return null;
    }
    public static void removeBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if ((base_path == null) || (base_path.equals("")))
        {
            return;
        }
        if (!hasScriptVar(target, base_path))
        {
            return;
        }
        int cnt = getIntScriptVar(target, base_path);
        deltadictionary dd = target.getScriptVars();
        dd.remove(base_path);
        for (int i = 0; i < cnt; i++)
        {
            dd.remove(base_path + "." + i);
        }
    }
    public static boolean hasResizeableObjIdBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableObjIdBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasObjIdBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getObjIdBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasResizeableStringBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableStringBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasStringBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getStringBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasResizeableLocationBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableLocationBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasResizeableIntBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getResizeableIntBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasLocationBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getLocationBatchScriptVar(target, base_path) != null;
    }
    public static boolean hasIntBatchScriptVar(obj_id target, String base_path) throws InterruptedException
    {
        return getIntBatchScriptVar(target, base_path) != null;
    }
    public static int getIntConfigSetting(String section, String key)
    {
        String setting = getConfigSetting(section, key);
        if (setting == null || setting.length() == 0)
        {
            return 0;
        }
        try
        {
            return Integer.parseInt(setting);
        }
        catch(NumberFormatException ignored)
        {
        }
        return 0;
    }
    public static float getFloatConfigSetting(String section, String key)
    {
        String setting = getConfigSetting(section, key);
        if (setting == null || setting.length() == 0)
        {
            return 0;
        }
        try
        {
            return Float.parseFloat(setting);
        }
        catch(NumberFormatException ignored)
        {
        }
        return 0;
    }
    public static boolean checkConfigFlag(String strSection, String strConfigSetting)
    {
        String strTest = toLower(getConfigSetting(strSection, strConfigSetting));
        return (strTest != null && (strTest.equals("true") || strTest.equals("1")));
    }
    public static boolean checkServerSpawnLimits() throws InterruptedException
    {
        final int intNumPlayers = getNumPlayers();
        // nobody is playing... spawn away Merrill...
        if(intNumPlayers <= 0) return true;
        final int intServerSpawnLimit = getServerSpawnLimit();
        final int intNumCreatures = utils.getNumCreaturesForSpawnLimit();

        if (intServerSpawnLimit > 0)
        {
            return intNumCreatures <= intServerSpawnLimit;
        }
        else
        {
            if (intNumPlayers < 200000)
            {
                return intNumCreatures <= 5000;
            }
            else
            {
                float fltRatio = (intNumCreatures / intNumPlayers);
                return !(fltRatio > 10.0f);
            }
        }
    }
    public static String formatTimeVerbose(int seconds) throws InterruptedException
    {
        if (seconds < 1)
        {
            return "";
        }
        int[] convert_time = player_structure.convertSecondsTime(seconds);
        int idx = 0;
        for (int i = 0; i < convert_time.length; i++)
        {
            if (convert_time[i] > 0)
            {
                idx = i;
                break;
            }
        }
        if (idx == convert_time.length - 1)
        {
            idx--;
        }
        String time_str = "";
        String[] verboseIndex = 
        {
            " days ",
            " hours ",
            " minutes ",
            " seconds "
        };
        for (int n = idx; n < convert_time.length; n++)
        {
            time_str += convert_time[n] + verboseIndex[n];
            if (n < convert_time.length - 1)
            {
                time_str += ", ";
            }
        }
        if (!time_str.equals(""))
        {
            return time_str;
        }
        return "";
    }
    public static String formatTimeVerboseNoSpaces(int seconds) throws InterruptedException
    {
        if (seconds < 1)
        {
            return "";
        }
        int[] convert_time = player_structure.convertSecondsTime(seconds);
        int idx = 0;
        for (int i = 0; i < convert_time.length; i++)
        {
            if (convert_time[i] > 0)
            {
                idx = i;
                break;
            }
        }
        if (idx == convert_time.length - 1)
        {
            idx--;
        }
        String time_str = "";
        String[] verboseIndex = 
        {
            " days",
            " hours",
            " minutes",
            " seconds"
        };
        for (int n = idx; n < convert_time.length; n++)
        {
            time_str += convert_time[n] + verboseIndex[n];
            if (n < convert_time.length - 1)
            {
                time_str += ", ";
            }
        }
        return time_str;
    }
    public static String formatTime(int seconds) throws InterruptedException
    {
        if (seconds < 1)
        {
            return "";
        }

        int[] convert_time = player_structure.convertSecondsTime(seconds);
        if(convert_time == null) return "";

        int idx = 0;
        for (int i = 0; i < convert_time.length; i++)
        {
            if (convert_time[i] > 0)
            {
                idx = i;
                break;
            }
        }
        if (idx == convert_time.length - 1)
        {
            idx--;
        }
        String time_str = "";
        for (int n = idx; n < convert_time.length; n++)
        {
            time_str += convert_time[n];
            if (n < convert_time.length - 1)
            {
                time_str += ":";
            }
        }
        return time_str;
    }
    public static String formatTime(float fTime) throws InterruptedException
    {
        int iTime = (int)fTime;
        float diff = fTime - iTime;
        int decimals = (int)(diff * 100);
        if (iTime > 0)
        {
            String sTime = formatTime(iTime);
            sTime += "." + decimals;
            return sTime;
        }
        else 
        {
            return "0." + decimals;
        }
    }
    public static String padTimeDHMS(int seconds) throws InterruptedException
    {
        if (seconds < 0)
        {
            return null;
        }
        int days = seconds / (3600 * 24);
        String daysText = "" + days;
        if (daysText.length() <= 4)
        {
            daysText = ("0000").substring(0, 4 - daysText.length()) + daysText;
        }
        seconds = seconds % (3600 * 24);
        int hours = seconds / 3600;
        String hoursText = "" + hours;
        if (hoursText.length() <= 2)
        {
            hoursText = ("00").substring(0, 2 - hoursText.length()) + hoursText;
        }
        seconds = seconds % 3600;
        int minutes = seconds / 60;
        String minutesText = "" + minutes;
        if (minutesText.length() <= 2)
        {
            minutesText = ("00").substring(0, 2 - minutesText.length()) + minutesText;
        }
        seconds = seconds % 60;
        String secondsText = "" + seconds;
        if (secondsText.length() <= 2)
        {
            secondsText = ("00").substring(0, 2 - secondsText.length()) + secondsText;
        }
        return daysText + "d:" + hoursText + "h:" + minutesText + "m:" + secondsText + "s";
    }
    public static String padTimeHM(int seconds) throws InterruptedException
    {
        seconds = seconds % (3600 * 24);
        int hours = seconds / 3600;
        String hoursText = "" + hours;
        if (hoursText.length() <= 2)
        {
            hoursText = ("00").substring(0, 2 - hoursText.length()) + hoursText;
        }
        seconds = seconds % 3600;
        int minutes = seconds / 60;
        String minutesText = "" + minutes;
        if (minutesText.length() <= 2)
        {
            minutesText = ("00").substring(0, 2 - minutesText.length()) + minutesText;
        }
        return hoursText + "h:" + minutesText + "m";
    }
    public static void sendDelayedSystemMessage(obj_id target, string_id sid, float delay) throws InterruptedException
    {
        if (!isIdValid(target) || (sid == null) || (delay < 0.0f))
        {
            return;
        }
        dictionary msg = new dictionary();
        msg.put("sidMsg", sid);
        messageTo(target, "handleDelayedSystemMessage", msg, delay, false);
    }
    public static void sendDelayedSystemMessage(obj_id target, String sid, float delay) throws InterruptedException
    {
        if (!isIdValid(target) || (sid == null) || sid.equals("") || (delay < 0.0f))
        {
            return;
        }
        dictionary msg = new dictionary();
        msg.put("stringMsg", sid);
        messageTo(target, "handleDelayedSystemMessage", msg, delay, false);
    }
    public static void sendDelayedProseMessage(obj_id msgTarget, string_id sid, obj_id actor, String actorString, string_id actorStringId, obj_id target, String targetString, string_id targetStringId, obj_id other, String otherString, string_id otherStringId, int di, float df, float delay) throws InterruptedException
    {
        if (!isIdValid(msgTarget) || (sid == null) || (delay < 0.0f))
        {
            return;
        }
        dictionary msg = new dictionary();
        msg.put("sid", sid);
        msg.put("actor", actor);
        msg.put("actorString", actorString);
        msg.put("actorStringId", actorStringId);
        msg.put("target", target);
        msg.put("targetString", targetString);
        msg.put("targetStringId", targetStringId);
        msg.put("other", other);
        msg.put("otherString", otherString);
        msg.put("otherStringId", otherStringId);
        msg.put("di", di);
        msg.put("df", df);
        messageTo(msgTarget, "handleDelayedProseMessage", msg, delay, false);
    }
    public static Vector alphabetizeStringArray(String[] array) throws InterruptedException
    {
        if ((array == null) || (array.length == 0))
        {
            return null;
        }
        Vector tmp = new Vector();
        java.text.Collator myCol = java.text.Collator.getInstance();
        for (String anArray : array) {
            boolean inserted = false;
            for (int n = 0; n < tmp.size(); n++) {
                if (myCol.compare(anArray, tmp.elementAt(n)) < 0) {
                    tmp.add(n, anArray);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                tmp.add(anArray);
            }
        }
        if (tmp.size() == 0)
        {
            return null;
        }
        return tmp;
    }
    public static Vector alphabetizeStringArray(Vector array) throws InterruptedException
    {
        if ((array == null) || (array.size() == 0))
        {
            return null;
        }
        String[] toPass = toStaticStringArray(array);
        if ((toPass == null) || (toPass.length == 0))
        {
            return null;
        }
        return alphabetizeStringArray(toPass);
    }
    public static String getPlayerSpeciesName(int species) throws InterruptedException
    {
        switch (species)
        {
            case SPECIES_HUMAN:
                return "human";
            case SPECIES_BOTHAN:
                return "bothan";
            case SPECIES_RODIAN:
                return "rodian";
            case SPECIES_TWILEK:
                return "twilek";
            case SPECIES_TRANDOSHAN:
                return "trandoshan";
            case SPECIES_MON_CALAMARI:
                return "moncalamari";
            case SPECIES_WOOKIEE:
                return "wookiee";
            case SPECIES_ZABRAK:
                return "zabrak";
            case SPECIES_ITHORIAN:
                return "ithorian";
            case SPECIES_SULLUSTAN:
                return "sullustan";
            default:
                return "unknown";
        }
    }
    public static void addListener(String strObjVar, obj_id objListener, obj_id objTarget) throws InterruptedException
    {
        if (strObjVar == null)
        {
            LOG("DESIGNER_FATAL", "Null objVar passed into addListener");
            return;
        }
        if (!isIdValid(objTarget))
        {
            LOG("DESIGNER_FATAL", "Null owner passed into addListener");
            return;
        }
        if (!isIdValid(objListener))
        {
            LOG("DESIGNER_FATAL", "Null listener passed into addListener");
            return;
        }
        dictionary dctParams = new dictionary();
        dctParams.put("objListener", objListener);
        dctParams.put("strObjVar", strObjVar);
        messageTo(objTarget, "addListener", dctParams, 0, true);
    }
    public static void removeListener(String strObjVar, obj_id objListener, obj_id objTarget) throws InterruptedException
    {
        if (strObjVar == null)
        {
            LOG("DESIGNER_FATAL", "Null objVar passed into removeListener");
            return;
        }
        if (!isIdValid(objTarget))
        {
            LOG("DESIGNER_FATAL", "Null target passed into removeListener");
            return;
        }
        if (!isIdValid(objListener))
        {
            LOG("DESIGNER_FATAL", "Null listener passed into removeListener");
            // no return here as we need to let this remove any null listeners anyway.
        }
        dictionary dctParams = new dictionary();
        dctParams.put("objListener", objListener);
        dctParams.put("strObjVar", strObjVar);
        messageTo(objTarget, "removeListener", dctParams, 0, true);
    }
    public static void messageListeners(String strObjVar, obj_id objOwner, String strMessageName, dictionary dctParams) throws InterruptedException
    {
        if (strObjVar == null)
        {
            LOG("DESIGNER_FATAL", "Null objVar passed into messageListeners");
            return;
        }
        if (!isIdValid(objOwner))
        {
            LOG("DESIGNER_FATAL", "Null owner passed into messageListeners");
            return;
        }
        if (strMessageName == null)
        {
            LOG("DESIGNER_FATAL", "Null messageName passed into messageListeners");
            return;
        }
        if (hasObjVar(objOwner, strObjVar))
        {
            int intI = 0;
            obj_id objListeners[] = getObjIdArrayObjVar(objOwner, "mission.objListeners");
            while (intI < objListeners.length)
            {
                messageTo(objListeners[intI], strMessageName, dctParams, 0, true);
                intI = intI + 1;
            }
        }
    }
    public static int getTheaterSize(String strLairType) throws InterruptedException
    {
        if (isTheater(strLairType))
        {
            if (strLairType.contains("medium"))
            {
                return 40;
            }
            if (strLairType.contains("small"))
            {
                return 24;
            }
            if (strLairType.contains("large"))
            {
                return 72;
            }
            return 24;
        }
        return -1;
    }
    public static boolean isTheater(String strLairType) throws InterruptedException
    {
        return strLairType != null && strLairType.contains("theater");
    }
    public static boolean isAppropriateName(String name) throws InterruptedException
    {
        if (name == null || name.equals(""))
        {
            return false;
        }
        java.util.StringTokenizer st = new java.util.StringTokenizer(name);
        while (st.hasMoreTokens())
        {
            String tkn = st.nextToken();
            if (stringToFloat(tkn) == Float.NEGATIVE_INFINITY)
            {
                if (isObscene(tkn) || !isAppropriateText(tkn))
                {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean setNonProfaneName(obj_id target, String name) throws InterruptedException
    {
        if (!isIdValid(target) || name == null || name.equals("") || !isAppropriateName(name))
        {
            return false;
        }
        return setName(target, name);
    }
    public static boolean setNonReservedName(obj_id target, String name) throws InterruptedException {
        if (!isIdValid(target) || name == null || name.equals("")) {
            return false;
        }
        return !isNameReserved(name) && setName(target, name);
    }
    public static boolean setFilteredName(obj_id target, String name) throws InterruptedException {
        if (!isIdValid(target) || name == null || name.equals("")) {
            return false;
        }
        return !isNameReserved(name) && setNonProfaneName(target, name);
    }
    public static void destroyObjects(obj_id[] objects) throws InterruptedException
    {
        if (objects == null || objects.length == 0)
        {
            return;
        }
        for (obj_id object : objects) {
            if (isIdValid(object)) {
                destroyObject(object);
            }
        }
    }
    public static String getTemplateFilenameNoPath(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        String fullPath = getTemplateName(target);
        if (fullPath != null && !fullPath.equals(""))
        {
            String[] tmp = split(fullPath, '/');
            if (tmp != null && tmp.length > 0)
            {
                return tmp[tmp.length - 1];
            }
        }
        return null;
    }
    public static int getFirstNonValidIdIndex(obj_id[] ids) throws InterruptedException
    {
        if (ids == null || ids.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < ids.length; i++)
        {
            if (!isIdValid(ids[i]))
            {
                return i;
            }
        }
        return -1;
    }
    public static int getFirstValidIdIndex(obj_id[] ids) throws InterruptedException
    {
        if (ids == null || ids.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < ids.length; i++)
        {
            if (isIdValid(ids[i]))
            {
                return i;
            }
        }
        return -1;
    }
    public static void moneyInMetric(obj_id objTransferer, String strAccount, int intAmount) throws InterruptedException
    {
        logBalance("moneyIn;" + getGameTime() + ";" + objTransferer + ";" + strAccount + ";" + intAmount);
    }
    public static void moneyOutMetric(obj_id objTransferer, String strAccount, int intAmount) throws InterruptedException
    {
        logBalance("moneyOut;" + getGameTime() + ";" + objTransferer + ";" + strAccount + ";" + intAmount);
    }
    public static int getValidAttributeIndex(String[] array) throws InterruptedException
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == null || array[i].equals(""))
            {
                return i;
            }
        }
        return -1;
    }
    public static String getStringName(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        String name = getEncodedName(target);
        string_id sid_name = unpackString(name);
        if (sid_name != null)
        {
            name = getString(sid_name);
        }
        return name;
    }
    public static void warpPlayer(obj_id player, location dest) throws InterruptedException
    {
        if (isIdValid(dest.cell))
        {
            warpPlayer(player, dest.area, 0.0f, 0.0f, 0.0f, dest.cell, dest.x, dest.y, dest.z);
        }
        else 
        {
            warpPlayer(player, dest.area, dest.x, dest.y, dest.z, null, 0.0f, 0.0f, 0.0f);
        }
    }
    public static void warpPlayer(obj_id player, String planet, location dest) throws InterruptedException
    {
        warpPlayer(player, planet, dest.x, dest.y, dest.z, null, 0.0f, 0.0f, 0.0f);
    }
    public static boolean copyObjVarList(obj_id from, obj_id to, String listpath) throws InterruptedException
    {
        if (!isIdValid(from) || !isIdValid(to) || listpath == null || listpath.equals(""))
        {
            return false;
        }
        obj_var_list ovl = getObjVarList(from, listpath);
        if (ovl == null)
        {
            return false;
        }
        boolean litmus = true;
        int numItems = ovl.getNumItems();
        for (int i = 0; i < numItems; i++)
        {
            obj_var ov = ovl.getObjVar(i);
            if (ov != null)
            {
                litmus &= copyObjVar(from, to, listpath + "." + ov.getName());
            }
        }
        return litmus;
    }
    public static string_id getCardinalDirectionForPoints(location locTest1, location locTest2) throws InterruptedException
    {
        String cardinalString = getStringCardinalDirection(locTest1, locTest2);
        if (cardinalString == null || cardinalString.equals(""))
        {
            return null;
        }
        return new string_id("mission/mission_generic", cardinalString);
    }
    public static String getStringCardinalDirection(location locTest1, location locTest2, boolean abbreviated) throws InterruptedException
    {
        if (locTest1 == null || locTest2 == null)
        {
            return null;
        }
        float fltAngle = getHeadingToLocation(locTest1, locTest2);
        if ((fltAngle >= -22.5) && (fltAngle <= 22.5))
        {
            if (!abbreviated)
            {
                return "north";
            }
            else 
            {
                return "N";
            }
        }
        else if ((fltAngle >= -67.5) && (fltAngle <= -22.5))
        {
            if (!abbreviated)
            {
                return "northeast";
            }
            else 
            {
                return "NE";
            }
        }
        else if ((fltAngle >= -112.5) && (fltAngle <= -67.5))
        {
            if (!abbreviated)
            {
                return "east";
            }
            else 
            {
                return "E";
            }
        }
        else if ((fltAngle >= -157.5) && (fltAngle <= -112.5))
        {
            if (!abbreviated)
            {
                return "southeast";
            }
            else 
            {
                return "SE";
            }
        }
        else if ((fltAngle >= 22.5) && (fltAngle <= 67.5))
        {
            if (!abbreviated)
            {
                return "northwest";
            }
            else 
            {
                return "NW";
            }
        }
        else if ((fltAngle >= 67.5) && (fltAngle <= 112.5))
        {
            if (!abbreviated)
            {
                return "west";
            }
            else 
            {
                return "W";
            }
        }
        else if ((fltAngle >= 112.5) && (fltAngle <= 157.5))
        {
            if (!abbreviated)
            {
                return "southwest";
            }
            else 
            {
                return "SW";
            }
        }
        else 
        {
            if (!abbreviated)
            {
                return "south";
            }
            else 
            {
                return "S";
            }
        }
    }
    public static String getStringCardinalDirection(location locTest1, location locTest2) throws InterruptedException
    {
        return getStringCardinalDirection(locTest1, locTest2, false);
    }
    public static boolean isContainer(obj_id target) throws InterruptedException {
        return isIdValid(target) && (getContainerType(target) != 0);
    }
    public static boolean noIncapDrainAttributes(obj_id target, int actionCost, int mindCost) throws InterruptedException {
        if (!isIdValid(target) || actionCost < 0) {
            return false;
        }
        return getAttrib(target, ACTION) >= actionCost && drainAttributes(target, actionCost, 0);
    }
    public static int getUnbuffedWoundedMaxAttrib(obj_id target, int attrib) throws InterruptedException
    {
        if (!isIdValid(target) || attrib < HEALTH || attrib > WILLPOWER)
        {
            return -1;
        }
        return getUnmodifiedMaxAttrib(target, attrib);
    }
    public static boolean validatePlayerHairStyle(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id hair = getObjectInSlot(player, "hair");
        if (!isIdValid(hair))
        {
            return false;
        }
        String hair_table = "datatables/customization/hair_assets_skill_mods.iff";
        int idx = dataTableSearchColumnForString(getTemplateName(hair), "SERVER_TEMPLATE", hair_table);
        if (idx < 0)
        {
            return false;
        }
        String required_template = dataTableGetString(hair_table, idx, "SERVER_PLAYER_TEMPLATE");
        if (required_template == null)
        {
            return false;
        }
        String player_template = getTemplateName(player);
        if (!player_template.equals(required_template))
        {
            String new_hair_template = dataTableGetString(hair_table, dataTableSearchColumnForString(player_template, "SERVER_PLAYER_TEMPLATE", hair_table), "SERVER_TEMPLATE");
            if (new_hair_template == null)
            {
                CustomerServiceLog("imageDesigner", getFirstName(player) + "(" + player + ") has an invalid hairstyle, but a replacement cannot be found!");
                return false;
            }
            destroyObject(hair);
            createObject(new_hair_template, player, "hair");
            CustomerServiceLog("imageDesigner", getFirstName(player) + "(" + player + ") has an invalid hairstyle. It has been replaced with a default style.");
            sendSystemMessageTestingOnly(player, "An illegal hairstyle has been detected on your character. This has been corrected.");
        }
        return true;
    }
    public static String getPackedScripts(obj_id objObject) throws InterruptedException
    {
        String strTest = "";
        String[] strScripts = getScriptList(objObject);
        if (strScripts.length > 0)
        {
            for (int intI = 0; intI < strScripts.length; intI++)
            {
                strTest += strScripts[intI];
                if (intI < strScripts.length - 1)
                {
                    strTest += ",";
                }
            }
        }
        return strTest;
    }
    public static String[] getUsableScriptList(obj_id objObject) throws InterruptedException
    {
        String[] strScripts = getScriptList(objObject);
        debugSpeakMsg(objObject, "script list is " + strScripts.length);
        if (strScripts.length > 0)
        {
            debugSpeakMsg(objObject, "list length is " + strScripts.length);
            String[] strCorrectArray = new String[strScripts.length];
            String script;
            for (int intI = 0; intI < strScripts.length; intI++)
            {
                script = strScripts[intI];
                if (script.contains("script."))
                {
                    script = script.substring(7);
                    debugSpeakMsg(objObject, "setting array to  " + script);
                    strCorrectArray[intI] = script;
                }
            }
            return strCorrectArray;
        }
        return null;
    }
    public static String[] unpackScriptString(String strScripts) throws InterruptedException
    {
        return split(strScripts, ',');
    }
    public static boolean isEquipped(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        obj_id containedBy = getContainedBy(item);
        return (isIdValid(containedBy) && getContainerType(containedBy) == 1);
    }
    public static obj_id unequipWeaponHand(obj_id player) throws InterruptedException
    {
        return unequipSlot(player, "hold_r");
    }
    public static obj_id unequipOffHand(obj_id player) throws InterruptedException
    {
        return unequipSlot(player, "hold_l");
    }
    public static obj_id unequipSlot(obj_id player, String slot) throws InterruptedException
    {
        obj_id equipment = getObjectInSlot(player, slot);
        if (isIdValid(equipment))
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            if (isIdValid(playerInv))
            {
                putInOverloaded(equipment, playerInv);
                return equipment;
            }
        }
        return obj_id.NULL_ID;
    }
    public static obj_id[] getAttackableTargetsInRadius(obj_id attacker, int radius, boolean player_targets) throws InterruptedException
    {
        location loc = getLocation(attacker);
        if (loc == null)
        {
            return null;
        }
        obj_id[] objects = getObjectsInRange(loc, radius);
        Vector attackable_targets = new Vector();
        attackable_targets.setSize(0);
        for (obj_id object : objects) {
            if (isMob(object)) {
                if (pvpCanAttack(attacker, object)) {
                    if (!isIncapacitated(object) && !isDead(object)) {
                        if (isPlayer(object)) {
                            if (!player_targets) {
                                continue;
                            }
                        }
                        if (canSee(attacker, object)) {
                            attackable_targets = utils.addElement(attackable_targets, object);
                        }
                    }
                }
            } else if (ai_lib.isTurret(object)) {
                if (pvpCanAttack(attacker, object)) {
                    if (canSee(attacker, object)) {
                        attackable_targets = utils.addElement(attackable_targets, object);
                    }
                }
            }
        }
        if (attackable_targets.size() >= 1)
        {
            obj_id[] _attackable_targets = new obj_id[attackable_targets.size()];
            attackable_targets.toArray(_attackable_targets);
            return _attackable_targets;
        }
        return null;
    }
    public static obj_id getTrapDroidId(obj_id player) throws InterruptedException
    {
        Vector data = getResizeableContents(getPlayerDatapad(player));
        if(data == null) return null;
        for (Object aData : data) {
            if (callable.hasCDCallable(((obj_id) aData)) && hasObjVar(((obj_id) aData), "module_data.trap_bonus")) {
                return ((obj_id) aData);
            }
        }
        return null;
    }
    public static int getTimeLeft(obj_id player, String toCheckFor, int timePenalty) throws InterruptedException
    {
        int timeCalled = utils.getIntScriptVar(player, toCheckFor);
        return timeCalled < 1 ? -1 : timeCalled + timePenalty - getGameTime();
    }
    public static int getTimeLeft(obj_id player, String toCheckFor, String modifiedTime) throws InterruptedException
    {
        int timeCalled = utils.getIntScriptVar(player, toCheckFor);
        return timeCalled < 1 ? -1 : timeCalled + utils.getIntScriptVar(player, modifiedTime) - getGameTime();
    }
    public static boolean isFreeTrial(obj_id player) throws InterruptedException
    {
        return isFreeTrialAccount(player);
    }
    public static boolean isFreeTrial(obj_id player, obj_id target) throws InterruptedException
    {
        return isFreeTrialAccount(player);
    }
    public static void notifyObject(obj_id objTarget, String strNotificationName, dictionary dctParams) throws InterruptedException
    {
        if (!isIdValid(objTarget))
        {
            debugServerConsoleMsg(null, "Null object id passed into notifyObject, exceptioning");
            Thread.dumpStack();
            throw new InterruptedException();
        }
        if (strNotificationName == null)
        {
            debugServerConsoleMsg(null, "null notification name passed into notifyObject, exceptioning");
            Thread.dumpStack();
            throw new InterruptedException();
        }
        if (strNotificationName.equals(""))
        {
            debugServerConsoleMsg(null, "Empty Notification name passed into notifyObject, exceptioning");
            Thread.dumpStack();
            throw new InterruptedException();
        }
        try
        {
            int intReturn = script_entry.callMessageHandlers(strNotificationName, objTarget, dctParams);
        }
        catch(Throwable err)
        {
            debugServerConsoleMsg(null, "Unable to call into callMessageHandlers ");
            Thread.dumpStack();
            throw new InterruptedException();
        }
    }
    public static void callTrigger(String strTrigger, Object[] params) throws InterruptedException
    {
        try
        {
            script_entry.runScripts(strTrigger, params);
        }
        catch(Throwable err)
        {
            debugServerConsoleMsg(null, "Unable to call into callMessageHandlers ");
            Thread.dumpStack();
            throw new InterruptedException();
        }
    }
    public static String getCellName(obj_id building, obj_id cell) throws InterruptedException
    {
        for (String cellName : getCellNames(building)) {
            if (getCellId(building, cellName) == cell) {
                return cellName;
            }
        }
        return "";
    }
    public static String getRealPlayerFirstName(obj_id player) throws InterruptedException
    {
        String firstName = getPlayerName(player);
        if (firstName == null)
        {
            return null;
        }
        int idx = (firstName.toLowerCase()).indexOf("corpse of");
        if (idx >= 0)
        {
            firstName = firstName.substring(idx + 1);
        }
        if (firstName.length() > 0)
        {
            char first = firstName.charAt(0);
            first = Character.toUpperCase(first);
            firstName = first + firstName.substring(1);
        }
        return firstName;
    }
    public static void dismountRiderJetpackCheck(obj_id rider) throws InterruptedException
    {
        if (!isIdValid(rider))
        {
            return;
        }
        obj_id mount = getMountId(rider);
        if (!exists(mount))
        {
            return;
        }
        if (isIdValid(mount))
        {
            pet_lib.doDismountNow(rider, false);
            if (vehicle.isJetPackVehicle(mount))
            {
                sendSystemMessage(rider, new string_id("pet/pet_menu", "jetpack_dismount"));
                vehicle.storeVehicle(callable.getCallableCD(mount), rider);
            }
        }
    }
    public static location getLocationFromTransform(transform trTest) throws InterruptedException
    {
        location locTest = new location();
        vector vctTest = trTest.getPosition_p();
        locTest.x = vctTest.x;
        locTest.y = vctTest.y;
        locTest.z = vctTest.z;
        return locTest;
    }
    public static int addClassRequirementAttributes(obj_id player, obj_id item, String[] names, String[] attribs, int firstFree, String prefix) throws InterruptedException
    {
        if (hasObjVar(item, prefix + "classRequired"))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(getStringObjVar(item, prefix + "classRequired"), ",");
            String requiredText = "";
            boolean qualifies = false;
            String classId;
            String tmp;

            while (st.hasMoreTokens())
            {
                classId = st.nextToken();
                tmp = "@skl_n:class_" + classId + "\0";
                if (st.hasMoreTokens())
                {
                    tmp += "\n\\>117\0";
                }
                requiredText += tmp;
                if (isProfession(player, stringToInt(classId)))
                {
                    qualifies = true;
                    break;
                }
            }
            if (!qualifies)
            {
                names[firstFree] = "class_required";
                attribs[firstFree++] = requiredText;
            }
        }
        if (hasObjVar(item, prefix + "levelRequired"))
        {
            int minLevel = getIntObjVar(item, prefix + "levelRequired");
            if (minLevel > getLevel(player))
            {
                names[firstFree] = "levelrequired";
                attribs[firstFree++] = "" + minLevel;
            }
        }
        return firstFree;
    }
    public static boolean testItemClassRequirements(obj_id player, String requiredClasses, boolean silent) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(requiredClasses, ",");
        while (st.hasMoreTokens())
        {
            if (isProfession(player, stringToInt(st.nextToken())))
                return true;
        }
        if (!silent)
        {
            sendSystemMessage(player, new string_id("spam", "classrequired"));
        }
        return false;
    }
    public static boolean testItemClassRequirements(obj_id player, obj_id thing, boolean silent, String prefix) throws InterruptedException {
        return !hasObjVar(thing, prefix + "classRequired") || testItemClassRequirements(player, getStringObjVar(thing, prefix + "classRequired"), silent);
    }
    public static boolean testItemLevelRequirements(obj_id player, obj_id thing, boolean silent, String prefix) throws InterruptedException
    {
        if (!hasObjVar(thing, prefix + "levelRequired"))
        {
            return true;
        }
        int minLevel = getIntObjVar(thing, prefix + "levelRequired");
        if (minLevel > getLevel(player))
        {
            if (!silent)
            {
                prose_package pp = prose.getPackage(new string_id("spam", "levelrequired"), "" + minLevel);
                sendSystemMessageProse(player, pp);
            }
            return false;
        }
        return true;
    }
    public static boolean testItemAbilityRequirements(obj_id player, obj_id thing, boolean silent, String prefix) throws InterruptedException
    {
        if (!hasObjVar(thing, prefix + "abilityRequired"))
        {
            return true;
        }
        String commandRequired = getStringObjVar(thing, prefix + "abilityRequired");
        if (!hasCommand(player, commandRequired))
        {
            if (!silent)
            {
                sendSystemMessageProse(player, prose.getPackage(new string_id("spam", "abilityrequired"), "@cmd_n:" + toLower(commandRequired)));
            }
            return false;
        }
        return true;
    }
    public static boolean testItemSkillRequirements(obj_id player, obj_id thing, boolean silent, String prefix) throws InterruptedException
    {
        if (!hasObjVar(thing, prefix + "skillRequired"))
        {
            return true;
        }
        String skillRequired = getStringObjVar(thing, prefix + "skillRequired");
        if (!hasSkill(player, skillRequired))
        {
            if (!silent)
            {
                sendSystemMessageProse(player, prose.getPackage(new string_id("spam", "skill_required"), "@skl_n:" + toLower(skillRequired)));
            }
            return false;
        }
        return true;
    }
    public static void makeItemNoDrop(obj_id item) throws InterruptedException
    {
        attachScript(item, "item.special.nomove");
        setObjVar(item, "noTrade", 1);
    }
    public static boolean isItemNoDrop(obj_id item) throws InterruptedException
    {
        return hasScript(item, "item.special.nomove") || hasObjVar(item, "noTrade");
    }
    public static void clearNoDropFromItem(obj_id item) throws InterruptedException
    {
        detachScript(item, "item.special.nomove");
        removeObjVar(item, "noTrade");
    }
    public static obj_id findNoTradeItem(obj_id[] items, boolean testPlayers) throws InterruptedException
    {
        return findNoTradeItem(items, testPlayers, false);
    }
    public static obj_id findNoTradeItem(obj_id[] items, boolean testPlayers, boolean novendor) throws InterruptedException
    {
        if (items != null)
        {
            obj_id result;
            for (obj_id item : items) {
                if (isIdValid(item) && (testPlayers || !isPlayer(item))) {
                    if (novendor && hasScript(item, "terminal.vendor")) {
                        continue;
                    }
                    if (hasScript(item, "terminal.terminal_structure")){
                        continue;
                    }
                    if (!canTrade(item)) {
                        return item;
                    } else if (utils.isContainer(item)) {
                        result = findNoTradeItem(getContents(item), testPlayers);
                        if (isIdValid(result)) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }
    public static obj_id findNoTradeItemNotVendor(obj_id[] items, boolean testPlayers) throws InterruptedException
    {
        return findNoTradeItem(items, testPlayers, true);
    }
    public static obj_id hasWaypoint(obj_id player, String name) throws InterruptedException
    {
        if (!isIdValid(player) || name == null)
        {
            return null;
        }
        obj_id[] waypoints = getWaypointsInDatapad(player);
        if (waypoints == null)
        {
            return null;
        }
        String waypointName;
        for (obj_id waypoint : waypoints) {
            if (isIdValid(waypoint)) {
                waypointName = getWaypointName(waypoint);
                if (waypointName != null && waypointName.equals(name)) {
                    return waypoint;
                }
            }
        }
        return null;
    }
    public static boolean waypointExists(obj_id player, obj_id waypoint) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(waypoint))
        {
            return false;
        }
        obj_id[] waypoints = getWaypointsInDatapad(player);
        if (waypoints == null || waypoints.length <= 0)
        {
            return false;
        }
        for (obj_id waypoint1 : waypoints) {
            if (isIdValid(waypoint1)) {
                if (waypoint1 == waypoint) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isExtendedASCII(String inString) throws InterruptedException
    {
        if (inString == null)
        {
            return false;
        }
        for (int i = 0; i < inString.length(); i++)
        {
            int ASCIIValue = inString.charAt(i);
            if (ASCIIValue < 0 || ASCIIValue > 255)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isAntiDecay(obj_id item) throws InterruptedException
    {
        return isIdValid(item) && hasObjVar(item, "antidecay");
    }
    public static boolean makeAntiDecay(obj_id item) throws InterruptedException
    {
        if (isIdValid(item))
        {
            setObjVar(item, "antidecay", 1);
            attachScript(item, "systems.veteran_reward.antidecay_examine");
            return true;
        }
        return false;
    }
    public static boolean removeAntiDecay(obj_id item) throws InterruptedException
    {
        if (isIdValid(item))
        {
            removeObjVar(item, "antidecay");
            return true;
        }
        return false;
    }
    public static boolean validItemForAntiDecay(obj_id item) throws InterruptedException
    {
        if (isIdValid(item))
        {
            if (jedi.isLightsaber(item) || cloninglib.isDamagedOnCloneGOT(getGameObjectType(item)) || isGameObjectTypeOf(item, GOT_weapon))
            {
                return true;
            }
        }
        return false;
    }
    public static obj_id[] getLocalGroupMemberIds(obj_id group) throws InterruptedException
    {
        if (!isIdValid(group))
        {
            return null;
        }
        obj_id[] groupMemberIds = getGroupMemberIds(group);
        if (groupMemberIds == null || groupMemberIds.length == 0)
        {
            return null;
        }
        Vector localMemberIdsVector = new Vector();
        for (obj_id groupMemberId : groupMemberIds) {
            if (isIdValid(groupMemberId) && exists(groupMemberId) && isPlayer(groupMemberId)) {
                localMemberIdsVector.addElement(groupMemberId);
            }
        }
        if (localMemberIdsVector == null || localMemberIdsVector.size() < 1)
        {
            return null;
        }
        return (utils.toStaticObjIdArray(localMemberIdsVector));
    }
    public static boolean canSpeakWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "class_smuggler_phase1_novice"))
        {
            return false;
        }
        return !hasSkill(player, "social_language_wookiee_comprehend");
    }
    public static void emoteWookieeConfusion(obj_id player, obj_id npc) throws InterruptedException
    {
        playClientEffectObj(npc, "clienteffect/voc_wookiee_med_4sec.cef", player, "");
        chat.thinkTo(player, player, new string_id("ep3/sidequests", "wke_convo_failure"));
    }
    public static void setObjVarsList(obj_id object, String objVarList) throws InterruptedException
    {
        if (objVarList == null || objVarList.equals(""))
        {
            return;
        }
        String[] objVarToSet;
        String objVarValue;
        String[] objVarNameAndType;
        String objVarName;

        for (String pair : split(objVarList, ',')) {
            objVarToSet = split(pair, '=');
            objVarValue = objVarToSet[1];
            objVarNameAndType = split(objVarToSet[0], ':');
            objVarName = objVarNameAndType[1];
            switch (objVarNameAndType[0]) {
                case "string":
                    setObjVar(object, objVarName, objVarValue);
                    break;
                case "int":
                    setObjVar(object, objVarName, utils.stringToInt(objVarValue));
                    break;
                case "float":
                    setObjVar(object, objVarName, utils.stringToFloat(objVarValue));
                    break;
                case "boolean":
                case "bool":
                    setObjVar(object, objVarName, utils.stringToInt(objVarValue));
                    break;
                default:
                    setObjVar(object, objVarName, objVarValue);
                    break;
            }
        }
    }
    public static void setObjVarsListUsingSemiColon(obj_id object, String objVarList) throws InterruptedException
    {
        if (objVarList == null || objVarList.equals(""))
        {
            return;
        }
        String[] objVarToSet;
        String objVarValue;
        String[] objVarNameAndType;
        String objVarName;

        for (String pair : split(objVarList, ',')) {
            objVarToSet = split(pair, '=');
            objVarValue = objVarToSet[1];
            objVarNameAndType = split(objVarToSet[0], ';');
            objVarName = objVarNameAndType[1];
            switch (objVarNameAndType[0]) {
                case "string":
                    setObjVar(object, objVarName, objVarValue);
                    break;
                case "int":
                    setObjVar(object, objVarName, utils.stringToInt(objVarValue));
                    break;
                case "float":
                    setObjVar(object, objVarName, utils.stringToFloat(objVarValue));
                    break;
                case "boolean":
                case "bool":
                    setObjVar(object, objVarName, utils.stringToInt(objVarValue));
                    break;
                default:
                    setObjVar(object, objVarName, objVarValue);
                    break;
            }
        }
    }
    public static boolean verifyLocationBasedDestructionAnchor(obj_id subject, float distance) throws InterruptedException
    {
        if (!hasObjVar(subject, "recordLoc"))
        {
            setObjVar(subject, "recordLoc", getLocation(subject));
            return true;
        }
        float distanceDifference = getDistance(getLocationObjVar(subject, "recordLoc"), getLocation(getTopMostContainer(subject)));
        if (distanceDifference > distance || distanceDifference == -1.0f)
        {
            destroyObject(subject);
            return false;
        }
        return true;
    }
    public static boolean verifyLocationBasedDestructionAnchor(obj_id subject, location recordLoc, float distance) throws InterruptedException
    {
        if (!hasObjVar(subject, "recordLoc"))
        {
            setObjVar(subject, "recordLoc", recordLoc);
            return true;
        }
        float distanceDifference = getDistance(getLocationObjVar(subject, "recordLoc"), getLocation(getTopMostContainer(subject)));
        if (distanceDifference > distance || distanceDifference == -1.0f)
        {
            destroyObject(subject);
            return false;
        }
        return true;
    }
    public static String assembleTimeRemainToUse(int time) throws InterruptedException
    {
        return assembleTimeRemainToUse(time, true);
    }
    public static String assembleTimeRemainToUse(int time, boolean localized) throws InterruptedException
    {
        if (time <= 60)
        {
            String attrib = "";
            if (localized)
            {
                attrib = time + (time != 1 ? " $@spam:seconds$" : " $@spam:second$");
            }
            else 
            {
                attrib = time + (time != 1 ? " seconds" : " second");
            }
            return attrib;
        }
        if (time > 60 && time <= 3600)
        {
            int minutes = (int)Math.floor(time / 60);
            int remainder_time = time % 60;
            String attrib = "";
            if (localized)
            {
                attrib = minutes + (minutes != 1 ? " $@spam:minutes$, " : " $@spam:minute$, ") + remainder_time + (remainder_time != 1 ? " $@spam:seconds$" : " $@spam:second$");
            }
            else 
            {
                attrib = minutes + (minutes != 1 ? " minutes, " : " minute, ") + remainder_time + (remainder_time != 1 ? " seconds" : " second");
            }
            return attrib;
        }
        if (time > 3600 && time <= 86400)
        {
            int hours = (int)Math.floor(time / 3600);
            int remainder_time = (time % 3600) / 60;
            String attrib = "";
            if (localized)
            {
                attrib = hours + (hours != 1 ? " $@spam:hours$, " : " $@spam:hour$, ") + remainder_time + (remainder_time != 1 ? " $@spam:minutes$" : " $@spam:minute$");
            }
            else 
            {
                attrib = hours + (hours != 1 ? " hours, " : " hour, ") + remainder_time + (remainder_time != 1 ? " minutes" : " minute");
            }
            return attrib;
        }
        if (time > 86400 && time < Integer.MAX_VALUE)
        {
            int days = (int)Math.floor(time / 86400);
            int remainder_hours = (time % 86400) / 3600;
            int remainder_minutes = (time % 3600) / 60;
            String attrib = "";
            if (localized)
            {
                attrib = days + (days != 1 ? " $@spam:days$, " : " $@spam:day$, ") + remainder_hours + (remainder_hours != 1 ? " $@spam:hours$, " : " $@spam:hour$, ") + remainder_minutes + (remainder_minutes != 1 ? " $@spam:minutes$" : " $@spam:minute$");
            }
            else 
            {
                attrib = days + (days != 1 ? " days, " : " day, ") + remainder_hours + (remainder_hours != 1 ? " hours, " : " hour, ") + remainder_minutes + (remainder_minutes != 1 ? " minutes" : " minute");
            }
            return attrib;
        }
        return null;
    }
    public static boolean isProfession(obj_id player, int profession) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (!isPlayer(player))
        {
            return false;
        }
        String professionName = "";
        switch (profession)
        {
            case COMMANDO:
                professionName = "commando";
                break;
            case SMUGGLER:
                professionName = "smuggler";
                break;
            case MEDIC:
                professionName = "medic";
                break;
            case OFFICER:
                professionName = "officer";
                break;
            case SPY:
                professionName = "spy";
                break;
            case BOUNTY_HUNTER:
                professionName = "bounty";
                break;
            case FORCE_SENSITIVE:
                professionName = "force";
                break;
            case TRADER:
                professionName = "trader";
                break;
            case ENTERTAINER:
                professionName = "entertainer";
                break;
            default:
                break;
        }
        String classTemplate = getSkillTemplate(player);
        return classTemplate != null && classTemplate.startsWith(professionName);
    }
    public static int getPlayerProfession(obj_id player) throws InterruptedException
    {
        String[] noviceSkillList = 
        {
            "class_forcesensitive_phase1_novice",
            "class_bountyhunter_phase1_novice",
            "class_smuggler_phase1_novice",
            "class_commando_phase1_novice",
            "class_officer_phase1_novice",
            "class_spy_phase1_novice",
            "class_medic_phase1_novice",
            "class_entertainer_phase1_novice"
        };
        int[] professionList = 
        {
            FORCE_SENSITIVE,
            BOUNTY_HUNTER,
            SMUGGLER,
            COMMANDO,
            OFFICER,
            SPY,
            MEDIC,
            ENTERTAINER
        };
        for (int i = 0; i < noviceSkillList.length; i++)
        {
            if (hasSkill(player, noviceSkillList[i]))
            {
                return professionList[i];
            }
        }
        return TRADER;
    }
    public static byte[] packObject(Object o) throws InterruptedException
    {
        byte[] ret = null;
        try
        {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            try
            {
                objectOutput.writeObject(o);
                ret = byteOutput.toByteArray();
            }
            catch(IOException err)
            {
                LOG("utils", "ERROR in Java utils.packObject(): " + err.getMessage());
            }
        }
        catch(java.io.IOException e)
        {
            LOG("utils", "ERROR in initialization of utils.packObject: " + e.getMessage());
        }
        return ret;
    }
    public static Object unpackObject(byte[] source) throws InterruptedException
    {
        try
        {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(source);
            ObjectInputStream ois = new ObjectInputStream(byteInput);
            return ois.readObject();
        }
        catch(ClassNotFoundException x)
        {
            LOG("utils", "ClassNotFoundException:  " + x.toString());
        }
        catch(IOException x)
        {
            LOG("utils", "IOException:  " + x.toString());
        }
        return null;
    }
    public static boolean replaceSnowflakeItem(obj_id oldItem, String newTemplateName) throws InterruptedException
    {
        obj_id ownerContainer = getContainedBy(oldItem);
        if (!isIdValid(ownerContainer))
        {
            return false;
        }
        boolean inVendor = isInVendor(oldItem);
        boolean inBazaar = isInBazaar(oldItem);
        if (inVendor || inBazaar)
        {
            messageTo(oldItem, "handlerReInitialize", null, 5.0f, false);
            return false;
        }
        obj_id newItem = null;
        if (isPlayer(ownerContainer))
        {
            obj_id inventory = utils.getInventoryContainer(ownerContainer);
            if (utils.isEquipped(oldItem))
            {
                putInOverloaded(oldItem, inventory);
            }
            newItem = createObjectOverloaded(newTemplateName, inventory);
            CustomerServiceLog("replaceSnowflakeItem: ", "Old Item (" + oldItem + ")" + " is contained in player's " + getFirstName(getContainingPlayer(oldItem)) + "(" + getContainingPlayer(oldItem) + ") inventory.");
            CustomerServiceLog("replaceSnowflakeItem: ", "New Item (" + newItem + ")" + " was created in " + getFirstName(getContainingPlayer(newItem)) + "(" + getContainingPlayer(newItem) + ") inventory.");
        }
        else if (getCellName(ownerContainer) != null)
        {
            if (player_structure.isBuilding(getTopMostContainer(ownerContainer)))
            {
                newItem = createObject(newTemplateName, getLocation(oldItem));
                if (isIdValid(newItem))
                {
                    if (isObjectPersisted(oldItem) && !isObjectPersisted(newItem))
                    {
                        persistObject(newItem);
                    }
                }
                CustomerServiceLog("replaceSnowflakeItem: ", "Old Item (" + oldItem + ") is contained in a building (" + getTopMostContainer(ownerContainer) + ")");
                CustomerServiceLog("replaceSnowflakeItem: ", "New Item (" + newItem + ") was created in building (" + getTopMostContainer(ownerContainer) + ")");
                CustomerServiceLog("replaceSnowflakeItem: ", "New Item (" + newItem + ")s location is in a building (" + getTopMostContainer(ownerContainer) + ")");
            }
        }
        else 
        {
            newItem = createObjectOverloaded(newTemplateName, ownerContainer);
            CustomerServiceLog("replaceSnowflakeItem: ", "New Item (" + newItem + ")" + " was created in container (" + ownerContainer + ")");
        }
        if (isIdValid(newItem))
        {
            CustomerServiceLog("replaceSnowflakeItem: ", "New Item (" + newItem + ")" + " has a valid ID. So Old Item (" + oldItem + ") is to be destroyed");
            destroyObject(oldItem);
        }
        return true;
    }
    public static void checkInventoryForSnowflakeItemSwaps(obj_id self) throws InterruptedException
    {
        String[] oldTemplates = dataTableGetStringColumn("datatables/item/snowflake_item_swaps.iff", "OLD_TEMPLATE");
        obj_id objInventory = getInventoryContainer(self);
        if (isIdValid(objInventory))
        {
            obj_id[] objContents = getContents(objInventory, true);
            if (objContents != null)
            {
                String newTemplate;
                for (obj_id objContent : objContents) {
                    for (int j = 0; j < oldTemplates.length; j++) {
                        if (getTemplateName(objContent).equals(oldTemplates[j])) {
                            newTemplate = dataTableGetString("datatables/item/snowflake_item_swaps.iff", j, "NEW_TEMPLATE");
                            if (newTemplate != null && !newTemplate.equals("")) {
                                replaceSnowflakeItem(objContent, newTemplate);
                            }
                        }
                    }
                }
            }
        }
    }
    public static boolean isVendorObject(obj_id object) throws InterruptedException
    {
        return hasScript(object, VENDOR_SCRIPT);
    }
    public static boolean isBazaarObject(obj_id object) throws InterruptedException
    {
        return hasScript(object, BAZAAR_SCRIPT);
    }
    public static boolean isInVendor(obj_id item) throws InterruptedException {
        obj_id ownerContainer = getContainedBy(item);
        return isIdValid(ownerContainer) && hasObjVar(getContainedBy(ownerContainer), "vendor");
    }
    public static boolean isInBazaar(obj_id item) throws InterruptedException {
        obj_id ownerContainer = getContainedBy(item);
        return isIdValid(ownerContainer) && hasScript(ownerContainer, "terminal.bazaar");
    }
    public static boolean outOfRange(obj_id self, obj_id player, float distance, boolean message) throws InterruptedException
    {
        if (isGod(player))
        {
            return false;
        }
        location a = getLocation(self);
        location b = getLocation(player);
        if (a.cell == b.cell && a.distance(b) < distance)
        {
            return false;
        }
        if (message)
        {
            sendSystemMessage(player, SID_OUT_OF_RANGE);
        }
        return true;
    }
    public static float roundFloatByDecimal(float number) throws InterruptedException
    {
        return (float) Math.round(number * 100) / 100;
    }
    public static void housePackingDecreaseIncrease(obj_id objPlayer, int change) throws InterruptedException
    {
        int[] housePacking;
        if (change > 0)
        {
            if (!hasObjVar(objPlayer, "housePackup"))
            {
                setObjVar(objPlayer, "housePackup", new int[utils.HOUSE_MAX]);
            }
            housePacking = getIntArrayObjVar(objPlayer, "housePackup");
            if (housePacking.length != utils.HOUSE_MAX)
            {
                return;
            }
            housePacking[0]++;
            housePacking[1]++;
            setObjVar(objPlayer, "housePackup", housePacking);
            if (!hasObjVar(objPlayer, "dailyHousePackup"))
            {
                setObjVar(objPlayer, "dailyHousePackup", 1);
                int resetTime = getGameTime() + player_structure.TIME_TO_NEXT_PACKUP;
                setObjVar(objPlayer, "maxHousePackupTimer", resetTime);
            }
            else
            {
                int dailyHousePacking = getIntObjVar(objPlayer, "dailyHousePackup");
                dailyHousePacking++;
                setObjVar(objPlayer, "dailyHousePackup", dailyHousePacking);
            }
            if (!badge.hasBadge(objPlayer, "house_packup_badge"))
            {
                int[] packupObjVar = getIntArrayObjVar(objPlayer, "housePackup");
                if (packupObjVar != null && packupObjVar.length > 0)
                {
                    if (packupObjVar[1] >= player_structure.NUM_NEEDED_PACKUP_FIRST_BADGE)
                    {
                        badge.grantBadge(objPlayer, "house_packup_badge");
                    }
                }
            }
            if (badge.hasBadge(objPlayer, "house_packup_badge"))
            {
                if (!badge.hasBadge(objPlayer, "house_packup_badge_master"))
                {
                    int[] packupObjVar = getIntArrayObjVar(objPlayer, "housePackup");
                    if (packupObjVar != null && packupObjVar.length > 0)
                    {
                        if (packupObjVar[1] >= player_structure.NUM_NEEDED_PACKUP_SECOND_BADGE)
                        {
                            badge.grantBadge(objPlayer, "house_packup_badge_master");
                        }
                    }
                }
            }
        }
        else
        {
            housePacking = getIntArrayObjVar(objPlayer, "housePackup");
            if (housePacking.length != utils.HOUSE_MAX)
            {
                return;
            }
            housePacking[0]--;
            setObjVar(objPlayer, "housePackup", housePacking);
        }
    }
    public static boolean isInHouseCellSpace(obj_id object) throws InterruptedException
    {
        if (isIdValid(object))
        {
            if (isNestedWithinAPlayer(object))
            {
                return false;
            }
            obj_id house = getTopMostContainer(object);
            if (isIdValid(house) && !player_structure.isBuilding(house))
            {
                return false;
            }
            obj_id container = getContainedBy(object);
            if (isIdValid(container) && (getContainedBy(container) != house))
            {
                return false;
            }
            return hasObjVar(house, "player_structure.admin.adminList");
        }
        return false;
    }
    public static boolean hasResourceInInventory(obj_id player, String resource) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (resource == null || resource.equals(""))
        {
            return false;
        }
        obj_id[] contents = getFilteredPlayerContents(player);
        if ((contents == null) || (contents.length == 0))
        {
            return false;
        }
        obj_id resourceId;
        for (obj_id content : contents) {
            int got = getGameObjectType(content);
            if (isGameObjectTypeOf(got, GOT_resource_container)) {
                if (isIdValid(content)) {
                    resourceId = getResourceContainerResourceType(content);
                    if (isIdValid(resourceId)) {
                        if (isResourceDerivedFrom(resourceId, resource)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public static obj_id[] validateObjIdArray(obj_id[] objIds) throws InterruptedException
    {
        Vector target = new Vector();
        target.setSize(0);
        for (obj_id objId : objIds) {
            if (isIdValid(objId) && exists(objId)) {
                addElement(target, objId);
            }
        }
        objIds = new obj_id[target.size()];
        target.toArray(objIds);

        return objIds;
    }
    public static obj_id[] getPlayersInBuildoutRow(obj_id player) throws InterruptedException
    {
        return getPlayersInBuildoutRow(getLocation(player).area, locations.getBuildoutAreaRow(player));
    }
    public static obj_id[] getPlayersInBuildoutDimensions(String scene, float x1, float x2, float z1, float z2) throws InterruptedException
    {
        float centX = Math.abs((x2 - x1) / 2.0f) + x1;
        float centZ = Math.abs((z2 - z1) / 2.0f) + z1;
        location centerLoc = new location(centX, 0.0f, centZ, scene);
        location lowerLeft = new location(x1, 0.0f, z1, scene);
        float hypotenuse = getDistance(centerLoc, lowerLeft);
        obj_id[] allPlayers = getAllPlayers(centerLoc, hypotenuse * 3.0f);
        Vector playersInArea = new Vector();
        playersInArea.setSize(0);
        if (allPlayers == null || allPlayers.length == 0)
        {
            return null;
        }
        location loc;
        for (obj_id allPlayer : allPlayers) {
            loc = getLocation(trial.getTop(allPlayer));
            if (loc.x < x1 || loc.x > x2 || loc.z < z1 || loc.z > z2) {
                LOG("doLogging", "" + loc.x + " vs: " + x1 + ", " + x2 + " and " + loc.z + " vs: " + z1 + ", " + z2);
                continue;
            }
            utils.addElement(playersInArea, allPlayer);
        }
        if (playersInArea.size() == 0)
        {
            return null;
        }
        obj_id[] _playersInArea = new obj_id[0];
        _playersInArea = new obj_id[playersInArea.size()];
        playersInArea.toArray(_playersInArea);
        return _playersInArea;
    }
    public static dictionary getCoordinatesInBuildoutRow(String scene, int buildout_row) throws InterruptedException
    {
        return dataTableGetRow("datatables/buildout/areas_" + scene + ".iff", buildout_row);
    }
    public static obj_id[] getPlayersInBuildoutRow(String scene, int buildout_row) throws InterruptedException
    {
        dictionary data = dataTableGetRow("datatables/buildout/areas_" + scene + ".iff", buildout_row);
        return getPlayersInBuildoutDimensions(scene, data.getFloat("x1"), data.getFloat("x2"), data.getFloat("z1"), data.getFloat("z2"));
    }
    public static obj_id[] getPlayersInBuildoutArea(String scene, String buildout_area) throws InterruptedException
    {
        int rowNum = dataTableSearchColumnForString(buildout_area, 0, "datatables/buildout/areas_" + scene + ".iff");
        if (rowNum == -1)
        {
            return null;
        }
        return getPlayersInBuildoutRow(scene, rowNum);
    }
    public static obj_id[] getAllObjectsInBuildoutArea(obj_id baseObject) throws InterruptedException
    {
        return getAllObjectsInBuildoutArea(getLocation(baseObject).area, locations.getBuildoutAreaRow(baseObject));
    }
    public static obj_id[] getAllObjectsInBuildoutArea(String scene, int buildout_row) throws InterruptedException
    {
        dictionary data = dataTableGetRow("datatables/buildout/areas_" + scene + ".iff", buildout_row);
        float x1 = data.getFloat("x1");
        float x2 = data.getFloat("x2");
        float z1 = data.getFloat("z1");
        float z2 = data.getFloat("z2");
        float centX = Math.abs((data.getFloat("x2") - data.getFloat("x1")) / 2.0f) + data.getFloat("x1");
        float centZ = Math.abs((data.getFloat("z2") - data.getFloat("z1")) / 2.0f) + data.getFloat("z1");
        location centerLoc = new location(centX, 0.0f, centZ, scene);
        location lowerLeft = new location(data.getFloat("x1"), 0.0f, data.getFloat("z1"), scene);
        float hypotenuse = getDistance(centerLoc, lowerLeft);
        obj_id[] allObjects = getObjectsInRange(centerLoc, hypotenuse * 3.0f);
        Vector objectsInArea = new Vector();
        objectsInArea.setSize(0);
        if (allObjects == null || allObjects.length == 0)
        {
            return null;
        }
        location testLoc;
        for (obj_id obj : allObjects) {
            testLoc = getLocation(trial.getTop(obj));
            if(testLoc == null || !isValidLocation(testLoc)){
                continue;
            }
            if (testLoc.x < x1 || testLoc.x > x2 || testLoc.z < z1 || testLoc.z > z2) {
                continue;
            }
            objectsInArea.add(obj);
        }
        if (objectsInArea.size() == 0)
        {
            return null;
        }
        obj_id[] _objectsInArea = new obj_id[0];
        _objectsInArea = new obj_id[objectsInArea.size()];
        objectsInArea.toArray(_objectsInArea);
        return _objectsInArea;
    }
    public static Vector shuffleArray(Vector list) throws InterruptedException
    {
        Vector newList = new Vector();
        for (int size = list.size(); size > 0; size--)
        {
            int i = rand(0, (size - 1));
            newList = utils.addElement(newList, list.get(i));
            list = utils.removeElementAt(list, i);
        }
        if (newList == null || newList.size() == 0)
        {
            return null;
        }
        return newList;
    }
    public static boolean grantGift(obj_id player) throws InterruptedException
    {
        if ((getCurrentBirthDate() - getPlayerBirthDate(player)) < 10)
        {
            return false;
        }
        if (hasObjVar(player, XMAS_RECEIVED_VI1))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        String config = getConfigSetting("GameServer", "grantGift");
        if (config != null)
        {
            if (config.equals("false"))
            {
                return false;
            }
        }
        if (isInTutorialArea(player))
        {
            setObjVar(player, XMAS_NOT_RECEIVED_TUTORIAL, 1);
            CustomerServiceLog("grantGift", getFirstName(player) + "(" + player + ") did not receive Christmas '06 gift because they logged in on the tutorial planet.");
            return false;
        }
        removeObjVar(player, XMAS_NOT_RECEIVED_TUTORIAL);
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            CustomerServiceLog("grantGift", getFirstName(player) + "(" + player + ") did not have an inventory container. Didn't receive Christmas '06 gift, but not blocked from future attempt.");
            return false;
        }
        obj_id giftOther = static_item.createNewItemFunction("item_lifeday_gift_other_01_02", inv);
        setObjVar(giftOther, LIFEDAY_OWNER, player);
        utils.sendMail(GIFT_GRANTED_SUB, GIFT_GRANTED, player, "System");
        setObjVar(player, XMAS_RECEIVED_VI1, 1);
        CustomerServiceLog("grantGift", getFirstName(player) + "(" + player + ") has received his Christmas '06 gift.");
        return true;
    }
    public static void fullExpertiseReset(obj_id player, boolean storeBeast) throws InterruptedException
    {
        if (storeBeast)
        {
            if (beast_lib.isBeastMaster(player))
            {
                beast_lib.storeBeasts(player);
            }
        }
        if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            if (buff.isInStance(player))
            {
                buff.removeBuff(player, jedi.JEDI_STANCE);
            }
            if (buff.isInFocus(player))
            {
                buff.removeBuff(player, jedi.JEDI_FOCUS);
            }
        }
        respec.setRespecVersion(player);
        float mtp_eavesdrop_duration = 0;
        float mtp_wine_duration = 0;
        obj_id self = getSelf();
        if(buff.hasBuff(self, "mtp_eavesdrop_lockout")){
            mtp_eavesdrop_duration = buff.getDuration("mtp_eavesdrop_lockout");
        }
        if(buff.hasBuff(self, "mtp_wine_lockout")){
            mtp_wine_duration = buff.getDuration("mtp_wine_lockout");
        }
        buff.removeAllBuffs(player, false, true);
        if(mtp_eavesdrop_duration > 0){
            buff.applyBuff(self, "mtp_eavesdrop_lockout", mtp_eavesdrop_duration);
        }
        if(mtp_wine_duration > 0){
            buff.applyBuff(self, "mtp_wine_lockout", mtp_eavesdrop_duration);
        }
        resetExpertises(player);
    }
    public static obj_id[] getAllRidersInVehicle(obj_id player, obj_id vehicle) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(vehicle))
        {
            return null;
        }
        if (!exists(player) || !exists(vehicle))
        {
            return null;
        }
        Vector resizeList = new Vector();
        resizeList.setSize(0);
        obj_id tempRider;
        for (int i = 1; i <= 25; ++i)
        {
            tempRider = getObjectInSlot(vehicle, "rider" + i);
            if (!isIdValid(tempRider) || !exists(tempRider))
            {
                continue;
            }
            resizeList = addElement(resizeList, tempRider);
        }
        obj_id[] _resizeList = new obj_id[0];
        if (resizeList != null)
        {
            _resizeList = new obj_id[resizeList.size()];
            resizeList.toArray(_resizeList);
        }
        return _resizeList;
    }
    public static void updateCTSObjVars(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        boolean retrievedCtsOjbvars = false;
        dictionary[] ctsOjbvars = null;
        String updateObjvar = utils.CTS_OBJVAR_HISTORY + ".1";
        if (!hasObjVar(player, updateObjvar))
        {
            ctsOjbvars = getCharacterRetroactiveCtsObjvars(player);
            retrievedCtsOjbvars = true;
            if ((ctsOjbvars != null) && (ctsOjbvars.length > 0))
            {
                utils.updateRespecCTSObjvars(player, ctsOjbvars);
                utils.updateBeastMasterCTSObjvars(player, ctsOjbvars);
            }
            setObjVar(player, updateObjvar, 1);
        }
        updateObjvar = utils.CTS_OBJVAR_HISTORY + ".2";
        if (!hasObjVar(player, updateObjvar))
        {
            if ((ctsOjbvars == null) && !retrievedCtsOjbvars)
            {
                ctsOjbvars = getCharacterRetroactiveCtsObjvars(player);
            }
            if ((ctsOjbvars != null) && (ctsOjbvars.length > 0))
            {
                utils.updateHousePackupCTSObjvars(player, ctsOjbvars);
            }
            setObjVar(player, updateObjvar, 1);
        }
    }
    public static void updateRespecCTSObjvars(obj_id player, dictionary[] ctsOjbvars) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if ((ctsOjbvars == null) || (ctsOjbvars.length <= 0))
        {
            return;
        }
        final int indexMostRecentTransfer = ctsOjbvars.length - 1;
        if ((ctsOjbvars[indexMostRecentTransfer] != null) && ctsOjbvars[indexMostRecentTransfer].containsKey("respecsBought") && ctsOjbvars[indexMostRecentTransfer].isInt("respecsBought"))
        {
            final int respecsBoughtFromTransfer = ctsOjbvars[indexMostRecentTransfer].getInt("respecsBought");
            int respecsBoughtCurrentValue = 0;
            if (hasObjVar(player, "respecsBought"))
            {
                respecsBoughtCurrentValue = getIntObjVar(player, "respecsBought");
            }
            if (respecsBoughtFromTransfer > respecsBoughtCurrentValue)
            {
                CustomerServiceLog("CharacterTransferRetroactiveHistory", "changing respecsBought objvar for " + player + " from " + respecsBoughtCurrentValue + " to " + respecsBoughtFromTransfer);
                setObjVar(player, "respecsBought", respecsBoughtFromTransfer);
            }
        }
        int[] recpecLevelsFromTransfer = 
        {
            -1,
            -1,
            -1
        };
        for (dictionary ctsOjbvar : ctsOjbvars) {
            if ((ctsOjbvar != null) && ctsOjbvar.containsKey(respec.PROF_LEVEL_ARRAY) && ctsOjbvar.isIntArray(respec.PROF_LEVEL_ARRAY)) {
                int[] levelArray = ctsOjbvar.getIntArray(respec.PROF_LEVEL_ARRAY);
                if ((levelArray != null) && (levelArray.length == 3)) {
                    if (levelArray[0] > recpecLevelsFromTransfer[0]) {
                        recpecLevelsFromTransfer[0] = levelArray[0];
                    }
                    if (levelArray[1] > recpecLevelsFromTransfer[1]) {
                        recpecLevelsFromTransfer[1] = levelArray[1];
                    }
                    if (levelArray[2] > recpecLevelsFromTransfer[2]) {
                        recpecLevelsFromTransfer[2] = levelArray[2];
                    }
                }
            }
        }
        if ((recpecLevelsFromTransfer[0] > -1) || (recpecLevelsFromTransfer[1] > -1) || (recpecLevelsFromTransfer[2] > -1))
        {
            int[] recpecLevelsCurrentValue = 
            {
                -1,
                -1,
                -1
            };
            if (hasObjVar(player, respec.PROF_LEVEL_ARRAY))
            {
                recpecLevelsCurrentValue = getIntArrayObjVar(player, respec.PROF_LEVEL_ARRAY);
            }
            if ((recpecLevelsCurrentValue != null) && (recpecLevelsCurrentValue.length == 3))
            {
                int[] recpecLevelsNewValue = 
                {
                    -1,
                    -1,
                    -1
                };
                recpecLevelsNewValue[0] = Math.max(recpecLevelsCurrentValue[0], recpecLevelsFromTransfer[0]);
                recpecLevelsNewValue[1] = Math.max(recpecLevelsCurrentValue[1], recpecLevelsFromTransfer[1]);
                recpecLevelsNewValue[2] = Math.max(recpecLevelsCurrentValue[2], recpecLevelsFromTransfer[2]);
                if ((recpecLevelsNewValue[0] != recpecLevelsCurrentValue[0]) || (recpecLevelsNewValue[1] != recpecLevelsCurrentValue[1]) || (recpecLevelsNewValue[2] != recpecLevelsCurrentValue[2]))
                {
                    CustomerServiceLog("CharacterTransferRetroactiveHistory", "changing " + respec.PROF_LEVEL_ARRAY + " objvar for " + player + " from (" + recpecLevelsCurrentValue[0] + "," + recpecLevelsCurrentValue[1] + "," + recpecLevelsCurrentValue[2] + ") to (" + recpecLevelsNewValue[0] + "," + recpecLevelsNewValue[1] + "," + recpecLevelsNewValue[2] + ")");
                    setObjVar(player, respec.PROF_LEVEL_ARRAY, recpecLevelsNewValue);
                }
            }
        }
        if (hasObjVar(player, respec.PROF_LEVEL_ARRAY))
        {
            String skillTemplate = getSkillTemplate(player);
            int[] recpecLevelsValue = getIntArrayObjVar(player, respec.PROF_LEVEL_ARRAY);
            if ((skillTemplate != null) && (skillTemplate.length() > 0) && (recpecLevelsValue != null) && (recpecLevelsValue.length == 3))
            {
                int currentLevel = getLevel(player);
                if (!skillTemplate.startsWith("entertainer") && !skillTemplate.startsWith("trader"))
                {
                    int combatLevel = recpecLevelsValue[respec.PROF_LEVEL_COMBAT];
                    if (currentLevel < combatLevel)
                    {
                        respec.autoLevelPlayer(player, combatLevel, true);
                    }
                }
                else if (skillTemplate.startsWith("entertainer"))
                {
                    int entLevel = recpecLevelsValue[respec.PROF_LEVEL_ENT];
                    if (currentLevel < entLevel)
                    {
                        respec.autoLevelPlayer(player, entLevel, true);
                    }
                }
                else if (skillTemplate.startsWith("trader"))
                {
                    int traderLevel = recpecLevelsValue[respec.PROF_LEVEL_TRADER];
                    if (currentLevel < traderLevel)
                    {
                        respec.autoLevelPlayer(player, traderLevel, true);
                    }
                }
            }
        }
    }
    public static void updateBeastMasterCTSObjvars(obj_id player, dictionary[] ctsOjbvars) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if ((ctsOjbvars == null) || (ctsOjbvars.length <= 0))
        {
            return;
        }
        HashSet abilities = new HashSet();
        String objVarName;
        for (dictionary ctsOjbvar : ctsOjbvars) {
            if (ctsOjbvar != null) {
                int j = 0;
                while (true) {
                    objVarName = beast_lib.PLAYER_KNOWN_SKILLS_LIST + "." + j;
                    if (ctsOjbvar.containsKey(objVarName) && ctsOjbvar.isIntArray(objVarName)) {
                        final int[] objVarValue = ctsOjbvar.getIntArray(objVarName);
                        if ((objVarValue != null) && (objVarValue.length > 0)) {
                            for (int anObjVarValue : objVarValue) {
                                abilities.add(anObjVarValue);
                            }
                        }
                        ++j;
                    } else {
                        break;
                    }
                }
            }
        }
        if (!abilities.isEmpty())
        {
            Vector abilitiesCurrent = utils.getResizeableIntBatchObjVar(player, beast_lib.PLAYER_KNOWN_SKILLS_LIST);
            boolean updateRequired = false;
            if ((abilitiesCurrent == null) || (abilitiesCurrent.size() <= 0))
            {
                updateRequired = true;
            }
            else 
            {
                HashSet abilitiesCurrentNoDupes = new HashSet();
                for (Object anAbilitiesCurrent : abilitiesCurrent) {
                    abilitiesCurrentNoDupes.add(Integer.valueOf((Integer) anAbilitiesCurrent));
                }
                Integer ability;
                for (Object abilitiesCurrentNoDupe : abilitiesCurrentNoDupes) {
                    ability = (Integer) abilitiesCurrentNoDupe;
                    abilities.add(ability);
                }
                if (abilities.size() != abilitiesCurrentNoDupes.size())
                {
                    updateRequired = true;
                }
            }
            if (updateRequired)
            {
                int[] abilitiesNew = new int[abilities.size()];
                Iterator abilitiesIterator = abilities.iterator();
                String strAbilitiesNew = "";
                int i = 0;
                Integer ability;
                while (abilitiesIterator.hasNext() && (i < abilitiesNew.length))
                {
                    ability = (Integer) abilitiesIterator.next();
                    abilitiesNew[i] = ability;
                    strAbilitiesNew += "" + abilitiesNew[i] + ", ";
                    ++i;
                }
                String strAbilitiesCurrent = "";
                if ((abilitiesCurrent != null) && (abilitiesCurrent.size() > 0))
                {
                    for (Object anAbilitiesCurrent : abilitiesCurrent) {
                        strAbilitiesCurrent += "" + (Integer) anAbilitiesCurrent + ", ";
                    }
                }
                CustomerServiceLog("CharacterTransferRetroactiveHistory", "changing " + beast_lib.PLAYER_KNOWN_SKILLS_LIST + " objvar for " + player + " from (" + strAbilitiesCurrent + ") to (" + strAbilitiesNew + ")");
                utils.setBatchObjVar(player, beast_lib.PLAYER_KNOWN_SKILLS_LIST, abilitiesNew);
            }
        }
    }
    public static void updateHousePackupCTSObjvars(obj_id player, dictionary[] ctsOjbvars) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if ((ctsOjbvars == null) || (ctsOjbvars.length <= 0))
        {
            return;
        }
        int[] housePackupNewValues = 
        {
            0,
            0
        };
        for (dictionary ctsOjbvar : ctsOjbvars) {
            if ((ctsOjbvar != null) && ctsOjbvar.containsKey(player_structure.HOUSE_PACKUP_ARRAY_OBJVAR) && ctsOjbvar.isIntArray(player_structure.HOUSE_PACKUP_ARRAY_OBJVAR)) {
                int[] housePackupCTSValues = ctsOjbvar.getIntArray(player_structure.HOUSE_PACKUP_ARRAY_OBJVAR);
                if ((housePackupCTSValues != null) && (housePackupCTSValues.length == 2)) {
                    if (housePackupCTSValues[0] > 0) {
                        housePackupNewValues[0] += housePackupCTSValues[0];
                    }
                    if (housePackupCTSValues[1] > 0) {
                        housePackupNewValues[1] += housePackupCTSValues[1];
                    }
                }
            }
        }
        if ((housePackupNewValues[0] > 0) || (housePackupNewValues[1] > 0))
        {
            int[] housePackupCurrentValues = 
            {
                0,
                0
            };
            if (hasObjVar(player, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR))
            {
                housePackupCurrentValues = getIntArrayObjVar(player, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR);
            }
            if ((housePackupCurrentValues != null) && (housePackupCurrentValues.length == 2))
            {
                housePackupNewValues[0] += housePackupCurrentValues[0];
                housePackupNewValues[1] += housePackupCurrentValues[1];
                CustomerServiceLog("CharacterTransferRetroactiveHistory", "changing " + player_structure.HOUSE_PACKUP_ARRAY_OBJVAR + " objvar for " + player + " from (" + housePackupCurrentValues[0] + ", " + housePackupCurrentValues[1] + ") to (" + housePackupNewValues[0] + ", " + housePackupNewValues[1] + ")");
                setObjVar(player, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR, housePackupNewValues);
            }
        }
    }
    public static String getOnlineOfflineStatus(obj_id player) throws InterruptedException
    {
        String returnData = "";
        if (!isPlayer(player))
        {
            returnData = "Deleted";
        }
        else if (!isPlayerConnected(player))
        {
            int lastLoginTime = getPlayerLastLoginTime(player);
            if (lastLoginTime > 0)
            {
                int timeDifference = getCalendarTime() - lastLoginTime;
                if (timeDifference > 0)
                {
                    returnData = "Offline " + utils.padTimeDHMS(timeDifference);
                }
                else 
                {
                    returnData = "Offline ????d:??h:??m:??s";
                }
            }
            else 
            {
                returnData = "Unknown";
            }
        }
        else 
        {
            String locText = "Unknown";
            dictionary playerLoc = getConnectedPlayerLocation(player);
            if (playerLoc == null)
            {
                return locText;
            }
            String planet = playerLoc.getString("planet");
            if (planet != null && planet.length() > 0)
            {
                locText = localize(new string_id("planet_n", planet));
            }
            String region = playerLoc.getString("region");
            if (region != null && region.length() > 0)
            {
                locText += ": " + localize(new string_id(region.substring(1, region.indexOf(":")), region.substring(region.indexOf(":") + 1, region.length())));
            }
            String city = playerLoc.getString("playerCity");
            if (city != null && city.length() > 0)
            {
                locText += ", " + city;
            }
            returnData = "Online " + locText;
        }
        return returnData;
    }
    public static String localizeSIDString(String text) throws InterruptedException
    {
        if (text == null || text.length() < 1)
        {
            return null;
        }
        int left = 0;
        if (text.startsWith("@"))
        {
            left = 1;
        }
        if (!text.contains(":"))
        {
            return text;
        }
        return localize(new string_id(text.substring(left, text.indexOf(":")), text.substring(text.indexOf(":") + 1, text.length())));
    }
    public static boolean colorizeItemFromWidget(obj_id player, obj_id item, String params) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(item) || !exists(item))
        {
            utils.removeScriptVar(player, "veteranRewardItemColor.color_setting");
            return false;
        }
        if (params == null || params.equals(""))
        {
            utils.removeScriptVar(player, "veteranRewardItemColor.color_setting");
            return false;
        }
        String[] colorArray = split(params, ' ');
        if (colorArray == null || colorArray.length <= 0)
        {
            utils.removeScriptVar(player, "veteranRewardItemColor.color_setting");
            return false;
        }
        for (int i = 0; i < colorArray.length; i += 2)
        {
            if (colorArray[i] == null || colorArray[i].equals(""))
            {
                break;
            }
            hue.setColor(item, colorArray[i], utils.stringToInt(colorArray[i + 1]));
        }
        utils.removeScriptVar(player, "veteranRewardItemColor.color_setting");
        return true;
    }
    public static boolean validateSkillModsAttached(obj_id item) throws InterruptedException
    {
        return true;
    }
    public static int getNumCreaturesForSpawnLimit() throws InterruptedException
    {
        final int intNumCreatures = getNumAI() - (getNumHibernatingAI() / 2);
        if (intNumCreatures <= 0)
        {
            return 0;
        }
        return intNumCreatures;
    }
    public static boolean inDebugMode() throws InterruptedException {
        return (utils.getIntConfigSetting("GameServer", "debugMode") == 1);
    }
}
