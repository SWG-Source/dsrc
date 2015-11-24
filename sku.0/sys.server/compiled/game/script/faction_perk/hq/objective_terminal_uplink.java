package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hq;
import script.library.xp;
import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.ai_lib;
import script.library.stealth;

public class objective_terminal_uplink extends script.faction_perk.hq.objective_object
{
    public objective_terminal_uplink()
    {
    }
    public static final string_id MNU_JAM = new string_id("hq", "mnu_jam");
    public static final String VAR_FREQ = "hq.objective.freq";
    public static final int STAGE_NONE = 0;
    public static final int STAGE_BAND = 1;
    public static final int STAGE_FREQ = 2;
    public static final string_id NO_TAMPER = new string_id("faction/faction_hq/faction_hq_response", "no_tamper");
    public static final string_id UPLINK_JAMMED = new string_id("faction/faction_hq/faction_hq_response", "uplink_jammed");
    public static final string_id OTHER_OBJECTIVES = new string_id("faction/faction_hq/faction_hq_response", "other_objectives");
    public static final string_id ONLY_A_BOUNTY_HUNTER = new string_id("faction/faction_hq/faction_hq_response", "only_a_bounty_hunter");
    public static final string_id BEGIN_SCANNING = new string_id("faction/faction_hq/faction_hq_response", "begin_scanning");
    public static final string_id JAM_NOT_IN_COMBAT = new string_id("faction/faction_hq/faction_hq_response", "jam_not_in_combat");
    public static final string_id JAM_NOT_IN_ROOM = new string_id("faction/faction_hq/faction_hq_response", "jam_not_in_room");
    public static final string_id JAM_TOO_FAR = new string_id("faction/faction_hq/faction_hq_response", "jam_too_far");
    public static final string_id INIT_JAMMING = new string_id("faction/faction_hq/faction_hq_response", "init_jamming");
    public static final string_id JAMMING_COMPLETE_SOMEONE_ELSE = new string_id("faction/faction_hq/faction_hq_response", "jamming_complete_someone_else");
    public static final string_id JAMMING_COMPLETE_YOU = new string_id("faction/faction_hq/faction_hq_response", "jamming_complete_you");
    public static final string_id JAMMING_IN_PROGRESS = new string_id("faction/faction_hq/faction_hq_response", "jamming_in_progress");
    public static final string_id ISOLATE_CARRIER_SIGNAL = new string_id("faction/faction_hq/faction_hq_response", "isolate_carrier_signal");
    public static final string_id NARROW_CARRIER_SIGNAL = new string_id("faction/faction_hq/faction_hq_response", "narrow_carrier_signal");
    public static final string_id WHITE_NOISE = new string_id("faction/faction_hq/faction_hq_response", "white_noise");
    public static final string_id SEARCH_HIGHER = new string_id("faction/faction_hq/faction_hq_response", "search_higher");
    public static final string_id SEARCH_LOWER = new string_id("faction/faction_hq/faction_hq_response", "search_lower");
    public static final string_id SUI_JAMMING_TITLE = new string_id("faction/faction_hq/faction_hq_response", "sui_jamming_title");
    public static final string_id SUI_JAMMING_PROMPT = new string_id("faction/faction_hq/faction_hq_response", "sui_jamming_prompt");
    public static final string_id CHANNEL = new string_id("faction/faction_hq/faction_hq_response", "channel");
    public static final string_id BAND = new string_id("faction/faction_hq/faction_hq_response", "band");
    public static final string_id CHANNEL_NUMBER = new string_id("faction/faction_hq/faction_hq_response", "channel_number");
    public static final string_id BAND_NUMBER = new string_id("faction/faction_hq/faction_hq_response", "band_number");
    public static final string_id SID_NO_STEALTH = new string_id("hq", "no_stealth");
    public static final string_id SCANNING_SIGNAL = new string_id("faction/faction_hq/faction_hq_response", "scanning_signal");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, VAR_FREQ, rand(1, 99));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int intState = getState(player, STATE_FEIGN_DEATH);
        if (isDead(player) || isIncapacitated(player) || intState > 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        int mnu = mi.addRootMenu(menu_info_types.ITEM_USE, MNU_JAM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (pvpGetType(player) == PVPTYPE_NEUTRAL)
        {
            pFac = 0;
        }
        if (!pvpAreFactionsOpposed(sFac, pFac))
        {
            sendSystemMessage(player, NO_TAMPER);
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessage(player, UPLINK_JAMMED);
            return SCRIPT_CONTINUE;
        }
        obj_id nextObjective = hq.getNextObjective(structure);
        if (nextObjective != self)
        {
            obj_id priorObjective = hq.getPriorObjective(structure, self);
            if (isIdValid(priorObjective))
            {
                prose_package ppDisableOther = prose.getPackage(hq.PROSE_DISABLE_OTHER, priorObjective, self);
                sendSystemMessageProse(player, ppDisableOther);
            }
            else 
            {
                sendSystemMessage(player, OTHER_OBJECTIVES);
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasSkill(player, "class_bountyhunter_phase2_novice"))
            {
                sendSystemMessage(player, ONLY_A_BOUNTY_HUNTER);
                return SCRIPT_CONTINUE;
            }
            String scriptvar_stage = "jamming." + player + ".stage";
            if (utils.hasScriptVar(self, scriptvar_stage))
            {
                sendSystemMessage(player, BEGIN_SCANNING);
                playJammingGame(self, player);
            }
            else 
            {
                sendSystemMessage(player, BEGIN_SCANNING);
                dictionary d = new dictionary();
                d.put("player", player);
                messageTo(self, "handleStartDelay", d, 3f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void playJammingGame(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, JAM_NOT_IN_COMBAT);
            return;
        }
        location here = getLocation(self);
        location there = getLocation(player);
        if (!canSee(self, player))
        {
            sendSystemMessage(player, JAM_NOT_IN_ROOM);
            return;
        }
        if (getDistance(here, there) > 15f)
        {
            sendSystemMessage(player, JAM_TOO_FAR);
            return;
        }
        String scriptvar = "jamming." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_opt = scriptvar + ".opt";
        String scriptvar_stage = scriptvar + ".stage";
        if (utils.hasScriptVar(self, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(self, scriptvar_pid);
            if (oldpid > -1)
            {
                sui.closeSUI(player, oldpid);
            }
            utils.removeScriptVar(self, scriptvar_pid);
        }
        int stage = utils.getIntScriptVar(self, scriptvar_stage);
        Vector opt = utils.getResizeableIntArrayScriptVar(self, scriptvar_opt);
        if (stage == STAGE_NONE || opt == null || opt.size() == 0)
        {
            stage = STAGE_BAND;
            opt = utils.concatArrays(null, getFreshArray());
        }
        else 
        {
        }
        String title = utils.packStringId(SUI_JAMMING_TITLE);
        string_id promptType = null;
        switch (stage)
        {
            case STAGE_FREQ:
            promptType = CHANNEL;
            break;
            case STAGE_BAND:
            default:
            promptType = BAND;
            break;
        }
        prose_package ppPrompt = prose.getPackage(SUI_JAMMING_PROMPT, promptType);
        String prompt = " \0" + packOutOfBandProsePackage(null, ppPrompt);
        if (opt == null || opt.size() == 0)
        {
            LOG("hqObjective", "invalid opt vector!!");
        }
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < opt.size(); i++)
        {
            string_id entryId = null;
            switch (stage)
            {
                case STAGE_FREQ:
                entryId = CHANNEL_NUMBER;
                break;
                case STAGE_BAND:
                default:
                entryId = BAND_NUMBER;
                break;
            }
            prose_package ppEntry = prose.getPackage(entryId, ((Integer)opt.get(i)).intValue() + 1);
            String entry = " \0" + packOutOfBandProsePackage(null, ppEntry);
            entries = utils.addElement(entries, entry);
        }
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, entries, "handleJammingGame");
        if (pid > -1)
        {
            utils.setScriptVar(self, scriptvar_pid, pid);
            utils.setScriptVar(self, scriptvar_opt, opt);
            utils.setScriptVar(self, scriptvar_stage, stage);
        }
    }
    public int[] getFreshArray() throws InterruptedException
    {
        int[] ret = new int[10];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = i;
        }
        return ret;
    }
    public int handleJammingGame(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        String scriptvar = "jamming." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_opt = scriptvar + ".opt";
        String scriptvar_stage = scriptvar + ".stage";
        utils.removeScriptVar(self, scriptvar_pid);
        int stage = utils.getIntScriptVar(self, scriptvar_stage);
        Vector opt = utils.getResizeableIntArrayScriptVar(self, scriptvar_opt);
        if (stage == STAGE_NONE || opt == null || opt.size() == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int freq = getIntObjVar(self, VAR_FREQ);
        int correct = -1;
        switch (stage)
        {
            case STAGE_FREQ:
            correct = freq % 10;
            break;
            case STAGE_BAND:
            default:
            correct = freq / 10;
            break;
        }
        float delay = 5f;
        string_id text = null;
        if (correct == ((Integer)opt.get(idx)).intValue())
        {
            delay = 1f;
            switch (stage)
            {
                case STAGE_FREQ:
                prose_package ppFreq = prose.getPackage(ISOLATE_CARRIER_SIGNAL, correct + 1);
                sendSystemMessageProse(player, ppFreq);
                sendSystemMessage(player, INIT_JAMMING);
                dictionary d = new dictionary();
                d.put("player", player);
                int cyclemod = getSkillStatMod(player, "droid_find_chance");
                float cyclemultiplier = (100f - (cyclemod * (1 + rand(-0.33f, 0.33f)))) / 100f;
                int cycleCount = Math.round(6 * cyclemultiplier);
                if (cycleCount < 0)
                {
                    cycleCount = 0;
                }
                d.put("cnt", cycleCount);
                messageTo(self, "handleJammingInProgress", d, 3f, false);
                return SCRIPT_CONTINUE;
                case STAGE_BAND:
                default:
                opt = utils.concatArrays(null, getFreshArray());
                stage = STAGE_FREQ;
                prose_package ppSig = prose.getPackage(NARROW_CARRIER_SIGNAL, correct + 1);
                sendSystemMessageProse(player, ppSig);
                break;
            }
        }
        else 
        {
            int findmod = getSkillStatMod(player, "droid_find_chance");
            int roll = rand(1, 150);
            if (roll > 145)
            {
                text = WHITE_NOISE;
            }
            else 
            {
                if (findmod > roll)
                {
                    if (correct > ((Integer)opt.get(idx)).intValue())
                    {
                        text = SEARCH_HIGHER;
                    }
                    else 
                    {
                        text = SEARCH_LOWER;
                    }
                }
                else 
                {
                    if (correct > ((Integer)opt.get(idx)).intValue())
                    {
                        text = SEARCH_LOWER;
                    }
                    else 
                    {
                        text = SEARCH_HIGHER;
                    }
                }
                int speedmod = getSkillStatMod(player, "droid_find_speed");
                float multiplier = (100 - speedmod) / 100f;
                delay *= multiplier;
            }
            utils.removeElementAt(opt, idx);
            sendSystemMessage(player, SCANNING_SIGNAL);
        }
        utils.setScriptVar(self, scriptvar_opt, opt);
        utils.setScriptVar(self, scriptvar_stage, stage);
        if (delay < 1f)
        {
            delay = 1f;
        }
        dictionary d = new dictionary();
        d.put("player", player);
        if (text != null)
        {
            d.put("text", text);
        }
        messageTo(self, "handleJammingGameDelay", d, delay, false);
        return SCRIPT_CONTINUE;
    }
    public int handleJammingGameDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            string_id text = params.getStringId("text");
            if (text != null)
            {
                sendSystemMessage(player, text);
            }
            playJammingGame(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleObjectiveDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleStartDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        playJammingGame(self, player);
        return SCRIPT_CONTINUE;
    }
    public int handleJammingInProgress(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessage(player, JAMMING_COMPLETE_SOMEONE_ELSE);
            return SCRIPT_CONTINUE;
        }
        int cnt = params.getInt("cnt");
        cnt--;
        if (cnt < 0)
        {
            sendSystemMessage(player, JAMMING_COMPLETE_YOU);
            String playerName = getName(player);
            CustomerServiceLog("faction_hq", playerName + "(" + player + "), " + "jammed Uplink Terminal at " + getGameTime() + ".");
            hq.disableObjective(self);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, JAMMING_IN_PROGRESS);
            params.put("cnt", cnt);
            float delay = 3f + rand(-1, 1);
            messageTo(self, "handleJammingInProgress", params, delay, false);
        }
        return SCRIPT_CONTINUE;
    }
}
