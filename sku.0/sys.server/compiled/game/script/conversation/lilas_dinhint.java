package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.money;
import script.library.utils;

public class lilas_dinhint extends script.base_script
{
    public lilas_dinhint()
    {
    }
    public static String c_stringFile = "conversation/lilas_dinhint";
    public boolean lilas_dinhint_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lilas_dinhint_condition_museumEventActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.museumEventStarted");
    }
    public boolean lilas_dinhint_condition_artworkAvailable(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventFeaturedSchematic"))
        {
            String schematic = getStringObjVar(npc, "bestine.museumEventFeaturedSchematic");
            if (schematic != null && !schematic.equals(""))
            {
                return true;
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_noEventButArtworkAvailable(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.museumEventFeaturedSchematic");
    }
    public boolean lilas_dinhint_condition_notEnoughCredits(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!money.hasFunds(player, money.MT_TOTAL, 48000));
    }
    public boolean lilas_dinhint_condition_noInventorySpace(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean hasNoInvRoom = false;
        obj_id playerInv = utils.getInventoryContainer(player);
        if (isIdValid(playerInv))
        {
            int free_space = getVolumeFree(playerInv);
            if (free_space < 1)
            {
                hasNoInvRoom = true;
            }
        }
        return hasNoInvRoom;
    }
    public boolean lilas_dinhint_condition_noEventAndNoArtwork(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lilas_dinhint_condition_canBuy(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!lilas_dinhint_condition_noInventorySpace(player, npc) && !lilas_dinhint_condition_notEnoughCredits(player, npc));
    }
    public boolean lilas_dinhint_condition_inSeanCampaign(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.electionStarted"))
        {
            int electionNum = getIntObjVar(npc, "bestine.electionStarted");
            if (hasObjVar(player, "bestine.campaign"))
            {
                int electionPlayerIsIn = getIntObjVar(player, "bestine.campaign");
                if (electionPlayerIsIn >= electionNum)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_madeRoomSeanTestimony(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((lilas_dinhint_condition_inSeanCampaign(player, npc)) && (hasObjVar(player, "bestine.sean_museum_noroom")));
    }
    public boolean lilas_dinhint_condition_talkAboutSeanTrenwell(obj_id player, obj_id npc) throws InterruptedException
    {
        if (lilas_dinhint_condition_inSeanCampaign(player, npc))
        {
            if (!hasObjVar(player, "bestine.sean_museum_noroom"))
            {
                if (!utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_questp_ctestimony.iff"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canChooseArtist(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!lilas_dinhint_condition_alreadyVoted(player, npc))
        {
            if (lilas_dinhint_condition_canVoteArtist01(player, npc) || lilas_dinhint_condition_canVoteArtist02(player, npc) || lilas_dinhint_condition_canVoteArtist03(player, npc) || lilas_dinhint_condition_canVoteArtist04(player, npc) || lilas_dinhint_condition_canVoteArtist05(player, npc) || lilas_dinhint_condition_canVoteArtist06(player, npc))
            {
                return true;
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canVoteArtist01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist01"))
            {
                int spokeToArtist = getIntObjVar(player, "bestine.spokeToArtist01");
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                if (spokeToArtist >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist01"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist01"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist01"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canVoteArtist02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist02"))
            {
                int spokeToArtist = getIntObjVar(player, "bestine.spokeToArtist02");
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                if (spokeToArtist >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist02"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist02"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist02"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canVoteArtist03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist03"))
            {
                int spokeToArtist = getIntObjVar(player, "bestine.spokeToArtist03");
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                if (spokeToArtist >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist03"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist03"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist03"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canVoteArtist04(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist04"))
            {
                int spokeToArtist = getIntObjVar(player, "bestine.spokeToArtist04");
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                if (spokeToArtist >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist04"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist04"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist04"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canVoteArtist05(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist05"))
            {
                int spokeToArtist = getIntObjVar(player, "bestine.spokeToArtist05");
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                if (spokeToArtist >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist05"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist05"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist05"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_canVoteArtist06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            if (hasObjVar(player, "bestine.spokeToArtist06"))
            {
                int spokeToArtist = getIntObjVar(player, "bestine.spokeToArtist06");
                int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
                if (spokeToArtist >= eventNum)
                {
                    String entry01 = "";
                    String entry02 = "";
                    String entry03 = "";
                    if (hasObjVar(npc, "bestine.museumEventEntry01"))
                    {
                        entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry02"))
                    {
                        entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
                    }
                    if (hasObjVar(npc, "bestine.museumEventEntry03"))
                    {
                        entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
                    }
                    if (entry01.equals("bestine_artist06"))
                    {
                        return true;
                    }
                    if (entry02.equals("bestine_artist06"))
                    {
                        return true;
                    }
                    if (entry03.equals("bestine_artist06"))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_cannotChooseArtistButHasntVoted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!lilas_dinhint_condition_alreadyVoted(player, npc))
        {
            if (!lilas_dinhint_condition_canVoteArtist01(player, npc) && !lilas_dinhint_condition_canVoteArtist02(player, npc) && !lilas_dinhint_condition_canVoteArtist03(player, npc) && !lilas_dinhint_condition_canVoteArtist04(player, npc) && !lilas_dinhint_condition_canVoteArtist05(player, npc) && !lilas_dinhint_condition_canVoteArtist06(player, npc))
            {
                
            }
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_alreadyVoted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.museumEventPlayerVoted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            int playerEventNum = getIntObjVar(player, "bestine.museumEventPlayerVoted");
            if (playerEventNum >= eventNum)
            {
                return true;
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_tellAboutArtist01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist01"))
        {
            return false;
        }
        String entry01 = "";
        String entry02 = "";
        String entry03 = "";
        if (hasObjVar(npc, "bestine.museumEventEntry01"))
        {
            entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry02"))
        {
            entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry03"))
        {
            entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
        }
        if (entry01.equals("bestine_artist01"))
        {
            return true;
        }
        if (entry02.equals("bestine_artist01"))
        {
            return true;
        }
        if (entry03.equals("bestine_artist01"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_tellAboutArtist02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist02"))
        {
            return false;
        }
        String entry01 = "";
        String entry02 = "";
        String entry03 = "";
        if (hasObjVar(npc, "bestine.museumEventEntry01"))
        {
            entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry02"))
        {
            entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry03"))
        {
            entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
        }
        if (entry01.equals("bestine_artist02"))
        {
            return true;
        }
        if (entry02.equals("bestine_artist02"))
        {
            return true;
        }
        if (entry03.equals("bestine_artist02"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_tellAboutArtist03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist03"))
        {
            return false;
        }
        String entry01 = "";
        String entry02 = "";
        String entry03 = "";
        if (hasObjVar(npc, "bestine.museumEventEntry01"))
        {
            entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry02"))
        {
            entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry03"))
        {
            entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
        }
        if (entry01.equals("bestine_artist03"))
        {
            return true;
        }
        if (entry02.equals("bestine_artist03"))
        {
            return true;
        }
        if (entry03.equals("bestine_artist03"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_tellAboutArtist04(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist04"))
        {
            return false;
        }
        String entry01 = "";
        String entry02 = "";
        String entry03 = "";
        if (hasObjVar(npc, "bestine.museumEventEntry01"))
        {
            entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry02"))
        {
            entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry03"))
        {
            entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
        }
        if (entry01.equals("bestine_artist04"))
        {
            return true;
        }
        if (entry02.equals("bestine_artist04"))
        {
            return true;
        }
        if (entry03.equals("bestine_artist04"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_tellAboutArtist05(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist05"))
        {
            return false;
        }
        String entry01 = "";
        String entry02 = "";
        String entry03 = "";
        if (hasObjVar(npc, "bestine.museumEventEntry01"))
        {
            entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry02"))
        {
            entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry03"))
        {
            entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
        }
        if (entry01.equals("bestine_artist05"))
        {
            return true;
        }
        if (entry02.equals("bestine_artist05"))
        {
            return true;
        }
        if (entry03.equals("bestine_artist05"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_tellAboutArtist06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist06"))
        {
            return false;
        }
        String entry01 = "";
        String entry02 = "";
        String entry03 = "";
        if (hasObjVar(npc, "bestine.museumEventEntry01"))
        {
            entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry02"))
        {
            entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
        }
        if (hasObjVar(npc, "bestine.museumEventEntry03"))
        {
            entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
        }
        if (entry01.equals("bestine_artist06"))
        {
            return true;
        }
        if (entry02.equals("bestine_artist06"))
        {
            return true;
        }
        if (entry03.equals("bestine_artist06"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_hasAskedAboutALocation(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist01"))
        {
            return true;
        }
        if (utils.hasScriptVar(player, "askedAboutArtist02"))
        {
            return true;
        }
        if (utils.hasScriptVar(player, "askedAboutArtist03"))
        {
            return true;
        }
        if (utils.hasScriptVar(player, "askedAboutArtist04"))
        {
            return true;
        }
        if (utils.hasScriptVar(player, "askedAboutArtist05"))
        {
            return true;
        }
        if (utils.hasScriptVar(player, "askedAboutArtist06"))
        {
            return true;
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_allowTimeLeftRequest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_withinTheHour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection < 3600)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_justAFewHours(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 3600 && timeUntilElection < 14400)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_lessThanADay(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 14400 && timeUntilElection < 86400)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_moreThanADay(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 86400 && timeUntilElection < 172800)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_justAFewDays(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 172800 && timeUntilElection < 345600)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_lessThanAWeek(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 345600 && timeUntilElection < 604800)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean lilas_dinhint_condition_time_moreThanAWeek(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventEnded"))
        {
            if (hasObjVar(npc, "bestine.timeNextEventStarts"))
            {
                int timeNextElectionStarts = getIntObjVar(npc, "bestine.timeNextEventStarts");
                int currentTime = getGameTime();
                int timeUntilElection = timeNextElectionStarts - currentTime;
                if (timeUntilElection >= 604800)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public void lilas_dinhint_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void lilas_dinhint_action_giveSchematic(obj_id player, obj_id npc) throws InterruptedException
    {
        String schematicTemplate = getStringObjVar(npc, "bestine.museumEventFeaturedSchematic");
        int schematicCost = 48000;
        obj_id playerInv = getObjectInSlot(player, "inventory");
        if (isIdValid(playerInv))
        {
            obj_id schematic = createObject(schematicTemplate, playerInv, "");
            if (isIdValid(schematic))
            {
                utils.moneyOutMetric(player, "Bestine_Museum", schematicCost);
                money.requestPayment(player, npc, schematicCost, "pass_fail", null, true);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_giveSeansTestimony(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.sean_museum_noroom"))
        {
            removeObjVar(player, "bestine.sean_museum_noroom");
        }
        String HTESTIMONY = "object/tangible/loot/quest/sean_questp_ctestimony.iff";
        if (isIdValid(player))
        {
            obj_id playerInv = getObjectInSlot(player, "inventory");
            if (isIdValid(playerInv))
            {
                obj_id item = createObject(HTESTIMONY, playerInv, "");
                if (isIdValid(item))
                {
                    return;
                }
            }
        }
        return;
    }
    public void lilas_dinhint_action_setNoRoomSeanTestimony(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "bestine.sean_museum_noroom", 1);
    }
    public void lilas_dinhint_action_setMuseumEventObjVar(obj_id player, obj_id npc) throws InterruptedException
    {
        int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
        setObjVar(player, "bestine.museumEventPlayer", eventNum);
    }
    public void lilas_dinhint_action_voteForArtist01(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.museumEventPlayerVoted", eventNum);
            lilas_dinhint_action_removeSpokeToObjVars(player, npc);
            String entry01 = "";
            String entry02 = "";
            String entry03 = "";
            if (hasObjVar(npc, "bestine.museumEventEntry01"))
            {
                entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry02"))
            {
                entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry03"))
            {
                entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
            }
            int votes = 0;
            if (entry01.equals("bestine_artist01"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry01");
                setObjVar(npc, "bestine.votesForEntry01", votes + 1);
                return;
            }
            if (entry02.equals("bestine_artist01"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry02");
                setObjVar(npc, "bestine.votesForEntry02", votes + 1);
                return;
            }
            if (entry03.equals("bestine_artist01"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry03");
                setObjVar(npc, "bestine.votesForEntry03", votes + 1);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_voteForArtist02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.museumEventPlayerVoted", eventNum);
            lilas_dinhint_action_removeSpokeToObjVars(player, npc);
            String entry01 = "";
            String entry02 = "";
            String entry03 = "";
            if (hasObjVar(npc, "bestine.museumEventEntry01"))
            {
                entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry02"))
            {
                entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry03"))
            {
                entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
            }
            int votes = 0;
            if (entry01.equals("bestine_artist02"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry01");
                setObjVar(npc, "bestine.votesForEntry01", votes + 1);
                return;
            }
            if (entry02.equals("bestine_artist02"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry02");
                setObjVar(npc, "bestine.votesForEntry02", votes + 1);
                return;
            }
            if (entry03.equals("bestine_artist02"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry03");
                setObjVar(npc, "bestine.votesForEntry03", votes + 1);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_voteForArtist03(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.museumEventPlayerVoted", eventNum);
            lilas_dinhint_action_removeSpokeToObjVars(player, npc);
            String entry01 = "";
            String entry02 = "";
            String entry03 = "";
            if (hasObjVar(npc, "bestine.museumEventEntry01"))
            {
                entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry02"))
            {
                entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry03"))
            {
                entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
            }
            int votes = 0;
            if (entry01.equals("bestine_artist03"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry01");
                setObjVar(npc, "bestine.votesForEntry01", votes + 1);
                return;
            }
            if (entry02.equals("bestine_artist03"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry02");
                setObjVar(npc, "bestine.votesForEntry02", votes + 1);
                return;
            }
            if (entry03.equals("bestine_artist03"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry03");
                setObjVar(npc, "bestine.votesForEntry03", votes + 1);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_voteForArtist04(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.museumEventPlayerVoted", eventNum);
            lilas_dinhint_action_removeSpokeToObjVars(player, npc);
            String entry01 = "";
            String entry02 = "";
            String entry03 = "";
            if (hasObjVar(npc, "bestine.museumEventEntry01"))
            {
                entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry02"))
            {
                entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry03"))
            {
                entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
            }
            int votes = 0;
            if (entry01.equals("bestine_artist04"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry01");
                setObjVar(npc, "bestine.votesForEntry01", votes + 1);
                return;
            }
            if (entry02.equals("bestine_artist04"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry02");
                setObjVar(npc, "bestine.votesForEntry02", votes + 1);
                return;
            }
            if (entry03.equals("bestine_artist04"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry03");
                setObjVar(npc, "bestine.votesForEntry03", votes + 1);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_voteForArtist05(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.museumEventPlayerVoted", eventNum);
            lilas_dinhint_action_removeSpokeToObjVars(player, npc);
            String entry01 = "";
            String entry02 = "";
            String entry03 = "";
            if (hasObjVar(npc, "bestine.museumEventEntry01"))
            {
                entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry02"))
            {
                entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry03"))
            {
                entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
            }
            int votes = 0;
            if (entry01.equals("bestine_artist05"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry01");
                setObjVar(npc, "bestine.votesForEntry01", votes + 1);
                return;
            }
            if (entry02.equals("bestine_artist05"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry02");
                setObjVar(npc, "bestine.votesForEntry02", votes + 1);
                return;
            }
            if (entry03.equals("bestine_artist05"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry03");
                setObjVar(npc, "bestine.votesForEntry03", votes + 1);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_voteForArtist06(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(npc, "bestine.museumEventStarted"))
        {
            int eventNum = getIntObjVar(npc, "bestine.museumEventStarted");
            setObjVar(player, "bestine.museumEventPlayerVoted", eventNum);
            lilas_dinhint_action_removeSpokeToObjVars(player, npc);
            String entry01 = "";
            String entry02 = "";
            String entry03 = "";
            if (hasObjVar(npc, "bestine.museumEventEntry01"))
            {
                entry01 = getStringObjVar(npc, "bestine.museumEventEntry01");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry02"))
            {
                entry02 = getStringObjVar(npc, "bestine.museumEventEntry02");
            }
            if (hasObjVar(npc, "bestine.museumEventEntry03"))
            {
                entry03 = getStringObjVar(npc, "bestine.museumEventEntry03");
            }
            int votes = 0;
            if (entry01.equals("bestine_artist06"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry01");
                setObjVar(npc, "bestine.votesForEntry01", votes + 1);
                return;
            }
            if (entry02.equals("bestine_artist06"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry02");
                setObjVar(npc, "bestine.votesForEntry02", votes + 1);
                return;
            }
            if (entry03.equals("bestine_artist06"))
            {
                votes = getIntObjVar(npc, "bestine.votesForEntry03");
                setObjVar(npc, "bestine.votesForEntry03", votes + 1);
                return;
            }
        }
        return;
    }
    public void lilas_dinhint_action_removeSpokeToObjVars(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.spokeToArtist01"))
        {
            removeObjVar(player, "bestine.spokeToArtist01");
        }
        if (hasObjVar(player, "bestine.spokeToArtist02"))
        {
            removeObjVar(player, "bestine.spokeToArtist02");
        }
        if (hasObjVar(player, "bestine.spokeToArtist03"))
        {
            removeObjVar(player, "bestine.spokeToArtist03");
        }
        if (hasObjVar(player, "bestine.spokeToArtist04"))
        {
            removeObjVar(player, "bestine.spokeToArtist04");
        }
        if (hasObjVar(player, "bestine.spokeToArtist05"))
        {
            removeObjVar(player, "bestine.spokeToArtist05");
        }
        if (hasObjVar(player, "bestine.spokeToArtist06"))
        {
            removeObjVar(player, "bestine.spokeToArtist06");
        }
    }
    public void lilas_dinhint_action_removeEventObjvar(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.museumEventPlayer"))
        {
            removeObjVar(player, "bestine.museumEventPlayer");
        }
    }
    public void lilas_dinhint_action_askedAboutArtist01(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "askedAboutArtist01", 1);
        lilas_dinhint_action_setMuseumEventObjVar(player, npc);
    }
    public void lilas_dinhint_action_askedAboutArtist02(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "askedAboutArtist02", 1);
        lilas_dinhint_action_setMuseumEventObjVar(player, npc);
    }
    public void lilas_dinhint_action_askedAboutArtist03(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "askedAboutArtist03", 1);
        lilas_dinhint_action_setMuseumEventObjVar(player, npc);
    }
    public void lilas_dinhint_action_askedAboutArtist04(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "askedAboutArtist04", 1);
        lilas_dinhint_action_setMuseumEventObjVar(player, npc);
    }
    public void lilas_dinhint_action_askedAboutArtist05(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "askedAboutArtist05", 1);
        lilas_dinhint_action_setMuseumEventObjVar(player, npc);
    }
    public void lilas_dinhint_action_askedAboutArtist06(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "askedAboutArtist06", 1);
        lilas_dinhint_action_setMuseumEventObjVar(player, npc);
    }
    public void lilas_dinhint_action_clearAskedLocationScrVars(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "askedAboutArtist01"))
        {
            utils.removeScriptVar(player, "askedAboutArtist01");
        }
        if (utils.hasScriptVar(player, "askedAboutArtist02"))
        {
            utils.removeScriptVar(player, "askedAboutArtist02");
        }
        if (utils.hasScriptVar(player, "askedAboutArtist03"))
        {
            utils.removeScriptVar(player, "askedAboutArtist03");
        }
        if (utils.hasScriptVar(player, "askedAboutArtist04"))
        {
            utils.removeScriptVar(player, "askedAboutArtist04");
        }
        if (utils.hasScriptVar(player, "askedAboutArtist05"))
        {
            utils.removeScriptVar(player, "askedAboutArtist05");
        }
        if (utils.hasScriptVar(player, "askedAboutArtist06"))
        {
            utils.removeScriptVar(player, "askedAboutArtist06");
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.lilas_dinhint");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "npc.conversation.lilas_dinhint");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (lilas_dinhint_condition_museumEventActive(player, self))
        {
            lilas_dinhint_action_clearAskedLocationScrVars(player, self);
            string_id message = new string_id(c_stringFile, "s_605d9185");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lilas_dinhint_condition_cannotChooseArtistButHasntVoted(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lilas_dinhint_condition_canChooseArtist(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lilas_dinhint_condition_alreadyVoted(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (lilas_dinhint_condition_artworkAvailable(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (lilas_dinhint_condition_talkAboutSeanTrenwell(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            boolean hasResponse6 = false;
            if (lilas_dinhint_condition_madeRoomSeanTestimony(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse6 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26152411");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_911a27f8");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_537ad9f6");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d543ced9");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e649bf0a");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8b3d6e46");
                }
                if (hasResponse6)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26c02ad3");
                }
                setObjVar(player, "conversation.lilas_dinhint.branchId", 1);
                npcStartConversation(player, self, "lilas_dinhint", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lilas_dinhint_condition_noEventButArtworkAvailable(player, self))
        {
            lilas_dinhint_action_clearAskedLocationScrVars(player, self);
            string_id message = new string_id(c_stringFile, "s_5d4aadf9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lilas_dinhint_condition_talkAboutSeanTrenwell(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lilas_dinhint_condition_madeRoomSeanTestimony(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (lilas_dinhint_condition_time_allowTimeLeftRequest(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47df8332");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6b5b28b2");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8b3d6e46");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26c02ad3");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9558d37a");
                }
                setObjVar(player, "conversation.lilas_dinhint.branchId", 26);
                npcStartConversation(player, self, "lilas_dinhint", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lilas_dinhint_condition_noEventAndNoArtwork(player, self))
        {
            lilas_dinhint_action_clearAskedLocationScrVars(player, self);
            string_id message = new string_id(c_stringFile, "s_aaa43f7b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lilas_dinhint_condition_talkAboutSeanTrenwell(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lilas_dinhint_condition_madeRoomSeanTestimony(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lilas_dinhint_condition_time_allowTimeLeftRequest(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8b3d6e46");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26c02ad3");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9558d37a");
                }
                setObjVar(player, "conversation.lilas_dinhint.branchId", 29);
                npcStartConversation(player, self, "lilas_dinhint", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("lilas_dinhint"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.lilas_dinhint.branchId");
        if (branchId == 1 && response.equals("s_26152411"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7312fa13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bd69df6");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_911a27f8"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3960dc15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_canVoteArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_canVoteArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_canVoteArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_canVoteArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_canVoteArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_canVoteArtist06(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e09f0f8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b824fcc0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce4aa2ab");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84e3c8d2");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55773f3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4443365b");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_537ad9f6"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5492284e");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_d543ced9"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9c595e48");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_e649bf0a"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_486f6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90b0871f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f115c47");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_8b3d6e46"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7c297430");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7552be07");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d1148d8");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_26c02ad3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71561c79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b67247f1");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. I hope you enjoy your visit. While I've got your ear, you should know that we are currently seeking a new work of art to feature. Would you like to help?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5bd69df6"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_147d9a5b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent. Once per month, I wish to feature a new work of art here at the museum. We have a fine permanent collection on display, as always, but it is my desire to enhance that collection by offering something new from time to time. To that end, we select a small number of artists and approach them with the chance of having their art featured here in Bestine.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'What I ask is that you seek out the artists we are considering and learn of their work. Then return to me and let me know which of them you preferred. Three artists are involved. I can tell you where to find them if you'd like?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look for a small square near the Lucky Despot, and you'll find Vanvi Hotne in that area.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Look near the back of the theater in Mos Entha to find Kolka Zteht.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Giaal Itotr calls Mos Espa home, and you'll usually find him in front of the hotel.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Try Mos Eisley to find Kahfr Oladi. You'll probably find her near the bank.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You'll find Klepa Laeel in Mos Espa. He's often found towards the back of the starport.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_82761006"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_400d075");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_c1f9104"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_67026fa8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_bea3d11"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_23b5bcf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_2ecc437c"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_1057c81b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_55e840b3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_68c6c699");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_39a4e8fa"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_askedAboutArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_36cff745");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition_tellAboutArtist01(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition_tellAboutArtist02(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lilas_dinhint_condition_tellAboutArtist03(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lilas_dinhint_condition_tellAboutArtist04(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lilas_dinhint_condition_tellAboutArtist05(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lilas_dinhint_condition_tellAboutArtist06(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lilas_dinhint_condition_hasAskedAboutALocation(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82761006");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1f9104");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bea3d11");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ecc437c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55e840b3");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39a4e8fa");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9059b2b");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463a0bda");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_b9059b2b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_clearAskedLocationScrVars(player, self);
                string_id message = new string_id(c_stringFile, "s_8338587c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_463a0bda"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_removeEventObjvar(player, self);
                string_id message = new string_id(c_stringFile, "s_8ed85f40");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Boulo Siesi is in Wayfar. She's usually by the cantina.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_e09f0f8"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_voteForArtist01(player, self);
                string_id message = new string_id(c_stringFile, "s_375a5dc0");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah good. Which artist stood out as your favorite?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_b824fcc0"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_voteForArtist02(player, self);
                string_id message = new string_id(c_stringFile, "s_375a5dc0");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah good. Which artist stood out as your favorite?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_ce4aa2ab"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_voteForArtist03(player, self);
                string_id message = new string_id(c_stringFile, "s_375a5dc0");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah good. Which artist stood out as your favorite?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_84e3c8d2"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_voteForArtist04(player, self);
                string_id message = new string_id(c_stringFile, "s_375a5dc0");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah good. Which artist stood out as your favorite?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_55773f3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_voteForArtist05(player, self);
                string_id message = new string_id(c_stringFile, "s_375a5dc0");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah good. Which artist stood out as your favorite?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_4443365b"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                lilas_dinhint_action_voteForArtist06(player, self);
                string_id message = new string_id(c_stringFile, "s_375a5dc0");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah good. Which artist stood out as your favorite?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_90b0871f"))
        {
            if (lilas_dinhint_condition_canBuy(player, self))
            {
                lilas_dinhint_action_giveSchematic(player, self);
                string_id message = new string_id(c_stringFile, "s_7882a978");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_notEnoughCredits(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c8e54945");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c845a58c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm asking for a modest... ahem... donation to the museum of 48,000 credits in exchange for the schematic. Oh, and I should warn you that some knowledge of advanced furniture production is required.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_3f115c47"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fb2eead4");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm asking for a modest... ahem... donation to the museum of 48,000 credits in exchange for the schematic. Oh, and I should warn you that some knowledge of advanced furniture production is required.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_47df8332"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_486f6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90b0871f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f115c47");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. Would you like to purchase a schematic of our featured artwork?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_6b5b28b2"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1aabf4f7");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. Would you like to purchase a schematic of our featured artwork?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_8b3d6e46"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7c297430");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7552be07");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d1148d8");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. Would you like to purchase a schematic of our featured artwork?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_26c02ad3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71561c79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b67247f1");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. Would you like to purchase a schematic of our featured artwork?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && response.equals("s_9558d37a"))
        {
            if (lilas_dinhint_condition_time_withinTheHour(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6dbeccc3");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_justAFewHours(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4f049f4e");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_lessThanADay(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5bc37444");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_moreThanADay(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_519f72a3");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_justAFewDays(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d9e04161");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_lessThanAWeek(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_35487421");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_moreThanAWeek(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a9f1e0f9");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum. Would you like to purchase a schematic of our featured artwork?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_90b0871f"))
        {
            if (lilas_dinhint_condition_canBuy(player, self))
            {
                lilas_dinhint_action_giveSchematic(player, self);
                string_id message = new string_id(c_stringFile, "s_7882a978");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_notEnoughCredits(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c8e54945");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_noInventorySpace(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c845a58c");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm asking for a modest... ahem... donation to the museum of 48,000 credits in exchange for the schematic. Oh, and I should warn you that some knowledge of advanced furniture production is required.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && response.equals("s_3f115c47"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fb2eead4");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm asking for a modest... ahem... donation to the museum of 48,000 credits in exchange for the schematic. Oh, and I should warn you that some knowledge of advanced furniture production is required.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_b9b27823"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cccdca43");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_8b3d6e46"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7c297430");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7552be07");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d1148d8");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_26c02ad3"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_71561c79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b67247f1");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && response.equals("s_9558d37a"))
        {
            if (lilas_dinhint_condition_time_withinTheHour(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6dbeccc3");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_justAFewHours(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4f049f4e");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_lessThanADay(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5bc37444");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_moreThanADay(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_519f72a3");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_justAFewDays(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d9e04161");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_lessThanAWeek(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_35487421");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_time_moreThanAWeek(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a9f1e0f9");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello and welcome to the Bestine Museum.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_7552be07"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eb35002");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Mr. Trenwell? Yes, of course. Many of our latest additions and newest programs have only been possible thanks to funding provided by the efforts of Mr. Trenwell. His large personal contributions - both in time and money - have been generous beyond expectations.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && response.equals("s_6d1148d8"))
        {
            if (lilas_dinhint_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7b488371");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lilas_dinhint_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b67247f1");
                    }
                    setObjVar(player, "conversation.lilas_dinhint.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.lilas_dinhint.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Mr. Trenwell? Yes, of course. Many of our latest additions and newest programs have only been possible thanks to funding provided by the efforts of Mr. Trenwell. His large personal contributions - both in time and money - have been generous beyond expectations.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && response.equals("s_b67247f1"))
        {
            if (!lilas_dinhint_condition_noInventorySpace(player, self))
            {
                lilas_dinhint_action_giveSeansTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_1042bed3");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_noInventorySpace(player, self))
            {
                lilas_dinhint_action_setNoRoomSeanTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_4f010def");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Indeed, I would be quite happy to do exactly that. I'll load that into this datapad that you can then take to Mrs. Aryon.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && response.equals("s_b67247f1"))
        {
            if (!lilas_dinhint_condition_noInventorySpace(player, self))
            {
                lilas_dinhint_action_giveSeansTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_1042bed3");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (lilas_dinhint_condition_noInventorySpace(player, self))
            {
                lilas_dinhint_action_setNoRoomSeanTestimony(player, self);
                string_id message = new string_id(c_stringFile, "s_4f010def");
                removeObjVar(player, "conversation.lilas_dinhint.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ah, of course. I remember you now. I have everything prepared and loaded onto this datapad that you can show to Mrs. Aryon.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.lilas_dinhint.branchId");
        return SCRIPT_CONTINUE;
    }
}
