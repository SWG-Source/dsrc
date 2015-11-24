package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.callable;
import script.library.pet_lib;
import script.library.utils;
import script.library.chat;
import script.library.create;

public class mount_vendor extends script.base_script
{
    public mount_vendor()
    {
    }
    public static final String MOUNT_VENDOR_CONVO = "beta/mount_vendor";
    public static final int EXPIRATION_TIME = 60 * 60 * 24 * 4;
    public static final int MOUNT_EXPIRATION_TIME = 60 * 60 * 24 * 7;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        persistObject(self);
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(self, "ai.diction", "townperson");
        setObjVar(self, "mount_vendor.create", (getGameTime() + EXPIRATION_TIME));
        setName(self, "Travelling Mount Merchant");
        messageTo(self, "expireAndCleanup", null, EXPIRATION_TIME, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int expirationTime = getIntObjVar(self, "mount_vendor.create");
        if (getGameTime() > expirationTime)
        {
            messageTo(self, "expireAndCleanup", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int expireAndCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        chat.setGoodMood(self);
        faceToBehavior(self, speaker);
        string_id greeting = new string_id(MOUNT_VENDOR_CONVO, "start");
        string_id response[] = new string_id[2];
        response[0] = new string_id(MOUNT_VENDOR_CONVO, "yes");
        response[1] = new string_id(MOUNT_VENDOR_CONVO, "no");
        npcStartConversation(speaker, self, MOUNT_VENDOR_CONVO, greeting, response);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if (!convo.equals(MOUNT_VENDOR_CONVO))
        {
            return SCRIPT_CONTINUE;
        }
        npcRemoveConversationResponse(player, response);
        if ((response.getAsciiId()).equals("yes"))
        {
            npcRemoveConversationResponse(player, new string_id(MOUNT_VENDOR_CONVO, "no"));
            string_id message = new string_id(MOUNT_VENDOR_CONVO, "choose");
            npcSpeak(player, message);
            string_id[] responses = new string_id[8];
            responses[0] = new string_id(MOUNT_VENDOR_CONVO, "carrion_spat");
            responses[1] = new string_id(MOUNT_VENDOR_CONVO, "kaadu");
            responses[2] = new string_id(MOUNT_VENDOR_CONVO, "dewback");
            responses[3] = new string_id(MOUNT_VENDOR_CONVO, "bol");
            responses[4] = new string_id(MOUNT_VENDOR_CONVO, "falumpaset");
            responses[5] = new string_id(MOUNT_VENDOR_CONVO, "brackaset");
            npcSetConversationResponses(player, responses);
            return SCRIPT_CONTINUE;
        }
        else if ((response.getAsciiId()).equals("no"))
        {
            npcRemoveConversationResponse(player, new string_id(MOUNT_VENDOR_CONVO, "yes"));
            string_id message = new string_id(MOUNT_VENDOR_CONVO, "goodbye");
            npcSpeak(player, message);
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        String mountType = response.getAsciiId();
        String diction = "";
        if (mountType.equals("carrion_spat"))
        {
            mountType = "carrion_spat";
        }
        else if (mountType.equals("kaadu"))
        {
            mountType = "kaadu_motley";
        }
        else if (mountType.equals("dewback"))
        {
            mountType = "lesser_dewback";
        }
        else if (mountType.equals("bol"))
        {
            mountType = "bol_lesser_plains";
        }
        else if (mountType.equals("falumpaset"))
        {
            mountType = "falumpaset_plodding";
        }
        else if (mountType.equals("brackaset"))
        {
            mountType = "brackaset_lowlands";
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        location spawnLoc = getLocation(self);
        spawnLoc.x += 2;
        spawnLoc.z += 2;
        obj_id mount = create.object(mountType, spawnLoc);
        if (isValidId(mount))
        {
            if (!hasScript(player, "ai.pet_master"))
            {
                attachScript(player, "ai.pet_master");
            }
            if (!createNewMount(player, mount))
            {
                destroyObject(mount);
            }
            else 
            {
                if (getMountsEnabled())
                {
                    if (couldPetBeMadeMountable(mount) == 0)
                    {
                        if (makePetMountable(mount))
                        {
                            obj_id petControlDevice = callable.getCallableCD(mount);
                            setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "For some reason, the creature spawned can NOT be turned into a mount. Might be out of SCALE, could be several other things.");
                        sendSystemMessageTestingOnly(player, "Anyhow, try making a mount using the REAL way instead of using a fake-out shortcut method. If you've got GOD access, it shouldn't take *much* longer.");
                        sendSystemMessageTestingOnly(player, "And no writing up bugs on a broken TEST script.");
                    }
                }
                string_id message = new string_id(MOUNT_VENDOR_CONVO, "ok");
                npcSpeak(player, message);
                npcEndConversation(player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean createNewMount(obj_id master, obj_id pet) throws InterruptedException
    {
        String creatureName = ai_lib.getCreatureName(pet);
        if (creatureName == null || creatureName.equals(""))
        {
            return false;
        }
        int petSpecies = ai_lib.aiGetSpecies(pet);
        if (petSpecies == -1)
        {
            return false;
        }
        if (callable.hasCallable(master, callable.CALLABLE_TYPE_RIDEABLE))
        {
            return false;
        }
        int petType = pet_lib.getPetType(pet);
        if (!pet_lib.hasMaxStoredPetsOfType(master, petType))
        {
            if (!callable.hasCallableCD(pet))
            {
                obj_id datapad = utils.getPlayerDatapad(master);
                if (!isIdValid(datapad))
                {
                    return false;
                }
                String controlTemplate = "object/intangible/pet/" + utils.dataTableGetString(create.CREATURE_TABLE, creatureName, "template");
                if (!controlTemplate.endsWith(".iff"))
                {
                    controlTemplate = pet_lib.PET_CTRL_DEVICE_TEMPLATE;
                }
                obj_id petControlDevice = createObject(controlTemplate, datapad, "");
                if (!isIdValid(petControlDevice))
                {
                    petControlDevice = createObject(pet_lib.PET_CTRL_DEVICE_TEMPLATE, datapad, "");
                    if (!isIdValid(petControlDevice))
                    {
                        sendSystemMessage(master, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
                        return false;
                    }
                }
                pet_lib.setUpPetControlDevice(petControlDevice, pet);
                callable.setCallableLinks(master, petControlDevice, pet);
                setObjVar(petControlDevice, "mount_vendor.create", (getGameTime() + MOUNT_EXPIRATION_TIME));
                messageTo(petControlDevice, "expireAndCleanup", null, MOUNT_EXPIRATION_TIME, true);
                attachScript(petControlDevice, "beta.free_mount");
            }
            else 
            {
                callable.setCallableLinks(master, callable.getCallableCD(pet), pet);
            }
            dictionary params = new dictionary();
            params.put("pet", pet);
            params.put("master", master);
            messageTo(pet, "handleAddMaster", params, 1, false);
            return true;
        }
        sendSystemMessage(master, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
        return false;
    }
}
