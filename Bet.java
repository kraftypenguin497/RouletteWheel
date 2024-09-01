public class Bet {
   private String type;
   private int amount;
   private int number; // Only applicable for number bets
   public Bet(String type, int amount) {
       this.type = type;
       this.amount = amount;
   }
   public Bet(String type, int amount, int number) {
       this.type = type;
       this.amount = amount;
       this.number = number;
   }
   public String getType() {
       return type;
   }
   public int getAmount() {
       return amount;
   }
   public int getNumber() {
       return number;
   }
}
