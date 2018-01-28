package com.jediterm.util;

import com.jediterm.terminal.model.JediTerminal;
import com.jediterm.terminal.model.StyleState;
import com.jediterm.terminal.model.TerminalTextBuffer;

/**
 * @author traff
 */
public class BackBufferTerminal extends JediTerminal {
    public BackBufferTerminal(TerminalTextBuffer terminalTextBuffer,
                              StyleState initialStyleState) {
        super(new BackBufferDisplay(terminalTextBuffer), terminalTextBuffer, initialStyleState);
    }
}
