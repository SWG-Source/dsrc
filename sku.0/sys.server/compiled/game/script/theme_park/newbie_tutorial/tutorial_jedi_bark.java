package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class tutorial_jedi_bark extends script.base_script
{
    public tutorial_jedi_bark()
    {
    }
    public static final String JEDI_BARK = "newbie_tutorial/newbie_convo";
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        string_id bark = pickRandomBarkText();
        dictionary webster = new dictionary();
        webster.put("bark", bark);
        messageTo(self, "barkRandomPhrase", webster, 5.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int barkRandomPhrase(obj_id self, dictionary params) throws InterruptedException
    {
        string_id bark = params.getStringId("bark");
        chat.chat(self, bark);
        string_id newBark = pickRandomBarkText();
        dictionary webster = new dictionary();
        webster.put("bark", newBark);
        messageTo(self, "barkRandomPhrase", webster, 25.0f, true);
        return SCRIPT_CONTINUE;
    }
    public string_id pickRandomBarkText() throws InterruptedException
    {
        string_id bark = new string_id();
        int randomBarkNumber = rand(1, 5);
        switch (randomBarkNumber)
        {
            case 1:
            bark = new string_id(JEDI_BARK, "jedi_bark_1");
            break;
            case 2:
            bark = new string_id(JEDI_BARK, "jedi_bark_2");
            break;
            case 3:
            bark = new string_id(JEDI_BARK, "jedi_bark_3");
            break;
            case 4:
            bark = new string_id(JEDI_BARK, "jedi_bark_4");
            break;
            case 5:
            bark = new string_id(JEDI_BARK, "jedi_bark_5");
            break;
            default:
            bark = new string_id(JEDI_BARK, "jedi_bark_1");
            break;
        }
        return bark;
    }
}
