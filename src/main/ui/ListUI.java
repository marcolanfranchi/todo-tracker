package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static model.Task.TaskCategory.*;

// The panel in which the todolist and its buttons and text fields are displayed
public class ListUI extends JPanel implements ListSelectionListener {

    private JList list;
    private DefaultListModel dlm;
    private ToDoList tdl;

    private JButton addTaskButton;
    private JButton deleteTaskButton;
    private JTextField taskName;
    private JTextField taskDueDate;
    private JComboBox taskCategory;
    private JMenuBar menuBar;
    private JScrollPane listScrollPane;
    private JPanel editTasksSection;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todolist.json";

    // EFFECTS: constructs the task manager panel
    public ListUI() {
        super(new BorderLayout());

        tdl = new ToDoList();

        dlm = new DefaultListModel();

        list = new JList(dlm);
        selectableList(list);
        listScrollPane = new JScrollPane(list);

        setJSon();

        addTaskButton = new JButton("Add Task");
        AddTaskListener addTaskListener = new AddTaskListener(addTaskButton);
        addTaskButton.setActionCommand("Add Task");
        addTaskButton.addActionListener(addTaskListener);
        addTaskButton.setEnabled(false);

        addDeleteTaskButton();

        taskName = new JTextField(10);
        taskName.addActionListener(addTaskListener);
        taskName.getDocument().addDocumentListener(addTaskListener);

        addTaskCategoryField();

        taskDueDate = new JTextField(10);
        taskDueDate.addActionListener(addTaskListener);
        taskDueDate.getDocument().addDocumentListener(addTaskListener);

        addMenuBar();

        createListPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets the JSON store for read and write
    private void setJSon() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: constructs frame for inputting task data
    private void createListPanel() {
        editTasksSection = new JPanel();
        editTasksSection.setLayout(new BoxLayout(editTasksSection,
                BoxLayout.PAGE_AXIS));

        editTasksSection.add(new JLabel("Enter task name:"));
        editTasksSection.add(taskName);

        editTasksSection.add(new JLabel("Choose task category"));
        editTasksSection.add(taskCategory);

        editTasksSection.add(new JLabel("Enter the due date:"));
        editTasksSection.add(taskDueDate);


        editTasksSection.add(addTaskButton);
        editTasksSection.add(deleteTaskButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(editTasksSection, BorderLayout.AFTER_LAST_LINE);
        add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
    }

    // MODIFIES: this
    // EFFECTS: constructs the over head menu bar for the task manager
    private void addMenuBar() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu imageMenu = new JMenu("Progress Message");
        setMenuOptions(fileMenu, imageMenu);
        menuBar.setForeground(Color.orange);
        menuBar.add(fileMenu);
        menuBar.add(imageMenu);
    }

    // MODIFIES: this
    // EFFECTS: constructs the JComboBox for choosing the taskCategory
    private void addTaskCategoryField() {
        taskCategory = new JComboBox<String>();
        addOptions(taskCategory);
    }

    // MODIFIES: this
    // EFFECTS: constructs the button to delete a task from the list
    private void addDeleteTaskButton() {
        deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.setActionCommand("Delete Task");
        deleteTaskButton.addActionListener(new DeleteListener());
    }

    // MODIFIES: this
    // EFFECTS: inserts menu items to the menu
    private void setMenuOptions(JMenu fileMenu, JMenu imageMenu) {
        fileMenu.setMnemonic('F');
        addMenuItem(fileMenu, new ListUI.SaveCurrentFileAction(),
                KeyStroke.getKeyStroke("control S"));
        addMenuItem(fileMenu, new ListUI.LoadSavedFileAction(),
                KeyStroke.getKeyStroke("control F"));


        fileMenu.setMnemonic('V');
        addMenuItem(imageMenu, new ListUI.ProgressMessageAction(),
                KeyStroke.getKeyStroke("control V"));
    }

    // EFFECTS: inserts string category options to the category combo box
    private void addOptions(JComboBox taskCategory) {
        taskCategory.addItem("School Work");
        taskCategory.addItem("Chore");
        taskCategory.addItem("Other");
    }

    // EFFECTS: sets select properties for the list
    private void selectableList(JList list) {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(7);
    }


    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }


