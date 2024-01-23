package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] board;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //throw new RuntimeException("Not implemented");
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //throw new RuntimeException("Not implemented");
        //System.out.println("GetPiece called");

        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // clear out board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        //System.out.print(showBoard(board));

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                // populate WHITE special pieces
                board[7][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                board[7][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                board[7][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                board[7][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                board[7][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                board[7][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                board[7][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                board[7][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
            }
            if (i == 1) {
                // populate WHITE pawns
                for (int j = 0; j < 8; j++) {
                    board[6][j] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                }
            }

            if (i == 2) {
                // populate BLACK pawns
                for (int j = 0; j < 8; j++) {
                    board[1][j] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                }
            }
            if (i == 3) {
                // populate BLACK special pieces
                board[0][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                board[0][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                board[0][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                board[0][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                board[0][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                board[0][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                board[0][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                board[0][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
            }
        }
        //System.out.print(showBoard(board));
        //throw new RuntimeException("Not implemented");
    }

    private StringBuilder showBoard(ChessPiece[][] board) {
        StringBuilder txtBoard = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            StringBuilder row = new StringBuilder("|");
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                String pieceSymbol = (piece != null) ? getPieceSymbol(piece) : " ";  // Check for null
                row.append(pieceSymbol).append("|");
            }
            txtBoard.append(row).append("\n");  // Append newline character
        }
        txtBoard.append("\n");

        return txtBoard;
    }

    private String getPieceSymbol(ChessPiece piece) {
        String symbol = "";
        ChessPiece.PieceType pieceType = piece.getPieceType();

        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            symbol = getWhitePieceSymbol(pieceType);
        } else {
            symbol = getBlackPieceSymbol(pieceType);
        }

        return symbol;
    }

    private String getWhitePieceSymbol(ChessPiece.PieceType pieceType) {
        switch (pieceType) {
            case KING:
                return "K";
            case QUEEN:
                return "Q";
            case BISHOP:
                return "B";
            case KNIGHT:
                return "N";  // "N" for Knights
            case ROOK:
                return "R";
            case PAWN:
                return "P";
            default:
                return "";
        }
    }

    private String getBlackPieceSymbol(ChessPiece.PieceType pieceType) {
        switch (pieceType) {
            case KING:
                return "k";
            case QUEEN:
                return "q";
            case BISHOP:
                return "b";
            case KNIGHT:
                return "n";  // "N" for Knights
            case ROOK:
                return "r";
            case PAWN:
                return "p";
            default:
                return "";
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
