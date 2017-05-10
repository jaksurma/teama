package teama.service;

public class Gate 
{
   private int number;
   private double lat;
   private double lon;
   
   public Gate(int number, double lat, double lon)
   {
       this.number = number;
       this.lat = lat;
       this.lon = lon;
   }
   
   public int getNumber()
   {
       return number;
   }
   
   public double getLat()
   {
       return lat;
   }
   
   public double getLon()
   {
       return lon;
   }
}
