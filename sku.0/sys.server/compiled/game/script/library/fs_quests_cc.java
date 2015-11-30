package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.community_crafting;
import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;

public class fs_quests_cc extends script.base_script
{
    public fs_quests_cc()
    {
    }
    public static final java.text.NumberFormat floatFormat = new java.text.DecimalFormat("###0.00%");
    public static final String SCRIPTVAR_CRAFTING_TRACKER = fs_dyn_village.OBJVAR_MY_MASTER_OBJECT;
    public static final String SCRIPTVAR_VILLAGE_PHASE = "community_crafting.village_phase";
    public static final String SCRIPTVAR_TRACKING_QUALITY = "community_crafting.quality";
    public static final String SCRIPTVAR_TRACKING_QUANTITY = "community_crafting.quantity";
    public static final String SCRIPTVAR_TRACKING_SLOTS = "community_crafting.slots";
    public static final String SCRIPTVAR_TRACKING_NUM_SLOTS = "community_crafting.numSlots";
    public static final String SCRIPTVAR_MIN_INGREDIENTS = "community_crafting.minIngredients";
    public static final string_id SID_RANKINGS_TITLE = new string_id("crafting", "player_rankings");
    public static final string_id SID_ATTRIBUTES_TITLE = new string_id("crafting", "project_attributes");
    public static final string_id SID_NO_PLAYERS = new string_id("crafting", "no_players");
    public static final string_id SID_ATTRIBUTES_PROMPT = new string_id("crafting", "attributes_prompt");
    public static final string_id SID_QUALITY_PROMPT = new string_id("crafting", "quality_prompt");
    public static final string_id SID_QUALITY_PROMPT_LIMITED = new string_id("crafting", "quality_prompt_limited");
    public static final string_id SID_QUANTITY_PROMPT = new string_id("crafting", "quantity_prompt");
    public static final string_id SID_QUANTITY_PROMPT_LIMITED = new string_id("crafting", "quantity_prompt_limited");
    public static boolean testCommunityCraftingEnabled(obj_id npc, int phase) throws InterruptedException
    {
        if (utils.getIntScriptVar(npc, SCRIPTVAR_VILLAGE_PHASE) != phase)
        {
            return false;
        }
        obj_id craftingTracker = utils.getObjIdScriptVar(npc, SCRIPTVAR_CRAFTING_TRACKER);
        if (isIdValid(craftingTracker))
        {
            return community_crafting.isSessionActive(craftingTracker);
        }
        return false;
    }
    public static void showPlayerProjectAttribs(obj_id npc, obj_id player, int phase) throws InterruptedException
    {
        if (testCommunityCraftingEnabled(npc, phase))
        {
            obj_id craftingTracker = utils.getObjIdScriptVar(npc, SCRIPTVAR_CRAFTING_TRACKER);
            if (!isIdValid(craftingTracker))
            {
                return;
            }
            Vector names = new Vector();
            Vector values = new Vector();
            if (!community_crafting.getProjectAttributes(craftingTracker, names, values))
            {
                return;
            }
            String[] attributes = new String[names.size()];
            for (int i = 0; i < attributes.length; ++i)
            {
                double f = (((Float)(values.get(i)))).doubleValue();
                values.set(i, floatFormat.format(f / 1000.0));
                attributes[i] = "@" + names.get(i) + " \\>200" + values.get(i);
            }
            int pid = sui.listbox(player, "@" + SID_ATTRIBUTES_PROMPT, "@" + SID_ATTRIBUTES_TITLE, sui.OK_ONLY, attributes);
        }
    }
    public static void showPlayerProjectStats(obj_id npc, obj_id player, int slot, boolean quality, int phase) throws InterruptedException
    {
        if (testCommunityCraftingEnabled(npc, phase))
        {
            obj_id craftingTracker = utils.getObjIdScriptVar(npc, SCRIPTVAR_CRAFTING_TRACKER);
            if (!isIdValid(craftingTracker))
            {
                return;
            }
            string_id prompt = SID_QUANTITY_PROMPT;
            Vector playerIds = new Vector();
            Vector playerNames = new Vector();
            Vector values = new Vector();
            if (!community_crafting.getPlayerRanking(craftingTracker, playerIds, playerNames, values, !quality, slot))
            {
                return;
            }
            if (quality)
            {
                prompt = SID_QUALITY_PROMPT;
                for (int i = 0; i < values.size(); ++i)
                {
                    double f = (((Float)(values.get(i)))).doubleValue();
                    values.set(i, floatFormat.format(f / 100.0));
                }
            }
            Vector rankings = new Vector();
            boolean skippingPlayers = false;
            boolean addedPlayer = false;
            int count = 0;
            for (int i = 0; i < playerIds.size() && count < sui.MAX_ARRAY_SIZE; ++i)
            {
                if (isIdValid((obj_id)(playerIds.get(i))))
                {
                    if (playerIds.get(i) == player)
                    {
                        rankings.add("\\#pcontrast1 " + getPlayerName((obj_id)(playerIds.get(i))) + "\\>200" + values.get(i) + "\\#.");
                        ++count;
                        addedPlayer = true;
                    }
                    else if ((addedPlayer && count < sui.MAX_ARRAY_SIZE) || (!addedPlayer && count < sui.MAX_ARRAY_SIZE - 1))
                    {
                        rankings.add(getPlayerName((obj_id)(playerIds.get(i))) + "\\>200" + values.get(i));
                        ++count;
                    }
                    else 
                    {
                        skippingPlayers = true;
                    }
                }
            }
            if (rankings.size() == 0)
            {
                rankings.add("@" + SID_NO_PLAYERS);
            }
            if (skippingPlayers)
            {
                if (prompt.equals(SID_QUANTITY_PROMPT))
                {
                    prompt = SID_QUANTITY_PROMPT_LIMITED;
                }
                else 
                {
                    prompt = SID_QUALITY_PROMPT_LIMITED;
                }
            }
            int pid = sui.listbox(player, "@" + prompt, "@" + SID_RANKINGS_TITLE, sui.OK_ONLY, rankings);
        }
    }
}
