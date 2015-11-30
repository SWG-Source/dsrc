package script.hnguyen;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.StringTokenizer;
import script.library.utils;
import script.library.player_structure;
import script.library.travel;

public class initialize_starport extends script.base_script
{
    public initialize_starport()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("initializeStarport "))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                obj_id starportOid = utils.stringToObjId(st.nextToken());
                if (!hasScript(starportOid, "structure.municipal.starport"))
                {
                    sendSystemMessageTestingOnly(self, "object " + starportOid + " doesn't have script structure.municipal.starport");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "inializing starport " + starportOid);
                    if (player_structure.isCivic(starportOid))
                    {
                        sendSystemMessageTestingOnly(self, "starport " + starportOid + " is civic");
                    }
                    else 
                    {
                        String planet = getCurrentSceneName();
                        String travel_point = travel.getTravelPointName(starportOid);
                        int travel_cost = travel.getTravelCost(starportOid);
                        if (travel_point == null || travel_cost == -1)
                        {
                            sendSystemMessageTestingOnly(self, "starport " + starportOid + " has null travel point or travel cost of -1");
                        }
                        else 
                        {
                            location loc = getPlanetTravelPointLocation(planet, travel_point);
                            if (loc != null)
                            {
                                sendSystemMessageTestingOnly(self, "starport " + starportOid + " is already registered with the travel system");
                            }
                            else 
                            {
                                travel.initializeStarport(starportOid, travel_point, travel_cost, false);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
