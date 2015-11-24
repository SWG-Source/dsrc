package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.create;
import script.library.incubator;
import script.library.player_structure;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class tcg_creature_armor_statue extends script.base_script
{
    public tcg_creature_armor_statue()
    {
    }
    public static final string_id SID_VAC_ARMOR = new string_id("tcg", "start_statue");
    public static final string_id SID_NOT_IN_HOUSE = new string_id("tcg", "not_in_house");
    public static final string_id SID_NO_CREATURE_IN_DP = new string_id("tcg", "no_creature_in_dp");
    public static final string_id SID_VAC_ARMOR_ADDED = new string_id("tcg", "armor_added");
    public static final string_id SID_VAC_ARMOR_REMOVED = new string_id("tcg", "armor_removed");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (checkLocation(self, player))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, SID_VAC_ARMOR);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE && isIdValid(player))
        {
            if (!checkLocation(self, player))
            {
                sendSystemMessage(player, SID_NOT_IN_HOUSE);
                return SCRIPT_CONTINUE;
            }
            obj_id[] eligibleMounts = getCreatureMountList(self, player);
            if (eligibleMounts != null && eligibleMounts.length > 0)
            {
                startArmorSwap(self, player, eligibleMounts);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, SID_NO_CREATURE_IN_DP);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkLocation(obj_id item, obj_id player) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (!utils.isInHouseCellSpace(item))
        {
            return false;
        }
        if (!utils.isNestedWithinAPlayer(item))
        {
            obj_id house = getTopMostContainer(item);
            if (isIdValid(house) && player_structure.isBuilding(house) && player_structure.isOwner(house, player))
            {
                return true;
            }
        }
        return false;
    }
    public obj_id[] getCreatureMountList(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id dataPad = utils.getPlayerDatapad(player);
        Vector data = getResizeableContents(dataPad);
        Vector ownedEligibleMounts = new Vector();
        for (int i = 0; i < data.size(); i++)
        {
            obj_id bcd = ((obj_id)data.get(i));
            if (hasObjVar(bcd, "pet.creatureName"))
            {
                String cName = getStringObjVar(bcd, "pet.creatureName");
                String[] eligibleMounts = getEligibleBeastsList(self);
                if (eligibleMounts != null && eligibleMounts.length > 0)
                {
                    for (int j = 0; j < eligibleMounts.length; j++)
                    {
                        String mountName = eligibleMounts[j];
                        if (cName.equals(mountName))
                        {
                            ownedEligibleMounts.addElement(bcd);
                        }
                    }
                }
            }
        }
        obj_id[] mountsArray = new obj_id[ownedEligibleMounts.size()];
        ownedEligibleMounts.toArray(mountsArray);
        Arrays.sort(mountsArray);
        return mountsArray;
    }
    public String[] getEligibleBeastsList(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "armored_creature_type"))
        {
            String datatable = "datatables/tcg/armored_creature_swap.iff";
            String armoredCreatureType = getStringObjVar(self, "armored_creature_type");
            String eligibleBeastsString = dataTableGetString(datatable, armoredCreatureType, "eligible_beasts");
            if (eligibleBeastsString != null && eligibleBeastsString.length() > 0)
            {
                String[] eligibleBeasts = split(eligibleBeastsString, ',');
                if (eligibleBeasts != null && eligibleBeasts.length > 0)
                {
                    return eligibleBeasts;
                }
            }
        }
        return null;
    }
    public String getArmoredMountName(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "armored_creature_type"))
        {
            String datatable = "datatables/tcg/armored_creature_swap.iff";
            String armoredCreatureType = getStringObjVar(self, "armored_creature_type");
            String armoredMountName = dataTableGetString(datatable, armoredCreatureType, "armored_version");
            if (armoredMountName != null && armoredMountName.length() > 0)
            {
                return armoredMountName;
            }
        }
        return "";
    }
    public String getMountName(obj_id bcd, obj_id player) throws InterruptedException
    {
        String mountType = getStringObjVar(bcd, "pet.creatureName");
        string_id mountNameSid = new string_id(ai_lib.CREATURE_NAME_FILE, mountType);
        String mountName = localize(mountNameSid);
        if (hasObjVar(bcd, "pet.mountName"))
        {
            mountName = getStringObjVar(bcd, "pet.mountName");
        }
        return mountName;
    }
    public void startArmorSwap(obj_id self, obj_id player, obj_id[] playerEligibleMounts) throws InterruptedException
    {
        String[] mountNameList = new String[playerEligibleMounts.length];
        for (int i = 0; i < playerEligibleMounts.length; i++)
        {
            obj_id bcd = playerEligibleMounts[i];
            String mountName = getMountName(bcd, player);
            String mountType = getStringObjVar(bcd, "pet.creatureName");
            String armoredMountName = getArmoredMountName(self);
            if (mountType.equals(armoredMountName))
            {
                mountName = mountName + " - Armored - Remove Armor from the Mount";
            }
            else 
            {
                mountName = mountName + " - Unarmored - Add Armor to the Mount";
            }
            mountNameList[i] = mountName;
        }
        stageMenu(self, player, "Please select a mount.", "Armored Mount Statue", mountNameList, "mountChoice", true, "choice", "mountList.mountNames");
    }
    public int mountChoice(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id container = getTopMostContainer(getSelf());
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(container))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] eligibleMounts = getCreatureMountList(self, player);
        String armoredMountName = getArmoredMountName(self);
        obj_id bcd = eligibleMounts[idx];
        if (hasObjVar(bcd, "pet.creatureName"))
        {
            String mountType = getStringObjVar(bcd, "pet.creatureName");
            if (mountType.equals(armoredMountName))
            {
                String unarmoredMountName = getStringObjVar(bcd, "unarmoredMountName");
                removeObjVar(bcd, "unarmoredMountName");
                setObjVar(bcd, "pet.creatureName", unarmoredMountName);
                sendSystemMessage(player, SID_VAC_ARMOR_REMOVED);
            }
            else 
            {
                setObjVar(bcd, "pet.creatureName", armoredMountName);
                setObjVar(bcd, "unarmoredMountName", mountType);
                sendSystemMessage(player, SID_VAC_ARMOR_ADDED);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void stageMenu(obj_id self, obj_id player, String prompt, String title, String[] options, String myHandler, boolean cancel, String PIDVar, String scriptVar) throws InterruptedException
    {
        closeOldWindow(player, scriptVar);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, false, false);
        sui.showSUIPage(pid);
        setWindowPid(player, pid, PIDVar);
        utils.setScriptVar(player, scriptVar, options);
    }
    public void closeOldWindow(obj_id player, String scriptVar) throws InterruptedException
    {
        if (utils.hasScriptVar(player, scriptVar))
        {
            int oldpid = utils.getIntScriptVar(player, scriptVar);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVarTree(player, scriptVar);
        }
    }
    public void setWindowPid(obj_id player, int pid, String scriptVar) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, scriptVar, pid);
        }
    }
}
