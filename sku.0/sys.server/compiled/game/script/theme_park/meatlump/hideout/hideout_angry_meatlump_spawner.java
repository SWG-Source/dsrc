package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.structure;
import script.library.trial;
import script.library.utils;

public class hideout_angry_meatlump_spawner extends script.base_script
{
    public hideout_angry_meatlump_spawner()
    {
    }
    public static final String ELIGIBLE_OBJVAR = "angryMeatlumpsEligible";
    public static final String ANGRY_MEATLUMPS_LIST_SCRIPTVAR = "angryMeatlumpsList";
    public static final String HIDEOUT_ID_OBJVAR = "angryMeatlump.hideout";
    public static final int MAX_NUM_ANGRY_MEATLUMPS = 19;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeAngryMeatlumps", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeAngryMeatlumps(obj_id self, dictionary params) throws InterruptedException
    {
        Vector angryMeatlumps = new Vector();
        angryMeatlumps.setSize(0);
        Vector eligibleMeatlumps = findEligibleMeatlumps(self);
        if (eligibleMeatlumps != null && eligibleMeatlumps.size() > 0)
        {
            while (angryMeatlumps.size() < MAX_NUM_ANGRY_MEATLUMPS && eligibleMeatlumps.size() > 0)
            {
                int index = rand(0, eligibleMeatlumps.size() - 1);
                obj_id chosenMeatlump = ((obj_id)eligibleMeatlumps.get(index));
                if (isIdValid(chosenMeatlump))
                {
                    buff.applyBuff(chosenMeatlump, "mtp_meatlump_angry");
                    setObjVar(chosenMeatlump, HIDEOUT_ID_OBJVAR, self);
                    utils.addElement(angryMeatlumps, chosenMeatlump);
                    utils.removeElement(eligibleMeatlumps, chosenMeatlump);
                }
            }
        }
        if (angryMeatlumps != null && angryMeatlumps.size() > 0)
        {
            utils.setScriptVar(self, ANGRY_MEATLUMPS_LIST_SCRIPTVAR, angryMeatlumps);
        }
        if (angryMeatlumps.size() < MAX_NUM_ANGRY_MEATLUMPS)
        {
            messageTo(self, "makeNewAngryMeatlump", null, 19, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int makeNewAngryMeatlump(obj_id self, dictionary params) throws InterruptedException
    {
        Vector angryMeatlumps = new Vector();
        angryMeatlumps.setSize(0);
        if (utils.hasScriptVar(self, ANGRY_MEATLUMPS_LIST_SCRIPTVAR))
        {
            angryMeatlumps = utils.getResizeableObjIdArrayScriptVar(self, ANGRY_MEATLUMPS_LIST_SCRIPTVAR);
        }
        if (params != null || !params.isEmpty())
        {
            if (params.containsKey("happyMeatlump"))
            {
                obj_id happyMeatlump = params.getObjId("happyMeatlump");
                if (isIdValid(happyMeatlump) && utils.isElementInArray(angryMeatlumps, happyMeatlump))
                {
                    utils.removeElement(angryMeatlumps, happyMeatlump);
                }
            }
        }
        if (angryMeatlumps.size() >= MAX_NUM_ANGRY_MEATLUMPS)
        {
            return SCRIPT_CONTINUE;
        }
        Vector eligibleMeatlumps = findEligibleMeatlumps(self);
        if (eligibleMeatlumps != null && eligibleMeatlumps.size() > 0)
        {
            int index = rand(0, eligibleMeatlumps.size() - 1);
            obj_id chosenMeatlump = ((obj_id)eligibleMeatlumps.get(index));
            if (isIdValid(chosenMeatlump))
            {
                buff.applyBuff(chosenMeatlump, "mtp_meatlump_angry");
                setObjVar(chosenMeatlump, HIDEOUT_ID_OBJVAR, self);
                utils.addElement(angryMeatlumps, chosenMeatlump);
            }
        }
        if (angryMeatlumps != null && angryMeatlumps.size() > 0)
        {
            utils.setScriptVar(self, ANGRY_MEATLUMPS_LIST_SCRIPTVAR, angryMeatlumps);
        }
        if (angryMeatlumps.size() < MAX_NUM_ANGRY_MEATLUMPS)
        {
            messageTo(self, "makeNewAngryMeatlump", null, 29, false);
        }
        return SCRIPT_CONTINUE;
    }
    public Vector findEligibleMeatlumps(obj_id hideout) throws InterruptedException
    {
        Vector eligibleMeatlumps = new Vector();
        eligibleMeatlumps.setSize(0);
        obj_id[] cellList = getContents(hideout);
        if (cellList != null && cellList.length > 0)
        {
            for (int i = 0; i < cellList.length; i++)
            {
                obj_id cell = cellList[i];
                if ((getTemplateName(cell)).equals(structure.TEMPLATE_CELL))
                {
                    obj_id[] cellContents = getContents(cell);
                    if (cellContents != null && cellContents.length > 0)
                    {
                        for (int j = 0; j < cellContents.length; j++)
                        {
                            obj_id thing = cellContents[j];
                            if (isMob(thing) && !isPlayer(thing))
                            {
                                if (hasObjVar(thing, ELIGIBLE_OBJVAR) && !buff.hasBuff(thing, "mtp_meatlump_angry") && !buff.hasBuff(thing, "mtp_meatlump_happy"))
                                {
                                    utils.addElement(eligibleMeatlumps, thing);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (eligibleMeatlumps != null && eligibleMeatlumps.size() > 0)
        {
            return eligibleMeatlumps;
        }
        return null;
    }
}
