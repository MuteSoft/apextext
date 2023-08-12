package org.apex.base.action;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import javax.swing.AbstractAction;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import org.apex.base.logging.Logger;

public class ReadConsoleAction extends AbstractAction {
  private OutputStream out;
  /**
   * Creates a new instance.
   */
  public ReadConsoleAction(OutputStream out) {
    super();
    this.out = out;
  }

  /**
   * Reads user input and supply it to the given process.
   * @param e The generated {@code ActionEvent}.
   * @see AbstractAction#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
    JTextComponent component = (JTextComponent) e.getSource();
    Document document = component.getDocument();
    try {
      Element root = document.getDefaultRootElement();
      int row = root.getElementIndex(component.getCaretPosition());
      int startOffset = root.getElement(row).getStartOffset();
      int endOffset = root.getElement(row).getEndOffset();
      String text = document.getText(startOffset, endOffset - startOffset - 1) + "\n";
      //text = text.replaceFirst("\\r", "");
      this.out.write(text.getBytes(StandardCharsets.UTF_8));
      this.out.flush();
      document.insertString(component.getCaretPosition(), "\n", null);
    } catch(BadLocationException ble){
      Logger.logError("Unable to read user input.", ble);
    } catch(IOException ioe){
      Logger.logError("Unable to supply user input", ioe);
    }
  }
}
