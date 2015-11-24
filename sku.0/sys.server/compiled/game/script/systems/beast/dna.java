package script.systems.beast;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.incubator;
import script.library.utils;

public class dna extends script.base_script
{
    public dna()
    {
    }
    public static final string_id SID_GOD_DNA_DATA = new string_id("incubator", "GODMODE_ADD_DATA");
    public static final string_id SID_GOD_FIX = new string_id("incubator", "GODMODE_FIX_DATA");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeValues", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self))
        {
            if (incubator.hasDnaQuality(self))
            {
                names[idx] = "quality";
                float attrib = incubator.getDnaQuality(self);
                attrib = utils.roundFloatByDecimal(attrib);
                attribs[idx] = "" + attrib + "%";
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            names[idx] = "bm_template";
            String template = incubator.getDnaParentCreature(self);
            if (template == null || template.equals(""))
            {
                names[idx] = "beast_type";
                int intTemplate = incubator.getDnaCreatureTemplate(self);
                template = incubator.getCreatureTypeFromHashTemplateDna(self, intTemplate);
                template = beast_lib.stripBmFromType(template);
            }
            String attrib = "";
            string_id templateId = new string_id("monster_name", template);
            if (localize(templateId) == null)
            {
                templateId = new string_id("mob/creature_names", template);
                if (localize(templateId) == null && hasObjVar(self, incubator.DNA_PARENT_TEMPLATE_NAME))
                {
                    templateId = utils.unpackString(getStringObjVar(self, incubator.DNA_PARENT_TEMPLATE_NAME));
                    if (localize(templateId) == null)
                    {
                        attrib = getStringObjVar(self, incubator.DNA_PARENT_TEMPLATE_NAME);
                    }
                }
            }
            if (localize(templateId) != null)
            {
                attrib = localize(templateId);
            }
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
        obj_id player = utils.getContainingPlayer(self);
        incubator.initializeDna(self, player);
        return SCRIPT_CONTINUE;
    }
}
