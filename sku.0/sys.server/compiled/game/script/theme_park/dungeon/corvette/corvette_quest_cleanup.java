package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class corvette_quest_cleanup extends script.base_script
{
    public corvette_quest_cleanup()
    {
    }
    public static final String CORL_CORVETTE_OBJVAR = "corl_corvette";
    public static final String TICKET_OWNER_OBJVAR = CORL_CORVETTE_OBJVAR + ".ticket_owner";
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, TICKET_OWNER_OBJVAR))
        {
            obj_id ticketOwner = getObjIdObjVar(self, TICKET_OWNER_OBJVAR);
            if (isIdValid(ticketOwner))
            {
                location ownerLoc = getLocation(ticketOwner);
                if (ownerLoc != null)
                {
                    String planet = ownerLoc.area;
                    if (planet != null && !planet.equals("") && !planet.equals("dungeon1"))
                    {
                        if (hasObjVar(ticketOwner, CORL_CORVETTE_OBJVAR))
                        {
                            String corvetteType = getStringObjVar(self, "space_dungeon.ticket.dungeon");
                            String custLogMsg = "*Corvette Ground Quest: Player %TU destroyed corvette ticket for " + corvetteType + ".";
                            CustomerServiceLog("DUNGEON_CorellianCorvette", custLogMsg, ticketOwner);
                            removeObjVar(ticketOwner, CORL_CORVETTE_OBJVAR);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
