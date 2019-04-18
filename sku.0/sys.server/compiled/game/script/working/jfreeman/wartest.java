package script.working.jfreeman;

import script.library.create;
import script.library.factions;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

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
        switch (txt) {
            case "go":
                if (!utils.hasScriptVar(self, "myWarriors")) {
                    spawnGroup(self, "group1", 20);
                    spawnGroup(self, "group2", -20);
                } else {
                    debugSpeakMsg(self, "already going");
                }
                break;
            case "fight":
                startFight(self);
                break;
            case "stop":
                killWarriors(self);
                break;
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
        for (obj_id warrior : warriors) {
            if (exists(warrior)) {
                destroyObject(warrior);
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
