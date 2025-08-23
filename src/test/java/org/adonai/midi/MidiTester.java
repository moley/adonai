package org.adonai.midi;

import javax.sound.midi.*;

public class MidiTester {

        public static void main(String[] args) {
            int programNumber = 1; // gewünschtes Programm

            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for (MidiDevice.Info info : infos) {
                System.out.println("Device: " + info.getName() + ", " + info.getVendor() + "-" + info.toString() + "-" + info.getClass().getName());
                MidiDevice device = null;
                try {
                    device = MidiSystem.getMidiDevice(info);
                    device.open();
                    Receiver receiver = device.getReceiver();
                    ShortMessage programChange = new ShortMessage();
                    programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, programNumber, 0);
                    receiver.send(programChange, -1);
                    receiver.close();
                    device.close();
                    System.out.println("Program Change gesendet an: " + info.getName());
                } catch (MidiUnavailableException e) {
                    System.out.println (e.getLocalizedMessage());
                } catch (InvalidMidiDataException e) {
                    System.out.println (e.getLocalizedMessage());
                }


            }
        }


}
