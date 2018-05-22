package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;

public class isapvpgod extends script.base_script
{
    public isapvpgod()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Greeting, Human!  To begin applying the smackdown, say PVP ON");
        sendSystemMessageTestingOnly(self, "To discontinue the laying down of the smack, say PVP OFF");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equalsIgnoreCase("pvp on"))
        {
            if (isSpaceScene())
            {
                space_transition.setPlayerOvert(self, (-615855020));
                sendSystemMessageTestingOnly(self, "Just start killing Bothans. I'll tell you when to stop.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "With what? Your HANDS?! You have to be in space, yo.");
            }
        }
        else if (text.equalsIgnoreCase("pvp off"))
        {
            if (isSpaceScene())
            {
                obj_id ship = space_transition.getContainingShip(self);
                space_transition.clearOvertStatus(ship);
                sendSystemMessageTestingOnly(self, "Ah! Safe, but never innocent. No wonder she still weeps for you.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "It is very dark. You are likely to be eaten by a Grue.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
