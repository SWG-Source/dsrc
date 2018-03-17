package script.faction_perk.hq;

import script.*;
import script.library.*;

import java.util.Vector;

public class objective_terminal_override extends script.faction_perk.hq.objective_object
{
    public objective_terminal_override()
    {
    }
    private static final string_id MNU_DNA = new string_id("hq", "mnu_dna");
    private static final string_id NO_TAMPER = new string_id("faction/faction_hq/faction_hq_response", "no_tamper");
    private static final string_id FAIL_SAFE_ALREADY_OVERRIDDEN = new string_id("faction/faction_hq/faction_hq_response", "fail_safe_already_overridden");
    private static final string_id OTHER_OBJECTIVES = new string_id("faction/faction_hq/faction_hq_response", "other_objectives");
    private static final string_id ONLY_A_BIO_ENGINEER = new string_id("faction/faction_hq/faction_hq_response", "only_a_bio_engineer");
    private static final string_id NEW_DNA_SAMPLE = new string_id("faction/faction_hq/faction_hq_response", "new_dna_sample");
    private static final string_id NO_SEQUENCE_IN_COMBAT = new string_id("faction/faction_hq/faction_hq_response", "no_sequence_in_combat");
    private static final string_id NOT_SAME_ROOM = new string_id("faction/faction_hq/faction_hq_response", "not_same_room");
    private static final string_id OVERRIDE_TOO_FAR = new string_id("faction/faction_hq/faction_hq_response", "override_too_far");
    private static final string_id STOP_SEQUENCING_OVERRIDDEN = new string_id("faction/faction_hq/faction_hq_response", "stop_sequencing_overridden");
    private static final string_id SEQUENCING_COMPLETE_OVERRIDE = new string_id("faction/faction_hq/faction_hq_response", "sequencing_complete_override");
    private static final string_id DNA_MATCHED = new string_id("faction/faction_hq/faction_hq_response", "dna_matched");
    private static final string_id DNA_MATCHED_PLURAL = new string_id("faction/faction_hq/faction_hq_response", "dna_matched_plural");
    private static final string_id SUI_SEQUENCE_TITLE = new string_id("faction/faction_hq/faction_hq_response", "sui_sequence_title");
    private static final string_id SUI_SEQUENCE_TEXT = new string_id("faction/faction_hq/faction_hq_response", "sui_sequence_text");
    private static final string_id SID_NO_STEALTH = new string_id("hq", "no_stealth");
    private static final int NUM_SEQUENCE = 23;
    private static final String VAR_DNA = "hq.objective.dna";
    private static final String[] NUCLEOTIDES =
    {
        "A",
        "G",
        "C",
        "T"
    };
    private static final String[] PAIRS =
    {
        "AT",
        "TA",
        "GC",
        "CG"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        constructDNAStrand(self);
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
        mi.addRootMenu(menu_info_types.ITEM_USE, MNU_DNA);
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
            sendSystemMessage(player, FAIL_SAFE_ALREADY_OVERRIDDEN);
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
            if (!hasSkill(player, "class_medic_phase1_novice"))
            {
                sendSystemMessage(player, ONLY_A_BIO_ENGINEER);
                return SCRIPT_CONTINUE;
            }
            startDNASequencing(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    private String[] constructDNAStrand(obj_id self) throws InterruptedException
    {
        String[] dna = new String[NUM_SEQUENCE];
        for (int i = 0; i < dna.length; i++)
        {
            dna[i] = NUCLEOTIDES[rand(0, NUCLEOTIDES.length - 1)];
        }
        setObjVar(self, VAR_DNA, dna);
        return dna;
    }
    private void startDNASequencing(obj_id self, obj_id player) throws InterruptedException
    {
        String scriptvar = "dna." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_locks = scriptvar + ".locks";
        if (utils.hasScriptVar(self, scriptvar_pid))
        {
            int oldpid = utils.getIntScriptVar(self, scriptvar_pid);
            sui.closeSUI(player, oldpid);
            utils.removeScriptVar(self, scriptvar_pid);
        }
        if (!utils.hasScriptVar(self, scriptvar_locks))
        {
            int[] locks = new int[NUM_SEQUENCE];
            for (int i = 0; i < locks.length; i++)
            {
                locks[i] = 0;
            }
            utils.setScriptVar(self, scriptvar_locks, locks);
        }
        sendSystemMessage(player, NEW_DNA_SAMPLE);
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(self, "handleSequenceDelay", d, 3f, false);
    }
    private void doSequencing(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessage(player, NO_SEQUENCE_IN_COMBAT);
            return;
        }
        String scriptvar = "dna." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_locks = scriptvar + ".locks";
        String scriptvar_chain = scriptvar + ".chain";
        location here = getLocation(self);
        location there = getLocation(player);
        if (here.cell != there.cell)
        {
            sendSystemMessage(player, NOT_SAME_ROOM);
            return;
        }
        if (getDistance(here, there) > 15f)
        {
            sendSystemMessage(player, OVERRIDE_TOO_FAR);
            return;
        }
        String[] dna = getStringArrayObjVar(self, VAR_DNA);
        if (dna == null || dna.length == 0)
        {
            dna = constructDNAStrand(self);
        }
        int[] locks = utils.getIntArrayScriptVar(self, scriptvar_locks);
        if (locks == null || locks.length == 0)
        {
            return;
        }
        int numLocks = 0;
        String dnaString = "";
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < dna.length; i++)
        {
            if (locks[i] == 0)
            {
                entries = utils.addElement(entries, dna[i]);
                dnaString = dnaString + dna[i];
            }
            else 
            {
                numLocks++;
                for (String PAIR : PAIRS) {
                    if (PAIR.startsWith(dna[i])) {
                        entries = utils.addElement(entries, "\\#00FF00" + PAIR + " \\#.");
                        dnaString = dnaString + "\\#00FF00" + dna[i] + "\\#.";
                        break;
                    }
                }
            }
        }
        String chain = utils.getStringScriptVar(self, scriptvar_chain);
        utils.removeScriptVar(self, scriptvar_chain);
        if (chain == null || chain.equals(""))
        {
            if (chain == null)
            {
                chain = "";
            }
            int chainlength = 8;
            for (int i = 0; i < chainlength; i++)
            {
                chain += NUCLEOTIDES[rand(0, NUCLEOTIDES.length - 1)];
            }
        }
        prose_package pp = prose.getPackage(SUI_SEQUENCE_TEXT, chain, dnaString, numLocks);
        String prompt = " \0" + packOutOfBandProsePackage(null, pp);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, utils.packStringId(SUI_SEQUENCE_TITLE), entries, "handleSequencing");
        if (pid > -1)
        {
            utils.setScriptVar(self, scriptvar_pid, pid);
            utils.setScriptVar(self, scriptvar_chain, chain);
        }
    }
    public int handleSequencing(obj_id self, dictionary params) throws InterruptedException
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
        String scriptvar = "dna." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_locks = scriptvar + ".locks";
        String scriptvar_chain = scriptvar + ".chain";
        utils.removeScriptVar(self, scriptvar_pid);
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessage(player, STOP_SEQUENCING_OVERRIDDEN);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int newLocks = 0;
        int[] locks = utils.getIntArrayScriptVar(self, scriptvar_locks);
        if (locks == null || locks.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int selrow = sui.getListboxSelectedRow(params);
        if (selrow > -1)
        {
            String[] dna = getStringArrayObjVar(self, VAR_DNA);
            if (dna == null || dna.length == 0)
            {
                constructDNAStrand(self);
                return SCRIPT_CONTINUE;
            }
            String chain = utils.getStringScriptVar(self, scriptvar_chain);
            if (chain == null || chain.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < chain.length(); i++)
            {
                int idx = selrow + i;
                if (idx < dna.length)
                {
                    String nucleotide = chain.substring(i, i + 1);
                    String pair = dna[idx] + nucleotide;
                    if (locks[idx] == 0 && utils.getElementPositionInArray(PAIRS, pair) > -1)
                    {
                        locks[idx] = 1;
                        newLocks++;
                    }
                }
                else 
                {
                    break;
                }
            }
            utils.setScriptVar(self, scriptvar_locks, locks);
        }
        prose_package pp = prose.getPackage(DNA_MATCHED_PLURAL, newLocks);
        if (newLocks == 1)
        {
            pp = prose.getPackage(DNA_MATCHED, newLocks);
        }
        sendSystemMessageProse(player, pp);
        int totalLocks = 0;
        for (int lock : locks) {
            if (lock != 0) {
                totalLocks++;
            }
        }
        if (totalLocks == locks.length)
        {
            String playerName = getName(player);
            CustomerServiceLog("faction_hq", playerName + "(" + player + "), " + "disabled Override Terminal at " + getGameTime() + ".");
            sendSystemMessage(player, SEQUENCING_COMPLETE_OVERRIDE);
            hq.disableObjective(self);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, scriptvar_chain);
        sendSystemMessage(player, NEW_DNA_SAMPLE);
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(self, "handleSequenceDelay", d, 3f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSequenceDelay(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player))
        {
            doSequencing(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
