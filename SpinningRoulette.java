public class SpinningRoulette {
   private static int balance;
   private static final int MIN_BET = 5;
   private static final int MAX_NUMBER = 36;
   private static ArrayList<Bet> bets = new ArrayList<>();
   public static void main(String[] args) {
       JFrame frame = new JFrame("Roulette Game");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(600, 400);
       JPanel panel = new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       frame.add(panel);
       JLabel balanceLabel = new JLabel("Enter your starting balance: $");
       JTextField balanceField = new JTextField(10);
       JButton startButton = new JButton("Start Game");
       panel.add(balanceLabel);
       panel.add(balanceField);
       panel.add(startButton);
       startButton.addActionListener(new ActionListener() {
          
           public void actionPerformed(ActionEvent e) {
               try {
                   balance = Integer.parseInt(balanceField.getText());
                   balanceLabel.setText("Your current balance: $" + balance);
                   balanceField.setEditable(false);
                   startButton.setEnabled(false);
                   addBettingUI(panel, balanceLabel, frame);
               } catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(frame, "Please enter a valid starting balance.");
               }
           }
       });
       frame.setVisible(true);
   }
   private static void addBettingUI(JPanel panel, JLabel balanceLabel, JFrame frame) {
       JTextField betAmountField = new JTextField(10);
       JTextField betTypeField = new JTextField(10);
       JTextField betNumberField = new JTextField(10);
       JButton placeBetButton = new JButton("Place Bet");
       JButton spinButton = new JButton("Spin");
       panel.add(new JLabel("Enter your bet amount (minimum $" + MIN_BET + "): $"));
       panel.add(betAmountField);
       panel.add(new JLabel("Enter your bet type (number/odd/even/red/black): "));
       panel.add(betTypeField);
       panel.add(new JLabel("If betting on number, enter the number (0-36): "));
       panel.add(betNumberField);
       panel.add(placeBetButton);
       panel.add(spinButton);
       placeBetButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               try {
                   int betAmount = Integer.parseInt(betAmountField.getText());
                   if (betAmount < MIN_BET || betAmount > balance) {
                       JOptionPane.showMessageDialog(frame, "Invalid bet amount. Please enter a valid amount.");
                       return;
                   }
                   String betType = betTypeField.getText().toLowerCase();
                   if (betType.equals("number")) {
                       int number = Integer.parseInt(betNumberField.getText());
                       if (number < 0 || number > MAX_NUMBER) {
                           JOptionPane.showMessageDialog(frame, "Invalid number. Please enter a number between 0 and 36.");
                           return;
                       }
                       bets.add(new Bet(betType, betAmount, number));
                       balance -= betAmount;
                   } else {
                       bets.add(new Bet(betType, betAmount));
                       balance -= betAmount;
                   }
                   balanceLabel.setText("Your current balance: $" + balance);
                   betAmountField.setText("");
                   betTypeField.setText("");
                   betNumberField.setText("");
               } catch (NumberFormatException ex) {
                   JOptionPane.showMessageDialog(frame, "Please enter valid bet details.");
               }
           }
       });
       spinButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (balance < MIN_BET) {
                   JOptionPane.showMessageDialog(frame, "You don't have enough balance to place a bet.");
                   return;
               }
               int winningNumber = spin();
               String winningColor = (winningNumber == 0 || (winningNumber >= 1 && winningNumber <= 10) || (winningNumber >= 19 && winningNumber <= 28)) ? "red" : "black";
               String result = calculatePayouts(winningNumber, winningColor);
               JOptionPane.showMessageDialog(frame, result);
               balanceLabel.setText("Your current balance: $" + balance);
               bets.clear();
           }
       });
   }
   private static int spin() {
       Random random = new Random();
       return random.nextInt(MAX_NUMBER + 1); // Returns a random number between 0 and MAX_NUMBER (inclusive)
   }
   private static String calculatePayouts(int winningNumber, String winningColor) {
       int totalWinnings = 0;
       String result = "";
       for (Bet bet : bets) {
           int amount = bet.getAmount();
           String betType = bet.getType();
           if (betType.equals("number")) {
               int betNumber = bet.getNumber();
               if (winningNumber == betNumber) {
                   totalWinnings += amount * 35; // Payout for correct number is 35 times the bet amount
                   result += "You won $" + (amount * 35) + " by betting on number " + betNumber + ".\n";
               }
           } else if (betType.equals("odd") || betType.equals("even")) {
               if ((winningNumber % 2 == 0 && betType.equals("even")) || (winningNumber % 2 != 0 && betType.equals("odd"))) {
                   totalWinnings += amount; // Payout for correct odd/even is the same as the bet amount
                   result += "You won $" + amount + " by betting on " + betType + " numbers.\n";
               }
           } else if (betType.equals("red") || betType.equals("black")) {
               if ((winningColor.equals("red") && betType.equals("red")) || (winningColor.equals("black") && betType.equals("black"))) {
                   totalWinnings += amount; // Payout for correct color is the same as the bet amount
                   result += "You won $" + amount + " by betting on " + winningColor + " color.\n";
               }
           }
       }
       // Display result of the spin
       result += "Winning number: " + winningNumber + "\n";
       result += "Winning color: " + winningColor + "\n";
       // Display total winnings
       result += "Total Winnings: $" + totalWinnings;
       balance += totalWinnings;
       return result;
   }
}
