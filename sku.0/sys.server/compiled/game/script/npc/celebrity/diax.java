package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.hue;
import script.library.colors;

public class diax extends script.base_script
{
    public diax()
    {
    }
    public static final String CONVO = "celebrity/diax";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id pants = createObject("object/tangible/wearables/pants/nightsister_pants_s01.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/nightsister_boots.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/nightsister_shirt_s01.iff", self, "");
        obj_id hat = createObject("object/tangible/wearables/hat/nightsister_hat_s01.iff", self, "");
        hue.setColor(pants, 1, 35);
        hue.setColor(pants, 2, 0);
        hue.setColor(pants, 3, 33);
        hue.setColor(pants, 4, 17);
        hue.setColor(boots, 1, 17);
        hue.setColor(hat, 1, 35);
        hue.setColor(shirt, 1, 34);
        hue.setColor(shirt, 2, 31);
        setName(self, "Diax");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "I'm a specific Nightsister.");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        string_id greeting = new string_id(CONVO, "npc_1");
        string_id response[] = new string_id[2];
        response[0] = new string_id(CONVO, "player_1");
        response[1] = new string_id(CONVO, "player_2");
        npcStartConversation(speaker, self, "celebConvo", greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("player_1"))
        {
            string_id message = new string_id(CONVO, "npc_2");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("player_2"))
        {
            string_id message = new string_id(CONVO, "npc_3");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
