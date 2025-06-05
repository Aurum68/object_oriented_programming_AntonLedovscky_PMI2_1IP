package org.practice.UI;

import lombok.Getter;
import org.practice.keyboard.Keyboard;
import org.practice.keyboard.ShortcutListener;

import java.util.Scanner;
import java.util.Set;

public class ShortcutRegistrationUI implements ShortcutListener {

   private final Object lock = new Object();

    @Getter
    private volatile String capturedShortcut;

    @Getter
    private String command;

    private final Keyboard keyboard;

    public ShortcutRegistrationUI(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public void onShortcutDetected(String shortcut) {
        synchronized (lock) {
            capturedShortcut = shortcut;
            keyboard.setListening(false);
            lock.notifyAll();
        }
    }

    public void run(Set<String> availableCommands) {
        System.out.println("Press any shortcut. You have to use 'Ctrl' or 'Alt' or 'Shift' to start shortcut.");

        this.capturedShortcut = waitForShortcut();
        if (capturedShortcut == null) throw new RuntimeException("Shortcut not available");

        keyboard.setListening(false);

        System.out.println();
        System.out.println("Input command to finish registration. You can choose one of the following commands:");
        for (String command : availableCommands) {
            System.out.print(command + "\n");
        }

        System.out.println();
        this.keyboard.setListening(false);
        this.command = new Scanner(System.in).nextLine();
        if (command == null || command.isEmpty()) throw new RuntimeException("Could not find command");
        System.out.println("Successful registration: " + capturedShortcut + " : " + command);
        keyboard.setListening(true);
    }

    public String waitForShortcut() {
        synchronized (lock) {
            capturedShortcut = null;
            keyboard.setListening(true);

            while (capturedShortcut == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return capturedShortcut;}

    }
}
