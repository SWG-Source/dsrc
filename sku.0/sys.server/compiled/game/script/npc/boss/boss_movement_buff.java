package script.npc.boss;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;

public class boss_movement_buff extends script.base_script
{
    public boss_movement_buff()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "boss_armor_break_immunity");
        buff.applyBuff(self, "boss_snare_immunity");
        buff.applyBuff(self, "boss_root_immunity");
        buff.applyBuff(self, "boss_mez_immunity");
        buff.applyBuff(self, "boss_slow_immunity");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "boss_armor_break_immunity");
        buff.applyBuff(self, "boss_snare_immunity");
        buff.applyBuff(self, "boss_root_immunity");
        buff.applyBuff(self, "boss_mez_immunity");
        buff.applyBuff(self, "boss_slow_immunity");
        return SCRIPT_CONTINUE;
    }
}
