package script.systems.image_designer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class holoemote extends script.base_script
{
    public holoemote()
    {
    }
    public static final String HOLO_EMOTE_SCRIPT = "systems.image_designer.holoemote";
    public static final String VAR_HOLO_HELP = "image_design.holohelp";
    public static final String HOLO_DATATABLE = "datatables/image_design/holoemote.iff";
    public static final String HOLO_HARDPOINT = "head";
    public static final String HOLO_EMOTE_ALL = "holoemote_all";
    public static final String HOLO_EMOTE_BEEHIVE = "holoemote_beehive";
    public static final String HOLO_EMOTE_BRAINSTORM = "holoemote_brainstorm";
    public static final String HOLO_EMOTE_IMPERIAL = "holoemote_imperial";
    public static final String HOLO_EMOTE_REBEL = "holoemote_rebel";
    public static final String HOLO_EMOTE_BUBBLEHEAD = "holoemote_bubblehead";
    public static final String HOLO_EMOTE_HOLOGLITTER = "holoemote_hologlitter";
    public static final String HOLO_EMOTE_HOLONOTES = "holoemote_holonotes";
    public static final String HOLO_EMOTE_SPARKY = "holoemote_sparky";
    public static final String HOLO_EMOTE_CHAMPAGNE = "holoemote_champagne";
    public static final String HOLO_EMOTE_BULLHORNS = "holoemote_bullhorns";
    public static final String HOLO_EMOTE_KITTY = "holoemote_kitty";
    public static final String HOLO_EMOTE_PHONYTAIL = "holoemote_phonytail";
    public static final String HOLO_EMOTE_BLOSSOM = "holoemote_blossom";
    public static final String HOLO_EMOTE_BUTTERFLIES = "holoemote_butterflies";
    public static final String HOLO_EMOTE_HAUNTED = "holoemote_haunted";
    public static final String HOLO_EMOTE_HEARTS = "holoemote_hearts";
    public static final String HOLO_EMOTE_DELETE = "delete";
    public static final String HOLO_EMOTE_HELP = "help";
    public static final String STF_FILE = "image_designer";
    public static final int MINUTE = 60;
    public int holoHelpOff(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id holoSelf = sui.getPlayerId(params);
        if (utils.hasScriptVar(holoSelf, VAR_HOLO_HELP))
        {
            utils.removeScriptVar(holoSelf, VAR_HOLO_HELP);
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdHoloEmote(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!utils.hasObjVar(self, "holoEmote"))
        {
            sendSystemMessage(self, new string_id(STF_FILE, "no_holoemote"));
            return SCRIPT_CONTINUE;
        }
        if ((toLower(params)).equals(HOLO_EMOTE_HELP))
        {
            if (!utils.hasScriptVar(self, VAR_HOLO_HELP))
            {
                int holoHelpCharges = utils.getIntObjVar(self, "holoEmoteCharges");
                if ((toLower(utils.getStringObjVar(self, "holoEmote"))).equals(HOLO_EMOTE_ALL))
                {
                    sui.msgbox(self, self, "Your Holo-Emote generator can play all Holo-Emotes available. \n You have " + holoHelpCharges + " charges remaining. \n To play a Holo-Emote, type /holoemote <name>. \n To delete your Holo-Emote type /holoemote delete. \n Purchasing a new Holo-Emote will automatically delete your current Holo-Emote. \n \n The available Holo-Emote names are: \n \n Beehive	Blossom	Brainstorm \n Bubblehead	Bullhorns	Butterflies \n Champagne	Haunted	Hearts \n Hologlitter	Holonotes	Imperial \n Kitty	Phonytail	Rebel \n Sparky", sui.OK_ONLY, "Holo-Emote Help", sui.MSG_NORMAL, "holoHelpOff");
                }
                else 
                {
                    String holoType = (utils.getStringObjVar(self, "holoEmote")).substring(10);
                    sui.msgbox(self, self, "Your current Holo-Emote is " + holoType + ". \n You have " + holoHelpCharges + " charges remaining. \n To play your Holo-Emote type /holoemote. \n To delete your Holo-Emote type /holoemote delete. \n Purchasing a new Holo-Emote will automatically delete your current Holo-Emote.", sui.OK_ONLY, "Holo-Emote Help", sui.MSG_NORMAL, "holoHelpOff");
                }
                utils.setScriptVar(self, VAR_HOLO_HELP, self);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        if ((toLower(params)).equals(HOLO_EMOTE_DELETE))
        {
            detachScript(self, HOLO_EMOTE_SCRIPT);
            utils.removeObjVar(self, "holoEmoteCharges");
            utils.removeObjVar(self, "holoEmote");
            sendSystemMessage(self, new string_id(STF_FILE, "remove_holoemote"));
            return SCRIPT_CONTINUE;
        }
        if (getGameTime() - utils.getIntScriptVar(self, "holoEmoteTimer") < MINUTE)
        {
            sendSystemMessage(self, "Your Holo-Emote generator will recharge in " + (utils.getIntScriptVar(self, "holoEmoteTimer") - getGameTime() + MINUTE) + " seconds.", null);
            return SCRIPT_CONTINUE;
        }
        if (utils.getIntObjVar(self, "holoEmoteCharges") <= 0)
        {
            sendSystemMessage(self, new string_id(STF_FILE, "no_charges_holoemote"));
            detachScript(self, HOLO_EMOTE_SCRIPT);
            utils.removeObjVar(self, "holoEmoteCharges");
            utils.removeObjVar(self, "holoEmote");
            if (utils.hasScriptVar(self, "holoEmoteTimer"))
            {
                utils.removeScriptVar(self, "holoEmoteTimer");
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            int charges = 0;
            if (hasObjVar(self, "holoEmote") && (toLower(utils.getStringObjVar(self, "holoEmote"))).equals(HOLO_EMOTE_ALL))
            {
                String holoEmoteIn = toLower(params);
                if (holoEmoteIn.equals("technokitty"))
                {
                    holoEmoteIn = "kitty";
                }
                String holoPath = getHoloEmotePath(holoEmoteIn);
                boolean playTrue = playClientEffectObj(self, holoPath, self, HOLO_HARDPOINT);
                if (playTrue)
                {
                    if (utils.hasScriptVar(self, "holoEmoteTimer"))
                    {
                        utils.removeScriptVar(self, "holoEmoteTimer");
                    }
                    charges = utils.getIntObjVar(self, "holoEmoteCharges");
                    charges--;
                    utils.removeObjVar(self, "holoEmoteCharges");
                    utils.setObjVar(self, "holoEmoteCharges", charges);
                    utils.setScriptVar(self, "holoEmoteTimer", getGameTime());
                }
                else 
                {
                    sendSystemMessage(self, new string_id(STF_FILE, "holoemote_help"));
                }
                return SCRIPT_CONTINUE;
            }
            else 
            {
                String holoString = utils.getStringObjVar(self, "holoEmote");
                if (holoString.length() < 11)
                {
                    LOG("HOLOEMOTE_ERROR", "A Holo-Emote received an invalid ObjVar from the UI: " + holoString);
                    return SCRIPT_CONTINUE;
                }
                String holoPath = getHoloEmotePath((utils.getStringObjVar(self, "holoEmote")).substring(10));
                boolean playTrue = playClientEffectObj(self, holoPath, self, HOLO_HARDPOINT);
                if (playTrue)
                {
                    if (utils.hasScriptVar(self, "holoEmoteTimer"))
                    {
                        utils.removeScriptVar(self, "holoEmoteTimer");
                    }
                    charges = utils.getIntObjVar(self, "holoEmoteCharges");
                    charges--;
                    utils.removeObjVar(self, "holoEmoteCharges");
                    utils.setObjVar(self, "holoEmoteCharges", charges);
                    utils.setScriptVar(self, "holoEmoteTimer", getGameTime());
                }
                else 
                {
                    LOG("HOLOEMOTE_ERROR", "A Holo-Emote did not fire! HoloString: " + holoString + " HoloPath: " + holoPath + " Charges: " + charges);
                }
                return SCRIPT_CONTINUE;
            }
        }
    }
    public String getHoloEmotePath(String holoEmote) throws InterruptedException
    {
        int holo_row = dataTableSearchColumnForString(holoEmote, 0, HOLO_DATATABLE);
        String holo_path = dataTableGetString(HOLO_DATATABLE, holo_row, 1);
        return holo_path;
    }
}
