package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.sui;
import script.library.static_item;
import script.library.sui;

public class tcg_varactyl_armor extends script.terminal.base.base_terminal
{
    public tcg_varactyl_armor()
    {
    }
    public static final string_id SID_VAC_ARMOR = new string_id("tcg", "start_statue");
    public static final string_id SID_NOT_IN_HOUSE = new string_id("tcg", "not_in_house");
    public static final string_id SID_NO_VARACYL_IN_DP = new string_id("tcg", "no_varactyl_in_dp");
    public static final string_id SID_VAC_ARMOR_ADDED = new string_id("tcg", "armor_added");
    public static final string_id SID_VAC_ARMOR_REMOVED = new string_id("tcg", "armor_removed");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return super.OnObjectMenuRequest(self, player, mi);
        }
        if (checkLocation(self, player))
        {
            int startArmorSwap = mi.addRootMenu(menu_info_types.ITEM_USE, SID_VAC_ARMOR);
        }
        return super.OnObjectMenuRequest(self, player, mi);
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
            obj_id[] varMounts = getVaractylMountList(player);
            if (varMounts.length > 0)
            {
                startArmorSwap(player, varMounts);
                return SCRIPT_CONTINUE;
            }
            sendSystemMessage(player, SID_NO_VARACYL_IN_DP);
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
    public obj_id[] getVaractylMountList(obj_id player) throws InterruptedException
    {
        obj_id dataPad = utils.getPlayerDatapad(player);
        Vector data = getResizeableContents(dataPad);
        Vector varMounts = new Vector();
        for (int i = 0; i < data.size(); i++)
        {
            if (utils.hasObjVar(((obj_id)data.get(i)), "pet.creatureName"))
            {
                String cName = utils.getStringObjVar(((obj_id)data.get(i)), "pet.creatureName");
                if (cName.equals("ep3_mount_varactyl") || cName.equals("tcg_armored_mount_varactyl") || cName.equals("ep3_qst_reward_varactyl"))
                {
                    varMounts.addElement(((obj_id)data.get(i)));
                }
            }
        }
        obj_id[] mountsArray = new obj_id[varMounts.size()];
        varMounts.toArray(mountsArray);
        Arrays.sort(mountsArray);
        return mountsArray;
    }
    public void startArmorSwap(obj_id player, obj_id[] playerVarMounts) throws InterruptedException
    {
        String[] mountNameList = new String[playerVarMounts.length];
        String mountName;
        String mountType;
        for (int i = 0; i < playerVarMounts.length; i++)
        {
            if (utils.hasObjVar(playerVarMounts[i], "pet.mountName"))
            {
                mountName = getStringObjVar(playerVarMounts[i], "pet.mountName");
            }
            else 
            {
                mountName = "Varactyl";
            }
            mountType = utils.getStringObjVar(playerVarMounts[i], "pet.creatureName");
            if (mountType.equals("ep3_mount_varactyl") || mountType.equals("ep3_qst_reward_varactyl"))
            {
                mountName = mountName + " - Unarmored - Add Armor to the Mount";
            }
            else if (mountType.equals("tcg_armored_mount_varactyl"))
            {
                mountName = mountName + " - Armored - Remove Armor from the Mount";
            }
            mountNameList[i] = mountName;
        }
        obj_id self = getSelf();
        stageMenu(self, player, "Please select a Varactyle mount.", "Armored Varactyle Statue", mountNameList, "mountChoice", true, "choice", "mountList.mountNames");
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
        obj_id[] varMounts = getVaractylMountList(player);
        obj_id mount = varMounts[idx];
        if (utils.hasObjVar(mount, "pet.creatureName"))
        {
            String mountType = utils.getStringObjVar(mount, "pet.creatureName");
            if (mountType.equals("ep3_mount_varactyl") || mountType.equals("ep3_qst_reward_varactyl"))
            {
                setObjVar(mount, "pet.creatureName", "tcg_armored_mount_varactyl");
                sendSystemMessage(player, SID_VAC_ARMOR_ADDED);
            }
            if (mountType.equals("tcg_armored_mount_varactyl"))
            {
                setObjVar(mount, "pet.creatureName", "ep3_mount_varactyl");
                sendSystemMessage(player, SID_VAC_ARMOR_REMOVED);
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
