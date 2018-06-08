package script.systems.beast;

import script.dictionary;
import script.library.incubator;
import script.obj_id;

public class cs_dna extends script.base_script
{
    public cs_dna()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeValues", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeValues", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeValues(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, incubator.DNA_TEMPLATE_OBJVAR))
        {
            String template = getStringObjVar(self, incubator.DNA_TEMPLATE_OBJVAR);
            if (template != null && !template.equals(""))
            {
                removeObjVar(self, incubator.DNA_TEMPLATE_OBJVAR);
                int crcTemplate = incubator.convertStringTemplateToCrC(template);
                if (crcTemplate != 0)
                {
                    setObjVar(self, incubator.DNA_TEMPLATE_OBJVAR, crcTemplate);
                    attachScript(self, "systems.beast.dna");
                    detachScript(self, "systems.beast.cs_dna");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
