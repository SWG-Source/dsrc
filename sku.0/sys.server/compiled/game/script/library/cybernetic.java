package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.buff;
import script.library.hue;
import script.library.money;
import script.library.movement;
import script.library.pclib;
import script.library.sui;
import script.library.utils;
import java.util.HashSet;
import java.util.Vector;

public class cybernetic extends script.base_script
{
    public cybernetic()
    {
    }
    public static final String CYBORG_TABLE = "datatables/cybernetic/cybernetic.iff";
    public static final String CYBORG_LEGS = "object/tangible/wearables/cybernetic/s01/cybernetic_s01_legs.iff";
    public static final String CYBORG_LEFT_ARM = "object/tangible/wearables/cybernetic/s01/cybernetic_s01_arm_l.iff";
    public static final String CYBORG_RIGHT_ARM = "object/tangible/wearables/cybernetic/s01/cybernetic_s01_arm_r.iff";
    public static final String CYBORG_TORSO = "object/tangible/wearables/cybernetic/s01/cybernetic_s01_torso.iff";
    public static final int MAX_INSTALLED = 4;
    public static final int CYBERNETIC_TORSO_COST = 6;
    public static final int CYBERNETIC_LEGS_COST = 5;
    public static final int CYBERNETIC_FULL_ARM_COST = 3;
    public static final int CYBERNETIC_FOREARM_COST = 2;
    public static final int CYBERNETIC_HAND_COST = 1;
    public static final String PID_VAR = "cybernetics";
    public static void installCybernetics(obj_id player, obj_id npc) throws InterruptedException
    {
        if (sui.hasPid(player, cybernetic.PID_VAR))
        {
            int pid = sui.getPid(player, PID_VAR);
            forceCloseSUIPage(pid);
        }
        showCyberneticsPage(player, npc, CYBERNETICS_UI_OPENTYPE_INSTALL);
    }
    public static void installCyberneticItem(obj_id player, obj_id npc, obj_id item) throws InterruptedException
    {
        if (!hasScript(player, "cybernetic.cybernetic_player"))
        {
            attachScript(player, "cybernetic.cybernetic_player");
        }
        if (hasObjVar(item, "biolink.id"))
        {
            obj_id biolink_id = getObjIdObjVar(item, "biolink.id");
            if ((player != biolink_id) && (biolink_id != utils.OBJ_ID_BIO_LINK_PENDING) && (biolink_id != null))
            {
                sendSystemMessage(player, new string_id("ep3/cybernetic", "bio_link_mismatch"));
                return;
            }
        }
        int amt = dataTableGetInt(CYBORG_TABLE, getTemplateName(item), "installCost");
        verifyInstallPayment(npc, player, amt, item);
    }
    public static void unInstallCybernetics(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasScript(player, "cybernetic.cybernetic_player"))
        {
            attachScript(player, "cybernetic.cybernetic_player");
        }
        if (sui.hasPid(player, cybernetic.PID_VAR))
        {
            int pid = sui.getPid(player, cybernetic.PID_VAR);
            forceCloseSUIPage(pid);
        }
        showCyberneticsPage(player, npc, CYBERNETICS_UI_OPENTYPE_UNINSTALL);
    }
    public static void unInstallCyberneticItem(obj_id player, obj_id npc, obj_id item) throws InterruptedException
    {
        int amt = dataTableGetInt(CYBORG_TABLE, getTemplateName(item), "removeCost");
        verifyUnInstallPayment(npc, player, amt, item);
    }
    public static void repairCybernetics(obj_id player, obj_id npc) throws InterruptedException
    {
        if (sui.hasPid(player, cybernetic.PID_VAR))
        {
            int pid = sui.getPid(player, cybernetic.PID_VAR);
            forceCloseSUIPage(pid);
        }
        showCyberneticsPage(player, npc, CYBERNETICS_UI_OPENTYPE_REPAIR);
    }
    public static void repairCyberneticItem(obj_id player, obj_id npc, obj_id item) throws InterruptedException
    {
        int costPerPoint = dataTableGetInt(CYBORG_TABLE, getTemplateName(item), "repairCost");
        int itemHps = getHitpoints(item);
        int maxHps = getMaxHitpoints(item);
        int amt = (maxHps - itemHps) * costPerPoint;
        maxHps--;
        if (itemHps >= maxHps || maxHps == 1 || amt <= 0)
        {
            sendSystemMessage(player, new string_id("ep3/cybernetic", "no_repair_needed"));
        }
        else 
        {
            verifyRepairPayment(npc, player, amt, item);
        }
    }
    public static boolean hasMaxInstalled(obj_id player) throws InterruptedException
    {
        return false;
    }
    public static boolean oldHasMaxInstalled(obj_id player) throws InterruptedException
    {
        obj_id[] contents = getContents(player);
        if (contents == null || contents.length == 0)
        {
            return false;
        }
        int count = 0;
        for (int intI = 0; intI < contents.length; intI++)
        {
            String templateName = getTemplateName(contents[intI]);
            if (hasScript(contents[intI], "cybernetic.cybernetic_item") || templateName.indexOf("cybernetic") > -1)
            {
                ++count;
                if (count >= MAX_INSTALLED)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static int getPlayerInstalledCyberneticCount(obj_id player) throws InterruptedException
    {
        obj_id[] allSlots = getAllWornItems(player, false);
        HashSet duplicateEater = new HashSet();
        for (int i = 0; i < allSlots.length; i++)
        {
            duplicateEater.add(allSlots[i]);
        }
        obj_id[] contents = new obj_id[duplicateEater.size()];
        duplicateEater.toArray(contents);
        int count = 0;
        LOG("sissynoid", "contents length: " + contents.length);
        for (int intI = 0; intI < contents.length; intI++)
        {
            LOG("sissynoid", "Contents: (" + contents[intI] + ") ::::: " + getTemplateName(contents[intI]));
            String templateName = getTemplateName(contents[intI]);
            if (hasScript(contents[intI], "cybernetic.cybernetic_item") || templateName.indexOf("cybernetic") > -1)
            {
                count += getCyberneticPointValue(contents[intI]);
            }
        }
        LOG("sissynoid", "Cybernetic Count - " + count);
        return count;
    }
    public static int getCyberneticPointValue(obj_id object) throws InterruptedException
    {
        int value = 0;
        int cyberneticGot = getGameObjectType(object);
        if (isGameObjectTypeOf(object, GOT_cybernetic_torso))
        {
            value += CYBERNETIC_TORSO_COST;
            LOG("sissynoid", "Torso Found: " + object);
        }
        if (isGameObjectTypeOf(object, GOT_cybernetic_legs))
        {
            value += CYBERNETIC_LEGS_COST;
            LOG("sissynoid", "Legs Found: " + object);
        }
        if (isGameObjectTypeOf(object, GOT_cybernetic_arm))
        {
            value += CYBERNETIC_FULL_ARM_COST;
            LOG("sissynoid", "Full Arm Found: " + object);
        }
        if (isGameObjectTypeOf(object, GOT_cybernetic_forearm))
        {
            value += CYBERNETIC_FOREARM_COST;
            LOG("sissynoid", "Forearm Found: " + object);
        }
        if (isGameObjectTypeOf(object, GOT_cybernetic_hand))
        {
            value += CYBERNETIC_HAND_COST;
            LOG("sissynoid", "Hand Found: " + object);
        }
        return value;
    }
    public static void applyDeathCybernetic(obj_id player) throws InterruptedException
    {
        CustomerServiceLog("Death", "Player(" + player + ") has entered 'applyDeathCybernetic' options.");
        if (rand(1, 100) > 5)
        {
            return;
        }
        CustomerServiceLog("Death", "Player(" + player + ") has passed the 'applyDeathCybernetic' random roll!");
        location loc = getLocation(player);
        String area = loc.area;
        int sceneCRC = getStringCrc(area);
        if (area.indexOf("space_") != -1)
        {
            return;
        }
        switch (sceneCRC)
        {
            case (1462210849):
            case (1666541635):
            case (-1344817832):
            case (1372916281):
            case (-1240008136):
            return;
            default:
            break;
        }
        if (hasCyberneticItem(player))
        {
            return;
        }
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return;
        }
        if (rand(0, 99) < 25)
        {
            obj_id shoes = getObjectInSlot(player, "shoes");
            if (isIdValid(shoes))
            {
                if (canPutIn(shoes, inv) != 0)
                {
                    CustomerServiceLog("Death", "Player(" + player + ") was supposed to receive Cybernetics, but we were unable to unequip his Shoes(" + shoes + ").");
                    return;
                }
            }
            obj_id pants1 = getObjectInSlot(player, "pants1");
            if (isIdValid(pants1))
            {
                if (canPutIn(pants1, inv) != 0)
                {
                    CustomerServiceLog("Death", "Player(" + player + ") was supposed to receive Cybernetics, but we were unable to unequip his Pants1(" + pants1 + ").");
                    return;
                }
            }
            obj_id pants2 = getObjectInSlot(player, "pants2");
            if (isIdValid(pants2))
            {
                if (canPutIn(pants2, inv) != 0)
                {
                    CustomerServiceLog("Death", "Player(" + player + ") was supposed to receive Cybernetics, but we were unable to unequip his Pants2(" + pants2 + ").");
                    return;
                }
            }
            if (isIdValid(shoes))
            {
                if (!putIn(shoes, inv))
                {
                    CustomerServiceLog("Death", "Player(" + player + ") was supposed to receive Cybernetics, but we failed the second check to un-equip his Shoes(" + shoes + ").");
                    return;
                }
            }
            if (isIdValid(pants1))
            {
                if (!putIn(pants1, inv))
                {
                    CustomerServiceLog("Death", "Player(" + player + ") was supposed to receive Cybernetics, but we failed the second check to un-equip his Pants1(" + pants1 + ").");
                    return;
                }
            }
            if (isIdValid(pants2))
            {
                if (!putIn(pants2, inv))
                {
                    CustomerServiceLog("Death", "Player(" + player + ") was supposed to receive Cybernetics, but we failed the second check to un-equip his Pants2(" + pants2 + ").");
                    return;
                }
            }
            obj_id newCybernetic = createObject(CYBORG_LEGS, player, "");
            CustomerServiceLog("Death", "Player(" + player + ") just received Cyborg Legs(" + newCybernetic + ")");
        }
        else 
        {
            String BracerUpperName = "bracer_upper_l";
            String BracerLowerName = "bracer_lower_l";
            int rightArm = rand(0, 1);
            if (rightArm == 1)
            {
                BracerUpperName = "bracer_upper_r";
                BracerLowerName = "bracer_lower_r";
            }
            obj_id upperBracer = getObjectInSlot(player, BracerUpperName);
            if (isIdValid(upperBracer))
            {
                if (canPutIn(upperBracer, inv) != 0)
                {
                    return;
                }
            }
            obj_id lowerBracer = getObjectInSlot(player, BracerLowerName);
            if (isIdValid(lowerBracer))
            {
                if (canPutIn(lowerBracer, inv) != 0)
                {
                    return;
                }
            }
            obj_id chest2 = getObjectInSlot(player, "chest2");
            if (isIdValid(chest2))
            {
                if (canPutIn(chest2, inv) != 0)
                {
                    return;
                }
            }
            if (isIdValid(upperBracer))
            {
                if (!putIn(upperBracer, inv))
                {
                    return;
                }
            }
            if (isIdValid(lowerBracer))
            {
                if (!putIn(lowerBracer, inv))
                {
                    return;
                }
            }
            if (isIdValid(chest2))
            {
                if (!putIn(chest2, inv))
                {
                    return;
                }
            }
            obj_id newCybernetic = obj_id.NULL_ID;
            if (rightArm == 1)
            {
                newCybernetic = createObject(CYBORG_RIGHT_ARM, player, "");
            }
            else 
            {
                newCybernetic = createObject(CYBORG_LEFT_ARM, player, "");
            }
            CustomerServiceLog("Death", "Player(" + player + ") just received a Cyborg Arm(" + newCybernetic + ")");
        }
    }
    public static boolean hasCyberneticItem(obj_id player) throws InterruptedException
    {
        obj_id[] allSlots = getAllWornItems(player, false);
        HashSet duplicateEater = new HashSet();
        for (int i = 0; i < allSlots.length; i++)
        {
            duplicateEater.add(allSlots[i]);
        }
        obj_id[] contents = new obj_id[duplicateEater.size()];
        duplicateEater.toArray(contents);
        for (int intI = 0; intI < contents.length; intI++)
        {
            String templateName = getTemplateName(contents[intI]);
            if ((hasScript(contents[intI], "cybernetic.cybernetic_item") || templateName.indexOf("cybernetic") > -1) && (!isGameObjectTypeOf(contents[intI], GOT_cybernetic_component)))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasCyberneticItemInInventory(obj_id player) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return false;
        }
        obj_id[] contents = getContents(inv);
        if (contents == null || contents.length == 0)
        {
            return false;
        }
        for (int intI = 0; intI < contents.length; intI++)
        {
            String templateName = getTemplateName(contents[intI]);
            if ((hasScript(contents[intI], "cybernetic.cybernetic_item") || templateName.indexOf("cybernetic") > -1) && (!isGameObjectTypeOf(contents[intI], GOT_cybernetic_component)))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasCyberneticsToRepair(obj_id player) throws InterruptedException
    {
        return (hasCyberneticItem(player) || hasCyberneticItemInInventory(player));
    }
    public static obj_id[] getInstalledCybernetics(obj_id player) throws InterruptedException
    {
        obj_id[] allSlots = getAllWornItems(player, false);
        HashSet duplicateEater = new HashSet();
        for (int i = 0; i < allSlots.length; i++)
        {
            duplicateEater.add(allSlots[i]);
        }
        obj_id[] contents = new obj_id[duplicateEater.size()];
        duplicateEater.toArray(contents);
        Vector cyberneticItems = new Vector();
        for (int intI = 0; intI < contents.length; intI++)
        {
            String templateName = getTemplateName(contents[intI]);
            if (hasScript(contents[intI], "cybernetic.cybernetic_item") || templateName.indexOf("cybernetic") > -1)
            {
                cyberneticItems.addElement(contents[intI]);
            }
        }
        if (cyberneticItems == null || cyberneticItems.size() < 1)
        {
            return null;
        }
        obj_id[] ret = new obj_id[cyberneticItems.size()];
        cyberneticItems.toArray(ret);
        return ret;
    }
    public static void applyCyberneticMods(obj_id player, obj_id cyberneticItem) throws InterruptedException
    {
        String templateName = getTemplateName(cyberneticItem);
        if (dataTableSearchColumnForString(templateName, 0, CYBORG_TABLE) == -1)
        {
            return;
        }
        utils.setScriptVar(cyberneticItem, "isSetup", true);
        if (!utils.hasScriptVar(player, "cyberneticItems"))
        {
            String[] installedCybernetics = new String[1];
            installedCybernetics[0] = templateName;
            utils.setScriptVar(player, "cyberneticItems", installedCybernetics);
        }
        else 
        {
            String[] installedCybernetics = utils.getStringArrayScriptVar(player, "cyberneticItems");
            String[] newInstalledCybernetics = new String[installedCybernetics.length + 1];
            newInstalledCybernetics = utils.copyArray(installedCybernetics, newInstalledCybernetics);
            newInstalledCybernetics[installedCybernetics.length] = templateName;
            utils.setScriptVar(player, "cyberneticItems", newInstalledCybernetics);
        }
        applyRunBoostMod(player, templateName);
        grantSpecialCommands(player, templateName);
    }
    public static void removeCyberneticMods(obj_id player, obj_id cyberneticItem) throws InterruptedException
    {
        String templateName = getTemplateName(cyberneticItem);
        if (dataTableSearchColumnForString(templateName, 0, CYBORG_TABLE) == -1)
        {
            return;
        }
        utils.removeScriptVar(cyberneticItem, "isSetup");
        if (!utils.hasScriptVar(player, "cyberneticItems"))
        {
            return;
        }
        String[] installedCybernetics = utils.getStringArrayScriptVar(player, "cyberneticItems");
        if (installedCybernetics == null || installedCybernetics.length < 1)
        {
            utils.removeScriptVar(player, "cyberneticItems");
            return;
        }
        if (installedCybernetics.length == 1)
        {
            if (installedCybernetics[0].equals(templateName))
            {
                utils.removeScriptVar(player, "cyberneticItems");
                removeRunBoostMod(player, templateName);
                revokeSpecialCommands(player, templateName);
            }
            return;
        }
        Vector newInstallList = new Vector();
        for (int i = 0; i < installedCybernetics.length; i++)
        {
            if (!installedCybernetics[i].equals(templateName))
            {
                newInstallList.addElement(installedCybernetics[i]);
            }
        }
        if (newInstallList == null || newInstallList.size() < 1)
        {
            utils.removeScriptVar(player, "cyberneticItems");
        }
        else 
        {
            String[] ret = new String[newInstallList.size()];
            newInstallList.toArray(ret);
            utils.setScriptVar(player, "cyberneticItems", ret);
        }
        removeRunBoostMod(player, templateName);
        revokeSpecialCommands(player, templateName);
    }
    public static void setupArmorValues(obj_id cyberneticItem) throws InterruptedException
    {
        String templateName = getTemplateName(cyberneticItem);
        dictionary parms = dataTableGetRow(CYBORG_TABLE, templateName);
        if (parms == null)
        {
            return;
        }
        float protectionAmount = parms.getFloat("protectionAmount");
        float conditionAmount = parms.getFloat("conditionAmount");
        armor.setArmorDataPercent(cyberneticItem, -1, -1, protectionAmount, conditionAmount);
    }
    public static void applyRunBoostMod(obj_id player, String templateName) throws InterruptedException
    {
        String moveBuff = dataTableGetString(CYBORG_TABLE, templateName, "moveRateBuff");
        if (moveBuff == null || moveBuff.equals(""))
        {
            return;
        }
        buff.applyBuff(player, moveBuff);
    }
    public static void removeRunBoostMod(obj_id player, String templateName) throws InterruptedException
    {
        String moveBuff = dataTableGetString(CYBORG_TABLE, templateName, "moveRateBuff");
        if (moveBuff == null || moveBuff.equals(""))
        {
            return;
        }
        buff.removeBuff(player, moveBuff);
    }
    public static void grantSpecialCommands(obj_id player, String templateName) throws InterruptedException
    {
        String specialCommands = dataTableGetString(CYBORG_TABLE, templateName, "specialCommand");
        if (specialCommands == null || specialCommands.equals(""))
        {
            return;
        }
        if (!hasCommand(player, specialCommands))
        {
            grantCommand(player, specialCommands);
        }
    }
    public static void revokeSpecialCommands(obj_id player, String templateName) throws InterruptedException
    {
        String specialCommands = dataTableGetString(CYBORG_TABLE, templateName, "specialCommand");
        if (specialCommands == null || specialCommands.equals(""))
        {
            return;
        }
        while (hasCommand(player, specialCommands))revokeCommand(player, specialCommands);
    }
    public static void setHueColor(obj_id cyberneticItem) throws InterruptedException
    {
        String templateName = getTemplateName(cyberneticItem);
        int hueColor = dataTableGetInt(CYBORG_TABLE, templateName, "hue");
        if (hueColor > 0)
        {
            hue.setColor(cyberneticItem, "/private/index_color_1", hueColor);
        }
    }
    public static float getThrowRangeMod(obj_id player, float maxRange) throws InterruptedException
    {
        float rangeMod = (float)getSkillStatMod(player, "cybernetic_throw_range");
        if (rangeMod != 0.0f)
        {
            return (maxRange + rangeMod);
        }
        return maxRange;
    }
    public static float getRangedRangeMod(obj_id player, float maxRange) throws InterruptedException
    {
        float rangeMod = (float)getSkillStatMod(player, "cybernetic_ranged_range");
        if (rangeMod != 0.0f)
        {
            return (maxRange + rangeMod);
        }
        return maxRange;
    }
    public static float getCyberneticHealingMod(obj_id player, float maxHealMod) throws InterruptedException
    {
        float healMod = (float)getSkillStatMod(player, "cybernetic_healing_mod");
        if (healMod != 0.0f)
        {
            healMod = healMod / 100;
            return (maxHealMod + healMod);
        }
        return maxHealMod;
    }
    public static float getCyberneticRangedAccuracyMod(obj_id player, float baseAccuracy) throws InterruptedException
    {
        float accMod = (float)getSkillStatMod(player, "cybernetic_ranged_acc");
        if (accMod != 0.0f)
        {
            accMod = accMod / 100;
            return (baseAccuracy + accMod);
        }
        return baseAccuracy;
    }
    public static float getCyberneticMeleeAccuracyMod(obj_id player, float baseAccuracy) throws InterruptedException
    {
        float accMod = (float)getSkillStatMod(player, "cybernetic_melee_acc");
        if (accMod != 0.0f)
        {
            accMod = accMod / 100;
            return (baseAccuracy + accMod);
        }
        return baseAccuracy;
    }
    public static float getCyberneticMeleeDefenseMod(obj_id player, float baseDefense) throws InterruptedException
    {
        float defMod = (float)getSkillStatMod(player, "cybernetic_melee_def");
        if (defMod != 0.0f)
        {
            defMod = defMod / 100;
            return (baseDefense + defMod);
        }
        return baseDefense;
    }
    public static boolean hasCommandoLegs(obj_id player) throws InterruptedException
    {
        return (getSkillStatMod(player, "cybernetic_heavy_weapon_legs") != 0);
    }
    public static void grantCyberneticSkillMods(obj_id player, obj_id cyberneticItem) throws InterruptedException
    {
        String templateName = getTemplateName(cyberneticItem);
        dictionary item = dataTableGetRow(CYBORG_TABLE, templateName);
        if (item == null)
        {
            return;
        }
        int skillMod = item.getInt("throwRangeMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_throw_range", skillMod);
        }
        skillMod = item.getInt("rangedRangeMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_ranged_range", skillMod);
        }
        skillMod = item.getInt("healingMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_healing_mod", skillMod);
        }
        skillMod = item.getInt("meleeAccuracyMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_melee_acc", skillMod);
        }
        skillMod = item.getInt("meleeDefMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_melee_def", skillMod);
        }
        skillMod = item.getInt("rangedAccuracyMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_ranged_acc", skillMod);
        }
        String moveBuff = item.getString("moveRateBuff");
        if (moveBuff != null && !moveBuff.equals(""))
        {
            if (buff.canApplyBuff(player, moveBuff))
            {
                buff.applyBuff(player, moveBuff);
            }
            applySkillStatisticModifier(player, "cybernetic_run_buff", 40);
        }
        skillMod = item.getInt("commandoLegs");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_heavy_weapon_legs", skillMod);
        }
    }
    public static void revokeCyberneticSkillMods(obj_id player, obj_id cyberneticItem) throws InterruptedException
    {
        String templateName = getTemplateName(cyberneticItem);
        dictionary item = dataTableGetRow(CYBORG_TABLE, templateName);
        if (item == null)
        {
            return;
        }
        int skillMod = item.getInt("throwRangeMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_throw_range", (-1 * skillMod));
        }
        skillMod = item.getInt("rangedRangeMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_ranged_range", (-1 * skillMod));
        }
        skillMod = item.getInt("healingMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_healing_mod", (-1 * skillMod));
        }
        skillMod = item.getInt("meleeAccuracyMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_melee_acc", (-1 * skillMod));
        }
        skillMod = item.getInt("meleeDefMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_melee_def", (-1 * skillMod));
        }
        skillMod = item.getInt("rangedAccuracyMod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_ranged_acc", (-1 * skillMod));
        }
        String moveBuff = item.getString("moveRateBuff");
        if (moveBuff != null && !moveBuff.equals(""))
        {
            applySkillStatisticModifier(player, "cybernetic_run_buff", -40);
        }
        skillMod = item.getInt("commandoLegs");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_heavy_weapon_legs", (-1 * skillMod));
        }
    }
    public static void validateSkillMods(obj_id player) throws InterruptedException
    {
        int skillMod = getSkillStatMod(player, "cybernetic_throw_range");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_throw_range", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_ranged_range");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_ranged_range", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_healing_mod");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_healing_mod", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_melee_acc");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_melee_acc", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_melee_def");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_melee_def", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_ranged_acc");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_ranged_acc", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_run_buff");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_run_buff", (-1 * skillMod));
        }
        skillMod = getSkillStatMod(player, "cybernetic_heavy_weapon_legs");
        if (skillMod != 0)
        {
            applySkillStatisticModifier(player, "cybernetic_heavy_weapon_legs", (-1 * skillMod));
        }
        obj_id[] cybernetics = getInstalledCybernetics(player);
        if (cybernetics != null && cybernetics.length > 0)
        {
            for (int i = 0; i < cybernetics.length; ++i)
            {
                grantCyberneticSkillMods(player, cybernetics[i]);
            }
        }
        movement.refresh(player);
    }
    public static void revokeAllOccurancesOfCommand(obj_id player, String commandName) throws InterruptedException
    {
        revokeCommand(player, commandName);
        while (hasCommand(player, commandName))revokeCommand(player, commandName);
    }
    public static boolean doCyborgRevive(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            sendSystemMessage(player, new string_id("ep3/cybernetic", "bad_target"));
            return false;
        }
        if (player == target || !isPlayer(target))
        {
            sendSystemMessage(player, new string_id("ep3/cybernetic", "bad_target"));
            return false;
        }
        if (!hasObjVar(target, pclib.VAR_BEEN_COUPDEGRACED))
        {
            sendSystemMessage(player, new string_id("ep3/cybernetic", "bad_target"));
            return false;
        }
        if (!pvpCanHelp(player, target))
        {
            sendSystemMessage(player, new string_id("ep3/cybernetic", "bad_target"));
            return false;
        }
        else if (!group.inSameGroup(player, target) && !pclib.hasConsent(player, target))
        {
            sendSystemMessage(player, new string_id("ep3/cybernetic", "no_consent"));
            return false;
        }
        pvpHelpPerformed(player, target);
        if (getPosture(player) != POSTURE_CROUCHED)
        {
            setPosture(player, POSTURE_CROUCHED);
        }
        playClientEffectObj(player, "clienteffect/pl_force_heal_self.cef", target, "");
        messageTo(target, "handlePlayerResuscitated", null, 5, false);
        messageTo(player, "handleReviveStand", null, 5, false);
        return true;
    }
    public static obj_id getSpecificCybernetic(obj_id player, String commandName) throws InterruptedException
    {
        int colNum = dataTableFindColumnNumber(CYBORG_TABLE, "specialCommand");
        if (colNum < 0)
        {
            return null;
        }
        int cyberneticRow = dataTableSearchColumnForString(commandName, colNum, CYBORG_TABLE);
        if (cyberneticRow < 0)
        {
            return null;
        }
        String itemName = dataTableGetString(CYBORG_TABLE, cyberneticRow, "templateName");
        if (itemName == null || itemName.equals(""))
        {
            return null;
        }
        obj_id[] installed = getInstalledCybernetics(player);
        if (installed == null || installed.length < 1)
        {
            return null;
        }
        for (int i = 0; i < installed.length; ++i)
        {
            if (itemName.equals(getTemplateName(installed[i])))
            {
                return installed[i];
            }
        }
        revokeAllOccurancesOfCommand(player, commandName);
        return null;
    }
    public static boolean hasUndamagedCybernetic(obj_id player, String commandName) throws InterruptedException
    {
        obj_id cyberneticItem = getSpecificCybernetic(player, commandName);
        if (!isIdValid(cyberneticItem))
        {
            return false;
        }
        int maxHps = getMaxHitpoints(cyberneticItem);
        int hps = getHitpoints(cyberneticItem);
        if (hps > 0)
        {
            return true;
        }
        sendSystemMessage(player, new string_id("ep3/cybernetic", "too_damaged"));
        return false;
    }
    public static void verifyInstallPayment(obj_id npc, obj_id player, int amt, obj_id item) throws InterruptedException
    {
        prose_package prompt = new prose_package();
        prompt = prose.setStringId(prompt, new string_id("ep3/cybernetic", "install_pay_prompt"));
        prompt = prose.setDI(prompt, amt);
        String title = "@ep3/cybernetic:install_pay_title";
        utils.setScriptVar(player, "cyborg.install.amount", amt);
        utils.setScriptVar(player, "cyborg.install.item", item);
        int pid = sui.msgbox(player, player, prompt, sui.OK_CANCEL, title, sui.MSG_QUESTION, "handleInstallPaymentConfirmed");
        sui.setPid(player, pid, PID_VAR);
    }
    public static void verifyUnInstallPayment(obj_id npc, obj_id player, int amt, obj_id item) throws InterruptedException
    {
        prose_package prompt = new prose_package();
        prompt = prose.setStringId(prompt, new string_id("ep3/cybernetic", "remove_pay_prompt"));
        prompt = prose.setDI(prompt, amt);
        String title = "@ep3/cybernetic:remove_pay_title";
        utils.setScriptVar(player, "cyborg.remove.amount", amt);
        utils.setScriptVar(player, "cyborg.remove.item", item);
        int pid = sui.msgbox(player, player, prompt, sui.OK_CANCEL, title, sui.MSG_QUESTION, "handleUnInstallPaymentConfirmed");
        sui.setPid(player, pid, PID_VAR);
    }
    public static void verifyRepairPayment(obj_id npc, obj_id player, int amt, obj_id item) throws InterruptedException
    {
        prose_package prompt = new prose_package();
        prompt = prose.setStringId(prompt, new string_id("ep3/cybernetic", "repair_pay_prompt"));
        prompt = prose.setDI(prompt, amt);
        String title = "@ep3/cybernetic:repair_pay_title";
        utils.setScriptVar(player, "cyborg.repair.amount", amt);
        utils.setScriptVar(player, "cyborg.repair.item", item);
        int pid = sui.msgbox(player, player, prompt, sui.OK_CANCEL, title, sui.MSG_QUESTION, "handleRepairPaymentConfirmed");
        sui.setPid(player, pid, PID_VAR);
    }
    public static boolean canInstallSelectedCybernetic(obj_id player, obj_id cybernetic) throws InterruptedException
    {
        int playerInstalledCount = getPlayerInstalledCyberneticCount(player);
        int playerCyberSkillMod = getEnhancedSkillStatisticModifierUncapped(player, "cybernetic_psychosis_resistance");
        int cyberneticToInstall = getCyberneticPointValue(cybernetic);
        if (playerInstalledCount < 0 || playerCyberSkillMod < 0 || cyberneticToInstall < 0)
        {
            CustomerServiceLog("cyborg", "Player(" + getPlayerFullName(player) + ", " + player + ") can not install Cybernetic (" + cybernetic + ") - Code issues - please contact the Design Team.");
            sendSystemMessage(player, new string_id("ep3/cybernetic", "cannot_install"));
            return false;
        }
        return true;
    }
}
