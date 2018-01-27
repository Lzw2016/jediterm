package com.jediterm;

import com.jediterm.terminal.ArrayTerminalDataStream;
import com.jediterm.terminal.Terminal;
import com.jediterm.terminal.TerminalColor;
import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.model.TerminalTextBuffer;
import com.jediterm.terminal.model.StyleState;
import com.jediterm.terminal.emulator.Emulator;
import com.jediterm.terminal.emulator.JediEmulator;
import com.jediterm.util.BackBufferTerminal;
import com.jediterm.util.FileUtil;
import com.jediterm.util.NullTerminalOutputStream;
import junit.framework.TestCase;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;

/**
 * @author traff
 */
@Ignore
public abstract class EmulatorTestAbstract extends TestCase {
  protected static void assertColor(TextStyle style, TerminalColor foreground, TerminalColor background) {
    assertEquals(foreground, style.getForeground());
    assertEquals(background, style.getBackground());
  }

  protected TerminalTextBuffer doTest() throws IOException {
    return doTest(80, 24);
  }

  private TerminalTextBuffer doTest(int width, int height) throws IOException {
    return doTest(width, height, FileUtil.loadFileLines(new File(getPathToTest() + ".after.txt")));
  }

  protected TerminalTextBuffer doTest(int width, int height, String expected) throws IOException {
    StyleState state = new StyleState();

    TerminalTextBuffer terminalTextBuffer = new TerminalTextBuffer(width, height, state);

    Terminal terminal = new BackBufferTerminal(terminalTextBuffer, state);

    ArrayTerminalDataStream
        fileStream = new ArrayTerminalDataStream(FileUtil.loadFileText(new File(getPathToTest() + ".txt"),
        "UTF-8"));

    Emulator emulator = new JediEmulator(fileStream, new NullTerminalOutputStream(), terminal);

    while (emulator.hasNext()) {
      emulator.next();
    }

    assertEquals(expected, terminalTextBuffer.getScreenLines());

    return terminalTextBuffer;
  }


  protected abstract String getPathToTest();
}
