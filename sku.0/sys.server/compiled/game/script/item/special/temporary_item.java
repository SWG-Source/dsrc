package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class temporary_item extends script.base_script
{
    public temporary_item()
    {
    }
    public static final float LIFESPAN = 3600.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id npcOwner = utils.getContainingNpcCreature(self);
        if (hasScript(npcOwner, "npc.vendor.vendor"))
        {
            detachScript(self, "item.special.temporary_item");
            removeObjVar(self, "item.lifespan");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            float lifeSpan = getLifeSpan(self);
            float rightNow = getGameTime();
            setObjVar(self, "item.temporary.time_stamp", rightNow);
            float dieTime = getDieTime(lifeSpan, self);
            if (dieTime < 1)
            {
                dieTime = 1.0f;
            }
            messageTo(self, "cleanUp", null, dieTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id npcOwner = utils.getContainingNpcCreature(self);
        if (hasScript(npcOwner, "npc.vendor.vendor"))
        {
            detachScript(self, "item.special.temporary_item");
            removeObjVar(self, "item.lifespan");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            float lifeSpan = getLifeSpan(self);
            float dieTime = getDieTime(lifeSpan, self);
            if (dieTime < 1)
            {
                dieTime = 1.0f;
            }
            messageTo(self, "cleanUp", null, dieTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public float getLifeSpan(obj_id self) throws InterruptedException
    {
        float lifeSpan = LIFESPAN;
        if (hasObjVar(self, "item.lifespan"))
        {
            lifeSpan = (float)getIntObjVar(self, "item.lifespan");
        }
        return lifeSpan;
    }
    public float getDieTime(float lifeSpan, obj_id tempObject) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(tempObject, "item.temporary.time_stamp");
        float deathStamp = timeStamp + lifeSpan;
        float rightNow = getGameTime();
        float dieTime = deathStamp - rightNow;
        return dieTime;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (self.isBeingDestroyed())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id containedBy = getContainedBy(self);
        if (isGameObjectTypeOf(containedBy, GOT_misc_container_public))
        {
            return SCRIPT_CONTINUE;
        }
        float lifeSpan = LIFESPAN;
        if (hasObjVar(self, "item.lifespan"))
        {
            lifeSpan = (float)getIntObjVar(self, "item.lifespan");
        }
        float dieTime = getDieTime(lifeSpan, self);
        if (dieTime < 1)
        {
            destroyObject(self);
        }
        else 
        {
            messageTo(self, "cleanUp", null, dieTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        obj_id npcOwner = utils.getContainingNpcCreature(self);
        if (hasScript(npcOwner, "npc.vendor.vendor"))
        {
            detachScript(self, "item.special.temporary_item");
            removeObjVar(self, "item.lifespan");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            float lifeSpan = getLifeSpan(self);
            float dieTime = getDieTime(lifeSpan, self);
            int timeLeft = (int)dieTime;
            names[idx] = "storyteller_time_remaining";
            attribs[idx] = utils.formatTimeVerbose(timeLeft);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
