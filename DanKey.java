//http://stackoverflow.com/questions/1485307/java-midi-getting-data-from-piano

import javax.sound.midi.*;
import java.util.Scanner;

public class DanKey{

  public static void main(String[] args){
  }

  public MidiDevice inputDevice;
  public MidiDevice outputDevice;
  public MidiDevice.Info[] infos;
  public Synthesizer synthesizer;

  public MidiDevice.Info[] getDevices(){
    return MidiSystem.getMidiDeviceInfo();
  }
   
   public void setInputOutputDevices(MidiDevice.Info input,
                                     MidiDevice.Info output){
    try {
      inputDevice = MidiSystem.getMidiDevice(input);
      outputDevice = MidiSystem.getMidiDevice(output);
    } catch (MidiUnavailableException e) {
      System.out.println("A MidiUnavailableException occured");
      e.printStackTrace();
    }
  }   

  public void play(){
    try{
      synthesizer = MidiSystem.getSynthesizer();
      Transmitter transmitter;
      Receiver receiver;

      // Open a connection to the input device
      inputDevice.open();
      synthesizer.open();

      final MidiChannel[] mc = synthesizer.getChannels();
      Instrument[] instr = synthesizer.getDefaultSoundbank().getInstruments();
      synthesizer.loadInstrument(instr[90]);

      //get transmitter and receiver and connect them
      transmitter = inputDevice.getTransmitter();
      receiver = synthesizer.getReceiver();
      transmitter.setReceiver(receiver);
      //then wait for an interrupt (do nothing)
      try {
        Thread.sleep(100000000);
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    } catch (MidiUnavailableException e) {
      System.out.println("A MidiUnavailableException occured");
      e.printStackTrace();
    } finally {
      inputDevice.close();
      outputDevice.close();
    }    
  }//method
  
}
