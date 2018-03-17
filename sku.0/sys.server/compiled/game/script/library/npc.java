package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.slots;
import script.library.hue;
import script.library.ai_lib;
import script.library.factions;
import script.library.utils;

public class npc extends script.base_script
{
    public npc()
    {
    }
    public static final String species_table = "datatables/npc_customization/species_choices2.iff";
    public static final String random_type = "datatables/npc_customization/random_type.iff";
    public static final String job_table = "datatables/npc_customization/job_table.iff";
    public static final String skill_table = "datatables/npc_customization/skill_table.iff";
    public static final String VERYEASY = "veryEasy";
    public static final String EASY = "easy";
    public static final String MEDIUM = "medium";
    public static final String HARD = "hard";
    public static final String VERYHARD = "veryHard";
    public static final String TYPE_HUTT = "hutt";
    public static final String TYPE_PIRATE = "pirate";
    public static final String TYPE_REBEL = "rebel";
    public static final String TYPE_TOWNPERSON = "townperson";
    public static final String TYPE_STORMTROOPER = "stormtrooper";
    public static final String TYPE_STORMTROOPER_LEADER = "stormtrooper_leader";
    public static final String TYPE_JAWA = "jawa";
    public static final String TYPE_TUSKEN = "tusken";
    public static final String TYPE_DROID = "droid";
    public static final String DATATABLE_NPC_CREATE = "datatables/npc/create_npc.iff";
    public static final String COL_KEY = "KEY";
    public static final String COL_TEMPLATE = "TEMPLATE";
    public static obj_id create(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB create(string type, location loc)");
        return null;
    }
    public static obj_id createNpc(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createNpc (string type, location loc)");
        return null;
    }
    public static obj_id createNpc(String type, obj_id bldg, String cellName) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createNpc (string type, obj_id bldg, string cellName)");
        return null;
    }
    public static obj_id createNpc(String type, obj_id box, float x, float z) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createNpc (string type, obj_id box, float x, float z)");
        return null;
    }
    public static obj_id createNpc(location here) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createNpc ( location here )");
        return null;
    }
    public static void hueClothes(obj_id newClothes, String clothes) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB hueClothes( obj_id newClothes, string clothes )");
    }
    public static String chooseSpecies(String type, int number, String gender) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB TO MAKE chooseSpecies ( string type, int number, string gender )");
        return null;
    }
    public static String chooseGender() throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB chooseGender ()");
        return null;
    }
    public static String chooseName(int number) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB chooseName( int number )");
        return null;
    }
    public static void dressUp(obj_id npc, String type, String gender) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB dressUp (obj_id npc, string type, string gender)");
    }
    public static void dressHair(obj_id npc, String name, String gender) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB dressHair (obj_id npc, string name, string gender)");
    }
    public static obj_id makeStormtrooperInside(obj_id bldg, String cellName) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB makeStormtrooperInside (obj_id bldg, string cellName )");
        return null;
    }
    public static void scriptAttach(obj_id npc, String type) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB scriptAttach (obj_id npc, string type)");
    }
    public static obj_id makeDroid(location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB makeDroid (location loc)");
        return null;
    }
    public static obj_id createAlienNpc(String alien, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createAlienNpc( string alien, location loc )");
        return null;
    }
    public static obj_id createBothan(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB  createBothan( string type, location loc )");
        return null;
    }
    public static obj_id createHuman(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createHuman ( string type, location loc )");
        return null;
    }
    public static obj_id createHumanMale(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB  createHumanMale ( string type, location loc )");
        return null;
    }
    public static obj_id createMoncal(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createMoncal ( string type, location loc )");
        return null;
    }
    public static obj_id createRodian(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB  createRodian( string type, location loc )");
        return null;
    }
    public static obj_id createTrandoshan(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createTrandoshan( string type, location loc )");
        return null;
    }
    public static obj_id createTwilek(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createTwilek( string type, location loc )");
        return null;
    }
    public static obj_id createWookiee(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createWookiee ( string type, location loc )");
        return null;
    }
    public static obj_id createZabrak(String type, location loc) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB createZabrak ( string type, location loc )");
        return null;
    }
    public static int findDifficulty() throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB findDifficulty ()");
        return 0;
    }
    public static void spamErrorMsg(String text) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB spamErrorMsg( string text )");
    }
    public static String getClothingTemplateName(String tableName, int column) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB getClothingTemplateName ( string tableName, int column )");
        return null;
    }
    public static obj_id setDifficulty(obj_id npc, String diff) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB setDifficulty (obj_id npc, string diff)");
        return null;
    }
    public static obj_id setDifficulty(obj_id npc, int diff) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB setDifficulty ( obj_id npc, int diff )");
        return null;
    }
    public static boolean setFactionRecruiter(obj_id npc, String faction) throws InterruptedException
    {
        debugServerConsoleMsg(getSelf(), "WARNING: SOMETHING IS STILL USING NPC.SCRIPTLIB setFactionRecruiter(obj_id npc, string faction)");
        return false;
    }
}
