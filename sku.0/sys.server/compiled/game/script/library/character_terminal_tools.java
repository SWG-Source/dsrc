package script.library;

import script.*;


public class character_terminal_tools extends script.base_script
{
    public character_terminal_tools()
    {
    }

    public static obj_id CreateProgrammedCommandChipInContainer(obj_id pInv, String commandName, int programSize)
    {
        obj_id chip = makeCraftedItem("object/draft_schematic/droid/component/droid_space_memory_module_1.iff", 999.0f, pInv);
        setObjVar(chip, "programSize", programSize);
        setObjVar(chip, "strDroidCommand", commandName);
        setName(chip, new string_id("space/droid_commands", commandName + "_chipname"));
        return chip;
    }


}
