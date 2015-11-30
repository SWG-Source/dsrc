package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.groundquests;
import script.library.sui;
import script.library.utils;

public class mtp_instance_enter extends script.base_script
{
    public mtp_instance_enter()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (getDistance(player, self) > 6.0f)
        {
            return SCRIPT_CONTINUE;
        }
        item.addRootMenu(menu_info_types.ITEM_USE, new string_id("building_name", "mtp_hideout_instance_enter"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            Vector startingTriggers = getStartingTrigger(player);
            if (startingTriggers != null && startingTriggers.size() > 0)
            {
                if (startingTriggers.size() == 1)
                {
                    utils.setScriptVar(player, "mtp_currentInstanceTrigger", ((String)startingTriggers.get(0)));
                    utils.setScriptVar(player, "mtp_hideoutCell", getLocation(self).cell);
                    instance.requestInstanceMovement(player, "mtp_hideout_instance", ((String)startingTriggers.get(0)));
                }
                else 
                {
                    utils.setScriptVar(player, "mtp_availableStartingTriggers", startingTriggers);
                    String title = utils.packStringId(new string_id("theme_park/corellia/quest", "mtp_choose_instance_title"));
                    String prompt = utils.packStringId(new string_id("theme_park/corellia/quest", "mtp_choose_instance_prompt"));
                    String[] availableInstances = new String[startingTriggers.size()];
                    for (int i = 0; i < startingTriggers.size(); i++)
                    {
                        String instanceName = utils.packStringId(new string_id("theme_park/corellia/quest", ((String)startingTriggers.get(i))));
                        availableInstances[i] = instanceName;
                    }
                    int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, availableInstances, "mtpInstanceSelected");
                    if (pid > -1)
                    {
                        String scriptvar_path = "mtp_choose_instance." + player;
                        utils.setScriptVar(self, scriptvar_path + ".pid", pid);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("elevator_text", "mtp_unable_to_descend"));
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int mtpInstanceSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        String scriptvar_path = "mtp_choose_instance." + player;
        String oldPidVar = scriptvar_path + ".pid";
        if (utils.hasScriptVar(self, oldPidVar))
        {
            utils.removeScriptVar(self, oldPidVar);
        }
        if (btn == sui.BP_CANCEL)
        {
            utils.removeScriptVar(player, "mtp_availableStartingTriggers");
            return SCRIPT_CONTINUE;
        }
        String[] startingTriggers = utils.getStringArrayScriptVar(player, "mtp_availableStartingTriggers");
        utils.removeScriptVar(player, "mtp_availableStartingTriggers");
        utils.setScriptVar(player, "mtp_currentInstanceTrigger", startingTriggers[idx]);
        utils.setScriptVar(player, "mtp_hideoutCell", getLocation(self).cell);
        instance.requestInstanceMovement(player, "mtp_hideout_instance", startingTriggers[idx]);
        return SCRIPT_CONTINUE;
    }
    public Vector getStartingTrigger(obj_id player) throws InterruptedException
    {
        Vector startingTriggers = new Vector();
        startingTriggers.setSize(0);
        if (groundquests.isTaskActive(player, "mtp_hideout_instance_kill_all_droids", "gotoInstance"))
        {
            utils.addElement(startingTriggers, "mtp_kill_all_droids");
        }
        if (groundquests.isTaskActive(player, "mtp_hideout_instance_kill_specific_droids", "gotoInstance"))
        {
            utils.addElement(startingTriggers, "mtp_kill_specific_droids");
        }
        if (groundquests.isTaskActive(player, "mtp_hideout_instance_recover_supplies", "gotoInstance"))
        {
            utils.addElement(startingTriggers, "mtp_recover_supplies");
        }
        if (groundquests.isTaskActive(player, "mtp_hideout_instance_escort_trapped_meatlump", "gotoInstance"))
        {
            utils.addElement(startingTriggers, "mtp_escort_trapped_meatlump");
        }
        return startingTriggers;
    }
}
