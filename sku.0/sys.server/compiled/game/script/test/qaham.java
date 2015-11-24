package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.StringTokenizer;

public class qaham extends script.base_script
{
    public qaham()
    {
    }
    public static final String QA_REGEN_OBJVAR = "test.qaham.OriginalActionRegen";
    public static final float QA_MASSIVE_REGEN_RATE = 10000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qaham");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
            else 
            {
                helpMessage(self);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qaham");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        restoreActionRegenRate(self);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        if (isGod(player))
        {
            StringTokenizer st = new java.util.StringTokenizer(text);
            int tokens = st.countTokens();
            String command = null;
            if (st.hasMoreTokens())
            {
                command = st.nextToken();
            }
            if (command.equals("stop_action_regen"))
            {
                stopActionRegenRate(player);
            }
            if (command.equals("restore_action_regen"))
            {
                restoreActionRegenRate(player);
            }
            if (command.equals("max_action_regen"))
            {
                setActionRegenRate(player, QA_MASSIVE_REGEN_RATE);
            }
            if (command.equals("qaham") || command.equals("test.qaham"))
            {
                helpMessage(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void restoreActionRegenRate(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (!hasObjVar(player, QA_REGEN_OBJVAR))
        {
            return;
        }
        float myStoredRegen = getFloatObjVar(player, QA_REGEN_OBJVAR);
        getActionRegenRate(player, myStoredRegen);
        removeObjVar(player, QA_REGEN_OBJVAR);
        if (!hasObjVar(player, QA_REGEN_OBJVAR))
        {
            sendSystemMessageTestingOnly(player, "Your Action Regen Rate has been restored");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Problem - Regen Rate was not restored!");
            sendSystemMessageTestingOnly(player, "Contact the QA Tool Team about this character immediately.");
        }
    }
    public void stopActionRegenRate(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        setActionRegenRate(player, 0f);
    }
    public void setActionRegenRate(obj_id player, float rate) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        float currentRegenRate = getActionRegenRate(player);
        if (!hasObjVar(player, QA_REGEN_OBJVAR))
        {
            setObjVar(player, QA_REGEN_OBJVAR, currentRegenRate);
        }
        getActionRegenRate(player, rate);
        if (hasObjVar(player, QA_REGEN_OBJVAR))
        {
            sendSystemMessageTestingOnly(player, "Your Action Regen Rate has been set to " + rate);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Problem - Regen Rate was not set correctly!");
            sendSystemMessageTestingOnly(player, "Contact the QA Tool Team about this character immediately.");
        }
    }
    public void helpMessage(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        sendSystemMessageTestingOnly(player, "Qaham Script Help Message");
        sendSystemMessageTestingOnly(player, "Say any the following commands in chat:");
        sendSystemMessageTestingOnly(player, "stop_action_regen");
        sendSystemMessageTestingOnly(player, "restore_action_regen");
        sendSystemMessageTestingOnly(player, "max_action_regen");
    }
}
