package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.incubator;
import script.library.utils;

public class enzyme extends script.base_script
{
    public enzyme()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, incubator.ENZYME_COLOR_OBJVAR))
        {
            messageTo(self, "rename_enzyme", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void renameEnzyme(obj_id which) throws InterruptedException
    {
        String name = "";
        if (hasObjVar(which, incubator.ENZYME_COLOR_OBJVAR))
        {
            int colorIndex = getIntObjVar(which, incubator.ENZYME_COLOR_OBJVAR);
            if (colorIndex >= incubator.ENZYME_COLORS.length)
            {
                return;
            }
            name = incubator.ENZYME_COLORS[colorIndex];
            if (!name.equals("") && name.length() > 0)
            {
                if (getGameObjectType(which) == GOT_misc_enzyme_isomerase)
                {
                    setName(which, name + " Isomerase Enzyme");
                }
                else if (getGameObjectType(which) == GOT_misc_enzyme_lyase)
                {
                    setName(which, name + " Lyase Enzyme");
                }
            }
        }
    }
    public int rename_enzyme(obj_id self, dictionary params) throws InterruptedException
    {
        renameEnzyme(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeValues", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self) && incubator.isStatEnzyme(self))
        {
            names[idx] = "bm_random_stats";
            int attrib = incubator.getEnzymeRandomStats(self);
            if (attrib != 0)
            {
                attribs[idx] = "" + attrib;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (exists(self) && incubator.isStatEnzyme(self))
        {
            names[idx] = "bm_stat_name";
            String attrib = incubator.getEnzymeFreeStat(self);
            if (!attrib.equals("") && attrib != null)
            {
                attribs[idx] = "" + attrib;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (exists(self) && incubator.isSkillEnzyme(self))
        {
            names[idx] = "bm_skill_points";
            float attrib = incubator.getEnzymeSkillPoints(self);
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && incubator.isQualityEnzyme(self))
        {
            names[idx] = "quality";
            float attrib = incubator.getEnzymeQuality(self);
            attrib = utils.roundFloatByDecimal(attrib);
            attribs[idx] = "" + attrib + "%";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && hasObjVar(self, incubator.ENZYME_COLOR_OBJVAR))
        {
            names[idx] = "enzyme_color";
            int colorIndex = getIntObjVar(self, incubator.ENZYME_COLOR_OBJVAR);
            String attrib = incubator.ENZYME_COLORS[colorIndex];
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeValues(obj_id self, dictionary params) throws InterruptedException
    {
        renameEnzyme(self);
        if (hasObjVar(self, incubator.ENZYME_QUALITY_OBJVAR) || hasObjVar(self, incubator.ENZYME_RANDOM_STATS_OBJVAR) || hasObjVar(self, incubator.ENZYME_FREE_STAT_OBJVAR))
        {
            if (hasObjVar(self, "collection_enzyme"))
            {
                removeObjVar(self, "collection_enzyme");
            }
            return SCRIPT_CONTINUE;
        }
        incubator.initializeEnzymes(self);
        incubator.awardEnzymeCollection(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        incubator.awardEnzymeCollection(self);
        return SCRIPT_CONTINUE;
    }
}
