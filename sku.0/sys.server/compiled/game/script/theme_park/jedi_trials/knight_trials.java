package script.theme_park.jedi_trials;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.create;
import script.library.factions;
import script.library.force_rank;
import script.library.jedi;
import script.library.jedi_trials;
import script.library.prose;
import script.library.sui;
import script.library.utils;
import script.library.xp;

public class knight_trials extends script.base_script
{
    public knight_trials()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String config = getConfigSetting("JediConfig", "skipJediKnightTrials");
        if (config != null && config.equals("true"))
        {
            int[] questList = new int[1];
            int rowCouncilChoice = dataTableSearchColumnForString("chooseCouncil", "trialType", jedi_trials.KNIGHT_TRIALS_DATATABLE);
            questList[0] = rowCouncilChoice;
            setObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR, questList);
            setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, 0);
            messageTo(self, "handleForceShrineTrialMessage", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR))
        {
            messageTo(self, "initializeKnightTrials", null, 2, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int jediTrialsTurnedOff(obj_id self, dictionary params) throws InterruptedException
    {
        sui.msgbox(self, new string_id("spam", "trials_turned_off"));
        messageTo(self, "jediTrialsCleanup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String frsConfig = getConfigSetting("GameServer", "enableFRS");
        if (frsConfig == null || frsConfig.length() < 1)
        {
            messageTo(self, "jediTrialsTurnedOff", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        String config = getConfigSetting("JediConfig", "skipJediKnightTrials");
        if (config != null && config.equals("true"))
        {
            int[] questList = new int[1];
            int rowCouncilChoice = dataTableSearchColumnForString("chooseCouncil", "trialType", jedi_trials.KNIGHT_TRIALS_DATATABLE);
            questList[0] = rowCouncilChoice;
            setObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR, questList);
            setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, 0);
            messageTo(self, "handleForceShrineTrialMessage", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR))
        {
            messageTo(self, "initializeKnightTrials", null, 2, false);
        }
        else 
        {
            if (hasObjVar(self, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR))
            {
                if (!jedi_trials.isValidShrineLocObjVar(self))
                {
                    location invalidShrineLoc = getLocationObjVar(self, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR);
                    String planet = invalidShrineLoc.area;
                    location forceShrineLoc = jedi_trials.chooseForceShrineOnPlanet(planet);
                    setObjVar(self, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR, forceShrineLoc);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int initializeKnightTrials(obj_id self, dictionary params) throws InterruptedException
    {
        jedi_trials.initializeKnightTrials(self);
        return SCRIPT_CONTINUE;
    }
    public int handleForceShrineTrialMessage(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR) || !hasObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR))
        {
            messageTo(self, "jediTrialsCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int[] questList = getIntArrayObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        if (trialNum >= questList.length)
        {
            messageTo(self, "handleJediKnightTrialsComplete", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int datatableRow = questList[trialNum];
        int numDatatableRows = dataTableGetNumRows(jedi_trials.KNIGHT_TRIALS_DATATABLE);
        if (datatableRow >= numDatatableRows)
        {
            trialNum = trialNum + 1;
            setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, trialNum);
            dictionary webster = new dictionary();
            webster.put("nextTrial", true);
            messageTo(self, "handleForceShrineTrialMessage", webster, 1, false);
            return SCRIPT_CONTINUE;
        }
        String trialType = dataTableGetString(jedi_trials.KNIGHT_TRIALS_DATATABLE, datatableRow, "trialType");
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR))
        {
            removeObjVar(self, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR);
        }
        if (trialType.equals("chooseCouncil"))
        {
            jedi_trials.threeButtonMsgBox(self, self, "handleKnightTrialsCouncilChoice", jedi_trials.SID_FORCE_SHRINE_TITLE, jedi_trials.SID_COUNCIL_CHOICE_MSG, jedi_trials.SID_LIGHTSIDE_BUTTON, jedi_trials.SID_CANCEL_BUTTON, jedi_trials.SID_DARKSIDE_BUTTON);
            return SCRIPT_CONTINUE;
        }
        if (trialType.equals("huntAndDestroy") || trialType.equals("factionHuntAndDestroy"))
        {
            if (hasObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR))
            {
                int chosenCouncil = getIntObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR);
                if (isOpposedFaction(self, chosenCouncil))
                {
                    giveOpposedFactionWarning(self, chosenCouncil);
                    return SCRIPT_CONTINUE;
                }
            }
            if (!hasObjVar(self, "handlePlayerCombatKill"))
            {
                setObjVar(self, "handlePlayerCombatKill", true);
            }
            String trialName = jedi_trials.getJediTrialsDatatableString(self, "trialName");
            if (trialName != null || !trialName.equals(""))
            {
                string_id sid_trialMsg = new string_id("jedi_trials", trialName);
                if (trialType.equals("factionHuntAndDestroy"))
                {
                    if (!hasObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR))
                    {
                        if (hasObjVar(self, "handlePlayerCombatKill"))
                        {
                            removeObjVar(self, "handlePlayerCombatKill");
                        }
                        int rowCouncilChoice = dataTableSearchColumnForString("chooseCouncil", "trialType", jedi_trials.KNIGHT_TRIALS_DATATABLE);
                        int trialNumCouncilChoice = dataTableGetInt(jedi_trials.KNIGHT_TRIALS_DATATABLE, rowCouncilChoice, "trialNum");
                        setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, trialNumCouncilChoice);
                        messageTo(self, "handleForceShrineTrialMessage", null, 0, false);
                        return SCRIPT_CONTINUE;
                    }
                    int council = getIntObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR);
                    switch (council)
                    {
                        case force_rank.DARK_COUNCIL:
                        trialName = trialName + "_dark";
                        break;
                        case force_rank.LIGHT_COUNCIL:
                        trialName = trialName + "_light";
                        break;
                        default:
                        jedi_trials.giveGenericForceShrineMessage(self);
                        return SCRIPT_CONTINUE;
                    }
                    sid_trialMsg = new string_id("jedi_trials", trialName);
                }
                String str_next = utils.packStringId(jedi_trials.SID_KNIGHT_EMPTY);
                if (params.containsKey("nextTrial"))
                {
                    str_next = utils.packStringId(jedi_trials.SID_KNIGHT_CURRENT_TRIAL_COMPLETE);
                }
                String str_trialMsg = utils.packStringId(sid_trialMsg);
                String str_message = str_next + " " + str_trialMsg;
                int targetTrialAmt = jedi_trials.getJediTrialsDatatableInt(self, "trialTargetAmt");
                if (targetTrialAmt > 1)
                {
                    int curTrialAmt = getIntObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR);
                    str_message = str_message + " " + curTrialAmt + " of " + targetTrialAmt;
                }
                jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_FORCE_SHRINE_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleForceShrineWrongShrineMessage(obj_id self, dictionary params) throws InterruptedException
    {
        sendSystemMessage(self, jedi_trials.SID_FORCE_SHRINE_WRONG);
        return SCRIPT_CONTINUE;
    }
    public int handleForceShrineWrongPlanetMessage(obj_id self, dictionary params) throws InterruptedException
    {
        location shrineLoc = getLocationObjVar(self, jedi_trials.JEDI_TRIALS_SHRINELOC_OBJVAR);
        string_id planet = new string_id("jedi_trials", shrineLoc.area);
        prose_package msg = prose.getPackage(jedi_trials.SID_FORCE_SHRINE_REMIND, planet);
        sendSystemMessageProse(self, msg);
        return SCRIPT_CONTINUE;
    }
    public int handleKnightTrialsCouncilChoice(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        String revert = params.getString(sui.MSGBOX_BTN_REVERT + ".RevertWasPressed");
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            string_id closeButton = jedi_trials.SID_CLOSE_BUTTON;
            string_id title = jedi_trials.SID_KNIGHT_TRIALS_TITLE;
            String str_message = "";
            if (revert != null && !revert.equals(""))
            {
                if (isOpposedFaction(self, force_rank.DARK_COUNCIL))
                {
                    str_message = utils.packStringId(jedi_trials.SID_FACTION_WRONG_CHOICE_DARK);
                    jedi_trials.oneButtonMsgBox(self, self, "noHandler", title, str_message, closeButton);
                    return SCRIPT_CONTINUE;
                }
                playMusic(self, "sound/music_themequest_victory_imperial.snd");
                sendSystemMessage(self, jedi_trials.SID_DARK_SIDE_CHOSEN);
                setObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR, force_rank.DARK_COUNCIL);
            }
            else 
            {
                if (isOpposedFaction(self, force_rank.LIGHT_COUNCIL))
                {
                    str_message = utils.packStringId(jedi_trials.SID_FACTION_WRONG_CHOICE_LIGHT);
                    jedi_trials.oneButtonMsgBox(self, self, "noHandler", title, str_message, closeButton);
                    return SCRIPT_CONTINUE;
                }
                playMusic(self, "sound/music_themequest_victory_rebel.snd");
                sendSystemMessage(self, jedi_trials.SID_LIGHT_SIDE_CHOSEN);
                setObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR, force_rank.LIGHT_COUNCIL);
            }
            messageTo(self, "handleTrialComplete", null, 1, false);
            break;
            case sui.BP_CANCEL:
            sendSystemMessage(self, jedi_trials.SID_DELAYED_COUNCIL_CHOICE);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerCombatKill(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR) || !hasObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR))
        {
            messageTo(self, "jediTrialsCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int[] questList = getIntArrayObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR);
        if (questList == null || questList.length < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR);
        if (trialNum >= questList.length)
        {
            messageTo(self, "handleJediKnightTrialsComplete", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int datatableRow = questList[trialNum];
        String trialType = dataTableGetString(jedi_trials.KNIGHT_TRIALS_DATATABLE, datatableRow, "trialType");
        if (trialType == null || trialType.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        String datatableColumn = "trialTarget";
        if (trialType.equals("factionHuntAndDestroy"))
        {
            if (!hasObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR))
            {
                if (hasObjVar(self, "handlePlayerCombatKill"))
                {
                    removeObjVar(self, "handlePlayerCombatKill");
                }
                int rowCouncilChoice = dataTableSearchColumnForString("chooseCouncil", "trialType", jedi_trials.KNIGHT_TRIALS_DATATABLE);
                int trialNumCouncilChoice = dataTableGetInt(jedi_trials.KNIGHT_TRIALS_DATATABLE, rowCouncilChoice, "trialNum");
                setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, trialNumCouncilChoice);
                messageTo(self, "handleForceShrineTrialMessage", null, 0, false);
                return SCRIPT_CONTINUE;
            }
            int council = getIntObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR);
            switch (council)
            {
                case force_rank.DARK_COUNCIL:
                datatableColumn = "trialTarget_Imperial";
                break;
                case force_rank.LIGHT_COUNCIL:
                datatableColumn = "trialTarget_Rebel";
                break;
            }
        }
        if (jedi_trials.checkForGroupCombatQuestKill(self, params, datatableColumn))
        {
            if (hasObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR))
            {
                int chosenCouncil = getIntObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR);
                if (isOpposedFaction(self, chosenCouncil))
                {
                    giveOpposedFactionWarning(self, chosenCouncil);
                    return SCRIPT_CONTINUE;
                }
            }
            int currentKills = 1;
            if (hasObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR))
            {
                currentKills = getIntObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR) + 1;
            }
            sendSystemMessage(self, jedi_trials.SID_KNIGHT_TRIALS_PROGRESS);
            int targetKills = jedi_trials.getJediTrialsDatatableInt(self, "trialTargetAmt");
            if (currentKills >= targetKills)
            {
                messageTo(self, "handleTrialComplete", null, 1, false);
            }
            else 
            {
                setObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR, currentKills);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleTrialComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR))
        {
            removeObjVar(self, jedi_trials.JEDI_TRIALS_CUR_TRIAL_TOTAL_OBJVAR);
        }
        if (hasObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR))
        {
            removeObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR);
        }
        if (hasObjVar(self, "handlePlayerCombatKill"))
        {
            removeObjVar(self, "handlePlayerCombatKill");
        }
        if (!jedi_trials.isEligibleForJediKnightTrials(self))
        {
            String str_message = utils.packStringId(jedi_trials.SID_KNIGHT_TRIALS_BEING_REMOVED);
            jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_KNIGHT_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
            messageTo(self, "jediTrialsCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        int[] questList = getIntArrayObjVar(self, jedi_trials.KNIGHT_QUESTLIST_OBJVAR);
        int trialNum = getIntObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR) + 1;
        setObjVar(self, jedi_trials.JEDI_TRIALS_TRIALNUMBER_OBJVAR, trialNum);
        if (trialNum >= questList.length)
        {
            messageTo(self, "handleJediKnightTrialsComplete", null, 1, false);
        }
        else 
        {
            dictionary webster = new dictionary();
            webster.put("nextTrial", true);
            messageTo(self, "handleForceShrineTrialMessage", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleJediKnightTrialsComplete(obj_id self, dictionary params) throws InterruptedException
    {
        String darkRankSkill = force_rank.getForceRankSkill(0, force_rank.DARK_COUNCIL);
        String lightRankSkill = force_rank.getForceRankSkill(0, force_rank.LIGHT_COUNCIL);
        if (darkRankSkill == null && lightRankSkill == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasSkill(self, darkRankSkill) || hasSkill(self, lightRankSkill))
        {
            messageTo(self, "jediTrialsCleanup", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        String music = "sound/music_become_dark_jedi.snd";
        String clientFx = "clienteffect/trap_electric_01.cef";
        location enclaveLoc = new location(5079.0f, 0.0f, 305.0f, "yavin4", null);
        String enclaveName = "dark jedi enclave";
        string_id trialsCompleteMsg = jedi_trials.SID_KNIGHT_TRIALS_COMPLETED_DARK;
        int jediCouncil = getIntObjVar(self, jedi_trials.KNIGHT_COUNCIL_CHOICE_OBJVAR);
        switch (jediCouncil)
        {
            case force_rank.DARK_COUNCIL:
            enclaveLoc = new location(5079.0f, 0.0f, 305.0f, "yavin4", null);
            enclaveName = "Dark Jedi Enclave";
            trialsCompleteMsg = jedi_trials.SID_KNIGHT_TRIALS_COMPLETED_DARK;
            music = "sound/music_become_dark_jedi.snd";
            clientFx = "clienteffect/trap_electric_01.cef";
            break;
            case force_rank.LIGHT_COUNCIL:
            enclaveLoc = new location(-5575.0f, 0.0f, 4905.0f, "yavin4", null);
            enclaveName = "Light Jedi Enclave";
            trialsCompleteMsg = jedi_trials.SID_KNIGHT_TRIALS_COMPLETED_LIGHT;
            music = "sound/music_become_light_jedi.snd";
            clientFx = "clienteffect/trap_electric_01.cef";
            break;
        }
        playMusic(self, music);
        playClientEffectLoc(self, clientFx, here, 0.1f);
        obj_id enclaveWaypoint = createWaypointInDatapad(self, enclaveLoc);
        setWaypointName(enclaveWaypoint, enclaveName);
        grantSkill(self, jedi_trials.JEDI_KNIGHT_SKBOX);
        force_rank.addToForceRankSystem(self, jediCouncil);
        String str_message = utils.packStringId(trialsCompleteMsg);
        jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_KNIGHT_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
        messageTo(self, "jediTrialsCleanup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int jediTrialsCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        jedi_trials.doJediTrialsCleanup(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSkillRevoked(obj_id self, String skillName) throws InterruptedException
    {
        if (skillName.startsWith("force_discipline"))
        {
            if (!jedi_trials.isEligibleForJediKnightTrials(self))
            {
                if (!hasObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR))
                {
                    setObjVar(self, jedi_trials.JEDI_TRIALS_INELIGIBLE_WARNING_OBJVAR, true);
                    String str_message = utils.packStringId(jedi_trials.SID_KNIGHT_TRIALS_NO_LONGER_ELIGIBLE);
                    jedi_trials.oneButtonMsgBox(self, self, "noHandler", jedi_trials.SID_KNIGHT_TRIALS_TITLE, str_message, jedi_trials.SID_CLOSE_BUTTON);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isOpposedFaction(obj_id player, int jediCouncil) throws InterruptedException
    {
        String opposedFaction = "";
        switch (jediCouncil)
        {
            case force_rank.DARK_COUNCIL:
            opposedFaction = factions.FACTION_REBEL;
            break;
            case force_rank.LIGHT_COUNCIL:
            opposedFaction = factions.FACTION_IMPERIAL;
            break;
            default:
            return false;
        }
        String playerFaction = factions.getFaction(player);
        if (playerFaction != null && playerFaction.equals(opposedFaction))
        {
            return true;
        }
        return false;
    }
    public void giveOpposedFactionWarning(obj_id player, int jediCouncil) throws InterruptedException
    {
        string_id closeButton = jedi_trials.SID_CLOSE_BUTTON;
        string_id title = jedi_trials.SID_KNIGHT_TRIALS_TITLE;
        String str_message = "";
        switch (jediCouncil)
        {
            case force_rank.DARK_COUNCIL:
            str_message = str_message = utils.packStringId(jedi_trials.SID_FACTION_WRONG_DARK);
            break;
            case force_rank.LIGHT_COUNCIL:
            str_message = str_message = utils.packStringId(jedi_trials.SID_FACTION_WRONG_LIGHT);
            break;
        }
        jedi_trials.oneButtonMsgBox(player, player, "noHandler", title, str_message, closeButton);
        return;
    }
}
