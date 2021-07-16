package script.theme_park.wod;

import script.dictionary;
import script.library.buff;
import script.obj_id;

public class mutant_rancor_npc extends script.base_script {

	public mutant_rancor_npc()
	{
	}

	public static final String[] BUFFS = {
			"wod_rancor_mutation",
			"wod_rancor_super_strength",
			"wod_rancor_razor_claws"
	};

	public int OnAttach(obj_id self) throws InterruptedException
	{
		messageTo(self, "doBuffs", null, 3f, false);
		return SCRIPT_CONTINUE;
	}

	public int OnExitedCombat(obj_id self) throws InterruptedException
	{
		if(!isDead(self))
		{
			buff.removeAllBuffs(self);
			messageTo(self, "doBuffs", null, 3f, false);
		}
		return SCRIPT_CONTINUE;
	}

	public int doBuffs(obj_id self, dictionary params) throws InterruptedException
	{
		buff.applyBuffWithStackCount(self, BUFFS[0], rand(1,3));
		buff.applyBuffWithStackCount(self,
				BUFFS[rand(1, BUFFS.length-1)],
				rand(1,2)
		);
		return SCRIPT_CONTINUE;
	}
}
