package script.npc.junk_dealer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.group;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.npc.converse.junk_dealer;

public class junk_dealer_summon extends script.base_script
{
    public junk_dealer_summon()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "timeUp", null, 300, true);
        messageTo(self, "handleGreeting", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void buffParty(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        String buffName = "sm_junk_dealer_1";
        int precisionBonus = (int)getSkillStatisticModifier(player, "expertise_buff_under_the_counter");
        int damageDecrease = (int)getSkillStatisticModifier(player, "expertise_buff_best_deal_ever");
        if (damageDecrease >= 6)
        {
            buffName = "sm_junk_dealer_5";
        }
        else if (damageDecrease >= 3)
        {
            buffName = "sm_junk_dealer_4";
        }
        else if (precisionBonus >= 50)
        {
            buffName = "sm_junk_dealer_3";
        }
        else if (precisionBonus >= 25)
        {
            buffName = "sm_junk_dealer_2";
        }
        else 
        {
            buffName = "sm_junk_dealer_1";
        }
        if (group.isGrouped(player))
        {
            Vector party = group.getPCMembersInRange(player, 32f);
            if (party != null)
            {
                for (int i = 0; i < party.size(); i++)
                {
                    obj_id who = (obj_id)party.elementAt(i);
                    buff.applyBuff(who, buffName);
                }
            }
        }
        else 
        {
            buff.applyBuff(player, buffName);
        }
    }
    public void totalProfits(obj_id self, obj_id player) throws InterruptedException
    {
        int totalProfits = utils.getIntScriptVar(self, "totalProfits");
        if (totalProfits > 0)
        {
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", "junk_dealer_total_profits"));
            pp = prose.setDI(pp, totalProfits);
            sendSystemMessageProse(player, pp);
        }
    }
    public int handleGreeting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id master = getMaster(self);
        setMaster(self, null);
        utils.setScriptVar(self, "smugglerMaster", master);
        if (isIdValid(master))
        {
            String name = getFirstName(master);
            int phraseId = rand(1, 6);
            String greeting = "junk_dealer_greeting_" + phraseId;
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", greeting));
            pp = prose.setTU(pp, name);
            chat.chat(self, master, chat.CHAT_SAY, null, chat.ChatFlag_targetGroupOnly, pp);
        }
        else 
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int timeUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getObjIdScriptVar(self, "smugglerMaster");
        if (isIdValid(player))
        {
            buffParty(self, player);
        }
        utils.setScriptVar(self, "dismissed", 1);
        messageTo(self, "handleRunAway", null, 1, false);
        totalProfits(self, player);
        return SCRIPT_CONTINUE;
    }
    public int dismissDealer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id master = utils.getObjIdScriptVar(self, "smugglerMaster");
        if (isIdValid(player) && isIdValid(master) && (master != player))
        {
            return SCRIPT_CONTINUE;
        }
        buffParty(self, player);
        utils.setScriptVar(self, "dismissed", 1);
        messageTo(self, "handleRunAway", null, 1, false);
        totalProfits(self, player);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleRunAway(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasScript(self, "conversation.junk_dealer_smuggler"))
        {
            detachScript(self, "conversation.junk_dealer_smuggler");
        }
        if (hasScript(self, "npc.converse.junk_dealer"))
        {
            detachScript(self, "npc.converse.junk_dealer");
        }
        if (isDead(self))
        {
            messageTo(self, "cleanUp", null, 5.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "runningaway"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master = utils.getObjIdScriptVar(self, "smugglerMaster");
        if (isIdValid(master))
        {
            ai_lib.pathAwayFrom(self, master);
        }
        utils.setScriptVar(self, "runningaway", 1);
        messageTo(self, "cleanUp", null, 5.0f, false);
        return SCRIPT_CONTINUE;
    }
}
