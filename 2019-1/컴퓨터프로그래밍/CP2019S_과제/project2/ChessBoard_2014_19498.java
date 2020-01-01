//2014-19498
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}

public class ChessBoard_2014_19498 {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Piece[][] chessBoardStatus = new Piece[8][8];
    private ImageIcon[] pieceImage_b = new ImageIcon[7];
    private ImageIcon[] pieceImage_w = new ImageIcon[7];
    private JLabel message = new JLabel("Enter Reset to Start");

    ChessBoard_2014_19498() {
        initPieceImages();
        initBoardStatus();
        initializeGui();
    }

    public final void initBoardStatus() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) chessBoardStatus[j][i] = new Piece();
        }
    }

    public final void initPieceImages() {
        pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

        pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    }

    public ImageIcon getImageIcon(Piece piece) {
        if (piece.color.equals(PlayerColor.black)) {
            if (piece.type.equals(PieceType.king)) return pieceImage_b[0];
            else if (piece.type.equals(PieceType.queen)) return pieceImage_b[1];
            else if (piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
            else if (piece.type.equals(PieceType.knight)) return pieceImage_b[3];
            else if (piece.type.equals(PieceType.rook)) return pieceImage_b[4];
            else if (piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
            else return pieceImage_b[6];
        } else if (piece.color.equals(PlayerColor.white)) {
            if (piece.type.equals(PieceType.king)) return pieceImage_w[0];
            else if (piece.type.equals(PieceType.queen)) return pieceImage_w[1];
            else if (piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
            else if (piece.type.equals(PieceType.knight)) return pieceImage_w[3];
            else if (piece.type.equals(PieceType.rook)) return pieceImage_w[4];
            else if (piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
            else return pieceImage_w[6];
        } else return pieceImage_w[6];
    }

    public final void initializeGui() {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton startButton = new JButton("Reset");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initiateBoard();
            }
        });

        tools.add(startButton);
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(new ButtonListener(i, j));
                b.setMargin(buttonMargin);
                b.setIcon(defaultIcon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
                else b.setBackground(Color.gray);
                b.setOpaque(true);
                b.setBorderPainted(false);
                chessBoardSquares[j][i] = b;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);

        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ChessBoard_2014_19498 cb = new ChessBoard_2014_19498();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
    //================================Utilize these functions========================================//
    class Piece {
        PlayerColor color;
        PieceType type;

        Piece() {
            color = PlayerColor.none;
            type = PieceType.none;
        }

        Piece(PlayerColor color, PieceType type) {
            this.color = color;
            this.type = type;
        }
    }

    public void setIcon(int x, int y, Piece piece) {
        chessBoardSquares[y][x].setIcon(getImageIcon(piece));
        chessBoardStatus[y][x] = piece;
    }

    public Piece getIcon(int x, int y) {
        return chessBoardStatus[y][x];
    }

    public void markPosition(int x, int y) {
        chessBoardSquares[y][x].setBackground(Color.pink);
    }

    public void unmarkPosition(int x, int y) {
        if ((y % 2 == 1 && x % 2 == 1) || (y % 2 == 0 && x % 2 == 0))
            chessBoardSquares[y][x].setBackground(Color.WHITE);
        else chessBoardSquares[y][x].setBackground(Color.gray);
    }

    public void setStatus(String input) {
        message.setText(input);
    }

    public void initiateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) setIcon(i, j, new Piece());
        }
        setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
        setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
        setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
        setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
        for (int i = 0; i < 8; i++) {
            setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
            setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
        }
        setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
        setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
        setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
        setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) unmarkPosition(i, j);
        }
        onInitiateBoard();
    }
    //======================================================Don't modify above==============================================================//
    //======================================================Implement below=================================================================//
    enum MagicType {MARK, CHECK, CHECKMATE, NONE};
    private int selX, selY;
    private boolean check, checkmate, end;
    private PlayerColor status;
    private MagicType type;
    private int kingX, kingY;
    private Piece KingP;

    class ButtonListener implements ActionListener {
        int x, y;

        ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent e) {
            // the end of the Chess game
            if (end == true) return;

            else if (type == MagicType.MARK) {
                AbstractButton button = (AbstractButton) e.getSource();
                Color Col = button.getBackground();

                if (Col.equals(Color.pink)) {
                    move();
                    markInit();
                    type = (kingCheck() == true) ? MagicType.CHECK : MagicType.NONE;
                    checkMate();
                    statusChange();
                }
                else {
                    markInit(); // if the unmarked position is clicked.
                    setX(x);
                    setY(y);
                }
            } else {
                if (getStatus() == getIcon(x, y).color) {
                    marking();
                    setX(x);
                    setY(y);
                    type = (markCheck() == true) ? MagicType.MARK : MagicType.NONE;
                }
            }
        }
        public void move() {
            Piece tmp = getIcon(getX(), getY());
            if (getIcon(x, y).type == PieceType.king) end = true;
            setIcon(x, y, tmp);
            setIcon(getX(), getY(), new Piece());
            setX(x);
            setY(y);
        }
        public void markInit() {
            type = MagicType.NONE;
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) unmarkPosition(i, j);
        }
        public void statusChange() {
            String current = "";

            if (getStatus() == PlayerColor.black) {
                setTurn(PlayerColor.white);
                current = "WHITE";
            }
            else if (getStatus() == PlayerColor.white) {
                setTurn(PlayerColor.black);
                current = "BLACK";
            }
            if (end == true) {
                current = loser();
                setStatus(current + "'S TURN!");
                return;
            }
            if (type == MagicType.CHECKMATE) {
                setStatus(current + "'S TURN / CHECKMATE");
                end = true;
            }
            else if (type == MagicType.CHECK)
                setStatus(current + "'S TURN / CHECK");

            else if (type == MagicType.NONE)
                setStatus(current + "'S TURN");
        }

        public String loser() {
            String loser = "";
            for(int i = 0; i < 8; i++) {
                for(int j =0; j<8; j++) {
                    if(getIcon(i,j).type == PieceType.king) {
                        if(getIcon(i,j).color == PlayerColor.black) {
                            return "WHITE";
                        } else if(getIcon(i, j).color == PlayerColor.white)
                            return "BLACK";
                    }
                }
            }
            return loser;
        }

        public void marking() {
            if (getIcon(x, y).type == PieceType.pawn) pawn();
            else if (getIcon(x, y).type.equals(PieceType.knight)) knight();
            else if (getIcon(x, y).type.equals(PieceType.bishop)) bishop();
            else if (getIcon(x, y).type.equals(PieceType.rook)) rook();
            else if (getIcon(x, y).type.equals(PieceType.king)) king();
            else if (getIcon(x, y).type.equals(PieceType.queen)) queen();
            else return;
        }

        public void pawn() {
            if (getIcon(x, y).color == PlayerColor.black) {
                if (x != 7 && getIcon(x + 1, y).color == PlayerColor.none) {
                    markPosition(x + 1, y);
                    if (x == 1 && getIcon(x + 2, y).color == PlayerColor.none)
                        markPosition(x + 2, y);
                }
                if (x != 7 && y != 0)
                    if (getIcon(x + 1, y - 1).color == PlayerColor.white)
                        markPosition(x + 1, y - 1);

                if (x != 7 && y != 7)
                    if (getIcon(x + 1, y + 1).color == PlayerColor.white)
                        markPosition(x + 1, y + 1);
            }
            else if (getIcon(x, y).color == PlayerColor.white) {
                if (x != 0 && getIcon(x - 1, y).color == PlayerColor.none) {
                    markPosition(x - 1, y);
                    if (x == 6 && getIcon(x - 2, y).color == PlayerColor.none)
                        markPosition(x - 2, y);
                }
                if (x != 0 && y != 0) {
                    if (getIcon(x - 1, y - 1).color == PlayerColor.black)
                        markPosition(x - 1, y - 1);
                }

                if (x != 0 && y != 7) {
                    if (getIcon(x - 1, y + 1).color == PlayerColor.black)
                        markPosition(x - 1, y + 1);
                }
            }
        }

        public void knight() {
            PlayerColor playerColor = getIcon(x, y).color;
            if (x != 0) {
                if (y >= 2) {
                    if (getIcon(x - 1, y - 2).color != playerColor)
                        markPosition(x - 1, y - 2);
                }
                if (y <= 5) {
                    if (getIcon(x - 1, y + 2).color != playerColor)
                        markPosition(x - 1, y + 2);
                }

                if (x != 1) {
                    if (y != 0) {
                        if (getIcon(x - 2, y - 1).color != playerColor)
                            markPosition(x - 2, y - 1);
                    }
                    if (y != 7) {
                        if (getIcon(x - 2, y + 1).color != playerColor)
                            markPosition(x - 2, y + 1);
                    }
                }
            }
            if (x != 7) {
                if (y >= 2) {
                    if (getIcon(x + 1, y - 2).color != playerColor)
                        markPosition(x + 1, y - 2);
                }
                if (y <= 5) {
                    if (getIcon(x + 1, y + 2).color != playerColor)
                        markPosition(x + 1, y + 2);
                }
                if (x != 6) {
                    if (y != 0) {
                        if (getIcon(x + 2, y - 1).color != playerColor)
                            markPosition(x + 2, y - 1);
                    }
                    if (y != 7) {
                        if (getIcon(x + 2, y + 1).color != playerColor)
                            markPosition(x + 2, y + 1);
                    }
                }
            }
        }

        public void bishop() {
            PlayerColor playerColor = getIcon(x, y).color;
            PlayerColor enemyColor = (playerColor == PlayerColor.black) ?
                    PlayerColor.white : playerColor.black;

            for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
                if (getIcon(x - i, y - i).color == playerColor) break;
                else if (getIcon(x - i, y - i).color == enemyColor) {
                    markPosition(x - i, y - i);
                    break;
                } else markPosition(x - i, y - i);
            }
            for (int i = 1; x + i <= 7 && y + i <= 7; i++) {
                if (getIcon(x + i, y + i).color == playerColor) break;
                else if (getIcon(x + i, y + i).color == enemyColor) {
                    markPosition(x + i, y + i);
                    break;
                } else markPosition(x + i, y + i);
            }
            for (int i = 1; x - i >= 0 && y + i <= 7; i++) {
                if (getIcon(x - i, y + i).color == playerColor) break;
                else if (getIcon(x - i, y + i).color == enemyColor) {
                    markPosition(x - i, y + i);
                    break;
                } else markPosition(x - i, y + i);
            }
            for (int i = 1; x + i <= 7 && y - i >= 0; i++) {
                if (getIcon(x + i, y - i).color == playerColor) break;
                else if (getIcon(x + i, y - i).color == enemyColor) {
                    markPosition(x + i, y - i);
                    break;
                } else markPosition(x + i, y - i);
            }
        }

        public void rook() {
            PlayerColor playerColor = getIcon(x, y).color;
            PlayerColor enemyColor = (getIcon(x, y).color == PlayerColor.black) ?
                    PlayerColor.white : PlayerColor.black;

            for (int i = 1; x - i >= 0; i++) {
                if (getIcon(x - i, y).color == playerColor) break;
                else if (getIcon(x - i, y).color == enemyColor) {
                    markPosition(x - i, y);
                    break;
                } else markPosition(x - i, y);
            }
            for (int i = 1; x + i <= 7; i++) {
                if (getIcon(x + i, y).color == playerColor) break;
                else if (getIcon(x + i, y).color == enemyColor) {
                    markPosition(x + i, y);
                    break;
                } else markPosition(x + i, y);
            }
            for (int i = 1; y - i >= 0; i++) {
                if (getIcon(x, y - i).color == playerColor) break;
                else if (getIcon(x, y - i).color == enemyColor) {
                    markPosition(x, y - i);
                    break;
                } else markPosition(x, y - i);
            }
            for (int i = 1; y + i <= 7; i++) {
                if (getIcon(x, y + i).color == playerColor) break;
                else if (getIcon(x, y + i).color == enemyColor) {
                    markPosition(x, y + i);
                    break;
                } else markPosition(x, y + i);
            }
        }

        public void king() {
            PlayerColor playerColor = getIcon(x, y).color;
            if (x != 0) {
                if (y != 0)
                    if (getIcon(x - 1, y - 1).color != playerColor) markPosition(x - 1, y - 1);

                if (getIcon(x - 1, y).color != playerColor) markPosition(x - 1, y);
                if (y != 7)
                    if (getIcon(x - 1, y + 1).color != playerColor) markPosition(x - 1, y + 1);
            }
            if (y != 0)
                if (getIcon(x, y - 1).color != playerColor) markPosition(x, y - 1);

            if (y != 7)
                if (getIcon(x, y + 1).color != playerColor) markPosition(x, y + 1);

            if (x != 7){
                if (y != 0)
                    if (getIcon(x + 1, y - 1).color != playerColor) markPosition(x + 1, y - 1);

                if (getIcon(x + 1, y).color != playerColor) markPosition(x + 1, y);
                if (y != 7)
                    if (getIcon(x + 1, y + 1).color != playerColor) markPosition(x + 1, y + 1);
            }
        }

        public void queen() {
            bishop();
            king();
            rook();
        }

        public boolean markCheck() {
            JButton button;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    button = chessBoardSquares[j][i];
                    Color col = button.getBackground();
                    if (col == Color.pink) return true;
                }
            }
            return false;
        }

        public boolean kingCheck() {
            int X = this.x;
            int Y = this.y;
            check = false;
            PlayerColor playerColor = getIcon(x, y).color;
            // mark every possible movement of the player!
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (getIcon(i, j).color == playerColor) {
                        this.x = i;
                        this.y = j;
                        marking();
                    }
                }
            }
            // find out if there is a king in marked position!
            JButton button;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    button = chessBoardSquares[j][i];
                    Color col = button.getBackground();
                    if (col == Color.pink) {
                        if (getIcon(i, j).type == PieceType.king) {
                            kingX = i;
                            kingY = j;
                            KingP = getIcon(i, j);
                            check = true;
                            break;
                        }
                    }
                }
            }
            this.x = X;
            this.y = Y;
            markInit();
            return check;
        }

        public void checkMate() {
            if (type != MagicType.CHECK) return;
            else {
                /** First step - check if the enemy can be deleted **/
                int[][] enemy = enemyCheck(kingX, kingY);
                int i = 0; // the enemies who can kill the king
                while (enemy[0][i] != -1) {
                    int j = 0;
                    // if there are helper to kill the enemy
                    int[][] helper = delete(enemy[0][i], enemy[1][i]);
                    if (helper[0][0] == -1) break;

                    int tmpX = enemy[0][i];
                    int tmpY = enemy[1][i];
                    /** if the enemy can be deleted by the helper **/
                    /** 1. if king is safe from enemies , then it's not checkmate status **/
                    /** 2. if king is still in danger, then check 2nd step **/
                    while (helper[0][j] != -1) {
                        Piece tmp = getIcon(tmpX, tmpY);
                        Piece helperP = getIcon(helper[0][j], helper[1][j]);
                        setIcon(tmpX, tmpY, helperP);
                        setIcon(helper[0][j], helper[1][j], new Piece());

                        if (kingCheck() == false) {
                            setIcon(tmpX, tmpY, tmp);
                            setIcon(helper[0][j], helper[1][j], helperP);
                            type = MagicType.CHECK;
                            return;
                        }
                        setIcon(tmpX, tmpY, tmp);
                        setIcon(helper[0][j], helper[1][j], helperP);
                        j++;
                    }
                    i++;
                }
                /** 2) Second step - if king can move in any way. king can escape**/
                if (kingMove() == true) {
                    if (moveCheck() == false) {
                        // false => king can run away.
                        // true => king can not runaway.
                        type = MagicType.CHECK;
                        return;
                    }
                }
                // if enemies cannot be deleted && king cannot move or still in danger.
                /** 3) Third step -  put another piece between the king and enemy. **/
                if (obstacleCheck() == false) {
                    type = MagicType.CHECK;
                    return;
                }
            }
            type = MagicType.CHECKMATE;
        }

        public boolean moveCheck() {
            kingCheck();
            PlayerColor playerColor = getIcon(kingX, kingY).color;
            boolean status = true;
            setIcon(kingX, kingY, new Piece());
            int X = kingX;
            int Y = kingY;
            int tmpX = kingX;
            int tmpY = kingY;
            Piece tmpP = KingP;

            if (X != 0) {
                if (Y != 0 && getIcon(X-1, Y-1).color != playerColor)
                    status = status && kingRun(X-1, Y-1);

                if (getIcon(X-1, Y).color != playerColor)
                    status = status && kingRun(X-1, Y);

                if (Y != 7 && getIcon(X-1, Y+1).color != playerColor)
                    status = status && kingRun(X - 1, Y + 1);
            }
            if (Y != 0 && getIcon(X, Y-1).color != playerColor)
                status = status && kingRun(X, Y - 1);

            if (Y != 0 && getIcon(X, Y+1).color != playerColor)
                status = status && kingRun(X, Y+1);

            if (X != 7) {
                if (Y != 0 && getIcon(X+1, Y-1).color != playerColor)
                    status = status && kingRun(X + 1, Y - 1);

                if (getIcon(X+1, Y).color != playerColor)
                    status = status && kingRun(X + 1, Y);

                if (Y != 7 && getIcon(X+1, Y+1).color != playerColor)
                    status = status && kingRun(X+1, Y+1);
            }
            setIcon(tmpX, tmpY, tmpP);
            return status;
        }
        /** if king get still attack or not **/
        public boolean kingRun(int X, int Y) {
            Piece tmp = getIcon(X, Y);
            setIcon(X, Y, KingP);
            boolean attack = kingCheck();
            setIcon(X, Y, tmp);
            return attack;
        }
        /** if there can be any other pieces between king and enemy **/
        public boolean obstacleCheck() {
            int[][] enemy = enemyCheck(kingX, kingY);
            if(enemy[0][1] != -1) {
                return true;
            } // if there are more than one enemy, then checkmate!!

            int tempX = this.x;
            int tempY = this.y;
            this.x = enemy[0][0];
            this.y = enemy[1][0];
            marking();
            this.x = tempX;
            this.y = tempY;

            int a = (kingX < enemy[0][0]) ? kingX : enemy[0][0];
            int b = (kingX < enemy[0][0]) ? enemy[0][0] : kingX;
            int c = (kingY < enemy[1][0]) ? kingY : enemy[1][0];
            int d = (kingY < enemy[1][0]) ? enemy[1][0] : kingY;

        /** only mark the path from king to enemy **/
            for(int j = 0; j<8; j++) {
                for(int m =0; m<8; m++) {
                    if(j<a || j>b || m<c || m>d) unmarkPosition(j, m);
                }
            }
            if(markCheck() == false) return true;
            int[][] area = new int[2][64];
            JButton button;
            int index = 0;

        /** save the marked path in array container and unmark the path **/
            for(int l = 0; l<8; l++) {
                for(int m = 0; m<8; m++) {
                    button = chessBoardSquares[m][l];
                    if(button.getBackground() == Color.pink) {
                        area[0][index] = l;
                        area[1][index] = m;
                        index++;
                    }
                }
            }
            MagicType tmp = type;
            markInit();
            type = tmp;

            PlayerColor playerColor = getIcon(kingX, kingY).color;
        /** mark every possible movement of current player. **/
            for(int k=0; k<8; k++) {
                for(int u = 0; u<8; u++) {
                    if(getIcon(k, u).color == playerColor){
                        this.x = k;
                        this.y = u;
                        marking();
                    }
                }
            }
            this.x = tempX;
            this.y = tempY;
            int k = 0;

        /** if marked position is between the the path from king to enemy **/
        /** the checkmate situation can be solved!!! **/
            while(k < index){
                button = chessBoardSquares[area[1][k]][area[0][k]];
                if(button.getBackground() == Color.pink)
                {
                    markInit();
                    return false;
                }
                k++;
            }
            markInit();
            return true;
        }
        /** returning the helpers for deleting the enemies **/
        public int[][] delete(int enemyX, int enemyY) {
            int X = enemyX;
            int Y = enemyY;
            int tmpX = this.x;
            int tmpY = this.y;
            this.x = kingX;
            this.y = kingY;
            // current pos = > kingX && kingY

            int[][] helper = enemyCheck(X, Y);
            this.x = tmpX;
            this.y = tmpY;
            return helper;
        }
        /** if king can move in any way, return true **/
        public boolean kingMove() {
            int X = this.x;
            int Y = this.y;
            this.x = kingX;
            this.y = kingY;

            king();
            boolean C = markCheck();
            this.x = X;
            this.y = Y;

            MagicType tmp = type;
            markInit();
            type = tmp;

            return C;
        }
        /** returning possible enemies **/
        public int[][] enemyCheck(int Xpos, int Ypos) {
            int[][] tmp = new int[2][30];
            for(int i = 0; i<2; i++) {
                for(int j = 0; j<30; j++) {
                    tmp[i][j] = -1;
                }
            }
            int index = 0;
            PlayerColor playerColor = getIcon(Xpos, Ypos).color;
            PlayerColor enemyColor = (playerColor == PlayerColor.black) ?
                    PlayerColor.white : PlayerColor.black;
            JButton button;
            int tempX = this.x;
            int tempY = this.y;

            for(int i = 0; i<8; i++) {
                for(int j = 0; j<8; j++) {
                    if(getIcon(i, j).color == enemyColor) {
                        this.x = i;
                        this.y = j;
                        marking();
                        /** saving the enemies in array, who can kill king **/
                        button = chessBoardSquares[Ypos][Xpos];
                        if(button.getBackground() == Color.pink) {
                            tmp[0][index] = i;
                            tmp[1][index] = j;
                            index++;
                        } markInit();
                    }
                }
            }
            this.x = tempX;
            this.y = tempY;
            return tmp;
        }
        public void setX(int X) { selX = X; }
        public void setY(int Y) { selY = Y; }
        public int getX() { return selX; }
        public int getY() { return selY; }
        public void setTurn(PlayerColor color) { status = color; }
        public PlayerColor getStatus() { return status; }
    }
    public void onInitiateBoard() {
        setStatus("BLACK'S TURN");
        status = PlayerColor.black;
        type = null;
        checkmate = false;
        end = false;
        check = false;
    }
}