package script.systems.spawning.dropship;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class lambda extends script.systems.spawning.dropship.base
{
    public lambda()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleAttachDelay", null, 2f, false);
        return super.OnAttach(self);
    }
    public int handleAttachDelay(obj_id self, dictionary params) throws InterruptedException
    {
        stop(self);
        setPosture(self, POSTURE_PRONE);
        messageTo(self, "spawnPayload", null, 20f, true);
        queueCommand(self, (-1465754503), self, "", COMMAND_PRIORITY_FRONT);
        return SCRIPT_CONTINUE;
    }
    public int changePosture(obj_id self, dictionary params) throws InterruptedException
    {
        setPosture(self, POSTURE_UPRIGHT);
        messageTo(self, "selfCleanUp", null, 60f, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnPayload(obj_id self, dictionary params) throws InterruptedException
    {
        String[] spawnNames = utils.getStringArrayScriptVar(self, "spawnNames");
        if (spawnNames != null && spawnNames.length > 0)
        {
            location here = getLocation(self);
            if (here != null)
            {
                int pos = 0;
                obj_id leader = null;
                Vector spawns = new Vector();
                spawns.setSize(0);
                obj_id thisSpawn;
                for (int i = 0; i < spawnNames.length; i++)
                {
                    thisSpawn = create.object(spawnNames[i], here);
                    if (isIdValid(thisSpawn) && !isIdValid(leader))
                    {
                        leader = thisSpawn;
                        spawns = utils.addElement(spawns, thisSpawn);
                        if (i > 0 && isIdValid(leader))
                        {
                            ai_lib.wander(thisSpawn);
                            attachScript(thisSpawn, "ai.imperial_presence.harass");
                        }
                    }
                    if (isIdValid(thisSpawn))
                    {
                        detachScript(leader, "ai.soldier");
                        setMovementRun(thisSpawn);
                        utils.removeObjVar(thisSpawn, "ai.diction");
                        ai_lib.followInFormation(thisSpawn, leader, 4, ++pos);
                    }
                }
                if (spawns != null && spawns.size() > 0)
                {
                    dictionary spawnParams = utils.getDictionaryScriptVar(self, "spawnParameters");
                    if (spawnParams != null && !spawnParams.isEmpty())
                    {
                        messageTo(((obj_id)spawns.get(0)), "handleSpawnParameters", spawnParams, 1f, false);
                    }
                }
            }
        }
        messageTo(self, "changePosture", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int selfCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIdValid(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
