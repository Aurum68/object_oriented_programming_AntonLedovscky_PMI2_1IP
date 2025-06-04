package org.practice.keyboard;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Keyboard implements NativeKeyListener{

    private static final List<String> MODIFIERS = Arrays.asList("Ctrl", "Shift", "Alt");

    private final LinkedHashSet<Integer> pressedKeyCodes = new LinkedHashSet<>();

    private List<Integer> lastCombination = new ArrayList<>();

    @Getter
    @Setter
    private ShortcutListener shortcutListener;

    @Setter
    private volatile boolean isListening = true;

    public Keyboard() {
        try {
            GlobalScreen.registerNativeHook();
        }catch (NativeHookException e){
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void startNativeHook() {
        GlobalScreen.addNativeKeyListener(this);
    }

    public void reset(){
        pressedKeyCodes.clear();
        lastCombination.clear();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!isListening) return;
        pressedKeyCodes.add(e.getKeyCode());
        lastCombination = new ArrayList<>(pressedKeyCodes);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

        if (!isListening) return;
        pressedKeyCodes.remove(e.getKeyCode());
        if (pressedKeyCodes.isEmpty() && !lastCombination.isEmpty()) {
            String shortcut = formatCombination();
            System.out.println("Combination: " + shortcut);
            lastCombination.clear();
            onKeyCombinationCaptured(shortcut);
        }
    }

    private String formatCombination() {
        List<String> modifiers = new ArrayList<>();
        List<String> keys = new ArrayList<>();

        getKeysAndModifiersFromCombination(modifiers, keys);

        List<String> result = new ArrayList<>(sortModifiers(modifiers));
        result.addAll(keys);
        return String.join("+", result);
    }

    private void getKeysAndModifiersFromCombination(List<String> modifiers, List<String> keys){
        for (Integer keyCode : lastCombination) {
            String keyText = NativeKeyEvent.getKeyText(keyCode);
            checkAndAddModifier(keyText, modifiers);
            if (!modifiers.contains(keyText)) keys.add(keyText);
        }
    }

    private static void checkAndAddModifier(String keyText, List<String> modifiers) {
        MODIFIERS.stream()
                .filter(modifier -> keyText.toLowerCase().contains(modifier.toLowerCase()))
                .findFirst()
                .ifPresent(modifiers::add);
    }

    private static List<String> sortModifiers(List<String> modifiers) {
        return MODIFIERS.stream()
                .filter(modifiers::contains)
                .toList();
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}

    public void onKeyCombinationCaptured(String shortcut) {
        if (isListening && shortcutListener != null) {
            shortcutListener.onShortcutDetected(shortcut);
        }
    }
}
