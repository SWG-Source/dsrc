package script.systems.city;

import script.obj_id;

public class senate_controller extends script.base_script
{
    public senate_controller()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        makeAllCellsPublic(self);
        setCellScripts(self);
        return SCRIPT_CONTINUE;
    }
    public void makeAllCellsPublic(obj_id senate) throws InterruptedException
    {
        String[] senateRooms = getCellNames(senate);
        obj_id cellId = null;
        for (String senateRoom : senateRooms) {
            cellId = getCellId(senate, senateRoom);
            if (!isIdValid(cellId)) {
                continue;
            }
            permissionsMakePublic(cellId);
        }
    }
    public void setCellScripts(obj_id enclave) throws InterruptedException
    {
        String[] enclaveRooms = getCellNames(enclave);
        obj_id cellId = null;
        for (String enclaveRoom : enclaveRooms) {
            cellId = getCellId(enclave, enclaveRoom);
            if (!isIdValid(cellId)) {
                continue;
            }
            attachScript(cellId, "systems.city.senate_cell");
        }
        return;
    }
}
