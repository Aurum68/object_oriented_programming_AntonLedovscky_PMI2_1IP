package org.practice.controller;

import org.practice.UI.ShortcutRegistrationUI;
import org.practice.command.CommandFactory;
import org.practice.command.CommandHistory;
import org.practice.command.CommandManager;
import org.practice.keyboard.Keyboard;
import org.practice.keyboard.ShortcutActionListener;
import org.practice.keyboard.ShortcutListener;
import org.practice.model.ShortcutRegistry;
import org.practice.persistence.ShortcutRegistryPersistence;

import java.util.Map;
import java.util.Scanner;

public class Controller implements ShortcutChangeListener{

    private final Keyboard keyboard;
    private final ShortcutListener shortcutListener;
    private final CommandManager commandManager;

    private final ShortcutRegistry shortcutRegistry;
    private final CommandFactory commandFactory;

    private volatile boolean registrationInProgress = false;

    public Controller(Keyboard keyboard) {

        this.keyboard = keyboard;
        this.commandManager = new CommandManager();

        CommandHistory commandHistory = new CommandHistory();
        this.shortcutListener = new ShortcutActionListener(this.commandManager, commandHistory);

        this.shortcutRegistry = ShortcutRegistryPersistence.load();
        this.commandFactory = new CommandFactory(this, this.shortcutRegistry, commandHistory);

        this.keyboard.setShortcutListener(this.shortcutListener);

        this.updateCommands();
    }

    @Override
    public void updateCommands() {
        for (Map.Entry<String, String> entry : shortcutRegistry.getAllShortcuts().entrySet()){
            commandManager.registerCommand(entry.getKey(), commandFactory.createCommand(entry.getValue()));
        }
    }

    @Override
    public String onChangeShortcutRequested() {

        if (registrationInProgress) {
            System.err.println("WARNING: Shortcut registration is already in progress.");
            return null;
        }
        try {
            registrationInProgress = true;
            return this.registerShortcut();
        }finally {
            registrationInProgress = false;
        }
    }

    private String registerShortcut() {
        System.out.print("Before starting registration, release all keys and press Enter...");
        new Scanner(System.in).nextLine();

        this.keyboard.setListening(false);

        ShortcutRegistrationUI ui = new ShortcutRegistrationUI(this.keyboard);
        this.keyboard.reset();
        this.keyboard.setListening(true);
        this.keyboard.setShortcutListener(ui);

        ui.run(commandFactory.getAvailableCommands());

        this.keyboard.reset();

        this.keyboard.setShortcutListener(this.shortcutListener);
        return String.format("%s:%s", ui.getCapturedShortcut(), ui.getCommand());
    }
}

