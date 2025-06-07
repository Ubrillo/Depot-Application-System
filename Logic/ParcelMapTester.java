package Logic;

public class ParcelMapTester{
    public static void main(String args[]){
        Parcel p1 = new Parcel("cxx", 2, 234, "1X1X2");
        Parcel p2 = new Parcel("yxx", 2, 234, "1XdX2");

        ParcelMap pm = new ParcelMap();
        pm.addParcelToWaitingList(p1);
        pm.addParcelToWaitingList(p2);
        System.out.println(pm.toString());
    }
}