package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.factions;
import script.library.utils;
import script.ai.ai_combat;

public class wartest extends script.base_script
{
    public wartest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        utils.removeScriptVar(self, "myWarriors");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String txt) throws InterruptedException
    {
        if (txt.equals("go"))
        {
            if (!utils.hasScriptVar(self, "myWarriors"))
            {
                spawnGroup(self, "group1", 20);
                spawnGroup(self, "group2", -20);
            }
            else 
            {
                debugSpeakMsg(self, "already going");
            }
        }
        else if (txt.equals("fight"))
        {
            startFight(self);
        }
        else if (txt.equals("stop"))
        {
            killWarriors(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void spawnGroup(obj_id chris, String group, int offset) throws InterruptedException
    {
        Vector warriors = new Vector();
        warriors.setSize(0);
        if (utils.hasScriptVar(chris, "myWarriors"))
        {
            warriors = utils.getResizeableObjIdArrayScriptVar(chris, "myWarriors");
        }
        location groupLoc = new location(getLocation(chris));
        groupLoc.x += offset;
        for (int i = 0; i < 50; i++)
        {
            obj_id npc = create.object("pirate", groupLoc);
            warriors = utils.addElement(warriors, npc);
            factions.setFaction(npc, group);
        }
        utils.setScriptVar(chris, "myWarriors", warriors);
    }
    public void killWarriors(obj_id chris) throws InterruptedException
    {
        if (!utils.hasScriptVar(chris, "myWarriors"))
        {
            debugSpeakMsg(chris, "none spawned");
            return;
        }
        obj_id[] warriors = utils.getObjIdArrayScriptVar(chris, "myWarriors");
        if (warriors.length < 100)
        {
            debugSpeakMsg(chris, "not done spawning yet " + warriors.length);
            return;
        }
        for (int i = 0; i < warriors.length; i++)
        {
            if (exists(warriors[i]))
            {
                destroyObject(warriors[i]);
            }
        }
        utils.removeScriptVar(chris, "myWarriors");
    }
    public void startFight(obj_id chris) throws InterruptedException
    {
        if (!utils.hasScriptVar(chris, "myWarriors"))
        {
            debugSpeakMsg(chris, "no warriors");
            return;
        }
        obj_id[] warriors = utils.getObjIdArrayScriptVar(chris, "myWarriors");
        if (warriors.length < 100)
        {
            debugSpeakMsg(chris, "not done spawning yet");
            return;
        }
        for (int i = 0; i < 50; i++)
        {
            if (exists(warriors[i]) && exists(warriors[i + 50]))
            {
                startCombat(warriors[i], warriors[i + 50]);
            }
        }
    }
}
