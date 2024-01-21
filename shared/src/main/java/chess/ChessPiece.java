package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece { //testing commit / push commands for streamdeck

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //throw new RuntimeException("Not implemented");
        return new ArrayList<>();  // temp, from phase 0 vid

    }
}
