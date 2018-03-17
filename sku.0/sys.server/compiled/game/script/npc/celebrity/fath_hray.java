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

public class fath_hray extends script.base_script
{
    public fath_hray()
    {
    }
    public static final String CONVO = "celebrity/fath_hray";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id pants = createObject("object/tangible/wearables/pants/nightsister_pants_s02.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/nightsister_boots.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/nightsister_shirt_s03.iff", self, "");
        obj_id hat = createObject("object/tangible/wearables/hat/nightsister_hat_s03.iff", self, "");
        hue.setColor(pants, 1, 24);
        hue.setColor(pants, 2, 3);
        hue.setColor(boots, 1, 0);
        hue.setColor(hat, 1, 0);
        hue.setColor(shirt, 1, 24);
        hue.setColor(shirt, 2, 3);
        setName(self, "Fath Hray");
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
