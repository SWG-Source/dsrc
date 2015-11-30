package script.theme_park.dungeon.nova_orion_station;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.groundquests;
import script.library.utils;

public class nova_orion_gravestone_node extends script.base_script
{
    public nova_orion_gravestone_node()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "graveEventInProgress"))
        {
            utils.removeScriptVar(self, "graveEventInProgress");
        }
        if (utils.hasScriptVar(self, "gravestoneSpawned"))
        {
            utils.removeScriptVar(self, "gravestoneSpawned");
        }
        if (utils.hasScriptVar(self, "katiaraSpawned"))
        {
            utils.removeScriptVar(self, "katiaraSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "graveEventInProgress"))
        {
            utils.removeScriptVar(self, "graveEventInProgress");
        }
        if (utils.hasScriptVar(self, "gravestoneSpawned"))
        {
            utils.removeScriptVar(self, "gravestoneSpawned");
        }
        if (utils.hasScriptVar(self, "katiaraSpawned"))
        {
            utils.removeScriptVar(self, "katiaraSpawned");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (isGod(player))
        {
            int menu = menuInfo.addRootMenu(menu_info_types.SERVER_MENU1, new string_id("ui_radial", "wave_event_reset"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            if (isGod(player))
            {
                sendSystemMessage(player, "Reseting gravestone event...", "");
                dictionary webster = new dictionary();
                webster.put("player", player);
                messageTo(self, "cleanupNoFinaleEvent", webster, 1, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startNoFinaleEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            if (!utils.hasScriptVar(self, "graveEventInProgress"))
            {
                utils.setScriptVar(self, "graveEventInProgress", player);
                String gravestoneTemplate = "object/tangible/quest/nova_orion/nova_orion_katiara_gravestone_display.iff";
                location here = getLocation(self);
                here.x = -26.91f;
                here.y = 29.00f;
                here.z = 15.11f;
                obj_id gravestone = createObject(gravestoneTemplate, here);
                setYaw(gravestone, 130.0f);
                utils.setScriptVar(self, "gravestoneSpawned", gravestone);
                utils.setScriptVar(gravestone, "objParent", self);
                here.x = -28.5f;
                here.y = 29.00f;
                here.z = 14.2f;
                obj_id katiara = create.staticObject("nova_orion_katiaras_ghost", here);
                setYaw(katiara, 125.0f);
                utils.setScriptVar(self, "katiaraSpawned", katiara);
                utils.setScriptVar(katiara, "objParent", self);
                utils.setScriptVar(katiara, "myPlayer", player);
                dictionary webster = new dictionary();
                webster.put("player", player);
                messageTo(self, "safeResetNoFinaleEvent", webster, 300, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int safeResetNoFinaleEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id cleanupPlayer = params.getObjId("player");
        obj_id currentPlayer = null;
        if (utils.hasScriptVar(self, "graveEventInProgress"))
        {
            currentPlayer = utils.getObjIdScriptVar(self, "graveEventInProgress");
            utils.removeScriptVar(self, "graveEventInProgress");
        }
        if (!isIdValid(cleanupPlayer) || !isIdValid(currentPlayer) || cleanupPlayer == currentPlayer)
        {
            if (utils.hasScriptVar(self, "gravestoneSpawned") || utils.hasScriptVar(self, "katiaraSpawned"))
            {
                obj_id gravestone = utils.getObjIdScriptVar(self, "gravestoneSpawned");
                obj_id katiara = utils.getObjIdScriptVar(self, "katiaraSpawned");
                utils.removeScriptVar(self, "gravestoneSpawned");
                utils.removeScriptVar(self, "katiaraSpawned");
                if (isIdValid(gravestone))
                {
                    destroyObject(gravestone);
                }
                if (isIdValid(katiara) && exists(katiara))
                {
                    destroyObject(katiara);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanupNoFinaleEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "graveEventInProgress"))
        {
            utils.removeScriptVar(self, "graveEventInProgress");
        }
        if (utils.hasScriptVar(self, "gravestoneSpawned"))
        {
            obj_id gravestone = utils.getObjIdScriptVar(self, "gravestoneSpawned");
            utils.removeScriptVar(self, "gravestoneSpawned");
            if (isIdValid(gravestone))
            {
                destroyObject(gravestone);
            }
        }
        if (utils.hasScriptVar(self, "katiaraSpawned"))
        {
            obj_id katiara = utils.getObjIdScriptVar(self, "katiaraSpawned");
            utils.removeScriptVar(self, "katiaraSpawned");
            if (isIdValid(katiara) && exists(katiara))
            {
                destroyObject(katiara);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
