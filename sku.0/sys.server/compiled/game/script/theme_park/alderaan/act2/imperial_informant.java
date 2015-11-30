package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;
import script.library.chat;
import script.ai.ai_combat;

public class imperial_informant extends script.base_script
{
    public imperial_informant()
    {
    }
    public static final String IMPERIAL_STF = "theme_park/alderaan/act2/imperial_missions";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        chat.setChatMood(self, chat.MOOD_HAUGHTY);
        createTriggerVolume("someoneApproaching", 15, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        string_id openDialog;
        Vector dialogResponse = new Vector();
        dialogResponse.setSize(0);
        boolean missionPlayer = (getObjIdObjVar(speaker, "coa2.imperial.missionNpcId") == self);
        if (missionPlayer)
        {
            openDialog = new string_id(IMPERIAL_STF, "m1_informant");
            string_id response = new string_id(IMPERIAL_STF, "m1_informant_report");
            dialogResponse = utils.addElement(dialogResponse, response);
            response = new string_id(IMPERIAL_STF, "m1_informant_reprimand");
            dialogResponse = utils.addElement(dialogResponse, response);
        }
        else 
        {
            openDialog = new string_id(IMPERIAL_STF, "m1_informant_invalid");
        }
        if (dialogResponse.size() == 0)
        {
            chat.chat(self, openDialog);
            return SCRIPT_OVERRIDE;
        }
        npcStartConversation(speaker, self, "imperialInformant", openDialog, dialogResponse);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals("imperialInformant"))
        {
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("m1_informant_report"))
        {
            string_id message = new string_id(IMPERIAL_STF, "m1_informant_report_response");
            npcSpeak(player, message);
            setObjVar(player, "coa2.progress", -3);
            messageTo(player, "createReturnMission", null, 1, false);
            npcEndConversation(player);
        }
        else if ((response.getAsciiId()).equals("m1_informant_reprimand"))
        {
            string_id message = new string_id(IMPERIAL_STF, "m1_informant_reprimand_response");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, response);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self || ai_lib.isAiDead(breacher) || ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals("someoneApproaching"))
        {
            string_id message = new string_id(IMPERIAL_STF, "m1_informant_taunt");
            chat.chat(self, message);
        }
        return SCRIPT_CONTINUE;
    }
}
