package script.quest.crowd_pleaser;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.prose;
import script.library.utils;

public class audience_member extends script.base_script
{
    public audience_member()
    {
    }
    public static final String[] MUSIC_NAMES = 
    {
        "starwars1",
        "rock",
        "starwars2",
        "folk",
        "starwars3",
        "ceremonial",
        "ballad",
        "waltz",
        "jazz",
        "virtuoso"
    };
    public static final String[] DANCE_NAMES = 
    {
        "basic",
        "rhythmic",
        "basic2",
        "rhythmic2",
        "footloose",
        "formal",
        "footloose2",
        "formal2",
        "popular",
        "poplock",
        "popular2",
        "poplock2",
        "lyrical",
        "exotic",
        "exotic2",
        "lyrical2",
        "exotic3",
        "exotic4"
    };
    public static final String PERFORMANCE_OBJVAR = "quest.crowd_pleaser.performance";
    public static final String PLAYER_OBJVAR = PERFORMANCE_OBJVAR + ".player";
    public static final String CONTROL_OBJVAR = PERFORMANCE_OBJVAR + ".control";
    public static final String RATING_OBJVAR = PERFORMANCE_OBJVAR + ".ratings";
    public static final String CHAT_OBJVAR = PERFORMANCE_OBJVAR + ".chat";
    public static final String STYLE_OBJVAR = PERFORMANCE_OBJVAR + ".style";
    public static final String AUDITION_OBJVAR = "quest.crowd_pleaser.audition";
    public static final String TYPE_OBJVAR = AUDITION_OBJVAR + ".type";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, "npc.converse.npc_converse_menu");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        String phrase = utils.getStringScriptVar(self, CHAT_OBJVAR);
        string_id msg = new string_id("quest/crowd_pleaser/audience", phrase);
        if (utils.hasScriptVar(self, STYLE_OBJVAR))
        {
            String style = utils.getStringScriptVar(self, STYLE_OBJVAR);
            if (!Character.isDigit(style.charAt(0)))
            {
                style = parseName(style);
            }
            prose_package pp = prose.getPackage(msg, style);
            chat.chat(self, speaker, null, null, chat.ChatFlag_targetOnly, pp);
        }
        else 
        {
            chat.chat(self, speaker, msg, chat.ChatFlag_targetOnly);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSetup(obj_id self, dictionary params) throws InterruptedException
    {
        int chance = rand(1, 100);
        obj_id control = params.getObjId("control");
        if (!isIdValid(control))
        {
            return SCRIPT_CONTINUE;
        }
        if (chance > 40)
        {
            int n = rand(0, 40);
            utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
            return SCRIPT_CONTINUE;
        }
        String type = utils.getStringScriptVar(control, TYPE_OBJVAR);
        Vector choices = new Vector();
        choices.setSize(0);
        if (type == null || type.equals(""))
        {
            type = "dance";
        }
        if (chance > 10)
        {
            if (chance > 25)
            {
                if (type.equals("music"))
                {
                    int m = rand(0, 5);
                    utils.setScriptVar(self, CHAT_OBJVAR, "positive_" + m + "_m");
                    for (int i = 0; i < MUSIC_NAMES.length; i++)
                    {
                        if (utils.hasScriptVar(control, RATING_OBJVAR + "." + MUSIC_NAMES[i]))
                        {
                            int rating = utils.getIntScriptVar(control, RATING_OBJVAR + "." + MUSIC_NAMES[i]);
                            if (rating > 0)
                            {
                                choices = utils.addElement(choices, MUSIC_NAMES[i]);
                            }
                        }
                    }
                    if (choices.size() == 0)
                    {
                        int n = rand(0, 40);
                        utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
                        return SCRIPT_CONTINUE;
                    }
                    int r = rand(0, (choices.size() - 1));
                    utils.setScriptVar(self, STYLE_OBJVAR, ((String)choices.get(r)));
                }
                else 
                {
                    int d = rand(0, 5);
                    utils.setScriptVar(self, CHAT_OBJVAR, "positive_" + d + "_d");
                    for (int i = 0; i < MUSIC_NAMES.length; i++)
                    {
                        if (utils.hasScriptVar(control, RATING_OBJVAR + "." + DANCE_NAMES[i]))
                        {
                            int rating = utils.getIntScriptVar(control, RATING_OBJVAR + "." + DANCE_NAMES[i]);
                            if (rating > 0)
                            {
                                choices = utils.addElement(choices, DANCE_NAMES[i]);
                            }
                        }
                    }
                    if (choices.size() == 0)
                    {
                        int n = rand(0, 40);
                        utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
                        return SCRIPT_CONTINUE;
                    }
                    int r = rand(0, (choices.size() - 1));
                    utils.setScriptVar(self, STYLE_OBJVAR, ((String)choices.get(r)));
                }
            }
            else 
            {
                int f = rand(0, 5);
                utils.setScriptVar(self, CHAT_OBJVAR, "positive_" + f + "_f");
                for (int i = 1; i <= 8; i++)
                {
                    if (utils.hasScriptVar(control, RATING_OBJVAR + "." + i))
                    {
                        int rating = utils.getIntScriptVar(control, RATING_OBJVAR + "." + i);
                        if (rating > 0)
                        {
                            choices = utils.addElement(choices, Integer.toString(i));
                        }
                    }
                }
                if (choices.size() == 0)
                {
                    int n = rand(0, 40);
                    utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
                    return SCRIPT_CONTINUE;
                }
                int r = rand(0, (choices.size() - 1));
                utils.setScriptVar(self, STYLE_OBJVAR, ((String)choices.get(r)));
            }
        }
        else 
        {
            if (chance > 5)
            {
                if (type.equals("music"))
                {
                    int m = rand(0, 5);
                    utils.setScriptVar(self, CHAT_OBJVAR, "negative_" + m + "_m");
                    for (int i = 0; i < MUSIC_NAMES.length; i++)
                    {
                        if (utils.hasScriptVar(control, RATING_OBJVAR + "." + MUSIC_NAMES[i]))
                        {
                            int rating = utils.getIntScriptVar(control, RATING_OBJVAR + "." + MUSIC_NAMES[i]);
                            if (rating < 0)
                            {
                                choices = utils.addElement(choices, MUSIC_NAMES[i]);
                            }
                        }
                    }
                    if (choices.size() == 0)
                    {
                        int n = rand(0, 40);
                        utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
                        return SCRIPT_CONTINUE;
                    }
                    int r = rand(0, (choices.size() - 1));
                    utils.setScriptVar(self, STYLE_OBJVAR, ((String)choices.get(r)));
                }
                else 
                {
                    int d = rand(0, 5);
                    utils.setScriptVar(self, CHAT_OBJVAR, "negative_" + d + "_d");
                    for (int i = 0; i < MUSIC_NAMES.length; i++)
                    {
                        if (utils.hasScriptVar(control, RATING_OBJVAR + "." + DANCE_NAMES[i]))
                        {
                            int rating = utils.getIntScriptVar(control, RATING_OBJVAR + "." + DANCE_NAMES[i]);
                            if (rating < 0)
                            {
                                choices = utils.addElement(choices, DANCE_NAMES[i]);
                            }
                        }
                    }
                    if (choices.size() == 0)
                    {
                        int n = rand(0, 40);
                        utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
                        return SCRIPT_CONTINUE;
                    }
                    int r = rand(0, (choices.size() - 1));
                    utils.setScriptVar(self, STYLE_OBJVAR, ((String)choices.get(r)));
                }
            }
            else 
            {
                int f = rand(0, 5);
                utils.setScriptVar(self, CHAT_OBJVAR, "negative_" + f + "_f");
                for (int i = 1; i <= 8; i++)
                {
                    if (utils.hasScriptVar(control, RATING_OBJVAR + "." + i))
                    {
                        int rating = utils.getIntScriptVar(control, RATING_OBJVAR + "." + i);
                        if (rating < 0)
                        {
                            choices = utils.addElement(choices, Integer.toString(i));
                        }
                    }
                }
                if (choices.size() == 0)
                {
                    int n = rand(0, 40);
                    utils.setScriptVar(self, CHAT_OBJVAR, "neutral_" + n);
                    return SCRIPT_CONTINUE;
                }
                int r = rand(0, (choices.size() - 1));
                utils.setScriptVar(self, STYLE_OBJVAR, ((String)choices.get(r)));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String parseName(String name) throws InterruptedException
    {
        if (Character.isDigit(name.charAt(name.length() - 1)))
        {
            name = name.substring(0, (name.length() - 1)) + " " + name.substring(name.length() - 1);
        }
        name = (name.substring(0, 1)).toUpperCase() + (name.substring(1)).toLowerCase();
        return name;
    }
}
