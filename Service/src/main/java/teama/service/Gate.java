package teama.service;

/**
 * Gate class
 * @author jaksurma
 */
public class Gate {
   public Gate(int number, double lat, double lon) {
       this.number = number;
       this.lat = lat;
       this.lon = lon;
   }
   
   public int getNumber() {
       return number;
   }
   
   public double getLat() {
       return lat;
   }
   
   public double getLon() {
       return lon;
   }
   
   private final int number;
   private final double lat;
   private final double lon;
}
