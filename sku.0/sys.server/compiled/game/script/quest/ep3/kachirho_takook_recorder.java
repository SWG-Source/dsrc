package script.quest.ep3;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.chat;
import script.library.attrib;
import script.library.utils;
import script.library.create;

public class kachirho_takook_recorder extends script.base_script
{
    public kachirho_takook_recorder()
    {
    }
    public static final String STF = "ep3/sidequests";
    public static final string_id LISTEN = new string_id(STF, "takook_recorder_listen");
    public static final string_id CLICK = new string_id(STF, "takook_recorder_click");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setRecorderAttributes(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setRecorderAttributes(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, LISTEN);
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_kachirho_missing_son", "talkToTakook"))
            {
                groundquests.grantQuest(player, "ep3_kachirho_takook_comm");
                groundquests.sendSignal(player, "takookTale");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, CLICK);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setRecorderAttributes(obj_id self) throws InterruptedException
    {
        createTriggerVolume("blink_range", 5, true);
        setAttributeInterested(self, attrib.ALL);
        return;
    }
    public int handleReset(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id lightParticle = params.getObjId("lightParticle");
        destroyObject(lightParticle);
        utils.removeScriptVar(self, "blinking");
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id who) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "blinking"))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("blink_range"))
        {
            if (groundquests.isTaskActive(who, "ep3_kachirho_missing_son", "talkToTakook"))
            {
                location here = getLocation(self);
                obj_id blinkLight = create.object("object/static/particle/pt_light_blink_green.iff", here);
                utils.setScriptVar(self, "blinking", 1);
                dictionary light = new dictionary();
                light.put("lightParticle", blinkLight);
                messageTo(self, "handleReset", light, 15f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
