package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.veteran_deprecated;
import script.library.prose;

public class harvester extends script.base_script
{
    public harvester()
    {
    }
    public static final String OBJVAR_GROUP_REWARDED = "rewarded";
    public static final string_id SID_CONVERT = new string_id("ui_radial", "convert_harvester");
    public static final string_id SID_MAKE_ELITE = new string_id("ui_radial", "make_elite");
    public static final string_id SID_HARVESTER_CONVERTED = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "harvester_converted");
    public static final string_id SID_DEED_CONVERTED = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "deed_converted");
    public static final string_id SID_HARVESTER_CONVERTED_SUBJECT = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "harvester_converted_subject");
    public static final string_id SID_HARVESTER_ALREADY_SELFPOWERED = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "harvester_already_selfpowered");
    public static final string_id SID_CANT_SELF_POWER_ELITE = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "harvester_cant_self_power_elite");
    public static final string_id SID_CANT_SELF_POWER = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "harvester_cant_self_power_normal");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            messageTo(self, "handleVeteranHarvestDestroy", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        boolean isEliteDeed = false;
        String strItemTemplate = getTemplateName(self);
        if (strItemTemplate.equals(player_structure.SELFPOWERED_DEED_ELITE))
        {
            isEliteDeed = true;
        }
        if (!isEliteDeed)
        {
            Vector deedList = getDeedsInInventory(player);
            if (deedList != null && deedList.size() >= 3)
            {
                item.addRootMenu(menu_info_types.SERVER_MENU8, SID_MAKE_ELITE);
            }
        }
        obj_id target = getIntendedTarget(player);
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isHarvester(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (getOwner(target) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(target, "selfpowered"))
        {
            sendSystemMessage(player, SID_HARVESTER_ALREADY_SELFPOWERED);
            return SCRIPT_CONTINUE;
        }
        if (player_structure.isEliteHarvester(target) && !isEliteDeed)
        {
            return SCRIPT_CONTINUE;
        }
        if (!player_structure.isEliteHarvester(target) && isEliteDeed)
        {
            return SCRIPT_CONTINUE;
        }
        item.addRootMenu(menu_info_types.ITEM_USE, SID_CONVERT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        boolean isEliteDeed = false;
        String strItemTemplate = getTemplateName(self);
        if (strItemTemplate.equals(player_structure.SELFPOWERED_DEED_ELITE))
        {
            isEliteDeed = true;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
            {
                return SCRIPT_CONTINUE;
            }
            if (utils.getContainingPlayer(self) != player)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id target = getIntendedTarget(player);
            if (!isIdValid(target))
            {
                return SCRIPT_CONTINUE;
            }
            if (!player_structure.isHarvester(target))
            {
                return SCRIPT_CONTINUE;
            }
            if (getOwner(target) != player)
            {
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(target, "selfpowered"))
            {
                sendSystemMessage(player, SID_HARVESTER_ALREADY_SELFPOWERED);
                return SCRIPT_CONTINUE;
            }
            if (player_structure.isEliteHarvester(target) && !isEliteDeed)
            {
                sendSystemMessage(player, SID_CANT_SELF_POWER_ELITE);
                return SCRIPT_CONTINUE;
            }
            if (!player_structure.isEliteHarvester(target) && isEliteDeed)
            {
                sendSystemMessage(player, SID_CANT_SELF_POWER);
                return SCRIPT_CONTINUE;
            }
            CustomerServiceLog("veteran", "Player %TU converting harvester " + target + " to use 0 power", player);
            setPowerRate(target, 0);
            setObjVar(target, "selfpowered", 1);
            setObjVar(self, OBJVAR_GROUP_REWARDED, true);
            attachScript(target, "systems.veteran_reward.harvester_examine");
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, SID_HARVESTER_CONVERTED);
            pp = prose.setTT(pp, getEncodedName(target));
            sendSystemMessageProse(player, pp);
            utils.sendMail(SID_HARVESTER_CONVERTED_SUBJECT, pp, player, "@" + veteran_deprecated.SID_VETERAN_NEW_REWARD_FROM);
            messageTo(self, "handleVeteranHarvestDestroy", null, 2, false);
        }
        else if (item == menu_info_types.SERVER_MENU8)
        {
            if (!isEliteDeed)
            {
                Vector deedList = getDeedsInInventory(player);
                if (deedList != null && deedList.size() >= 3)
                {
                    if (convertNonEliteToEliteDeeds(deedList, player))
                    {
                        sendSystemMessage(player, SID_DEED_CONVERTED);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVeteranHarvestDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public Vector getDeedsInInventory(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        obj_id[] objContents = utils.getFilteredPlayerContents(player);
        if ((objContents == null) || (objContents.length == 0))
        {
            return null;
        }
        Vector deedList = new Vector();
        deedList.setSize(0);
        for (int n = 0; n < objContents.length; n++)
        {
            String strItemTemplate = getTemplateName(objContents[n]);
            if (strItemTemplate.equals(player_structure.SELFPOWERED_DEED))
            {
                deedList.add(objContents[n]);
            }
        }
        return deedList;
    }
    public boolean convertNonEliteToEliteDeeds(Vector deedList, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id playerInventory = utils.getInventoryContainer(player);
        if (!isIdValid(playerInventory))
        {
            return false;
        }
        obj_id[] tempDeedList = new obj_id[deedList.size()];
        deedList.toArray(tempDeedList);
        if (tempDeedList != null)
        {
            int numDestroyed = 0;
            for (int n = 0; n < 3; n++)
            {
                destroyObject(tempDeedList[n]);
                numDestroyed++;
            }
            if (numDestroyed == 3)
            {
                createObjectOverloaded(player_structure.SELFPOWERED_DEED_ELITE, playerInventory);
                return true;
            }
        }
        return false;
    }
}
