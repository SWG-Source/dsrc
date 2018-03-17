package script.faction_perk.hq;

import script.*;
import script.library.*;

import java.util.Vector;

public class objective_terminal_uplink extends script.faction_perk.hq.objective_object
{
    public objective_terminal_uplink()
    {
    }
    private static final string_id MNU_JAM = new string_id("hq", "mnu_jam");
    private static final String VAR_FREQ = "hq.objective.freq";
    private static final int STAGE_NONE = 0;
    private static final int STAGE_BAND = 1;
    private static final int STAGE_FREQ = 2;
    private static final string_id NO_TAMPER = new string_id("faction/faction_hq/faction_hq_response", "no_tamper");
    private static final string_id UPLINK_JAMMED = new string_id("faction/faction_hq/faction_hq_response", "uplink_jammed");
    private static final string_id OTHER_OBJECTIVES = new string_id("faction/faction_hq/faction_hq_response", "other_objectives");
    private static final string_id ONLY_A_BOUNTY_HUNTER = new string_id("faction/faction_hq/faction_hq_response", "only_a_bounty_hunter");
    private static final string_id BEGIN_SCANNING = new string_id("faction/faction_hq/faction_hq_response", "begin_scanning");
    private static final string_id JAM_NOT_IN_COMBAT = new string_id("faction/faction_hq/faction_hq_response", "jam_not_in_combat");
    private static final string_id JAM_NOT_IN_ROOM = new string_id("faction/faction_hq/faction_hq_response", "jam_not_in_room");
    private static final string_id JAM_TOO_FAR = new string_id("faction/faction_hq/faction_hq_response", "jam_too_far");
    private static final string_id INIT_JAMMING = new string_id("faction/faction_hq/faction_hq_response", "init_jamming");
    private static final string_id JAMMING_COMPLETE_SOMEONE_ELSE = new string_id("faction/faction_hq/faction_hq_response", "jamming_complete_someone_else");
    private static final string_id JAMMING_COMPLETE_YOU = new string_id("faction/faction_hq/faction_hq_response", "jamming_complete_you");
    private static final string_id JAMMING_IN_PROGRESS = new string_id("faction/faction_hq/faction_hq_response", "jamming_in_progress");
    private static final string_id ISOLATE_CARRIER_SIGNAL = new string_id("faction/faction_hq/faction_hq_response", "isolate_carrier_signal");
    private static final string_id NARROW_CARRIER_SIGNAL = new string_id("faction/faction_hq/faction_hq_response", "narrow_carrier_signal");
    private static final string_id WHITE_NOISE = new string_id("faction/faction_hq/faction_hq_response", "white_noise");
    private static final string_id SEARCH_HIGHER = new string_id("faction/faction_hq/faction_hq_response", "search_higher");
    private static final string_id SEARCH_LOWER = new string_id("faction/faction_hq/faction_hq_response", "search_lower");
    private static final string_id SUI_JAMMING_TITLE = new string_id("faction/faction_hq/faction_hq_response", "sui_jamming_title");
    private static final string_id SUI_JAMMING_PROMPT = new string_id("faction/faction_hq/faction_hq_response", "sui_jamming_prompt");
    private static final string_id CHANNEL = new string_id("faction/faction_hq/faction_hq_response", "channel");
    private static final string_id BAND = new string_id("faction/faction_hq/faction_hq_response", "band");
    private static final string_id CHANNEL_NUMBER = new string_id("faction/faction_hq/faction_hq_response", "channel_number");
    private static final string_id BAND_NUMBER = new string_id("faction/faction_hq/faction_hq_response", "band_number");
    private static final string_id SID_NO_STEALTH = new string_id("hq", "no_stealth");
    private static final string_id SCANNING_SIGNAL = new string_id("faction/faction_hq/faction_hq_response", "scanning_signal");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, VAR_FREQ, rand(1, 99));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (stealth.hasInvisibleBuff(player))
        {
            sendSystemMessage(player, SID_NO_STEALTH);
            return SCRIPT_CONTINUE;
        }
        int intState = getState(player, STATE_FEIGN_DEATH);
        if (isDead(player) || isIncapacitated(player) || intState > 0)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, MNU_JAM);
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
    private void playJammingGame(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, JAM_NOT_IN_COMBAT);
            return;
        }
        if (!canSee(self, player))
        {
            sendSystemMessage(player, JAM_NOT_IN_ROOM);
            return;
        }
        if (getDistance(getLocation(self), getLocation(player)) > 15f)
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
        Vector opts = utils.getResizeableIntArrayScriptVar(self, scriptvar_opt);
        if (stage == STAGE_NONE || opts == null || opts.size() == 0)
        {
            stage = STAGE_BAND;
            opts = utils.concatArrays(null, getFreshArray());
        }
        String title = utils.packStringId(SUI_JAMMING_TITLE);
        string_id promptType;
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
        if (opts == null || opts.size() == 0)
        {
            LOG("hqObjective", "invalid opt vector!!");
        }
        Vector entries = new Vector();
        entries.setSize(0);
        string_id entryId;
        for (Object opt : opts) {
            entryId = null;
            switch (stage) {
                case STAGE_FREQ:
                    entryId = CHANNEL_NUMBER;
                    break;
                case STAGE_BAND:
                default:
                    entryId = BAND_NUMBER;
                    break;
            }
            prose_package ppEntry = prose.getPackage(entryId, (Integer) opt + 1);
            String entry = " \0" + packOutOfBandProsePackage(null, ppEntry);
            entries = utils.addElement(entries, entry);
        }
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, entries, "handleJammingGame");
        if (pid > -1)
        {
            utils.setScriptVar(self, scriptvar_pid, pid);
            utils.setScriptVar(self, scriptvar_opt, opts);
            utils.setScriptVar(self, scriptvar_stage, stage);
        }
    }
    private int[] getFreshArray() throws InterruptedException
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
        int correct = freq / 10;
        if(stage == STAGE_FREQ){
            correct = freq % 10;
        }
        float delay = 5f;
        string_id text = null;
        if (correct == (Integer) opt.get(idx))
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
                    if (correct > (Integer) opt.get(idx))
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
                    if (correct > (Integer) opt.get(idx))
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
