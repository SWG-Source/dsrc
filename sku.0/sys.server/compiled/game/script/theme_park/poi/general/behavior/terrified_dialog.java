package script.theme_park.poi.general.behavior;

import script.dictionary;
import script.library.chat;
import script.obj_id;
import script.string_id;

public class terrified_dialog extends script.base_script
{
    public terrified_dialog()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "saySomething", null, 10, true);
        setWantSawAttackTriggers(self, false);
        return SCRIPT_CONTINUE;
    }
    public int saySomething(obj_id self, dictionary params) throws InterruptedException
    {
        int words = rand(1, 10);
        String dialog = "terrified_" + words;
        string_id thingToSay = new string_id("random_dialog", dialog);
        chat.chat(self, thingToSay);
        messageTo(self, "saySomething", null, rand(15, 30), true);
        return SCRIPT_CONTINUE;
    }
    public int creatureDied(obj_id self, dictionary params) throws InterruptedException
    {
        attachScript(self, "theme_park.poi.general.conversation.pounce_convo");
        return SCRIPT_CONTINUE;
    }
}
