package script.fishing;

import script.obj_id;
import script.string_id;

public class elusive_fish extends script.base_script {

    public elusive_fish()
    {
    }

    public int OnAttach(obj_id self) throws InterruptedException {
        String name = getStringObjVar(self, "fish.name");
        setName(self, new string_id("fish_elusive_n", name));
        setCount(self, 1); // sometimes you catch a stack of 2-4 fish, but only 1 can be ~ elusive ~
        setObjVar(self, "noTrade", 1);
        setObjVar(self, "noTradeShared", 1);
        return SCRIPT_CONTINUE;
    }

}
