package script.item.microphone_and_speaker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.debug;
import script.library.xp;

public class microphone extends script.base_script
{
    public microphone()
    {
    }
    public static final float MIC_RADIUS = .05f;
    public static final string_id SIGNAL_MESSAGE = new string_id("sui", "mic_speaker_signal");
    public static final string_id SID_ACTIVATE = new string_id("sui", "mic_activation");
    public static final string_id SID_DEACTIVATE = new string_id("sui", "mic_deactivation");
    public static final string_id SID_ACTIVATE_MSG = new string_id("sui", "mic_activation_msg");
    public static final string_id SID_DEACTIVATE_MSG = new string_id("sui", "mic_deactivation_msg");
    public static final String VAR_IS_ACTIVE = "microphone_is_active";
    public static final String SPEAKER_TEMPLATE = "object/tangible/speaker/speaker.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############microphone script initialized############");
        setObjVar(self, VAR_IS_ACTIVE, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id player, String message) throws InterruptedException
    {
        boolean isActive = getBooleanObjVar(self, VAR_IS_ACTIVE);
        if (!isActive || getDistance(player, self) > MIC_RADIUS)
        {
            return SCRIPT_CONTINUE;
        }
        debug.debugAllMsg("DEBUG", self, "############MIC IS ACTIVE############");
        obj_id[] speakers = getAllObjectsWithTemplate(getLocation(self), 30, SPEAKER_TEMPLATE);
        if (speakers.length > 0)
        {
            xp.grantCraftingXpChance(self, player, 40);
            dictionary params = new dictionary();
            params.put("message", message);
            for (int i = 0; i < speakers.length; ++i)
            {
                messageTo(speakers[i], "projectMicSpeak", params, 0, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        boolean isActive = getBooleanObjVar(self, VAR_IS_ACTIVE);
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
            setObjVar(self, VAR_IS_ACTIVE, false);
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            sendSystemMessage(player, SID_ACTIVATE_MSG);
            obj_id[] speakers = getAllObjectsWithTemplate(getLocation(self), 30, SPEAKER_TEMPLATE);
            if (speakers.length > 0)
            {
                dictionary params = new dictionary();
                params.put("message", utils.packStringId(SIGNAL_MESSAGE));
                for (int i = 0; i < speakers.length; ++i)
                {
                    messageTo(speakers[i], "projectMicActive", params, 0, true);
                }
            }
            setObjVar(self, VAR_IS_ACTIVE, true);
        }
        return SCRIPT_CONTINUE;
    }
}
