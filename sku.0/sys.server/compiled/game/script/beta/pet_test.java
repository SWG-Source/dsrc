package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.pet_lib;
import script.library.colors;
import script.ai.ai_combat;

public class pet_test extends script.base_script
{
    public pet_test()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "Pet test ready.");
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location playerLocation = getLocation(self);
        if (text.equals("beginpetdemo"))
        {
            petTest(self);
        }
        else if (text.equals("endpetdemo"))
        {
            endPetTest(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void petTest(obj_id self) throws InterruptedException
    {
        grantSkill(self, "outdoors_creaturehandler_master");
        grantSkill(self, "outdoors_creaturehandler_support_04");
        grantSkill(self, "outdoors_creaturehandler_healing_04");
        grantSkill(self, "outdoors_creaturehandler_training_04");
        grantSkill(self, "outdoors_creaturehandler_taming_04");
        grantSkill(self, "outdoors_creaturehandler_taming_03");
        grantSkill(self, "outdoors_creaturehandler_training_03");
        grantSkill(self, "outdoors_creaturehandler_healing_03");
        grantSkill(self, "outdoors_creaturehandler_support_03");
        grantSkill(self, "outdoors_creaturehandler_training_02");
        grantSkill(self, "outdoors_creaturehandler_taming_02");
        grantSkill(self, "outdoors_creaturehandler_healing_02");
        grantSkill(self, "outdoors_creaturehandler_support_02");
        grantSkill(self, "outdoors_creaturehandler_taming_01");
        grantSkill(self, "outdoors_creaturehandler_training_01");
        grantSkill(self, "outdoors_creaturehandler_healing_01");
        grantSkill(self, "outdoors_creaturehandler_support_01");
        grantSkill(self, "outdoors_creaturehandler_novice");
        obj_id pet = create.object("bantha", getLocation(self));
        attachScript(pet, "ai.pet_advance");
        pet_lib.makePet(pet, self);
        showFlyTextPrivate(pet, self, new string_id("npc_reaction/flytext", "success"), 1.5f, colors.FORESTGREEN);
        setObjVar(pet, "ai.pet.commandList" + pet_lib.COMMAND_FOLLOW, "bob follow me");
        setObjVar(pet, "ai.pet.commandList" + pet_lib.COMMAND_ATTACK, "bob attack");
        setObjVar(pet, "ai.pet.commandList" + pet_lib.COMMAND_TRICK_1, "bob do a trick");
        setObjVar(self, "pet_test.pet_id", pet);
        sendSystemMessage(self, "Ready for pet demonstration.", null);
    }
    public void endPetTest(obj_id self) throws InterruptedException
    {
        obj_id pet = getObjIdObjVar(self, "pet_test.pet_id");
        if (!isIdValid(pet))
        {
            return;
        }
        pet_lib.removeFromPetList(pet);
        messageTo(self, "destroyPet", null, 2.f, true);
    }
    public int destroyPet(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id pet = getObjIdObjVar(self, "pet_test.pet_id");
        if (!isIdValid(pet))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(pet);
        sendSystemMessage(self, "Pet demonstration complete.", null);
        return SCRIPT_CONTINUE;
    }
}
