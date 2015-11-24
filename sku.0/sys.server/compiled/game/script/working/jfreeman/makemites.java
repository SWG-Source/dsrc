package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class makemites extends script.base_script
{
    public makemites()
    {
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "rockMiteMaking");
        if (hasObjVar(self, "miteReg"))
        {
            killAllMites(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "rockMiteMaking");
        if (hasObjVar(self, "miteReg"))
        {
            killAllMites(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location myLoc = getLocation(self);
        if (text.equals("go"))
        {
            if (hasObjVar(self, "rockMiteMaking"))
            {
                debugSpeakMsg(self, "I'm still making rock mites!");
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "rockMiteMaking", 0);
            messageTo(self, "spawnMite", null, 0, false);
        }
        else if ((text.equals("stop") || text.equals("die")) && hasObjVar(self, "rockMiteMaking"))
        {
            removeObjVar(self, "rockMiteMaking");
        }
        if (text.equals("die") && hasObjVar(self, "miteReg"))
        {
            killAllMites(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnMite(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "rockMiteMaking"))
        {
            return SCRIPT_CONTINUE;
        }
        int numSpawned = getIntObjVar(self, "rockMiteMaking");
        location spawnLoc = new location(getLocation(self));
        spawnLoc.x += rand(-15, 15);
        spawnLoc.z += rand(-15, 15);
        String templateName = "object/creature/monster/rock_mite/rock_mite_desert_";
        switch (rand(0, 3))
        {
            case 0:
            templateName = templateName + "giant.iff";
            break;
            case 1:
            templateName = templateName + "large.iff";
            break;
            case 2:
            templateName = templateName + "medium.iff";
            break;
            case 3:
            templateName = templateName + "small.iff";
            break;
        }
        obj_id mite = createObject(templateName, spawnLoc);
        registerMite(self, mite);
        numSpawned++;
        if (numSpawned > 19)
        {
            debugSpeakMsg(self, "I made 20 rock mites!");
            removeObjVar(self, "rockMiteMaking");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "rockMiteMaking", numSpawned);
        messageTo(self, "spawnMite", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public void registerMite(obj_id player, obj_id mite) throws InterruptedException
    {
        if (!hasObjVar(player, "miteReg"))
        {
            obj_id[] mites = new obj_id[1];
            mites[0] = mite;
            setObjVar(player, "miteReg", mites);
            return;
        }
        Vector mites = getResizeableObjIdArrayObjVar(player, "miteReg");
        mites = utils.addElement(mites, mite);
        if (mites != null && mites.size() > 0)
        {
            setObjVar(player, "miteReg", mites);
        }
    }
    public void killAllMites(obj_id player) throws InterruptedException
    {
        obj_id[] mites = getObjIdArrayObjVar(player, "miteReg");
        for (int i = 0; i < mites.length; i++)
        {
            destroyObject(mites[i]);
        }
        removeObjVar(player, "miteReg");
    }
}
