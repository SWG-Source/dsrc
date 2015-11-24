package script.corpse;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.pet_lib;
import script.library.utils;
import script.library.corpse;
import script.library.permissions;
import script.library.money;
import script.library.prose;
import script.library.group;
import script.library.ai_lib;
import script.library.loot;

public class corpse_fast_recycle extends script.base_script
{
    public corpse_fast_recycle()
    {
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        if (isIdValid(self))
        {
            messageTo(self, "handleIsCorpseEmpty", null, 5, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleIsCorpseEmpty(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIdValid(self) && hasScript(self, "corpse.ai_corpse") && corpse.isCorpseEmpty(self) == true)
        {
            messageTo(self, corpse.HANDLER_CORPSE_EXPIRE, null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
