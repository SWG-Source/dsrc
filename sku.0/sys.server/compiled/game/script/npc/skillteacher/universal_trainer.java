package script.npc.skillteacher;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.skill;

public class universal_trainer extends script.base_script
{
    public universal_trainer()
    {
    }
    public static final String skill_table = "datatables/npc_customization/skill_table.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id pants = createObject("object/tangible/wearables/pants/pants_s01.iff", self, "");
        obj_id boots = createObject("object/tangible/wearables/boots/boots_s03.iff", self, "");
        obj_id shirt = createObject("object/tangible/wearables/shirt/shirt_s15.iff", self, "");
        String tbl_trainer_skills = "datatables/npc_customization/skill_table.iff";
        Vector allSkills = new Vector();
        allSkills.setSize(0);
        int numCols = dataTableGetNumColumns(tbl_trainer_skills);
        if (numCols < 1)
        {
            return SCRIPT_OVERRIDE;
        }
        for (int i = 0; i < numCols; i++)
        {
            String[] colData = dataTableGetStringColumnNoDefaults(tbl_trainer_skills, i);
            if (colData != null && colData.length > 0)
            {
                allSkills = utils.concatArrays(allSkills, colData);
            }
        }
        if (allSkills != null && allSkills.size() > 0)
        {
            utils.setBatchScriptVar(self, skill.SCRIPTVAR_SKILLS, allSkills);
        }
        attachScript(self, "npc.skillteacher.skillteacher");
        return SCRIPT_CONTINUE;
    }
}
