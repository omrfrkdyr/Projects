import javax.swing.*;
import java.awt.*;

public class DeleteFrame extends JFrame {
    private JTextField tfId = new JTextField(10);
    private JButton btnDelete = new JButton("Remove Permanently");

    public DeleteFrame() {
        super("Remove Reservation");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add(new JLabel("Reservation ID to Delete:"));
        add(tfId);
        add(btnDelete);

        btnDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                boolean deleted = ReservationSystem.deleteReservation(id);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Reservation " + id + " has been removed.");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "ID not found. Removal failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.");
            }
        });

        pack();
     
    }
}
