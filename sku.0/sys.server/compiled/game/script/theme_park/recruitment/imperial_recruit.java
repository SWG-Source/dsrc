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
import script.library.factions;
import script.library.space_flags;

public class imperial_recruit extends script.theme_park.recruitment.base.base_recruiter
{
    public imperial_recruit()
    {
    }
    public static final String CONVO = "recruiting/imperial_recruit";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
	// TODO: wtf is the empty if, and the scope brackets for below?
	// also couldn't this ranodmizing crap be made a function for all classes to consume instead?
	if (!hasObjVar(self, "dressed"))
        {

	}

	{
            obj_id suit = createObject("object/tangible/wearables/bodysuit/bodysuit_tie_fighter.iff", self, "");
            obj_id boots = createObject("object/tangible/wearables/boots/boots_s03.iff", self, "");
            hue.hueObject(self);
            pvpSetAlignedFaction(self, (-615855020));
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
        string_id greeting = new string_id(CONVO, "ir1");
        Vector response = new Vector();
        response.setSize(0);
        boolean addRedeemOption = false;
        String faction = factions.getFaction(speaker);
        if (faction != null)
        {
            if (faction.equals(factions.FACTION_REBEL))
            {
                greeting = new string_id(CONVO, "ir16");
                response = utils.addElement(response, new string_id(CONVO, "ir17"));
            }
            else if (faction.equals(factions.FACTION_IMPERIAL))
            {
                greeting = new string_id(CONVO, "ir13");
                response = utils.addElement(response, new string_id(CONVO, "ir14"));
            }
        }
        else 
        {
            greeting = new string_id(CONVO, "ir1");
            response = utils.addElement(response, new string_id(CONVO, "ir2"));
            response = utils.addElement(response, new string_id(CONVO, "ir3"));
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
        if ((response.getAsciiId()).equals("ir2"))
        {
            string_id message = new string_id(CONVO, "ir4");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "ir2"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "ir3"));
            npcAddConversationResponse(player, new string_id(CONVO, "ir5"));
            npcAddConversationResponse(player, new string_id(CONVO, "ir6"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir3"))
        {
            string_id message = new string_id(CONVO, "ir11");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir5"))
        {
            string_id message = new string_id(CONVO, "ir7");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "ir5"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "ir6"));
            npcAddConversationResponse(player, new string_id(CONVO, "ir8"));
            npcAddConversationResponse(player, new string_id(CONVO, "ir9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir6"))
        {
            string_id message = new string_id(CONVO, "ir7");
            npcSpeak(player, message);
            npcRemoveConversationResponse(player, new string_id(CONVO, "ir5"));
            npcRemoveConversationResponse(player, new string_id(CONVO, "ir6"));
            npcAddConversationResponse(player, new string_id(CONVO, "ir8"));
            npcAddConversationResponse(player, new string_id(CONVO, "ir9"));
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir8"))
        {
            string_id message = new string_id(CONVO, "ir12");
            pvpSetAlignedFaction(player, (-615855020));
            pvpMakeDeclared(player);
            setObjVar(player, "imperial", 1);
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir9"))
        {
            string_id message = new string_id(CONVO, "ir10");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir14"))
        {
            string_id message = new string_id(CONVO, "ir15");
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
            removeObjVar(player, "rebel");
            return SCRIPT_CONTINUE;
        }
        if ((response.getAsciiId()).equals("ir17"))
        {
            string_id message = new string_id(CONVO, "ir18");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
