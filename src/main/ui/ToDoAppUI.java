package ui;


import model.Event;
import model.EventLog;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

// the ToDoList application
class ToDoAppUI extends JFrame {

    private final JDesktopPane desktop;
    private static final int WIDTH = 350;
    private static final int HEIGHT = 500;
    private final JInternalFrame listManager;


    // EFFECTS: Constructs ToDoList application
    public ToDoAppUI() {

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        listManager = new JInternalFrame("Task Manager", false, true, false, false);
        listManager.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("Marco's ToDoList App");
        setSize(WIDTH, HEIGHT);

        addListPanel();

        listManager.pack();
        listManager.setVisible(true);
        listManager.setSize(WIDTH, HEIGHT);

        listManager.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                listManager.dispose();
                closeDesktop();
                printLog(EventLog.getInstance());
            }
        });

        desktop.add(listManager);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: closes the desktop pane
    private void closeDesktop() {
        WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
    }

    // EFFECTS: prints the event log to console
    private void printLog(EventLog instance) {
        for (Event next : instance) {
            System.out.println(next.toString() + "\n\n");
        }


    }

    // MODIFIES: this
    // EFFECTS: sets up list display panel
    private void addListPanel() {
        ListUI listUI = new ListUI();
        listManager.add(listUI, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: centres the application on the screen
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    //Represents action to be taken when user clicks desktop
    //to switch focus. (Needed for key handling.)
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            ToDoAppUI.this.requestFocusInWindow();
        }

    }

    // starts the application
    public static void main(String[] args) {

        new ToDoAppUI();
    }

}
