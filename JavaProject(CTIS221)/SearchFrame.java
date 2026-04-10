import javax.swing.*;
import java.awt.*;

public class SearchFrame extends JFrame {
    private JTextField tfId = new JTextField(10);
    private JTextArea taResult = new JTextArea(10, 30);
    private JButton btnSearch = new JButton("Search");

    public SearchFrame() {
        super("Search Reservation");
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel();
        top.add(new JLabel("Enter Reservation ID:"));
        top.add(tfId);
        top.add(btnSearch);

        taResult.setEditable(false);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(taResult), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                Reservation res = ReservationSystem.searchReservationById(id);
                if (res != null) {
                    taResult.setText(res.toString());
                } else {
                    taResult.setText("No reservation found with ID: " + id);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.");
            }
        });

        pack();
      
    }
}
