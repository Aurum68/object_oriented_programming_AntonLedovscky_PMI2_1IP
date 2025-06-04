package org.practice.command.commands;

import lombok.RequiredArgsConstructor;
import org.practice.model.Volume;
import org.practice.writers.TxtWriter;

@RequiredArgsConstructor
public class VolumeUpCommand implements UndoableCommand{

    private static final TxtWriter TXT_WRITER = new TxtWriter();

    private final Volume volume;

    @Override
    public void undo() {
        TXT_WRITER.write(this.volume.volumeDown(), false);
    }

    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public void execute() {
        TXT_WRITER.write(this.volume.volumeUp(), false);
    }
}
