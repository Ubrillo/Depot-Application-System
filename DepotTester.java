
package Logic;
public class DepotTester {
    public static void main(String args[]){
        Depot depot = new Depot();
        ParcelMap parcelList = depot.getParcelList();
        CustomersQueue queue = depot.getQueue();
        String data = "";
        data = queue.getAllCustomersDetails();
        System.out.println(data);
    }
}