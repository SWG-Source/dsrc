package script.working.steve;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.veteran_deprecated;

public class veteran_npc extends script.base_script
{
    public veteran_npc()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id conversant) throws InterruptedException
    {
        string_id greeting = null;
        string_id[] responses = null;
        if (veteran_deprecated.canGetReward(conversant))
        {
            greeting = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "greeting");
            responses = new string_id[2];
            responses[0] = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "get_award");
            responses[1] = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "not_now");
        }
        else 
        {
            greeting = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "go_away");
            responses = new string_id[1];
            responses[0] = new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "damn");
        }
        npcStartConversation(conversant, self, "veteran", greeting, responses);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversation, obj_id conversant, string_id response) throws InterruptedException
    {
        debugServerConsoleMsg(null, "OnNpcConversationResponse response = " + response);
        if (response.equals(new string_id(veteran_deprecated.VETERAN_STRING_TABLE, "get_award")))
        {
            veteran_deprecated.requestVeteranRewards(conversant);
        }
        npcEndConversation(conversant);
        return SCRIPT_CONTINUE;
    }
}
