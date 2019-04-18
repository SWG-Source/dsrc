package script.beta;

import script.library.player_structure;
import script.location;
import script.obj_id;

public class e3_housing extends script.base_script
{
    public e3_housing()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id deed = obj_id.NULL_ID;
        obj_id inv = getObjectInSlot(self, "inventory");
        if (inv == null)
        {
            sendSystemMessageTestingOnly(self, "Player inventory is null.");
            return SCRIPT_CONTINUE;
        }
        switch (text) {
            case "tatooine small":
                deed = createObjectOverloaded("object/tangible/deed/player_house_deed/tatooine_house_small_deed.iff", inv);
                break;
            case "naboo medium":
                deed = createObjectOverloaded("object/tangible/deed/player_house_deed/naboo_house_medium_deed.iff", inv);
                break;
            case "corellia large":
                deed = createObjectOverloaded("object/tangible/deed/player_house_deed/corellia_house_large_deed.iff", inv);
                break;
            case "tatooine guild":
                deed = createObjectOverloaded("object/tangible/deed/guild_deed/tatooine_guild_style_02_deed.iff", inv);
                break;
        }
        if (isIdValid(deed))
        {
            setObjVar(deed, player_structure.VAR_DEED_BUILDTIME, 15);
            removeObjVar(deed, player_structure.VAR_DEED_SCENE);
            sendSystemMessageTestingOnly(self, deed + " created.");
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("destroy"))
        {
            location loc = getLocation(self);
            obj_id[] objects = getObjectsInRange(loc, 30.0f);
            for (obj_id object : objects) {
                if (player_structure.isBuilding(object) || player_structure.isInstallation(object)) {
                    if (player_structure.getStructureOwnerObjId(object) == self) {
                        player_structure.destroyStructure(object);
                        sendSystemMessageTestingOnly(self, object + " destroyed.");
                    }
                }
            }
        }
        if (text.startsWith("damagestructure"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                player_structure.damageStructure(target, 100);
                debugSpeakMsg(self, "Structure damaged.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
