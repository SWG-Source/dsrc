package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class deed extends script.faction_perk.base.factional_deed
{
    public deed()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String templateName = getTemplateName(self);
        String faction = "imperial";
        if (templateName.indexOf("_rebel") != -1)
        {
            faction = "rebel";
        }
        string_id currentStringId = getNameStringId(self);
        String stringIdAsciiPart = currentStringId.getAsciiId();
        stringIdAsciiPart = stringIdAsciiPart + "_" + faction;
        string_id newFactionalizedName = new string_id("deed", stringIdAsciiPart);
        setName(self, "");
        setName(self, newFactionalizedName);
        return SCRIPT_CONTINUE;
    }
}
