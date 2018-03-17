package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;

public class rebel_sympathizer extends script.base_script
{
    public rebel_sympathizer()
    {
    }
    public static final String REBEL_STF = "theme_park/alderaan/act2/rebel_missions";
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act2/shared_rebel_missions";
    public static final String ENCODED_DEAD_EYE_DISK = "object/tangible/encoded_disk/dead_eye_disk.iff";
    public static final string_id DATA_DISK_RECEIVED = new string_id(REBEL_SHARED_STF, "m1_data_disk_received");
    public static final string_id DATA_DISK_DENIED = new string_id(REBEL_SHARED_STF, "m1_data_disk_denied");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setCondition(self, CONDITION_CONVERSABLE);
        createTriggerVolume("someoneApproaching", 15, true);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (hasObjVar(speaker, "coa2.rebel.missionNpcId"))
        {
            obj_id npc = getObjIdObjVar(speaker, "coa2.rebel.missionNpcId");
            if (npc == self)
            {
                if (!utils.playerHasItemByTemplate(speaker, ENCODED_DEAD_EYE_DISK))
                {
                    giveDataDisk(speaker);
                }
                string_id message = new string_id(REBEL_STF, "m1_sympathizer_complete");
                chat.chat(self, message);
                return SCRIPT_CONTINUE;
            }
        }
        string_id message = new string_id(REBEL_STF, "m1_sympathizer_invalid");
        chat.chat(self, message);
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
            if (hasObjVar(breacher, "coa2.rebel.missionNpcId"))
            {
                obj_id npc = getObjIdObjVar(breacher, "coa2.rebel.missionNpcId");
                if (npc == self)
                {
                    chat.setChatMood(self, chat.MOOD_RELIEVED);
                    string_id message = new string_id(REBEL_STF, "m1_sympathizer_greeting");
                    chat.chat(self, message);
                    giveDataDisk(breacher);
                    messageTo(breacher, "createReturnMission", null, 1, false);
                    removeTriggerVolume("someoneApproaching");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void giveDataDisk(obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        obj_id disk = createObject("object/tangible/encoded_disk/dead_eye_disk.iff", inventory, "");
        if (!isIdValid(disk))
        {
            sendSystemMessage(player, DATA_DISK_DENIED);
        }
        else 
        {
            sendSystemMessage(player, DATA_DISK_RECEIVED);
            removeObjVar(player, "coa2.rebel.missionNpcId");
        }
    }
}
