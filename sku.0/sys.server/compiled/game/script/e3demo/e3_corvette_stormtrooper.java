package script.e3demo;

import script.dictionary;
import script.library.anims;
import script.library.chat;
import script.library.factions;
import script.obj_id;

public class e3_corvette_stormtrooper extends script.base_script
{
    public e3_corvette_stormtrooper()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAnimationMood(self, "npc_imperial");
        createTriggerVolume("playerEnter", 6.0f, true);
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals("playerEnter"))
        {
            if (isPlayer(breacher))
            {
                faceTo(self, getLocation(breacher));
                setState(breacher, STATE_SHIP_INTERIOR, false);
                messageTo(self, "doSalute", null, 0.5f, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doSalute(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, anims.PLAYER_SALUTE2);
        messageTo(self, "sendMessage", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int sendMessage(obj_id self, dictionary params) throws InterruptedException
    {
        chat.chat(self, "Sir, This area is secured. The bridge access and command center are still unsecured.");
        return SCRIPT_CONTINUE;
    }
}
