package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.groundquests;
import script.library.money;
import script.library.space_dungeon;
import script.library.utils;

public class ep3_etyyy_sordaan_xris extends script.base_script
{
    public ep3_etyyy_sordaan_xris()
    {
    }
    public static String c_stringFile = "conversation/ep3_etyyy_sordaan_xris";
    public boolean ep3_etyyy_sordaan_xris_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_eligibleForABet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_hunt_tuwezz_collect_uller_horns");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_lostWallugaBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet_lost");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_wonWallugaBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet_won");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_lostMoufBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet_lost");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_sentByKerssoc(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_kerssoc_enter_etyyy", "etyyy_talkToSordaan"));
    }
    public boolean ep3_etyyy_sordaan_xris_condition_speakWithSordaan(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_hunt_sordaan_seek_sordaan"));
    }
    public boolean ep3_etyyy_sordaan_xris_condition_sentToZivenFirst(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "ep3_hunt_kerssoc_enter_etyyy", "etyyy_talkToZiven"));
    }
    public boolean ep3_etyyy_sordaan_xris_condition_finishedTuwezz(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_sordaan", "sordaan_tuwezzSendsYou");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_finishedEhartt(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_sordaan", "sordaan_eharttSendsYou");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_finishedTripp(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_sordaan", "sordaan_trippSendsYou");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_finishedZiven(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedTask(player, "ep3_hunt_sordaan_seek_sordaan", "sordaan_zivenSendsYou");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_betAlreadyActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet"))
        {
            return true;
        }
        if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet"))
        {
            return true;
        }
        if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet"))
        {
            return true;
        }
        if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_eligibleForWallugaBet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "ep3_hunt_ehartt_collect_walluga_claws"))
        {
            if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
            {
                int WALLUGA_BET_LEVEL = 1;
                int sordaanBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
                if (sordaanBetLevel >= WALLUGA_BET_LEVEL)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_eligibleForMoufBet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "ep3_hunt_tripp_collect_mouf_incisors"))
        {
            if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
            {
                int MOUF_BET_LEVEL = 2;
                int sordaanBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
                if (sordaanBetLevel >= MOUF_BET_LEVEL)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_eligibleForWebweaverBet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.hasCompletedQuest(player, "ep3_hunt_ziven_collect_webweaver_eyes"))
        {
            if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
            {
                int WEBWEAVER_BET_LEVEL = 3;
                int sordaanBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
                if (sordaanBetLevel >= WEBWEAVER_BET_LEVEL)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_eligibleForAnyBet(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
        {
            int ALL_BETS_BET_LEVEL = 4;
            int sordaanBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
            if (sordaanBetLevel >= ALL_BETS_BET_LEVEL)
            {
                return true;
            }
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_canAffordWebweaverBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 25000);
    }
    public boolean ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 10000);
    }
    public boolean ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 5000);
    }
    public boolean ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return money.hasFunds(player, money.MT_TOTAL, 2000);
    }
    public boolean ep3_etyyy_sordaan_xris_condition_lostUllerBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet_lost");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_wonUllerBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet_won");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_wonMoufBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet_won");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_lostWebweaverBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet_lost");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_wonWebweaverBet(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet_won");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_betWonByPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet_won"))
        {
            return true;
        }
        else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet_won"))
        {
            return true;
        }
        else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet_won"))
        {
            return true;
        }
        else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet_won"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_betLostByPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet_lost"))
        {
            return true;
        }
        else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet_lost"))
        {
            return true;
        }
        else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet_lost"))
        {
            return true;
        }
        else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet_lost"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_etyyy_sordaan_xris_condition_hasNotSeenHarroom(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_sordaan_seek_harroom");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_notGottenUllerReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "ep3_hunt_sordaan_seek_harroom") && !groundquests.hasCompletedQuest(player, "ep3_hunt_harroom_uller_reward");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_notGottenWallugaReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "ep3_hunt_sordaan_seek_harroom") && !groundquests.hasCompletedQuest(player, "ep3_hunt_harroom_walluga_reward");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_notGottenMoufReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "ep3_hunt_sordaan_seek_harroom") && !groundquests.hasCompletedQuest(player, "ep3_hunt_harroom_mouf_reward");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_notGottenWebweaverReward(obj_id player, obj_id npc) throws InterruptedException
    {
        return !groundquests.isQuestActive(player, "ep3_hunt_sordaan_seek_harroom") && !groundquests.hasCompletedQuest(player, "ep3_hunt_harroom_webweaver_reward");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_killedEtyyyNamedCreatures(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "ep3_hunt_loot_completed_all");
    }
    public boolean ep3_etyyy_sordaan_xris_condition_destroyedBocctyyyTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
        if (!utils.playerHasItemByTemplateInBankOrInventory(player, ticketTemplate))
        {
            if (groundquests.isTaskActive(player, "ep3_hunt_sordaan_uller_bet", "sordaan_ullerBet"))
            {
                return false;
            }
            if (groundquests.isTaskActive(player, "ep3_hunt_sordaan_walluga_bet", "sordaan_wallugaBet"))
            {
                return false;
            }
            if (groundquests.isTaskActive(player, "ep3_hunt_sordaan_mouf_bet", "sordaan_moufBet"))
            {
                return false;
            }
            if (groundquests.isTaskActive(player, "ep3_hunt_sordaan_webweaver_bet", "sordaan_webweaverBet"))
            {
                return false;
            }
            return true;
        }
        return false;
    }
    public void ep3_etyyy_sordaan_xris_action_sendToManfred(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToSordaan");
        groundquests.grantQuest(player, "ep3_hunt_manfred_steal_chiss_goods");
    }
    public void ep3_etyyy_sordaan_xris_action_sendToZiven(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToSordaan");
        groundquests.grantQuest(player, "ep3_hunt_ziven_collect_webweaver_fangs");
    }
    public void ep3_etyyy_sordaan_xris_action_firstToZiven(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "etyyy_talkToSordaan");
    }
    public void ep3_etyyy_sordaan_xris_action_sendToEhartt(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToSordaan");
        groundquests.grantQuest(player, "ep3_hunt_ehartt_collect_walluga_claws");
    }
    public void ep3_etyyy_sordaan_xris_action_giveWebweaverBet(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
        obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_THE_BET, ticketTemplate);
        if (isIdValid(ticket))
        {
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "ep3_hunt_sordaan_webweaver_bet");
            setObjVar(ticket, "kashyyyk.ticket_owner", player);
            money.requestPayment(player, npc, 25000, "pass_fail", null, true);
            groundquests.grantQuest(player, "ep3_hunt_sordaan_webweaver_bet");
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_giveMoufBet(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
        obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_THE_BET, ticketTemplate);
        if (isIdValid(ticket))
        {
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "ep3_hunt_sordaan_mouf_bet");
            setObjVar(ticket, "kashyyyk.ticket_owner", player);
            money.requestPayment(player, npc, 10000, "pass_fail", null, true);
            groundquests.grantQuest(player, "ep3_hunt_sordaan_mouf_bet");
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_giveUllerBet(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
        obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_THE_BET, ticketTemplate);
        if (isIdValid(ticket))
        {
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "ep3_hunt_sordaan_uller_bet");
            setObjVar(ticket, "kashyyyk.ticket_owner", player);
            money.requestPayment(player, npc, 2000, "pass_fail", null, true);
            groundquests.grantQuest(player, "ep3_hunt_sordaan_uller_bet");
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_giveWallugaBet(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
        obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_THE_BET, ticketTemplate);
        if (isIdValid(ticket))
        {
            setObjVar(ticket, "space_dungeon.ticket.quest_type", "ep3_hunt_sordaan_walluga_bet");
            setObjVar(ticket, "kashyyyk.ticket_owner", player);
            money.requestPayment(player, npc, 5000, "pass_fail", null, true);
            groundquests.grantQuest(player, "ep3_hunt_sordaan_walluga_bet");
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_playerUllerWin(obj_id player, obj_id npc) throws InterruptedException
    {
        int WALLUGA_BET_LEVEL = 1;
        if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
        {
            int currentBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
            if (currentBetLevel < WALLUGA_BET_LEVEL)
            {
                setObjVar(player, "ep3Etyyy.sordaanBetLevel", WALLUGA_BET_LEVEL);
            }
        }
        else 
        {
            setObjVar(player, "ep3Etyyy.sordaanBetLevel", WALLUGA_BET_LEVEL);
        }
        groundquests.sendSignal(player, "sordaan_ullerBetWon");
        int credits = 4000;
        sendSystemMessage(player, credits + " credits have been deposited in your bank account: Your original stake plus your winnings.", null);
        money.bankTo(money.ACCT_EP3_HUNTING_GROUNDS, player, credits);
    }
    public void ep3_etyyy_sordaan_xris_action_playerWallugaWin(obj_id player, obj_id npc) throws InterruptedException
    {
        int MOUF_BET_LEVEL = 2;
        if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
        {
            int currentBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
            if (currentBetLevel < MOUF_BET_LEVEL)
            {
                setObjVar(player, "ep3Etyyy.sordaanBetLevel", MOUF_BET_LEVEL);
            }
        }
        else 
        {
            setObjVar(player, "ep3Etyyy.sordaanBetLevel", MOUF_BET_LEVEL);
        }
        groundquests.sendSignal(player, "sordaan_wallugaBetWon");
        int credits = 10000;
        sendSystemMessage(player, credits + " credits have been deposited in your bank account: Your original stake plus your winnings.", null);
        money.bankTo(money.ACCT_EP3_HUNTING_GROUNDS, player, credits);
    }
    public void ep3_etyyy_sordaan_xris_action_playerMoufWin(obj_id player, obj_id npc) throws InterruptedException
    {
        int WEBWEAVER_BET_LEVEL = 3;
        if (hasObjVar(player, "ep3Etyyy.sordaanBetLevel"))
        {
            int currentBetLevel = getIntObjVar(player, "ep3Etyyy.sordaanBetLevel");
            if (currentBetLevel < WEBWEAVER_BET_LEVEL)
            {
                setObjVar(player, "ep3Etyyy.sordaanBetLevel", WEBWEAVER_BET_LEVEL);
            }
        }
        else 
        {
            setObjVar(player, "ep3Etyyy.sordaanBetLevel", WEBWEAVER_BET_LEVEL);
        }
        groundquests.sendSignal(player, "sordaan_moufBetWon");
        int credits = 20000;
        sendSystemMessage(player, credits + " credits have been deposited in your bank account: Your original stake plus your winnings.", null);
        money.bankTo(money.ACCT_EP3_HUNTING_GROUNDS, player, credits);
    }
    public void ep3_etyyy_sordaan_xris_action_playerWebweaverWin(obj_id player, obj_id npc) throws InterruptedException
    {
        int ALL_BETS_BET_LEVEL = 4;
        setObjVar(player, "ep3Etyyy.sordaanBetLevel", ALL_BETS_BET_LEVEL);
        groundquests.sendSignal(player, "sordaan_webweaverBetWon");
        int credits = 50000;
        sendSystemMessage(player, credits + " credits have been deposited in your bank account: Your original stake plus your winnings.", null);
        money.bankTo(money.ACCT_EP3_HUNTING_GROUNDS, player, credits);
    }
    public void ep3_etyyy_sordaan_xris_action_playerWebweaverLost(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_webweaverBetLost");
        if (!hasObjVar(player, "ep3Etyyy.canDoBanolQuests"))
        {
            setObjVar(player, "ep3Etyyy.canDoBanolQuests", true);
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_playerMoufLost(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_moufBetLost");
        if (!hasObjVar(player, "ep3Etyyy.canDoBanolQuests"))
        {
            setObjVar(player, "ep3Etyyy.canDoBanolQuests", true);
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_playerWallugaLost(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_wallugaBetLost");
        if (!hasObjVar(player, "ep3Etyyy.canDoBanolQuests"))
        {
            setObjVar(player, "ep3Etyyy.canDoBanolQuests", true);
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_playerUllerLost(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_ullerBetLost");
        if (!hasObjVar(player, "ep3Etyyy.canDoBanolQuests"))
        {
            setObjVar(player, "ep3Etyyy.canDoBanolQuests", true);
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_fromZivenHunt(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "sordaan_talkToSordaan");
    }
    public void ep3_etyyy_sordaan_xris_action_giveUllerReward(obj_id player, obj_id npc) throws InterruptedException
    {
        ep3_etyyy_sordaan_xris_action_playerUllerWin(player, npc);
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_harroom", false);
        groundquests.sendSignal(player, "sordaan_ullerBetReward");
    }
    public void ep3_etyyy_sordaan_xris_action_giveWallugaReward(obj_id player, obj_id npc) throws InterruptedException
    {
        ep3_etyyy_sordaan_xris_action_playerWallugaWin(player, npc);
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_harroom", false);
        groundquests.sendSignal(player, "sordaan_wallugaBetReward");
    }
    public void ep3_etyyy_sordaan_xris_action_giveMoufReward(obj_id player, obj_id npc) throws InterruptedException
    {
        ep3_etyyy_sordaan_xris_action_playerMoufWin(player, npc);
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_harroom", false);
        groundquests.sendSignal(player, "sordaan_moufBetReward");
    }
    public void ep3_etyyy_sordaan_xris_action_giveWebweaverReward(obj_id player, obj_id npc) throws InterruptedException
    {
        ep3_etyyy_sordaan_xris_action_playerWebweaverWin(player, npc);
        String medalTemplate = "object/tangible/wearables/necklace/necklace_rodian_safari.iff";
        obj_id medal = createObjectInInventoryAllowOverload(medalTemplate, player);
        setBioLink(medal, player);
        sendSystemMessage(player, "A Rodian Hunter's Medallion has been placed in your inventory. This medallion has been biolinked to you.", null);
        groundquests.grantQuest(player, "ep3_hunt_sordaan_seek_harroom", false);
        groundquests.sendSignal(player, "sordaan_webweaverBetReward");
    }
    public void ep3_etyyy_sordaan_xris_action_giveEtyyyNamedCreaturesReward(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "lootQuest_defeatedAll");
        if (!badge.hasBadge(player, "bdg_kash_hunting_excellence"))
        {
            badge.grantBadge(player, "bdg_kash_hunting_excellence");
        }
        return;
    }
    public void ep3_etyyy_sordaan_xris_action_giveReplacementTicket(obj_id player, obj_id npc) throws InterruptedException
    {
        String ticketTemplate = "object/tangible/travel/travel_ticket/bocctyyy_path_ticket.iff";
        obj_id ticket = space_dungeon.createTicket(player, "kashyyyk_hunting", "kashyyyk_hunting", space_dungeon.KASH_THE_BET, ticketTemplate);
        if (isIdValid(ticket))
        {
            String questName = "";
            if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_uller_bet"))
            {
                questName = "ep3_hunt_sordaan_uller_bet";
            }
            else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_walluga_bet"))
            {
                questName = "ep3_hunt_sordaan_walluga_bet";
            }
            else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_mouf_bet"))
            {
                questName = "ep3_hunt_sordaan_mouf_bet";
            }
            else if (groundquests.isQuestActive(player, "ep3_hunt_sordaan_webweaver_bet"))
            {
                questName = "ep3_hunt_sordaan_webweaver_bet";
            }
            setObjVar(ticket, "space_dungeon.ticket.quest_type", questName);
            setObjVar(ticket, "kashyyyk.ticket_owner", player);
        }
        return;
    }
    public String ep3_etyyy_sordaan_xris_tokenTO_playerName(obj_id player, obj_id npc) throws InterruptedException
    {
        return getFirstName(player);
    }
    public int ep3_etyyy_sordaan_xris_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1136"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_sendToEhartt(player, npc);
                string_id message = new string_id(c_stringFile, "s_1138");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1140");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1144");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1156"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_sendToManfred(player, npc);
                string_id message = new string_id(c_stringFile, "s_1158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1160");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1164");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1166"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_sendToZiven(player, npc);
                string_id message = new string_id(c_stringFile, "s_1168");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1170");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1174");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1176"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_fromZivenHunt(player, npc);
                string_id message = new string_id(c_stringFile, "s_1178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1180");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1184");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1186"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1188");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1453"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveEtyyyNamedCreaturesReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1435");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1140"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1142");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1144"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1148");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1152");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1148"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1150");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1152"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_1154");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1414"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1416");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1420"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1422");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1160"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1162");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1164"))
        {
            if (ep3_etyyy_sordaan_xris_condition_hasNotSeenHarroom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1294");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betWonByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1228");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1246");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1264");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betLostByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1298");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1270");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1274");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1278");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1282");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1286");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betAlreadyActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_destroyedBocctyyyTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1302");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForAnyBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1308");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1312");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1316");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1320");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1324");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWebweaverBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1348");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1352");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1356");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1376");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1380");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1384");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1388");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1390");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1404");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1408");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1412");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1170"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1172");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1174"))
        {
            if (ep3_etyyy_sordaan_xris_condition_hasNotSeenHarroom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1294");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betWonByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1228");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1246");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1264");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betLostByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1298");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1270");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1274");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1278");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1282");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1286");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betAlreadyActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_destroyedBocctyyyTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1302");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForAnyBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1308");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1312");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1316");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1320");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1324");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWebweaverBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1348");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1352");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1356");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1376");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1380");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1384");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1388");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1390");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1404");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1408");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1412");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1180"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1182");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1184"))
        {
            if (ep3_etyyy_sordaan_xris_condition_hasNotSeenHarroom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1294");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betWonByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1228");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1246");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1264");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betLostByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1298");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1270");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1274");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1278");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1282");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1286");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betAlreadyActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_destroyedBocctyyyTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1302");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForAnyBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1308");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1312");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1316");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1320");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1324");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWebweaverBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1348");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1352");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1356");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1376");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1380");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1384");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1388");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1390");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1404");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1408");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1412");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1192"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenUllerReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1196");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerUllerWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1200");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1202");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1206");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1210"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenWallugaReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWallugaReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1212");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1214");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWallugaWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1218");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1220");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1224");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1228"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenMoufReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveMoufReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1232");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerMoufWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1238");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1242");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1246"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenWebweaverReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWebweaverReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1250");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWebweaverWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1254");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1256");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1260");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1264"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1266");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1196"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1198");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1202"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1204");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1206"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1208");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1214"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1216");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1220"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1222");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1224"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1226");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1232"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1234");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1238"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1240");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1242"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1244");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1250"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1252");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1256"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1258");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1260"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1262");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1270"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerUllerLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1272");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1274"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWallugaLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1276");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1278"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerMoufLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1280");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1282"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWebweaverLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1284");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1286"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1288");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1292"))
        {
            if (ep3_etyyy_sordaan_xris_condition_hasNotSeenHarroom(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1294");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betWonByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1296");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_wonWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1192");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1228");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1246");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1264");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betLostByPlayer(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1298");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostUllerBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWallugaBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostMoufBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition_lostWebweaverBet(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1270");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1274");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1278");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1282");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1286");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_betAlreadyActive(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1300");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition_destroyedBocctyyyTicket(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1302");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForAnyBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1308");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1312");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1316");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1320");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1324");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWebweaverBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1348");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1352");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1356");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1376");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1380");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1384");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_eligibleForWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1388");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1390");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1404");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1408");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1412");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1424"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1426");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1451"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveEtyyyNamedCreaturesReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1435");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1192"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenUllerReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1196");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerUllerWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1200");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1202");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1206");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1210"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenWallugaReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWallugaReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1212");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1214");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWallugaWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1218");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1220");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1224");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1228"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenMoufReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveMoufReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1232");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerMoufWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1238");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1242");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1246"))
        {
            if (ep3_etyyy_sordaan_xris_condition_notGottenWebweaverReward(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWebweaverReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1250");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWebweaverWin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1254");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1256");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1260");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1264"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1266");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1270"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerUllerLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1272");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1274"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWallugaLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1276");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1278"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerMoufLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1280");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1282"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_playerWebweaverLost(player, npc);
                string_id message = new string_id(c_stringFile, "s_1284");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1286"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1288");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_213"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveReplacementTicket(player, npc);
                string_id message = new string_id(c_stringFile, "s_214");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1302"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1304");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1308"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1310");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1334");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1340");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1312"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1314");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1366");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1372");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1316"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1318");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1394");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1400");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1320"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1322");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1324"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1326");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1334"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordWebweaverBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1336");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordWebweaverBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWebweaverBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1338");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1340"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1342");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1366"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1368");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveMoufBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1370");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1372"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1374");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1394"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1396");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWallugaBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1398");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1400"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1402");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1414"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1416");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1420"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1422");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1330"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1332");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1334");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1340");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1344"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1346");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1366");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1372");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1348"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1350");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1394");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1400");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1352"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1354");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1356"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1358");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1334"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordWebweaverBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1336");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordWebweaverBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWebweaverBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1338");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1340"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1342");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1366"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1368");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveMoufBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1370");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1372"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1374");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1394"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1396");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWallugaBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1398");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1400"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1402");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1414"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1416");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1420"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1422");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1362"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1366");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1372");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1376"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1378");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1394");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1400");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1380"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1382");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1384"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1386");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1366"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1368");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordMoufBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveMoufBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1370");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1372"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1374");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1394"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1396");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWallugaBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1398");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1400"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1402");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1414"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1416");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1420"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1422");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1390"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1392");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1394");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1400");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1404"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1414");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1420");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1408"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1410");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1394"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1396");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordWallugaBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveWallugaBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1398");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1400"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1402");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1414"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1416");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1420"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1422");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1414"))
        {
            if (!ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1416");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (ep3_etyyy_sordaan_xris_condition_canAffordUllerBet(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveUllerBet(player, npc);
                string_id message = new string_id(c_stringFile, "s_1418");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1420"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1422");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1449"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveEtyyyNamedCreaturesReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1435");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1437"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveEtyyyNamedCreaturesReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1435");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1433"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ep3_etyyy_sordaan_xris_action_giveEtyyyNamedCreaturesReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_1434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1435");
                    }
                    utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_etyyy_sordaan_xris_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1435"))
        {
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1436");
                utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.ep3_etyyy_sordaan_xris");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_etyyy_sordaan_xris");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ep3_etyyy_sordaan_xris_condition_speakWithSordaan(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1134");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition_finishedTuwezz(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_sordaan_xris_condition_finishedEhartt(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_sordaan_xris_condition_finishedTripp(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_sordaan_xris_condition_finishedZiven(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (ep3_etyyy_sordaan_xris_condition_killedEtyyyNamedCreatures(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1136");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1156");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1166");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1176");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1186");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1453");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 1);
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_sordaan_xris_condition_betWonByPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1190");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition_wonUllerBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_sordaan_xris_condition_wonWallugaBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_sordaan_xris_condition_wonMoufBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_sordaan_xris_condition_wonWebweaverBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1192");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1210");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1228");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1246");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1264");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 14);
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_sordaan_xris_condition_betLostByPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1268");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition_lostUllerBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_sordaan_xris_condition_lostWallugaBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_sordaan_xris_condition_lostMoufBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (ep3_etyyy_sordaan_xris_condition_lostWebweaverBet(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1270");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1274");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1278");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1282");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1286");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 36);
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_sordaan_xris_condition_eligibleForABet(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1290");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (ep3_etyyy_sordaan_xris_condition_killedEtyyyNamedCreatures(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1292");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1424");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1451");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 42);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(ep3_etyyy_sordaan_xris_tokenTO_playerName(player, npc));
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                pp.other.set(ep3_etyyy_sordaan_xris_tokenTO_playerName(player, npc));
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_sordaan_xris_condition_sentToZivenFirst(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1428");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition_killedEtyyyNamedCreatures(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1449");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 84);
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_sordaan_xris_condition_sentByKerssoc(player, npc))
        {
            ep3_etyyy_sordaan_xris_action_firstToZiven(player, npc);
            string_id message = new string_id(c_stringFile, "s_1430");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition_killedEtyyyNamedCreatures(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1437");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 85);
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_etyyy_sordaan_xris_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1432");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_etyyy_sordaan_xris_condition_killedEtyyyNamedCreatures(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1433");
                }
                utils.setScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId", 86);
                npcStartConversation(player, npc, "ep3_etyyy_sordaan_xris", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_etyyy_sordaan_xris"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
        if (branchId == 1 && ep3_etyyy_sordaan_xris_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && ep3_etyyy_sordaan_xris_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_etyyy_sordaan_xris_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_etyyy_sordaan_xris_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && ep3_etyyy_sordaan_xris_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_etyyy_sordaan_xris_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_etyyy_sordaan_xris_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && ep3_etyyy_sordaan_xris_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_etyyy_sordaan_xris_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_etyyy_sordaan_xris_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_etyyy_sordaan_xris_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_etyyy_sordaan_xris_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_etyyy_sordaan_xris_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_etyyy_sordaan_xris_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_etyyy_sordaan_xris_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_etyyy_sordaan_xris_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && ep3_etyyy_sordaan_xris_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_etyyy_sordaan_xris_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && ep3_etyyy_sordaan_xris_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && ep3_etyyy_sordaan_xris_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && ep3_etyyy_sordaan_xris_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && ep3_etyyy_sordaan_xris_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && ep3_etyyy_sordaan_xris_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && ep3_etyyy_sordaan_xris_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && ep3_etyyy_sordaan_xris_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && ep3_etyyy_sordaan_xris_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && ep3_etyyy_sordaan_xris_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && ep3_etyyy_sordaan_xris_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && ep3_etyyy_sordaan_xris_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && ep3_etyyy_sordaan_xris_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && ep3_etyyy_sordaan_xris_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && ep3_etyyy_sordaan_xris_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && ep3_etyyy_sordaan_xris_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && ep3_etyyy_sordaan_xris_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && ep3_etyyy_sordaan_xris_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && ep3_etyyy_sordaan_xris_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && ep3_etyyy_sordaan_xris_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && ep3_etyyy_sordaan_xris_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && ep3_etyyy_sordaan_xris_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && ep3_etyyy_sordaan_xris_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && ep3_etyyy_sordaan_xris_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && ep3_etyyy_sordaan_xris_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && ep3_etyyy_sordaan_xris_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_etyyy_sordaan_xris.branchId");
        return SCRIPT_CONTINUE;
    }
}
