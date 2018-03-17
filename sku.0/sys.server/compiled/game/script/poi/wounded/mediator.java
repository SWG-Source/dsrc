package script.poi.wounded;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.scenario;
import script.library.poi;
import script.library.ai_lib;
import script.library.utils;
import script.library.money;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final int PROGRESS_NONE = 0;
    public static final int ANSWERED_YES = 1;
    public static final int ANSWERED_NO = 2;
    public static final int ANSWERED_NO_AGAIN = 3;
    public static final int PROVIDED_MEDICINE = 4;
    public static final int COMPLETED = 5;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, scenario.HANDLER_INCAPACITATION, null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        messageTo(self, scenario.HANDLER_INCAPACITATION, null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to locate base object");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to determine conversation file");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            scenario.say(self, convo, "m_combat_" + rand(1, 5));
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        obj_id leader = poi.findObject(scenario.MEDIATOR + "_0");
        if ((leader == null) || (leader == obj_id.NULL_ID))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to locate MEDIATOR_0");
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        Vector responses = new Vector();
        responses.setSize(0);
        LOG("poiWounded", "speaker current progress = " + progress);
        if (self == leader)
        {
            if (poi.isComplete(poiMaster))
            {
                scenario.say(self, convo, "m_completed");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
            }
            String msg_id = "m_greet";
            responses = utils.addElement(responses, new string_id(convo, "response_yes"));
            switch (progress)
            {
                case PROGRESS_NONE:
                responses = utils.addElement(responses, new string_id(convo, "response_no"));
                break;
                case ANSWERED_YES:
                scenario.say(self, convo, "m_willing");
                openInventories(speaker, self);
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                case ANSWERED_NO:
                msg_id = "m_rejected";
                responses = utils.addElement(responses, new string_id(convo, "response_no_again"));
                break;
                case ANSWERED_NO_AGAIN:
                scenario.say(self, convo, "m_enraged");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                case PROVIDED_MEDICINE:
                scenario.say(self, convo, "m_medicine");
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
                default:
                break;
            }
            string_id msg = new string_id(convo, msg_id);
            npcStartConversation(speaker, self, convo, msg, responses);
        }
        else 
        {
            int idx = rand(1, 5);
            scenario.say(self, convo, "m_" + idx);
            npcEndConversation(speaker);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("poiWounded", "response = " + response.toString());
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            LOG("poiWounded", "I have no idea what object I belong to!");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            LOG("poiWounded", "I have no idea what I am supposed to say");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (!convoName.equals(convo))
        {
            LOG("poiWounded", "convo files do not match!!");
            return SCRIPT_CONTINUE;
        }
        obj_id leader = poi.findObject(scenario.MEDIATOR + "_0");
        if ((leader == null) || (leader == obj_id.NULL_ID))
        {
            LOG("poiWounded", "OnStartNpcConversation: unable to locate MEDIATOR_0");
            return SCRIPT_CONTINUE;
        }
        if (self != leader)
        {
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        String aId = response.getAsciiId();
        string_id msg = new string_id();
        if ((aId.equals("response_yes")))
        {
            scenario.say(self, convo, "m_yes");
            openInventories(speaker, self);
            npcEndConversation(speaker);
            scenario.setPlayerProgress(speaker, ANSWERED_YES);
        }
        else if ((aId.equals("response_no")))
        {
            scenario.say(self, convo, "m_no");
            npcEndConversation(speaker);
            scenario.setPlayerProgress(speaker, ANSWERED_NO);
        }
        else if ((aId.equals("response_no_again")))
        {
            scenario.say(self, convo, "m_enraged");
            npcEndConversation(speaker);
            scenario.setPlayerProgress(speaker, ANSWERED_NO_AGAIN);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean openInventories(obj_id player, obj_id target) throws InterruptedException
    {
        if ((player == null) || (target == null))
        {
            return false;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        if ((pInv == null) || (pInv == obj_id.NULL_ID))
        {
            return false;
        }
        obj_id tInv = utils.getInventoryContainer(target);
        if ((tInv == null) || (tInv == obj_id.NULL_ID))
        {
            return false;
        }
        if (utils.requestContainerOpen(player, pInv) && utils.requestContainerOpen(player, tInv))
        {
            return true;
        }
        return false;
    }
    public int handleIncapacitation(obj_id self, dictionary params) throws InterruptedException
    {
        scenario.actorIncapacitated(self);
        return SCRIPT_CONTINUE;
    }
    public int giveReward(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt("amt");
        LOG("poiWounded", "transfering bank credits from named account. amt = " + amt);
        transferBankCreditsFromNamedAccount(money.ACCT_NPC_LOOT, self, amt, "fromAccountPass", "xferFail", params);
        utils.moneyInMetric(self, money.ACCT_NPC_LOOT, amt);
        return SCRIPT_CONTINUE;
    }
    public int xferFail(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiWounded", "money transaction failed.");
        return SCRIPT_CONTINUE;
    }
    public int xferPass(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("poiWounded", "money transaction complete.");
        return SCRIPT_CONTINUE;
    }
    public int fromAccountPass(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt("amt");
        if (amt > 0)
        {
            LOG("poiWounded", "withdrawing cash");
            withdrawCashFromBank(self, amt, "withdrawPass", "xferFail", params);
        }
        else 
        {
            LOG("poiWounded", "fromAccountPass: invalid amount = " + amt);
        }
        return SCRIPT_CONTINUE;
    }
    public int withdrawPass(obj_id self, dictionary params) throws InterruptedException
    {
        int amt = params.getInt("amt");
        if (amt > 0)
        {
            obj_id target = params.getObjId("target");
            if ((target == null) || (target == obj_id.NULL_ID))
            {
                LOG("poiWounded", "invalid cash transfer target!");
            }
            else 
            {
                LOG("poiWounded", "transfering cash");
                transferCashTo(self, target, amt, "xferPass", "xferFail", params);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
