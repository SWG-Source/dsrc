package script.npc.faction_recruiter;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.sui;
import script.library.utils;
import script.library.faction_perk;
import script.library.xp;
import script.library.prose;

public class player_recruiter extends script.base_script
{
    public player_recruiter()
    {
    }
    public static final string_id SID_INCREASE_FACTION_TO_CONVERT = new string_id("faction_recruiter", "increase_faction_to_convert");
    public static final string_id SID_SUI_CONFIRM_CONVERSION_TITLE = new string_id("faction_recruiter", "sui_confirm_conversion_title");
    public static final string_id SID_SUI_CONFIRM_CONVERSION_PROMPT = new string_id("faction_recruiter", "sui_confirm_conversion_prompt");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
        if (hasObjVar(self, "faction_recruiter"))
        {
            removeObjVar(self, "faction_recruiter");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgResignFromFaction(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
        if (hasObjVar(self, "faction_recruiter"))
        {
            removeObjVar(self, "faction_recruiter");
        }
        int resign_faction_id = params.getInt("faction_id");
        if (resign_faction_id == 0)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgResignFromFaction -- faction id is 0 for " + self);
            return SCRIPT_CONTINUE;
        }
        String resign_faction = factions.getFactionNameByHashCode(resign_faction_id);
        if (resign_faction == null)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgResignFromFaction -- Could not find " + resign_faction_id + " in the faction datatable for " + self);
            return SCRIPT_CONTINUE;
        }
        if (hasSkill(self, "force_rank_light_novice") || hasSkill(self, "force_rank_dark_novice"))
        {
            sendSystemMessage(self, new string_id("faction_recruiter", "jedi_cant_resign"));
            return SCRIPT_CONTINUE;
        }
        int player_faction_id = pvpGetAlignedFaction(self);
        if (player_faction_id != resign_faction_id)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgResignFromFaction -- " + self + "'s faction has changed since resignation.");
            return SCRIPT_CONTINUE;
        }
        factions.resignFromFaction(self, resign_faction);
        return SCRIPT_CONTINUE;
    }
    public int msgGoCovert(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String bldgName = getTemplateName(top);
        if (bldgName.equals("object/building/general/space_dungeon_corellian_corvette.iff") || bldgName.equals("object/building/general/space_dungeon_corellian_corvette_imperial.iff") || bldgName.equals("object/building/general/space_dungeon_corellian_corvette_rebel.iff"))
        {
            setObjVar(self, "corl_corvette.made_overt", 1);
            return SCRIPT_CONTINUE;
        }
        detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
        if (hasObjVar(self, "faction_recruiter"))
        {
            removeObjVar(self, "faction_recruiter");
        }
        int resign_faction_id = params.getInt("faction_id");
        if (resign_faction_id == 0)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgResignFromFaction -- faction id is 0 for " + self);
            return SCRIPT_CONTINUE;
        }
        String resign_faction = factions.getFactionNameByHashCode(resign_faction_id);
        if (resign_faction == null)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgResignFromFaction -- Could not find " + resign_faction_id + " in the faction datatable for " + self);
            return SCRIPT_CONTINUE;
        }
        int player_faction_id = pvpGetAlignedFaction(self);
        if (player_faction_id != resign_faction_id)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgResignFromFaction -- " + self + "'s faction has changed since resignation.");
            return SCRIPT_CONTINUE;
        }
        if (hasScript(self, "force_rank_light_novice") || hasScript(self, "force_rank_dark_novice"))
        {
            sendSystemMessage(self, new string_id("faction_recruiter", "jedi_cant_go_covert"));
            return SCRIPT_CONTINUE;
        }
        pvpMakeCovert(self);
        factions.unequipFactionEquipment(self, true);
        factions.releaseFactionHirelings(self);
        sendSystemMessage(self, factions.SID_COVERT_COMPLETE);
        return SCRIPT_CONTINUE;
    }
    public int msgFactionTrainingTypeSelected(obj_id self, dictionary params) throws InterruptedException
    {
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected < 0)
        {
            detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, factions.VAR_TRAINING_SELECTION, row_selected);
        sui.inputbox(self, self, "@faction_recruiter:enter_training_points", "msgFactionTrainingAmountSelected");
        return SCRIPT_CONTINUE;
    }
    public int msgFactionTrainingAmountSelected(obj_id self, dictionary params) throws InterruptedException
    {
        String amount_str = sui.getInputBoxText(params);
        int amt = utils.stringToInt(amount_str);
        if (amt < 1)
        {
            sendSystemMessage(self, factions.SID_INVALID_AMOUNT_ENTERED);
            removeObjVar(self, "faction_recruiter");
            return SCRIPT_CONTINUE;
        }
        int base_amt = amt;
        int row_selected;
        if (hasObjVar(self, factions.VAR_TRAINING_SELECTION))
        {
            row_selected = getIntObjVar(self, factions.VAR_TRAINING_SELECTION);
        }
        else 
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- could not find selection data on " + self);
            return SCRIPT_CONTINUE;
        }
        int num_items = dataTableGetNumRows(factions.DATATABLE_ALLOWED_XP_TYPES);
        if (row_selected >= num_items)
        {
            detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
            return SCRIPT_CONTINUE;
        }
        dictionary row = dataTableGetRow(factions.DATATABLE_ALLOWED_XP_TYPES, row_selected);
        String xp_type = row.getString("xp_type");
        float ratio = row.getFloat("ratio");
        String name = row.getString("name");
        int faction_id = pvpGetAlignedFaction(self);
        if (faction_id == 0)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- faction_id is 0 for " + self);
            return SCRIPT_CONTINUE;
        }
        String faction = factions.getFactionNameByHashCode(faction_id);
        if (faction == null)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- unable to find " + faction_id + " in the faction datatable for " + self);
            return SCRIPT_CONTINUE;
        }
        amt *= ratio;
        float mod = faction_perk.getFactionPrejudice(getSpecies(self), faction);
        float totalRatio = mod * ratio;
        float modulus = amt % mod;
        float toSpend = amt - modulus;
        int baseXp = (int)(toSpend / mod);
        String sName = getString(utils.unpackString(name));
        int convertedXp = xp.applySpeciesXpModifier(self, sName, baseXp);
        if (convertedXp > 0)
        {
            utils.setScriptVar(self, factions.VAR_TRAINING_COST, toSpend);
            utils.setScriptVar(self, factions.VAR_TRAINING_XP, convertedXp);
            prose_package ppSuiConvertPrompt = prose.getPackage(SID_SUI_CONFIRM_CONVERSION_PROMPT);
            prose.setDF(ppSuiConvertPrompt, toSpend);
            prose.setDI(ppSuiConvertPrompt, convertedXp);
            prose.setTO(ppSuiConvertPrompt, sName);
            String prompt = " \0" + packOutOfBandProsePackage(null, ppSuiConvertPrompt);
            String title = utils.packStringId(SID_SUI_CONFIRM_CONVERSION_TITLE);
            sui.msgbox(self, self, prompt, sui.YES_NO, title, "msgFactionTrainingAmountConfirm");
        }
        else 
        {
            prose_package ppIncreaseFaction = prose.getPackage(SID_INCREASE_FACTION_TO_CONVERT);
            prose.setDF(ppIncreaseFaction, totalRatio);
            prose.setDI(ppIncreaseFaction, base_amt);
            sendSystemMessageProse(self, ppIncreaseFaction);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFactionTrainingAmountConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
            removeObjVar(self, "faction_recruiter");
            return SCRIPT_CONTINUE;
        }
        int row_selected;
        if (hasObjVar(self, factions.VAR_TRAINING_SELECTION))
        {
            row_selected = getIntObjVar(self, factions.VAR_TRAINING_SELECTION);
            removeObjVar(self, "faction_recruiter");
        }
        else 
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- could not find selection data on " + self);
            return SCRIPT_CONTINUE;
        }
        int num_items = dataTableGetNumRows(factions.DATATABLE_ALLOWED_XP_TYPES);
        if (row_selected >= num_items)
        {
            detachScript(self, factions.SCRIPT_PLAYER_RECRUITER);
            return SCRIPT_CONTINUE;
        }
        dictionary row = dataTableGetRow(factions.DATATABLE_ALLOWED_XP_TYPES, row_selected);
        String xp_type = row.getString("xp_type");
        float ratio = row.getFloat("ratio");
        String name = row.getString("name");
        int toSpend = (int)(utils.getFloatScriptVar(self, factions.VAR_TRAINING_COST));
        int convertedXp = utils.getIntScriptVar(self, factions.VAR_TRAINING_XP);
        int faction_id = pvpGetAlignedFaction(self);
        if (faction_id == 0)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- faction_id is 0 for " + self);
            return SCRIPT_CONTINUE;
        }
        String faction = factions.getFactionNameByHashCode(faction_id);
        if (faction == null)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- unable to find " + faction_id + " in the faction datatable for " + self);
            return SCRIPT_CONTINUE;
        }
        int standing = (int)factions.getFactionStanding(self, faction);
        if (standing - toSpend < factions.FACTION_RATING_DECLARABLE_MIN)
        {
            prose_package pp = prose.getPackage(factions.SID_NOT_ENOUGH_STANDING_SPEND, faction, (int)factions.FACTION_RATING_DECLARABLE_MIN);
            sendSystemMessageProse(self, pp);
            setObjVar(self, factions.VAR_TRAINING_SELECTION, row_selected);
            sui.inputbox(self, self, "@faction_recruiter:enter_training_points", "msgFactionTrainingAmountSelected");
            return SCRIPT_CONTINUE;
        }
        if (factions.addFactionStanding(self, faction, -toSpend))
        {
            dictionary resultData = new dictionary();
            resultData.put("convertedXp", convertedXp);
            resultData.put("xp_type", xp_type);
            if (xp.grant(self, xp_type, convertedXp, "msgFactionTrainingAmountConfirmXpResult", resultData) == 0)
            {
                LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- unable to grant " + convertedXp + " " + xp_type + " experience to " + self);
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- unable to decrement faction standing for " + self);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgFactionTrainingAmountConfirmXpResult(obj_id self, dictionary params) throws InterruptedException
    {
        int granted = params.getInt(xp.GRANT_XP_RESULT_VALUE);
        dictionary data = params.getDictionary(xp.GRANT_XP_RETURN_DATA);
        int convertedXp = data.getInt("convertedXp");
        String xp_type = data.getString("xp_type");
        if (granted == 0)
        {
            LOG("LOG_CHANNEL", "player_recruiter::msgFactionTrainingAmountSelected -- unable to grant " + convertedXp + " " + xp_type + " experience to " + self);
        }
        return SCRIPT_CONTINUE;
    }
}
