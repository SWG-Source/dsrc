package script.systems.jedi;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.force_rank;
import script.library.pet_lib;

public class jedi_enclave_gate_keeper extends script.base_script
{
    public jedi_enclave_gate_keeper()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if ((isPlayer(item)) && (force_rank.isForceRanked(item)) && (force_rank.isPlayersEnclave(self, item)))
        {
            return SCRIPT_OVERRIDE;
        }
        else if ((!isPlayer(item)) && (force_rank.canForceRankedPetEnterEnclave(item, self)))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        force_rank.refreshAllRoomPermissions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        force_rank.refreshAllRoomPermissions(self);
        return SCRIPT_CONTINUE;
    }
}
