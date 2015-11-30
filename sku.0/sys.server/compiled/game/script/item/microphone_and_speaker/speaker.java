package script.item.microphone_and_speaker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.debug;
import script.library.xp;

public class speaker extends script.base_script
{
    public speaker()
    {
    }
    public static final String VAR_ACCEPTING_SIGNAL = "speaker.acceptingSignal";
    public static final string_id SID_DEACTIVATE = new string_id("sui", "speaker_deactivate");
    public static final string_id SID_ACTIVATE = new string_id("sui", "speaker_activate");
    public static final string_id SID_ACTIVATE_MSG = new string_id("sui", "speaker_activate_msg");
    public static final string_id SID_DEACTIVATE_MSG = new string_id("sui", "speaker_deactivate_msg");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############speaker script initialized############");
        setObjVar(self, VAR_ACCEPTING_SIGNAL, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        boolean isActive = getBooleanObjVar(self, VAR_ACCEPTING_SIGNAL);
        if (isActive)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_DEACTIVATE);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_ACTIVATE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            sendSystemMessage(player, SID_DEACTIVATE_MSG);
            setObjVar(self, VAR_ACCEPTING_SIGNAL, false);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            sendSystemMessage(player, SID_ACTIVATE_MSG);
            setObjVar(self, VAR_ACCEPTING_SIGNAL, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int projectMicSpeak(obj_id self, dictionary params) throws InterruptedException
    {
        if (!getBooleanObjVar(self, VAR_ACCEPTING_SIGNAL))
        {
            return SCRIPT_CONTINUE;
        }
        String message = (String)params.get("message");
        debug.debugAllMsg("DEBUG", self, "############SPEAKER PROJECTS: " + message + "############");
        chat.chat(self, message);
        return SCRIPT_CONTINUE;
    }
    public int projectMicActive(obj_id self, dictionary params) throws InterruptedException
    {
        if (!getBooleanObjVar(self, VAR_ACCEPTING_SIGNAL))
        {
            return SCRIPT_CONTINUE;
        }
        String message = (String)params.get("message");
        debug.debugAllMsg("DEBUG", self, "############SPEAKER PROJECTS: " + message + "############");
        chat.chat(self, message);
        return SCRIPT_CONTINUE;
    }
}
