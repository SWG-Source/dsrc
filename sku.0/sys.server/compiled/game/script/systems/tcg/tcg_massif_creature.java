package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.create;
import script.library.colors;
import script.library.house_pet;
import script.library.hue;
import script.library.locations;
import script.library.pet_lib;
import script.library.player_structure;
import script.library.space_transition;
import script.library.space_utils;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class tcg_massif_creature extends script.base_script
{
    public tcg_massif_creature()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String LOGGING_CATEGORY = "sissynoid";
    public static final String TCG_STR = new String("tcg");
    public static final string_id SID_CHANGE_NAME = new string_id("player_structure", "greeter_change_name");
    public static final string_id SID_OBSCENE = new string_id(TCG_STR, "obscene_or_space_in_name");
    public static final string_id SID_SET_COLOR = new string_id(TCG_STR, "set_color");
    public static final string_id SID_TURN_ON = new string_id(TCG_STR, "call_pet");
    public static final string_id SID_TURN_OFF = new string_id(TCG_STR, "store_pet");
    public static final string_id SID_PET_RENAMED = new string_id(TCG_STR, "pet_renamed");
    public static final string_id SID_PET_CANT_HEAR_YOU = new string_id(TCG_STR, "pet_cant_hear_you");
    public static final int CHECK_PULSE = 60;
    public static final int MAX_DISTANCE_FOR_COMMAND = 8;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        blog("OnDestroy - init");
        if (hasObjVar(self, house_pet.PET_FOOD_OID))
        {
            obj_id food = getObjIdObjVar(self, house_pet.PET_FOOD_OID);
            if (isValidId(food) && exists(food))
            {
                destroyObject(food);
            }
        }
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnDestroy - has parent id");
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(controller, house_pet.MASSIFF_FOOD_OBJ))
        {
            obj_id food = getObjIdObjVar(self, house_pet.MASSIFF_FOOD_OBJ);
            if (isValidId(food) && exists(food))
            {
                destroyObject(food);
            }
            removeObjVar(controller, house_pet.MASSIFF_FOOD_OBJ);
        }
        blog("OnDestroy - removing child data from controller");
        CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " is being destroyed. This will remove the Massif OID from the controller and remove the KNOCK OUT variable.");
        removeObjVar(controller, house_pet.CHILD_OBJ_ID);
        removeObjVar(controller, house_pet.PET_HAS_BEEN_CALLED);
        removeObjVar(controller, house_pet.PET_KNOCKED_OUT);
        removeObjVar(controller, house_pet.MASSIFF_LAST_FED);
        removeObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "resetVariablesOnMassiff", null, 1, false);
        updateMassiffPet(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "resetVariablesOnMassiff", null, 1, false);
        obj_id owner = getOwner(self);
        if (isGod(owner))
        {
            messageTo(self, "findRandomMassiffPetActivity", null, rand(9, 18), false);
        }
        else 
        {
            messageTo(self, "findRandomMassiffPetActivity", null, rand(900, 1800), false);
        }
        updateMassiffPet(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id master, String text) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, house_pet.BEING_FED))
        {
            return SCRIPT_CONTINUE;
        }
        processSpeech(self, master, text);
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, house_pet.PET_MOVING, true);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        utils.removeScriptVar(self, house_pet.PET_MOVING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!house_pet.isInAPlayerHouse(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == owner)
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_CHANGE_NAME);
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_SET_COLOR);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!house_pet.isInAPlayerHouse(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (player == owner)
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                int pid = sui.inputbox(self, player, "@tcg:pet_name_d", sui.OK_CANCEL, "@tcg:pet_name_t", sui.INPUT_NORMAL, null, "handleSetPetName", null);
                sui.setPid(player, pid, house_pet.HOUSE_PET_MENU_PID);
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                int pid = sui.colorize(self, player, self, hue.INDEX_1, "handlePetColorize");
                sui.setPid(player, pid, house_pet.HOUSE_PET_MENU_PID);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawEmote(obj_id self, obj_id performer, String emote) throws InterruptedException
    {
        if (!isValidId(performer) || !exists(performer))
        {
            return SCRIPT_CONTINUE;
        }
        if (emote == null || emote.length() <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, house_pet.BEING_FED))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(self) || !isPlayer(performer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        if (!isValidId(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (performer != owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (getLookAtTarget(performer) != self)
        {
            if (getIntendedTarget(performer) != self)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (getPosture(self) == POSTURE_KNOCKED_DOWN)
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " saw an emote from owner: " + owner + " but is currently knocked out and cannot move.");
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(performer) || ai_lib.aiIsDead(self))
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " saw an emote from owner: " + owner + ", but the emote is being ignored.");
            if (ai_lib.isInCombat(self))
            {
                CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " could not act on emote from owner: " + owner + " because the Massif is reporting it is in COMBAT.");
            }
            else if (ai_lib.isInCombat(performer))
            {
                CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " could not act on emote from owner: " + owner + " because the Massif is reporting the owner is is in COMBAT.");
            }
            else if (ai_lib.aiIsDead(self))
            {
                CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " could not act on emote from owner: " + owner + " because the Massif is reporting it is DEAD.");
            }
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("tcg", "Massif Pet Object: " + self + " of controller object: " + controller + " saw an emote from owner: " + owner + " and the Massif will not act on the emote.");
        if (emote.equals("pointat") || emote.equals("tap"))
        {
            initiateMassiffAction(self, performer, house_pet.CONFUSED);
        }
        else if (emote.equals("beckon") || emote.equals("summon"))
        {
            initiateMassiffAction(self, performer, house_pet.COME);
        }
        else if (emote.startsWith("pet") || emote.equals("reassure") || emote.equals("nuzzle") || emote.equals("hug"))
        {
            initiateMassiffAction(self, performer, house_pet.GOOD);
        }
        else if (emote.equals("bonk") || emote.equals("whap") || emote.equals("scold") || emote.equals("bad") || emote.equals("slap") || emote.equals("poke") || emote.equals("bite"))
        {
            initiateMassiffAction(self, performer, house_pet.BAD);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean processSpeech(obj_id pet, obj_id speaker, String text) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            return false;
        }
        if (!isIdValid(speaker))
        {
            return false;
        }
        if (ai_lib.isAiDead(pet))
        {
            return false;
        }
        if (text == null || text.length() <= 0)
        {
            return false;
        }
        if (!hasObjVar(pet, house_pet.PARENT_OBJ_ID))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(pet, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        obj_id owner = getOwner(pet);
        if (!isValidId(owner))
        {
            return false;
        }
        if (speaker != owner)
        {
            return false;
        }
        CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + owner + " and the Massif will now debug the command.");
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        String petCommand = "";
        String petName = "";
        if (tok.hasMoreTokens())
        {
            petName = tok.nextToken();
            if (petName == null || petName.length() <= 0)
            {
                return false;
            }
            if (!(getName(pet)).equals(petName))
            {
                return false;
            }
            if (tok.hasMoreTokens())
            {
                petCommand = tok.nextToken();
            }
            if (petCommand == null || petCommand.length() <= 0)
            {
                return false;
            }
            petCommand = toLower(petCommand);
        }
        CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + owner + " and the command has passed basic debugging, continuing.");
        ai_lib.aiSetPosture(pet, POSTURE_UPRIGHT);
        if (!initiateMassiffAction(pet, speaker, petCommand))
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + owner + " but the command failed.");
            return false;
        }
        return true;
    }
    public boolean initiateMassiffAction(obj_id pet, obj_id speaker, String petCommand) throws InterruptedException
    {
        return initiateMassiffAction(pet, speaker, petCommand, true);
    }
    public boolean initiateMassiffAction(obj_id pet, obj_id speaker, String petCommand, boolean showCantHearOwner) throws InterruptedException
    {
        if (!isValidId(pet) || !exists(pet))
        {
            return false;
        }
        if (!isValidId(speaker) || !exists(speaker))
        {
            return false;
        }
        if (petCommand == null || petCommand.length() <= 0)
        {
            return false;
        }
        if (!hasObjVar(pet, house_pet.PARENT_OBJ_ID))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(pet, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (petCommand.length() > 30)
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " but the command was more than 30 characters long. Failing.");
            return false;
        }
        if (isInWorldCell(speaker))
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " but the owner was in the world so the Massif will ignore the player. Failing.");
            return false;
        }
        if (isInWorldCell(pet))
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " but the Massif was in the world!!! The Massif is now going to self destruct. Failing.");
            destroyObject(pet);
            return false;
        }
        location masterLoc = getLocation(speaker);
        location petLoc = getLocation(pet);
        if (masterLoc == null || petLoc == null)
        {
            return false;
        }
        if (getDistance(masterLoc, petLoc) > MAX_DISTANCE_FOR_COMMAND)
        {
            if (showCantHearOwner)
            {
                sendSystemMessage(speaker, SID_PET_CANT_HEAR_YOU);
            }
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " but the owner was too far away, so the Massif is ignoring his owner. Failing.");
            return false;
        }
        if (((masterLoc.cell != null) && isIdValid(masterLoc.cell)) && ((petLoc.cell == null) || (!isIdValid(petLoc.cell))))
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " but either the owner or the massif are in an invalid location. Failing.");
            return false;
        }
        blog("initiateMassiffAction - masterLoc.cell: " + masterLoc.cell);
        blog("initiateMassiffAction - petLoc.cell: " + petLoc.cell);
        String creatureTemplate = getTemplateName(pet);
        boolean goToMaster = false;
        String petAnim = "";
        int petState = POSTURE_UPRIGHT;
        int petBehaviour = getBehavior(pet);
        String petSound = "";
        faceTo(pet, speaker);
        if (petCommand.startsWith(house_pet.GOOD))
        {
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
            }
            petAnim = house_pet.MASSIF_ANIMATION_HAPPY;
        }
        else if (petCommand.startsWith(house_pet.BEG))
        {
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
            }
            petAnim = house_pet.MASSIF_ANIMATION_THREATEN;
        }
        else if (petCommand.startsWith(house_pet.CONFUSED))
        {
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
            }
            petAnim = house_pet.MASSIF_ANIMATION_CONFUSED;
        }
        else if (petCommand.startsWith(house_pet.BAD))
        {
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
            }
            petAnim = house_pet.MASSIF_ANIMATION_ASHAMED;
        }
        else if (petCommand.startsWith(house_pet.SIT))
        {
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petState = POSTURE_LYING_DOWN;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petState = POSTURE_SITTING;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petState = POSTURE_SITTING;
            }
        }
        else if (petCommand.startsWith(house_pet.TRICK))
        {
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
            }
            petAnim = "trick_" + rand(1, 2);
        }
        else if (petCommand.startsWith(house_pet.STAY))
        {
            petBehaviour = ai_lib.BEHAVIOR_SENTINEL;
        }
        else if (petCommand.startsWith(house_pet.SPEAK) || petCommand.startsWith(house_pet.BARK))
        {
            petAnim = house_pet.MASSIF_ANIMATION_VOCALIZE;
        }
        else if (petCommand.startsWith(house_pet.DOWN) || petCommand.startsWith(house_pet.SLEEP) || petCommand.startsWith(house_pet.BED))
        {
            petState = POSTURE_LYING_DOWN;
        }
        else if (petCommand.startsWith(house_pet.BREAK) || petCommand.startsWith(house_pet.POODOO) || petCommand.startsWith(house_pet.POTTY) || petCommand.startsWith(house_pet.POOP))
        {
            if (getIntObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE) == house_pet.MASSIFF_HUNGRY)
            {
                if (creatureTemplate.indexOf("nuna") > -1)
                {
                    petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
                }
                if (creatureTemplate.indexOf("massif") > -1)
                {
                    petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
                }
                if (creatureTemplate.indexOf("scurrier") > -1)
                {
                    petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
                }
                petAnim = house_pet.MASSIF_ANIMATION_ASHAMED;
            }
            else 
            {
                if (creatureTemplate.indexOf("nuna") > -1)
                {
                    petState = POSTURE_LYING_DOWN;
                }
                if (creatureTemplate.indexOf("massif") > -1)
                {
                    petState = POSTURE_SITTING;
                }
                if (creatureTemplate.indexOf("scurrier") > -1)
                {
                    petState = POSTURE_SITTING;
                }
                messageTo(pet, "resumeStanding", null, 4, false);
            }
        }
        else if (petCommand.startsWith(house_pet.DEAD) || petCommand.startsWith(house_pet.DIE) || petCommand.startsWith(house_pet.PLAYDEAD))
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " to be knocked out.  This means the Massif is playing dead and will continue to do this until another command is given.");
            setObjVar(controller, house_pet.PET_KNOCKED_OUT, true);
            petState = POSTURE_KNOCKED_DOWN;
        }
        else if (petCommand.startsWith(house_pet.COME) || petCommand.startsWith(house_pet.HEEL) || petCommand.startsWith(house_pet.HERE))
        {
            petState = POSTURE_UPRIGHT;
            petBehaviour = ai_lib.BEHAVIOR_SENTINEL;
            goToMaster = true;
        }
        else 
        {
            CustomerServiceLog("tcg", "Massif Pet Object: " + pet + " of controller object: " + controller + " heard a command from owner: " + speaker + " but the owner didn't use a known command so the owner was ignored. Failing.");
            showFlyText(pet, new string_id("npc_reaction/flytext", "alert"), 2.0f, colors.MEDIUMSLATEBLUE);
            messageTo(pet, "startLoitering", null, 5, false);
            return false;
        }
        if (petState > -1)
        {
            ai_lib.aiSetPosture(pet, petState);
        }
        if (petAnim != null && petAnim.length() > 0)
        {
            doAnimationAction(pet, petAnim);
        }
        if (petBehaviour > 0)
        {
            ai_lib.setDefaultCalmBehavior(pet, petBehaviour);
        }
        if (petSound != null && petSound.length() > 0)
        {
            playClientEffectObj(pet, petSound, speaker, "");
        }
        if (goToMaster)
        {
            messageTo(pet, "findOwner", null, 0, false);
        }
        return true;
    }
    public int findRandomMassiffPetActivity(obj_id self, dictionary params) throws InterruptedException
    {
        blog("findRandomMassiffPetActivity - init");
        String creatureTemplate = getTemplateName(self);
        boolean goToMaster = false;
        String petAnim = "";
        int petState = POSTURE_UPRIGHT;
        int petBehaviour = getBehavior(self);
        String petSound = "";
        int randNum = rand(0, 35);
        blog("findRandomMassiffPetActivity - randNum: " + randNum);
        switch (randNum)
        {
            case 0:
            petState = POSTURE_UPRIGHT;
            petAnim = "trick_" + rand(1, 2);
            break;
            case 1:
            petState = POSTURE_SITTING;
            petAnim = house_pet.MASSIF_ANIMATION_HAPPY;
            break;
            case 2:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_HAPPY;
            break;
            case 3:
            petState = POSTURE_SITTING;
            petAnim = house_pet.MASSIF_ANIMATION_CONFUSED;
            break;
            case 4:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_CONFUSED;
            break;
            case 5:
            petState = POSTURE_SITTING;
            petAnim = house_pet.MASSIF_ANIMATION_ASHAMED;
            break;
            case 6:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_ASHAMED;
            break;
            case 7:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_SCRATCH;
            break;
            case 8:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_ALERT;
            break;
            case 9:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_FIDGET;
            break;
            case 10:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_LOOK;
            break;
            case 11:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_LOOK_LEFT;
            break;
            case 12:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_LOOK_RIGHT;
            break;
            case 13:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_NERVOUS;
            break;
            case 14:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_SNIFF;
            break;
            case 15:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_STRETCH;
            break;
            case 16:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_TRICK1;
            break;
            case 17:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_TRICK2;
            break;
            case 18:
            petState = POSTURE_UPRIGHT;
            petAnim = house_pet.MASSIF_ANIMATION_VOCALIZE;
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_NUNA;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_MASSIFF;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petSound = house_pet.SOUND_GROWL_RANDOM_SCURRIER;
            }
            break;
            case 19:
            petState = POSTURE_SITTING;
            break;
            case 20:
            petState = POSTURE_LYING_DOWN;
            break;
            case 21:
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petState = POSTURE_LYING_DOWN;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petState = POSTURE_SITTING;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petState = POSTURE_SITTING;
            }
            messageTo(self, "layNunaEggs", null, 4, false);
            break;
            case 22:
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petState = POSTURE_LYING_DOWN;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petState = POSTURE_SITTING;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petState = POSTURE_SITTING;
            }
            messageTo(self, "layNunaEggs", null, 4, false);
            break;
            case 23:
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petState = POSTURE_LYING_DOWN;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petState = POSTURE_SITTING;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petState = POSTURE_SITTING;
            }
            messageTo(self, "layNunaEggs", null, 4, false);
            break;
            case 24:
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petState = POSTURE_LYING_DOWN;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petState = POSTURE_SITTING;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petState = POSTURE_SITTING;
            }
            messageTo(self, "layNunaEggs", null, 4, false);
            break;
            case 25:
            if (creatureTemplate.indexOf("nuna") > -1)
            {
                petState = POSTURE_LYING_DOWN;
            }
            if (creatureTemplate.indexOf("massif") > -1)
            {
                petState = POSTURE_SITTING;
            }
            if (creatureTemplate.indexOf("scurrier") > -1)
            {
                petState = POSTURE_SITTING;
            }
            messageTo(self, "layNunaEggs", null, 4, false);
            break;
            case 26:
            petState = POSTURE_SITTING;
            break;
            case 27:
            petState = POSTURE_LYING_DOWN;
            break;
            case 28:
            petState = POSTURE_SITTING;
            break;
            case 29:
            petState = POSTURE_LYING_DOWN;
            break;
            case 30:
            petState = POSTURE_SITTING;
            break;
            case 31:
            petState = POSTURE_LYING_DOWN;
            break;
            case 32:
            petState = POSTURE_SITTING;
            break;
            case 33:
            petState = POSTURE_LYING_DOWN;
            break;
            case 34:
            petState = POSTURE_SITTING;
            break;
            case 35:
            petState = POSTURE_LYING_DOWN;
            break;
            default:
            petState = POSTURE_SITTING;
            messageTo(self, "layNunaEggs", null, 4, false);
            break;
        }
        if (petState > -1)
        {
            ai_lib.aiSetPosture(self, petState);
        }
        if (petAnim != null && petAnim.length() > 0)
        {
            doAnimationAction(self, petAnim);
        }
        if (petBehaviour > 0)
        {
            ai_lib.setDefaultCalmBehavior(self, petBehaviour);
        }
        if (petSound != null && petSound.length() > 0)
        {
            playClientEffectObj(self, petSound, self, "");
        }
        obj_id owner = getOwner(self);
        if (isGod(owner))
        {
            messageTo(self, "findRandomMassiffPetActivity", null, 20, false);
        }
        else 
        {
            messageTo(self, "findRandomMassiffPetActivity", null, rand(900, 1800), false);
        }
        return SCRIPT_CONTINUE;
    }
    public int layNunaEggs(obj_id self, dictionary params) throws InterruptedException
    {
        blog("LAYNUNAEGGS - Enter");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            blog("LAYNUNAEGGS - no Parent_obj_id");
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            blog("LAYNUNAEGGS - controller does not exist");
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(self);
        blog("LAYNUNAEGGS - OWNER of the House Pet is: (" + owner + ")");
        if (!isIdValid(owner))
        {
            blog("LAYNUNAEGGS - Invalid Owner!");
            return SCRIPT_CONTINUE;
        }
        String petType = getTemplateName(self);
        if (petType.indexOf("nuna") > -1)
        {
            int rand = rand(1, 100);
            if (isGod(owner))
            {
                blog("LAYNUNAEGGS - Laying Eggs due to god mode!");
                rand = 1;
            }
            if (rand > 10)
            {
                ai_lib.aiSetPosture(self, POSTURE_UPRIGHT);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            blog("LAYNUNAEGGS - I'm not a Nuna - Standing Up and Exiting");
            ai_lib.aiSetPosture(self, POSTURE_UPRIGHT);
            return SCRIPT_CONTINUE;
        }
        if (isValidId(owner))
        {
            blog("LAYNUNAEGGS - Valid Owner - continue with Egg Creation");
            if (!hasObjVar(self, house_pet.MASSIFF_EGG_COUNT) || getIntObjVar(self, house_pet.MASSIFF_EGG_COUNT) <= 50)
            {
                blog("LAYNUNAEGGS - We have enough room for eggs.");
                location poopLocation = getLocation(self);
                poopLocation.z += .10f;
                blog("LAYNUNAEGGS - Creating Egg Item");
                obj_id poodoo = static_item.createNewItemFunction(house_pet.MASSIFF_POO_STATIC_ITEM, getContainedBy(self), poopLocation);
                if (isValidId(poodoo) && exists(poodoo))
                {
                    setOwner(poodoo, owner);
                    if (!hasObjVar(self, house_pet.MASSIFF_EGG_COUNT))
                    {
                        setObjVar(self, house_pet.MASSIFF_EGG_COUNT, 1);
                    }
                    else 
                    {
                        int count = getIntObjVar(self, house_pet.MASSIFF_EGG_COUNT);
                        setObjVar(self, house_pet.MASSIFF_EGG_COUNT, ++count);
                    }
                }
            }
        }
        ai_lib.aiSetPosture(self, POSTURE_UPRIGHT);
        return SCRIPT_CONTINUE;
    }
    public int handleSetPetName(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (sui.hasPid(player, house_pet.HOUSE_PET_MENU_PID))
        {
            int pid = sui.getPid(player, house_pet.HOUSE_PET_MENU_PID);
            forceCloseSUIPage(pid);
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            sui.removePid(player, house_pet.HOUSE_PET_MENU_PID);
            return SCRIPT_CONTINUE;
        }
        String petName = sui.getInputBoxText(params);
        if (petName == null || petName.length() <= 0 || isNameReserved(petName) || petName.indexOf(" ") > -1)
        {
            sendSystemMessage(player, SID_OBSCENE);
            int pid = sui.inputbox(self, player, "@tcg:pet_name_d", sui.OK_CANCEL, "@tcg:pet_name_t", sui.INPUT_NORMAL, null, "handleSetPetName", null);
            sui.setPid(player, pid, house_pet.HOUSE_PET_MENU_PID);
            return SCRIPT_CONTINUE;
        }
        if (petName.length() > 40)
        {
            petName = petName.substring(0, 39);
        }
        setName(self, petName);
        setObjVar(controller, house_pet.PET_NAME, petName);
        sendSystemMessage(player, SID_PET_RENAMED);
        return SCRIPT_CONTINUE;
    }
    public int findOwner(obj_id self, dictionary params) throws InterruptedException
    {
        blog("findOwner - Init");
        obj_id owner = getOwner(self);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        blog("findOwner - Owner found");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (isInWorldCell(owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (isInWorldCell(self))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        location pathToLoc = getLocation(owner);
        location myLoc = getLocation(self);
        if (getDistance(pathToLoc, myLoc) > MAX_DISTANCE_FOR_COMMAND)
        {
            sendSystemMessage(owner, SID_PET_CANT_HEAR_YOU);
            return SCRIPT_CONTINUE;
        }
        if (((pathToLoc.cell != null) && isIdValid(pathToLoc.cell)) && ((myLoc.cell == null) || (!isIdValid(myLoc.cell))))
        {
            return SCRIPT_CONTINUE;
        }
        if (pathToLoc.x < myLoc.x)
        {
            pathToLoc.x += 0.5f;
        }
        else 
        {
            pathToLoc.x -= 0.5f;
        }
        if (pathToLoc.z < myLoc.z)
        {
            pathToLoc.z += 0.5f;
        }
        else 
        {
            pathToLoc.z -= 0.5f;
        }
        pathTo(self, pathToLoc);
        removeObjVar(controller, house_pet.PET_KNOCKED_OUT);
        messageTo(self, "makeHappySounds", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int feedingTime(obj_id self, dictionary params) throws InterruptedException
    {
        blog("feedingTime - init");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("feedingTime - validation done");
        location pathToLoc = getLocation(controller);
        location myLoc = getLocation(self);
        if (pathToLoc.x < myLoc.x)
        {
            pathToLoc.x += 0.5f;
        }
        else 
        {
            pathToLoc.x -= 0.5f;
        }
        if (pathToLoc.z < myLoc.z)
        {
            pathToLoc.z += 0.5f;
        }
        else 
        {
            pathToLoc.z -= 0.5f;
        }
        blog("feedingTime - pathing");
        pathTo(self, pathToLoc);
        messageTo(self, "eatTheFood", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int eatTheFood(obj_id self, dictionary params) throws InterruptedException
    {
        blog("eatTheFood - init");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("eatTheFood - controller found");
        if (!hasObjVar(self, house_pet.PET_FOOD_OID) && !hasObjVar(controller, house_pet.MASSIFF_FOOD_OBJ))
        {
            return SCRIPT_CONTINUE;
        }
        blog("eatTheFood - pet & controller food objid found");
        obj_id food = getObjIdObjVar(controller, house_pet.MASSIFF_FOOD_OBJ);
        if (!isValidId(food) || !exists(food))
        {
            food = getObjIdObjVar(self, house_pet.PET_FOOD_OID);
            if (!isValidId(food) || !exists(food))
            {
                return SCRIPT_CONTINUE;
            }
        }
        blog("eatTheFood - controller food objid found");
        String creatureTemplate = getTemplateName(self);
        if (creatureTemplate.indexOf("nuna") > -1)
        {
            playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_NUNA, self, "");
        }
        if (creatureTemplate.indexOf("massif") > -1)
        {
            playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_MASSIFF, self, "");
        }
        if (creatureTemplate.indexOf("scurrier") > -1)
        {
            playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_SCURRIER, self, "");
        }
        doAnimationAction(self, house_pet.MASSIF_ANIMATION_EAT);
        destroyObject(food);
        removeObjVar(controller, house_pet.MASSIFF_FOOD_OBJ);
        removeObjVar(self, house_pet.PET_FOOD_OID);
        messageTo(self, "startLoitering", null, 5, false);
        setObjVar(controller, house_pet.MASSIFF_LAST_FED, getCalendarTime());
        setObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE, house_pet.MASSIFF_FED);
        utils.removeScriptVar(self, house_pet.BEING_FED);
        CustomerServiceLog("tcg", "Massif Pet: " + self + " has been fed by owner: " + getOwner(controller));
        return SCRIPT_CONTINUE;
    }
    public boolean updateMassiffPet(obj_id pet) throws InterruptedException
    {
        blog("updateMassiffPet - Init");
        if (!isValidId(pet) || !exists(pet))
        {
            return false;
        }
        obj_id controller = getObjIdObjVar(pet, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return false;
        }
        if (!hasObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE))
        {
            setObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE, house_pet.MASSIFF_HUNGRY);
        }
        int currentPhase = getIntObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE);
        blog("updateMassiffPet - currentPhase: " + currentPhase);
        if (currentPhase < house_pet.MASSIFF_FED)
        {
            blog("updateMassiffPet - currentPhase WAS -1");
            currentPhase = house_pet.MASSIFF_FED;
        }
        else if (currentPhase >= house_pet.MASSIFF_DEAD)
        {
            blog("updateMassiffPet - is MASSIFF_DEAD of higher");
            setObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE, house_pet.MASSIFF_DEAD);
            return true;
        }
        boolean justBorn = false;
        int currentGameTime = getCalendarTime();
        int updateWeek = house_pet.getUpdateWeekly(controller);
        if (!hasObjVar(controller, house_pet.MASSIFF_LAST_FED))
        {
            justBorn = true;
        }
        int lastFed = getIntObjVar(controller, house_pet.MASSIFF_LAST_FED);
        if (currentGameTime < (lastFed + updateWeek))
        {
            CustomerServiceLog("tcg", "Massif Pet: " + pet + " has received an update before the request too soon. Bailing out.");
            blog("updateMassiffPet - Last Fed Timer: " + (lastFed + updateWeek) + " currentGameTime: " + currentGameTime);
            return true;
        }
        if (currentGameTime >= (lastFed + updateWeek) && currentGameTime < (lastFed + (updateWeek * 2)) && !utils.hasScriptVar(pet, house_pet.BEING_FED))
        {
            CustomerServiceLog("tcg", "Massif Pet: " + pet + " is hungry but has time before being DEAD. Exiting.");
            if (currentPhase != house_pet.MASSIFF_HUNGRY)
            {
                CustomerServiceLog("tcg", "Massif Pet: " + pet + " had a current phase of: " + currentPhase + " so we will correct that to make the Massif HUNGRY.");
                setObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE, house_pet.MASSIFF_HUNGRY);
            }
            obj_id owner = getOwner(controller);
            if (canSeeOwner(owner, pet) && !utils.hasScriptVar(pet, "whining"))
            {
                messageTo(pet, "startWhining", null, 3, false);
                initiateMassiffAction(pet, owner, house_pet.BAD);
                String petType = getTemplateName(pet);
                if (petType.indexOf("massif") > -1)
                {
                    playClientEffectObj(pet, house_pet.MASSIFF_HUNGRY_EMOTE, pet, "");
                }
                if (petType.indexOf("nuna") > -1)
                {
                    playClientEffectObj(pet, house_pet.NUNA_HUNGRY_EMOTE, pet, "");
                }
                if (petType.indexOf("scurrier") > -1)
                {
                    playClientEffectObj(pet, house_pet.SCURRIER_HUNGRY_EMOTE, pet, "");
                }
            }
            CustomerServiceLog("tcg", "Massif Pet: " + pet + " is currently hungry but still has time before moving to dead. Bailing out.");
            return true;
        }
        if (!justBorn && currentGameTime >= (lastFed + updateWeek * 2))
        {
            CustomerServiceLog("tcg", "Massif Pet: " + pet + " is supposed to be dead because the player has not fed it in over 2 weeks.");
            if (currentPhase != house_pet.MASSIFF_DEAD)
            {
                CustomerServiceLog("tcg", "Massif Pet: " + pet + " had a current phase of: " + currentPhase + " but because it has been 2 or more weeks since being fed, we will correct that to make the Massif DEAD.");
                setObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE, house_pet.MASSIFF_DEAD);
            }
            ai_lib.aiSetPosture(pet, POSTURE_KNOCKED_DOWN);
            setObjVar(controller, house_pet.PET_KNOCKED_OUT, true);
            CustomerServiceLog("tcg", "Massif Pet: " + pet + " is currently dead due to neglect.");
            return true;
        }
        if (justBorn)
        {
            CustomerServiceLog("tcg", "Massif Pet: " + pet + " was recently spawned or has never been fed.");
            obj_id owner = getOwner(controller);
            if (canSeeOwner(owner, pet) && !utils.hasScriptVar(pet, "whining"))
            {
                messageTo(pet, "startWhining", null, 3, false);
                initiateMassiffAction(pet, owner, house_pet.BAD);
                String petType = getTemplateName(pet);
                if (petType.indexOf("massif") > -1)
                {
                    playClientEffectObj(pet, house_pet.MASSIFF_HUNGRY_EMOTE, pet, "");
                }
                if (petType.indexOf("nuna") > -1)
                {
                    playClientEffectObj(pet, house_pet.NUNA_HUNGRY_EMOTE, pet, "");
                }
                if (petType.indexOf("scurrier") > -1)
                {
                    playClientEffectObj(pet, house_pet.SCURRIER_HUNGRY_EMOTE, pet, "");
                }
            }
            return true;
        }
        CustomerServiceLog("tcg", "Massif Pet: " + pet + " is currently in some unknown state: " + currentPhase + " report this to design asap.");
        return false;
    }
    public boolean canSeeOwner(obj_id owner, obj_id pet) throws InterruptedException
    {
        if (!isValidId(owner) || !exists(owner))
        {
            return false;
        }
        if (!isValidId(pet) || !exists(pet))
        {
            return false;
        }
        if (isInWorldCell(owner))
        {
            return false;
        }
        if (isInWorldCell(pet))
        {
            destroyObject(pet);
            return false;
        }
        location ownerLoc = getLocation(owner);
        location myLoc = getLocation(pet);
        if (ownerLoc.cell != myLoc.cell)
        {
            return false;
        }
        if (getDistance(ownerLoc, myLoc) > MAX_DISTANCE_FOR_COMMAND)
        {
            sendSystemMessage(owner, SID_PET_CANT_HEAR_YOU);
            return false;
        }
        CustomerServiceLog("tcg", "Massif Pet: " + pet + " found its owner in the same cell.");
        return true;
    }
    public int startWhining(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE))
        {
            return SCRIPT_CONTINUE;
        }
        int currentPhase = getIntObjVar(controller, house_pet.MASSIFF_CURRENT_PHASE);
        if (currentPhase < 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, house_pet.BEING_FED) || currentPhase == house_pet.MASSIFF_FED)
        {
            utils.removeScriptVar(self, "whining");
            return SCRIPT_CONTINUE;
        }
        obj_id owner = getOwner(controller);
        if (!isValidId(owner) || !exists(owner))
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("tcg", "Massif Pet: " + self + " is whining that it is hungry because the owner is in same cell.");
        utils.setScriptVar(self, "whining", true);
        initiateMassiffAction(self, owner, house_pet.BAD, false);
        String petType = getTemplateName(self);
        if (petType.indexOf("massif") > -1)
        {
            playClientEffectObj(self, house_pet.MASSIFF_HUNGRY_EMOTE, self, "");
        }
        if (petType.indexOf("nuna") > -1)
        {
            playClientEffectObj(self, house_pet.NUNA_HUNGRY_EMOTE, self, "");
        }
        if (petType.indexOf("scurrier") > -1)
        {
            playClientEffectObj(self, house_pet.SCURRIER_HUNGRY_EMOTE, self, "");
        }
        if (!hasMessageTo(self, "startWhining"))
        {
            messageTo(self, "startWhining", null, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int makeHappySounds(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isFollowing(self))
        {
            ai_lib.aiStopFollowing(self);
        }
        blog("makeHappySounds - init");
        String creatureTemplate = getTemplateName(self);
        if (creatureTemplate.indexOf("nuna") > -1)
        {
            playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_NUNA, self, "");
        }
        if (creatureTemplate.indexOf("massif") > -1)
        {
            playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_MASSIFF, self, "");
        }
        if (creatureTemplate.indexOf("scurrier") > -1)
        {
            playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_SCURRIER, self, "");
        }
        doAnimationAction(self, house_pet.MASSIF_ANIMATION_VOCALIZE);
        return SCRIPT_CONTINUE;
    }
    public int handlePetColorize(obj_id self, dictionary params) throws InterruptedException
    {
        blog("handlePetColorize - Init");
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("handlePetColorize - controller found");
        int idx = sui.getColorPickerIndex(params);
        if (idx < 0)
        {
            idx = 0;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        custom_var myVar = getCustomVarByName(self, hue.INDEX_1);
        if (myVar != null && myVar.isPalColor())
        {
            palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
            pcVar.setValue(idx);
            setObjVar(controller, house_pet.PET_HUE_INDEX1, idx);
        }
        return SCRIPT_CONTINUE;
    }
    public int resetVariablesOnMassiff(obj_id self, dictionary params) throws InterruptedException
    {
        blog("resetVariablesOnMassiff - init");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        blog("resetVariablesOnMassiff - initial validation completed");
        if (hasObjVar(controller, house_pet.PET_HUE_INDEX1))
        {
            int color = getIntObjVar(controller, house_pet.PET_HUE_INDEX1);
            if (color >= 0)
            {
                hue.setColor(self, "/private/index_color_1", color);
            }
        }
        if (hasObjVar(controller, house_pet.PET_NAME))
        {
            String name = getStringObjVar(controller, house_pet.PET_NAME);
            if (name == null || name.length() <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            setName(self, name);
        }
        return SCRIPT_CONTINUE;
    }
    public int startLoitering(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasMessageTo(self, "startLoitering"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, house_pet.PET_MOVING))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ai.defaultCalmBehavior") && getIntObjVar(self, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_WANDER)
        {
            return SCRIPT_CONTINUE;
        }
        ai_lib.aiSetPosture(self, POSTURE_UPRIGHT);
        setMovementWalk(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_WANDER);
        return SCRIPT_CONTINUE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int resumeStanding(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.aiSetPosture(self, POSTURE_UPRIGHT);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
    public int snackTime(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Scurrier informed of SnackTime");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            return SCRIPT_CONTINUE;
        }
        location pathToLoc = getLocation(controller);
        location myLoc = getLocation(self);
        if (pathToLoc.x < myLoc.x)
        {
            pathToLoc.x += 0.5f;
        }
        else 
        {
            pathToLoc.x -= 0.5f;
        }
        if (pathToLoc.z < myLoc.z)
        {
            pathToLoc.z += 0.5f;
        }
        else 
        {
            pathToLoc.z -= 0.5f;
        }
        pathTo(self, pathToLoc);
        messageTo(self, "eatTheSnack", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int eatTheSnack(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Scurrier is Eating the snack");
        if (!hasObjVar(self, house_pet.PARENT_OBJ_ID))
        {
            LOG("sissynoid", "No Parent Obj Id");
            return SCRIPT_CONTINUE;
        }
        obj_id controller = getObjIdObjVar(self, house_pet.PARENT_OBJ_ID);
        if (!isValidId(controller) || !exists(controller))
        {
            LOG("sissynoid", "Invalid Controller Obj Id");
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, house_pet.PET_SNACK_OID) && !hasObjVar(controller, house_pet.SCURRIER_SNACK_OBJ))
        {
            LOG("sissynoid", "No Pet Snack ID - or No controller Snack ID Objvar");
            return SCRIPT_CONTINUE;
        }
        obj_id snack = getObjIdObjVar(controller, house_pet.SCURRIER_SNACK_OBJ);
        if (!isValidId(snack) || !exists(snack))
        {
            LOG("sissynoid", "Snack is invalid - it may have been deleted before eaten.");
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, house_pet.SOUND_GROWL_RANDOM_SCURRIER, self, "");
        doAnimationAction(self, house_pet.MASSIF_ANIMATION_EAT);
        scurrierPetEatingSurprise(controller, self);
        destroyObject(snack);
        removeObjVar(controller, house_pet.SCURRIER_SNACK_OBJ);
        removeObjVar(self, house_pet.PET_SNACK_OID);
        messageTo(self, "startLoitering", null, 5, false);
        setObjVar(controller, house_pet.SCURRIER_SNACK_LAST_FED, getCalendarTime());
        utils.removeScriptVar(self, house_pet.BEING_FED);
        CustomerServiceLog("tcg", "Scurrier Pet: " + self + " has been given a snack by owner: " + getOwner(controller));
        return SCRIPT_CONTINUE;
    }
    public void scurrierPetEatingSurprise(obj_id controller, obj_id creature) throws InterruptedException
    {
        if (!isValidId(controller) || !exists(controller))
        {
            return;
        }
        if (!isValidId(creature) || !exists(creature))
        {
            return;
        }
        int rollChance = rand(1, 100);
        if (isGod(getOwner(controller)))
        {
            rollChance = 100;
        }
        if (rollChance > 60)
        {
            location feedDishLoc = getLocation(controller);
            feedDishLoc.y = feedDishLoc.y + 0.05f;
            int rollItem = rand(1, 4);
            obj_id spawnedItem = obj_id.NULL_ID;
            switch (rollItem)
            {
                case 1:
                spawnedItem = static_item.createNewItemFunction(house_pet.SCURRIER_ITEM_01, getContainedBy(controller), feedDishLoc);
                break;
                case 2:
                spawnedItem = static_item.createNewItemFunction(house_pet.SCURRIER_ITEM_02, getContainedBy(controller), feedDishLoc);
                break;
                case 3:
                spawnedItem = static_item.createNewItemFunction(house_pet.SCURRIER_ITEM_03, getContainedBy(controller), feedDishLoc);
                break;
                case 4:
                spawnedItem = static_item.createNewItemFunction(house_pet.SCURRIER_ITEM_04, getContainedBy(controller), feedDishLoc);
                break;
                default:
                CustomerServiceLog("tcg", "Scurrier Pet(" + creature + ") failed to spawn a junk combine item. Controller(" + controller + ") - please contact a developer should this trend continue.");
                break;
            }
            if (!isValidId(spawnedItem) || !exists(spawnedItem))
            {
                CustomerServiceLog("tcg", "Scurrier Pet(" + creature + ") failed to spawn a junk combine item. Controller(" + controller + ") - please contact a developer should this trend continue.");
            }
            else 
            {
                setOwner(spawnedItem, getOwner(controller));
                CustomerServiceLog("tcg", "Scurrier Pet(" + creature + ") spawned item(" + spawnedItem + ") at Controller(" + controller + ") - owner(" + getOwner(controller) + ").");
            }
        }
    }
}
