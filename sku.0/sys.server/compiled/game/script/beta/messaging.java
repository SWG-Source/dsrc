package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class messaging extends script.base_script
{
    public messaging()
    {
    }
    public int setLoginTitle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        location locTest = getLocation(self);
        obj_id objPlanetObject = getPlanetByName(locTest.area);
        setObjVar(objPlanetObject, "strLoginMessageTitle", params);
        sendSystemMessageTestingOnly(self, "Login Title set");
        return SCRIPT_CONTINUE;
    }
    public int setLoginMessage(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        location locTest = getLocation(self);
        obj_id objPlanetObject = getPlanetByName(locTest.area);
        setObjVar(objPlanetObject, "strLoginMessage", params);
        sendSystemMessageTestingOnly(self, "Login message set");
        return SCRIPT_CONTINUE;
    }
    public int resendLoginMessageToAll(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        location locTest = getLocation(self);
        obj_id objPlanetObject = getPlanetByName(locTest.area);
        String strLoginMessageTitle = "";
        String strLoginMessage = "";
        if (hasObjVar(objPlanetObject, "strLoginMessageTitle"))
        {
            strLoginMessageTitle = getStringObjVar(objPlanetObject, "strLoginMessageTitle");
        }
        if (hasObjVar(objPlanetObject, "strLoginMessage"))
        {
            strLoginMessage = getStringObjVar(objPlanetObject, "strLoginMessage");
        }
        deltadictionary dctPlayerInformation = objPlanetObject.getScriptVars();
        obj_id[] objPlayers = dctPlayerInformation.getObjIdArray("objPlayers");
        int intI = 0;
        if (objPlayers == null)
        {
            debugServerConsoleMsg(self, "Null array for objPlayers");
            return SCRIPT_CONTINUE;
        }
        if ((!strLoginMessage.equals("")) && (!strLoginMessageTitle.equals("")))
        {
            while (intI < objPlayers.length)
            {
                debugServerConsoleMsg(self, "objPlayers[" + intI + "] is " + objPlayers[intI]);
                intI = intI + 1;
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "No title or message currently set, please set before sending ");
        }
        return SCRIPT_CONTINUE;
    }
}
