import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.event.*;
import java.awt.Font;
import javax.sound.midi.*;

public class StartGUI extends JFrame implements ActionListener{
  private JButton next;
  private JComboBox inputDevice;
  private JComboBox outputDevice;
  private DanKey danKey;

  public void initUI() {
    setTitle("DanKey v0.0.1");
    setSize(300,300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    //create a new Keyboard
    danKey = new DanKey();

    //do some stuff
    Container pane = getContentPane();
    setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    //then add some components
    pane.add(new JLabel("Please select an input device"));
    inputDevice = new JComboBox(danKey.getDevices());
    pane.add(inputDevice);
    pane.add(new JLabel("Please select an output device"));
    outputDevice = new JComboBox(danKey.getDevices());
    pane.add(outputDevice);
    next = new JButton("Next");
    next.addActionListener(this);
    pane.add(next);

    //change the font size of everything and align to left
    for (Component j:pane.getComponents()){
      j.setFont(new Font("Arial", Font.PLAIN, 40));
      ((JComponent)j).setAlignmentX(Component.LEFT_ALIGNMENT);
    }
  }


  public void actionPerformed(ActionEvent e){
    //for now, we may assume that the action was the next button being pressed
    //in which case, we want to set the input and output device, then call the
    //next GUI (the main one)
    //Also the following is a hack, only works if < 10 midi devices!
    danKey.setInputOutputDevices((MidiDevice.Info)inputDevice.getSelectedItem(),
                              (MidiDevice.Info)outputDevice.getSelectedItem());
    //actually start the playing
    Thread playThread = new Thread(){
      public void run(){
        danKey.play();
      }
    };

    playThread.start();
    startMainGUI(danKey);
  }

  public void startMainGUI(DanKey dk){
    MainGUI mg = new MainGUI();
    mg.initUI(dk);
    mg.pack();
    mg.setVisible(true);
  }

  //just for testing purposes
  public static void main(String[] a){
    StartGUI sg = new StartGUI();
    sg.initUI();
    sg.pack();
    sg.setVisible(true);
  }
}
