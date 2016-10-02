package script.theme_park.recruitment;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.utils;
import script.library.ai_lib;
import script.library.colors;
import script.library.factions;
import script.library.space_flags;

public class rebel_recruit extends script.theme_park.recruitment.base.base_recruiter
{
    public rebel_recruit()
    {
    }
    public static final String CONVO = "recruiting/rebel_recruit";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "dressed"))
        {

	}
	{
            obj_id suit = createObject("object/tangible/wearables/bodysuit/bodysuit_bwing.iff", self, "");
            obj_id boots = createObject("object/tangible/wearables/boots/boots_s03.iff", self, "");
            hue.setColor(suit, 1, colors.ORANGE);
            hue.hueObject(self);
            hue.randomizeObject(self);
            pvpSetAlignedFaction(self, (370444368));
            pvpMakeDeclared(self);
            String hair_table = "datatables/tangible/wearable/hair/hair_human_male.iff";
            int numHair = dataTableGetNumRows(hair_table);
            numHair = numHair - 1;
            numHair = rand(1, numHair);
            String hair = dataTableGetString(hair_table, numHair, 1);
            obj_id hairStyle = createObject(hair, self, "");
            hue.hueObject(hairStyle);
            setObjVar(self, "dressed", 1);
        }
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        string_id greeting = new string_id(CONVO, "rr1");
        Vector response = new Vector();
        response.setSize(0);
        boolean addRedeemOption = false;
        String faction = factions.getFaction(speaker);
        if (faction != null)
        {
            if (faction.equals(factions.FACTION_REBEL))
            {
                greeting = new string_id(CONVO, "rr13");
                response = utils.addElement(response, new string_id(CONVO, "rr14"));
            }
            else if (faction.equals(factions.FACTION_IMPERIAL))
            {
                greeting = new string_id(CONVO, "rr16");
                response = utils.addElement(response, new string_id(CONVO, "rr17"));
            }
        }
        else 
        {
            greeting = new string_id(CONVO, "rr1");
            response = utils.addElement(response, new string_id(CONVO, "rr2"));
            response = utils.addElement(response, new string_id(CONVO, "rr3"));
        }
        if (addRedeemOption)
        {
            response = utils.addElement(response, new string_id(DATA_ITEM_CONVO, "redeem"));
        }
        if ((response == null) || (response.size() == 0))
        {
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            npcStartConversation(speaker, self, CONVO, greeting, response);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getTable()).equals(DATA_ITEM_CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr2"))
        {
            string_id message = new string_id(CONVO, "rr4");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "rr2"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "rr3"));
            npcAddConversationResponse(player, new string_id(CONVO, "rr5"));
            npcAddConversationResponse(player, new string_id(CONVO, "rr6"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr3"))
        {
            string_id message = new string_id(CONVO, "rr11");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr5"))
        {
            string_id message = new string_id(CONVO, "rr7");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "rr5"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "rr6"));
            npcAddConversationResponse(player, new string_id(CONVO, "rr8"));
            npcAddConversationResponse(player, new string_id(CONVO, "rr9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr6"))
        {
            string_id message = new string_id(CONVO, "rr7");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "rr5"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "rr6"));
            npcAddConversationResponse(player, new string_id(CONVO, "rr8"));
            npcAddConversationResponse(player, new string_id(CONVO, "rr9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr8"))
        {
            string_id message = new string_id(CONVO, "rr12");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr9"))
        {
            string_id message = new string_id(CONVO, "rr10");
            npcSpeak(player, message);
            npcEndConversation(player);
            pvpSetAlignedFaction(player, (370444368));
            pvpMakeDeclared(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr14"))
        {
            string_id message = new string_id(CONVO, "rr15");
            npcSpeak(player, message);
            npcEndConversation(player);
            pvpSetAlignedFaction(self, 0);
            pvpMakeNeutral(self);
            if (space_flags.isImperialPilot(self))
            {
                pvpSetAlignedFaction(self, (-615855020));
            }
            else if (space_flags.isRebelPilot(self))
            {
                pvpSetAlignedFaction(self, (370444368));
            }
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("rr17"))
        {
            string_id message = new string_id(CONVO, "rr18");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
