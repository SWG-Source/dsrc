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

public class objective_terminal_override extends script.faction_perk.hq.objective_object
{
    public objective_terminal_override()
    {
    }
    public static final string_id MNU_DNA = new string_id("hq", "mnu_dna");
    public static final int NUM_SEQUENCE = 23;
    public static final String VAR_DNA = "hq.objective.dna";
    public static final String[] NUCLEOTIDES = 
    {
        "A",
        "G",
        "C",
        "T"
    };
    public static final String[] PAIRS = 
    {
        "AT",
        "TA",
        "GC",
        "CG"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "type", "terminal");
        setObjVar(self, "intTerminal", 1);
        constructDNAStrand(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_DNA);
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
            sendSystemMessageTestingOnly(player, "The fail-safe sequence has already been overridden.");
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
            startDNASequencing(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public String[] constructDNAStrand(obj_id self) throws InterruptedException
    {
        String[] dna = new String[NUM_SEQUENCE];
        for (int i = 0; i < dna.length; i++)
        {
            dna[i] = NUCLEOTIDES[rand(0, NUCLEOTIDES.length - 1)];
        }
        setObjVar(self, VAR_DNA, dna);
        return dna;
    }
    public void startDNASequencing(obj_id self, obj_id player) throws InterruptedException
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
        sendSystemMessageTestingOnly(player, "Retrieving new DNA sample...");
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(self, "handleSequenceDelay", d, 3f, false);
    }
    public void doSequencing(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(player))
        {
            sendSystemMessageTestingOnly(player, "You cannot DNA sequence while you are in combat!");
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
            sendSystemMessageTestingOnly(player, "You cannot sequences DNA from the terminal if you are not even in the same room!");
            return;
        }
        if (getDistance(here, there) > 15f)
        {
            sendSystemMessageTestingOnly(player, "You are too far away from the override terminal to continue sequencing!");
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
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < dna.length; i++)
        {
            if (locks[i] == 0)
            {
                entries = utils.addElement(entries, dna[i]);
            }
            else 
            {
                numLocks++;
                for (int n = 0; n < PAIRS.length; n++)
                {
                    if (PAIRS[n].startsWith(dna[i]))
                    {
                        entries = utils.addElement(entries, PAIRS[n]);
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
            int harvestmod = getSkillStatMod(player, "dna_harvesting");
            int maxlength = 3 + Math.round(rand(0.25f, 1.25f) * harvestmod / 20f);
            int chainlength = rand(3, 3 + harvestmod / 20);
            if (chainlength > 8)
            {
                chainlength = 8;
            }
            for (int i = 0; i < chainlength; i++)
            {
                chain += NUCLEOTIDES[rand(0, NUCLEOTIDES.length - 1)];
            }
        }
        String title = "DNA SEQUENCING";
        String prompt = "DNA Sequence Processing...\nComplete the missing pairs: AT,TA,GC,CG\n";
        prompt += "Matched Pairs: " + numLocks + "\n";
        prompt += "Sampled Chain: " + chain + "\n\n";
        prompt += "Select the DNA index to match the chain to...";
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, entries, "handleSequencing");
        if (pid > -1)
        {
            utils.setScriptVar(self, scriptvar_pid, pid);
            utils.setScriptVar(self, scriptvar_chain, chain);
        }
    }
    public int handleSequencing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            LOG("hqObjective", "invalid player!");
            return SCRIPT_CONTINUE;
        }
        String scriptvar = "dna." + player;
        String scriptvar_pid = scriptvar + ".pid";
        String scriptvar_locks = scriptvar + ".locks";
        String scriptvar_chain = scriptvar + ".chain";
        utils.removeScriptVar(self, scriptvar_pid);
        if (hasObjVar(self, hq.VAR_IS_DISABLED))
        {
            sendSystemMessageTestingOnly(player, "You stop sequencing as the fail-safe sequence has already been overridden.");
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
                    if (utils.getElementPositionInArray(PAIRS, pair) > -1)
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
        sendSystemMessageTestingOnly(player, "You match " + newLocks + " new sets of nucleotides.");
        int totalLocks = 0;
        for (int i = 0; i < locks.length; i++)
        {
            if (locks[i] != 0)
            {
                totalLocks++;
            }
        }
        if (totalLocks == locks.length)
        {
            sendSystemMessageTestingOnly(player, "Sequencing complete! You disable the security override for the facility...");
            hq.disableObjective(self);
            xp.grant(player, xp.CRAFTING_BIO_ENGINEER_CREATURE, 1000);
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, scriptvar_chain);
        sendSystemMessageTestingOnly(player, "Retrieving new DNA sample...");
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
