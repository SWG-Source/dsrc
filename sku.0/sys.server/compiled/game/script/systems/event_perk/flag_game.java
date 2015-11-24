package script.systems.event_perk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.create;
import script.library.factions;
import script.library.prose;

public class flag_game extends script.base_script
{
    public flag_game()
    {
    }
    public static final String[] TEMPLATE = 
    {
        "object/tangible/furniture/all/event_flag_game_neut_banner.iff",
        "object/tangible/furniture/all/event_flag_game_imp_banner.iff",
        "object/tangible/furniture/all/event_flag_game_reb_banner.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        startGame(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        startGame(self);
        return SCRIPT_CONTINUE;
    }
    public void startGame(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "intGameStarted"))
        {
            return;
        }
        utils.setScriptVar(self, "intGameStarted", 1);
        dictionary params = new dictionary();
        params.put("flagType", 0);
        int gameState = 0;
        int canSwitchFlag = 0;
        utils.setScriptVar(self, "event_perk.game_state", gameState);
        setObjVar(self, "event_perk.can_switch_flag", canSwitchFlag);
        setObjVar(self, "event_perk.time_limit", 40);
        setObjVar(self, "event_perk.rebel_score", 0);
        setObjVar(self, "event_perk.imperial_score", 0);
        messageTo(self, "spawnFlag", params, 1, false);
        messageTo(self, "advanceGamePulse", null, 15, false);
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id flag = getObjIdObjVar(self, "event_perk.current_flag");
        messageTo(self, "gameOver", null, 1, false);
        messageTo(flag, "goDie", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int showTimeTillExpiration(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float expirationTimeMinutesFloat = ((lifeSpan + timeStamp) - rightNow) / 60;
        int expirationTimeMinutes = (int)expirationTimeMinutesFloat;
        prose_package showExpiration = new prose_package();
        showExpiration = prose.getPackage(new string_id("event_perk", "show_exp_time"));
        prose.setDI(showExpiration, expirationTimeMinutes);
        sendSystemMessageProse(player, showExpiration);
        return SCRIPT_CONTINUE;
    }
    public int tryRedeeding(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id[] allContents = utils.getAllItemsInBankAndInventory(player);
        int perkCount = 0;
        for (int i = 0; i < allContents.length; i++)
        {
            if (hasObjVar(allContents[i], "event_perk"))
            {
                perkCount++;
            }
            if (perkCount >= 5)
            {
                sendSystemMessage(player, new string_id("event_perk", "redeed_too_many_deeds"));
                return SCRIPT_CONTINUE;
            }
        }
        String sourceDeed = getStringObjVar(self, "event_perk.source_deed");
        float lifeSpan = getFloatObjVar(self, "event_perk.lifeSpan");
        float timeStamp = getFloatObjVar(self, "event_perk.timeStamp");
        float rightNow = getGameTime();
        float adjustedLifeSpan = (lifeSpan + timeStamp) - rightNow;
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id perkDeed = createObject(sourceDeed, playerInventory, "");
        if (isIdValid(perkDeed))
        {
            setObjVar(perkDeed, "event_perk.lifeSpan", adjustedLifeSpan);
            setObjVar(perkDeed, "event_perk.redeeded", 1);
            destroyObject(self);
            sendSystemMessage(player, new string_id("event_perk", "redeed_success"));
        }
        else 
        {
            sendSystemMessage(player, new string_id("event_perk", "redeed_failed"));
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnFlag(obj_id self, dictionary params) throws InterruptedException
    {
        int flagType = params.getInt("flagType");
        obj_id owner = getObjIdObjVar(self, "event_perk.owner");
        float terminalRegistration = getFloatObjVar(self, "event_perk.terminal_registration");
        location here = getLocation(self);
        obj_id flag = create.object(TEMPLATE[flagType], here);
        setObjVar(self, "event_perk.current_flag", flag);
        setObjVar(flag, "event_perk.owner", owner);
        setObjVar(flag, "event_perk.terminal_registration", terminalRegistration);
        setObjVar(flag, "event_perk.mom", self);
        attachScript(flag, "systems.event_perk.flag_game_flag");
        return SCRIPT_CONTINUE;
    }
    public int setTimeLimit(obj_id self, dictionary params) throws InterruptedException
    {
        int timeLimit = params.getInt("timeLimit");
        setObjVar(self, "event_perk.base_time_limit", timeLimit);
        setObjVar(self, "event_perk.time_limit", timeLimit);
        return SCRIPT_CONTINUE;
    }
    public int startGame(obj_id self, dictionary params) throws InterruptedException
    {
        int gameState = utils.getIntScriptVar(self, "event_perk.game_state");
        if (gameState == 4)
        {
            int timeLimit = getIntObjVar(self, "event_perk.base_time_limit");
            setObjVar(self, "event_perk.time_limit", timeLimit);
            obj_id oldFlag = getObjIdObjVar(self, "event_perk.current_flag");
            messageTo(oldFlag, "goDie", null, 1, false);
            params.put("flagType", 0);
            int canSwitchFlag = 0;
            setObjVar(self, "event_perk.can_switch_flag", canSwitchFlag);
            setObjVar(self, "event_perk.rebel_score", 0);
            setObjVar(self, "event_perk.imperial_score", 0);
            messageTo(self, "spawnFlag", params, 1, false);
            messageTo(self, "advanceGamePulse", null, 15, false);
        }
        obj_id flag = getObjIdObjVar(self, "event_perk.current_flag");
        utils.setScriptVar(self, "event_perk.game_state", 1);
        String messageId = "flag_game_game_starting";
        announceStatusToPlayers(self, messageId);
        return SCRIPT_CONTINUE;
    }
    public int tryToSwapFlags(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id flag = getObjIdObjVar(self, "event_perk.current_flag");
        int gameState = utils.getIntScriptVar(self, "event_perk.game_state");
        int playerFactionId = pvpGetAlignedFaction(player);
        String playerFaction = factions.getFactionNameByHashCode(playerFactionId);
        int canSwitchFlag = getIntObjVar(self, "event_perk.can_switch_flag");
        if ((gameState == 1 || gameState == 3) && playerFaction.equals("Imperial") && canSwitchFlag == 0)
        {
            utils.setScriptVar(self, "event_perk.game_state", 2);
            setObjVar(self, "event_perk.can_switch_flag", 2);
            params.put("flagType", 1);
            messageTo(flag, "goDie", null, 1, false);
            messageTo(self, "spawnFlag", params, 5, false);
            String messageId = "flag_game_imperial_capture";
            announceStatusToPlayers(self, messageId);
            return SCRIPT_CONTINUE;
        }
        if ((gameState == 1 || gameState == 2) && playerFaction.equals("Rebel") && canSwitchFlag == 0)
        {
            utils.setScriptVar(self, "event_perk.game_state", 3);
            setObjVar(self, "event_perk.can_switch_flag", 2);
            params.put("flagType", 2);
            messageTo(flag, "goDie", null, 1, false);
            messageTo(self, "spawnFlag", params, 5, false);
            String messageId = "flag_game_rebel_capture";
            announceStatusToPlayers(self, messageId);
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, new string_id("event_perk", "flag_game_cannot_switch_yet"));
        return SCRIPT_CONTINUE;
    }
    public int advanceGamePulse(obj_id self, dictionary params) throws InterruptedException
    {
        int timeLimit = getIntObjVar(self, "event_perk.time_limit");
        int canSwitchFlag = getIntObjVar(self, "event_perk.can_switch_flag");
        int gameState = utils.getIntScriptVar(self, "event_perk.game_state");
        int rebelScore = getIntObjVar(self, "event_perk.rebel_score");
        int imperialScore = getIntObjVar(self, "event_perk.imperial_score");
        if (timeLimit <= 0)
        {
            messageTo(self, "gameOver", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (timeLimit > 0)
        {
            if (canSwitchFlag > 0)
            {
                canSwitchFlag--;
                setObjVar(self, "event_perk.can_switch_flag", canSwitchFlag);
            }
            if (canSwitchFlag < 0)
            {
                setObjVar(self, "event_perk.can_switch_flag", 0);
            }
            timeLimit--;
            setObjVar(self, "event_perk.time_limit", timeLimit);
            messageTo(self, "advanceGamePulse", null, 15, false);
        }
        if (gameState == 2)
        {
            imperialScore++;
            setObjVar(self, "event_perk.imperial_score", imperialScore);
        }
        if (gameState == 3)
        {
            rebelScore++;
            setObjVar(self, "event_perk.rebel_score", rebelScore);
        }
        return SCRIPT_CONTINUE;
    }
    public int gameOver(obj_id self, dictionary params) throws InterruptedException
    {
        int rebelScore = getIntObjVar(self, "event_perk.rebel_score");
        int imperialScore = getIntObjVar(self, "event_perk.imperial_score");
        utils.setScriptVar(self, "event_perk.game_state", 4);
        obj_id flag = getObjIdObjVar(self, "event_perk.current_flag");
        if (rebelScore > imperialScore)
        {
            params.put("flagType", 2);
            messageTo(flag, "goDie", null, 1, false);
            messageTo(self, "spawnFlag", params, 5, false);
            String type = "clienteffect/holoemote_rebel.cef";
            String faction = "Rebel";
            String messageId = "flag_game_over_reb_win";
            announceStatusToPlayers(self, messageId);
            playVictoryEffects(self, faction);
        }
        if (imperialScore > rebelScore)
        {
            params.put("flagType", 1);
            messageTo(flag, "goDie", null, 1, false);
            messageTo(self, "spawnFlag", params, 5, false);
            String type = "clienteffect/holoemote_imperial.cef";
            String faction = "Imperial";
            String messageId = "flag_game_over_imp_win";
            announceStatusToPlayers(self, messageId);
            playVictoryEffects(self, faction);
        }
        if (imperialScore == rebelScore)
        {
            String messageId = "flag_game_over_tie";
            announceStatusToPlayers(self, messageId);
        }
        return SCRIPT_CONTINUE;
    }
    public int showScores(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int rebelScore = getIntObjVar(self, "event_perk.rebel_score");
        int imperialScore = getIntObjVar(self, "event_perk.imperial_score");
        string_id response1 = new string_id("event_perk", "flag_game_score_1");
        prose_package pp1 = prose.getPackage(response1, imperialScore);
        string_id response2 = new string_id("event_perk", "flag_game_score_2");
        prose_package pp2 = prose.getPackage(response2, rebelScore);
        sendSystemMessageProse(player, pp1);
        sendSystemMessageProse(player, pp2);
        return SCRIPT_CONTINUE;
    }
    public void announceStatusToPlayers(obj_id self, String messageId) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int i = 0; i < objPlayers.length; i++)
            {
                sendSystemMessage(objPlayers[i], new string_id("event_perk", messageId));
            }
        }
    }
    public void playVictoryEffects(obj_id self, String faction) throws InterruptedException
    {
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 256.0f);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int i = 0; i < objPlayers.length; i++)
            {
                int playerFactionId = pvpGetAlignedFaction(objPlayers[i]);
                String playerFaction = factions.getFactionNameByHashCode(playerFactionId);
                if (playerFaction.equals("Rebel") && faction.equals("Rebel"))
                {
                    playClientEffectObj(objPlayers[i], "clienteffect/holoemote_rebel.cef", objPlayers[i], "head");
                }
                if (playerFaction.equals("Imperial") && faction.equals("Imperial"))
                {
                    playClientEffectObj(objPlayers[i], "clienteffect/holoemote_imperial.cef", objPlayers[i], "head");
                }
            }
        }
    }
}
