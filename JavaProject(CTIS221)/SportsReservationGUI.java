import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SportsReservationGUI extends JFrame {

    private static final Map<Integer, Set<String>> studentReservationDates = new HashMap<>();

    private JComboBox<String> courtCombo = new JComboBox<>();
    private JComboBox<String> surfaceCombo = new JComboBox<>(new String[]{"Hard", "Clay", "Grass"});
    private JLabel lblSurface = new JLabel("Surface Type:");

    private JTextField tfResId = new JTextField(10), tfDate = new JTextField(10),
            tfStart = new JTextField(10), tfDur = new JTextField(10),
            tfPlayers = new JTextField(10);

    private JTextField tfStudId = new JTextField(10), tfStudName = new JTextField(10),
            tfStudPhone = new JTextField(10);

    private JTextArea outputArea = new JTextArea(15, 50);
    private Map<String, Integer> courtMap = new HashMap<>();

    private SearchFrame sf;
    private DeleteFrame df;

    public SportsReservationGUI() {
        super("Campus Court Reservation Management System");

        initializeData();
        buildUI();

        sf = new SearchFrame();
        sf.setVisible(false);

        df = new DeleteFrame();
        df.setVisible(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeData() {
        TennisCourt t1 = new TennisCourt(1, "Main Campus Tennis Court", 200.0, 8, 22, true, "Hard", true);
        BasketballCourt b1 = new BasketballCourt(2, "Dormitories Basketball Court", 150.0, 9, 23, true, true, true);

        ReservationSystem.addCourt(t1);
        ReservationSystem.addCourt(b1);

        addCourtOption(t1);
        addCourtOption(b1);
    }

    private void addCourtOption(Court c) {
        String label = c.getCourtId() + " - " + c.getName();
        courtMap.put(label, c.getCourtId());
        courtCombo.addItem(label);
    }

    private void buildUI() {
        setLayout(new BorderLayout(15, 15));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel resPanel = new JPanel(new GridLayout(0, 2, 5, 8));
        resPanel.setBorder(new TitledBorder("Reservation Details"));
        resPanel.add(new JLabel("Select Court:")); 
        resPanel.add(courtCombo);
        resPanel.add(lblSurface); 
        resPanel.add(surfaceCombo);
        resPanel.add(new JLabel("Reservation ID:")); 
        resPanel.add(tfResId);
        resPanel.add(new JLabel("Date (DD-MM-YYYY):")); 
        resPanel.add(tfDate);
        resPanel.add(new JLabel("Start Hour (0-23):")); 
        resPanel.add(tfStart);
        resPanel.add(new JLabel("Duration (Hours):")); 
        resPanel.add(tfDur);
        resPanel.add(new JLabel("Player Count:")); 
        resPanel.add(tfPlayers);

        JPanel studPanel = new JPanel(new GridLayout(0, 2, 5, 8));
        studPanel.setBorder(new TitledBorder("Student Information"));
        studPanel.add(new JLabel("Student ID:")); 
        studPanel.add(tfStudId);
        studPanel.add(new JLabel("Full Name:")); 
        studPanel.add(tfStudName);
        studPanel.add(new JLabel("Phone Number:")); 
        studPanel.add(tfStudPhone);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdd = new JButton("Confirm Reservation");
        JButton btnDisplay = new JButton("Show All Reservations");
        JButton btnRevenue = new JButton("Calculate Total Revenue");
        JButton btnOpenSearch = new JButton("Search Reservation");
        JButton btnOpenDelete = new JButton("Remove Reservation");

        btnPanel.add(btnOpenSearch);
        btnPanel.add(btnOpenDelete);
        btnPanel.add(btnAdd);
        btnPanel.add(btnDisplay);
        btnPanel.add(btnRevenue);

        outputArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    outputArea.setText("--- Current Reservations (Refreshed) ---\n");
                    outputArea.append(ReservationSystem.displayReservations());
                }
            }
        });

        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleAddReservation();
                }
            }
        };

        tfResId.addKeyListener(enterKeyAdapter);
        tfDate.addKeyListener(enterKeyAdapter);
        tfStart.addKeyListener(enterKeyAdapter);
        tfDur.addKeyListener(enterKeyAdapter);
        tfPlayers.addKeyListener(enterKeyAdapter);
        tfStudId.addKeyListener(enterKeyAdapter);
        tfStudName.addKeyListener(enterKeyAdapter);
        tfStudPhone.addKeyListener(enterKeyAdapter);
        
        btnOpenSearch.addActionListener(e -> sf.setVisible(true));
        btnOpenDelete.addActionListener(e -> df.setVisible(true));

        leftPanel.add(resPanel);
        leftPanel.add(studPanel);
        leftPanel.add(btnPanel);

        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(new TitledBorder("System Output Log"));

        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        courtCombo.addActionListener(e -> {
            String selected = (String) courtCombo.getSelectedItem();
            boolean isTennis = selected != null && selected.toLowerCase().contains("tennis");
            lblSurface.setVisible(isTennis);
            surfaceCombo.setVisible(isTennis);
        });

        btnAdd.addActionListener(e -> handleAddReservation());

        btnDisplay.addActionListener(e -> {
            outputArea.setText("--- Current Reservations ---\n");
            outputArea.append(ReservationSystem.displayReservations());
        });

        btnRevenue.addActionListener(e -> {
            double total = ReservationSystem.calculateTotalRevenue();
            outputArea.append("\n[FINANCIAL] Total Revenue Collected: $" + total + "\n");
        });
    }

    private void handleAddReservation() {
        try {
            int rId = Integer.parseInt(tfResId.getText().trim());
            int start = Integer.parseInt(tfStart.getText().trim());
            double dur = Double.parseDouble(tfDur.getText().trim());
            int pCount = Integer.parseInt(tfPlayers.getText().trim());
            int sId = Integer.parseInt(tfStudId.getText().trim());

            String date = tfDate.getText().trim();
            String sName = tfStudName.getText().trim();
            String sPhone = tfStudPhone.getText().trim();

            studentReservationDates.putIfAbsent(sId, new HashSet<>());

            if (studentReservationDates.get(sId).contains(date)) {
                outputArea.append("FAILED: Student ID " + sId + " already has a reservation on " + date + ".\n");
                return;
            }

            int courtId = courtMap.get(courtCombo.getSelectedItem().toString());
            Court selectedCourt = ReservationSystem.searchCourtById(courtId);

            Student student = new Student(sId, sName, sPhone);
            Reservation res = new Reservation(rId, date, start, dur, pCount, selectedCourt, student);

            boolean success = selectedCourt.reserve(res);

            if (success) {
                studentReservationDates.get(sId).add(date);
                outputArea.append("SUCCESS: Reservation ID " + rId + " confirmed for " + sName + ".\n");
            } else {
                outputArea.append("FAILED: Time slot unavailable or outside working hours.\n");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SportsReservationGUI::new);
    }
}