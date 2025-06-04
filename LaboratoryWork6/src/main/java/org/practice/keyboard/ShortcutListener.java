package org.practice.keyboard;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public interface ShortcutListener {
    void onShortcutDetected(String shortcut);
}
