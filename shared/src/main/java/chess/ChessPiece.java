package chess;

import java.util.ArrayList;
import java.util.Collection;

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
        throw new RuntimeException("Not implemented (mine)");
    }

    private Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        Collection<ChessMove> possibleMoves = new ArrayList<>();

        int[] NE = {1, 1}; // North East
        int[] SE = {-1, 1}; // South East
        int[] SW = {-1, -1}; // South West
        int[] NW = {1, -1}; // North West

        int[][] directions = {NE, SE, SW, NW}; // which directions are going to be checked

        for (int[] direction : directions) { // loops through the four possible directions
            for (int i = 1; i <= 8; i++) { // checks all possible squares in a straight line
                int[] nextSquare = {row + direction[0] * i, col + direction[1] * i};

                // Check if the next square is on the board
                if (nextSquare[0] >= 1
                        && nextSquare[0] <= 8
                        && nextSquare[1] >= 1
                        && nextSquare[1] <= 8) {

                    // check if there's a piece on the next square
                    ChessPosition newPosition = new ChessPosition(nextSquare[0], nextSquare[1]);
                    ChessPiece piece = board.getPiece(newPosition);
                    if (piece == null) { // new square is empty, add move to possibleMoves list
                        possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                    } else { // new square is occupied by an...
                        if (piece.getTeamColor() != this.getTeamColor()) {  // ...opponent, add move to list
                            possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                            break;
                        } else { // ...ally, do not add to list
                            break;
                        }
                    }
                } else { // invalid move: out of bounds
                    break;
                }
            }
        }
        return possibleMoves;
        //throw new RuntimeException("Not implemented (mine)");
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
            }
            case BISHOP -> {
                validMoves = diagonalMoves(board, myPosition);
            }
            case KNIGHT -> {
            }
            case ROOK -> {
            }
            case PAWN -> {
            }
        }
        return validMoves;
        //throw new RuntimeException("Not implemented");
    }
}
