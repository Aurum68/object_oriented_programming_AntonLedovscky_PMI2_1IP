package org.practice.command.commands;

import lombok.RequiredArgsConstructor;
import org.practice.model.MediaPlayer;
import org.practice.writers.TxtWriter;

@RequiredArgsConstructor
public class MediaPlayerOnCommand implements UndoableCommand{

    private static final TxtWriter TXT_WRITER = new TxtWriter();

    private final MediaPlayer mediaPlayer;

    @Override
    public void undo() {
        TXT_WRITER.write(mediaPlayer.close(), false);
    }

    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public void execute() {
        TXT_WRITER.write(mediaPlayer.launch(), false);
    }
}
