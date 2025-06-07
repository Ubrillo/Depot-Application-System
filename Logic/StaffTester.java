package Logic;
import java.util.ArrayList;
public class StaffTester {
    public static void main(String args[]) {
        Depot depot = new Depot();
        Staff operator = new Staff("operator", "1234", depot);

        operator.loadParcelData();
        operator.loadCustomerData();

        operator.addParcelToWaitingList("X888", "8", "8", "1","2","3");
        operator.addCustomerToQueue("Jemmy", "Albert", "X888");

        double money = operator.processAParcel("X888");
        System.out.println(operator.viewParcel("X888"));

//
//        operator.AddParcelToWaitingList("C201", "8", "8", "1","2","3");
//        operator.addCustomerToQueue("Jemmy", "Albert", "C201");
//
//        operator.AddParcelToWaitingList("C203", "8", "8", "1","2","3");
//        operator.addCustomerToQueue("Jemmy", "Albert", "C203");
//
//        operator.processACustomer("Jemmy", "Albert");

//        operator.removeACustomerFromQueue("Jemmy", "Albert");
        //String result = operator.viewLog();
        //operator.generateReport();
//

    }
}
