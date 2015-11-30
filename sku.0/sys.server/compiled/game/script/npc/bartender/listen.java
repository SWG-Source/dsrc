package script.npc.bartender;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;

public class listen extends script.base_script
{
    public listen()
    {
    }
    public static final String SCRIPT_LISTEN = "npc.bartender.list";
    public static final String VAR_LISTEN_BASE = "bartender.listen";
    public static final String VAR_LISTEN_VALUE = "bartender.listen.value";
    public static final String VAR_LISTEN_SPEAKER = "bartender.listen.speaker";
    public static final String VAR_LISTEN_TEXT = "bartender.listen.text";
    public static final String TBL_KEYWORDS = "datatables/npc/bartender/keyword.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleListenStop", null, rand(60, 180), false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, VAR_LISTEN_BASE);
        messageTo(self, "handleListen", null, rand(600, 1200), false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (isPlayer(speaker))
        {
            if (text.length() > 40 && text.length() < 100)
            {
                int max = getIntObjVar(self, VAR_LISTEN_VALUE);
                String[] keywords = dataTableGetStringColumn(TBL_KEYWORDS, "keyword");
                if (keywords != null && keywords.length > 0)
                {
                    int total = 1;
                    for (int i = 0; i < keywords.length; i++)
                    {
                        if ((toLower(text)).indexOf(toLower(keywords[i])) > -1)
                        {
                            total += dataTableGetInt(TBL_KEYWORDS, i, "value");
                        }
                    }
                    if (total > max)
                    {
                        setObjVar(self, VAR_LISTEN_VALUE, total);
                        setObjVar(self, VAR_LISTEN_SPEAKER, getAssignedName(speaker));
                        setObjVar(self, VAR_LISTEN_TEXT, text);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleListenStop(obj_id self, dictionary params) throws InterruptedException
    {
        String speaker = getStringObjVar(self, VAR_LISTEN_SPEAKER);
        String text = getStringObjVar(self, VAR_LISTEN_TEXT);
        if (speaker != null && !speaker.equals("") && text != null && !text.equals(""))
        {
            dictionary d = new dictionary();
            d.put("speaker", speaker);
            d.put("text", text);
            messageTo(self, "handleNewRumor", d, 0, false);
        }
        detachScript(self, SCRIPT_LISTEN);
        return SCRIPT_CONTINUE;
    }
}
