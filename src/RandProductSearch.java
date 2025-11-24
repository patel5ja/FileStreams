import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultsArea;

    public RandProductSearch() {
        super("Random Product Search");
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(new JLabel("Search Name:"));
        searchField = new JTextField(20);
        top.add(searchField);
        JButton searchBtn = new JButton("Search");
        top.add(searchBtn);
        add(top, BorderLayout.NORTH);

        resultsArea = new JTextArea();
        add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        JButton quitBtn = new JButton("Quit");
        add(quitBtn, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> searchProducts());
        quitBtn.addActionListener(e -> System.exit(0));

        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void searchProducts() {
        resultsArea.setText("");
        String query = searchField.getText().toLowerCase();

        try (RandomAccessFile raf = new RandomAccessFile("products.dat", "r")) {
            while (raf.getFilePointer() < raf.length()) {

                char[] nameChars = new char[Product.NAME_LEN];
                for (int i = 0; i < Product.NAME_LEN; i++) nameChars[i] = raf.readChar();
                String name = new String(nameChars).trim();

                char[] descChars = new char[Product.DESC_LEN];
                for (int i = 0; i < Product.DESC_LEN; i++) descChars[i] = raf.readChar();
                String description = new String(descChars).trim();

                char[] idChars = new char[Product.ID_LEN];
                for (int i = 0; i < Product.ID_LEN; i++) idChars[i] = raf.readChar();
                String id = new String(idChars).trim();

                double cost = raf.readDouble();

                Product p = new Product(name, description, id, cost);

                if (p.getName().toLowerCase().contains(query)) {
                    resultsArea.append(
                            "ID: " + p.getID() + "\n" +
                                    "Name: " + p.getName() + "\n" +
                                    "Description: " + p.getDescription() + "\n" +
                                    "Cost: " + p.getCost() + "\n\n"
                    );
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


}