     //Represents action to be taken when user wants to save the current todolist to file
    class SaveCurrentFileAction extends AbstractAction {

        SaveCurrentFileAction() {
            super("Save Current File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(tdl);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null,
                        "Saved your " + tdl.size() + " task long ToDo List to file");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to save file");
            }
            EventLog.getInstance().logEvent(new Event("Saved a " + tdl.size() + " task long "
                    + "todolist to file"));
        }
    }



     // Represents action to be taken when user wants to load the previously saved file from file
    class LoadSavedFileAction extends AbstractAction {

        LoadSavedFileAction() {
            super("Load Previous File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                ToDoList loadedTdl = jsonReader.read();
                ArrayList<String> stringList = loadedTdl.listToStrings();
                for (String string : stringList) {
                    dlm.addElement(string);
                }
                tdl = loadedTdl;

                JOptionPane.showMessageDialog(null,
                        "Loaded your " + tdl.size() + " task long ToDo List from file");


            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file");
            }
            EventLog.getInstance().logEvent(new Event("Loaded a " + tdl.size() + " task long "
                    + "todolist from file"));
        }
    }


    // Represents action to be taken when user wants to delete a task
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //remove what is selected
            int index = list.getSelectedIndex();
            String stringTask = dlm.get(index).toString();

            dlm.remove(index);

            tdl.deleteTaskString(stringTask);

            if (dlm.getSize() == 0) {
                deleteTaskButton.setEnabled(false);

            } else {
                if (index == dlm.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }


    // Represents action to be taken when user wants to add a task
    class AddTaskListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddTaskListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            Task task = new Task(taskName.getText(), toCategory(taskCategory),
                    taskDueDate.getText(), false);
            String stringTask = task.taskToString();

            dlm.addElement(stringTask);

            tdl.addTask(task);


            //Reset the text fields for taskName and taskDueDate
            taskName.requestFocusInWindow();
            taskName.setText("");
            taskDueDate.setText("");
        }

        // EFFECTS: converts combo box selection to TaskCategory enum type
        private Task.TaskCategory toCategory(JComboBox inputTaskCategory) {
            int selectedIndex = taskCategory.getSelectedIndex();
            Object selection = taskCategory.getItemAt(selectedIndex);
            String stringCategory = selection.toString();

            if (stringCategory.equals("School Work")) {
                return SCHOOL;
            } else if (stringCategory.equals("Chore")) {
                return CHORE;
            } else {
                return OTHER;
            }
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // EFFECTS: disables the add button if text fields are empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable delete button.
                deleteTaskButton.setEnabled(false);

            } else {
                //Selection, enable the delete button.
                deleteTaskButton.setEnabled(true);
            }
        }
    }


    private class ProgressMessageAction extends AbstractAction {

        ProgressMessageAction() {
            super("View Progress Message");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {

            if (tdl.size() == 0) {
                displayHappy();
            } else {
                displayAngry();
            }
        }

        private void displayAngry() {
            ImageIcon angryImage = new ImageIcon("angryteacher1.jpg");
            Image angryPic = angryImage.getImage();
            Image scaledAngryImage = angryPic.getScaledInstance(20, 20, Image.SCALE_DEFAULT);


        }

        private void displayHappy() {
            ImageIcon happyImage = new ImageIcon("cheering.jpg");
            Image happyPic = happyImage.getImage();
            Image scaledHappyImage = happyPic.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            ImageIcon scaledHappyIcon = new ImageIcon(scaledHappyImage);
            JLabel happyLabel = new JLabel(scaledHappyIcon);
        }
    }
}