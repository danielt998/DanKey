import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.*;
import javax.sound.midi.*;

public class MainGUI extends JFrame implements ActionListener{
  public DanKey danKey;
  public JComboBox instrument;

  public void initUI(DanKey dk){
    setTitle("DanKey v0.0.1");
    setSize(300,300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    danKey = dk;
    Container pane = getContentPane();
    setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    //then add some components
    pane.add(new JLabel("Please select the instrument to use"));
    instrument = new JComboBox(danKey.synthesizer.getAvailableInstruments());
    instrument.addActionListener(this);
    pane.add(instrument);

    //change the font size of everything and align all components to the left
    for (Component j:pane.getComponents()){
      j.setFont(new Font("Arial", Font.PLAIN, 40));
      ((JComponent)j).setAlignmentX(Component.LEFT_ALIGNMENT);
    }
  }

  public void changeInstrument(Instrument inst){
    danKey.synthesizer.loadInstrument(inst);
    MidiChannel[] mc = danKey.synthesizer.getChannels();
    //note that the below is a hack based on the knowledge that the first
    //instrument to be selected is always a piano, which will use the first 
    //channel
    mc[0].programChange(inst.getPatch().getProgram());
  }

  public void actionPerformed(ActionEvent e){
    if (e.getSource() == instrument){
      changeInstrument((Instrument)instrument.getSelectedItem());
    }
  }	
}
