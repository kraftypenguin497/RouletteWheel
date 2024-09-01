import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Roulette extends JFrame {
   private SlicedCirclePanel slicedCirclePanel;
   public Roulette() {
       setTitle("Roulette Wheel");
       setSize(1200, 1000);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLocationRelativeTo(null);
       // Create an instance of the custom JPanel and add it to the frame
       slicedCirclePanel = new SlicedCirclePanel();
       add(slicedCirclePanel, BorderLayout.CENTER);
       // Create a button to trigger the spin animation
       JButton spinButton = new JButton("Spin");
       spinButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               // Start the spin animation
               slicedCirclePanel.startAnimation();
           }
       });
       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
       buttonPanel.add(spinButton);
       add(buttonPanel, BorderLayout.SOUTH);
   }
   // Custom JPanel class for drawing the sliced circle
   class SlicedCirclePanel extends JPanel {
       private final int[] numbers = {
               0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26
       };
           private Timer timer;
           private double rotationAngle = 0.0;
           private int counter = 0;
           private int totalFrames = (int) (300 * Math.random()) + 200;;
           private double initialSpeed = 5.0;
           private double finalSpeed = 0.5;
           public SlicedCirclePanel() {
               timer = new Timer(20, new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                       // Update the rotation angle and repaint
                       counter++;
                       if (counter <= totalFrames) {
                           double t = (double) counter / totalFrames;
                           double speed = Math.abs(initialSpeed + (finalSpeed - initialSpeed) * (1 - Math.cos(Math.PI / 2 * t)));
                           rotationAngle += Math.toRadians(speed); // Increment rotation angle by calculated speed
                           repaint();
                       } else {
                           // Stop the animation
                           stopAnimation();
                       }
                   }
               });
           }
           public void startAnimation() {
               // Start the animation timer
               timer.start();
           }
           public void stopAnimation() {
               // Stop the animation timer
               resetCounter();
               timer.stop();
           }
           public void resetCounter() {
               counter = 0;
           }
           public void resetTF() {
               totalFrames = (int) (300 * Math.random()) + 200;
           }
       
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           Graphics2D g2 = (Graphics2D) g;
           int centerX = getWidth() / 2;
           int centerY = getHeight() / 2;
           int outerRadius = 450;
           int innerRadius = 300;
           int slices = numbers.length;
           double angleIncrement = 360.0 / slices;
           int arcAngleDegrees = (int) angleIncrement;
           // Apply rotation transformation
           g2.rotate(rotationAngle, centerX, centerY);
          
           g2.setColor(Color.GREEN);
           g2.fillArc(centerX - outerRadius, centerY - outerRadius, 2 * outerRadius, 2 * outerRadius, 0, arcAngleDegrees + 1);
           g2.setColor(getBackground());
           g2.fillArc(centerX - innerRadius, centerY - innerRadius, 2 * innerRadius, 2 * innerRadius, 0, arcAngleDegrees + 1);
          
           g2.setColor(Color.WHITE);
           double angle0 = Math.toRadians(0 + arcAngleDegrees / 2.0);
           int x0Text = centerX + (int) ((outerRadius + innerRadius) / 2.0 * Math.cos(angle0));
           int y0Text = centerY - (int) ((outerRadius + innerRadius) / 2.0 * Math.sin(angle0));
           g2.drawString(String.valueOf(numbers[0]), x0Text - 10, y0Text + 5);
          
           for (int i = 1; i < slices; i++) {
               double startAngle = i * angleIncrement;
               int startAngleDegrees = (int) startAngle;
               if (i % 2 == 0) {
                   g2.setColor(Color.RED);
               } else {
                   g2.setColor(Color.BLACK);
               }
               // Draw the slices
               g2.fillArc(centerX - outerRadius, centerY - outerRadius, 2 * outerRadius, 2 * outerRadius, startAngleDegrees, arcAngleDegrees + 1);
               g2.setColor(getBackground());
               g2.fillArc(centerX - innerRadius, centerY - innerRadius, 2 * innerRadius, 2 * innerRadius, startAngleDegrees, arcAngleDegrees + 1);
              
               double angle = Math.toRadians(startAngle + arcAngleDegrees / 2.0);
               int xText = centerX + (int) ((outerRadius + innerRadius) / 2.0 * Math.cos(angle));
               int yText = centerY - (int) ((outerRadius + innerRadius) / 2.0 * Math.sin(angle));
               g2.setColor(Color.WHITE);
               g2.drawString(String.valueOf(numbers[i]), xText - 10, yText + 5);
           }
           // Draw the outlines of the circles
           g2.setColor(Color.BLACK);
           g2.drawOval(centerX - outerRadius, centerY - outerRadius, 2 * outerRadius, 2 * outerRadius);
           g2.drawOval(centerX - innerRadius, centerY - innerRadius, 2 * innerRadius, 2 * innerRadius);
           g2.drawOval(centerX - 13, centerY - 13, 26, 26);
           // Draw the interior circle
           g2.setColor(Color.BLACK);
           g2.fillOval(centerX - innerRadius + 4, centerY - innerRadius + 4, 2 * innerRadius - 8, 2 * innerRadius - 8);
           g2.setColor(Color.WHITE);
           Font bigFont = new Font("Serif", Font.BOLD, 40);
           g2.setFont(bigFont);
           g2.drawString("ROULETTE", centerX-115, centerY);
           // Reset rotation transformation
           g2.rotate(-rotationAngle, centerX, centerY);
           // Draw the triangle at the top
           int triangleHeight = 25;
           int triangleBase = 25;
           int[] xPoints = {centerX, centerX - triangleBase / 2, centerX + triangleBase / 2};
           int[] yPoints = {centerY - outerRadius + triangleHeight - 5, centerY - outerRadius - 5, centerY - outerRadius - 5};
           g2.setColor(Color.MAGENTA);
           g2.fillPolygon(xPoints, yPoints, 3);
       }
   }
   public static void main(String[] args) {
       // Create and display the frame
       SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               Roulette frame = new Roulette();
               frame.setVisible(true);
           }
       });
   }
}

