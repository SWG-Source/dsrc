package script.systems.gcw;

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
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_JAM);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "type", "terminal");
        setObjVar(self, "intTerminal", 1);
        setObjVar(self, VAR_FREQ, rand(1, 99));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (pvpGetType(player) != PVPTYPE_DECLARED)
        {
            sendSystemMessageTestingOnly(player, "Only declared factional personnel may access this terminal!");
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getObjIdObjVar(self, "objParent");
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        int sFac = pvpGetAlignedFaction(structure);
        int pFac = pvpGetAlignedFaction(player);
        if (!pvpAreFactionsOpposed(sFac, pFac))
        {
            sendSystemMessageTestingOnly(player, "You are not an enemy of this structure. Why would you want to tamper?");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessageTestingOnly(player, "It's no use! The uplink has been jammed.");
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
                sendSystemMessageTestingOnly(player, "Other objectives must be disabled prior to gaining access to this one.");
            }
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            String scriptvar_stage = "jamming." + player + ".stage";
            if (utils.hasScriptVar(self, scriptvar_stage))
            {
                sendSystemMessageTestingOnly(player, "You resume scanning for baseline carrier signals...");
                playJammingGame(self, player);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "You begin scanning for baseline carrier signals...");
                dictionary d = new dictionary();
                d.put("player", player);
                messageTo(self, "handleStartDelay", d, 3f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void playJammingGame(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id objParent = getObjIdObjVar(self, "objParent");
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessageTestingOnly(player, "You cannot jam this uplink while you are in combat!");
            return;
        }
        location here = getLocation(self);
        location there = getLocation(player);
        if (here.cell != there.cell)
        {
            sendSystemMessageTestingOnly(player, "You cannot jam the uplink if you are not even in the same room!");
            return;
        }
        if (getDistance(here, there) > 15f)
        {
            sendSystemMessageTestingOnly(player, "You are too far away from the uplink to continue jamming!");
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
            opt = opt = utils.concatArrays(null, getFreshArray());
        }
        else 
        {
        }
        String title = "JAMMING...";
        String prompt = "Select the ";
        switch (stage)
        {
            case STAGE_FREQ:
            prompt += "channel ";
            break;
            case STAGE_BAND:
            default:
            prompt += "band ";
            break;
        }
        prompt += " that you wish to search.";
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < opt.size(); i++)
        {
            String entry = "";
            switch (stage)
            {
                case STAGE_FREQ:
                entry = "Channel #";
                break;
                case STAGE_BAND:
                default:
                entry = "Band #";
                break;
            }
            entry += Integer.toString(((Integer)opt.get(i)).intValue() + 1);
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
        String text = null;
        if (correct == ((Integer)opt.get(idx)).intValue())
        {
            delay = 1f;
            switch (stage)
            {
                case STAGE_FREQ:
                sendSystemMessageTestingOnly(player, "You isolate the carrier signal to Channel #" + Integer.toString(correct + 1));
                sendSystemMessageTestingOnly(player, "Initializing jamming sequence...");
                dictionary d = new dictionary();
                d.put("player", player);
                int cyclemod = getSkillStatMod(player, "droid_find_chance");
                cyclemod *= rand(0.67f, 1.33f);
                float cyclemultiplier = (100f - cyclemod) / 100f;
                int cycleCount = Math.round(10f * cyclemultiplier);
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
                sendSystemMessageTestingOnly(player, "You narrow the carrier signal down to Band #" + Integer.toString(correct + 1));
                break;
            }
        }
        else 
        {
            int findmod = getSkillStatMod(player, "droid_find_chance");
            int roll = rand(1, 150);
            if (roll > 145)
            {
                text = "You lose concentration and become lost in a sea of white noise...";
            }
            else 
            {
                if (findmod > roll)
                {
                    if (correct > ((Integer)opt.get(idx)).intValue())
                    {
                        text = "You feel like you need to search higher...";
                    }
                    else 
                    {
                        text = "You feel like you need to search lower...";
                    }
                }
                else 
                {
                    if (correct > ((Integer)opt.get(idx)).intValue())
                    {
                        text = "You feel like you need to search lower...";
                    }
                    else 
                    {
                        text = "You feel like you need to search higher...";
                    }
                }
                int speedmod = getSkillStatMod(player, "droid_find_speed");
                float multiplier = (100 - speedmod) / 100f;
                delay *= multiplier;
            }
            utils.removeElementAt(opt, idx);
        }
        utils.setScriptVar(self, scriptvar_opt, opt);
        utils.setScriptVar(self, scriptvar_stage, stage);
        if (delay < 1f)
        {
            delay = 1f;
        }
        dictionary d = new dictionary();
        d.put("player", player);
        if (text != null && !text.equals(""))
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
            String text = params.getString("text");
            if (text != null && !text.equals(""))
            {
                sendSystemMessageTestingOnly(player, text);
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
            sendSystemMessageTestingOnly(player, "Jamming complete! Someone else has disable the uplink...");
            return SCRIPT_CONTINUE;
        }
        int cnt = params.getInt("cnt");
        cnt--;
        if (cnt < 0)
        {
            sendSystemMessageTestingOnly(player, "Jamming complete! You disable the uplink...");
            hq.disableObjective(self);
            xp.grant(player, xp.BOUNTYHUNTER, 1000);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Jamming in progress...");
            params.put("cnt", cnt);
            float delay = 3f * rand(-2f, 2f);
            messageTo(self, "handleJammingInProgress", params, delay, false);
        }
        return SCRIPT_CONTINUE;
    }
}
