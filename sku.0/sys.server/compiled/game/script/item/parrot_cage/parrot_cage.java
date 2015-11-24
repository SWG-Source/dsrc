package script.item.parrot_cage;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.debug;
import java.util.StringTokenizer;
import script.library.chat;
import script.library.craftinglib;

public class parrot_cage extends script.base_script
{
    public parrot_cage()
    {
    }
    public static final String PARROT_VOLUME = "parrot_trigger_volume";
    public static final float HEARING_RADIUS = 6.0f;
    public static final String VAR_MESSAGE = "spoken_message";
    public static final String VAR_BIRD_RESPONSE = "bird_response";
    public static final String VAR_SPEECH_IGNORE = "ignore_next_speech";
    public static final String VAR_MATCHES = "bird_response_matches";
    public static final String[] BIRD_SOUNDS = 
    {
        "SQUACK!",
        "TWEET",
        "YIPE!",
        "WEK?",
        "AAAAAWK!",
        "AWK",
        "CRACKER?",
        "ZIP"
    };
    public static final int SOUND_CHANCE = 60;
    public static final int NO_REPLY_PERCENT = 50;
    public static final String VAR_SPECIAL_MESSAGE = "bird_special_message";
    public static final float SPECIAL_LOC_RADIUS = 3f;
    public static final int LOOT_RADIUS_MAX = 50;
    public static final int MATCH_COUNT = 50;
    public static final String PARROT_TREASURE_SCRIPT = "item.parrot_cage.parrot_treasure";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############Parrot script initialized############");
        setObjVar(self, VAR_MESSAGE, "no_message_yet");
        setObjVar(self, VAR_SPECIAL_MESSAGE, false);
        setObjVar(self, VAR_BIRD_RESPONSE, "no_response_yet");
        setObjVar(self, VAR_MATCHES, 0);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String message) throws InterruptedException
    {
        String previousMessage = getStringObjVar(self, VAR_MESSAGE);
        if (hasObjVar(self, "ignore_me") || getDistance(speaker, self) > HEARING_RADIUS)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "ignore_me", "to_ignore_next_speech_trigger");
        debug.debugAllMsg("DEBUG", self, "#############Last Player Message: " + getStringObjVar(self, VAR_MESSAGE) + "############");
        dictionary params = new dictionary();
        params.put("parrot", self);
        params.put("speaker", speaker);
        params.put("message", message);
        messageTo(self, "parrotSpeak", params, 1.5f, true);
        setObjVar(self, VAR_MESSAGE, message);
        return SCRIPT_CONTINUE;
    }
    public int parrotSpeak(obj_id self, dictionary params) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############parrotSpeak()############");
        obj_id parrot = (obj_id)params.get("parrot");
        obj_id speaker = (obj_id)params.get("speaker");
        String message = (String)params.get("message");
        StringTokenizer st = new StringTokenizer(message);
        int wordCount = st.countTokens();
        int startingPosition = rand(0, wordCount - 1);
        int wordsLeft = wordCount - startingPosition;
        int numberToSpeak = rand(0, wordsLeft);
        String parrotText = new String();
        int chanceSound = rand(0, 100);
        boolean soundOnEnd = false;
        boolean noBirdSounds = false;
        if (chanceSound < SOUND_CHANCE)
        {
            chanceSound = rand(0, BIRD_SOUNDS.length);
            if (chanceSound < BIRD_SOUNDS.length)
            {
                if (rand(0, 1) == 1)
                {
                    parrotText += BIRD_SOUNDS[chanceSound];
                }
                else 
                {
                    soundOnEnd = true;
                }
            }
        }
        else 
        {
            noBirdSounds = true;
        }
        int index = 0;
        int tokenCount = 0;
        while (st.hasMoreTokens())
        {
            ++index;
            if (index >= startingPosition && index <= (startingPosition + numberToSpeak))
            {
                parrotText += " " + st.nextToken();
                ++tokenCount;
            }
            else 
            {
                st.nextToken();
            }
        }
        if (soundOnEnd)
        {
            parrotText += " " + BIRD_SOUNDS[chanceSound];
        }
        chanceSound = rand(0, 100);
        if (parrotText != null && !parrotText.equals("") && chanceSound > NO_REPLY_PERCENT)
        {
            if (tokenCount == wordCount && noBirdSounds && wordCount > 4)
            {
                int matches = getIntObjVar(self, VAR_MATCHES);
                setObjVar(self, VAR_MATCHES, ++matches);
                setObjVar(self, VAR_BIRD_RESPONSE, "no_response_yet");
                if (!hasScript(speaker, PARROT_TREASURE_SCRIPT) && matches > MATCH_COUNT)
                {
                    chat.chat(parrot, parrotText);
                    messageTo(self, "specialMessage", params, 1.5f, true);
                    debug.debugAllMsg("DEBUG", self, "#############SPECIAL MESSAGE TRIGGERED!!!!############");
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                chat.chat(parrot, parrotText);
                setObjVar(self, VAR_BIRD_RESPONSE, parrotText);
                debug.debugAllMsg("DEBUG", self, "#############Parrot response: " + parrotText + "############");
            }
        }
        removeObjVar(self, "ignore_me");
        return SCRIPT_CONTINUE;
    }
    public int specialMessage(obj_id self, dictionary params) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############specialMessage()############");
        obj_id parrot = (obj_id)params.get("parrot");
        obj_id speaker = (obj_id)params.get("speaker");
        debug.debugAllMsg("DEBUG", self, "#############creating special message and location############");
        location parrotLoc = getLocation(parrot);
        location desiredLoc = (location)parrotLoc.clone();
        desiredLoc = findSpotNear(parrot, parrotLoc, LOOT_RADIUS_MAX);
        for (int i = 0; i < 5 && !isValidLocation(desiredLoc, SPECIAL_LOC_RADIUS); ++i)
        {
            desiredLoc = findSpotNear(parrot, parrotLoc, LOOT_RADIUS_MAX);
        }
        if (desiredLoc != null)
        {
            debug.debugAllMsg("DEBUG", self, "#############" + desiredLoc.toString() + "############");
            string_id specialMessage = new string_id("item_n", "bird_special_message");
            chat.chat(parrot, getName(speaker));
            chat.chat(parrot, specialMessage);
            String locationStr = new String();
            StringTokenizer st = new StringTokenizer(desiredLoc.toString());
            for (int i = 0; i < 3 && st.hasMoreTokens(); ++i)
            {
                locationStr += st.nextToken();
            }
            locationStr = locationStr.substring(1);
            locationStr = locationStr.substring(0, locationStr.length() - 2);
            chat.chat(parrot, locationStr);
            attachScript(speaker, PARROT_TREASURE_SCRIPT);
            params.put("specialLoc", desiredLoc);
            messageTo(speaker, "createTreasureLocation", params, 0, true);
        }
        else 
        {
            debug.debugAllMsg("DEBUG", self, "#############The special parrot location is bad############");
        }
        messageTo(self, "resetBirdHearing", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int resetBirdHearing(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "ignore_me");
        setObjVar(self, VAR_MATCHES, 0);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".useModifier"))
        {
            names[idx] = "usemodifier";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".useModifier");
            attribs[idx] = "" + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
