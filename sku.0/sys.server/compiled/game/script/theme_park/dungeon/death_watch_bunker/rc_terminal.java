package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.create;

public class rc_terminal extends script.base_script
{
    public rc_terminal()
    {
    }
    public static final String MSGS = "dungeon/death_watch";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Droid Terminal");
        createTriggerVolume("usability", 3, true);
        createMouseDroid(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        location here = getLocation(self);
        location there = getLocation(speaker);
        float locDistance = getDistance(here, there);
        if (locDistance > 2.0f)
        {
            string_id tooFar = new string_id(MSGS, "too_far_from_terminal");
            sendSystemMessage(speaker, tooFar);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "previous"))
        {
            obj_id old = getObjIdObjVar(self, "previous");
            if (old == speaker)
            {
                string_id voice = new string_id(MSGS, "reload_voice_pattern");
                sendSystemMessage(speaker, voice);
                return SCRIPT_CONTINUE;
            }
        }
        if (!hasObjVar(self, "controller"))
        {
            if (isPlayer(speaker))
            {
                setObjVar(self, "controller", speaker);
            }
        }
        obj_id current = getObjIdObjVar(self, "controller");
        if (speaker != current)
        {
            string_id inUse = new string_id(MSGS, "terminal_in_use");
            sendSystemMessage(speaker, inUse);
            return SCRIPT_CONTINUE;
        }
        dictionary web = new dictionary();
        web.put("player", speaker);
        messageTo(self, "timesUp", web, 180, false);
        obj_id mouse = getObjIdObjVar(self, "mouse");
        int forwardCheck = text.indexOf("forward");
        int backwardCheck = text.indexOf("backward");
        int rightCheck = text.indexOf("right");
        int leftCheck = text.indexOf("left");
        if (forwardCheck > -1)
        {
            String distUp = text.substring(text.indexOf(" ") + 1, text.length());
            int distanceUp = utils.stringToInt(distUp);
            dictionary up = new dictionary();
            up.put("distance", distanceUp);
            messageTo(mouse, "moveForward", up, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (backwardCheck > -1)
        {
            String distDown = text.substring(text.indexOf(" ") + 1, text.length());
            int distanceDown = utils.stringToInt(distDown);
            dictionary down = new dictionary();
            down.put("distance", distanceDown);
            messageTo(mouse, "moveBackward", down, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (rightCheck > -1)
        {
            String distRight = text.substring(text.indexOf(" ") + 1, text.length());
            int distanceRight = utils.stringToInt(distRight);
            dictionary right = new dictionary();
            right.put("distance", distanceRight);
            messageTo(mouse, "moveRight", right, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (leftCheck > -1)
        {
            String dist = text.substring(text.indexOf(" ") + 1, text.length());
            int distance = utils.stringToInt(dist);
            dictionary left = new dictionary();
            left.put("distance", distance);
            messageTo(mouse, "moveLeft", left, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (text.equals("detonate"))
        {
            dictionary webster = new dictionary();
            webster.put("player", speaker);
            messageTo(mouse, "detonate", webster, 1, false);
            removeObjVar(self, "controller");
            setObjVar(self, "previous", speaker);
            messageTo(self, "removePrevious", null, 30, false);
            removeObjVar(self, "mouse");
            return SCRIPT_CONTINUE;
        }
        if (text.equals("reset"))
        {
            if (!hasObjVar(self, "mouse"))
            {
                createMouseDroid(self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id droid = getObjIdObjVar(self, "mouse");
                destroyObject(droid);
                createMouseDroid(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createMouseDroid(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        String planet = here.area;
        obj_id top = getTopMostContainer(self);
        obj_id room = getCellId(top, "observationroom34");
        location mouseLoc = new location(92, -64, -128, planet, room);
        obj_id mouse = create.object("r2", mouseLoc);
        setObjVar(self, "mouse", mouse);
        attachScript(mouse, "theme_park.dungeon.death_watch_bunker.rc_mouse");
        return;
    }
    public int removePrevious(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "previous"))
        {
            removeObjVar(self, "previous");
        }
        return SCRIPT_CONTINUE;
    }
    public int timesUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id speaker = params.getObjId("player");
        obj_id player = getObjIdObjVar(self, "controller");
        removeObjVar(self, "controller");
        setObjVar(self, "previous", player);
        messageTo(self, "removePrevious", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            testSui(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int testSui(obj_id self, obj_id player) throws InterruptedException
    {
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "readableText");
        string_id fallBackString = new string_id("BOOK", "wiped");
        string_id fallBackTitle = new string_id("BOOK", "no_title");
        if (hasObjVar(self, "text_id"))
        {
            int textNum = getIntObjVar(self, "text_id");
            fallBackString = new string_id("theme_park/book_text", "text_" + textNum);
            fallBackTitle = new string_id("theme_park/book_text", "title_" + textNum);
        }
        String text = getString(fallBackString);
        String title = getString(fallBackTitle);
        if (title == null || title.equals(""))
        {
            title = "VRS Terminal";
        }
        if (text == null || text.equals(""))
        {
            string_id instructions = new string_id(MSGS, "rc_mouse_instructions");
            text = getString(instructions);
        }
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "CLOSE");
        sui.showSUIPage(pid);
        return pid;
    }
}
