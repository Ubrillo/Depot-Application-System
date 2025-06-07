package Logic;

public class CustomersQueueTester {
    public static void main(String args[]){
        Customer c1 = new Customer("John", "Benji");
        Customer c2 = new Customer("Andrew", "Tunt");

        Parcel p1 = new Parcel("cxx", 2, 234, "1x1x2");
        Parcel p2 = new Parcel("yxx", 2, 234, "1XdX2");
        String p1_id = p1.getId();
        c1.addParcelToList(p1_id);
        c1.addParcelToList("yxx");

        CustomersQueue Q = new CustomersQueue();
         Q.addNewCustomer(c2);
         Q.addNewCustomer(c1);
         System.out.println(c1.getParcelList());
    }
}
