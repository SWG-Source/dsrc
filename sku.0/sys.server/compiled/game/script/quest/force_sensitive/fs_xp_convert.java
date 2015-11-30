package script.quest.force_sensitive;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.xp;
import script.library.fs_quests;
import script.library.prose;
import script.library.pclib;

public class fs_xp_convert extends script.base_script
{
    public fs_xp_convert()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "quest.force_sensitive.fs_xp_convert");
        return SCRIPT_CONTINUE;
    }
    public int msgFSXPConvertSelected(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "force_sensitive.xp_convert") || !utils.hasScriptVar(self, "force_sensitive.xp_convert_type"))
        {
            detachScript(self, "quest.force_sensitive.fs_xp_convert");
            return SCRIPT_CONTINUE;
        }
        String[] valid_xp_types = utils.getStringArrayScriptVar(self, "force_sensitive.xp_convert");
        utils.removeScriptVar(self, "force_sensitive.xp_convert");
        utils.removeScriptVar(self, "force_sensitive.sui_pid");
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            detachScript(self, "quest.force_sensitive.fs_xp_convert");
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            detachScript(self, "quest.force_sensitive.fs_xp_convert");
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= valid_xp_types.length)
        {
            detachScript(self, "quest.force_sensitive.fs_xp_convert");
            return SCRIPT_CONTINUE;
        }
        int experience = getExperiencePoints(self, valid_xp_types[row_selected]);
        if (experience < 1)
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_not_enough_xp"));
            detachScript(self, "quest.force_sensitive.fs_xp_convert");
            return SCRIPT_CONTINUE;
        }
        String fs_type = utils.getStringScriptVar(self, "force_sensitive.xp_convert_type");
        int ratio = getConversionRatio(fs_type, valid_xp_types[row_selected]);
        if (ratio < 1)
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_illegal_type"));
            return SCRIPT_CONTINUE;
        }
        String xp_name = localize(new string_id("exp_n", valid_xp_types[row_selected]));
        String msg = "How much " + xp_name + " experience do you wish to convert?\n(1 for " + ratio + " conversion ratio)";
        int pid = sui.transfer(self, self, msg, "@quest/force_sensitive/utils:xp_transfer_prompt", "Experience", experience, "To Convert", 0, "msgFSXPConvertCommit");
        utils.setScriptVar(self, "force_sensitive.sui_pid", pid);
        utils.setScriptVar(self, "force_sensitive.xp_type_selected", valid_xp_types[row_selected]);
        return SCRIPT_CONTINUE;
    }
    public int msgFSXPConvertCommit(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "quest.force_sensitive.fs_xp_convert");
        if (!utils.hasScriptVar(self, "force_sensitive.xp_convert_type") || !utils.hasScriptVar(self, "force_sensitive.xp_type_selected"))
        {
            utils.removeScriptVar(self, "force_sensitive.xp_convert_type");
            utils.removeScriptVar(self, "force_sensitive.xp_type_selected");
            return SCRIPT_CONTINUE;
        }
        String fs_type = utils.getStringScriptVar(self, "force_sensitive.xp_convert_type");
        String xp_type = utils.getStringScriptVar(self, "force_sensitive.xp_type_selected");
        utils.removeScriptVar(self, "force_sensitive.xp_convert_type");
        utils.removeScriptVar(self, "force_sensitive.xp_type_selected");
        if (!utils.hasScriptVar(self, "force_sensitive.sui_pid"))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        int amt = sui.getTransferInputTo(params);
        if (amt < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int ratio = getConversionRatio(fs_type, xp_type);
        if (ratio < 1)
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_illegal_type"));
            return SCRIPT_CONTINUE;
        }
        int current_xp = getExperiencePoints(self, xp_type);
        if (current_xp < amt)
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_not_enough_xp"));
            return SCRIPT_CONTINUE;
        }
        int converted_xp = amt / ratio;
        int mod_amt = amt % ratio;
        if (mod_amt > 0)
        {
            amt -= mod_amt;
        }
        int fs_xp_cap = getExperienceCap(self, "fs_" + fs_type);
        int fs_current_xp = getExperiencePoints(self, "fs_" + fs_type);
        if (fs_xp_cap > 0)
        {
            if (fs_xp_cap <= fs_current_xp)
            {
                sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_at_fs_skill_cap"));
                return SCRIPT_CONTINUE;
            }
            if (fs_current_xp + converted_xp > fs_xp_cap)
            {
                converted_xp = fs_xp_cap - fs_current_xp;
                amt = converted_xp * ratio;
            }
        }
        else 
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_no_skill"));
            return SCRIPT_CONTINUE;
        }
        if (converted_xp < 1)
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "convert_allocate_more_xp"));
            return SCRIPT_CONTINUE;
        }
        grantExperiencePoints(self, xp_type, -amt);
        grantExperiencePoints(self, "fs_" + fs_type, converted_xp);
        string_id sid_xp_type = new string_id("exp_n", xp_type);
        string_id sid_converted_xp = new string_id("exp_n", "fs_" + fs_type);
        prose_package pp = prose.getPackage(new string_id("quest/force_sensitive/utils", "xp_convert_lose"), self, null, sid_xp_type, amt);
        sendSystemMessageProse(self, pp);
        prose_package pp2 = prose.getPackage(new string_id("quest/force_sensitive/utils", "xp_convert_gain"), self, null, sid_converted_xp, converted_xp);
        sendSystemMessageProse(self, pp2);
        CustomerServiceLog("fs_quests", "%TU has converted " + amt + " " + xp_type + " experience into " + converted_xp + " fs_" + fs_type + " experience.", self, null);
        return SCRIPT_CONTINUE;
    }
    public int msgBranchSelected(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "quest.force_sensitive.fs_xp_convert");
        if (utils.hasScriptVar(self, fs_quests.SCRIPT_VAR_BRANCH_SELECT_SUI))
        {
            utils.removeScriptVar(self, fs_quests.SCRIPT_VAR_BRANCH_SELECT_SUI);
        }
        String[] branches_available;
        if (utils.hasScriptVar(self, fs_quests.SCRIPT_VAR_BRANCH_SELECT_LIST))
        {
            branches_available = utils.getStringArrayScriptVar(self, fs_quests.SCRIPT_VAR_BRANCH_SELECT_LIST);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            detachScript(self, "quest.force_sensitive.fs_xp_convert");
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int free_branches = fs_quests.getFreeUnlockBranches(self);
        if (free_branches < 1)
        {
            sendSystemMessage(self, new string_id("quest/force_sensitive/utils", "no_free_branches"));
            return SCRIPT_CONTINUE;
        }
        if (row_selected >= branches_available.length)
        {
            return SCRIPT_CONTINUE;
        }
        String branch_selected = branches_available[row_selected];
        if (fs_quests.hasUnlockedBranch(self, branch_selected))
        {
            return SCRIPT_CONTINUE;
        }
        int branches_used = 0;
        if (hasObjVar(self, fs_quests.VAR_FREE_UNLOCK_USED))
        {
            branches_used = getIntObjVar(self, fs_quests.VAR_FREE_UNLOCK_USED);
        }
        branches_used++;
        setObjVar(self, fs_quests.VAR_FREE_UNLOCK_USED, branches_used);
        fs_quests.unlockBranch(self, branch_selected);
        int index = dataTableSearchColumnForString(branch_selected, "branch", fs_quests.DATATABLE_SKILL_BRANCH);
        String skill = dataTableGetString(fs_quests.DATATABLE_SKILL_BRANCH, index, "skill");
        if (skill != null)
        {
            dictionary req_xp = getSkillPrerequisiteExperience(skill);
            if (req_xp != null)
            {
                java.util.Enumeration e = req_xp.keys();
                if (e.hasMoreElements())
                {
                    String xp_type = (String)(e.nextElement());
                    if (xp_type.length() > 0)
                    {
                        xp.grant(self, xp_type, 1275000);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int getConversionRatio(String fs_type, String xp_type) throws InterruptedException
    {
        if (fs_type == null)
        {
            LOG("force_sensitive", "fs_xp_convert.getConversionRation -- fs_type is null");
            return -1;
        }
        if (xp_type == null)
        {
            LOG("force_sensitive", "fs_xp_convert.getConversionRation -- xp_type is null");
            return -1;
        }
        int index = dataTableSearchColumnForString(fs_type, "fs_type", "datatables/quest/force_sensitive/xp_conversion.iff");
        if (index < 0)
        {
            LOG("force_sensitive", "fs_xp_convert.getConversionRation -- can't find fs_type " + fs_type);
            return -1;
        }
        for (int i = index; i < dataTableGetNumRows("datatables/quest/force_sensitive/xp_conversion.iff"); i++)
        {
            dictionary row = dataTableGetRow("datatables/quest/force_sensitive/xp_conversion.iff", i);
            if ((row.getString("xp_type")).equals(xp_type))
            {
                return row.getInt("ratio");
            }
        }
        return -1;
    }
}
