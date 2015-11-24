package script.theme_park.recruitment;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.hue;
import script.library.colors;
import script.library.ai_lib;
import script.library.factions;

public class hutt_recruit extends script.base_script
{
    public hutt_recruit()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        persistObject(self);
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        float huttness = getFloatObjVar(speaker, "faction.Hutt");
        String faction = factions.getFaction(speaker);
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, "faction.Hutt"))
        {
            if (huttness > 500)
            {
                string_id seeBib = new string_id("recruiting/hutt_recruit", "seebib");
                chat.chat(self, seeBib);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(speaker, "hutt.hutt-two"))
        {
            detachScript(speaker, "theme_park.recruitment.hutt_quest2");
            removeObjVar(speaker, "hutt.thisGuy");
            string_id greeting = new string_id("recruiting/hutt_recruit", "hr_25");
            string_id response[] = new string_id[1];
            response[0] = new string_id("recruiting/hutt_recruit", "hr_26");
            factions.addFactionStanding(speaker, "Hutt", 501.0f);
            npcStartConversation(speaker, self, "recruiting/hutt_recruit", greeting, response);
        }
        else if (hasObjVar(speaker, "hutt.hutt-one"))
        {
            detachScript(speaker, "theme_park.recruitment.hutt_quest1");
            removeObjVar(speaker, "hutt.thisGuy");
            string_id greeting = new string_id("recruiting/hutt_recruit", "hr_13");
            string_id response[] = new string_id[2];
            response[0] = new string_id("recruiting/hutt_recruit", "hr_14");
            response[1] = new string_id("recruiting/hutt_recruit", "hr_15");
            removeObjVar(speaker, "hutt.beenToHuttRecruiter");
            npcStartConversation(speaker, self, "recruiting/hutt_recruit", greeting, response);
        }
        else if (hasObjVar(speaker, "hutt.beenToHuttRecruiter"))
        {
            string_id greeting = new string_id("recruiting/hutt_recruit", "hr_29");
            string_id response[] = new string_id[2];
            response[0] = new string_id("recruiting/hutt_recruit", "hr_2");
            response[1] = new string_id("recruiting/hutt_recruit", "hr_3");
            npcStartConversation(speaker, self, "recruiting/hutt_recruit", greeting, response);
        }
        else 
        {
            string_id greeting = new string_id("recruiting/hutt_recruit", "hr_1");
            string_id response[] = new string_id[2];
            response[0] = new string_id("recruiting/hutt_recruit", "hr_2");
            response[1] = new string_id("recruiting/hutt_recruit", "hr_3");
            npcStartConversation(speaker, self, "recruiting/hutt_recruit", greeting, response);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("hr_2"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_4");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_2"));
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_3"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_5"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_6"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_3"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_11");
            debugSpeakMsg(self, "I should say something here");
            npcSpeak(player, message);
            setObjVar(player, "hutt.beenToHuttRecruiter", 1);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_5"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_7");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_5"));
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_6"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_8"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_6"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_11");
            npcSpeak(player, message);
            setObjVar(player, "hutt.beenToHuttRecruiter", 1);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_8"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_12");
            npcSpeak(player, message);
            attachScript(player, "theme_park.recruitment.hutt_quest1");
            setObjVar(player, "hutt.thisGuy", getLocation(self));
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_9"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_10");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_14"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_16");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_14"));
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_15"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_18"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_19"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_15"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_17");
            npcSpeak(player, message);
            setObjVar(player, "hutt.beenToHuttRecruiter", 1);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_18"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_20");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_18"));
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_19"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_21"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_22"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_15"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_19"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_28");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_19"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_21"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_23");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_21"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_22"));
            npcAddConversationResponse(player, new string_id("recruiting/hutt_recruit", "hr_15"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_22"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_24");
            npcSpeak(player, message);
            attachScript(player, "theme_park.recruitment.hutt_quest2");
            setObjVar(player, "hutt.thisGuy", getLocation(self));
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("hr_26"))
        {
            string_id message = new string_id("recruiting/hutt_recruit", "hr_27");
            npcSpeak(player, message);
            npcEndConversation(player);
            removeObjVar(player, "hutt.hutt-one");
            removeObjVar(player, "hutt.hutt-two");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "reeloDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
