package com.p3ng00.efsc.module.textcolors;

import com.p3ng00.efsc.module.Module;
import com.p3ng00.efsc.module.textcolors.command.TextColorsCommand;
import com.p3ng00.efsc.module.textcolors.command.UnsignCommand;
import com.p3ng00.efsc.module.textcolors.listener.TextColorsListener;

import static com.p3ng00.p3plugin.P3Plugin.CONFIG;

public class TextColors extends Module {

    // 0 - Anvil
    // 1 - Book
    // 2 - Chat
    // 3 - Sign
    private boolean[] checks;

    public TextColors() {
        super("Text Colors", new TextColorsListener(), new TextColorsCommand(), new UnsignCommand());
    }

    @Override
    public boolean enable() {
        checks = new boolean[4];
        checks[0] = CONFIG.getBoolean(createPath("anvil"));
        checks[1] = CONFIG.getBoolean(createPath("book"));
        checks[2] = CONFIG.getBoolean(createPath("chat"));
        checks[3] = CONFIG.getBoolean(createPath("sign"));
        return super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        CONFIG.set(createPath("anvil"), checks[0]);
        CONFIG.set(createPath("book"), checks[1]);
        CONFIG.set(createPath("chat"), checks[2]);
        CONFIG.set(createPath("sign"), checks[3]);
    }

    public void setAnvil(boolean enable) {
        checks[0] = enable;
    }

    public void setBook(boolean enable) {
        checks[1] = enable;
    }

    public void setChat(boolean enable) {
        checks[2] = enable;
    }

    public void setSign(boolean enable) {
        checks[3] = enable;
    }

    public boolean getAnvil() {
        return checks[0];
    }

    public boolean getBook() {
        return checks[1];
    }

    public boolean getChat() {
        return checks[2];
    }

    public boolean getSign() {
        return checks[3];
    }
}
