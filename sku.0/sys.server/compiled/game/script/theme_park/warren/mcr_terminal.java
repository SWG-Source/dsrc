package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.colors;

public class mcr_terminal extends script.base_script
{
    public mcr_terminal()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public static final String DEACTIVATE = "mcr_term_deactivated";
    public static final String ACTIVATE = "mcr_term_active";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "mcr_term_name_on"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        boolean isInactive = utils.getBooleanScriptVar(self, "warren.isInactive");
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 10.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        if (isInactive)
        {
            showFlyText(self, new string_id(SYSTEM_MESSAGES, DEACTIVATE), 1.75f, colors.GREENYELLOW);
            return SCRIPT_CONTINUE;
        }
        if (!hasPasskey(player, self))
        {
            showFlyText(self, new string_id(SYSTEM_MESSAGES, ACTIVATE), 1.75f, colors.GREENYELLOW);
            return SCRIPT_CONTINUE;
        }
        showFlyText(self, new string_id(SYSTEM_MESSAGES, DEACTIVATE), 1.75f, colors.GREENYELLOW);
        deactivateTerminal(self);
        return SCRIPT_CONTINUE;
    }
    public boolean hasPasskey(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id[] contents = getContents(pInv);
        if (contents == null)
        {
            return false;
        }
        int keyNum = getIntObjVar(self, "warren.passkeyNumber");
        if (keyNum == 0)
        {
            debugSpeakMsg(self, "I AM SUPPOSED TO HAVE A warren.passkeyNumber OBJVAR BUT I DON'T");
            return false;
        }
        for (int i = 0; i < contents.length; i++)
        {
            int passKeyNumber = getIntObjVar(contents[i], "warren.passkeyNumber");
            if (passKeyNumber == keyNum)
            {
                destroyObject(contents[i]);
                return true;
            }
        }
        return false;
    }
    public void deactivateTerminal(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, "warren.isInactive", true);
        obj_id bldg = getTopMostContainer(self);
        int keyNum = getIntObjVar(self, "warren.passkeyNumber");
        utils.setScriptVar(bldg, "warren.passkeyNumberDeactivated" + keyNum, true);
        messageTo(self, "handleReactiveTerminal", null, 1800, false);
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "mcr_term_name_off"));
        return;
    }
    public int handleReactiveTerminal(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "warren.isInactive");
        obj_id bldg = getTopMostContainer(self);
        int keyNum = getIntObjVar(self, "warren.passkeyNumber");
        utils.removeScriptVar(bldg, "warren.passkeyNumberDeactivated" + keyNum);
        setName(self, "");
        setName(self, new string_id(SYSTEM_MESSAGES, "mcr_term_name_on"));
        return SCRIPT_CONTINUE;
    }
}
