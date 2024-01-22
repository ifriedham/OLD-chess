package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor color;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return color == piece.color && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING, // 1 square in all directions; cannot move into check
        QUEEN, // any # of squares in all directions
        BISHOP, // any # of squares diagonally
        KNIGHT, // (horse); 2-1 movement; complicated?
        ROOK, // any # of squares vertically/horizontally
        PAWN // 1 square forward (exception: on first turn, it can move 2 forward); kills diagonally; can become a queen?
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> straightMoves(ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {
                {1, 0}, // North
                {0, 1}, // East
                {-1, 0}, // South
                {0, -1}  // West
        };

        return findMoves(board, myPosition, directions);
    }

    private Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        int[][] directions = {
                {1, 1},   // North East
                {-1, 1},  // South East
                {-1, -1}, // South West
                {1, -1}   // North West
        };

        return findMoves(board, myPosition, directions);
    }

    private Collection<ChessMove> findMoves(ChessBoard board, ChessPosition myPosition, int[][] directions) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ArrayList<ChessMove> possibleMoves = new ArrayList<>();

        for (int[] direction : directions) { // loops through the four possible directions
            for (int i = 1; i <= 7; i++) { // checks all possible squares in a straight line
                int nextRow = row + i * direction[0];
                int nextCol = col + i * direction[1];

                if (nextRow < 1 || nextRow > 8 || nextCol < 1 || nextCol > 8) {
                    break;
                }

                ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

                // check if there's a piece on the next square
                ChessPiece piece = board.getPiece(nextPosition);

                if (piece == null) { // new square is empty, add move to possibleMoves list
                    //System.out.println("{" + nextRow + ", " + nextCol + "}, ");
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null));

                } else { // new square is occupied by an...
                    if (piece.getTeamColor() != this.getTeamColor()) {  // ...opponent, add move to list
                        possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
                        break;
                    } else { // ...ally, do not add to list
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<>();

        switch (this.type) {
            case KING -> {
            }
            case QUEEN -> {
                validMoves.addAll(straightMoves(board, myPosition));
                validMoves.addAll(diagonalMoves(board, myPosition));
            }
            case BISHOP -> {
                validMoves = diagonalMoves(board, myPosition);
            }
            case KNIGHT -> {
            }
            case ROOK -> {
                validMoves = straightMoves(board, myPosition);
            }
            case PAWN -> {
            }
        }
        return validMoves;
        //throw new RuntimeException("Not implemented");
    }
}
