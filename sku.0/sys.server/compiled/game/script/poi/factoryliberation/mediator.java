package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.ai_lib;
import script.library.poi;
import script.library.scenario;
import script.library.chat;
import script.library.utils;
import script.library.group;
import script.library.badge;
import script.library.locations;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiFactoryLiberation Mediator";
    public static final int CONV_GREET = 0;
    public static final int CONV_INSULT = 1;
    public static final int CONV_MAYBEHELP = 2;
    public static final int CONV_CONSULTMEDIATOR = 3;
    public static final int CONV_NOHELP = 4;
    public static final int CONV_YESHELP = 5;
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
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        boolean isLeader = poi.isNpcTagged(self, scenario.MEDIATOR + "_0");
        boolean antagonistsDead = false;
        boolean someDead = false;
        obj_id[] antagonists = scenario.getTeamMembers("antagonist");
        int deadcount = scenario.getDeadTeamMemberCount(poiMaster, "antagonist");
        if (deadcount == antagonists.length)
        {
            antagonistsDead = true;
        }
        else if (deadcount > 0)
        {
            someDead = true;
        }
        boolean speakerCredit = scenario.hasKillCredit(poiMaster, "antagonist", speaker);
        boolean wallHasBeenDestroyed = getBooleanObjVar(poiMaster, "wallHasBeenDestroyed");
        boolean wallDamaged = getBooleanObjVar(poiMaster, "wallDamaged");
        if (!isLeader)
        {
            minionChatter(self, convo, speaker, poiMaster, antagonistsDead, speakerCredit, someDead, wallHasBeenDestroyed, wallDamaged);
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(convo, "m_greet_0");
        Vector responses = new Vector();
        responses.setSize(0);
        if (scenario.isComplete())
        {
            poi.quickSay(self, chat.CHAT_DECREE, chat.MOOD_EXUBERANT, "m_freedom");
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (antagonistsDead)
        {
            if (!wallHasBeenDestroyed)
            {
                scenario.say(self, convo, "m_destroywall", false);
            }
            else if (speakerCredit)
            {
                poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_EXUBERANT, "m_thanks");
                finishScenario(self, speaker);
            }
            else 
            {
                scenario.say(self, convo, "m_saved", false);
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (wallHasBeenDestroyed)
        {
            scenario.say(self, convo, "m_killantagonists", false);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (someDead)
        {
            if (!speakerCredit)
            {
                poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_EXUBERANT, "m_somepiratesdead");
            }
            else 
            {
                poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_EXUBERANT, "m_killingpirates");
            }
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        else if (wallDamaged)
        {
            scenario.say(self, convo, "m_walldamaged", false);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        switch (progress)
        {
            case CONV_GREET:
            msg = new string_id(convo, "m_greet_0");
            responses = utils.addElement(responses, new string_id(convo, "r_m_greet_yeshelp"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_greet_nohelp"));
            npcStartConversation(speaker, self, convo, msg, responses);
            scenario.setPlayerProgress(speaker, CONV_CONSULTMEDIATOR);
            break;
            case CONV_INSULT:
            case CONV_MAYBEHELP:
            msg = new string_id(convo, "m_greet_1");
            responses = utils.addElement(responses, new string_id(convo, "r_m_greet_yeshelp"));
            responses = utils.addElement(responses, new string_id(convo, "r_m_greet_nohelp"));
            npcStartConversation(speaker, self, convo, msg, responses);
            scenario.setPlayerProgress(speaker, CONV_CONSULTMEDIATOR);
            break;
            case CONV_NOHELP:
            poi.quickSay(self, chat.CHAT_HUFF, chat.MOOD_DISGUSTED, "m_disdain");
            npcEndConversation(speaker);
            break;
            case CONV_YESHELP:
            scenario.say(self, convo, "m_helpyet", false);
            npcEndConversation(speaker);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void minionChatter(obj_id self, String convo, obj_id speaker, obj_id poiMaster, boolean antagonistsDead, boolean speakerCredit, boolean someDead, boolean wallHasBeenDestroyed, boolean wallDamaged) throws InterruptedException
    {
        if (antagonistsDead)
        {
            if (!wallHasBeenDestroyed)
            {
                scenario.say(self, convo, "m_minion_destroywall", false);
            }
            else if (speakerCredit)
            {
                poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_EXUBERANT, "m_minion_thanks");
            }
            else 
            {
                poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_EXUBERANT, "m_minion_saved");
            }
        }
        else 
        {
            if (wallHasBeenDestroyed)
            {
                scenario.say(self, convo, "m_minion_killantagonists", false);
            }
            else if (wallDamaged)
            {
                scenario.say(self, convo, "m_minion_walldamaged", false);
            }
            else 
            {
                int idx = rand(1, 4);
                scenario.say(self, convo, "m_minion_" + idx, false);
            }
        }
        npcEndConversation(speaker);
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (!convoName.equals(convo))
        {
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        boolean isLeader = poi.isNpcTagged(self, scenario.MEDIATOR + "_0");
        if (!isLeader)
        {
            return SCRIPT_CONTINUE;
        }
        String aId = response.getAsciiId();
        string_id msg = new string_id();
        string_id[] responses = new string_id[0];
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_m_greet_yeshelp")))
        {
            npcSpeak(speaker, new string_id(convo, "m_yeshelp"));
            scenario.setPlayerProgress(speaker, CONV_YESHELP);
        }
        else if ((aId.equals("r_m_greet_nohelp")))
        {
            npcSpeak(speaker, new string_id(convo, "m_nohelp"));
            scenario.setPlayerProgress(speaker, CONV_NOHELP);
        }
        return SCRIPT_CONTINUE;
    }
    public int celebrateWallLeader(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        queueCommand(self, (1780871594), null, "peptalk", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        poi.quickSay(self, chat.CHAT_SHOUT, chat.MOOD_ENCOURAGING, "m_celebratewall");
        return SCRIPT_CONTINUE;
    }
    public int celebrateWall(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        queueCommand(self, (1780871594), null, "cheer", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        poi.quickSay(self, chat.CHAT_PROCLAIM, chat.MOOD_ENTHUSIASTIC, "m_minion_celebratewall");
        return SCRIPT_CONTINUE;
    }
    public void finishScenario(obj_id self, obj_id speaker) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        scenario.complete(poiMaster);
        if (group.isGroupObject(speaker))
        {
            obj_id[] members = getGroupMemberIds(speaker);
            if ((members == null) || (members.length == 0))
            {
            }
            else 
            {
                for (int n = 0; n < members.length; n++)
                {
                    if (!badge.hasBadge(members[n], "poi_factoryliberation"))
                    {
                        badge.grantBadge(members[n], "poi_factoryliberation");
                    }
                    else 
                    {
                        badge.notifyHasBadge(members[n], "poi_factoryliberation");
                    }
                }
            }
        }
        else 
        {
            if (!badge.hasBadge(speaker, "poi_factoryliberation"))
            {
                badge.grantBadge(speaker, "poi_factoryliberation");
            }
            else 
            {
                badge.notifyHasBadge(speaker, "poi_factoryliberation");
            }
        }
        obj_id[] team = scenario.getTeamMembers(poiMaster, "mediator");
        if (team == null)
        {
            return;
        }
        for (int i = 0; i < team.length; i++)
        {
            messageTo(team[i], "celebrateFreedom", null, 0, false);
        }
        messageTo(self, "runForFreedom", null, 10, false);
    }
    public int runForFreedom(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        poi.quickSay(self, chat.CHAT_PROCLAIM, chat.MOOD_ENTHUSIASTIC, "m_letsgo");
        location dest = getLocation(self);
        dest.z += 500;
        dest.x += 0;
        dest = locations.getGoodLocationAroundLocation(dest, 10, 10, 50, 50);
        setMovementRun(self);
        ai_lib.pathTo(self, dest);
        setHomeLocation(self, dest);
        obj_id[] team = scenario.getTeamMembers(poiMaster, "mediator");
        if (team == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < team.length; i++)
        {
            if (team[i] == self)
            {
                continue;
            }
            location myDest = locations.getGoodLocationAroundLocation(dest, 1, 1, 10, 10);
            setMovementRun(team[i]);
            ai_lib.pathTo(team[i], myDest);
            setHomeLocation(team[i], dest);
        }
        return SCRIPT_CONTINUE;
    }
}
