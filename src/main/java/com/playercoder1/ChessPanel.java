package com.playercoder1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.runelite.client.ui.PluginPanel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class ChessPanel extends PluginPanel {
    private final JButton joinRoomButton;
    private final JTextField roomIdField;
    private final JPanel chessGamePanel;
    private final int chessBoardSize = 8;

    private static final Map<Character, String> pieceMap = new HashMap<>();
    static {
        pieceMap.put('r', "♜");
        pieceMap.put('n', "♞");
        pieceMap.put('b', "♝");
        pieceMap.put('q', "♛");
        pieceMap.put('k', "♚");
        pieceMap.put('p', "♟");
        pieceMap.put('R', "♖");
        pieceMap.put('N', "♘");
        pieceMap.put('B', "♗");
        pieceMap.put('Q', "♕");
        pieceMap.put('K', "♔");
        pieceMap.put('P', "♙");
    }

    public ChessPanel() {
        super();

        setLayout(new BorderLayout());

        joinRoomButton = new JButton("Join Room");
        joinRoomButton.setPreferredSize(new Dimension(150, 30));
        joinRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinRoom();
            }
        });

        chessGamePanel = new JPanel(new GridLayout(chessBoardSize, chessBoardSize));

        roomIdField = new JTextField(10);
        JPanel manualJoinPanel = new JPanel();
        manualJoinPanel.add(new JLabel("Enter Room ID: "));
        manualJoinPanel.add(roomIdField);
        manualJoinPanel.add(joinRoomButton);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(joinRoomButton);
        verticalBox.add(manualJoinPanel);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(chessGamePanel);

        add(verticalBox, BorderLayout.CENTER);

        drawManualChessboard(chessGamePanel);
    }


    private void joinRoom() {
        String roomId = roomIdField.getText();

        if (!roomId.isEmpty()) {
            String accessToken = "YOUR_TOKEN";
            String apiUrl = "https://lichess.org/api/challenge/" + roomId + "/accept";

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(apiUrl)
                        .post(RequestBody.create(null, new byte[0]))
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {

                } else {
                    JOptionPane.showMessageDialog(null, "Failed to join room. HTTP status code: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while connecting to the Lichess API.");
            }


            chessGamePanel.removeAll();
            drawManualChessboard(chessGamePanel);

            chessGamePanel.revalidate();
            chessGamePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid Room ID.");
        }
    }

    private void drawManualChessboard(JPanel panel) {
        panel.removeAll();

        panel.setLayout(new GridLayout(chessBoardSize, chessBoardSize));

        Color lightSquareColor = new Color(240, 217, 181);
        Color darkSquareColor = new Color(181, 136, 99);

        String[] rows = {
                "rnbqkbnr",
                "pppppppp",
                "        ",
                "        ",
                "        ",
                "        ",
                "PPPPPPPP",
                "RNBQKBNR"
        };

        Map<Integer, Color> colorMap = new HashMap<>();
        colorMap.put(0, Color.WHITE);
        colorMap.put(1, Color.BLACK);

        for (int row = 0; row < chessBoardSize; row++) {
            for (int col = 0; col < chessBoardSize; col++) {
                JPanel cell = new JPanel();


                if ((row + col) % 2 == 0) {
                    cell.setBackground(lightSquareColor);
                } else {
                    cell.setBackground(darkSquareColor);
                }

                char pieceChar = rows[row].charAt(col);
                String pieceSymbol = pieceMap.get(pieceChar);
                if (pieceSymbol != null) {
                    JLabel pieceLabel = new JLabel(pieceSymbol);
                    pieceLabel.setForeground(colorMap.get(Character.isUpperCase(pieceChar) ? 0 : 1));
                    cell.add(pieceLabel);
                }

                panel.add(cell);
            }
        }

        panel.revalidate();
        panel.repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 500);
    }
}