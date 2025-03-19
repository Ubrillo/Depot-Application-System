package Main;
import Logic.Depot;
import Logic.DepotController;
import Logic.Staff;
import Presentation.StaffGUI;
public class main {
    public static void main(String[] arg) {
        Staff staff = new Staff("jav", "1234", new Depot());
        StaffGUI portal = new StaffGUI(staff);
        DepotController controller = new DepotController(staff, portal);
    }
}