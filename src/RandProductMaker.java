import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {

    private JTextField nameField, descField, idField, costField, countField;
    private int recordCount = 0;
    private RandomAccessFile raf;


    private static final int RECORD_SIZE =
            (Product.NAME_LEN * 2) +
                    (Product.DESC_LEN * 2) +
                    (Product.ID_LEN * 2) +
                    8;

    public RandProductMaker() {
        super("Random Product Maker");
        setLayout(new GridLayout(6, 2));


        try {
            File f = new File("products.dat");
            raf = new RandomAccessFile(f, "rw");
            recordCount = (int)(raf.length() / RECORD_SIZE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error opening file.");
            System.exit(0);
        }


        add(new JLabel("Name:")); nameField = new JTextField(); add(nameField);
        add(new JLabel("Description:")); descField = new JTextField(); add(descField);
        add(new JLabel("ID:")); idField = new JTextField(); add(idField);
        add(new JLabel("Cost:")); costField = new JTextField(); add(costField);

        add(new JLabel("Record Count:"));
        countField = new JTextField(String.valueOf(recordCount));
        countField.setEditable(false);
        add(countField);

        JButton addBtn = new JButton("Add");
        JButton quitBtn = new JButton("Quit");
        add(addBtn); add(quitBtn);

        addBtn.addActionListener(e -> addRecord());
        quitBtn.addActionListener(e -> quitProgram());

        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addRecord() {
        try {

            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String id = idField.getText().trim();
            String costStr = costField.getText().trim();

            if (name.isEmpty() || desc.isEmpty() || id.isEmpty() || costStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            if (id.length() > Product.ID_LEN) {
                JOptionPane.showMessageDialog(this, "ID must be 6 characters or fewer.");
                return;
            }

            double cost;
            try {
                cost = Double.parseDouble(costStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cost must be numeric.");
                return;
            }

            Product p = new Product(name, desc, id, cost);


            raf.seek(raf.length());
            raf.writeChars(p.getRAFName());
            raf.writeChars(p.getRAFDescription());
            raf.writeChars(p.getRAFID());
            raf.writeDouble(p.getCost());

            recordCount++;
            countField.setText(String.valueOf(recordCount));


            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error writing record.");
        }
    }

    private void quitProgram() {
        try {
            raf.close();
        } catch (IOException ignored) {}
        System.exit(0);
    }


}
