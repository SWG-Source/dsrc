package script.systems.crafting.item;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.stealth;
import script.obj_id;

public class crafting_base_detect_item extends script.systems.crafting.crafting_base
{
    public crafting_base_detect_item()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        float effectiveness = 0;
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("effectiveness")) {
                    effectiveness = (int) itemAttribute.currentValue;
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
        if (effectiveness > 0)
        {
            String template = getTemplateName(prototype);
            if (template.contains("biosensorprobe"))
            {
                setObjVar(prototype, stealth.BIO_PROBE_STORAGE_TIME, effectiveness / 1000 * stealth.MAX_BIO_PROBE_STORAGE_TIME);
            }
            else if (template.contains("trackingbeacon") || template.contains("motionsensor"))
            {
                setObjVar(prototype, stealth.BEACON_BATTERY, effectiveness);
            }
            else 
            {
                setObjVar(prototype, stealth.DETECT_EFFECTIVENESS, effectiveness);
            }
        }
    }
}
