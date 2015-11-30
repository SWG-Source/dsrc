package script.theme_park.heroic.tusken;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.utils;

public class macy_malo extends script.base_script
{
    public macy_malo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "bomb_a_tusken", null, rand(35, 60), false);
        detachScript(self, "ai.creature_combat");
        detachScript(self, "ai.ai");
        detachScript(self, "systems.combat.credit_for_kills");
        setCreatureCoverVisibility(self, false);
        return SCRIPT_CONTINUE;
    }
    public int bomb_a_tusken(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(getLocation(self), 1000.0f);
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        Vector npc = new Vector();
        npc.setSize(0);
        Vector gods = new Vector();
        gods.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            if (!isIdValid(objects[i]) || !exists(objects[i]))
            {
                continue;
            }
            if (!isMob(objects[i]) && !isPlayer(objects[i]))
            {
                continue;
            }
            if (isGod(objects[i]))
            {
                gods.add(objects[i]);
            }
            if (isDead(objects[i]))
            {
                continue;
            }
            String tFac = factions.getFaction(objects[i]);
            if (tFac == null || tFac.equals(""))
            {
                continue;
            }
            if (!tFac.equals("tusken") && !tFac.equals("heroic_tusken"))
            {
                continue;
            }
            if (isIdValid(getLocation(objects[i]).cell))
            {
                continue;
            }
            npc.add(objects[i]);
        }
        if (npc == null || npc.size() == 0)
        {
            messageTo(self, "bomb_a_tusken", null, rand(35, 60), false);
            sendGodMessage(gods, "Macy found no valid targets to bomb, will try again in 35-60 seconds");
            return SCRIPT_CONTINUE;
        }
        obj_id target = ((obj_id)npc.get(rand(0, npc.size() - 1)));
        setLocation(self, getLocation(target));
        location loc = getLocation(target);
        String locationData = "" + loc.x + " " + loc.y + " " + loc.z + " " + loc.cell + " " + loc.x + " " + loc.y + " " + loc.z;
        queueCommand(self, (-116167121), target, locationData, COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "bomb_a_tusken", null, rand(35, 60), false);
        sendGodMessage(gods, "My name is Macy, I fly a Y-Wing and I just attacked a tusken at " + loc + ". Why don't you warp on over here and check it out. Now remember, if a creature is too strong to kill in one shot then nothing will happen. Thems the break.");
        return SCRIPT_CONTINUE;
    }
    public void sendGodMessage(Vector gods, String message) throws InterruptedException
    {
        if (gods != null || gods.size() > 0)
        {
            obj_id[] xfer = new obj_id[0];
            if (gods != null)
            {
                xfer = new obj_id[gods.size()];
                gods.toArray(xfer);
            }
            utils.sendSystemMessageTestingOnly(xfer, message);
        }
    }
}
