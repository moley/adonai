package org.adonai.midi;

import lombok.extern.slf4j.Slf4j;
import org.adonai.model.Song;

import javax.sound.midi.*;

@Slf4j
public class MidiController {

    public void selectSong (final Song song) {
        log.info("select song " + song.getTitle() + " with program number " + song.getId());
        int programNumber = song.getId() - 1;

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
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
                log.info("Program Change gesendet an: " + info.getName());
            } catch (MidiUnavailableException e) {
                log.error(e.getLocalizedMessage(), e);
            } catch (InvalidMidiDataException e) {
                log.error(e.getLocalizedMessage(), e);
            }


        }
    }
}
