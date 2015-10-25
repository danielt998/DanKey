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

  public String[] getDevices(){
    infos = MidiSystem.getMidiDeviceInfo();
    String[] list = new String[infos.length]; 
    for (int i = 0; i < infos.length; i++){
      list[i] = i + ")" + infos[i].getName() + " - "+ infos[i].getDescription();
    }
    return list;
  }
   
   public void setInputOutputDevices(int input, int output){
    try {
      //first set input
      inputDevice =
          MidiSystem.getMidiDevice(infos[input]);
      //then output
      outputDevice =
          MidiSystem.getMidiDevice(infos[output]);
    } catch (MidiUnavailableException e) {
      System.out.println("A MidiUnavailableException occured");
      e.getStackTrace();
    }
  }   

  public void play(){
    try{
      synthesizer = MidiSystem.getSynthesizer();
      Transmitter transmitter;
      Receiver receiver;

      // Open a connection to your input device
      inputDevice.open();
      synthesizer.open();

      final MidiChannel[] mc = synthesizer.getChannels();
      Instrument[] instr = synthesizer.getDefaultSoundbank().getInstruments();
      synthesizer.loadInstrument(instr[90]);

      // Get the transmitter class from your input device
      transmitter = inputDevice.getTransmitter();
      // Get the receiver class from your sequencer
      receiver = synthesizer.getReceiver();
      // Bind the transmitter to the receiver so the receiver gets input from the transmitter
      transmitter.setReceiver(receiver);
      //then wait for an interrupt (do nothing)
      try {
        Thread.sleep(100000000);
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    } catch (MidiUnavailableException e) {
      System.out.println("A MidiUnavailableException occured");
      e.getStackTrace();
    } finally {
      inputDevice.close();
      outputDevice.close();
    }    
  }//method
  
}
