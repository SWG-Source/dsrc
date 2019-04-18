package script.poi.prisonbreak;

import script.dictionary;
import script.library.*;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class antagonist extends script.poi.base.scenario_actor
{
    public antagonist()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiPrisonBreak Antagonist";
    public static final int CONV_GREET = 0;
    public static final int CONV_INSULT = 1;
    public static final int CONV_NOHELP = 2;
    public static final int CONV_YESHELP = 3;
    public static final int CONV_TALKMEDIATOR = 4;
    public static final int CONV_CHECKGUARD = 5;
    public static final int CONV_GUARDPEEING = 6;
    public static final int CONV_GUARDHOLDINGIT = 7;
    public static final int CONV_MAYBEHELP = 8;
    public static final int CONV_SABOTAGEPLAN = 9;
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
        boolean isLeader = poi.isNpcTagged(self, "antagonist_0");
        boolean mediatorsDead = false;
        obj_id[] mediators = scenario.getTeamMembers("mediator");
        int deadcount = scenario.getDeadTeamMemberCount(poiMaster, "mediator");
        if (deadcount == mediators.length)
        {
            mediatorsDead = true;
        }
        if (mediatorsDead)
        {
            int idx = rand(1, 4);
            poi.quickSay(self, "a_victory_" + idx);
            npcEndConversation(speaker);
        }
        if (!isLeader)
        {
            minionChatter(self, convo, speaker, poiMaster);
            return SCRIPT_CONTINUE;
        }
        string_id msg = new string_id(convo, "a_greet");
        Vector responses = new Vector();
        responses.setSize(0);
        switch (progress)
        {
            case CONV_GREET:
            msg = new string_id(convo, "a_greet");
            responses = utils.addElement(responses, new string_id(convo, "r_greet_tellstory"));
            npcStartConversation(speaker, self, convo, msg, responses);
            scenario.setPlayerProgress(speaker, CONV_MAYBEHELP);
            break;
            case CONV_MAYBEHELP:
            msg = new string_id(convo, "a_couldusehelp");
            responses = utils.addElement(responses, new string_id(convo, "r_couldusehelp_yeshelp"));
            responses = utils.addElement(responses, new string_id(convo, "r_couldusehelp_nohelp"));
            npcStartConversation(speaker, self, convo, msg, responses);
            break;
            case CONV_INSULT:
            scenario.say(self, convo, "a_insulted", false);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
            case CONV_YESHELP:
            case CONV_SABOTAGEPLAN:
            scenario.say(self, convo, "a_please_distractguard", false);
            npcEndConversation(speaker);
            break;
            case CONV_NOHELP:
            scenario.say(self, convo, "a_insulted_nohelp", false);
            npcEndConversation(speaker);
            break;
            default:
            scenario.say(self, convo, "a_insulted", false);
            npcEndConversation(speaker);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void minionChatter(obj_id self, String convo, obj_id speaker, obj_id poiMaster) throws InterruptedException
    {
        int idx = rand(1, 4);
        poi.quickSay(self, "a_minion_" + idx);
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
        boolean isLeader = poi.isNpcTagged(self, scenario.ANTAGONIST + "_0");
        if (!isLeader)
        {
            return SCRIPT_CONTINUE;
        }
        String aId = response.getAsciiId();
        Vector responses = new Vector();
        responses.setSize(0);
        npcSetConversationResponses(speaker, responses);
        switch (aId) {
            case "r_greet_tellstory":
                npcSpeak(speaker, new string_id(convo, "a_mystory"));
                responses = utils.addElement(responses, new string_id(convo, "r_mystory_howhorrible"));
                responses = utils.addElement(responses, new string_id(convo, "r_mystory_looklikefarmers"));
                npcSetConversationResponses(speaker, responses);
                break;
            case "r_mystory_howhorrible":
                npcSpeak(speaker, new string_id(convo, "a_couldusehelp_horrible"));
                responses = utils.addElement(responses, new string_id(convo, "r_couldusehelp_yeshelp"));
                responses = utils.addElement(responses, new string_id(convo, "r_couldusehelp_nohelp"));
                npcSetConversationResponses(speaker, responses);
                break;
            case "r_mystory_looklikefarmers":
                npcSpeak(speaker, new string_id(convo, "a_angrylooklikefarmers"));
                scenario.setPlayerProgress(speaker, CONV_INSULT);
                break;
            case "r_couldusehelp_yeshelp":
                npcSpeak(speaker, new string_id(convo, "a_please_distractguard"));
                scenario.setPlayerProgress(speaker, CONV_YESHELP);
                break;
            case "r_couldusehelp_nohelp":
                npcSpeak(speaker, new string_id(convo, "a_angrynohelp"));
                scenario.setPlayerProgress(speaker, CONV_NOHELP);
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int attackWallLeader(obj_id self, dictionary params) throws InterruptedException
    {
        poi.quickSay(self, chat.CHAT_EXCLAIM, chat.MOOD_DEVIOUS, "a_attackwall_leader");
        return SCRIPT_CONTINUE;
    }
    public int attackEveryone(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = getStringObjVar(poiMaster, scenario.VAR_SCENARIO_CONVO);
        if (convo.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getBoolean("idiot"))
        {
            poi.quickSay(self, chat.CHAT_BELLOW, chat.MOOD_ANGRY, "a_idiots");
        }
        else 
        {
            poi.quickSay(self, chat.CHAT_BELLOW, chat.MOOD_ENTHUSIASTIC, "a_attackeveryone");
        }
        obj_id[] players = getObjIdArrayObjVar(poiMaster, scenario.VAR_SCENARIO_PLAYERS);
        obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
        obj_id[] antagonists = scenario.getTeamMembers(poiMaster, "antagonist");
        for (obj_id found : antagonists) {
            if ((found == null) || (found == obj_id.NULL_ID)) {
                continue;
            }
            if (isIncapacitated(found) || isDead(found)) {
                continue;
            }
            obj_id enemy = mediators[rand(0, mediators.length - 1)];
            startCombat(found, enemy);
            LOG(LOG_NAME, "Pirate: " + found + " attacking Farmer: " + enemy);
            LOG(LOG_NAME, "Pirate: " + found + " can attack Farmer: " + enemy + ": " + pvpCanAttack(found, enemy));
            for (obj_id mediator : mediators) {
                ai_lib.addToMentalStateToward(found, mediator, ANGER, 100.0f);
            }
            if (players != null) {
                for (obj_id player : players) {
                    if ((player != null) && (player != obj_id.NULL_ID)) {
                        ai_lib.addToMentalStateToward(found, player, ANGER, 100.0f, BEHAVIOR_ATTACK);
                    }
                }
            }
        }
        int k = 1;
        for (int i = 0; i < mediators.length; i++)
        {
            obj_id found = mediators[i];
            if ((found == null) || (found == obj_id.NULL_ID))
            {
                continue;
            }
            if (isIncapacitated(found) || isDead(found))
            {
                continue;
            }
            if (i % 3 == 0)
            {
                poi.quickSay(found, chat.CHAT_SHOUT, chat.MOOD_SURPRISED, "m_underattack_" + k++);
                if (k > 3)
                {
                    k = 0;
                }
            }
            obj_id enemy = antagonists[rand(0, antagonists.length - 1)];
            startCombat(found, enemy);
            LOG(LOG_NAME, "Farmer: " + found + " attacking Pirate: " + enemy);
            LOG(LOG_NAME, "Farmer: " + found + " can attack Pirate: " + enemy + ": " + pvpCanAttack(found, enemy));
            for (obj_id antagonist : antagonists) {
                ai_lib.addToMentalStateToward(found, antagonist, ANGER, 100.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        if (!isIdValid(defender) || getTarget(self) == defender)
        {
            return SCRIPT_CONTINUE;
        }
        if (attackers == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        obj_id[] mediators = scenario.getTeamMembers(poiMaster, "mediator");
        obj_id[] antagonists = scenario.getTeamMembers(poiMaster, "antagonist");
        boolean antagonistAttacked = false;
        for (obj_id antagonist : antagonists) {
            if (defender == antagonist) {
                antagonistAttacked = true;
            }
        }
        if (!antagonistAttacked)
        {
            return SCRIPT_CONTINUE;
        }
        boolean mediatorAttacking = false;
        for (obj_id mediator : mediators) {
            for (obj_id attacker : attackers) {
                if (attacker == mediator) {
                    mediatorAttacking = true;
                }
            }
        }
        if (!mediatorAttacking)
        {
            return SCRIPT_CONTINUE;
        }
        if (!canSee(self, defender))
        {
            return SCRIPT_OVERRIDE;
        }
        if (defender == self || ai_lib.isAiDead(defender))
        {
            return SCRIPT_CONTINUE;
        }
        if (!ai_lib.isInCombat(self))
        {
            obj_id enemy = antagonists[rand(0, antagonists.length - 1)];
            startCombat(self, enemy);
        }
        return SCRIPT_OVERRIDE;
    }
}
