package script.working.cthurow;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class uber extends script.base_script
{
    public uber()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Uberscript attached.  Say \"uber\" for options.");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        String[] argv = split(text, ' ');
        if (argv[0].equals("uber"))
        {
            sendSystemMessageTestingOnly(self, "List of commands:vendor_skills house generator factory bank_terminal");
        }
        if (argv[0].equals("house"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            createObject("object/tangible/deed/player_house_deed/tatooine_house_small_deed.iff", inventory, "");
            sendSystemMessageTestingOnly(self, "Housified.");
        }
        if (argv[0].equals("generator"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            createObject("object/tangible/deed/generator_deed/power_generator_fusion_deed.iff", inventory, "");
            sendSystemMessageTestingOnly(self, "Generated.");
        }
        if (argv[0].equals("factory"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            createObject("object/tangible/deed/factory_deed/item_factory_deed.iff", inventory, "");
            sendSystemMessageTestingOnly(self, "Factored.");
        }
        if (argv[0].equals("bank_terminal"))
        {
            obj_id inventory = utils.getInventoryContainer(self);
            createObject("object/tangible/terminal/terminal_bank.iff", getLocation(self));
            sendSystemMessageTestingOnly(self, "terminated.");
        }
        return SCRIPT_CONTINUE;
    }
}
