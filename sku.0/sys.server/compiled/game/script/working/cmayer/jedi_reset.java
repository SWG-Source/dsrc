package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.pclib;
import script.library.sui;
import java.util.StringTokenizer;

public class jedi_reset extends script.base_script
{
    public jedi_reset()
    {
    }
    public static final String SCRIPT_NAME = "cmayer.jedi_reset";
    public static final String COMMAND = "reset_jedi";
    public static final String JEDI_TABLE = "datatables/jedi/jedi_skill_requirements.iff";
    public void resetJedi(obj_id self, obj_id target, int numSkills) throws InterruptedException
    {
        boolean result = true;
        if (result)
        {
            removeOldJediData(target);
            setNewSkillReq(target, numSkills);
            sendSystemMessageTestingOnly(self, "Jedi requirements have successfully been reset");
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Jedi requirements were not reset");
        }
        return;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || (getGodLevel(self) < 50))
        {
            int numSkills = 8;
            if (hasObjVar(self, "numJediSkillsToReset"))
            {
                numSkills = getIntObjVar(self, "numJediSkillsToReset");
            }
            resetJedi(self, self, numSkills);
            detachScript(self, SCRIPT_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || (getGodLevel(self) < 50))
        {
            int numSkills = 8;
            if (hasObjVar(self, "numJediSkillsToReset"))
            {
                numSkills = getIntObjVar(self, "numJediSkillsToReset");
            }
            resetJedi(self, self, numSkills);
            detachScript(self, SCRIPT_NAME);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if ((toLower(text)).startsWith(COMMAND))
        {
            StringTokenizer st = new StringTokenizer(text);
            if (st.countTokens() != 2)
            {
                sendSystemMessageTestingOnly(self, "Error: Syntax is \"" + COMMAND + " <number of skills>\"");
                return SCRIPT_CONTINUE;
            }
            String command = st.nextToken();
            int numSkills = utils.stringToInt(st.nextToken());
            if ((numSkills < 1) || (numSkills > 8))
            {
                sendSystemMessageTestingOnly(self, "Error: Number of skills must be 1-8");
                return SCRIPT_CONTINUE;
            }
            obj_id target = getLookAtTarget(self);
            if (target == null || !isIdValid(target) || !isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "Error: You must target a valid player");
                return SCRIPT_CONTINUE;
            }
            if (!hasObjVar(target, "jedi.enabled"))
            {
                String error = "WARNING: It appears that " + getName(target) + " ";
                error += "has not yet met the requirements to activate a force sensitive ";
                error += "slot.\n\nDo you still wish to continue resetting this player's ";
                error += "Jedi requirements?";
                showWarningSui(self, target, numSkills, error);
                return SCRIPT_CONTINUE;
            }
            resetJedi(self, target, numSkills);
        }
        return SCRIPT_CONTINUE;
    }
    public void showWarningSui(obj_id self, obj_id target, int numSkills, String msg) throws InterruptedException
    {
        int pid = sui.msgbox(self, self, msg, sui.YES_NO, "RESET JEDI", "handleWarningSui");
        if (pid > -1)
        {
            utils.setScriptVar(self, "resetJedi_target", target);
            utils.setScriptVar(self, "resetJedi_numSkills", numSkills);
        }
        return;
    }
    public int handleWarningSui(obj_id self, dictionary params) throws InterruptedException
    {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = utils.getObjIdScriptVar(self, "resetJedi_target");
        int numSkills = utils.getIntScriptVar(self, "resetJedi_numSkills");
        if (target == null || !isIdValid(target))
        {
            sendSystemMessageTestingOnly(self, "Error: Jedi requirements did not reset");
            return SCRIPT_CONTINUE;
        }
        if (numSkills < 1 || numSkills > 8)
        {
            sendSystemMessageTestingOnly(self, "Error: Jedi requirements did not reset");
            return SCRIPT_CONTINUE;
        }
        resetJedi(self, target, numSkills);
        return SCRIPT_CONTINUE;
    }
    public void removeOldJediData(obj_id target) throws InterruptedException
    {
        if (hasObjVar(target, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS))
        {
            removeObjVar(target, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
        }
        if (hasObjVar(target, "jedi.timeStamp"))
        {
            removeObjVar(target, "jedi.timeStamp");
        }
        if (hasObjVar(target, "jedi.enabled"))
        {
            removeObjVar(target, "jedi.enabled");
        }
        if (hasObjVar(target, "jediAddedAcknowlege"))
        {
            removeObjVar(target, "jediAddedAcknowlege");
        }
        return;
    }
    public void setNewSkillReq(obj_id target, int numSkills) throws InterruptedException
    {
        int rows = dataTableGetNumRows(JEDI_TABLE);
        if (numSkills < 1)
        {
            numSkills = 1;
        }
        if (numSkills > 8)
        {
            numSkills = 8;
        }
        String[] skills = new String[numSkills];
        int n = 0;
        while (n < numSkills)
        {
            String skill = dataTableGetString(JEDI_TABLE, rand(0, (rows - 1)), 0);
            if (skill != null && !skill.equals(""))
            {
                boolean skillNotUsed = true;
                for (int i = 0; i < n; i++)
                {
                    if (skills[i] != null && !skills[i].equals(""))
                    {
                        if (skills[i].equals(skill))
                        {
                            skillNotUsed = false;
                        }
                    }
                }
                if (skillNotUsed)
                {
                    skills[n] = skill;
                    n++;
                }
            }
        }
        setObjVar(target, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS, skills);
        return;
    }
}
