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

    private Collection<ChessMove> findLinearMoves(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ArrayList<ChessMove> possibleMoves = new ArrayList<>();

        // loop through all squares in a straight line
        for (int i = 1; i < 8; i++) {
            int nextRow = row + i * rowDirection;
            int nextCol = col + i * colDirection;

            // check if the position is valid
            if (inValid(nextRow, nextCol)) {
                break;
            }

            // position is valid, make ChessPosition class at location
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

            // check if there's a piece on the next square
            ChessPiece piece = board.getPiece(nextPosition);

            if (piece == null) { // new square is empty, add move to possibleMoves list
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
        return possibleMoves;
    }

    private ChessMove findMove(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int nextRow = row + rowDirection;
        int nextCol = col + colDirection;

        // check if the position is valid
        if (inValid(nextRow, nextCol)) {
            return null;
        }

        // position is valid, make ChessPosition class at location
        ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
        // check if there's a piece on the next square
        ChessPiece piece = board.getPiece(nextPosition);

        if (piece == null) { // new square is empty, add move to possibleMoves list
            return new ChessMove(myPosition, nextPosition, null);
        } else { // new square is occupied by an...
            if (piece.getTeamColor() != this.getTeamColor()) {  // ...opponent, add move to list
                return new ChessMove(myPosition, nextPosition, null);
            } else { // ...ally, do not add to list
                return null;
            }
        }
    }

    private boolean inValid(int nextRow, int nextCol) {
        return nextRow <= 0 || nextRow > 8 || nextCol <= 0 || nextCol > 8;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        Collection<ChessMove> validMoves = new ArrayList<>();

        switch (this.type) {
            case KING -> {
                int[][] directions = {
                        {1, 0}, // North
                        {1, 1},   // North East
                        {0, 1}, // East
                        {-1, 1},  // South East
                        {-1, 0}, // South
                        {-1, -1}, // South West
                        {0, -1},  // West
                        {1, -1}   // North West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    ChessMove possibleMove = findMove(board, myPosition, direction[0], direction[1]);
                    if (possibleMove != null){
                        validMoves.add(possibleMove);
                    }
                }
            }
            case QUEEN -> {
                int[][] directions = {
                        {1, 0}, // North
                        {1, 1},   // North East
                        {0, 1}, // East
                        {-1, 1},  // South East
                        {-1, 0}, // South
                        {-1, -1}, // South West
                        {0, -1},  // West
                        {1, -1}   // North West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    validMoves.addAll(findLinearMoves(board, myPosition, direction[0], direction[1]));
                }
            }
            case BISHOP -> {
                int[][] directions = {
                        {1, 1},   // North East
                        {-1, 1},  // South East
                        {-1, -1}, // South West
                        {1, -1}   // North West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    validMoves.addAll(findLinearMoves(board, myPosition, direction[0], direction[1]));
                }
            }
            case KNIGHT -> {
                int[][] directions = {
                        {2, 1},
                        {1, 2},
                        {-1, 2},
                        {-2, 1},
                        {-2, -1},
                        {-1, -2},
                        {1, -2},
                        {2, -1}
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    ChessMove possibleMove = findMove(board, myPosition, direction[0], direction[1]);
                    if (possibleMove != null){
                        validMoves.add(possibleMove);
                    }
                }

            }
            case ROOK -> {
                //validMoves = straightMoves(board, myPosition);
            }
            case PAWN -> {
            }
        }
        return validMoves;
        //throw new RuntimeException("Not implemented");
    }
}
